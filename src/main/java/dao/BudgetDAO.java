package dao;

import model.Budget;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class BudgetDAO implements IBudgetDAO {
	
	SessionFactory session = HibernateUtil.getSessionFactory();

	@Override
	public void add(Budget budget) {
		// TO DO
	}
	
}
