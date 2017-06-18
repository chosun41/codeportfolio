package Proj2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.table.DefaultTableModel;


public class WEB extends Thread {
	
	private String  country;
	private String date;
	private DefaultTableModel populationDLM;
	
	WEB (String country,String date,DefaultTableModel populationDLM) {
		this.country = country;
		this.date = date;
		this.populationDLM = populationDLM;
	}
	
	public static String getPopulation(String country,String date) throws IOException {
		String url = "http://api.population.io/1.0/population/" + country  + "/" + date;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		String result = "";
		try {
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			result = response.toString();
		} catch (Exception ex) {
			result = "NA";
		}
		return result; 
    }    
	public void run(){
		try {
			String temp = getPopulation(this.country,this.date);
			populationDLM.addRow(new Object[]{this.country,temp});
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {}
	}