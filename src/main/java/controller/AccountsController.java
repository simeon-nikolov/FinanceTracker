package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dao.IAccountDAO;
import dao.IFinanceOperationDAO;
import dao.IUserDAO;
import exceptions.DAOException;
import model.Account;
import model.Expense;
import model.Income;
import model.User;
import utils.MoneyOperations;
import view.model.AccountViewModel;

@Controller
public class AccountsController {
	
	@Autowired
	IUserDAO userDao;
	@Autowired
	IAccountDAO accountDao;
	@Autowired
	IFinanceOperationDAO foDao;
	
	@RequestMapping(value = "/addAccount", method = RequestMethod.GET)
	public String showAddAccountPage(Model model) {
		AccountViewModel accountViewModel = new AccountViewModel();
		model.addAttribute("accountViewModel", accountViewModel);
		return "addAccount";
	}
	
	@RequestMapping(value = "/addAccount", method = RequestMethod.POST)
	public String addAccount(@ModelAttribute("accountViewModel") @Valid AccountViewModel accountViewModel, BindingResult result,
			Model model, HttpSession session) {		
		
		if (result.hasErrors()) {
			return "addAccount";
		}
		try {			
			User user = getUserFromSession(session);
			Account accForDB = new Account();
			accForDB.setTitle(accountViewModel.getTitle());
			int balance = MoneyOperations.moneyToCents((float)accountViewModel.getBalance());
			accForDB.setBalance(balance);
			accForDB.setUser(user);
			int id = accountDao.addAccount(accForDB);
			accForDB.setId(id);
		
		} catch (Exception e) {
			e.printStackTrace();
			return "forward:error";
		}
		
		return "redirect:/allAccounts";
	}
	
	@RequestMapping(value = "/allAccounts", method = RequestMethod.GET)
	public String showAllAccountsPage(Model model, HttpSession session) {
		
		try {
			User user = getUserFromSession(session);
			List<Account> accounts = (List<Account>) accountDao.getAllAccountsForUser(user);
			List<AccountViewModel> viewModelAccounts = new ArrayList<AccountViewModel>();
						
			for (Account acc : accounts) {
				AccountViewModel temp = new AccountViewModel();	
				int balance = acc.getBalance();
				balance = addAllIncomesToAccount(balance, acc);
				balance = substractAllExpensesFromAccount(balance, acc);
				
				temp.setTitle(acc.getTitle());
				temp.setId(acc.getId());
				temp.setCurrency(acc.getUser().getCurrency());
				temp.setBalance(MoneyOperations.amountPerHendred(balance));
				viewModelAccounts.add(temp);
			}
			model.addAttribute("accounts", viewModelAccounts);			
			
		} catch (Exception e) {
			e.printStackTrace();
			return "forward:error";
		}
		
		return "allAccounts";
	}
	
	@RequestMapping(value = "/verifyDeleteAccount", method = RequestMethod.GET)
	public String verifyDeleteAccount(@ModelAttribute(value="id") int id, Model model, HttpSession session) {
		try {			
			Account acc = accountDao.getAccountById(id);
			model.addAttribute("accountTitle", acc.getTitle());
			model.addAttribute("accountId", id);
		} catch (Exception e) {			
			e.printStackTrace();
			return "forward:error";
		}
		return "verifyDeleteAccount";
		
	}
	
	@RequestMapping(value = "/deleteAccount", method = RequestMethod.GET)
	public String deleteAccount(@ModelAttribute(value="id") int id, Model model, HttpSession session) {
		try {
			User user = getUserFromSession(session);
			Account account = accountDao.getAccountById(id);
			if (accountDao.checkUserHasAccount(account, user)) {
				deleteAllExpensesByAccount(account);
				deleteAllIncomesByAccount(account);
				
				accountDao.deleteAccount(account);
			}
			else {
				throw new Exception("Invalid account!");
			}			
		} catch (Exception e) {
			e.printStackTrace();
			return "forward:error";
		}
		
		return "redirect:/allAccounts";
	}
	
	@RequestMapping(value = "/editAccount", method = RequestMethod.GET)
	public String showEditAccountPage(@ModelAttribute(value="id") int id, Model model, HttpSession session) {
		try {
			User user = getUserFromSession(session);
			Account account = accountDao.getAccountById(id);
			
			if (accountDao.checkUserHasAccount(account, user)) {
				AccountViewModel accountViewModel = new AccountViewModel();
				accountViewModel.setTitle(account.getTitle());
				accountViewModel.setBalance(MoneyOperations.amountPerHendred(account.getBalance()));
				
				model.addAttribute("accountViewModel", accountViewModel);
				session.setAttribute("editAccountId", id);
			}
			else {
				throw new Exception("Invalid account to delete!");
			}			
		} catch (Exception e) {
			e.printStackTrace();			
			return "forward:error";
		}
		
		return "editAccount";
	}
	
	@RequestMapping(value = "/editAccount", method = RequestMethod.POST)
	public String editAccount(@ModelAttribute("accountViewModel") @Valid AccountViewModel accountViewModel, BindingResult result,
			Model model, HttpSession session) {
		try {
			User user = getUserFromSession(session);
			int id = (int) session.getAttribute("editAccountId");
			Account account = accountDao.getAccountById(id);
			
			if (accountDao.checkUserHasAccount(account, user)) {
				account.setBalance(MoneyOperations.moneyToCents(accountViewModel.getBalance()));
				account.setTitle(accountViewModel.getTitle());
				
				accountDao.updateAccount(account);
			}
			else {
				throw new Exception("Invalid account to delete!");
			}				
		} catch (Exception e) {
			e.printStackTrace();
			return "forward:error";
		}
		
		return "redirect:/allAccounts";
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
	
	private int substractAllExpensesFromAccount(int balance, Account account) throws DAOException {
		List<Expense> allExpenses = (List<Expense>) foDao.getAllExpensesByAccount(account);
		for (Expense e : allExpenses) {
			balance -= e.getAmount();			
		}
		
		return balance;
	}
	
	private int addAllIncomesToAccount(int balance, Account account) throws DAOException {
		List<Income> allIncomes = (List<Income>) foDao.getAllIncomesByAccount(account);
		for (Income income : allIncomes) {
			balance += income.getAmount();
		}			
		
		return balance;
	}
	
	private void deleteAllExpensesByAccount(Account acc) throws DAOException {
		List<Expense> result = (List<Expense>) foDao.getAllExpensesByAccount(acc);
		if (result != null) {
			for (Expense e : result) {
				foDao.delete(e);
			}
		}
	}
	
	private void deleteAllIncomesByAccount(Account acc) throws DAOException {
		List<Income> result = (List<Income>) foDao.getAllIncomesByAccount(acc);
		if (result != null) {
			for (Income i : result) {
				foDao.delete(i);
			}
		}
		
	}

}
