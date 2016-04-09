package dao;

import java.util.Collection;

import model.Category;
import model.FinanceOperationType;
import model.Tag;
import exceptions.DAOException;

public interface ITagDAO {
	int addTag(Tag tag) throws DAOException;

	void updateTag(Tag tag) throws DAOException;

	void deleteTag(Tag tag) throws DAOException;

	Tag getTagById(int id) throws DAOException;

	Tag getTagByTagname(String name) throws DAOException;

	Collection<Tag> getAllTagsByTypeFor(FinanceOperationType forType) throws DAOException;

	Collection<Tag> getTagsForCategory(Category category) throws DAOException;
}
