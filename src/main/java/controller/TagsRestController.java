package controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import model.Category;
import model.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dao.ICategoryDAO;
import dao.ITagDAO;

@RestController
public class TagsRestController {
	@Autowired
	private ICategoryDAO categoryDAO;
	@Autowired
	private ITagDAO tagDAO;

	@RequestMapping(value = "/{category}/tags", method = RequestMethod.GET)
	public List<String> getExpensesForAllAccounts(@PathVariable("category") String categoryName,
			HttpSession session) {
		List<String> tags = new LinkedList<String>();

		try {
			Category category = categoryDAO.getCategoryByName(categoryName);
			List<Tag> tagsForCategory = (List<Tag>) tagDAO.getTagsForCategory(category);

			if (tagsForCategory != null) {
				for (Tag tag : tagsForCategory) {
					tags.add(tag.getTagName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tags;
	}
}
