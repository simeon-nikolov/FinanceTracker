package dao;

import java.util.Collection;

import model.Budget;
import model.User;

public interface IBudgetDAO {
	int add(Budget budget) throws DAOException;
	
	void update(Budget budget) throws DAOException;
	
	void delete(Budget budget) throws DAOException;
	
	Budget getBudgetById(int id) throws DAOException;
	
	Collection<Budget> getAllBudgetsByUser(User user) throws DAOException;

	boolean checkUserHasBudget(Budget budget, User user) throws DAOException;
}
