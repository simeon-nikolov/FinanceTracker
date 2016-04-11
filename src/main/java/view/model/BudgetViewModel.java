package view.model;

import javax.validation.constraints.NotNull;

import model.BudgetType;
import model.Currency;
import model.RepeatType;

import org.joda.time.LocalDate;

public class BudgetViewModel {
	private int id;

	@NotNull
	private BudgetType budgetType;

	private RepeatType repeatType;

	private LocalDate beginDate;

	private LocalDate endDate;

	private float amount;

	@NotNull
	private Currency currency;

	public BudgetViewModel() {}

	public BudgetViewModel(int id, BudgetType budgetType, RepeatType repeatType, LocalDate beginDate, LocalDate endDate,
			float amount, Currency currency) {
		this.id = id;
		this.budgetType = budgetType;
		this.repeatType = repeatType;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.amount = amount;
		this.currency = currency;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
}
