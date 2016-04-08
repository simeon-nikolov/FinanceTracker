package utils;


public class MoneyOperations {
	private static final int HTTP_SUCCESS = 200;
	private static final String API_KEY = "96a447edf5218ec1a46ad420e2d71d31";
	private static final String BASE_URL = "http://apilayer.net/api/";

	public static float amountPerHendred(int amount) {
		return (amount / 100.0f);
	}
	
	public static int moneyToCents(float money) {
		return (int) (money * 100);
	}
}
