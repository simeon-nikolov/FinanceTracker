package controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import utils.MoneyOperations;

@Controller
public class OverviewController {
	
	@Autowired
	private IFinanceOperationDAO financeOperationDAO;
	@Autowired
	private IAccountDAO accountDAO;
	@Autowired
	private IUserDAO userDAO;

	@RequestMapping(value = "/overview", method = RequestMethod.GET)
	public String showOverview(Model model, HttpSession session) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String username = auth.getName();
			session.setAttribute("username", username);
			
			User user = userDAO.getUserByUsername(username);
			int month = LocalDate.now().getMonthOfYear();
			int year = LocalDate.now().getYear();
			List<Account> accounts = (List<Account>) accountDAO.getAllAccountsForUser(user);
			List<Expense> expenses = new LinkedList<Expense>();
			Set<String> categories = new HashSet<String>();
			Map<String, Integer> moneyByCategory = new HashMap<String, Integer>();
			int amountToSpend = 0;
			
			for (Account acc : accounts) {
				List<Expense> accExpenses = (List<Expense>) financeOperationDAO.getAllExpensesByAccount(acc);
				for (Expense expense : accExpenses) {
					if (expense.getDate().getMonthOfYear() == month && 
								expense.getDate().getYear() == year) {
						expenses.add(expense);
					}
				}
				amountToSpend += acc.getBalance();
				
				for (Expense expense : expenses) {
					amountToSpend -= expense.getAmount();
					String category = "'" + expense.getCategory().getCategoryName() + "'";
					categories.add(category);
					int oldAmount = 0;
					
					if (moneyByCategory.containsKey(category)) {
						oldAmount = moneyByCategory.get(category);
					}
					
					moneyByCategory.put(category, oldAmount + expense.getAmount());
				}
			}
			
			float moneyToSpend = MoneyOperations.amountPerHendred(amountToSpend);
			model.addAttribute("expenses", expenses);
			model.addAttribute("moneyToSpend", moneyToSpend);
			model.addAttribute("categories", moneyByCategory.keySet());
			model.addAttribute("money", moneyByCategory.values());
			
			
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return "overview";
	}
}
