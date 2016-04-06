package dao;

import java.util.Collection;

import model.Account;
import model.User;

public interface IAccountDAO {

	int addAccount(Account account) throws DAOException;
	
	Account getAccountById(int accountId) throws DAOException;

	void deleteAccount(Account account) throws DAOException;

	void updateAccount(Account account) throws DAOException;

	Collection<Account> getAllAccountsForUser(User user) throws DAOException;
	
	public Account getAccountForUserByName(String accountName, User user) throws DAOException;
	
	boolean checkUserHasAccount(Account account, User user) throws DAOException;

}