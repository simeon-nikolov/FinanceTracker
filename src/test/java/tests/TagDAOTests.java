package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import model.FinanceOperationType;
import model.Tag;

import org.junit.Test;

import dao.ITagDAO;
import dao.TagDAO;

public class TagDAOTests {
	private static final int TAGS_COUNT = 15;
	private static final String NEW_TAG_NAME = "New Tag Name";
	private static final String TAG_NAME = "Job";
	
	private ITagDAO tagDAO = new TagDAO();
	
	@Test
	public void testAddTag() {
		Tag tag = new Tag();
		tag.setTagName(TAG_NAME);
		tag.setForType(FinanceOperationType.INCOME);
		int id = tagDAO.addTag(tag);
		Tag fromDB = tagDAO.getTagById(id);
		assertEquals(id, fromDB.getId());
		assertEquals(tag.getTagName(), fromDB.getTagName());
		assertEquals(tag.getForType(), fromDB.getForType());
		tagDAO.deleteTag(fromDB);
	}
	
	@Test
	public void testUpdateTag() {
		Tag tag = new Tag();
		tag.setTagName(TAG_NAME);
		tag.setForType(FinanceOperationType.INCOME);
		int id = tagDAO.addTag(tag);
		tag.setId(id);
		tag.setTagName(NEW_TAG_NAME);
		tag.setForType(FinanceOperationType.EXPENSE);
		tagDAO.updateTag(tag);
		Tag fromDB = tagDAO.getTagById(id);
		assertEquals(id, fromDB.getId());
		assertEquals(tag.getTagName(), fromDB.getTagName());
		assertEquals(tag.getForType(), fromDB.getForType());
		tagDAO.deleteTag(fromDB);
	}
	
	@Test
	public void testDeleteTag() {
		Tag tag = new Tag();
		tag.setTagName(TAG_NAME);
		tag.setForType(FinanceOperationType.INCOME);
		int id = tagDAO.addTag(tag);
		tag.setId(id);
		tagDAO.deleteTag(tag);
	}
	
	@Test
	public void testGetTagByTagname() {
		Tag tag = new Tag();
		tag.setTagName(TAG_NAME);
		tag.setForType(FinanceOperationType.INCOME);
		int id = tagDAO.addTag(tag);
		Tag fromDB = tagDAO.getTagByTagname(TAG_NAME);
		assertEquals(id, fromDB.getId());
		assertEquals(tag.getTagName(), fromDB.getTagName());
		assertEquals(tag.getForType(), fromDB.getForType());
		tagDAO.deleteTag(fromDB);
	}
	
	@Test
	public void testGetAllTagsByTypeFor() {
		List<Tag> tags = new ArrayList<Tag>(TAGS_COUNT);
		
		for (int index = 0; index < TAGS_COUNT; index++) {
			Tag tag = new Tag();
			tag.setTagName(TAG_NAME + index);
			tag.setForType(FinanceOperationType.INCOME);
			int id = tagDAO.addTag(tag);
			tag.setId(id);
			tags.add(tag);
		}
		
		List<Tag> tagsFromDB = (List<Tag>) tagDAO.getAllTagsByTypeFor(FinanceOperationType.INCOME);
		assertEquals(tags.size(), tagsFromDB.size());
		
		for (Tag tag : tagsFromDB) {
			tagDAO.deleteTag(tag);
		}
	}
}
