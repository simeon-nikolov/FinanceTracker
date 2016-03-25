package dao;

import java.util.Collection;

import model.Budget;
import model.User;

public interface IBudgetDAO {
	int add(Budget budget);
	
	void update(Budget budget);
	
	void delete(Budget budget);
	
	Budget getBudgetById(int id);
	
	Collection<Budget> getAllBudgetsByUser(User user);
}
