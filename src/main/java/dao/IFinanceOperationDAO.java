package dao;

import java.util.Collection;

import model.Account;
import model.Expense;
import model.FinanceOperation;
import model.Income;
import model.User;

import org.joda.time.LocalDate;

import exceptions.DAOException;

public interface IFinanceOperationDAO {
	int add(FinanceOperation financeOperation) throws DAOException;
	
	void update(FinanceOperation financeOperation) throws DAOException;
	
	void delete(FinanceOperation financeOperation) throws DAOException;
	
	Income getIncomeById(int id) throws DAOException;
	
	Expense getExpenseById(int id) throws DAOException;
	
	Collection<Income> getAllIncomesByAccount(Account account) throws DAOException;
	
	Collection<Expense> getAllExpensesByAccount(Account account) throws DAOException;
	
	Collection<Expense> getAllExpensesForAMonth(LocalDate date, Account account) throws DAOException;

	boolean checkUserHasFinanceOperation(FinanceOperation financeOperation, User user) throws DAOException;
}
