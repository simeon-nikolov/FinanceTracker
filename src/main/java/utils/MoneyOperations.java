package utils;

public class MoneyOperations {

	public static float amountPerHendred(int amount) {
		return (amount / 100.0f);
	}
	
	public static int moneyToCents(float money) {
		return (int) (money * 100);
	}

}
