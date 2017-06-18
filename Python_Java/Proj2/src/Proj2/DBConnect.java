package Proj2;

import java.sql.*;

import javax.swing.DefaultListModel;

public class DBConnect {

		private Connection con;
		
		public DBConnect() {
			try{
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/world?autoReconnect=true&useSSL=false","root","sasa");
				
				
			} catch(Exception ex) {
				System.out.println("Error: " + ex);
			}
		}
		
		public void getData1() {
			try {
				Statement st;
				ResultSet rs;
				
				String query = "select Name From Country";
				st = con.createStatement();
				rs = st.executeQuery(query);
				
				System.out.println("Records from database");
				while (rs.next()) {
					String name = rs.getString("Name");
					System.out.println(name);
				}
			} catch(Exception ex) {
				System.out.println("Error: " + ex);
			}
		}
		
		
		public DefaultListModel<String> getData() {
			DefaultListModel<String> DLM = new DefaultListModel<String>();
			try {
				Statement st;
				ResultSet rs;
				
				String query = "select Name From Country";
				st = con.createStatement();
				rs = st.executeQuery(query);
				while (rs.next()) {
					String name = rs.getString("name");
					DLM.addElement(name);
				}
			} catch(Exception ex) {
				System.out.println("Error: " + ex);
			}
			return DLM;
		}
}