package com.redn.connect.jenkins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpConnectUtil {
	
	public static String connectAndGetResponse(String urlString, String method) throws Exception{
		
		HttpURLConnection httpConnection = null;
		BufferedReader reader = null;
		String responseStr = null;
		
		try {
		
			URL url = new URL(urlString);
			
			httpConnection = (HttpURLConnection) url.openConnection();
			
			httpConnection.setRequestMethod(method);
			
			int responseCode = httpConnection.getResponseCode();
			
			System.out.println("Sent Request to: " + url);
			System.out.println("Got Response Code: " + responseCode);
			
			reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
			
			String line = null;
			StringBuilder response = new StringBuilder();
			
			while ( (line = reader.readLine()) != null ){
				response.append(line);
			}
			
			System.out.println("Response from Mule Agent: \n" + response.toString());
			
			responseStr = response.toString();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw e;
		} catch (ProtocolException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			if(reader != null){
				try{
					reader.close();
				}catch(Exception e){
					//
				}
			}
			
			if(httpConnection != null){
				httpConnection.disconnect();
			}
	   }
		
		return responseStr;
	}
}
