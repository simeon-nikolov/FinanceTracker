package dao;

import java.util.ArrayList;
import java.util.Collection;

import model.Account;
import model.Expense;
import model.FinanceOperation;
import model.Income;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FinanceOperationDAO implements IFinanceOperationDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int add(FinanceOperation financeOperation) throws DAOException {
		int id = 0;
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			id = (int) sessionFactory.getCurrentSession().save(financeOperation);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Finance operation can not be read from database!", e);
		}
		return id;
	}

	@Override
	public void update(FinanceOperation financeOperation) throws DAOException {
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			sessionFactory.getCurrentSession().update(financeOperation);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Finance operation can not be read from database!", e);
		}
	}

	@Override
	public void delete(FinanceOperation financeOperation) throws DAOException {
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			sessionFactory.getCurrentSession().delete(financeOperation);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Finance operation can not be read from database!", e);
		}
	}

	@Override
	public Income getIncomeById(int id) throws DAOException {
		Income income = null;
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			income = (Income) sessionFactory.getCurrentSession().get(Income.class, id);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Finance operation can not be read from database!", e);
		}

		return income;
	}

	@Override
	public Expense getExpenseById(int id) throws DAOException {
		Expense expense = null;
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			expense = (Expense) sessionFactory.getCurrentSession().get(Expense.class, id);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Finance operation can not be read from database!", e);
		}

		return expense;
	}

	@Override
	public Collection<Income> getAllIncomesByAccount(Account account) throws DAOException {
		Collection<Income> result = null;

		try {
			sessionFactory.getCurrentSession().beginTransaction();
			Query query = sessionFactory.getCurrentSession().createQuery("from Income i where i.account = :account");
			query.setEntity("account", account);
			result = query.list();
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Finance operation can not be read from database!", e);
		}

		return result;
	}

	@Override
	public Collection<Expense> getAllExpensesByAccount(Account account) throws DAOException {
		Collection<Expense> result = null;
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			Query query = sessionFactory.getCurrentSession().createQuery("from Expense e where e.account = :account");
			query.setEntity("account", account);
			result = query.list();
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Finance operation can not be read from database!", e);
		}

		return result;
	}
	
	@Override
	public Collection<Expense> getAllExpensesForAMonth(LocalDate date, Account account) throws DAOException {
		Collection<Expense> allExpenses = null;
		Collection<Expense> result = null;
		
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			allExpenses = new FinanceOperationDAO().getAllExpensesByAccount(account);
			int month = date.getMonthOfYear();
			int year = date.getYear();
			result = new ArrayList<Expense>();
			for (Expense expense : allExpenses) {
				if (expense.getDate().getMonthOfYear() == month && expense.getDate().getYear() == year) {
					result.add(expense);
				}
			}		
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Finance operation can not be read from database!", e);
		}

		return result;
	}

}
