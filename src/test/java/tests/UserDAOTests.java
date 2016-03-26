package tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import model.Currency;
import model.User;

import org.junit.Test;

import dao.IUserDAO;
import dao.UserDAO;
import exceptions.InvalidArgumentException;

public class UserDAOTests {

	private IUserDAO dao = new UserDAO();
	
	@Test
	public void testGetUserById() {
		try {
			int randNumber = (int) (Math.random() * 10_000);
			User newUser = new User();
			newUser.setUsername("testusername" + randNumber);
			newUser.setPassword("124356");
			newUser.setEmail("testemail" + randNumber + "@asd.asd");
			newUser.setCurrency(Currency.BGN);
			int id = dao.addUser(newUser);
			User user = dao.getUserById(id);
			
			assertEquals(id, user.getId());
			assertEquals(newUser.getUsername(), user.getUsername());	
			
			dao.deleteUser(user);
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddUser() {
		try {
			int randNumber = (int) (Math.random() * 10_000);
			User user = new User();
			user.setUsername("user3" + randNumber);
			user.setPassword("123456");
			user.setEmail("abv1" + randNumber + "@abv1.com");
			user.setCurrency(Currency.BGN);
			
			int id = dao.addUser(user);
			User tempUser = dao.getUserById(id);
			
			assertEquals(tempUser.getUsername(), user.getUsername());	
			
			dao.deleteUser(tempUser);
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateUser() {
		try {
			int randNumber = (int) (Math.random() * 10_000);
			User user = new User();
			user.setUsername("testusername" + randNumber);
			user.setEmail("testemail" + randNumber + "@asd.asd");
			user.setPassword("123123");
			user.setCurrency(Currency.BGN);
			int id = dao.addUser(user);
			user.setId(id);
			String newEmail = "test@test.com";
			user.setEmail(newEmail);
			dao.updateUser(user);
			User tempUser = dao.getUserById(id);
			assertEquals(newEmail, tempUser.getEmail());
			dao.deleteUser(user);
		} catch (InvalidArgumentException e) {			
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAllUsers() {
		List<User> list= (List<User>) dao.getAllUsers();
		
		System.out.println(list.size());
		System.out.println(list);
	}
	
	@Test
	public void testGetUserByUsername() {
		try {
			int randNumber = (int) (Math.random() * 10_000);
			User user = new User();
			String username = "testusername" + randNumber;
			user.setUsername(username);
			user.setPassword("124356");
			user.setEmail("testemail" + randNumber + "@asd.asd");
			user.setCurrency(Currency.BGN);
			int id = dao.addUser(user);
			User fromDB = dao.getUserByUsername(username);
			assertEquals(fromDB.getId(), id);
			assertEquals(fromDB.getUsername(), user.getUsername());
			assertEquals(fromDB.getEmail(), user.getEmail());
			assertEquals(fromDB.getCurrency(), user.getCurrency());
			dao.deleteUser(fromDB);
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
	}

}
