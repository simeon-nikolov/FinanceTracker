package dao;

import java.util.Collection;

import model.Account;
import model.User;

import org.hibernate.Query;
import org.hibernate.SessionFactory;


public class AccountDAO implements IAccountDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();	
	
	@Override
	public int addAccount(Account account) {
		sessionFactory.getCurrentSession().beginTransaction();
		int id = (int) sessionFactory.getCurrentSession().save(account);
		sessionFactory.getCurrentSession().getTransaction().commit();
		return id;
	}
	
	@Override
	public void updateAccount(Account account) {
		sessionFactory.getCurrentSession().beginTransaction();
		sessionFactory.getCurrentSession().update(account);
		sessionFactory.getCurrentSession().getTransaction().commit();
	}	
	
	@Override
	public void deleteAccount(Account account) {
		sessionFactory.getCurrentSession().beginTransaction();
		sessionFactory.getCurrentSession().delete(account);
		sessionFactory.getCurrentSession().getTransaction().commit();		
	}
	
	@Override
	public Account getAccountById(int accountId) {
		sessionFactory.getCurrentSession().beginTransaction();
		Account account = sessionFactory.getCurrentSession().get(Account.class, accountId);
		sessionFactory.getCurrentSession().getTransaction().commit();
		
		return account;
	}
	
	@Override
	public Collection<Account> getAllAccountsForUser(User user) {
		sessionFactory.getCurrentSession().beginTransaction();
		Query query = sessionFactory.getCurrentSession().createQuery(
				"FROM Account a WHERE a.user = :user");
		query.setEntity("user", user);		
		Collection<Account> result = query.list();
		sessionFactory.getCurrentSession().getTransaction().commit();
		
		return result;
	}

}
