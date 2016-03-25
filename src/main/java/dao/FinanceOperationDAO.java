package dao;

import java.util.Collection;

import model.Account;
import model.Expense;
import model.FinanceOperation;
import model.Income;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

public class FinanceOperationDAO implements IFinanceOperationDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	@Override
	public int add(FinanceOperation financeOperation) {
		sessionFactory.getCurrentSession().beginTransaction();
		int id = (int) sessionFactory.getCurrentSession().save(financeOperation);
		sessionFactory.getCurrentSession().getTransaction().commit();
		return id;
	}

	@Override
	public void update(FinanceOperation financeOperation) {
		sessionFactory.getCurrentSession().beginTransaction();
		sessionFactory.getCurrentSession().update(financeOperation);
		sessionFactory.getCurrentSession().getTransaction().commit();
	}

	@Override
	public void delete(FinanceOperation financeOperation) {
		sessionFactory.getCurrentSession().beginTransaction();
		sessionFactory.getCurrentSession().delete(financeOperation);
		sessionFactory.getCurrentSession().getTransaction().commit();
	}

	@Override
	public Income getIncomeById(int id) {
		Income income = sessionFactory.getCurrentSession().get(Income.class, id);
		return income;
	}

	@Override
	public Expense getExpenseById(int id) {
		Expense expense = sessionFactory.getCurrentSession().get(Expense.class, id);
		return expense;
	}

	@Override
	public Collection<Income> getAllIncomesByAccount(Account account) {
		sessionFactory.getCurrentSession().beginTransaction();
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from Income i where i.account_id = :account_id");
		query.setInteger("account_id", account.getId());
		Collection<Income> result = query.list();
		sessionFactory.getCurrentSession().getTransaction().commit();
		return result;
	}

	@Override
	public Collection<Expense> getAllExpensesByAccount(Account account) {
		sessionFactory.getCurrentSession().beginTransaction();
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from Expense e where e.account_id = :account_id");
		query.setInteger("account_id", account.getId());
		Collection<Expense> result = query.list();
		sessionFactory.getCurrentSession().getTransaction().commit();
		return result;
	}

}
