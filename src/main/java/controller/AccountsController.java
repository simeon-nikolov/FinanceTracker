package controller;

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

import dao.DAOException;
import dao.IAccountDAO;
import dao.IUserDAO;
import model.Account;
import model.User;
import utils.MoneyOperations;
import view.model.AccountViewModel;

@Controller
public class AccountsController {
	
	@Autowired
	IUserDAO userDao;
	@Autowired
	IAccountDAO accountDao;
	
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
			String username = (String) session.getAttribute("username");
			if (username == null || username.isEmpty()) {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				username = auth.getName();
				session.setAttribute("username", username);
			}
			
			User user = userDao.getUserByUsername(username);
			Account accForDB = new Account();
			accForDB.setTitle(accountViewModel.getTitle());
			int balance = MoneyOperations.moneyToCents((float)accountViewModel.getBalance());
			accForDB.setBalance(balance);
			accForDB.setUser(user);
			int id = accountDao.addAccount(accForDB);
			accForDB.setId(id);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/overview";
	}

}
