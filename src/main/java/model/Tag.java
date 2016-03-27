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
@Table(name="tags")
public class Tag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	private String tagName;
	
	@Column(name="for_type")
	@Enumerated(EnumType.STRING)
	private FinanceOperationType forType;
	
	public Tag() {}
	
	public Tag(int id, String tagName, FinanceOperationType forType) {
		this.id = id;
		this.tagName = tagName;
		this.forType = forType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public FinanceOperationType getForType() {
		return forType;
	}

	public void setForType(FinanceOperationType forType) {
		this.forType = forType;
	}
	
	@Override
	protected Object clone() {
		return new Tag(this.id, this.tagName, this.forType);
	}
}
