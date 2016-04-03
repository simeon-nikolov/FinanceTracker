package controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import view.model.UserViewModel;

@Controller
public class signUpController {

	@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public String signUP(Model model) {
		UserViewModel userViewModel = new UserViewModel();
		model.addAttribute("userViewModel", userViewModel);
		return "signUp";
	}

	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public String doLogin(@ModelAttribute("userViewModel") @Valid UserViewModel userViewModel, BindingResult result,
			Model model) {

		System.out.println(userViewModel.getUsername());
		if (result.hasErrors()) {			
			return "signUp";
		}
		return "index";
	}
}
