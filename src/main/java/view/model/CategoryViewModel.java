package view.model;

import java.util.Collection;
import java.util.LinkedList;

import model.FinanceOperationType;

public class CategoryViewModel {
	private int id;
	
	private String categoryName;
	
	private FinanceOperationType forType;
	
	private Collection<String> tags = new LinkedList<String>();
	
	public CategoryViewModel() {}

	public CategoryViewModel(int id, String categoryName, FinanceOperationType forType, Collection<String> tags) {
		this.id = id;
		this.categoryName = categoryName;
		this.forType = forType;
		this.tags = tags;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public FinanceOperationType getForType() {
		return forType;
	}

	public void setForType(FinanceOperationType forType) {
		this.forType = forType;
	}

	public Collection<String> getTags() {
		return tags;
	}

	public void setTags(Collection<String> tags) {
		this.tags = tags;
	}
}
