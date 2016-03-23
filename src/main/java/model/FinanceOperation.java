package model;

import java.time.LocalDate;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import exceptions.InvalidArgumentException;

@Entity
@Table(name="finance_operations")
public abstract class FinanceOperation implements IFinanceOperation {
	private static final String DATE_ERROR_MESSAGE = "Date is null!";
	private static final String DESCRIPTION_ERROR_MESSAGE = "The description is null or empty string!";
	private static final String ACCOUNT_ERROR_MESSAGE = "Account is null!";
	private static final String CATEGORY_ERROR_MESSAGE = "Category is null or is empty string!";
	private static final String MONEY_AMOUNT_ERROR_MESSAGE = "Money amount can't be a negative value!";
	private static final String ID_ERROR_MESSAGE = "ID can't be a negative number!";
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column
	private int amount;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="currency_id")
	private Currency currency;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="category_id")
	private String category;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="account_id")
	private Account account;
	
	@Column
	private LocalDate date;
	
	@Column
	private String description;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="repeat_type_id")
	private RepeatType repeatType;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="finance_operatio_type_id")
	private String type;
	
	public FinanceOperation() {}

	public FinanceOperation(int id, int amount, Currency currency, String category, 
			Account account, LocalDate date, String description, RepeatType repeatType) 
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

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) throws InvalidArgumentException {
	if (id < 0) {
		throw new InvalidArgumentException(ID_ERROR_MESSAGE);
	}
			
		this.id = id;
	}

	@Override
	public int getAmount() {
		
		
		return amount;
	}

	@Override
	public void setAmount(int amount) throws InvalidArgumentException {
		if (amount < 0) {
			throw new InvalidArgumentException(MONEY_AMOUNT_ERROR_MESSAGE);
		}
		
		this.amount = amount;
	}

	@Override
	@Enumerated(EnumType.STRING)
	public Currency getCurrency() {
		return currency;
	}

	@Override
	@Enumerated(EnumType.STRING)
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	@Override
	public String getCategory() {
		return category;
	}

	@Override
	public void setCategory(String category) throws InvalidArgumentException {
		if (category == null || category.trim().equals("")) {
			throw new InvalidArgumentException(CATEGORY_ERROR_MESSAGE);
		}
		
		this.category = category;
	}

	@Override
	public Account getAccount() {
		return account;
	}

	@Override
	public void setAccount(Account account) throws InvalidArgumentException {
		if (account == null) {
			throw new InvalidArgumentException(ACCOUNT_ERROR_MESSAGE);
		}
		
		this.account = account;
	}

	@Override
	public LocalDate getDate() {
		return date;
	}

	@Override
	public void setDate(LocalDate date) throws InvalidArgumentException {
		if (date == null) {
			throw new InvalidArgumentException(DATE_ERROR_MESSAGE);
		}
		
		this.date = date;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) throws InvalidArgumentException {
		if (description == null || description.trim().equals("")) {
			throw new InvalidArgumentException(DESCRIPTION_ERROR_MESSAGE);
		}
		
		this.description = description;
	}

	@Override
	@Enumerated(EnumType.STRING)
	public RepeatType getRepeatType() {
		return repeatType;
	}

	@Override
	@Enumerated(EnumType.STRING)
	public void setRepeatType(RepeatType repeatType) {
		this.repeatType = repeatType;
	}
	
	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}
}
