package dao;

import java.util.Collection;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import exceptions.DAOException;
import exceptions.DuplicateUserException;
import model.User;

public interface IUserDAO {
	int addUser(User user) throws DAOException, DuplicateUserException;
	
	void updateUser(User user) throws DAOException;
	
	void deleteUser(User user) throws DAOException;
	
	User getUserById(int userId) throws DAOException;

	User getUserByUsername(String username) throws DAOException;
	
	Collection<User> getAllUsers() throws DAOException;
}
