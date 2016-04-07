package tests;

import model.Currency;

import org.junit.Test;

import utils.MoneyOperations;

public class MoneyOperationsTests {
	@Test
	public void testConvertFromCurrency() {
		MoneyOperations.convertFromCurrency(Currency.BGN, Currency.EUR, 2.45);
	}
}
