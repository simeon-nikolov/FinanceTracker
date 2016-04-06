package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import exceptions.InvalidArgumentException;

@Entity
@Table(name="budgets")
public class Budget {
	private static final String BUGHET_AMOUNT_ERROR_MESSAGE = "Bughet amount can't be negative value!";
	private static final String ID_ERROR_MESSAGE = "ID can't be a negative number!";
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="budget_type")
	@Enumerated(EnumType.STRING)
	private BudgetType budgetType;
	
	@Column(name="repeat_type")
	@Enumerated(EnumType.STRING)
	private RepeatType repeatType;
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name="begin_date")
	private LocalDate beginDate;
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name="end_date")
	private LocalDate endDate;
	
	@Column
	private int amount;
	
	@Column(name="currency")
	@Enumerated(EnumType.STRING)
	private Currency currency;
	
	@ManyToOne
	private User user;
	
	public Budget() {}
	
	public Budget(int id, BudgetType budgetType, RepeatType repeatType, 
			LocalDate beginDate, LocalDate endDate, int amount, Currency currency, User user) 
					throws InvalidArgumentException {
		this.setId(id);
		this.setBudgetType(budgetType);
		this.setRepeatType(repeatType);
		this.setBeginDate(beginDate);
		this.setEndDate(endDate);
		this.setAmount(amount);
		this.setCurrency(currency);
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

	public BudgetType getBudgetType() {
		return budgetType;
	}

	public void setBudgetType(BudgetType budgetType) {
		this.budgetType = budgetType;
	}

	public RepeatType getRepeatType() {
		return repeatType;
	}

	public void setRepeatType(RepeatType repeatType) {
		this.repeatType = repeatType;
	}

	public LocalDate getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(LocalDate beginDate) {
		this.beginDate = beginDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) throws InvalidArgumentException {
		if (amount < 0) {
			throw new InvalidArgumentException(BUGHET_AMOUNT_ERROR_MESSAGE);
		}
		
		this.amount = amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
