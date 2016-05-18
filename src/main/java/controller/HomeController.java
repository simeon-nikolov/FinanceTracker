package controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value={"/index", "/"})
public class HomeController {

	@RequestMapping(method=RequestMethod.GET)
	public String showIndex(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean isLoggedIn = auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);

		if (isLoggedIn) {
			return "redirect:/overview";
		}

		return "index";
	}
}
