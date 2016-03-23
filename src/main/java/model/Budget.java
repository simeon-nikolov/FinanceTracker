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

import org.springframework.security.core.userdetails.User;

import exceptions.InvalidArgumentException;

@Entity
@Table(name="budgets")
public class Budget implements IBudget {
	private static final String END_DATE_ERROR_MESSAGE = "End date is null!";
	private static final String BEGIN_DATE_ERROR_MESSAGE = "Begin date is null!";
	private static final String BUGHET_AMOUNT_ERROR_MESSAGE = "Bughet amount can't be negative value!";
	private static final String ID_ERROR_MESSAGE = "ID can't be a negative number!";
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne(cascade=CascadeType.ALL, 
			fetch = FetchType.EAGER)
	@JoinColumn(name="budget_type_id")
	private BudgetType budgetType;
	
	@ManyToOne(cascade=CascadeType.ALL, 
			fetch = FetchType.EAGER)
	@JoinColumn(name="repeat_type_id")
	private RepeatType repeatType;
	
	@Column(name="begin_date")
	private LocalDate beginDate;
	
	@Column(name="end_date")
	private LocalDate endDate;
	
	@Column
	private int amount;
	
	@ManyToOne(cascade=CascadeType.ALL, 
			fetch = FetchType.EAGER)
	@JoinColumn(name="currency_id")
	private Currency currency;
	
	@ManyToOne(cascade=CascadeType.ALL, 
			fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
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
	@Enumerated(EnumType.STRING)
	public BudgetType getBudgetType() {
		return budgetType;
	}

	@Override
	@Enumerated(EnumType.STRING)
	public void setBudgetType(BudgetType budgetType) {
		this.budgetType = budgetType;
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
	@Enumerated(EnumType.STRING)
	public Currency getCurrency() {
		return currency;
	}

	@Override
	@Enumerated(EnumType.STRING)
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
