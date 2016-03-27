package dao;

import java.util.Collection;
import java.util.List;

import model.FinanceOperationType;
import model.Tag;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

public class TagDAO implements ITagDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	@Override
	public int addTag(Tag tag) {
		sessionFactory.getCurrentSession().beginTransaction();
		int id = (int) sessionFactory.getCurrentSession().save(tag);
		sessionFactory.getCurrentSession().getTransaction().commit();
		return id;
	}

	@Override
	public void updateTag(Tag tag) {
		sessionFactory.getCurrentSession().beginTransaction();
		sessionFactory.getCurrentSession().update(tag);
		sessionFactory.getCurrentSession().getTransaction().commit();
	}

	@Override
	public void deleteTag(Tag tag) {
		sessionFactory.getCurrentSession().beginTransaction();
		sessionFactory.getCurrentSession().delete(tag);
		sessionFactory.getCurrentSession().getTransaction().commit();
	}

	@Override
	public Tag getTagById(int id) {
		sessionFactory.getCurrentSession().beginTransaction();
		Tag tag = sessionFactory.getCurrentSession().get(Tag.class, id);
		sessionFactory.getCurrentSession().getTransaction().commit();
		return tag;
	}

	@Override
	public Tag getTagByTagname(String name) {
		sessionFactory.getCurrentSession().beginTransaction();
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from Tag t where t.tagName = :name");
		query.setString("name", name);
		query.setMaxResults(1);
		List<Tag> result = query.list();
		sessionFactory.getCurrentSession().getTransaction().commit();
		if (result == null || result.size() == 0) {
			return null;
		}
		return result.get(0);
	}
	
	@Override
	public Collection<Tag> getAllTagsByTypeFor(FinanceOperationType forType) {
		sessionFactory.getCurrentSession().beginTransaction();
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from Tag t where t.forType = :forType");
		query.setString("forType", forType.toString());
		Collection<Tag> result = query.list();
		sessionFactory.getCurrentSession().getTransaction().commit();
		if (result == null || result.size() == 0) {
			return null;
		}
		return result;
	}

}
