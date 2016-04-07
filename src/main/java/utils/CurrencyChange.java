package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import exceptions.APIException;
import model.Currency;

public class CurrencyChange {
	
	private static final int SUCCESS_CODE = 200;
	private static final String MAIN_URL = "http://api.fixer.io/latest?base=BGN&symbols=";

	public static int convertToThisCurrency(int sum, Currency currency) throws APIException {
		String url = MAIN_URL+currency.toString();
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
			con.setRequestMethod("GET");
			
			
			if (con.getResponseCode() == SUCCESS_CODE) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String line = reader.readLine();
				
				Gson gson = new Gson();
				JsonObject jsonObj = gson.fromJson(line, JsonObject.class);
				
				float rate = jsonObj.getAsJsonObject("rates").get(currency.toString()).getAsFloat();
				
				return  (int) (sum * rate);			
			}	
			else {
				throw new APIException("Error reading currency API!");
			}
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return 0;
	}
}
