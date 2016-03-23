package model;

import javax.persistence.Entity;
import javax.persistence.Table;

import exceptions.InvalidArgumentException;

@Entity
@Table(name="accounts")
public class Account implements IAccount {
	
	private static final String OBJECT_CAN_NOT_BE_NULL_ERROR_MESSAGE = "Object can not be null!";
	private static final String TITLE_ERROR_MESSAGE = "Invalid argument - string is null or empty!";
	private static final String ID_ERROR_MESSAGE = "ID can't be a negative number!";
	
	
	private int id;
	private String title;
	private int balance;	

	public Account() {}	
	

	public Account(int id, String title, int balance) throws InvalidArgumentException {
		
		this.setId(id);
		this.setTitle(title);
		this.setBalance(balance);
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public int getBalance() {
		return balance;
	}

	@Override
	public void setId(int id) throws InvalidArgumentException {
		if (id < 0) {
			throw new InvalidArgumentException(ID_ERROR_MESSAGE);
		}
		this.id = id;
	}

	@Override
	public void setTitle(String title) throws InvalidArgumentException {
		if (title == null || title.isEmpty()) {
			throw new InvalidArgumentException(TITLE_ERROR_MESSAGE);
		}
		this.title = title;
	}

	@Override
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	
	
	

}
