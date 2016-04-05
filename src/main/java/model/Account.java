package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import exceptions.InvalidArgumentException;

@Entity
@Table(name="accounts")
public class Account {
	
	
	private static final String USER_ERROR_MESSAGE = "Invalid argument - user can not be null!";
	private static final String TITLE_ERROR_MESSAGE = "Invalid argument - string is null or empty!";
	private static final String ID_ERROR_MESSAGE = "ID can't be a negative number!";
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String title;
	
	@Column
	private int balance;	
		
	@ManyToOne
	private User user;

	public Account() {}	
	

	public Account(int id, String title, int balance, User user) throws InvalidArgumentException {
		
		this.setId(id);
		this.setTitle(title);
		this.setBalance(balance);
		this.setUser(user);
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

	public User getUser() {
		return user;
	}


	public void setUser(User user) throws InvalidArgumentException {
		if (user == null) {
			throw new InvalidArgumentException(USER_ERROR_MESSAGE);
		}
		this.user = user;
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
