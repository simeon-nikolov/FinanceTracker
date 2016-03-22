package model;

import java.time.LocalDate;

import exceptions.InvalidArgumentException;

public abstract class FinanceOperation implements IFinanceOperation {
	private static final String DATE_ERROR_MESSAGE = "Date is null!";
	private static final String DESCRIPTION_ERROR_MESSAGE = "The description is null or empty string!";
	private static final String ACCOUNT_ERROR_MESSAGE = "Account is null!";
	private static final String CATEGORY_ERROR_MESSAGE = "Category is null or is empty string!";
	private static final String MONEY_AMOUNT_ERROR_MESSAGE = "Money amount can't be a negative value!";
	private static final String ID_ERROR_MESSAGE = "ID can't be a negative number!";
	
	private int id;
	private int amount;
	private Currency currency;
	private String category;
	private IAccount account;
	private LocalDate date;
	private String description;
	private RepeatType repeatType;
	
	public FinanceOperation() {}

	public FinanceOperation(int id, int amount, Currency currency, String category, 
			IAccount account, LocalDate date, String description, RepeatType repeatType) 
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
	public Currency getCurrency() {
		return currency;
	}

	@Override
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
	public IAccount getAccount() {
		return account;
	}

	@Override
	public void setAccount(IAccount account) throws InvalidArgumentException {
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
	public RepeatType getRepeatType() {
		return repeatType;
	}

	@Override
	public void setRepeatType(RepeatType repeatType) {
		this.repeatType = repeatType;
	}
}
