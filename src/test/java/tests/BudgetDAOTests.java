package tests;


import static org.junit.Assert.assertEquals;
import model.Budget;
import model.BudgetType;
import model.Currency;
import model.RepeatType;
import model.User;

import org.joda.time.LocalDate;
import org.junit.Test;

import dao.BudgetDAO;
import dao.IBudgetDAO;
import dao.IUserDAO;
import dao.UserDAO;
import exceptions.InvalidArgumentException;


public class BudgetDAOTests {
	
	private IBudgetDAO dao = new BudgetDAO();

	@Test
	public void testAddBudget() {
		try {
			User user = new User();
			int randNumber = (int) (Math.random() * 10_000);
			user.setUsername("testusername" + randNumber);
			user.setEmail("testemail" + randNumber + "@domain.asd");
			user.setPassword("123456");
			user.setCurrency(Currency.BGN);
			
			IUserDAO userDAO = new UserDAO();
			userDAO.addUser(user);
			
			Budget budget = new Budget();
			budget.setAmount(10000);
			budget.setBeginDate(LocalDate.now());
			budget.setEndDate(LocalDate.now().plusDays(5));
			budget.setBudgetType(BudgetType.ALL);
			budget.setCurrency(Currency.BGN);
			budget.setRepeatType(RepeatType.NO_REPEAT);		
			budget.setUser(user);
			
			int id = dao.add(budget);
			Budget fromDB = dao.getBudgetById(id);
			
			assertEquals(budget.getId(), fromDB.getId());
			assertEquals(budget.getAmount(), fromDB.getAmount());
			assertEquals(budget.getBeginDate(), fromDB.getBeginDate());
			assertEquals(budget.getEndDate(), fromDB.getEndDate());
			assertEquals(budget.getBudgetType(), fromDB.getBudgetType());
			assertEquals(budget.getCurrency(), fromDB.getCurrency());
			assertEquals(budget.getRepeatType(), fromDB.getRepeatType());
			assertEquals(budget.getUser().getId(), fromDB.getUser().getId());
			
			dao.delete(fromDB);
			userDAO.deleteUser(user);
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
	}

}
