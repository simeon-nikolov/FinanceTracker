package model;

import java.time.LocalDate;

import exceptions.InvalidArgumentException;

public interface IFinanceOperation {

	int getId();

	void setId(int id) throws InvalidArgumentException;

	int getAmount();

	void setAmount(int amount) throws InvalidArgumentException;

	Currency getCurrency();

	void setCurrency(Currency currency);

	String getCategory();

	void setCategory(String category) throws InvalidArgumentException;

	IAccount getAccount();

	void setAccount(IAccount account) throws InvalidArgumentException;

	LocalDate getDate();

	void setDate(LocalDate date) throws InvalidArgumentException;

	String getDescription();

	void setDescription(String description) throws InvalidArgumentException;

	RepeatType getRepeatType();

	void setRepeatType(RepeatType repeatType);

}