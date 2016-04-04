package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.Account;
import model.Category;
import model.Currency;
import model.Expense;
import model.FinanceOperationType;
import model.Income;
import model.RepeatType;
import model.Tag;
import model.User;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import dao.IAccountDAO;
import dao.ICategoryDAO;
import dao.IFinanceOperationDAO;
import dao.ITagDAO;
import dao.IUserDAO;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=config.SpringWebConfig.class)
public class FinanceOperationDAOTests {
	private static final int TAGS_COUNT = 3;
	private static final int LIST_SIZE = 5;
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

	@Autowired
	private IAccountDAO accDAO;
	@Autowired
	private IUserDAO userDAO;
	@Autowired
	private ITagDAO tagDAO;
	@Autowired
	private IFinanceOperationDAO foDAO;
	@Autowired
	private ICategoryDAO categoryDAO;

	@Test
	public void testAddIncome() throws Exception {
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
		assertEquals(income.getTags().size(), fromDB.getTags().size());
		
		for (Tag tag : fromDB.getTags()) {
			tagDAO.deleteTag(tag);
		}
		
		Account acc = fromDB.getAccount();
		User user = acc.getUser();
		Category category = fromDB.getCategory();
		foDAO.delete(fromDB);
		categoryDAO.deleteCategory(category);
		accDAO.deleteAccount(acc);
		userDAO.deleteUser(user);
	}

	@Test
	public void testAddExpense() throws Exception {
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
		assertEquals(expense.getTags().size(), fromDB.getTags().size());
		
		for (Tag tag : fromDB.getTags()) {
			tagDAO.deleteTag(tag);
		}
		
		Account acc = fromDB.getAccount();
		User user = acc.getUser();
		Category category = fromDB.getCategory();
		foDAO.delete(fromDB);
		categoryDAO.deleteCategory(category);
		accDAO.deleteAccount(acc);
		userDAO.deleteUser(user);
	}

	@Test
	public void testUpdateIncome() throws Exception {
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
		
		for (Tag tag : fromDB.getTags()) {
			tagDAO.deleteTag(tag);
		}
		
		Account acc = fromDB.getAccount();
		User user = acc.getUser();
		Category category = fromDB.getCategory();
		foDAO.delete(fromDB);
		categoryDAO.deleteCategory(category);
		accDAO.deleteAccount(acc);
		userDAO.deleteUser(user);
	}

	@Test
	public void testUpdateExpense() throws Exception {
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
		
		for (Tag tag : fromDB.getTags()) {
			tagDAO.deleteTag(tag);
		}
		
		Account acc = fromDB.getAccount();
		User user = acc.getUser();
		Category category = fromDB.getCategory();
		foDAO.delete(fromDB);
		categoryDAO.deleteCategory(category);
		accDAO.deleteAccount(acc);
		userDAO.deleteUser(user);
	}

	@Test
	public void testDeleteIncome() throws Exception {
		Income income = makeNewIncome();
		int id = foDAO.add(income);
		Income fromDB = foDAO.getIncomeById(id);
		Account acc = fromDB.getAccount();
		User user = acc.getUser();
		
		for (Tag tag : fromDB.getTags()) {
			tagDAO.deleteTag(tag);
		}
		
		Category category = fromDB.getCategory();
		foDAO.delete(fromDB);
		categoryDAO.deleteCategory(category);
		accDAO.deleteAccount(acc);
		userDAO.deleteUser(user);
	}

	@Test
	public void testDeleteExpense() throws Exception {
		Expense expense = makeNewExpense();
		int id = foDAO.add(expense);
		Expense fromDB = foDAO.getExpenseById(id);
		Account acc = fromDB.getAccount();
		User user = acc.getUser();
		
		for (Tag tag : fromDB.getTags()) {
			tagDAO.deleteTag(tag);
		}
		
		Category category = fromDB.getCategory();
		foDAO.delete(fromDB);
		System.out.println(category.getId() + " " + category.getCategoryName());
		categoryDAO.deleteCategory(category);
		accDAO.deleteAccount(acc);
		userDAO.deleteUser(user);
	}

	@Test
	public void testGetAllIncomesByAccount() throws Exception {
		List<Income> incomes = addManyIncomesToDB();
		Account account = incomes.get(0).getAccount();
		List<Income> incomesFromDB = (List<Income>) foDAO.getAllIncomesByAccount(account);
		assertEquals(incomes.size(), incomesFromDB.size());
		
		for (Income fromDB : incomesFromDB) {
			for (Tag tag : fromDB.getTags()) {
				tagDAO.deleteTag(tag);
			}
			
			Category category = fromDB.getCategory();
			foDAO.delete(fromDB);
			categoryDAO.deleteCategory(category);
		}
		
		User user = account.getUser();
		accDAO.deleteAccount(account);
		userDAO.deleteUser(user);
	}
	
	@Test
	public void testGetAllExpensesByAccount() throws Exception {
		List<Expense> expenses = addManyExpensesToDB();
		Account account = expenses.get(0).getAccount();
		List<Expense> expensesFromDB = (List<Expense>) foDAO.getAllExpensesByAccount(account);
		assertEquals(expenses.size(), expensesFromDB.size());
		
		for (Expense fromDB : expensesFromDB) {	
			for (Tag tag : fromDB.getTags()) {
				tagDAO.deleteTag(tag);
			}
			
			Category category = fromDB.getCategory();
			foDAO.delete(fromDB);
			categoryDAO.deleteCategory(category);
		}
		
		User user = account.getUser();
		accDAO.deleteAccount(account);
		userDAO.deleteUser(user);
	}	
	
	private List<Income> addManyIncomesToDB() throws Exception {
		List<Income> incomes = new ArrayList<Income>(LIST_SIZE);
		Account account = addAccountToDB();
		
		for (int index = 0; index < LIST_SIZE; index++) {
			Income income = makeNewIncome(account);
			int id = foDAO.add(income);
			income.setId(id);
			incomes.add(income);
		}

		return incomes;
	}
	
	private List<Expense> addManyExpensesToDB() throws Exception {
		List<Expense> expenses = new ArrayList<Expense>(LIST_SIZE);
		Account account = addAccountToDB();

		for (int index = 0; index < LIST_SIZE; index++) {
			Expense expense = makeNewExpense(account);
			int id = foDAO.add(expense);
			expense.setId(id);
			expenses.add(expense);
		}

		return expenses;
	}

	private Income makeNewIncome() throws Exception {
		Account account = addAccountToDB();
		return makeNewIncome(account);
	}
	
	private Income makeNewIncome(Account account) throws Exception {
		Income income = new Income();
		income.setAccount(account);
		income.setAmount(INCOME_AMOUNT);
		Category category = addIncomeCategory();
		income.setCategory(category);
		income.setCurrency(Currency.BGN);
		income.setDate(LocalDate.now());
		income.setDescription(INCOME_DESCRIPTION_TEXT);
		income.setRepeatType(RepeatType.MONTHLY);
		List<Tag> tags = addTagsToDB(FinanceOperationType.INCOME);
		income.setTags(tags);

		return income;
	}

	private Expense makeNewExpense() throws Exception {
		Account account = addAccountToDB();
		return makeNewExpense(account);
	}
	
	private Expense makeNewExpense(Account account) throws Exception {
		Expense expense = new Expense();
		expense.setAccount(account);
		expense.setAmount(15_000);
		Category category = addExpenseCategory();
		expense.setCategory(category);
		expense.setCurrency(Currency.BGN);
		expense.setDate(LocalDate.now());
		expense.setDescription(EXPENSE_DESCRIPTION_TEXT);
		expense.setRepeatType(RepeatType.MONTHLY);
		List<Tag> tags = addTagsToDB(FinanceOperationType.EXPENSE);
		expense.setTags(tags);

		return expense;
	}

	private Category addExpenseCategory() throws Exception {
		int randNumber = (int) (Math.random() * RANDOM_NUMBERS_SIZE);
		Category category = new Category(0, EXPENSE_CATEGORY + randNumber, FinanceOperationType.EXPENSE);
		categoryDAO.addCategory(category);
		
		return category;
	}
	
	private Category addIncomeCategory() throws Exception {
		int randNumber = (int) (Math.random() * RANDOM_NUMBERS_SIZE);
		Category category = new Category(0, INCOME_CATEGORY + randNumber, FinanceOperationType.INCOME);
		categoryDAO.addCategory(category);
		
		return category;
	}

	private List<Tag> addTagsToDB(FinanceOperationType forType) throws Exception {
		List<Tag> tags = new LinkedList<Tag>();
		
		for (int index = 0; index < TAGS_COUNT; index++) {
			Tag tag = new Tag(0, "tag" + index, forType);
			int id = tagDAO.addTag(tag);
			tag.setId(id);
			tags.add(tag);
		}
		
		return tags;
	}

	private Account addAccountToDB() throws Exception {
		User user = addUserToDB();
		Account account = new Account();
		account.setBalance(ACCOUNT_BALANCE);
		account.setTitle(ACCOUNT_TITLE);
		account.setUser(user);
		accDAO.addAccount(account);

		return account;
	}

	private User addUserToDB() throws Exception {
		User user = new User();
		int randNumber = (int) (Math.random() * RANDOM_NUMBERS_SIZE);
		user.setUsername("testusername" + randNumber);
		user.setEmail("testemail" + randNumber + "@domain.asd");
		user.setPassword("123456");
		user.setCurrency(Currency.BGN);
		int id = userDAO.addUser(user);
		user.setId(id);

		return user;
	}
}
