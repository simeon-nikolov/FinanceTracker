package model;

import exceptions.InvalidArgumentException;

public interface IAccount {
		
	int getId();
	
	String getTitle();
	
	int getBalance();
	
	void setId(int id) throws InvalidArgumentException;
	
	void setTitle(String title) throws InvalidArgumentException;
	
	public void setBalance(int balance);
	
	

}
