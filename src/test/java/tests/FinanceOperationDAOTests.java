package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import model.Account;
import model.Category;
import model.Currency;
import model.Expense;
import model.FinanceOperationType;
import model.Income;
import model.RepeatType;
import model.User;

import org.joda.time.LocalDate;
import org.junit.Test;

import dao.AccountDAO;
import dao.FinanceOperationDAO;
import dao.IAccountDAO;
import dao.IFinanceOperationDAO;
import dao.IUserDAO;
import dao.UserDAO;
import exceptions.InvalidArgumentException;

public class FinanceOperationDAOTests {
	private static final int LIST_SIZE = 15;
	private static final String NEW_DESCRIPTION_TEXT = "New description....";
	private static final int NEW_AMOUNT = 123_000;
	private static final String EXPENSE_DESCRIPTION_TEXT = "Electricity tax.";
	private static final String EXPENSE_CATEGORY = "Bills";
	private static final String INCOME_DESCRIPTION_TEXT = "15% of salary.";
	private static final String INCOME_CATEGORY = "Salary";
	private static final String ACCOUNT_TITLE = "Savings";
	private static final int INCOME_AMOUNT = 10_000;
	private static final int ACCOUNT_BALANCE = 100_000;
	private static final int RANDOM_NUMBERS_SIZE = 10_000;

	private IAccountDAO accDAO = new AccountDAO();
	private IUserDAO userDAO = new UserDAO();
	private IFinanceOperationDAO foDAO = new FinanceOperationDAO();

	@Test
	public void testAddIncome() {
		Income income = makeNewIncome();
		int id = foDAO.add(income);
		Income fromDB = foDAO.getIncomeById(id);
		assertEquals(id, fromDB.getId());
		assertEquals(income.getAccount().getId(), fromDB.getAccount().getId());
		assertEquals(income.getFinanceOperationType(), fromDB.getFinanceOperationType());
		assertEquals(income.getDate(), fromDB.getDate());
		assertEquals(income.getAmount(), fromDB.getAmount());
		assertEquals(income.getCategory().getId(), fromDB.getCategory().getId());
		assertEquals(income.getCategory().getCategoryName(), fromDB.getCategory().getCategoryName());
		assertEquals(income.getCurrency(), fromDB.getCurrency());
		assertEquals(income.getDescription(), fromDB.getDescription());
		assertEquals(income.getRepeatType(), fromDB.getRepeatType());
		Account acc = fromDB.getAccount();
		User user = acc.getUser();
		foDAO.delete(fromDB);
		accDAO.deleteAccount(acc);
		userDAO.deleteUser(user);
	}

	@Test
	public void testAddExpense() {
		Expense expense = makeNewExpense();
		int id = foDAO.add(expense);
		Expense fromDB = foDAO.getExpenseById(id);
		assertEquals(id, fromDB.getId());
		assertEquals(expense.getAccount().getId(), fromDB.getAccount().getId());
		assertEquals(expense.getFinanceOperationType(), fromDB.getFinanceOperationType());
		assertEquals(expense.getDate(), fromDB.getDate());
		assertEquals(expense.getAmount(), fromDB.getAmount());
		assertEquals(expense.getCategory().getId(), fromDB.getCategory().getId());
		assertEquals(expense.getCategory().getCategoryName(), fromDB.getCategory().getCategoryName());
		assertEquals(expense.getCurrency(), fromDB.getCurrency());
		assertEquals(expense.getDescription(), fromDB.getDescription());
		assertEquals(expense.getRepeatType(), fromDB.getRepeatType());
		Account acc = fromDB.getAccount();
		User user = acc.getUser();
		foDAO.delete(fromDB);
		accDAO.deleteAccount(acc);
		userDAO.deleteUser(user);
	}

	@Test
	public void testUpdateIncome() {
		try {
			Income income = makeNewIncome();
			int id = foDAO.add(income);
			income.setId(id);
			income.setAmount(NEW_AMOUNT);
			income.setCurrency(Currency.USD);
			income.setDate(LocalDate.now().plusDays(1));
			income.setDescription(NEW_DESCRIPTION_TEXT);
			income.setRepeatType(RepeatType.DAILY);
			foDAO.update(income);
			Income fromDB = foDAO.getIncomeById(id);
			assertEquals(id, fromDB.getId());
			assertEquals(income.getAccount().getId(), fromDB.getAccount().getId());
			assertEquals(income.getFinanceOperationType(), fromDB.getFinanceOperationType());
			assertEquals(income.getDate(), fromDB.getDate());
			assertEquals(income.getAmount(), fromDB.getAmount());
			assertEquals(income.getCategory().getId(), fromDB.getCategory().getId());
			assertEquals(income.getCategory().getCategoryName(), fromDB.getCategory().getCategoryName());
			assertEquals(income.getCurrency(), fromDB.getCurrency());
			assertEquals(income.getDescription(), fromDB.getDescription());
			assertEquals(income.getRepeatType(), fromDB.getRepeatType());
			Account acc = fromDB.getAccount();
			User user = acc.getUser();
			foDAO.delete(fromDB);
			accDAO.deleteAccount(acc);
			userDAO.deleteUser(user);
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateExpense() {
		try {
			Expense expense = makeNewExpense();
			int id = foDAO.add(expense);
			expense.setId(id);
			expense.setAmount(NEW_AMOUNT);
			expense.setCurrency(Currency.USD);
			expense.setDate(LocalDate.now().plusDays(1));
			expense.setDescription(NEW_DESCRIPTION_TEXT);
			expense.setRepeatType(RepeatType.DAILY);
			foDAO.update(expense);
			Expense fromDB = foDAO.getExpenseById(id);
			assertEquals(id, fromDB.getId());
			assertEquals(expense.getAccount().getId(), fromDB.getAccount().getId());
			assertEquals(expense.getFinanceOperationType(), fromDB.getFinanceOperationType());
			assertEquals(expense.getDate(), fromDB.getDate());
			assertEquals(expense.getAmount(), fromDB.getAmount());
			assertEquals(expense.getCategory().getId(), fromDB.getCategory().getId());
			assertEquals(expense.getCategory().getCategoryName(), fromDB.getCategory().getCategoryName());
			assertEquals(expense.getCurrency(), fromDB.getCurrency());
			assertEquals(expense.getDescription(), fromDB.getDescription());
			assertEquals(expense.getRepeatType(), fromDB.getRepeatType());
			Account acc = fromDB.getAccount();
			User user = acc.getUser();
			foDAO.delete(fromDB);
			accDAO.deleteAccount(acc);
			userDAO.deleteUser(user);
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteIncome() {
		Income income = makeNewIncome();
		int id = foDAO.add(income);
		Income fromDB = foDAO.getIncomeById(id);
		Account acc = fromDB.getAccount();
		User user = acc.getUser();
		foDAO.delete(fromDB);
		accDAO.deleteAccount(acc);
		userDAO.deleteUser(user);
	}

	@Test
	public void testDeleteExpense() {
		Expense expense = makeNewExpense();
		int id = foDAO.add(expense);
		Expense fromDB = foDAO.getExpenseById(id);
		Account acc = fromDB.getAccount();
		User user = acc.getUser();
		foDAO.delete(fromDB);
		accDAO.deleteAccount(acc);
		userDAO.deleteUser(user);
	}

	@Test
	public void testGetAllIncomesByAccount() {
		List<Income> incomes = addManyIncomesToDB();
		Account account = incomes.get(0).getAccount();
		List<Income> incomesFromDB = (List<Income>) foDAO.getAllIncomesByAccount(account);
		assertEquals(incomes.size(), incomesFromDB.size());
		
		for (Income fromDB : incomesFromDB) {
			foDAO.delete(fromDB);
		}
		
		User user = account.getUser();
		accDAO.deleteAccount(account);
		userDAO.deleteUser(user);
	}
	
	@Test
	public void testGetAllExpensesByAccount() {
		List<Expense> expenses = addManyExpensesToDB();
		Account account = expenses.get(0).getAccount();
		List<Expense> expensesFromDB = (List<Expense>) foDAO.getAllExpensesByAccount(account);
		assertEquals(expenses.size(), expensesFromDB.size());
		
		for (Expense fromDB : expensesFromDB) {
			foDAO.delete(fromDB);
		}
		
		User user = account.getUser();
		accDAO.deleteAccount(account);
		userDAO.deleteUser(user);
	}
	
	private List<Income> addManyIncomesToDB() {
		List<Income> incomes = new ArrayList<Income>(LIST_SIZE);
		Account account = addAccountToDB();
		
		try {
			for (int index = 0; index < LIST_SIZE; index++) {
				Income income = makeNewIncome(account);
				int id = foDAO.add(income);
				income.setId(id);
				incomes.add(income);
			}
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}

		return incomes;
	}
	
	private List<Expense> addManyExpensesToDB() {
		List<Expense> expenses = new ArrayList<Expense>(LIST_SIZE);
		Account account = addAccountToDB();
		
		try {
			for (int index = 0; index < LIST_SIZE; index++) {
				Expense expense = makeNewExpense(account);
				int id = foDAO.add(expense);
				expense.setId(id);
				expenses.add(expense);
			}
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}

		return expenses;
	}

	private Income makeNewIncome() {
		Account account = addAccountToDB();
		return makeNewIncome(account);
	}
	
	private Income makeNewIncome(Account account) {
		Income income = new Income();

		try {
			income.setAccount(account);
			income.setAmount(INCOME_AMOUNT);
			Category category = new Category(1, INCOME_CATEGORY, FinanceOperationType.INCOME);
			income.setCategory(category);
			income.setCurrency(Currency.BGN);
			income.setDate(LocalDate.now());
			income.setDescription(INCOME_DESCRIPTION_TEXT);
			income.setRepeatType(RepeatType.MONTHLY);
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}

		return income;
	}

	private Expense makeNewExpense() {
		Account account = addAccountToDB();
		return makeNewExpense(account);
	}
	
	private Expense makeNewExpense(Account account) {
		Expense expense = new Expense();

		try {
			expense.setAccount(account);
			expense.setAmount(15_000);
			Category category = new Category(2, EXPENSE_CATEGORY, FinanceOperationType.EXPENSE);
			expense.setCategory(category);
			expense.setCurrency(Currency.BGN);
			expense.setDate(LocalDate.now());
			expense.setDescription(EXPENSE_DESCRIPTION_TEXT);
			expense.setRepeatType(RepeatType.MONTHLY);
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}

		return expense;
	}

	private Account addAccountToDB() {
		User user = addUserToDB();
		Account account = new Account();

		try {
			account.setBalance(ACCOUNT_BALANCE);
			account.setTitle(ACCOUNT_TITLE);
			account.setUser(user);
			accDAO.addAccount(account);
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}

		return account;
	}

	private User addUserToDB() {
		User user = new User();

		try {
			int randNumber = (int) (Math.random() * RANDOM_NUMBERS_SIZE);
			user.setUsername("testusername" + randNumber);
			user.setEmail("testemail" + randNumber + "@domain.asd");
			user.setPassword("123456");
			user.setCurrency(Currency.BGN);
			int id = userDAO.addUser(user);
			user.setId(id);
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}

		return user;
	}
}
