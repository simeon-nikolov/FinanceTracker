package view.model;

import model.Currency;

public class ChangeCurrencyViewModel {
	private Currency currency;
	
	public ChangeCurrencyViewModel() {}

	public ChangeCurrencyViewModel(Currency currency) {
		this.currency = currency;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
}
