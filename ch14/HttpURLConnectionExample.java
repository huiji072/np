package ch14;

import java.io.*;
import java.net.*;
import java.util.*;

public class HttpURLConnectionExample {
	
	private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception{
		HttpURLConnectionExample http = new HttpURLConnectionExample();
		System.out.println("Sent Http GET request");
		http.sendGet();
	}
	
	public void sendGet() {
		try {
			URL url = new URL("http://localhost:5000");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", USER_AGENT);
			
			int responseCode = connection.getResponseCode();
			
			System.out.println("Response Code: " + responseCode);
			if(responseCode == 200) {
				String response = getResponse(connection);
				System.out.println("response: " + response.toString());
			}else {
				System.out.println("Bade Response Code: " + responseCode);
			}
		}catch(MalformedURLException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getResponse(HttpURLConnection connection) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));) {
//			Process headers
			System.out.println("Request Headers");
			Map<String, List<String>> requestHeaders = connection.getHeaderFields();
			Set<String> keySet = requestHeaders.keySet();
			
			for(String key : keySet) {
				List values = requestHeaders.get(key);
				String header = key + " = " + values.toString() + "\n";
				System.out.println(header);
			}
			System.out.println();
			
			String inputLine;
			StringBuilder response = new StringBuilder();
			
			while((inputLine = br.readLine()) != null) {
				response.append(inputLine).append("\n");
			}
			br.close();
			return response.toString();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
