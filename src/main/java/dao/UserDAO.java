package dao;

import model.User;

import org.hibernate.SessionFactory;

public class UserDAO implements IUserDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	@Override
	public User getUserById(int userId) {
		sessionFactory.getCurrentSession().beginTransaction();
		User user = sessionFactory.getCurrentSession().get(User.class, userId);
		sessionFactory.getCurrentSession().getTransaction().commit();
		return user;
	}

}
