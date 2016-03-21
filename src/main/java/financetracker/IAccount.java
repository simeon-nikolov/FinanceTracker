package financetracker;

import exceptions.InvalidArgumentException;

public interface IAccount {
	
	void addExpense(Expenses expense) throws InvalidArgumentException;
	
	void addIncome(Income income) throws InvalidArgumentException;
	
	int getId();
	
	String getTitle();
	
	int getBalance();
	
	void setId(int id) throws InvalidArgumentException;
	
	void setTitle(String title) throws InvalidArgumentException;
	
	public void setBalance(int balance);
	
	

}
