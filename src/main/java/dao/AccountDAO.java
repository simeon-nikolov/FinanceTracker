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
	public int addAccount(Account account) throws DAOException {
		int id = 0;
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			id = (int) sessionFactory.getCurrentSession().save(account);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Account can not be read from database!", e);
		}
		return id;
	}

	@Override
	public void updateAccount(Account account) throws DAOException {
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			sessionFactory.getCurrentSession().update(account);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Account can not be read from database!", e);
		}
	}

	@Override
	public void deleteAccount(Account account) throws DAOException {
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			sessionFactory.getCurrentSession().delete(account);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Account can not be read from database!", e);
		}
	}

	@Override
	public Account getAccountById(int accountId) throws DAOException {
		Account account = null;
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			account = (Account) sessionFactory.getCurrentSession().get(Account.class, accountId);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Account can not be read from database!", e);
		}

		return account;
	}

	@Override
	public Collection<Account> getAllAccountsForUser(User user) throws DAOException {
		Collection<Account> result = null;
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			Query query = sessionFactory.getCurrentSession().createQuery("FROM Account a WHERE a.user = :user");
			query.setEntity("user", user);
			result = query.list();
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Account can not be read from database!", e);
		}

		return result;
	}

	@Override
	@SuppressWarnings("null")
	public Account getAccountForUserByName(String accountName, User user) throws DAOException {
		Account account = null;
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			Query query = sessionFactory.getCurrentSession()
					.createQuery("FROM Account A WHERE A.title = :title AND A.user = :user ");
			query.setString("title", accountName);
			query.setEntity("user", user);
			query.setMaxResults(1);
			@SuppressWarnings("unchecked")
			List<Account> result = query.list();

			if (result != null && result.size() > 0) {
				account = result.get(0);
			}

			sessionFactory.getCurrentSession().getTransaction().commit();

		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Account can not be read from database!", e);
		}
		return account;
	}

}
