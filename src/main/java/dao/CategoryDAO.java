package dao;

import java.util.Collection;
import java.util.List;

import model.Category;
import model.FinanceOperationType;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDAO implements ICategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int addCategory(Category category) throws DAOException {
		int id = 0;
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			id = (int) sessionFactory.getCurrentSession().save(category);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Category can not be read from database!", e);
		}
		return id;
	}

	@Override
	public void updateCategory(Category category) throws DAOException {
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			sessionFactory.getCurrentSession().update(category);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Category can not be read from database!", e);
		}
	}

	@Override
	public void deleteCategory(Category category) throws DAOException {
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			sessionFactory.getCurrentSession().delete(category);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Category can not be read from database!", e);
		}
	}

	@Override
	public Category getCategoryById(int categoryId) throws DAOException {
		Category category = null;
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			category = (Category) sessionFactory.getCurrentSession().get(Category.class, categoryId);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Category can not be read from database!", e);
		}

		return category;
	}

	@Override
	public Collection<Category> getAllCategoriesForFOType(FinanceOperationType type) throws DAOException {
		Collection<Category> result = null;
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			Query query = sessionFactory.getCurrentSession().createQuery("FROM Category c WHERE c.forType = :type");
			query.setParameter("type", type);
			result = query.list();

			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Category can not be read from database!", e);
		}

		return result;
	}

	@Override
	public Category getCategoryByName(String categoryName) throws DAOException {
		Category category = null;

		try {
			sessionFactory.getCurrentSession().beginTransaction();
			Query query = sessionFactory.getCurrentSession()
					.createQuery("FROM Category C WHERE C.categoryName = :category");
			query.setString("category", categoryName);
			query.setMaxResults(1);
			@SuppressWarnings("unchecked")
			List<Category> result = query.list();
			if (result != null && result.size() > 0) {
				category = result.get(0);
			}

			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Category can not be read from database!", e);
		}

		return category;
	}
}
