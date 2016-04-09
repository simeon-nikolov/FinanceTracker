package controller;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import model.Account;
import model.Category;
import model.Expense;
import model.FinanceOperationType;
import model.Tag;
import model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import utils.MoneyOperations;
import view.model.ExpenseViewModel;
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

	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public List<ExpenseViewModel> getExpensesForAllAccounts(HttpSession session) {
		List<ExpenseViewModel> result = new LinkedList<ExpenseViewModel>();
		try {
			User user = getUserFromSession(session);
			int month = (int) session.getAttribute("month");
			int year = (int) session.getAttribute("year");

			List<Account> accounts = (List<Account>) accDao.getAllAccountsForUser(user);

			for (Account acc : accounts) {
				result.addAll(getExpensesByAccount(month, year, acc));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		Collections.sort(result, (e1, e2) -> e1.getDate().getDayOfMonth() - e2.getDate().getDayOfMonth());

		return result;
	}

	@RequestMapping(value = "/accounts/{accountName}", method = RequestMethod.GET)
	public List<ExpenseViewModel> getExpensesForAccount(@PathVariable("accountName") String accountName,
			HttpSession session) {
		List<ExpenseViewModel> result = new LinkedList<ExpenseViewModel>();

		try {
			User user = getUserFromSession(session);
			int month = (int) session.getAttribute("month");
			int year = (int) session.getAttribute("year");

			Account account = accDao.getAccountForUserByName(accountName, user);
			result = getExpensesByAccount(month, year, account);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Collections.sort(result, (e1, e2) -> e1.getDate().getDayOfMonth() - e2.getDate().getDayOfMonth());

		return result;
	}

	private List<ExpenseViewModel> getExpensesByAccount(int month, int year, Account acc) throws Exception {
		List<ExpenseViewModel> result = new LinkedList<ExpenseViewModel>();
		List<Expense> accExpenses = (List<Expense>) foDao.getAllExpensesByAccount(acc);

		for (Expense expense : accExpenses) {
			if (expense.getDate().getMonthOfYear() == month && expense.getDate().getYear() == year) {
				ExpenseViewModel expenseViewModel = expenseToExpenseViewModel(expense);
				result.add(expenseViewModel);
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
}
