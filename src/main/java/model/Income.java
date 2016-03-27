package model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.joda.time.LocalDate;

import exceptions.InvalidArgumentException;

@Entity
@DiscriminatorValue(value="INCOME")
public class Income extends FinanceOperation {

	public Income() {
		super.setFinanceOperationType(FinanceOperationType.INCOME);
	}
	
	public Income(int id, int amount, Currency currency, Category category, 
			Account account, LocalDate date, String description, RepeatType repeatType) 
					throws InvalidArgumentException {
		super(id, amount, currency, category, account, date, description, repeatType, FinanceOperationType.INCOME);
	}

	@Override
	public void setFinanceOperationType(FinanceOperationType financeOperationType) {
		super.setFinanceOperationType(FinanceOperationType.INCOME);
	}
}
