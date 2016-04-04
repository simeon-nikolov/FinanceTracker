package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dao.IUserDAO;
import model.User;


@Controller
public class TestController {

	@Autowired
	IUserDAO dao;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(Model model) {
//		User user = dao.getUserById(138);
//		String hashed = user.getPassword();
//		
//		//System.out.println(BCrypt.checkpw("qwerty", hashed));
		
		return "headerAuthenticated";
	}	
}
