package controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import model.Account;
import model.Category;
import model.Currency;
import model.FinanceOperationType;
import model.Income;
import model.RepeatType;
import model.Tag;
import model.User;

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

import utils.CurrencyConverter;
import utils.MoneyOperations;
import view.model.IncomeViewModel;
import dao.IAccountDAO;
import dao.ICategoryDAO;
import dao.IFinanceOperationDAO;
import dao.ITagDAO;
import dao.IUserDAO;
import exceptions.DAOException;

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
			List<IncomeViewModel> incomeViews = new ArrayList<IncomeViewModel>();
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
						IncomeViewModel incomeViewModel = incomeToIncomeViewModel(in);
						if (in.getCurrency() != user.getCurrency()) {
							int result = CurrencyConverter.convertToThisCurrency(in.getAmount(), in.getCurrency(),
									user.getCurrency());
							float userCurrencyAmount = MoneyOperations.amountPerHendred(result);
							incomeViewModel.setUserCurrencyAmount(userCurrencyAmount);
						}
						incomeViews.add(incomeViewModel);
					}
				}

				for (IncomeViewModel incomeViewModel : incomeViews) {
					String category = "'" + incomeViewModel.getCategory() + "'";
					int oldAmount = 0;

					if (amountsByCategory.containsKey(category)) {
						oldAmount = amountsByCategory.get(category);
					}
					amountsByCategory.put(category,
							oldAmount + MoneyOperations.moneyToCents(incomeViewModel.getAmount()));
				}
			}

			List<List<String>> chartData = new LinkedList<List<String>>();

			for (String category : amountsByCategory.keySet()) {
				int amount = amountsByCategory.get(category);
				List<String> dataRow = new LinkedList<String>();
				dataRow.add(category);
				dataRow.add(String.valueOf(amount));
				chartData.add(dataRow);
			}

			Collections.sort(incomeViews, (i1, i2) -> i1.getDate().getDayOfMonth() - i2.getDate().getDayOfMonth());

			model.addAttribute("chartData", chartData);
			model.addAttribute("incomes", incomeViews);
			model.addAttribute("accounts", accounts);
		} catch (Exception e) {
			e.printStackTrace();
			return "forward:error";
		}

		return "allIncomes";
	}

	@RequestMapping(value = "/addIncome", method = RequestMethod.GET)
	public String showAddIncomePage(HttpSession session, Model model) {
		try {
			Currency[] allCurrencies = Currency.values();
			RepeatType[] allRepeatTypes = RepeatType.values();
			User user = getUserFromSession(session);

			List<String> allCategories = getAllCategoriesForIncomes();
			List<String> allAccounts = getAllAccountsForUser(user);
			List<String> allTags = getAllTagsForIncomes();

			model.addAttribute("allCurrencies", allCurrencies);
			model.addAttribute("allRepeatTypes", allRepeatTypes);
			model.addAttribute("allCategories", allCategories);
			model.addAttribute("allAccounts", allAccounts);
			model.addAttribute("allTags", allTags);
			model.addAttribute("incomeViewModel", new IncomeViewModel());
		} catch (Exception e) {
			e.printStackTrace();
			return "forward:error";
		}
		return "addIncome";
	}

	@RequestMapping(value = "/addIncome", method = RequestMethod.POST)
	public String addIncome(@ModelAttribute("incomeViewModel") @Valid IncomeViewModel incomeViewModel,
			BindingResult result, Model model, HttpSession session) throws DAOException {

		try {
			if (result.hasErrors()) {
				String username = (String) session.getAttribute("username");
				User user = userDao.getUserByUsername(username);
				List<String> allCategories = getAllCategoriesForIncomes();
				List<String> allAccounts = getAllAccountsForUser(user);
				List<String> allTags = getAllTagsForIncomes();

				model.addAttribute("allCategories", allCategories);
				model.addAttribute("allAccounts", allAccounts);
				model.addAttribute("allTags", allTags);
				model.addAttribute("incomeViewModel", incomeViewModel);
				return "addIncome";
			}

			User user = getUserFromSession(session);
			Income income = incomeViewModelToIncome(incomeViewModel, user);
			foDao.add(income);
		} catch (Exception e) {
			e.printStackTrace();
			return "forward:error";
		}

		return "redirect:allIncomes";
	}

	@RequestMapping(value = "/editIncome", method = RequestMethod.GET)
	public String showEditIncomePage(@ModelAttribute(value = "id") int id, HttpSession session, Model model) {
		try {
			User user = getUserFromSession(session);
			Income income = foDao.getIncomeById(id);

			List<String> allCategories = getAllCategoriesForIncomes();
			List<String> allAccounts = getAllAccountsForUser(user);
			List<String> allTags = getAllTagsForIncomes();

			if (foDao.checkUserHasFinanceOperation(income, user)) {
				IncomeViewModel incomeViewModel = incomeToIncomeViewModel(income);
				model.addAttribute("incomeViewModel", incomeViewModel);
				model.addAttribute("allCategories", allCategories);
				model.addAttribute("allAccounts", allAccounts);
				model.addAttribute("allTags", allTags);
			} else {
				throw new Exception("Invalid Income!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "forward:error";
		}

		return "editIncome";
	}

	@RequestMapping(value = "/editIncome", method = RequestMethod.POST)
	public String editIncome(@ModelAttribute("incomeViewModel") @Valid IncomeViewModel incomeViewModel,
			BindingResult result, Model model, HttpSession session) throws DAOException {

		if (result.hasErrors()) {
			String username = (String) session.getAttribute("username");
			User user = userDao.getUserByUsername(username);
			List<String> allCategories = getAllCategoriesForIncomes();
			List<String> allAccounts = getAllAccountsForUser(user);
			List<String> allTags = getAllTagsForIncomes();

			model.addAttribute("allCategories", allCategories);
			model.addAttribute("allAccounts", allAccounts);
			model.addAttribute("allTags", allTags);
			return "addIncome";
		}

		try {
			User user = getUserFromSession(session);
			Income income = incomeViewModelToIncome(incomeViewModel, user);

			if (foDao.checkUserHasFinanceOperation(income, user)) {
				foDao.update(income);
			} else {
				throw new Exception("Invalid Income!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "forward:error";
		}
		return "redirect:allIncomes";
	}

	@RequestMapping(value = "/verifyDeleteIncome", method = RequestMethod.GET)
	public String verifyDeleteIncome(@ModelAttribute(value = "id") int id, Model model, HttpSession session) {
		try {
			Income income = foDao.getIncomeById(id);
			model.addAttribute("incomeDate", income.getDate());
			model.addAttribute("incomeAmount", MoneyOperations.amountPerHendred(income.getAmount()));
			model.addAttribute("incomeId", id);
		} catch (Exception e) {
			e.printStackTrace();
			return "forward:error";
		}
		return "verifyDeleteIncome";

	}

	@RequestMapping(value = "/deleteIncome", method = RequestMethod.GET)
	public String deleteIncome(@ModelAttribute(value = "id") int id, Model model, HttpSession session) {
		try {
			User user = getUserFromSession(session);
			Income income = foDao.getIncomeById(id);
			if (foDao.checkUserHasFinanceOperation(income, user)) {
				foDao.delete(income);
			} else {
				throw new Exception("Invalid income for deletion!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "forward:error";
		}

		return "redirect:/allIncomes";
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
		income.setId(incomeViewModel.getId());
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
		incomeViewModel.setUserCurrencyAmount(MoneyOperations.amountPerHendred(income.getAmount()));
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

	private List<String> getAllTagsForIncomes() throws DAOException {
		List<Tag> tags = (List<Tag>) tagDao.getAllTagsByTypeFor(FinanceOperationType.INCOME);
		List<String> allTags = new LinkedList<String>();

		if (tags != null) {
			for (Tag tag : tags) {
				allTags.add(tag.getTagName());
			}
		}
		return allTags;
	}

	private List<String> getAllCategoriesForIncomes() throws DAOException {
		Collection<Category> categories = categoryDao.getAllCategoriesForFOType(FinanceOperationType.INCOME);
		List<String> allCategories = new LinkedList<String>();

		for (Category category : categories) {
			allCategories.add(category.getCategoryName());
		}

		return allCategories;
	}

	private List<String> getAllAccountsForUser(User user) throws DAOException {
		List<Account> userAccounts = (List<Account>) accountDao.getAllAccountsForUser(user);
		List<String> allAccounts = new LinkedList<String>();

		for (Account acc : userAccounts) {
			allAccounts.add(acc.getTitle());
		}

		return allAccounts;
	}

}
