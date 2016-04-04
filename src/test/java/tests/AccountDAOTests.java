package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import model.Account;
import model.Currency;
import model.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import dao.IAccountDAO;
import dao.IUserDAO;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=config.SpringWebConfig.class)
public class AccountDAOTests {
	
	private static final int NUMBER_OF_TEST_ACCOUNTS = 10;
	private static final int ACCOUNT_BALANCE_RANDOM_NUMBER = 2000;
	private static final int RANDOM_NUMBER_SIZE = 10_000;
	
	@Autowired
	private IAccountDAO dao;
	@Autowired
	private IUserDAO userDao;
	
	@Test
	public void testAddAccount() throws Exception {
		User user = addUserToDB();			
		Account account = makeNewAccount(user);		
		int id = dao.addAccount(account);	
		account.setId(id);
		Account accTemp = dao.getAccountById(id);
		assertEquals(account.getTitle(), accTemp.getTitle());
		assertEquals(account.getBalance(), accTemp.getBalance());
		dao.deleteAccount(account);
		userDao.deleteUser(user);
	}
	
	@Test
	public void testUpdateAccount() throws Exception {
		User user = addUserToDB();			
		Account account = makeNewAccount(user);		
		int id = dao.addAccount(account);				
		int newBalance = (int)(Math.random()*ACCOUNT_BALANCE_RANDOM_NUMBER);
		String newTitle = "testUpdate";
		account.setBalance(newBalance);
		account.setTitle(newTitle);
		dao.updateAccount(account);
		account = dao.getAccountById(id);
		assertEquals(account.getBalance(), newBalance);
		assertEquals(account.getTitle(), newTitle);
		dao.deleteAccount(account);
		userDao.deleteUser(user);
	}
	
	@Test
	public void testGetAccountById() throws Exception {
		User user = addUserToDB();			
		Account account = makeNewAccount(user);	
		String title = account.getTitle();
		int balance = account.getBalance();
		int id = dao.addAccount(account);	
		Account fromDB = dao.getAccountById(id);
		assertEquals(fromDB.getTitle(), title);	
		assertEquals(fromDB.getBalance(), balance);
		dao.deleteAccount(account);
		userDao.deleteUser(user);
	}
	
	@Test
	public void testGetAllAccountsForUser() throws Exception {
		User user = addUserToDB();
		List<Account> accounts = new ArrayList<Account>();
		
		for (int i = 0 ; i < NUMBER_OF_TEST_ACCOUNTS; i++)  {
			Account account = makeNewAccount(user);
			int id = dao.addAccount(account);
			account.setId(id);
			accounts.add(account);
		}
		
		List<Account> listFromDB = (List<Account>) dao.getAllAccountsForUser(user);
		assertEquals(listFromDB.size(), accounts.size());		
		
		for (int i = 0 ; i < NUMBER_OF_TEST_ACCOUNTS; i++) {
			dao.deleteAccount(accounts.get(i));
		}
		
		userDao.deleteUser(user);		
	}
	
	@Test
	public void testGetAccountForUserByName() throws Exception {
		User user = addUserToDB();
		Account account = makeNewAccount(user);
		String title = account.getTitle();
		int id = dao.addAccount(account);
		Account fromDB = dao.getAccountForUserByName(title, user);
		assertEquals(fromDB.getBalance(), account.getBalance());
		assertEquals(fromDB.getId(), id);
		dao.deleteAccount(account);
		userDao.deleteUser(user);
	}
	
	private User addUserToDB() throws Exception {
		User user = new User();
		int randNumber = (int) (Math.random() * RANDOM_NUMBER_SIZE);
		user.setUsername("testusername" + randNumber);
		user.setEmail("testemail" + randNumber + "@domain.asd");
		user.setPassword("123456");
		user.setCurrency(Currency.BGN);
		int id = userDao.addUser(user);
		user.setId(id);

		return user;
	}
	
	private Account makeNewAccount(User user) throws Exception {
		Account account = new Account();
		int balance = (int)(Math.random() * ACCOUNT_BALANCE_RANDOM_NUMBER);
		account.setBalance(balance);
		account.setTitle("testAccount");
		account.setUser(user);

		return account;
	}
}
