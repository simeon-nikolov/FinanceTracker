package dao;

import java.util.Collection;

import model.User;

public interface IUserDAO {
	int addUser(User user);
	
	void updateUser(User user);
	
	void deleteUser(User user);
	
	User getUserById(int userId);

	User getUserByUsername(String username);
	
	Collection<User> getAllUsers();
}
