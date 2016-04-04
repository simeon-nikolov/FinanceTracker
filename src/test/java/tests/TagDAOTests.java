package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import model.FinanceOperationType;
import model.Tag;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import dao.DAOException;
import dao.ITagDAO;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=config.SpringWebConfig.class)
public class TagDAOTests {
	private static final int TAGS_COUNT = 15;
	private static final String NEW_TAG_NAME = "New Tag Name";
	private static final String TAG_NAME = "Fun";
	
	@Autowired
	private ITagDAO tagDAO;
	
	@Test
	public void testAddTag() throws DAOException {
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
	public void testUpdateTag() throws DAOException {
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
	public void testDeleteTag() throws DAOException {
		Tag tag = new Tag();
		tag.setTagName(TAG_NAME);
		tag.setForType(FinanceOperationType.INCOME);
		int id = tagDAO.addTag(tag);
		tag.setId(id);
		tagDAO.deleteTag(tag);
	}
	
	@Test
	public void testGetTagByTagname() throws DAOException {
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
	public void testGetAllTagsByTypeFor() throws DAOException {
		List<Tag> tags = new ArrayList<Tag>(TAGS_COUNT);
		List<Tag> tagsFromDB = (List<Tag>) tagDAO.getAllTagsByTypeFor(FinanceOperationType.EXPENSE);
		int sizeBefore = tagsFromDB == null ? 0 : tagsFromDB.size();
		
		for (int index = 0; index < TAGS_COUNT; index++) {
			Tag tag = new Tag();
			tag.setTagName(TAG_NAME + index);
			tag.setForType(FinanceOperationType.EXPENSE);
			int id = tagDAO.addTag(tag);
			tag.setId(id);
			tags.add(tag);
		}
		
		tagsFromDB = (List<Tag>) tagDAO.getAllTagsByTypeFor(FinanceOperationType.EXPENSE);
		assertEquals(tags.size(), tagsFromDB.size() - sizeBefore);
		
		for (Tag tag : tagsFromDB) {
			tagDAO.deleteTag(tag);
		}
	}
}
