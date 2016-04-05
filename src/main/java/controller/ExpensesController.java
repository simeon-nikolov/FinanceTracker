package controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dao.DAOException;
import dao.IAccountDAO;
import dao.IFinanceOperationDAO;
import dao.IUserDAO;
import model.Account;
import model.Expense;
import model.User;

@Controller
public class ExpensesController {
	@Autowired
	private IFinanceOperationDAO financeOperationDAO;
	@Autowired
	private IUserDAO userDAO;
	@Autowired
	private IAccountDAO accountDAO;
	
	@RequestMapping(value = "/allExpenses", method = RequestMethod.GET)
	public String showAllExpenses(HttpSession session, Model model) {
		try {
			String username = (String) session.getAttribute("username");
			User user = userDAO.getUserByUsername(username);
			List<Account> accounts = (List<Account>) accountDAO.getAllAccountsForUser(user);
			Map<String, Integer> amountsByCategory = new HashMap<String, Integer>();
			List<Expense> expenses = new LinkedList<Expense>();
			int month = LocalDate.now().getMonthOfYear();
			int year = LocalDate.now().getYear();
			
			if (session.getAttribute("month") != null) {
				month = (int) session.getAttribute("month");
			}
			
			if (session.getAttribute("year") != null) {
				year = (int) session.getAttribute("year");
			}
			
			for (Account acc : accounts) {
				List<Expense> accExpenses = (List<Expense>) financeOperationDAO.getAllExpensesByAccount(acc);
				
				for (Expense expense : accExpenses) {
					if (expense.getDate().getMonthOfYear() == month && 
								expense.getDate().getYear() == year) {
						expenses.add(expense);
					}
				}
				
				for (Expense expense : expenses) {
					String category = "'" + expense.getCategory().getCategoryName() + "'";
					int oldAmount = 0;
					
					if (amountsByCategory.containsKey(category)) {
						oldAmount = amountsByCategory.get(category);
					}
					
					amountsByCategory.put(category, oldAmount + expense.getAmount());
				}
			}
			
			model.addAttribute("categories", amountsByCategory.keySet());
			model.addAttribute("expensesAmounts", amountsByCategory.values());
			model.addAttribute("expenses", expenses);
			model.addAttribute("accounts", accounts);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return "expenses";
	}
	
	@RequestMapping(value = "/addExpense", method = RequestMethod.GET)
	public String showAddExpensePage() {
		
		return "addExpense";
	}
}
