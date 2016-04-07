package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import model.Currency;

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
	
	public static double convertFromCurrency(Currency fromCurrency, Currency toCurrency, double moneyAmmount) {
		String url = BASE_URL + "convert?access_key=" + API_KEY + 
				"&from=" + fromCurrency + 
				"&to=" + toCurrency + 
				"&amount=" + moneyAmmount + 
				"&format=1";
		
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
			con.setRequestMethod("GET");
			
			if (con.getResponseCode() == HTTP_SUCCESS) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String line = reader.readLine();
				StringBuilder builder = new StringBuilder();
				
				while (line != null) {
					builder.append(line);
					line = reader.readLine();
				}
				
				System.out.println(builder.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

}
