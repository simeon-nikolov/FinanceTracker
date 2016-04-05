package view.model;


import javax.validation.constraints.Size;

public class AccountViewModel {

	@Size(min = 4, max = 15)
	private String title;
	
	
	private float balance;
	
	public AccountViewModel() {}	

	public AccountViewModel(String title, int balance) {		
		this.title = title;
		this.balance = balance;
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
	};
	
	
}
