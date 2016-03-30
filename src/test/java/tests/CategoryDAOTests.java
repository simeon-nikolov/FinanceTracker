package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import model.Category;
import model.FinanceOperationType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import dao.ICategoryDAO;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=config.SpringWebConfig.class)
public class CategoryDAOTests {
	
	private static final int RANDOM_NUMBER_FOR_TESTS = 10_000;
	private static final int NUMBER_OF_TEST_CATEGORIES = 10;
	
	@Autowired
	private ICategoryDAO dao;
	
	@Test
	public void testAddCatgory() {
		FinanceOperationType type = getRandomFinanceOperationType();
		Category category = makeNewCategory(type);
		String title = category.getCategoryName();		
		int id = dao.addCategory(category);
		
		Category fromDB = dao.getCategoryById(id);
		
		assertEquals(fromDB.getCategoryName(), title);
		assertEquals(fromDB.getForType(), type);
		
		dao.deleteCategory(category);
	}
	
	@Test
	public void testUpdateCategory() {
		FinanceOperationType type = getRandomFinanceOperationType();
		Category category = makeNewCategory(type);
		int id = dao.addCategory(category);
		
		String title = "testTitle";
		category.setCategoryName(title);
		
		dao.updateCategory(category);
		Category fromDB = dao.getCategoryById(id);
		
		assertEquals(fromDB.getCategoryName(), title);
		
		dao.deleteCategory(category);		
	}
	
	@Test
	public void testGetCategoryById() {
		FinanceOperationType type = getRandomFinanceOperationType();
		Category category = makeNewCategory(type);
		String title = category.getCategoryName();		
		int id = dao.addCategory(category);
		
		Category fromDB = dao.getCategoryById(id);
		
		assertEquals(fromDB.getCategoryName(), title);
		assertEquals(fromDB.getForType(), type);
		
		dao.deleteCategory(category);		
	}
	
	@Test
	public void testGetAllCategoriesForFOType() {
		FinanceOperationType type = getRandomFinanceOperationType();
		List<Category> startingList = (List<Category>) dao.getAllCategoriesForFOType(type);
		int startNumber = startingList.size();
		List<Category> testCategories = new ArrayList<Category>();
		
		for (int numberOfNewCats = 0; numberOfNewCats < NUMBER_OF_TEST_CATEGORIES; numberOfNewCats++) {
			Category testCat = makeNewCategory(type);
			testCategories.add(testCat);
			dao.addCategory(testCat);
		}
		List<Category> finalList = (List<Category>) dao.getAllCategoriesForFOType(type);
		int finalNumber = finalList.size();
		startNumber += NUMBER_OF_TEST_CATEGORIES;
		
		assertEquals(startNumber, finalNumber);
		
		for (int numberOftestCats = 0; numberOftestCats < testCategories.size(); numberOftestCats++) {
			dao.deleteCategory(testCategories.get(numberOftestCats));
		}
		
	}
	
	@Test
	public void testGetCategoryByName() {
		FinanceOperationType type = getRandomFinanceOperationType();
		Category category = makeNewCategory(type);
		String categoryName = category.getCategoryName();
		int id = dao.addCategory(category);
		category.setId(id);
		
		Category fromDB = dao.getCategoryByName(categoryName);
		
		assertEquals(fromDB.getId(), id);
		assertEquals(fromDB.getCategoryName(), category.getCategoryName());
		assertEquals(fromDB.getForType(), category.getForType());
		
		dao.deleteCategory(fromDB);		
	}
	
	private Category makeNewCategory(FinanceOperationType type) {
		Category category = new Category();
		int randomNum = (int)(Math.random() * RANDOM_NUMBER_FOR_TESTS);
		
		category.setCategoryName("testCategory" + randomNum);
		category.setForType(type);
		
		return category;
		
	}
	
	private FinanceOperationType getRandomFinanceOperationType() {
		FinanceOperationType type = null;
		if (Math.random() > 0.5) {
			type = FinanceOperationType.EXPENSE;
		}
		else {
			type = FinanceOperationType.INCOME;
		}
		
		return type;
	}

}
