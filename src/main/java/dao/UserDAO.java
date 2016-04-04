package dao;

import java.util.Collection;
import java.util.List;

import model.User;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import exceptions.DuplicateUserException;

@Repository
public class UserDAO implements IUserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int addUser(User user) throws DAOException, DuplicateUserException {
		int id = 0;
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			id = (int) sessionFactory.getCurrentSession().save(user);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (ConstraintViolationException  e) {
			throw new  DuplicateUserException("Username or email is aleready taken!", e);			
		} catch (RuntimeException e) {			
			throw new DAOException("User can not be read from database!", e);
		} 
		

		return id;
	}

	@Override
	public void updateUser(User user) throws DAOException {
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			sessionFactory.getCurrentSession().update(user);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("User can not be read from database!", e);
		}
	}

	@Override
	public void deleteUser(User user) throws DAOException {
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			sessionFactory.getCurrentSession().delete(user);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("User can not be read from database!", e);
		}
	}

	@Override
	public User getUserById(int userId) throws DAOException {
		User user = null;
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			user = (User) sessionFactory.getCurrentSession().get(User.class, userId);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("User can not be read from database!", e);
		}

		return user;
	}

	@Override
	public User getUserByUsername(String username) throws DAOException {
		User user = null;
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.username = :username");
			query.setString("username", username);
			query.setMaxResults(1);
			@SuppressWarnings("unchecked")
			List<User> result = query.list();

			if (result != null && result.size() > 0) {
				user = result.get(0);
			}

			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("User can not be read from database!", e);
		}

		return user;
	}

	@Override
	public Collection<User> getAllUsers() throws DAOException {
		Collection<User> result = null;
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			Query sql = sessionFactory.getCurrentSession().createQuery("from User u");
			result = sql.list();
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("User can not be read from database!", e);
		}

		return result;
	}

}
