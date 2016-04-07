package controller;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import model.Account;
import model.Expense;
import model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dao.DAOException;
import dao.IAccountDAO;
import dao.IFinanceOperationDAO;
import dao.IUserDAO;

@RestController
public class AccountsRestController {
	
	@Autowired
	IAccountDAO accDao;
	
	@Autowired
	IFinanceOperationDAO foDao;
	
	@Autowired
	IUserDAO userDao;

	@RequestMapping(value="/accounts/{accountName}", method=RequestMethod.GET)
	public List<Expense> getExpensesForAccount(@PathVariable("accountName") String accountName, HttpSession session) {
		List<Expense> result = new LinkedList<Expense>();
		
		try {
			User user = getUserFromSession(session);
			int month = (int) session.getAttribute("month");
			int year = (int) session.getAttribute("year");
			
			if (accountName.equals("all")) {
				List<Account> accounts = (List<Account>) accDao.getAllAccountsForUser(user);
				
				for (Account acc : accounts) {					
					result.addAll(getExpensesByAccount(month, year, acc));					
				}
			} else {
				Account account = accDao.getAccountForUserByName(accountName, user);
				result = getExpensesByAccount(month, year, account);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Collections.sort(result, (e1, e2) -> e1.getDate().getDayOfMonth() - e2.getDate().getDayOfMonth());
		
		return result;
	}


	private List<Expense> getExpensesByAccount(int month, int year, Account acc) throws DAOException {
		List<Expense> result = new LinkedList<Expense>();
		List<Expense> accExpenses = (List<Expense>) foDao.getAllExpensesByAccount(acc);
		
		for (Expense expense : accExpenses) {
			if (expense.getDate().getMonthOfYear() == month && 
						expense.getDate().getYear() == year) {
				result.add(expense);
			}
		}
		
		return result;
	}
	
	private User getUserFromSession(HttpSession session) throws DAOException {
		String username = (String) session.getAttribute("username");
		if (username == null || username.isEmpty()) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			username = auth.getName();
			session.setAttribute("username", username);
		}
		User user = userDao.getUserByUsername(username);

		return user;
	}
}
