package controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

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
			List<Account> accounts = (List<Account>) accountDAO.getAllAccountsForUser(user);
			List<Expense> expenses = new LinkedList<Expense>();
			int amountToSpend = 0;
			
			for (Account acc : accounts) {
				List<Expense> accExpenses = (List<Expense>) financeOperationDAO.getAllExpensesByAccount(acc);
				expenses.addAll(accExpenses);
				amountToSpend += acc.getBalance();
				
				for (Expense expense : accExpenses) {
					amountToSpend -= expense.getAmount();
				}
			}
			
			float moneyToSpend = MoneyOperations.amountPerHendred(amountToSpend);
			model.addAttribute("expenses", expenses);
			model.addAttribute("moneyToSpend", moneyToSpend);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return "overview";
	}
}
