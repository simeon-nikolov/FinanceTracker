package controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import model.Currency;
import model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import view.model.ChangeCurrencyViewModel;
import view.model.ChangeEmailViewModel;
import view.model.ChangePasswordViewModel;
import dao.IUserDAO;
import exceptions.DAOException;

@Controller
public class ProfileController {
	@Autowired
	IUserDAO userDAO;

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String showProfilePage(HttpSession session, Model model) {
		try {
			User user = getUserFromSession(session);
			model.addAttribute("username", user.getUsername());
			model.addAttribute("email", user.getEmail());
			model.addAttribute("changeEmailViewModel", new ChangeEmailViewModel());
			model.addAttribute("changePasswordViewModel", new ChangePasswordViewModel());
			ChangeCurrencyViewModel changeCurrencyViewModel = new ChangeCurrencyViewModel();
			changeCurrencyViewModel.setCurrency(user.getCurrency());
			model.addAttribute("changeCurrencyViewModel", changeCurrencyViewModel);
			model.addAttribute("currencies", Currency.values());
		} catch (Exception e) {
			e.printStackTrace();
			return "forward:error";
		}

		return "profile";
	}

	@RequestMapping(value = "/changeCurrency", method = RequestMethod.POST)
	public String changeDefaultCurrency(@ModelAttribute("changeCurrencyViewModel") @Valid ChangeCurrencyViewModel changeCurrencyViewModel,
			BindingResult result, Model model, HttpSession session) {
		Currency oldCurrency = null;
		User user = null;

		try {
			user = getUserFromSession(session);
			oldCurrency = user.getCurrency();
			user.setCurrency(changeCurrencyViewModel.getCurrency());
			userDAO.updateUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:error";
		}

		return "redirect:profile?changeCurrency&c1=" + oldCurrency.name() + "&c2=" + user.getCurrency();
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePassword(@ModelAttribute("changePasswordViewModel") @Valid ChangePasswordViewModel changePasswordViewModel,
			BindingResult result, Model model, HttpSession session) {
		try {
			User user = getUserFromSession(session);
			String oldPassword = changePasswordViewModel.getOldPassword();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			if (encoder.matches(oldPassword, user.getPassword())) {
				if (!changePasswordViewModel.getNewPassword().equals(changePasswordViewModel.getConfirmNewPassword())) {
					return "redirect:profile?passwordsMatchError";
				}
				
				String password = encoder.encode(changePasswordViewModel.getNewPassword());
				user.setPassword(password);
				userDAO.updateUser(user);
			} else {
				return "redirect:profile?passwordIncorrect";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:error";
		}

		return "redirect:profile?changePassowrd";
	}

	@RequestMapping(value = "/changeEmail", method = RequestMethod.POST)
	public String changeEmail(@ModelAttribute("changeEmailViewModel") @Valid ChangeEmailViewModel changeEmailViewModel,
			BindingResult result, Model model, HttpSession session) {
		try {
			User user = getUserFromSession(session);
			String password = changeEmailViewModel.getEnterPassword();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			
			if (result.hasErrors()) {
				model.addAttribute("username", user.getUsername());
				model.addAttribute("email", user.getEmail());
				model.addAttribute("changeEmailViewModel", new ChangeEmailViewModel());
				model.addAttribute("changePasswordViewModel", new ChangePasswordViewModel());
				ChangeCurrencyViewModel changeCurrencyViewModel = new ChangeCurrencyViewModel();
				changeCurrencyViewModel.setCurrency(user.getCurrency());
				model.addAttribute("changeCurrencyViewModel", changeCurrencyViewModel);
				model.addAttribute("currencies", Currency.values());
				return "profile";
			}

			if (encoder.matches(password, user.getPassword())) {
				if (!changeEmailViewModel.getNewEmail().equals(changeEmailViewModel.getConfirmNewEmail())) {
					return "redirect:profile?emailMatchError";
				}

				String email = changeEmailViewModel.getNewEmail();
				user.setEmail(email);
				userDAO.updateUser(user);
			} else {
				return "redirect:profile?passwordEmailIncorrect";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:error";
		}

		return "redirect:profile?changeEmail";
	}

	private User getUserFromSession(HttpSession session) throws DAOException {
		String username = (String) session.getAttribute("username");

		if (username == null || username.isEmpty()) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			username = auth.getName();
			session.setAttribute("username", username);
		}

		User user = userDAO.getUserByUsername(username);

		return user;
	}
}
