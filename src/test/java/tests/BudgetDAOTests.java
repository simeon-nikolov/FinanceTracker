package tests;

import java.time.LocalDate;

import model.Budget;
import model.BudgetType;
import model.Currency;
import model.RepeatType;

import org.junit.Test;

import exceptions.InvalidArgumentException;


public class BudgetDAOTests {

	@Test
	public void testAddBudget() {
		try {
			Budget budget = new Budget();
			budget.setAmount(10000);
			budget.setBeginDate(LocalDate.now());
			budget.setEndDate(LocalDate.now().plusDays(5));
			budget.setBudgetType(BudgetType.ALL);
			budget.setCurrency(Currency.BGN);
			budget.setRepeatType(RepeatType.NO_REPEAT);
			
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
		
	}

}
