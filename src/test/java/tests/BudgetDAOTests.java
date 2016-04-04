package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import model.Budget;
import model.BudgetType;
import model.Currency;
import model.RepeatType;
import model.User;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import dao.IBudgetDAO;
import dao.IUserDAO;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=config.SpringWebConfig.class)
public class BudgetDAOTests {

	private static final int MONEY_AMOUNT = 10000;
	private static final int RANDOM_NUMBERS_SIZE = 10_000;
	private static final int NUMBER_OF_BUDGETS = 5;
	
	@Autowired
	private IBudgetDAO budgetDAO;
	@Autowired
	private IUserDAO userDAO;

	@Test
	public void testAddBudget() throws Exception {
		User user = addUserToDB();
		Budget budget = makeNewBudget(user);
		int id = budgetDAO.add(budget);
		Budget fromDB = budgetDAO.getBudgetById(id);
		assertEquals(budget.getId(), fromDB.getId());
		assertEquals(budget.getAmount(), fromDB.getAmount());
		assertEquals(budget.getBeginDate(), fromDB.getBeginDate());
		assertEquals(budget.getEndDate(), fromDB.getEndDate());
		assertEquals(budget.getBudgetType(), fromDB.getBudgetType());
		assertEquals(budget.getCurrency(), fromDB.getCurrency());
		assertEquals(budget.getRepeatType(), fromDB.getRepeatType());
		assertEquals(budget.getUser().getId(), fromDB.getUser().getId());
		budgetDAO.delete(fromDB);
		userDAO.deleteUser(user);
	}

	@Test
	public void testUpdateBudget() throws Exception {
		User user = addUserToDB();
		Budget budget = makeNewBudget(user);
		int id = budgetDAO.add(budget);
		budget.setId(id);
		budget.setAmount(12345);
		budget.setBeginDate(LocalDate.now().plusDays(123));
		budget.setEndDate(LocalDate.now().plusDays(321));
		budget.setCurrency(Currency.EUR);
		budget.setRepeatType(RepeatType.MONTHLY);
		budgetDAO.update(budget);
		Budget fromDB = budgetDAO.getBudgetById(id);
		assertEquals(budget.getAmount(), fromDB.getAmount());
		assertEquals(budget.getBeginDate(), fromDB.getBeginDate());
		assertEquals(budget.getEndDate(), fromDB.getEndDate());
		assertEquals(budget.getCurrency(), fromDB.getCurrency());
		assertEquals(budget.getRepeatType(), fromDB.getRepeatType());
		assertEquals(budget.getId(), fromDB.getId());
		budgetDAO.delete(budget);
		userDAO.deleteUser(user);
	}

	@Test
	public void testDeleteBudget() throws Exception {
		User user = addUserToDB();
		Budget budget = makeNewBudget(user);
		int id = budgetDAO.add(budget);
		budget.setId(id);
		budgetDAO.delete(budget);
		userDAO.deleteUser(user);
	}
	
	@Test
	public void testGetBudgetsByUser() throws Exception {
		User user = addUserToDB();
		List<Budget> budgets = new ArrayList<Budget>(NUMBER_OF_BUDGETS);
		
		for (int index = 0; index < NUMBER_OF_BUDGETS; index++) {
			Budget budget = makeNewBudget(user);
			int id = budgetDAO.add(budget);
			budget.setId(id);
			budgets.add(budget);
		}
		
		List<Budget> budgetsFromDB = (List<Budget>) budgetDAO.getAllBudgetsByUser(user);
		assertEquals(budgets.size(), budgetsFromDB.size());
		
		for (int index = 0; index < NUMBER_OF_BUDGETS; index++) {
			budgetDAO.delete(budgets.get(index));
		}
		
		userDAO.deleteUser(user);
	}
	
	@Test
	public void testGetBudgetById() throws Exception {
		User user = addUserToDB();
		Budget budget = makeNewBudget(user);
		int id = budgetDAO.add(budget);
		Budget fromDB = budgetDAO.getBudgetById(id);
		assertNotEquals(fromDB, null);
		assertEquals(fromDB.getId(), id);
		budgetDAO.delete(fromDB);
		userDAO.deleteUser(user);
	}

	private Budget makeNewBudget(User user) throws Exception {
		Budget budget = new Budget();
		budget.setAmount(MONEY_AMOUNT);
		budget.setBeginDate(LocalDate.now());
		budget.setEndDate(LocalDate.now().plusDays(5));
		budget.setBudgetType(BudgetType.ALL);
		budget.setCurrency(Currency.BGN);
		budget.setRepeatType(RepeatType.NO_REPEAT);
		budget.setUser(user);

		return budget;
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
