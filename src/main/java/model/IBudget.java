package model;

import java.time.LocalDate;

import exceptions.InvalidArgumentException;

public interface IBudget {

	int getId();

	void setId(int id) throws InvalidArgumentException;

	BudgetType getBudgetType();

	void setBudgetType(BudgetType budgetType);

	RepeatType getRepeatType();

	void setRepeatType(RepeatType repeatType);

	LocalDate getBeginDate();

	void setBeginDate(LocalDate beginDate) throws InvalidArgumentException;

	LocalDate getEndDate();

	void setEndDate(LocalDate endDate) throws InvalidArgumentException;

	int getAmount();

	void setAmount(int amount) throws InvalidArgumentException;

	Currency getCurrency();

	void setCurrency(Currency currency);

}