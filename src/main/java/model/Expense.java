package model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.joda.time.LocalDate;

import exceptions.InvalidArgumentException;

@Entity
@DiscriminatorValue(value="EXPENSE")
public class Expense extends FinanceOperation {

	public Expense() {
		super.setFinanceOperationType(FinanceOperationType.EXPENSE);
	}

	public Expense(int id, int amount, Currency currency, Category category, 
			Account account, LocalDate date, String description, RepeatType repeatType) 
					throws InvalidArgumentException {
		super(id, amount, currency, category, account, date, description, repeatType, FinanceOperationType.EXPENSE);
	}
	
	@Override
	public void setFinanceOperationType(FinanceOperationType financeOperationType) {
		super.setFinanceOperationType(FinanceOperationType.EXPENSE);
	}
}
