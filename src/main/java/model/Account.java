package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import exceptions.InvalidArgumentException;

@Entity
@Table(name="accounts")
public class Account {
	
	
	private static final String TITLE_ERROR_MESSAGE = "Invalid argument - string is null or empty!";
	private static final String ID_ERROR_MESSAGE = "ID can't be a negative number!";
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column
	private String title;
	
	@Column
	private int balance;	

	public Account() {}	
	

	public Account(int id, String title, int balance) throws InvalidArgumentException {
		
		this.setId(id);
		this.setTitle(title);
		this.setBalance(balance);
	}
	
	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public int getBalance() {
		return balance;
	}

	public void setId(int id) throws InvalidArgumentException {
		if (id < 0) {
			throw new InvalidArgumentException(ID_ERROR_MESSAGE);
		}
		this.id = id;
	}

	public void setTitle(String title) throws InvalidArgumentException {
		if (title == null || title.isEmpty()) {
			throw new InvalidArgumentException(TITLE_ERROR_MESSAGE);
		}
		this.title = title;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	
	
	

}
