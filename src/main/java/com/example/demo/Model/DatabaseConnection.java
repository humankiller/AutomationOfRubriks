package com.example.demo.Model;

import java.sql.*;

public class DatabaseConnection {
	
	public static void main(String[] args) throws SQLException {
		
		Connection con = DriverManager.getConnection("jdbc:postgresql://ec2-107-22-239-155.compute-1.amazonaws.com/daknuflimm0laj", "utufnbbozfaphi", "4a7b61f6d36d53dd87d281cc3786acbe2bdcaf7470f7368b46ac370c1c5dbd95");
		Statement statement = con.createStatement();
		
		if(con != null) {
			System.out.println("Database Connected");
		} else {
			System.out.println("Database Not Connected");
		}
		
		ResultSet results = statement.executeQuery("SELECT * FROM question_types");
		
		while(results.next()) {
			
			String data = results.getString(1);
			System.out.println("Fetching data by column index for row " + results.getRow() + " : " + data);
			data = results.getString("name");
			System.out.println("Fetching data by column name for row " + results.getRow() + " : " + data);
			data = results.getString("description");
			System.out.println("Fetching data by column name for row " + results.getRow() + " : " + data);
			Date date = results.getDate("created");
			System.out.println("Fetching data by column name for row " + results.getRow() + " : " + date);
			
		}
		
	}
		
		
}
	
