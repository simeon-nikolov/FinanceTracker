package dao;

import java.util.Collection;

import model.Account;
import model.User;

public interface IAccountDAO {

	int addAccount(Account account);
	
	Account getAccountById(int accountId);

	void deleteAccount(Account account);

	void updateAccount(Account account);

	Collection<Account> getAllAccountsForUser(User user);
	
	public Account getAccountForUserByName(String accountName, User user);

}