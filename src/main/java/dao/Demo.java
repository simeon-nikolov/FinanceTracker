package dao;

import model.Budget;


public class Demo {
	
	public static void main(String[] args) {
		BudgetDAO dao = new BudgetDAO();
		Budget budget = dao.getBudgetById(1);
		System.out.println(budget.getAmount());
		System.out.println(budget.getBeginDate());
		System.out.println(budget.getEndDate());
		System.out.println(budget.getBudgetType());
		System.out.println(budget.getRepeatType());
		System.out.println(budget.getCurrency());
		System.out.println(budget.getUser().getUsername());
	}

}
