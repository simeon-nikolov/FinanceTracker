package controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MonthChangerController {
	
	@RequestMapping(value = "/nextMonth", method = RequestMethod.GET)
	public String setNextMonth(@ModelAttribute(value="view") String view, 
			HttpSession session, Model model) {
		int currentMonth = (int) session.getAttribute("month");
		currentMonth++;
		if (currentMonth == 13) {
			currentMonth = 1;
			int year = (int)session.getAttribute("year");
			year++;
			session.setAttribute("year", year);
		}
		session.setAttribute("month", currentMonth);
		return "redirect:"+view;
	}
	
	@RequestMapping(value = "/previousMonth", method = RequestMethod.GET)
	public String setPreviousMonth(@ModelAttribute(value="view") String view, 
			HttpSession session, Model model) {
		int currentMonth = (int) session.getAttribute("month");
		currentMonth--;
		if (currentMonth == 0) {
			currentMonth = 12;
			int year = (int)session.getAttribute("year");
			year--;
			session.setAttribute("year", year);
		}
		session.setAttribute("month", currentMonth);
		return "redirect:"+view;
	}

}
