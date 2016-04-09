package dao;

import java.util.Collection;
import java.util.List;

import model.Category;
import model.FinanceOperationType;
import model.Tag;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import exceptions.DAOException;

@Repository
public class TagDAO implements ITagDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int addTag(Tag tag) throws DAOException {
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			int id = (int) sessionFactory.getCurrentSession().save(tag);
			sessionFactory.getCurrentSession().getTransaction().commit();
			return id;
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Tag can not be added to database!", e);
		}
	}

	@Override
	public void updateTag(Tag tag) throws DAOException {
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			sessionFactory.getCurrentSession().update(tag);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Tag can not be read from database!", e);
		}
	}

	@Override
	public void deleteTag(Tag tag) throws DAOException {
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			sessionFactory.getCurrentSession().delete(tag);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Tag can not be deleted from database!", e);
		}
	}

	@Override
	public Tag getTagById(int id) throws DAOException {
		Tag tag = null;
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			tag = (Tag) sessionFactory.getCurrentSession().get(Tag.class, id);
			sessionFactory.getCurrentSession().getTransaction().commit();

		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Tag can not be read from database!", e);
		}
		return tag;
	}

	@Override
	public Tag getTagByTagname(String name) throws DAOException {
		List<Tag> result = null;
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			Query query = sessionFactory.getCurrentSession().createQuery("from Tag t where t.tagName = :name");
			query.setString("name", name);
			query.setMaxResults(1);
			result = query.list();
			sessionFactory.getCurrentSession().getTransaction().commit();
			if (result == null || result.size() == 0) {
				return null;
			}

		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Tag can not be read from database!", e);
		}
		return result.get(0);
	}

	@Override
	public Collection<Tag> getAllTagsByTypeFor(FinanceOperationType forType) throws DAOException {
		Collection<Tag> result = null;
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			Query query = sessionFactory.getCurrentSession().createQuery("from Tag t where t.forType = :forType");
			query.setString("forType", forType.toString());
			result = query.list();
			sessionFactory.getCurrentSession().getTransaction().commit();
			if (result == null || result.size() == 0) {
				return null;
			}

		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Tag can not be read from database!", e);
		}
		return result;
	}

	@Override
	public Collection<Tag> getTagsForCategory(Category category) throws DAOException {
		Collection<Tag> result = null;

		try {
			sessionFactory.getCurrentSession().beginTransaction();
			Query query = sessionFactory.getCurrentSession()
					.createQuery("select t from Tag t, Category c where t in elements(c.tags) and c = :category");
			query.setEntity("category", category);
			result = query.list();
			sessionFactory.getCurrentSession().getTransaction().commit();

			if (result == null || result.size() == 0) {
				return null;
			}
		} catch (RuntimeException e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
			throw new DAOException("Tags can not be read from database!", e);
		}

		return result;
	}
}
