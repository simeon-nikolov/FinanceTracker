package dao;

import java.util.Collection;

import model.Category;
import model.FinanceOperationType;

public interface ICategoryDAO {

	int addCategory(Category category) throws DAOException;

	void updateCategory(Category category) throws DAOException;

	void deleteCategory(Category category) throws DAOException;

	Category getCategoryById(int categoryId) throws DAOException;

	Collection<Category> getAllCategoriesForFOType(FinanceOperationType type) throws DAOException;

	Category getCategoryByName(String categoryName) throws DAOException;

}