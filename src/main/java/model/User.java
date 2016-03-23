package model;

import exceptions.InvalidArgumentException;

public class User implements IUser {

	private static final String USERNAME_ERROR_MESSAGE = "Username can not be null or empty string!";
	private static final String ID_ERROR_MESSAGE = "ID can not be 0 or negative number";
	private static final String PASSWORD_ERROR_MESSAGE = "Password can not be null or empty string!";
	private static final String EMAIL_ERROR_MESSAGE = "Email can not be null or empty string!";
	
	
	
	private int id;
	private String username;
	private String password;
	private String email;
	private Currency currency;
	
	
	
	public User(int id, String username, String password, String email, Currency currency)
									throws InvalidArgumentException {
		this.setId(id);
		this.setUsername(username);
		this.setPassword(password);
		this.setEmail(email);
		this.setCurrency(currency);
	}

	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public String getEmail() {
		return email;
	}
	
	@Override
	public Currency getCurrency() {
		return currency;		
	}
	
	@Override
	public void setId(int id) throws InvalidArgumentException {
		if (id < 1) {
			throw new InvalidArgumentException(ID_ERROR_MESSAGE);
		}
		this.id = id;
	}
	
	@Override
	public void setUsername(String username) throws InvalidArgumentException {
		if (username == null || username.isEmpty()) {
			throw new InvalidArgumentException(USERNAME_ERROR_MESSAGE);
		}
		this.username = username;
	}
	
	@Override
	public void setPassword(String password) throws InvalidArgumentException {
		if (password == null || password.isEmpty()) {
			throw new InvalidArgumentException(PASSWORD_ERROR_MESSAGE);
		}
		this.password = password;
	}
	
	@Override
	public void setEmail(String email) throws InvalidArgumentException {
		if (email == null || email.isEmpty()) {
			throw new InvalidArgumentException(EMAIL_ERROR_MESSAGE);
		}
		this.email = email;
	}
	
	@Override
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	
}
