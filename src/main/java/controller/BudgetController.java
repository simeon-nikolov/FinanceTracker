package controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dao.DAOException;
import dao.IBudgetDAO;
import dao.IUserDAO;
import model.Budget;
import model.User;
import utils.MoneyOperations;
import view.model.BudgetViewModel;

@Controller
public class BudgetController {
	@Autowired
	private IBudgetDAO budgetDAO;
	@Autowired
	private IUserDAO userDAO;
	
	@RequestMapping(value = "/allBudgets", method = RequestMethod.GET)
	public String showBudgetsPage(HttpSession session, Model model) {
		try {
			User user = getUserFromSession(session);
			List<Budget> budgets = (List<Budget>) budgetDAO.getAllBudgetsByUser(user);
			List<BudgetViewModel> budgetsViewModel = budgetsTobudgetsViewModel(budgets);
			model.addAttribute("allBudgets", budgetsViewModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "allBudgets";
	}

	private List<BudgetViewModel> budgetsTobudgetsViewModel(List<Budget> budgets) {
		List<BudgetViewModel> budgetsViewModel = new LinkedList<BudgetViewModel>();
		
		if (budgets != null) {
			for (Budget budget : budgets) {
				BudgetViewModel budgetViewModel = budgetToBudgetViewModel(budget);
				budgetsViewModel.add(budgetViewModel);
			}
		}
		
		return budgetsViewModel;
	}

	private BudgetViewModel budgetToBudgetViewModel(Budget budget) {
		BudgetViewModel budgetViewModel = new BudgetViewModel();
		
		if (budget != null) {
			budgetViewModel.setId(budget.getId());
			budgetViewModel.setAmount(MoneyOperations.amountPerHendred(budget.getAmount()));
			budgetViewModel.setCurrency(budget.getCurrency());
			budgetViewModel.setBeginDate(budget.getBeginDate());
			budgetViewModel.setEndDate(budget.getEndDate());
			budgetViewModel.setBudgetType(budget.getBudgetType());
			budgetViewModel.setRepeatType(budget.getRepeatType());
		}
		
		return budgetViewModel;
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
