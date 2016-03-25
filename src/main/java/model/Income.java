package model;

import java.time.LocalDate;

import exceptions.InvalidArgumentException;

public class Income extends FinanceOperation {

	public Income() {}
	
	public Income(int id, int amount, Currency currency, String category, 
			Account account, LocalDate date, String description, RepeatType repeatType) 
					throws InvalidArgumentException {
		super(id, amount, currency, category, account, date, description, repeatType, FinanceOperationType.INCOME);
	}

}
