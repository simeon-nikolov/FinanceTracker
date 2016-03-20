package financetracker;

import java.time.LocalDate;

import exceptions.InvalidArgumentException;

public class Expenses extends FinanceOperation {

	public Expenses() {}

	public Expenses(int id, int amount, Currency currency, String category, 
			IAccount account, LocalDate date, String description, RepeatType repeatType) 
					throws InvalidArgumentException {
		super(id, amount, currency, category, account, date, description, repeatType);
	}

}
