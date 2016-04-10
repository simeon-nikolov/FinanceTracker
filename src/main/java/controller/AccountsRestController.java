package controller;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import model.Account;
import model.Category;
import model.Expense;
import model.FinanceOperationType;
import model.Income;
import model.Tag;
import model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import utils.CurrencyConverter;
import utils.MoneyOperations;
import view.model.ExpenseViewModel;
import view.model.IncomeViewModel;
import dao.IAccountDAO;
import dao.ICategoryDAO;
import dao.IFinanceOperationDAO;
import dao.ITagDAO;
import dao.IUserDAO;
import exceptions.DAOException;

@RestController
public class AccountsRestController {
	@Autowired
	IAccountDAO accDao;
	@Autowired
	IFinanceOperationDAO foDao;
	@Autowired
	IUserDAO userDao;
	@Autowired
	private ITagDAO tagDAO;
	@Autowired
	private ICategoryDAO categoryDAO;

	@RequestMapping(value = "/accounts/expenses", method = RequestMethod.GET)
	public List<ExpenseViewModel> getExpensesForAllAccounts(HttpSession session) {
		List<ExpenseViewModel> result = new LinkedList<ExpenseViewModel>();

		try {
			User user = getUserFromSession(session);
			int month = (int) session.getAttribute("month");
			int year = (int) session.getAttribute("year");

			List<Account> accounts = (List<Account>) accDao.getAllAccountsForUser(user);

			for (Account acc : accounts) {
				result.addAll(getExpensesByAccount(month, year, acc, user));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Collections.sort(result, (e1, e2) -> e1.getDate().getDayOfMonth() - e2.getDate().getDayOfMonth());

		return result;
	}

	@RequestMapping(value = "/accounts/{accountName}/expenses", method = RequestMethod.GET)
	public List<ExpenseViewModel> getExpensesForAccount(@PathVariable("accountName") String accountName,
			HttpSession session) {
		List<ExpenseViewModel> result = new LinkedList<ExpenseViewModel>();

		try {
			User user = getUserFromSession(session);
			int month = (int) session.getAttribute("month");
			int year = (int) session.getAttribute("year");

			Account account = accDao.getAccountForUserByName(accountName, user);
			result = getExpensesByAccount(month, year, account, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collections.sort(result, (e1, e2) -> e1.getDate().getDayOfMonth() - e2.getDate().getDayOfMonth());

		return result;
	}

	@RequestMapping(value = "/accounts/incomes", method = RequestMethod.GET)
	public List<IncomeViewModel> getIncomesForAllAccounts(HttpSession session) {
		List<IncomeViewModel> result = new LinkedList<IncomeViewModel>();

		try {
			User user = getUserFromSession(session);
			int month = (int) session.getAttribute("month");
			int year = (int) session.getAttribute("year");

			List<Account> accounts = (List<Account>) accDao.getAllAccountsForUser(user);

			for (Account acc : accounts) {
				result.addAll(getIncomesByAccount(month, year, acc, user));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Collections.sort(result, (i1, i2) -> i1.getDate().getDayOfMonth() - i2.getDate().getDayOfMonth());

		return result;
	}

	@RequestMapping(value = "/accounts/{accountName}/incomes", method = RequestMethod.GET)
	public List<IncomeViewModel> getIncomesForAccount(@PathVariable("accountName") String accountName,
			HttpSession session) {
		List<IncomeViewModel> result = new LinkedList<IncomeViewModel>();

		try {
			User user = getUserFromSession(session);
			int month = (int) session.getAttribute("month");
			int year = (int) session.getAttribute("year");

			Account account = accDao.getAccountForUserByName(accountName, user);
			result = getIncomesByAccount(month, year, account, user);

		} catch (Exception e) {
			e.printStackTrace();
		}

		Collections.sort(result, (i1, i2) -> i1.getDate().getDayOfMonth() - i2.getDate().getDayOfMonth());

		return result;
	}

	private List<ExpenseViewModel> getExpensesByAccount(int month, int year, Account acc, User user) throws Exception {
		List<ExpenseViewModel> result = new LinkedList<ExpenseViewModel>();
		List<Expense> accExpenses = (List<Expense>) foDao.getAllExpensesByAccount(acc);

		for (Expense expense : accExpenses) {
			if (expense.getDate().getMonthOfYear() == month && expense.getDate().getYear() == year) {
				ExpenseViewModel expenseViewModel = expenseToExpenseViewModel(expense);
				float userCurrencyAmount = expenseViewModel.getAmount();

				if (expense.getCurrency() != user.getCurrency()) {
					int amount = CurrencyConverter.convertToThisCurrency(expense.getAmount(),
								expense.getCurrency(), user.getCurrency());
					userCurrencyAmount = MoneyOperations.amountPerHendred(amount);
				}

				expenseViewModel.setUserCurrencyAmount(userCurrencyAmount);
				expenseViewModel.setUserCurrency(user.getCurrency());
				result.add(expenseViewModel);
			}
		}

		return result;
	}

	private List<IncomeViewModel> getIncomesByAccount(int month, int year, Account acc, User user) throws Exception {
		List<IncomeViewModel> result = new LinkedList<IncomeViewModel>();
		List<Income> accIncomes = (List<Income>) foDao.getAllIncomesByAccount(acc);

		for (Income income : accIncomes) {
			if (income.getDate().getMonthOfYear() == month && income.getDate().getYear() == year) {
				IncomeViewModel incomeViewModel = incomeToIncomeViewModel(income);
				float userCurrencyAmount = incomeViewModel.getAmount();

				if (income.getCurrency() != user.getCurrency()) {
					int amount = CurrencyConverter.convertToThisCurrency(income.getAmount(),
							income.getCurrency(), user.getCurrency());
					userCurrencyAmount = MoneyOperations.amountPerHendred(amount);
				}

				incomeViewModel.setUserCurrencyAmount(userCurrencyAmount);
				incomeViewModel.setUserCurrency(user.getCurrency());
				result.add(incomeViewModel);
			}
		}

		return result;
	}

	private User getUserFromSession(HttpSession session) throws DAOException {
		String username = (String) session.getAttribute("username");
		if (username == null || username.isEmpty()) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			username = auth.getName();
			session.setAttribute("username", username);
		}
		User user = userDao.getUserByUsername(username);

		return user;
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
		Account account = accDao.getAccountForUserByName(expenseViewModel.getAccount(), user);
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

	private IncomeViewModel incomeToIncomeViewModel(Income income) throws Exception {
		IncomeViewModel incomeViewModel = new IncomeViewModel();
		incomeViewModel.setId(income.getId());
		incomeViewModel.setAmount(MoneyOperations.amountPerHendred(income.getAmount()));
		incomeViewModel.setUserCurrencyAmount(MoneyOperations.amountPerHendred(income.getAmount()));
		incomeViewModel.setAccount(income.getAccount().getTitle());
		incomeViewModel.setCategory(income.getCategory().getCategoryName());
		incomeViewModel.setCurrency(income.getCurrency());
		incomeViewModel.setDate(income.getDate());
		incomeViewModel.setDescription(income.getDescription());
		incomeViewModel.setRepeatType(income.getRepeatType());
		List<String> tags = new LinkedList<String>();

		if (income.getTags() != null) {
			for (Tag tag : income.getTags()) {
				tags.add(tag.getTagName());
			}
		}

		incomeViewModel.setTags(tags);

		return incomeViewModel;
	}

	private Income incomeViewModelToIncome(IncomeViewModel incomeViewModel, User user) throws Exception {
		Income income = new Income();
		income.setId(incomeViewModel.getId());
		income.setAmount(MoneyOperations.moneyToCents(incomeViewModel.getAmount()));
		income.setCurrency(incomeViewModel.getCurrency());
		income.setDate(incomeViewModel.getDate());
		income.setDescription(incomeViewModel.getDescription());
		income.setRepeatType(incomeViewModel.getRepeatType());
		income.setFinanceOperationType(FinanceOperationType.INCOME);
		Account account = accDao.getAccountForUserByName(incomeViewModel.getAccount(), user);
		income.setAccount(account);
		Category category = categoryDAO.getCategoryByName(incomeViewModel.getCategory());
		income.setCategory(category);
		List<Tag> tags = new LinkedList<Tag>();

		if (incomeViewModel.getTags() != null) {
			for (String tagName : incomeViewModel.getTags()) {
				Tag tag = tagDAO.getTagByTagname(tagName);
				tags.add(tag);
			}
		}

		income.setTags(tags);

		return income;
	}
}
