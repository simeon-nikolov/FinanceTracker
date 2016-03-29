package dao;

import java.util.Collection;
import java.util.List;

import model.Account;
import model.User;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDAO implements IAccountDAO {
	
	@Autowired
	private SessionFactory sessionFactory;	
	
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
	
	@Override
	@SuppressWarnings("null")
	public Account getAccountForUserByName(String accountName, User user) {
		sessionFactory.getCurrentSession().beginTransaction();
		Query query = sessionFactory.getCurrentSession()
				.createQuery("FROM Account A WHERE A.title = :title AND A.user = :user ");
		query.setString("title", accountName);
		query.setEntity("user", user);
		query.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<Account> result = query.list();
		Account account = null;
		
		if (result != null || result.size() > 0) {
			account = result.get(0);
		}
		
		sessionFactory.getCurrentSession().getTransaction().commit();
		return account;
	}

}
