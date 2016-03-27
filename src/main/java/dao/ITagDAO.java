package dao;

import model.Tag;

public interface ITagDAO {
	int addTag(Tag tag);
	
	void updateTag(Tag tag);
	
	void deleteTag(Tag tag);
	
	Tag getTagById(int id);
	
	Tag getTagByTagname(String name);
}
