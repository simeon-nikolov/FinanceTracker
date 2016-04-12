package controller;

import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import model.Account;
import model.Expense;
import model.Income;
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

import utils.CurrencyConverter;
import utils.MoneyOperations;
import view.model.ExpenseViewModel;

import com.google.gson.Gson;

import dao.IAccountDAO;
import dao.IFinanceOperationDAO;
import dao.IUserDAO;
import exceptions.APIException;
import exceptions.DAOException;

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
			User user = getUserFromSession(session);
			addMonthAndYearToSession(session);
			int month = (int) session.getAttribute("month");
			int year = (int) session.getAttribute("year");

			List<Account> accounts = (List<Account>) accountDAO.getAllAccountsForUser(user);
			List<ExpenseViewModel> expenseViews = new LinkedList<ExpenseViewModel>();
			Map<String, Integer> moneyByCategory = new TreeMap<String, Integer>();
			Map<String, Float> expensesByDate = new TreeMap<String, Float>();
			Map<String, Float> incomesByDate = new TreeMap<String, Float>();
			Float amountToSpend = 0.0f;

			for (Account acc : accounts) {
				List<Expense> accExpenses = (List<Expense>) financeOperationDAO.getAllExpensesByAccount(acc);
				amountToSpend = addAndCalculateExpensesForMonth(user, month, year, expenseViews, expensesByDate, amountToSpend, accExpenses);
				amountToSpend += MoneyOperations.amountPerHendred(acc.getBalance());
				List<Income> accIncomes = (List<Income>) financeOperationDAO.getAllIncomesByAccount(acc);
				amountToSpend = addAndCalculateIncomesForMonth(user, month, year, incomesByDate, amountToSpend, accIncomes);
			}

			List<Map<String, Object>> chartData = new LinkedList<Map<String, Object>>();
			List<String> dates = new LinkedList<String>();
			addChartData(month, year, expensesByDate, incomesByDate, chartData, dates);
			addDataToModel(model, user, accounts, expenseViews, amountToSpend, chartData);
			model.addAttribute("dates", new Gson().toJson(dates));
		} catch (Exception e) {
			e.printStackTrace();
			return "forward:error";
		}

		return "overview";
	}

	private void addDataToModel(Model model, User user, List<Account> accounts, List<ExpenseViewModel> expenseViews, Float amountToSpend, List<Map<String, Object>> chartData) {
		Collections.sort(expenseViews, (e1, e2) -> e1.getDate().getDayOfMonth()-e2.getDate().getDayOfMonth());
		model.addAttribute("expenses", expenseViews);
		model.addAttribute("moneyToSpend", amountToSpend);
		model.addAttribute("userCurrency", user.getCurrency());
		model.addAttribute("accounts", accounts);
		model.addAttribute("chartData", new Gson().toJson(chartData));
	}

	private void addChartData(int month, int year, Map<String, Float> expensesByDate, Map<String, Float> incomesByDate, List<Map<String, Object>> chartData, List<String> dates) {
		YearMonth yearMonthObject = YearMonth.of(year, month);
		int daysInMonth = yearMonthObject.lengthOfMonth();
		List<Float> expensesInMonth = new LinkedList<Float>();
		List<Float> incomesInMonth = new LinkedList<Float>();

		for (int day = 1; day <= daysInMonth; day++) {
			String date = year + "-" + month + "-" + day;
			dates.add(date);

			if (expensesByDate.containsKey(date)) {
				expensesInMonth.add(expensesByDate.get(date));
			} else {
				expensesInMonth.add(0.0f);
			}

			if (incomesByDate.containsKey(date)) {
				incomesInMonth.add(incomesByDate.get(date));
			} else {
				incomesInMonth.add(0.0f);
			}
		}

		Map<String, Object> incomesChartData = new HashMap<String, Object>();
		incomesChartData.put("name", "Incomes");
		incomesChartData.put("data", incomesInMonth);
		chartData.add(incomesChartData);
		Map<String, Object> expensesChartData = new HashMap<String, Object>();
		expensesChartData.put("name", "Expenses");
		expensesChartData.put("data", expensesInMonth);
		chartData.add(expensesChartData);
	}

	private float addAndCalculateIncomesForMonth(User user, int month, int year, Map<String, Float> incomesByDate, float amountToSpend, List<Income> accIncomes) throws APIException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");

		for (Income income : accIncomes) {
			if (income.getDate().getMonthOfYear() == month &&
					income.getDate().getYear() == year) {
				float userCurrencyAmount = MoneyOperations.amountPerHendred(income.getAmount());

				if (income.getCurrency() != user.getCurrency()) {
					int result = CurrencyConverter.convertToThisCurrency(income.getAmount(),
							income.getCurrency(), user.getCurrency());
					userCurrencyAmount = MoneyOperations.amountPerHendred(result);
				}

				amountToSpend += userCurrencyAmount;
				Date date = income.getDate().toDateTimeAtStartOfDay().toDate();
				java.time.LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				String dateKey = localDate.format(formatter);

				if (!incomesByDate.containsKey(dateKey)) {
					incomesByDate.put(dateKey, 0.0f);
				}

				incomesByDate.put(dateKey, incomesByDate.get(dateKey) + userCurrencyAmount);
			}
		}

		return amountToSpend;
	}

	private float addAndCalculateExpensesForMonth(User user, int month, int year, List<ExpenseViewModel> expenseViews, Map<String, Float> expensesByDate, float amountToSpend, List<Expense> accExpenses) throws Exception,
			APIException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");

		for (Expense expense : accExpenses) {
			if (expense.getDate().getMonthOfYear() == month &&
						expense.getDate().getYear() == year) {
				 expense.getAmount();
				float userCurrencyAmount = MoneyOperations.amountPerHendred(expense.getAmount());
				ExpenseViewModel expenseViewModel = expenseToExpenseViewModel(expense);

				if (expense.getCurrency() != user.getCurrency()) {
					int result = CurrencyConverter.convertToThisCurrency(expense.getAmount(),
							expense.getCurrency(), user.getCurrency());
					userCurrencyAmount = MoneyOperations.amountPerHendred(result);
				}

				amountToSpend -= userCurrencyAmount;
				expenseViewModel.setUserCurrencyAmount(userCurrencyAmount);
				expenseViewModel.setUserCurrency(user.getCurrency());
				expenseViews.add(expenseViewModel);

				Date date = expense.getDate().toDateTimeAtStartOfDay().toDate();
				java.time.LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				String dateKey = localDate.format(formatter);

				if (!expensesByDate.containsKey(dateKey)) {
					expensesByDate.put(dateKey, 0.0f);
				}

				expensesByDate.put(dateKey, expensesByDate.get(dateKey) + userCurrencyAmount);
			}
		}

		return amountToSpend;
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

	private void addMonthAndYearToSession(HttpSession session){
		if (session.getAttribute("month") == null || session.getAttribute("year") == null) {
			int month = LocalDate.now().getMonthOfYear();
			session.setAttribute("month", month);
			int year = LocalDate.now().getYear();
			session.setAttribute("year", year);
		}
	}
}
