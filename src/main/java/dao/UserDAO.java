package dao;

import java.util.Collection;
import java.util.List;

import model.User;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

public class UserDAO implements IUserDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	@Override
	public int addUser(User user) {
		sessionFactory.getCurrentSession().beginTransaction();
		int id = (int) sessionFactory.getCurrentSession().save(user);
		sessionFactory.getCurrentSession().getTransaction().commit();
		return id;
	}
	
	@Override
	public void updateUser(User user) {
		sessionFactory.getCurrentSession().beginTransaction();
		sessionFactory.getCurrentSession().update(user);
		sessionFactory.getCurrentSession().getTransaction().commit();
	}	
	
	@Override
	public void deleteUser(User user) {
		sessionFactory.getCurrentSession().beginTransaction();
		sessionFactory.getCurrentSession().delete(user);
		sessionFactory.getCurrentSession().getTransaction().commit();		
	}
	
	@Override
	public User getUserById(int userId) {
		sessionFactory.getCurrentSession().beginTransaction();
		User user = sessionFactory.getCurrentSession().get(User.class, userId);
		sessionFactory.getCurrentSession().getTransaction().commit();
		return user;
	}
	
	@Override
	public User getUserByUsername(String username) {
		sessionFactory.getCurrentSession().beginTransaction();
		Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.username = :username");
		query.setString("username", username);
		query.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<User> result = query.list();
		User user = null;
		
		if (result != null || result.size() > 0) {
			user = result.get(0);
		}
		
		sessionFactory.getCurrentSession().getTransaction().commit();
		return user;
	}
	
	@Override
	public Collection<User> getAllUsers() {
		sessionFactory.getCurrentSession().beginTransaction();
		Query sql = sessionFactory.getCurrentSession().createQuery("from User u");
		@SuppressWarnings("unchecked")
		Collection<User> result = sql.list();
		sessionFactory.getCurrentSession().getTransaction().commit();
				
		return result;
	}

}
