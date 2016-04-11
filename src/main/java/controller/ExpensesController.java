package controller;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

import utils.CurrencyConverter;
import utils.MoneyOperations;
import view.model.ExpenseViewModel;
import dao.IAccountDAO;
import dao.ICategoryDAO;
import dao.IFinanceOperationDAO;
import dao.ITagDAO;
import dao.IUserDAO;
import exceptions.APIException;
import exceptions.DAOException;

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
			Map<String, Integer> amountsByCategory = new TreeMap<String, Integer>();
			List<ExpenseViewModel> expenseViews = new LinkedList<ExpenseViewModel>();

			addMonthAndYearToSession(session);
			int month = (int) session.getAttribute("month");
			int year = (int) session.getAttribute("year");

			for (Account acc : accounts) {
				List<Expense> accExpenses = (List<Expense>) financeOperationDAO.getAllExpensesByAccount(acc);
				addAndCalculateExpensesForMonth(user, expenseViews, month, year, accExpenses);
			}

			calculateAmountsByCategory(amountsByCategory, expenseViews);
			List<List<String>> chartData = new LinkedList<List<String>>();
			addChartData(amountsByCategory, chartData);
			Collections.sort(expenseViews, (e1, e2) -> e1.getDate().getDayOfMonth() - e2.getDate().getDayOfMonth());
			model.addAttribute("chartData", chartData);
			model.addAttribute("expenses", expenseViews);
			model.addAttribute("accounts", accounts);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:error";
		}

		return "allExpenses";
	}

	private void addChartData(Map<String, Integer> amountsByCategory, List<List<String>> chartData) {
		for (String category : amountsByCategory.keySet()) {
			int amount = amountsByCategory.get(category);
			List<String> dataRow = new LinkedList<String>();
			dataRow.add(category);
			dataRow.add(String.valueOf(amount));
			chartData.add(dataRow);
		}
	}

	private void calculateAmountsByCategory(Map<String, Integer> amountsByCategory, List<ExpenseViewModel> expenseViews) {
		for (ExpenseViewModel expenseViewModel : expenseViews) {
			String category = "'" + expenseViewModel.getCategory() + "'";
			int oldAmount = 0;

			if (amountsByCategory.containsKey(category)) {
				oldAmount = amountsByCategory.get(category);
			}

			amountsByCategory.put(category, oldAmount + MoneyOperations.moneyToCents(expenseViewModel.getAmount()));
		}
	}

	private void addAndCalculateExpensesForMonth(User user, List<ExpenseViewModel> expenseViews, int month, int year, List<Expense> accExpenses) throws Exception, APIException {
		for (Expense expense : accExpenses) {
			if (expense.getDate().getMonthOfYear() == month && expense.getDate().getYear() == year) {
				ExpenseViewModel expenseViewModel = expenseToExpenseViewModel(expense);
				float userCurrencyAmount = expenseViewModel.getAmount();

				if (expense.getCurrency() != user.getCurrency()) {
					int result = CurrencyConverter.convertToThisCurrency(expense.getAmount(),
								expense.getCurrency(), user.getCurrency());
					userCurrencyAmount = MoneyOperations.amountPerHendred(result);
				}

				expenseViewModel.setUserCurrencyAmount(userCurrencyAmount);
				expenseViewModel.setUserCurrency(user.getCurrency());
				expenseViews.add(expenseViewModel);
			}
		}
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
			List<String> tags = new LinkedList<String>();

			if (allCategories != null && allCategories.size() > 0) {
				Category category = categoryDAO.getCategoryByName(allCategories.get(0));
				Collection<Tag> tagsForCategory = tagDAO.getTagsForCategory(category);

				for (Tag tag : tagsForCategory) {
					tags.add(tag.getTagName());
				}
			}

			model.addAttribute("allCurrencies", allCurrencies);
			model.addAttribute("allRepeatTypes", allRepeatTypes);
			model.addAttribute("allCategories", allCategories);
			model.addAttribute("allAccounts", allAccounts);
			model.addAttribute("tags", tags);
			model.addAttribute("expenseViewModel", new ExpenseViewModel());
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:error";
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
			List<String> tags = new LinkedList<String>();

			if (allCategories != null && allCategories.size() > 0) {
				Category category = categoryDAO.getCategoryByName(allCategories.get(0));
				Collection<Tag> tagsForCategory = tagDAO.getTagsForCategory(category);

				for (Tag tag : tagsForCategory) {
					tags.add(tag.getTagName());
				}
			}

			model.addAttribute("allCategories", allCategories);
			model.addAttribute("allAccounts", allAccounts);
			model.addAttribute("tags", tags);
			return "addExpense";
		}

		try {
			String username = (String) session.getAttribute("username");
			User user = userDAO.getUserByUsername(username);
			Expense expense = expenseViewModelToExpense(expenseViewModel, user);
			financeOperationDAO.add(expense);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:error";
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
			List<String> tags = new LinkedList<String>();

			if (allCategories != null && allCategories.size() > 0) {
				Category category = categoryDAO.getCategoryByName(allCategories.get(0));
				Collection<Tag> tagsForCategory = tagDAO.getTagsForCategory(category);

				for (Tag tag : tagsForCategory) {
					tags.add(tag.getTagName());
				}
			}

			if (financeOperationDAO.checkUserHasFinanceOperation(expense, user)) {
				ExpenseViewModel expenseViewModel = expenseToExpenseViewModel(expense);
				model.addAttribute("expenseViewModel", expenseViewModel);
				model.addAttribute("allCategories", allCategories);
				model.addAttribute("allAccounts", allAccounts);
				model.addAttribute("tags", tags);
			} else {
				throw new Exception("Invalid expense!");
			}
		} catch(Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "redirect:error";
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
			List<String> tags = new LinkedList<String>();

			if (allCategories != null && allCategories.size() > 0) {
				Category category = categoryDAO.getCategoryByName(allCategories.get(0));
				Collection<Tag> tagsForCategory = tagDAO.getTagsForCategory(category);

				for (Tag tag : tagsForCategory) {
					tags.add(tag.getTagName());
				}
			}

			model.addAttribute("allCategories", allCategories);
			model.addAttribute("allAccounts", allAccounts);
			model.addAttribute("tags", tags);
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
			return "redirect:error";
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
			return "redirect:error";
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
			return "redirect:error";
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
		Collection<Category> categories = categoryDAO.getAllCategoriesForFOType(FinanceOperationType.EXPENSE);
		List<String> allCategories = new LinkedList<String>();

		if (categories != null) {
			for (Category category : categories) {
				allCategories.add(category.getCategoryName());
			}
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

	private void addMonthAndYearToSession(HttpSession session){
		if (session.getAttribute("month") == null || session.getAttribute("year") == null) {
			int month = LocalDate.now().getMonthOfYear();
			session.setAttribute("month", month);
			int year = LocalDate.now().getYear();
			session.setAttribute("year", year);
		}
	}
}
