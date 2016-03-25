package dao;

import model.User;

import java.util.Collection;

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
	public Collection<User> getAllUsers() {
		sessionFactory.getCurrentSession().beginTransaction();
		Query sql = sessionFactory.getCurrentSession().createQuery("from User u");
		@SuppressWarnings("unchecked")
		Collection<User> result = sql.list();
		sessionFactory.getCurrentSession().getTransaction().commit();
				
		return result;
	}

}
