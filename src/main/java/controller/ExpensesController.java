package controller;

import java.util.Collection;
import java.util.Collections;
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

import utils.CurrencyChange;
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
			List<ExpenseViewModel> expenseViews = new LinkedList<ExpenseViewModel>();
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
						ExpenseViewModel expenseViewModel = expenseToExpenseViewModel(expense);
						if (expense.getCurrency() != user.getCurrency()) {
							int result = CurrencyChange.convertToThisCurrency(expense.getAmount(), 
										expense.getCurrency(), user.getCurrency());
							float userCurrencyAmount = MoneyOperations.amountPerHendred(result);
							expenseViewModel.setUserCurrencyAmount(userCurrencyAmount);
						}
						expenseViews.add(expenseViewModel);
					}
				}
			}
			
			for (ExpenseViewModel expenseViewModel : expenseViews) {
				String category = "'" + expenseViewModel.getCategory() + "'";
				int oldAmount = 0;

				if (amountsByCategory.containsKey(category)) {
					oldAmount = amountsByCategory.get(category);
				}

				amountsByCategory.put(category, oldAmount + MoneyOperations.moneyToCents(expenseViewModel.getAmount()));
			}

			Collections.sort(expenseViews, (e1, e2) -> e1.getDate().getDayOfMonth() - e2.getDate().getDayOfMonth());
			
			model.addAttribute("categories", amountsByCategory.keySet());
			System.out.println(amountsByCategory.keySet());
			model.addAttribute("expensesAmounts", amountsByCategory.values());
			System.out.println(amountsByCategory.values());
			model.addAttribute("expenses", expenseViews);
			System.out.println(expenseViews);
			model.addAttribute("accounts", accounts);
			System.out.println(accounts);
		} catch (DAOException e) {
			e.printStackTrace();		
		} catch (Exception e) {			
			e.printStackTrace();
		}

		return "allExpenses";
	}

	@RequestMapping(value = "/addExpense", method = RequestMethod.GET)
	public String showAddExpensePage(HttpSession session, Model model) {
		try {
			Currency[] allCurrencies = Currency.values();
			RepeatType[] allRepeatTypes = RepeatType.values();
			String username = (String) session.getAttribute("username");
			User user = userDAO.getUserByUsername(username);
			
			List<String> allCategories = getAllCategoriesForExpenses();			
			List<String> allAccounts = getAllAccountsForUser(user);
			List<String> allTags = getAllTagsForExpenses();

			model.addAttribute("allCurrencies", allCurrencies);
			model.addAttribute("allRepeatTypes", allRepeatTypes);
			model.addAttribute("allCategories", allCategories);
			model.addAttribute("allAccounts", allAccounts);
			model.addAttribute("allTags", allTags);
			model.addAttribute("expenseViewModel", new ExpenseViewModel());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "addExpense";
	}

	@RequestMapping(value = "/addExpense", method = RequestMethod.POST)
	public String addExpense(@ModelAttribute("expenseViewModel") @Valid ExpenseViewModel expenseViewModel,
			BindingResult result, Model model, HttpSession session) throws DAOException {
		
		if (result.hasErrors()) {
			String username = (String) session.getAttribute("username");
			User user = userDAO.getUserByUsername(username);
			List<String> allCategories = getAllCategoriesForExpenses();			
			List<String> allAccounts = getAllAccountsForUser(user);
			List<String> allTags = getAllTagsForExpenses();
			
			model.addAttribute("allCategories", allCategories);
			model.addAttribute("allAccounts", allAccounts);
			model.addAttribute("allTags", allTags);
			return "addExpense";
		}

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
			
			List<String> allCategories = getAllCategoriesForExpenses();				
			List<String> allAccounts = getAllAccountsForUser(user);			
			List<String> allTags = getAllTagsForExpenses();
			
			if (financeOperationDAO.checkUserHasFinanceOperation(expense, user)) {
				ExpenseViewModel expenseViewModel = expenseToExpenseViewModel(expense);
				model.addAttribute("expenseViewModel", expenseViewModel);
				model.addAttribute("allCategories", allCategories);
				model.addAttribute("allAccounts", allAccounts);
				model.addAttribute("allTags", allTags);				
			} else {
				throw new Exception("Invalid expense!");
			}
		} catch(Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "forward:error";
		}
		
		return "editExpense";
	}
	
	@RequestMapping(value = "/editExpense", method = RequestMethod.POST)
	public String editExpense (@ModelAttribute("expenseViewModel") @Valid ExpenseViewModel expenseViewModel, BindingResult result,
			Model model, HttpSession session) throws DAOException {
		String username = (String) session.getAttribute("username");
		User user = userDAO.getUserByUsername(username);
		
		if (result.hasErrors()) {
			
			List<String> allCategories = getAllCategoriesForExpenses();			
			List<String> allAccounts = getAllAccountsForUser(user);
			List<String> allTags = getAllTagsForExpenses();
			
			model.addAttribute("allCategories", allCategories);
			model.addAttribute("allAccounts", allAccounts);
			model.addAttribute("allTags", allTags);
			return "editExpense";
		}
		
		try {			
			Expense expense = expenseViewModelToExpense(expenseViewModel, user);			
			
			if (financeOperationDAO.checkUserHasFinanceOperation(expense, user)) {
				financeOperationDAO.update(expense);
			}
			else {
				throw new Exception("Invalid Income!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:allExpenses";
	}
	
	@RequestMapping(value = "/verifyDeleteExpense", method = RequestMethod.GET)
	public String showVerifyDeleteExpensePage(@ModelAttribute(value="id") int id, Model model, HttpSession session) {
		try {			
			Expense expense = financeOperationDAO.getExpenseById(id);
			model.addAttribute("expenseDate", expense.getDate());
			model.addAttribute("expenseAmount", MoneyOperations.amountPerHendred(expense.getAmount()));
			model.addAttribute("expenseId", id);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return "verifyDeleteExpense";
		
	}
	
	@RequestMapping(value = "/deleteExpense", method = RequestMethod.GET)
	public String deleteExpense(@ModelAttribute(value="id") int id, Model model, HttpSession session) {
		try {
			String username = (String) session.getAttribute("username");
			User user = userDAO.getUserByUsername(username);
			Expense expense = financeOperationDAO.getExpenseById(id);
			if (financeOperationDAO.checkUserHasFinanceOperation(expense, user)) {
				financeOperationDAO.delete(expense);
			}
			else {
				throw new Exception("Invalid expense for deletion!");
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/allExpenses";
	}

	private ExpenseViewModel expenseToExpenseViewModel(Expense expense) throws Exception {
		ExpenseViewModel expenseViewModel = new ExpenseViewModel();
		expenseViewModel.setId(expense.getId());
		expenseViewModel.setAmount(MoneyOperations.amountPerHendred(expense.getAmount()));
		expenseViewModel.setUserCurrencyAmount(MoneyOperations.amountPerHendred(expense.getAmount()));
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
		expense.setId(expenseViewModel.getId());
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
	
	private List<String> getAllCategoriesForExpenses() throws DAOException {
		Collection<Category> categories = categoryDAO.getAllCategoriesForFOType(FinanceOperationType.INCOME);
		List<String> allCategories = new LinkedList<String>();

		for (Category category : categories) {
			allCategories.add(category.getCategoryName());
		}
		
		return allCategories;
	}
	
	private List<String> getAllAccountsForUser(User user) throws DAOException {
		List<Account> userAccounts = (List<Account>) accountDAO.getAllAccountsForUser(user);
		List<String> allAcounts = new LinkedList<String>();

		for (Account acc : userAccounts) {
			allAcounts.add(acc.getTitle());
		}
		
		return allAcounts;
	}
	

	private List<String> getAllTagsForExpenses() throws DAOException {
		List<Tag> tags = (List<Tag>) tagDAO.getAllTagsByTypeFor(FinanceOperationType.EXPENSE);
		List<String> allTags = new LinkedList<String>();

		if (tags != null) {
			for (Tag tag : tags) {
				allTags.add(tag.getTagName());
			}
		}
		return allTags;
	}
}
