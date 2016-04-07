package controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import model.Account;
import model.Currency;
import model.Expense;
import model.Tag;
import model.User;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import utils.CurrencyChange;
import utils.MoneyOperations;
import view.model.ExpenseViewModel;
import dao.DAOException;
import dao.IAccountDAO;
import dao.IFinanceOperationDAO;
import dao.IUserDAO;

@Controller
public class OverviewController {
	
	@Autowired
	private IFinanceOperationDAO financeOperationDAO;
	@Autowired
	private IAccountDAO accountDAO;
	@Autowired
	private IUserDAO userDAO;

	@RequestMapping(value = "/overview", method = RequestMethod.GET)
	public String showOverview(Model model, HttpSession session) {
		try {
			String username = (String) session.getAttribute("username");
			
			if (username == null || username.isEmpty()) {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				username = auth.getName();
				session.setAttribute("username", username);
			}
			
			User user = userDAO.getUserByUsername(username);			
			
			if (session.getAttribute("month") == null) {
				int month = LocalDate.now().getMonthOfYear();
				session.setAttribute("month", month);
			}
			int month = (int) session.getAttribute("month");			
			
			if (session.getAttribute("year") == null) {
				int year = LocalDate.now().getYear();
				session.setAttribute("year", year);
			}
			int year = (int) session.getAttribute("year");
			
			List<Account> accounts = (List<Account>) accountDAO.getAllAccountsForUser(user);
			List<ExpenseViewModel> expenseViews = new LinkedList<ExpenseViewModel>();
			Map<String, Integer> moneyByCategory = new HashMap<String, Integer>();
			int amountToSpend = 0;
			
			for (Account acc : accounts) {
				List<Expense> accExpenses = (List<Expense>) financeOperationDAO.getAllExpensesByAccount(acc);
				
				for (Expense expense : accExpenses) {
					if (expense.getDate().getMonthOfYear() == month && 
								expense.getDate().getYear() == year) {
						
						ExpenseViewModel expenseViewModel = expenseToExpenseViewModel(expense);						
						if (expense.getCurrency() != user.getCurrency()) {
							int result = CurrencyChange.convertToThisCurrency(expense.getAmount(),
									expense.getCurrency(), user.getCurrency());
							float userCurrencyAmount = MoneyOperations.amountPerHendred(result);
							expenseViewModel.setUserCurrencyAmount(userCurrencyAmount);
						}
						expenseViews.add(expenseViewModel);
					}
				}
				
				amountToSpend += acc.getBalance();
				
				for (ExpenseViewModel expenseViewModel : expenseViews) {
					amountToSpend -= (MoneyOperations.moneyToCents(expenseViewModel.getAmount()));
					String category = "'" + expenseViewModel.getCategory() + "'";
					int oldAmount = 0;
					
					if (moneyByCategory.containsKey(category)) {
						oldAmount = moneyByCategory.get(category);
					}
					
					moneyByCategory.put(category, oldAmount + MoneyOperations.moneyToCents(expenseViewModel.getAmount()));
				}
			}
			Collections.sort(expenseViews, (e1, e2) -> e1.getDate().getDayOfMonth()-e2.getDate().getDayOfMonth());
			float moneyToSpend = MoneyOperations.amountPerHendred(amountToSpend);
			model.addAttribute("expenses", expenseViews);		
			model.addAttribute("moneyToSpend", moneyToSpend);
			model.addAttribute("categories", moneyByCategory.keySet());
			model.addAttribute("expensesAmounts", moneyByCategory.values());
			model.addAttribute("accounts", accounts);
			System.out.println(accounts);
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		return "overview";
	}
	
	private ExpenseViewModel expenseToExpenseViewModel(Expense expense) throws Exception {
		ExpenseViewModel expenseViewModel = new ExpenseViewModel();
		expenseViewModel.setId(expense.getId());
		expenseViewModel.setAmount(MoneyOperations.amountPerHendred(expense.getAmount()));
		expenseViewModel.setUserCurrencyAmount(MoneyOperations.amountPerHendred(expense.getAmount()));
		expenseViewModel.setAccount(expense.getAccount().getTitle());
		expenseViewModel.setCategory(expense.getCategory().getCategoryName());
		expenseViewModel.setCurrency(expense.getCurrency());		
		expenseViewModel.setDate(expense.getDate());
		expenseViewModel.setDescription(expense.getDescription());
		expenseViewModel.setRepeatType(expense.getRepeatType());
		List<String> tags = new LinkedList<String>();
		
		if (expense.getTags() != null) {
			for (Tag tag : expense.getTags()) {
				tags.add(tag.getTagName());
			}
		}
		
		expenseViewModel.setTags(tags);
		
		return expenseViewModel;
	}
}
