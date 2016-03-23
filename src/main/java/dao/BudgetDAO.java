package dao;

import model.Budget;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BudgetDAO implements IBudgetDAO {
	
	@Autowired
	SessionFactory session;

	@Override
	public void add(Budget budget) {
		session.getCurrentSession().save(budget);
	}

}
