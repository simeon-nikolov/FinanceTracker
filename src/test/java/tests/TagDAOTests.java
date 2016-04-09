package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import model.Category;
import model.FinanceOperationType;
import model.Tag;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import dao.ICategoryDAO;
import dao.ITagDAO;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=config.SpringWebConfig.class)
public class TagDAOTests {
	private static final int RANDOM_NUMBER_FOR_TESTS = 10_000;
	private static final int TAGS_COUNT = 15;
	private static final String NEW_TAG_NAME = "New Tag Name";
	private static final String TAG_NAME = "tag";

	@Autowired
	private ITagDAO tagDAO;
	@Autowired
	private ICategoryDAO categoryDAO;

	@Test
	public void testAddTag() throws Exception {
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
	public void testUpdateTag() throws Exception {
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
	public void testDeleteTag() throws Exception {
		Tag tag = new Tag();
		tag.setTagName(TAG_NAME);
		tag.setForType(FinanceOperationType.INCOME);
		int id = tagDAO.addTag(tag);
		tag.setId(id);
		tagDAO.deleteTag(tag);
	}

	@Test
	public void testGetTagByTagname() throws Exception {
		Tag tag = new Tag();
		int randNumber = (int) (Math.random() * RANDOM_NUMBER_FOR_TESTS);
		String tagName = TAG_NAME + randNumber;
		tag.setTagName(tagName);
		tag.setForType(FinanceOperationType.INCOME);
		int id = tagDAO.addTag(tag);
		Tag fromDB = tagDAO.getTagByTagname(tagName);
		assertEquals(id, fromDB.getId());
		assertEquals(tag.getTagName(), fromDB.getTagName());
		assertEquals(tag.getForType(), fromDB.getForType());
		tagDAO.deleteTag(fromDB);
	}

	@Test
	public void testGetAllTagsByTypeFor() throws Exception {
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

	@Test
	public void testGetTagsByCategory() throws Exception {
		Category category = new Category(0, "TestCategory", FinanceOperationType.EXPENSE);
		Collection<Tag> tags = new LinkedList<Tag>();

		for (int index = 0; index < 5; index++) {
			tags.add(new Tag(0, "test" + index, FinanceOperationType.EXPENSE));
		}

		for (Tag tag : tags) {
			int id = tagDAO.addTag(tag);
			tag.setId(id);
		}

		category.setTags(tags);
		categoryDAO.addCategory(category);

		Collection<Tag> tagsFromDB = tagDAO.getTagsForCategory(category);
		assertEquals(tags.size(), tagsFromDB.size());

		categoryDAO.deleteCategory(category);
	}
}
