package dao;

import java.util.Collection;

import model.Account;
import model.Expense;
import model.FinanceOperation;
import model.Income;

public interface IFinanceOperationDAO {
	int add(FinanceOperation financeOperation);
	
	void update(FinanceOperation financeOperation);
	
	void delete(FinanceOperation financeOperation);
	
	Income getIncomeById(int id);
	
	Expense getExpenseById(int id);
	
	Collection<Income> getAllIncomesByAccount(Account account);
	
	Collection<Expense> getAllExpensesByAccount(Account account);
}
