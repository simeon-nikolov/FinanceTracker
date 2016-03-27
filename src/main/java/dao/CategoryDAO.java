package dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;


import model.Category;
import model.FinanceOperationType;


public class CategoryDAO implements ICategoryDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		
	@Override
	public int addCategory(Category category) {
		sessionFactory.getCurrentSession().beginTransaction();
		int id = (int) sessionFactory.getCurrentSession().save(category);
		sessionFactory.getCurrentSession().getTransaction().commit();
		return id;
	}
		
	@Override
	public void updateCategory(Category category) {
		sessionFactory.getCurrentSession().beginTransaction();
		sessionFactory.getCurrentSession().update(category);
		sessionFactory.getCurrentSession().getTransaction().commit();
	}	
		
	@Override
	public void deleteCategory(Category category) {
		sessionFactory.getCurrentSession().beginTransaction();
		sessionFactory.getCurrentSession().delete(category);
		sessionFactory.getCurrentSession().getTransaction().commit();		
	}
	
	@Override
	public Category getCategoryById(int categoryId) {
		sessionFactory.getCurrentSession().beginTransaction();
		Category category = sessionFactory.getCurrentSession().get(Category.class, categoryId);
		sessionFactory.getCurrentSession().getTransaction().commit();
		
		return category;
	}

	
	@Override
	public Collection<Category> getAllCategoriesForFOType(FinanceOperationType type) {
		sessionFactory.getCurrentSession().beginTransaction();
		Query query = sessionFactory.getCurrentSession().createQuery(
				"FROM Category c WHERE c.forType = :type");	
		query.setParameter("type", type);				
		Collection<Category> result = query.list();
		sessionFactory.getCurrentSession().getTransaction().commit();
		
		return result;
	}
	
	@SuppressWarnings("null")
	@Override
	public Category getCategoryByName(String categoryName) {
		sessionFactory.getCurrentSession().beginTransaction();
		Query query = sessionFactory.getCurrentSession()
				.createQuery("FROM Category C WHERE C.categoryName = :category");
		query.setString("category", categoryName);
		query.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<Category> result = query.list();
		Category category = null;
		
		if (result != null || result.size() > 0) {
			category = result.get(0);
		}
		
		sessionFactory.getCurrentSession().getTransaction().commit();
		return category;
	}
}
