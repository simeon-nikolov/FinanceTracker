package dao;

import java.util.Collection;

import model.FinanceOperationType;
import model.Tag;

public interface ITagDAO {
	int addTag(Tag tag);
	
	void updateTag(Tag tag);
	
	void deleteTag(Tag tag);
	
	Tag getTagById(int id);
	
	Tag getTagByTagname(String name);

	Collection<Tag> getAllTagsByTypeFor(FinanceOperationType forType);
}
