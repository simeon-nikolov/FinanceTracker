package controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import dao.DAOException;
import dao.IAccountDAO;
import dao.IUserDAO;
import exceptions.DuplicateUserException;
import exceptions.InvalidArgumentException;
import model.Account;
import model.Currency;
import model.User;
import view.model.UserViewModel;

@Controller
public class SignUpController {

	private final static String DEFAULT_STARTING_ACCOUNT_NAME = "Cash";
	private final static Currency DEFAULT_CURRENCY = Currency.BGN;
	private final static int DEFAULT_STARTING_ACCOUNT_BALANCE = 0;

	@Autowired
	IUserDAO userDao;
	@Autowired
	IAccountDAO accountDao;

	@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public String showSignUpPage(Model model) {
		UserViewModel userViewModel = new UserViewModel();
		model.addAttribute("userViewModel", userViewModel);
		return "signUp";
	}

	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public String signUp(@ModelAttribute("userViewModel") @Valid UserViewModel userViewModel, BindingResult result,
			Model model) {

		System.out.println(userViewModel.getUsername());
		if (result.hasErrors()) {
			return "signUp";
		}

		if (!userViewModel.getPassword().equals(userViewModel.getConfirmPassword())) {
			model.addAttribute("errorMessage", "Both passwords must be the same!");
			return "signUp";
		}
		if (!checkIsPassStrongEnough(userViewModel.getPassword())) {
			model.addAttribute("passNotStrong", "Your password must contain at least 1 upper case, 1 lower case and 1 digit!");
			return "signUp";
		}

		User userForDB = new User();
		String hashedPassword = new BCryptPasswordEncoder().encode(userViewModel.getPassword());

		try {
			userForDB.setUsername(userViewModel.getUsername());
			userForDB.setPassword(hashedPassword);
			userForDB.setEmail(userViewModel.getEmail());
			userForDB.setCurrency(DEFAULT_CURRENCY);

			int userID = userDao.addUser(userForDB);
			userForDB.setId(userID);
		} catch (DuplicateUserException e) {
			model.addAttribute("signUpError", e.getMessage());			
			e.printStackTrace();
			return "signUp";			
		} catch (Exception e) {
			model.addAttribute("signUpError", e.getMessage());
			e.printStackTrace();
			return "signUp";
		}
		Account defaultUserAccount = new Account();

		try {
			defaultUserAccount.setUser(userForDB);
			defaultUserAccount.setTitle(DEFAULT_STARTING_ACCOUNT_NAME);
			defaultUserAccount.setBalance(DEFAULT_STARTING_ACCOUNT_BALANCE);

			accountDao.addAccount(defaultUserAccount);		
		} catch (Exception e) {
			model.addAttribute("signUpError", e.getMessage());
			e.printStackTrace();
			return "signUp";
		}

		return "login";
	}

	private boolean checkIsPassStrongEnough(String pass) {
		if (pass != null) {
			boolean hasUppercase = !pass.equals(pass.toLowerCase());
			boolean hasLowercase = !pass.equals(pass.toUpperCase());
			boolean hasDigit = pass.matches(".*\\d+.*");
			if (hasDigit && hasLowercase && hasUppercase) {
				return true;
			}			
		}
		return false;
	}
}
