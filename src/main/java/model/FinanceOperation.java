package model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import exceptions.InvalidArgumentException;

@Entity
@Table(name="finance_operations")
public abstract class FinanceOperation {
	private static final String DATE_ERROR_MESSAGE = "Date is null!";
	private static final String DESCRIPTION_ERROR_MESSAGE = "The description is null or empty string!";
	private static final String ACCOUNT_ERROR_MESSAGE = "Account is null!";
	private static final String CATEGORY_ERROR_MESSAGE = "Category is null or is empty string!";
	private static final String MONEY_AMOUNT_ERROR_MESSAGE = "Money amount can't be a negative value!";
	private static final String ID_ERROR_MESSAGE = "ID can't be a negative number!";
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private int amount;

	@JoinColumn(name="currency")
	private Currency currency;
	
	@JoinColumn(name="category")
	private String category;
	
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
	
	@Column(name="finance_operatio_type")
	@Enumerated(EnumType.STRING)
	private FinanceOperationType financeOperationType;
	
	public FinanceOperation() {}

	public FinanceOperation(int id, int amount, Currency currency, String category, 
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) throws InvalidArgumentException {
		if (category == null || category.trim().equals("")) {
			throw new InvalidArgumentException(CATEGORY_ERROR_MESSAGE);
		}
		
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
}
