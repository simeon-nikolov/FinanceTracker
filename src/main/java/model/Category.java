package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	public Category() {}

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
}
