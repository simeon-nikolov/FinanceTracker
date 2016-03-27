package model;


import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import exceptions.InvalidArgumentException;

@Entity
@Table(name="finance_operations")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "finance_operation_type", discriminatorType = DiscriminatorType.STRING)
public abstract class FinanceOperation {
	private static final String DATE_ERROR_MESSAGE = "Date is null!";
	private static final String DESCRIPTION_ERROR_MESSAGE = "The description is null or empty string!";
	private static final String ACCOUNT_ERROR_MESSAGE = "Account is null!";
	private static final String MONEY_AMOUNT_ERROR_MESSAGE = "Money amount can't be a negative value!";
	private static final String ID_ERROR_MESSAGE = "ID can't be a negative number!";
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private int amount;

	@Column(name="currency")
	@Enumerated(EnumType.STRING)
	private Currency currency;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="account_id")
	private Account account;
	
	@Column
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate date;
	
	@Column
	private String description;
	
	@Column(name="repeat_type")
	@Enumerated(EnumType.STRING)
	private RepeatType repeatType;
	
	@Column(name="finance_operation_type", insertable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private FinanceOperationType financeOperationType;
	
	@ManyToMany
	@JoinTable(name="finance_operation_has_tags",
	          joinColumns=@JoinColumn(name="finance_operation_id"),
	          inverseJoinColumns=@JoinColumn(name="tag_id"))
	private Collection<Tag> tags = new LinkedList<Tag>();
	
	public FinanceOperation() {}

	public FinanceOperation(int id, int amount, Currency currency, Category category, 
			Account account, LocalDate date, String description, RepeatType repeatType,
			FinanceOperationType financeOperationType)
					throws InvalidArgumentException {
		this.setId(id);
		this.setAmount(amount);
		this.setCurrency(currency);
		this.setCategory(category);
		this.setAccount(account);
		this.setDate(date);
		this.setDescription(description);
		this.setRepeatType(repeatType);
		this.setFinanceOperationType(financeOperationType);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) throws InvalidArgumentException {
	if (id < 0) {
		throw new InvalidArgumentException(ID_ERROR_MESSAGE);
	}
			
		this.id = id;
	}

	public int getAmount() {
		
		
		return amount;
	}

	public void setAmount(int amount) throws InvalidArgumentException {
		if (amount < 0) {
			throw new InvalidArgumentException(MONEY_AMOUNT_ERROR_MESSAGE);
		}
		
		this.amount = amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {		
		this.category = category;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) throws InvalidArgumentException {
		if (account == null) {
			throw new InvalidArgumentException(ACCOUNT_ERROR_MESSAGE);
		}
		
		this.account = account;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) throws InvalidArgumentException {
		if (date == null) {
			throw new InvalidArgumentException(DATE_ERROR_MESSAGE);
		}
		
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) throws InvalidArgumentException {
		if (description == null || description.trim().equals("")) {
			throw new InvalidArgumentException(DESCRIPTION_ERROR_MESSAGE);
		}
		
		this.description = description;
	}

	public RepeatType getRepeatType() {
		return repeatType;
	}

	public void setRepeatType(RepeatType repeatType) {
		this.repeatType = repeatType;
	}
	
	public FinanceOperationType getFinanceOperationType() {
		return financeOperationType;
	}

	public void setFinanceOperationType(FinanceOperationType financeOperationType) {
		this.financeOperationType = financeOperationType;
	}

	public Collection<Tag> getTags() {
		List<Tag> tags = new LinkedList<Tag>();
		
		for (Tag tag : this.tags) {
			tags.add((Tag) tag.clone());
		}
		
		return tags;
	}

	public void setTags(Collection<Tag> tags) {
		for (Tag tag : tags) {
			this.tags.add((Tag) tag.clone());
		}
	}
}
