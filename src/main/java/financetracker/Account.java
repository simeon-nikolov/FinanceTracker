package financetracker;

import java.util.ArrayList;
import java.util.List;

import exceptions.InvalidArgumentException;

public class Account implements IAccount {
	
	private static final String OBJECT_CAN_NOT_BE_NULL_ERROR_MESSAGE = "Object can not be null!";
	private static final String TITLE_ERROR_MESSAGE = "Invalid argument - string is null or empty!";
	private static final String ID_ERROR_MESSAGE = "ID can't be a negative number!";
	
	
	private int id;
	private String title;
	private int balance;	
	private List<Expenses> expenses;
	private List<Income> incomes;

	public Account() {}	
	

	public Account(int id, String title, int balance) throws InvalidArgumentException {
		
		this.setId(id);
		this.setTitle(title);
		this.setBalance(balance);
		this.expenses = new ArrayList<Expenses>();
		this.incomes = new ArrayList<Income>();
	}
	
	public void addExpense(Expenses expense) throws InvalidArgumentException {
		if (expense == null) {
			throw new InvalidArgumentException(OBJECT_CAN_NOT_BE_NULL_ERROR_MESSAGE);
		}
		this.expenses.add(expense);
	}
	
	public void addIncome(Income income) throws InvalidArgumentException {
		if (income == null) {
			throw new InvalidArgumentException(OBJECT_CAN_NOT_BE_NULL_ERROR_MESSAGE);
		}
		this.incomes.add(income);
	}
	
	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public int getBalance() {
		return balance;
	}

	public void setId(int id) throws InvalidArgumentException {
		if (id < 0) {
			throw new InvalidArgumentException(ID_ERROR_MESSAGE);
		}
		this.id = id;
	}

	public void setTitle(String title) throws InvalidArgumentException {
		if (title == null || title.isEmpty()) {
			throw new InvalidArgumentException(TITLE_ERROR_MESSAGE);
		}
		this.title = title;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	
	
	

}
