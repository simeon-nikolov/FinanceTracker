package model;

import java.time.LocalDate;

import exceptions.InvalidArgumentException;

public class Budget implements IBudget {
	private static final String END_DATE_ERROR_MESSAGE = "End date is null!";
	private static final String BEGIN_DATE_ERROR_MESSAGE = "Begin date is null!";
	private static final String BUGHET_AMOUNT_ERROR_MESSAGE = "Bughet amount can't be negative value!";
	private static final String ID_ERROR_MESSAGE = "ID can't be a negative number!";
	
	private int id;
	private BudgetType budgetType;
	private RepeatType repeatType;
	private LocalDate beginDate;
	private LocalDate endDate;
	private int amount;
	private Currency currency;
	
	public Budget() {}
	
	public Budget(int id, BudgetType budgetType, RepeatType repeatType, 
			LocalDate beginDate, LocalDate endDate, int amount, Currency currency) 
					throws InvalidArgumentException {
		this.setId(id);
		this.setBudgetType(budgetType);
		this.setRepeatType(repeatType);
		this.setBeginDate(beginDate);
		this.setEndDate(endDate);
		this.setAmount(amount);
		this.setCurrency(currency);
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
	public BudgetType getBudgetType() {
		return budgetType;
	}

	@Override
	public void setBudgetType(BudgetType budgetType) {
		this.budgetType = budgetType;
	}

	@Override
	public RepeatType getRepeatType() {
		return repeatType;
	}

	@Override
	public void setRepeatType(RepeatType repeatType) {
		this.repeatType = repeatType;
	}

	@Override
	public LocalDate getBeginDate() {
		return beginDate;
	}

	@Override
	public void setBeginDate(LocalDate beginDate) throws InvalidArgumentException {
		if (beginDate == null) {
			throw new InvalidArgumentException(BEGIN_DATE_ERROR_MESSAGE);
		}
		
		this.beginDate = beginDate;
	}

	@Override
	public LocalDate getEndDate() {
		return endDate;
	}

	@Override
	public void setEndDate(LocalDate endDate) throws InvalidArgumentException {
		if (endDate == null) {
			throw new InvalidArgumentException(END_DATE_ERROR_MESSAGE);
		}
		
		this.endDate = endDate;
	}

	@Override
	public int getAmount() {
		return amount;
	}

	@Override
	public void setAmount(int amount) throws InvalidArgumentException {
		if (amount < 0) {
			throw new InvalidArgumentException(BUGHET_AMOUNT_ERROR_MESSAGE);
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
}
