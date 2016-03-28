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
	public int add(Budget budget) {
		sessionFactory.getCurrentSession().beginTransaction();
		int id = (int) sessionFactory.getCurrentSession().save(budget);
		sessionFactory.getCurrentSession().getTransaction().commit();
		return id;
	}

	@Override
	public void update(Budget budget) {
		sessionFactory.getCurrentSession().beginTransaction();
		sessionFactory.getCurrentSession().update(budget);
		sessionFactory.getCurrentSession().getTransaction().commit();
	}

	@Override
	public void delete(Budget budget) {
		sessionFactory.getCurrentSession().beginTransaction();
		sessionFactory.getCurrentSession().delete(budget);
		sessionFactory.getCurrentSession().getTransaction().commit();
	}

	@Override
	public Budget getBudgetById(int id) {
		sessionFactory.getCurrentSession().beginTransaction();
		Budget budget = sessionFactory.getCurrentSession().get(Budget.class, id);
		sessionFactory.getCurrentSession().getTransaction().commit();
		return budget;
	}

	@Override
	public Collection<Budget> getAllBudgetsByUser(User user) {
		sessionFactory.getCurrentSession().beginTransaction();
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from Budget b where b.user = :user");
		query.setEntity("user", user);
		Collection<Budget> result = query.list();
		sessionFactory.getCurrentSession().getTransaction().commit();
		return result;
	}
}
