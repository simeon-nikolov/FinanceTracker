package model;

import exceptions.InvalidArgumentException;

public interface IUser {

	int getId();

	String getUsername();

	String getPassword();

	String getEmail();

	Currency getCurrency();

	void setId(int id) throws InvalidArgumentException;

	void setUsername(String username) throws InvalidArgumentException;

	void setPassword(String password) throws InvalidArgumentException;

	void setEmail(String email) throws InvalidArgumentException;

	void setCurrency(Currency currency);

}