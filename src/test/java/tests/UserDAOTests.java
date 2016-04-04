package tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import model.Currency;
import model.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import dao.IUserDAO;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=config.SpringWebConfig.class)
public class UserDAOTests {

	private static final int RANDOM_NUMBER_FOR_TESTS = 10_000;

	@Autowired
	private IUserDAO dao;

	@Test
	public void testGetUserById() throws Exception {
		User user = makeNewUser();
		int id = dao.addUser(user);
		user.setId(id);
		User userFromDB = dao.getUserById(id);

		assertEquals(userFromDB.getId(), user.getId());
		assertEquals(userFromDB.getUsername(), user.getUsername());
		assertEquals(userFromDB.getEmail(), user.getEmail());

		dao.deleteUser(user);

	}

	@Test
	public void testAddUser() throws Exception {
		User user = makeNewUser();
		int id = dao.addUser(user);
		user.setId(id);
		User userFromDB = dao.getUserById(id);

		assertEquals(userFromDB.getId(), user.getId());
		assertEquals(userFromDB.getUsername(), user.getUsername());
		assertEquals(userFromDB.getEmail(), user.getEmail());

		dao.deleteUser(user);
	}

	@Test
	public void testUpdateUser() throws Exception {
		User user = makeNewUser();
		int id = dao.addUser(user);
		user.setId(id);
		String newEmail = "test" + RANDOM_NUMBER_FOR_TESTS + "@test.com";
		user.setEmail(newEmail);
		dao.updateUser(user);
		User tempUser = dao.getUserById(id);

		assertEquals(newEmail, tempUser.getEmail());

		dao.deleteUser(user);

	}

	@Test
	public void testGetAllUsers() throws Exception {
		List<User> list = (List<User>) dao.getAllUsers();
	}

	@Test
	public void testGetUserByUsername() throws Exception {
		User user = makeNewUser();
		String username = user.getUsername();		
		int id = dao.addUser(user);		
		User fromDB = dao.getUserByUsername(username);
		
		assertEquals(fromDB.getId(), id);
		assertEquals(fromDB.getUsername(), user.getUsername());
		assertEquals(fromDB.getEmail(), user.getEmail());
		assertEquals(fromDB.getCurrency(), user.getCurrency());
		
		dao.deleteUser(fromDB);
	}

	private User makeNewUser() throws Exception {
		User user = new User();
		int randNumber = (int) (Math.random() * RANDOM_NUMBER_FOR_TESTS);
		String username = "testusername" + randNumber;
		String email = "testemail" + randNumber + "@asd.asd";
		user.setUsername(username);
		user.setPassword("123456");
		user.setEmail(email);
		user.setCurrency(Currency.BGN);

		return user;
	}
}
