package model;

import java.time.LocalDate;

import exceptions.InvalidArgumentException;

public class Expense extends FinanceOperation {

	public Expense() {}

	public Expense(int id, int amount, Currency currency, String category, 
			Account account, LocalDate date, String description, RepeatType repeatType) 
					throws InvalidArgumentException {
		super(id, amount, currency, category, account, date, description, repeatType, FinanceOperationType.EXPENSE);
	}

}
