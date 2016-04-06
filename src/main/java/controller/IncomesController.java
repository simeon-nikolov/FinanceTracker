package controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dao.DAOException;
import dao.IAccountDAO;
import dao.ICategoryDAO;
import dao.IFinanceOperationDAO;
import dao.ITagDAO;
import dao.IUserDAO;
import model.Account;
import model.Category;
import model.Currency;
import model.FinanceOperationType;
import model.Income;
import model.RepeatType;
import model.Tag;
import model.User;
import utils.MoneyOperations;
import view.model.IncomeViewModel;

@Controller
public class IncomesController {

	@Autowired
	IUserDAO userDao;
	@Autowired
	IAccountDAO accountDao;
	@Autowired
	IFinanceOperationDAO foDao;
	@Autowired
	ICategoryDAO categoryDao;
	@Autowired
	ITagDAO tagDao;

	@RequestMapping(value = "/allIncomes", method = RequestMethod.GET)
	public String showAllIncomesPage(HttpSession session, Model model) {
		try {
			User user = getUserFromSession(session);
			List<Account> accounts = (List<Account>) accountDao.getAllAccountsForUser(user);
			List<Income> incomes = new ArrayList<Income>();
			Map<String, Integer> amountsByCategory = new HashMap<String, Integer>();
			int month = LocalDate.now().getMonthOfYear();
			int year = LocalDate.now().getYear();

			if (session.getAttribute("month") != null) {
				month = (int) session.getAttribute("month");
			}

			if (session.getAttribute("year") != null) {
				year = (int) session.getAttribute("year");
			}

			for (Account acc : accounts) {
				List<Income> accIncomes = (List<Income>) foDao.getAllIncomesByAccount(acc);

				for (Income in : accIncomes) {
					if (in.getDate().getMonthOfYear() == month && in.getDate().getYear() == year) {
						incomes.add(in);
					}
				}

				for (Income in : incomes) {
					String category = "'" + in.getCategory().getCategoryName() + "'";
					int oldAmount = 0;

					if (amountsByCategory.containsKey(category)) {
						oldAmount = amountsByCategory.get(category);
					}
					amountsByCategory.put(category, oldAmount + in.getAmount());
				}
			}

			model.addAttribute("categories", amountsByCategory.keySet());
			model.addAttribute("incomesAmounts", amountsByCategory.values());
			model.addAttribute("incomes", incomes);
			model.addAttribute("accounts", accounts);
			System.out.println(incomes);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "allIncomes";
	}

	@RequestMapping(value = "/addIncome", method = RequestMethod.GET)
	public String showAddIncomePage(HttpSession session, Model model) {
		try {
			Currency[] allCurrencies = Currency.values();
			RepeatType[] allRepeatTypes = RepeatType.values();
			Collection<Category> categories = categoryDao.getAllCategoriesForFOType(FinanceOperationType.INCOME);
			List<String> allCategories = new LinkedList<String>();

			for (Category category : categories) {
				allCategories.add(category.getCategoryName());
			}

			User user = getUserFromSession(session);
			List<Account> userAccounts = (List<Account>) accountDao.getAllAccountsForUser(user);
			List<String> allAcounts = new LinkedList<String>();

			for (Account acc : userAccounts) {
				allAcounts.add(acc.getTitle());
			}

			List<Tag> tags = (List<Tag>) tagDao.getAllTagsByTypeFor(FinanceOperationType.INCOME);
			List<String> allTags = new LinkedList<String>();

			if (tags != null) {
				for (Tag tag : tags) {
					allTags.add(tag.getTagName());
				}
			}

			model.addAttribute("allCurrencies", allCurrencies);
			model.addAttribute("allRepeatTypes", allRepeatTypes);
			model.addAttribute("allCategories", allCategories);
			model.addAttribute("allAccounts", allAcounts);
			model.addAttribute("allTags", allTags);
			model.addAttribute("incomeViewModel", new IncomeViewModel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "addIncome";
	}

	@RequestMapping(value = "/addIncome", method = RequestMethod.POST)
	public String addIncome(@ModelAttribute("incomeViewModel") @Valid IncomeViewModel incomeViewModel,
			BindingResult result, Model model, HttpSession session) {
		try {
			User user = getUserFromSession(session);
			Income income = incomeViewModelToIncome(incomeViewModel, user);
			foDao.add(income);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:allIncomes";
	}
	
	@RequestMapping(value = "/editIncome", method = RequestMethod.GET)
	public String showEditIncomePage(@ModelAttribute(value="id") int id, 
			HttpSession session, Model model) {
		try {
			User user = getUserFromSession(session);
			Income income = foDao.getIncomeById(id);
			
			if (foDao.checkUserHasFinanceOperation(income, user)) {
				IncomeViewModel incomeViewModel = incomeToIncomeViewModel(income);
				model.addAttribute("incomeViewModel", incomeViewModel);
			} else {
				throw new Exception("Invalid Income!");
			}
		} catch(Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "forward:error";
		}
		
		return "editIncome";
	}		

	private User getUserFromSession(HttpSession session) throws DAOException {
		String username = (String) session.getAttribute("username");
		if (username == null || username.isEmpty()) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			username = auth.getName();
			session.setAttribute("username", username);
		}
		User user = userDao.getUserByUsername(username);

		return user;
	}

	private Income incomeViewModelToIncome(IncomeViewModel incomeViewModel, User user) throws Exception {
		Income income = new Income();
		income.setAmount(MoneyOperations.moneyToCents(incomeViewModel.getAmount()));
		income.setCurrency(incomeViewModel.getCurrency());
		income.setDate(incomeViewModel.getDate());
		income.setDescription(incomeViewModel.getDescription());
		income.setRepeatType(incomeViewModel.getRepeatType());
		income.setFinanceOperationType(FinanceOperationType.EXPENSE);
		Account account = accountDao.getAccountForUserByName(incomeViewModel.getAccount(), user);
		income.setAccount(account);
		Category category = categoryDao.getCategoryByName(incomeViewModel.getCategory());
		income.setCategory(category);
		List<Tag> tags = new LinkedList<Tag>();

		if (incomeViewModel.getTags() != null) {
			for (String tagName : incomeViewModel.getTags()) {
				Tag tag = tagDao.getTagByTagname(tagName);
				tags.add(tag);
			}
		}

		income.setTags(tags);

		return income;
	}
	
	private IncomeViewModel incomeToIncomeViewModel(Income income) throws Exception {
		IncomeViewModel incomeViewModel = new IncomeViewModel();
		incomeViewModel.setId(income.getId());
		incomeViewModel.setAmount(MoneyOperations.amountPerHendred(income.getAmount()));
		incomeViewModel.setAccount(income.getAccount().getTitle());
		incomeViewModel.setCategory(income.getCategory().getCategoryName());
		incomeViewModel.setCurrency(income.getCurrency());
		incomeViewModel.setDate(income.getDate());
		incomeViewModel.setDescription(income.getDescription());
		incomeViewModel.setRepeatType(income.getRepeatType());
		List<String> tags = new LinkedList<String>();
		
		if (income.getTags() != null) {
			for (Tag tag : income.getTags()) {
				tags.add(tag.getTagName());
			}
		}
		
		incomeViewModel.setTags(tags);
		
		return incomeViewModel;
	}

}
