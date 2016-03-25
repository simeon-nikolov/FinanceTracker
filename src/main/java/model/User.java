package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import exceptions.InvalidArgumentException;

@Entity
@Table(name="users")
public class User {

	private static final String USERNAME_ERROR_MESSAGE = "Username can not be null or empty string!";
	private static final String ID_ERROR_MESSAGE = "ID can not be 0 or negative number";
	private static final String PASSWORD_ERROR_MESSAGE = "Password can not be null or empty string!";
	private static final String EMAIL_ERROR_MESSAGE = "Email can not be null or empty string!";
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String username;
	
	@Column
	private String password;
	
	@Column
	private String email;
	
	@JoinTable(name = "currencies", joinColumns = @JoinColumn(name = "currency"))
	@Column(name = "currency", nullable = false, length=1)
	@Enumerated(EnumType.STRING)
	private Currency currency;

	public User() {}
	
	public User(int id, String username, String password, String email, Currency currency)
									throws InvalidArgumentException {
		this.setId(id);
		this.setUsername(username);
		this.setPassword(password);
		this.setEmail(email);
		this.setCurrency(currency);
	}

	public int getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public Currency getCurrency() {
		return currency;		
	}
	
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	public void setId(int id) throws InvalidArgumentException {
		if (id < 1) {
			throw new InvalidArgumentException(ID_ERROR_MESSAGE);
		}
		this.id = id;
	}
	
	public void setUsername(String username) throws InvalidArgumentException {
		if (username == null || username.isEmpty()) {
			throw new InvalidArgumentException(USERNAME_ERROR_MESSAGE);
		}
		this.username = username;
	}
	
	public void setPassword(String password) throws InvalidArgumentException {
		if (password == null || password.isEmpty()) {
			throw new InvalidArgumentException(PASSWORD_ERROR_MESSAGE);
		}
		this.password = password;
	}
	
	public void setEmail(String email) throws InvalidArgumentException {
		if (email == null || email.isEmpty()) {
			throw new InvalidArgumentException(EMAIL_ERROR_MESSAGE);
		}
		this.email = email;
	}
}
