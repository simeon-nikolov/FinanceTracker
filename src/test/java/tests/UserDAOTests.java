package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import dao.IUserDAO;
import dao.UserDAO;
import exceptions.InvalidArgumentException;
import model.Currency;
import model.User;

public class UserDAOTests {

	private IUserDAO dao = new UserDAO();
	
	@Test
	public void testAddUser() {
		User user = new User();
		try {
			user.setUsername("user3");
			user.setPassword("123456");
			user.setEmail("abv1@abv1.com");
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
		int id = 3;
		User user = dao.getUserById(id);
		String newEmail = "test@test.com";
		try {
			user.setEmail(newEmail);
			dao.updateUser(user);
		} catch (InvalidArgumentException e) {			
			e.printStackTrace();
		}
		
		
		User tempUser = dao.getUserById(id);
		assertEquals(newEmail, tempUser.getEmail());
		
		
	}
	
	@Test
	public void testGetUserById() {
		int id = 3;
		User user = dao.getUserById(id);
		
		assertEquals(id, user.getId());
		assertEquals("user", user.getUsername());		
	}
	
	@Test
	public void testGetAllUsers() {
		List<User> list= (List<User>) dao.getAllUsers();
		
		System.out.println(list.size());
		System.out.println(list);
	}

}
