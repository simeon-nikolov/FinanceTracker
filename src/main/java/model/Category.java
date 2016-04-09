package model;

import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="categories")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="category")
	private String categoryName;

	@Column(name="for_type")
	@Enumerated(EnumType.STRING)
	private FinanceOperationType forType;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name="categories_has_tags",
	          joinColumns=@JoinColumn(name="category_id"),
	          inverseJoinColumns=@JoinColumn(name="tag_id"))
	private Collection<Tag> tags = new LinkedList<Tag>();

	public Category() {}

	public Category(int id, String categoryName, FinanceOperationType forType, Collection<Tag> tags) {
		this.id = id;
		this.categoryName = categoryName;
		this.forType = forType;
		this.setTags(tags);
	}

	public Category(int id, String categoryName, FinanceOperationType forType) {
		this.id = id;
		this.categoryName = categoryName;
		this.forType = forType;
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

	public Collection<Tag> getTags() {
		return tags;
	}

	public void setTags(Collection<Tag> tags) {
		this.tags = tags;
	}
}
