package controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import model.Budget;
import model.BudgetType;
import model.Currency;
import model.RepeatType;
import model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import utils.CurrencyChange;
import utils.MoneyOperations;
import view.model.BudgetViewModel;
import dao.DAOException;
import dao.IBudgetDAO;
import dao.IUserDAO;
import exceptions.APIException;

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
	
	@RequestMapping(value = "/addBudget", method = RequestMethod.GET)
	public String showAddBudgetPage(Model model) {
		try {
			model.addAttribute("currenicies", Currency.values());
			model.addAttribute("budgetTypes", BudgetType.values());
			model.addAttribute("repeatTypes", RepeatType.values());
			model.addAttribute("budgetViewModel", new BudgetViewModel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "addBudget";
	}
	
	@RequestMapping(value = "/addBudget", method = RequestMethod.POST)
	public String addBudget(@ModelAttribute("budgetViewModel") @Valid BudgetViewModel budgetViewModel,
			BindingResult result, Model model, HttpSession session) {
		try {
			User user = getUserFromSession(session);
			Budget budget = budgetViewModelToBudget(budgetViewModel);
			budget.setUser(user);
			budgetDAO.add(budget);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:allBudgets";
	}
	
	@RequestMapping(value = "/editBudget", method = RequestMethod.GET)
	public String showEditBudgetPage(@ModelAttribute(value="id") int id, 
			HttpSession session, Model model) {
		try {
			User user = getUserFromSession(session);
			Budget budget = budgetDAO.getBudgetById(id);
			
			if (budgetDAO.checkUserHasBudget(budget, user)) {
				BudgetViewModel budgetViewModel = budgetToBudgetViewModel(budget);
				model.addAttribute("budgetViewModel", budgetViewModel);
			} else {
				throw new Exception("Invalid budget!");
			}
		} catch(Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "forward:error";
		}
		
		return "editBudget";
	}
	
	@RequestMapping(value = "/editBudget", method = RequestMethod.POST)
	public String editExpense (@ModelAttribute("budgetViewModel") @Valid BudgetViewModel budgetViewModel, BindingResult result,
			Model model, HttpSession session) throws DAOException {
		try {
			User user = getUserFromSession(session);
			Budget budget = budgetViewModelToBudget(budgetViewModel);
			budget.setUser(user);
			
			if (budgetDAO.checkUserHasBudget(budget, user)) {
				budgetDAO.update(budget);
			} else {
				throw new Exception("Invalid budget!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:allBudgets";
	}
	
	@RequestMapping(value = "/verifyDeleteBudget", method = RequestMethod.GET)
	public String showVerifyDeleteExpensePage(@ModelAttribute(value="id") int id, Model model, HttpSession session) {
		try {			
			Budget budget = budgetDAO.getBudgetById(id);
			BudgetViewModel budgetViewModel = budgetToBudgetViewModel(budget);
			model.addAttribute("budgetViewModel", budgetViewModel);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		return "verifyDeleteBudget";
	}
	
	@RequestMapping(value = "/deleteBudget", method = RequestMethod.GET)
	public String deleteExpense(@ModelAttribute(value="id") int id, Model model, HttpSession session) {
		try {
			User user = getUserFromSession(session);
			Budget budget = budgetDAO.getBudgetById(id);
			
			if (budgetDAO.checkUserHasBudget(budget, user)) {
				budgetDAO.delete(budget);
			} else {
				throw new Exception("Invalid budget for deletion!");
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/allBudgets";
	}

	private Budget budgetViewModelToBudget(BudgetViewModel budgetViewModel) throws Exception {
		Budget budget = new Budget();
		
		if (budgetViewModel != null) {
			budget.setId(budgetViewModel.getId());
			budget.setAmount(MoneyOperations.moneyToCents(budgetViewModel.getAmount()));
			budget.setBudgetType(budgetViewModel.getBudgetType());
			budget.setCurrency(budgetViewModel.getCurrency());
			budget.setRepeatType(budgetViewModel.getRepeatType());
			budget.setBeginDate(budgetViewModel.getBeginDate());
			budget.setEndDate(budgetViewModel.getEndDate());
		}
		
		return budget;
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
