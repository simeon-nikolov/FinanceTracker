package dao;

import java.util.Collection;

import model.Budget;
import model.User;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BudgetDAO implements IBudgetDAO {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public int add(Budget budget) throws DAOException {
		int id = 0;
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			id = (int) sessionFactory.getCurrentSession().save(budget);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Budget can not be read from database!", e);
		}
		return id;
	}

	@Override
	public void update(Budget budget) throws DAOException {
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			sessionFactory.getCurrentSession().update(budget);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Budget can not be read from database!", e);
		}
	}

	@Override
	public void delete(Budget budget) throws DAOException {
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			sessionFactory.getCurrentSession().delete(budget);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Budget can not be read from database!", e);
		}
	}

	@Override
	public Budget getBudgetById(int id) throws DAOException {
		Budget budget = null;
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			budget = (Budget) sessionFactory.getCurrentSession().get(Budget.class, id);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Budget can not be read from database!", e);
		}

		return budget;
	}

	@Override
	public Collection<Budget> getAllBudgetsByUser(User user) throws DAOException {
		Collection<Budget> result = null;
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			Query query = sessionFactory.getCurrentSession().createQuery("from Budget b where b.user = :user");
			query.setEntity("user", user);
			result = query.list();
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Budget can not be read from database!", e);
		}

		return result;
	}
}
