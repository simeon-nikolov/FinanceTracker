package view.model;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;

import model.Currency;
import model.RepeatType;

public class IncomeViewModel {

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
	
	public IncomeViewModel() {}

	public IncomeViewModel(int id, float amount, Currency currency, String category, String account, LocalDate date,
			String description, RepeatType repeatType, Collection<String> tags) {
		super();
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

	public float getAmount() {
		return amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public String getCategory() {
		return category;
	}

	public String getAccount() {
		return account;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public RepeatType getRepeatType() {
		return repeatType;
	}

	public Collection<String> getTags() {
		return tags;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setRepeatType(RepeatType repeatType) {
		this.repeatType = repeatType;
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
