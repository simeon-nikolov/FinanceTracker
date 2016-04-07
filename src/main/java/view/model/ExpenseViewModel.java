package view.model;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import model.Currency;
import model.RepeatType;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;

public class ExpenseViewModel {
	private int id;
	
	@NotNull
	private float amount;
	
	@NotNull
	private float userCurrencyAmount;

	@NotNull
	private Currency currency;

	@NotNull
	@NotEmpty
	private String category;
	
	@NotNull
	@NotEmpty
	private String account;

	private LocalDate date;
	
	@NotNull
	@NotEmpty
	private String description;

	@NotNull
	private RepeatType repeatType;

	@NotNull
	private Collection<String> tags;

	public ExpenseViewModel() {}

	public ExpenseViewModel(int id, float amount, Currency currency, String category, String account, LocalDate date, String description,
			RepeatType repeatType, Collection<String> tags) {
		this.id = id;
		this.amount = amount;
		this.currency = currency;
		this.category = category;
		this.account = account;
		this.date = date;
		this.description = description;
		this.repeatType = repeatType;
		this.tags = tags;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RepeatType getRepeatType() {
		return repeatType;
	}

	public void setRepeatType(RepeatType repeatType) {
		this.repeatType = repeatType;
	}

	public Collection<String> getTags() {
		return tags;
	}

	public void setTags(Collection<String> tags) {
		this.tags = tags;
	}

	public float getUserCurrencyAmount() {
		return userCurrencyAmount;
	}

	public void setUserCurrencyAmount(float userCurrencyAmount) {
		this.userCurrencyAmount = userCurrencyAmount;
	}
}
