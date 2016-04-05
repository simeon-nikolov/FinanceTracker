package view.model;


import javax.validation.constraints.Size;

public class AccountViewModel {
	private int id;
	
	@Size(min = 4, max = 15)
	private String title;
	
	
	private float balance;
	
	public AccountViewModel() {}	

	public AccountViewModel(int id, String title, int balance) {
		this.id = id;
		this.title = title;
		this.balance = balance;
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
	};
	
	
}
