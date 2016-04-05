package controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import model.Account;
import model.Category;
import model.Currency;
import model.Expense;
import model.FinanceOperationType;
import model.RepeatType;
import model.Tag;
import model.User;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import utils.MoneyOperations;
import view.model.ExpenseViewModel;
import dao.DAOException;
import dao.IAccountDAO;
import dao.ICategoryDAO;
import dao.IFinanceOperationDAO;
import dao.ITagDAO;
import dao.IUserDAO;

@Controller
public class ExpensesController {
	@Autowired
	private IFinanceOperationDAO financeOperationDAO;
	@Autowired
	private IUserDAO userDAO;
	@Autowired
	private IAccountDAO accountDAO;
	@Autowired
	private ICategoryDAO categoryDAO;
	@Autowired
	private ITagDAO tagDAO;

	@RequestMapping(value = "/allExpenses", method = RequestMethod.GET)
	public String showAllExpenses(HttpSession session, Model model) {
		try {
			String username = (String) session.getAttribute("username");
			User user = userDAO.getUserByUsername(username);
			List<Account> accounts = (List<Account>) accountDAO.getAllAccountsForUser(user);
			Map<String, Integer> amountsByCategory = new HashMap<String, Integer>();
			List<Expense> expenses = new LinkedList<Expense>();
			int month = LocalDate.now().getMonthOfYear();
			int year = LocalDate.now().getYear();

			if (session.getAttribute("month") != null) {
				month = (int) session.getAttribute("month");
			}

			if (session.getAttribute("year") != null) {
				year = (int) session.getAttribute("year");
			}

			for (Account acc : accounts) {
				List<Expense> accExpenses = (List<Expense>) financeOperationDAO.getAllExpensesByAccount(acc);

				for (Expense expense : accExpenses) {
					if (expense.getDate().getMonthOfYear() == month && expense.getDate().getYear() == year) {
						expenses.add(expense);
					}
				}

				for (Expense expense : expenses) {
					String category = "'" + expense.getCategory().getCategoryName() + "'";
					int oldAmount = 0;

					if (amountsByCategory.containsKey(category)) {
						oldAmount = amountsByCategory.get(category);
					}

					amountsByCategory.put(category, oldAmount + expense.getAmount());
				}
			}

			model.addAttribute("categories", amountsByCategory.keySet());
			model.addAttribute("expensesAmounts", amountsByCategory.values());
			model.addAttribute("expenses", expenses);
			model.addAttribute("accounts", accounts);
		} catch (DAOException e) {
			e.printStackTrace();
		}

		return "allExpenses";
	}

	@RequestMapping(value = "/addExpense", method = RequestMethod.GET)
	public String showAddExpensePage(HttpSession session, Model model) {
		try {
			Currency[] allCurrencies = Currency.values();
			RepeatType[] allRepeatTypes = RepeatType.values();
			Collection<Category> categories = categoryDAO.getAllCategoriesForFOType(FinanceOperationType.EXPENSE);
			List<String> allCategories = new LinkedList<String>();

			for (Category category : categories) {
				allCategories.add(category.getCategoryName());
			}

			String username = (String) session.getAttribute("username");
			User user = userDAO.getUserByUsername(username);
			List<Account> userAccounts = (List<Account>) accountDAO.getAllAccountsForUser(user);
			List<String> allAcounts = new LinkedList<String>();

			for (Account acc : userAccounts) {
				allAcounts.add(acc.getTitle());
			}

			List<Tag> tags = (List<Tag>) tagDAO.getAllTagsByTypeFor(FinanceOperationType.EXPENSE);
			List<String> allTags = new LinkedList<String>();

			if (tags != null) {
				for (Tag tag : tags) {
					allTags.add(tag.getTagName());
				}
			}

			model.addAttribute("allCurrencies", allCurrencies);
			model.addAttribute("allRepeatTypes", allRepeatTypes);
			model.addAttribute("allCategories", allCategories);
			model.addAttribute("allAccounts", allAcounts);
			model.addAttribute("allTags", allTags);
			model.addAttribute("expenseViewModel", new ExpenseViewModel());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "addExpense";
	}

	@RequestMapping(value = "/addExpense", method = RequestMethod.POST)
	public String addExpense(@ModelAttribute("expenseViewModel") @Valid ExpenseViewModel expenseViewModel,
			BindingResult result, Model model, HttpSession session) {

		try {
			String username = (String) session.getAttribute("username");
			User user = userDAO.getUserByUsername(username);
			Expense expense = expenseViewModelToExpense(expenseViewModel, user);
			financeOperationDAO.add(expense);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:allExpenses";
	}
	
	@RequestMapping(value = "/editExpense", method = RequestMethod.GET)
	public String showEditExpensePage(@ModelAttribute(value="id") int id, 
			HttpSession session, Model model) {
		try {
			String username = (String) session.getAttribute("username");
			User user = userDAO.getUserByUsername(username);
			Expense expense = financeOperationDAO.getExpenseById(id);
			
			if (financeOperationDAO.checkUserHasFinanceOperation(expense, user)) {
				ExpenseViewModel expenseViewModel = expenseToExpenseViewModel(expense);
				model.addAttribute("expenseViewModel", expenseViewModel);
			} else {
				throw new Exception("Invalid expense!");
			}
		} catch(Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "forward:error";
		}
		
		return "editExpense";
	}

	private ExpenseViewModel expenseToExpenseViewModel(Expense expense) throws Exception {
		ExpenseViewModel expenseViewModel = new ExpenseViewModel();
		expenseViewModel.setId(expense.getId());
		expenseViewModel.setAmount(MoneyOperations.amountPerHendred(expense.getAmount()));
		expenseViewModel.setAccount(expense.getAccount().getTitle());
		expenseViewModel.setCategory(expense.getCategory().getCategoryName());
		expenseViewModel.setCurrency(expense.getCurrency());
		expenseViewModel.setDate(expense.getDate());
		expenseViewModel.setDescription(expense.getDescription());
		expenseViewModel.setRepeatType(expense.getRepeatType());
		List<String> tags = new LinkedList<String>();
		
		if (expense.getTags() != null) {
			for (Tag tag : expense.getTags()) {
				tags.add(tag.getTagName());
			}
		}
		
		expenseViewModel.setTags(tags);
		
		return expenseViewModel;
	}

	private Expense expenseViewModelToExpense(ExpenseViewModel expenseViewModel, User user) throws Exception {
		Expense expense = new Expense();
		expense.setAmount(MoneyOperations.moneyToCents(expenseViewModel.getAmount()));
		expense.setCurrency(expenseViewModel.getCurrency());
		expense.setDate(expenseViewModel.getDate());
		expense.setDescription(expenseViewModel.getDescription());
		expense.setRepeatType(expenseViewModel.getRepeatType());
		expense.setFinanceOperationType(FinanceOperationType.EXPENSE);
		Account account = accountDAO.getAccountForUserByName(expenseViewModel.getAccount(), user);
		expense.setAccount(account);
		Category category = categoryDAO.getCategoryByName(expenseViewModel.getCategory());
		expense.setCategory(category);
		List<Tag> tags = new LinkedList<Tag>();

		if (expenseViewModel.getTags() != null) {
			for (String tagName : expenseViewModel.getTags()) {
				Tag tag = tagDAO.getTagByTagname(tagName);
				tags.add(tag);
			}
		}

		expense.setTags(tags);

		return expense;
	}
}
