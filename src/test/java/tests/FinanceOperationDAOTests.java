package tests;

import static org.junit.Assert.assertEquals;
import model.Account;
import model.Category;
import model.Currency;
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

	private Income makeNewIncome() {
		Account account = addAccountToDB();
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
			income.setFinanceOperationType(FinanceOperationType.INCOME);
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
		
		return income;
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
