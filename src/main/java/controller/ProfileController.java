package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dao.IUserDAO;

@Controller
public class ProfileController {
	
	@Autowired
	IUserDAO userDao;
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String showProfilePage(Model model) {
		
		
		return "profile";
	}

}
