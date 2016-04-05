package view.model;


import javax.validation.constraints.Size;

import model.Currency;

public class AccountViewModel {
<<<<<<< HEAD
	private int id;
	
=======
	
	private int id;

>>>>>>> 17d4ea8ee292ae277a98bc8ad384950b343c6809
	@Size(min = 4, max = 15)
	private String title;
	
	
	private float balance;
	
	private Currency currency;
	
	public AccountViewModel() {}	

<<<<<<< HEAD
	public AccountViewModel(int id, String title, int balance) {
=======
	public AccountViewModel(int id, String title, int balance, Currency currency) {
>>>>>>> 17d4ea8ee292ae277a98bc8ad384950b343c6809
		this.id = id;
		this.title = title;
		this.balance = balance;
		this.currency = currency;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public float getBalance() {
		return balance;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	};
	
	
}
