package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import model.Currency;
import model.Expense;
import model.FinanceOperation;
import model.Income;
import model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import utils.CurrencyConverter;
import utils.MoneyOperations;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dao.IAccountDAO;
import dao.IFinanceOperationDAO;
import dao.IUserDAO;
import exceptions.APIException;
import exceptions.DAOException;

@Controller
public class ExportController {
	private static final int TABLE_COLS_COUNT = 5;

	@Autowired
	private IUserDAO userDAO;
	@Autowired
	private IAccountDAO accountDAO;
	@Autowired
	private IFinanceOperationDAO financeOperationDAO;

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public String showExportPage() {
		return "export";
	}

	@RequestMapping(value = "/export/pdf", method = RequestMethod.GET)
	public void showPdfForCurrenthMonth(HttpServletResponse response, HttpSession session) {
		try {
			User user = getUserFromSession(session);
			List<Account> accounts = (List<Account>) accountDAO.getAllAccountsForUser(user);
			List<Expense> expenses = new LinkedList<Expense>();
			List<Income> incomes = new LinkedList<Income>();

			for (Account account : accounts) {
				List<Expense> expensesInAccount = (List<Expense>) financeOperationDAO.getAllExpensesByAccount(account);
				List<Income> incomesInAccount = (List<Income>) financeOperationDAO.getAllIncomesByAccount(account);
				expenses.addAll(expensesInAccount);
				incomes.addAll(incomesInAccount);
			}

			generatePdf(response, expenses, incomes, user.getCurrency());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	public void generatePdf(HttpServletResponse response, List<Expense> expenses, List<Income> incomes, Currency userCurrency) {
		Document document = new Document();
		response.setContentType("application/pdf");

		try {
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			Font smallItalicFont = new Font(BaseFont.createFont(), 10, 2);
			document.add(new Phrase("Generated on " + LocalDateTime.now(), smallItalicFont));
			PdfPTable expensesTable = new PdfPTable(TABLE_COLS_COUNT);
			Font bigBoldFont = new Font(BaseFont.createFont(), 16, 1);
			LocalDate initial = LocalDate.now();
			Paragraph expensesParagraph = new Paragraph("\nExpenses:\n\n", bigBoldFont);
			document.add(expensesParagraph);
			addTableInfo(expensesTable, expenses, userCurrency);
			document.add(expensesTable);
			Paragraph incomesParagraph = new Paragraph("\nIncomes:\n\n", bigBoldFont);
			document.add(incomesParagraph);
			PdfPTable incomesTable = new PdfPTable(TABLE_COLS_COUNT);
			addTableInfo(incomesTable, incomes, userCurrency);
			document.add(incomesTable);
			document.close();
			response.flushBuffer();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addTableInfo(PdfPTable table, List<? extends FinanceOperation> financeOperations, Currency userCurrency) throws DocumentException, IOException {
		table.setWidthPercentage(100);
		createTableHeading(table);

		if (financeOperations != null) {
			for (FinanceOperation financeOperation : financeOperations) {
				addTableRow(table, financeOperation, userCurrency);
			}
		}
	}

	private void addTableRow(PdfPTable table, FinanceOperation financeOperation, Currency userCurrency) throws DocumentException, IOException {
		String date = financeOperation.getDate().toString();
		addCellToTableRow(table, date);
		String category = financeOperation.getCategory().getCategoryName();
		addCellToTableRow(table, category);
		String currency = financeOperation.getCurrency().name();
		int amountSum = financeOperation.getAmount();
		String amount = String.format("%.2f", MoneyOperations.amountPerHendred(amountSum));
		addCellToTableRow(table, currency + " " + amount);
		String mainCurrency = userCurrency.name();
		String amountInMainCurrency = "";

		try {
			amountInMainCurrency = String.valueOf(MoneyOperations.amountPerHendred(CurrencyConverter
					.convertToThisCurrency(amountSum, financeOperation.getCurrency(), userCurrency)));
		} catch (APIException e) {
			e.printStackTrace();
		}

		addCellToTableRow(table, mainCurrency + amountInMainCurrency);
		addCellToTableRow(table, financeOperation.getDescription());
	}

	private void addCellToTableRow(PdfPTable table, String text) throws DocumentException, IOException {
		PdfPCell cell = new PdfPCell();
		Font rowCellFont = new Font(BaseFont.createFont(), 12, 0);
		cell.addElement(new Paragraph(10, text, rowCellFont));
		table.addCell(cell);
	}

	private void createTableHeading(PdfPTable table) throws DocumentException, IOException {
		addCellToTableHead(table, "Date");
		addCellToTableHead(table, "Category");
		addCellToTableHead(table, "Amount");
		addCellToTableHead(table, "In main currency");
		addCellToTableHead(table, "Description");
	}

	private void addCellToTableHead(PdfPTable table, String text) throws DocumentException, IOException {
		PdfPCell cell = new PdfPCell();
		Font headFont = new Font(BaseFont.createFont(), 12, 1);
		cell.addElement(new Paragraph(10, text, headFont));
		table.addCell(cell);
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
