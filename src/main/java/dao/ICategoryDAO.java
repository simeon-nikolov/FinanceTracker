package dao;

import java.util.Collection;

import model.Category;
import model.FinanceOperationType;

public interface ICategoryDAO {

	int addCategory(Category category);

	void updateCategory(Category category);

	void deleteCategory(Category category);

	Category getCategoryById(int categoryId);

	Collection<Category> getAllCategoriesForFOType(FinanceOperationType type);

	Category getCategoryByName(String categoryName);

}