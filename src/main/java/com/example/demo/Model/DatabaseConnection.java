package com.example.demo.Model;

import java.sql.*; // Import java SQL library

public class DatabaseConnection {
	
	public static void main(String[] args) throws SQLException {
		
		// Establish connection object by giving it postgreSQL database information 
		Connection con = DriverManager.getConnection("jdbc:postgresql://ec2-107-22-239-155.compute-1.amazonaws.com/daknuflimm0laj", "utufnbbozfaphi", "4a7b61f6d36d53dd87d281cc3786acbe2bdcaf7470f7368b46ac370c1c5dbd95");
		
		Statement statement = con.createStatement(); // Create a "Statement" object to do operations on
		
		if(con != null) { // Error checking
			System.out.println("Database Connected");
		} else {
			System.out.println("Database Not Connected");
		}
		
		/*
		 * 1. Create ResultSet object to hold the results
		 * 2. Call function executeQuery on Statement object and pass in SQL code:
		 * 		a. "SELECT *" means select all
		 * 		b. "FROM question_types" means from the table that you give it (in this case question_types)
		 */
		ResultSet results = statement.executeQuery("SELECT * FROM question_types");
		
		while(results.next()) { // While there aren't any more rows in the table...
			
			String data = results.getString(1);
			System.out.println("Fetching data by column index for row " + results.getRow() + " : " + data);
			
			data = results.getString("name"); // call getString function w/ parameter "name" (column w/ data type string in database)
			System.out.println("Fetching data by column name for row " + results.getRow() + " : " + data);
			
			data = results.getString("description"); // call getString function w/ parameter "description" (column w/ data type string in database)
			System.out.println("Fetching data by column name for row " + results.getRow() + " : " + data);
			
			Date date = results.getDate("created"); // call getDate function w/ parameter "created" (column w/ data type timezone in database)
			System.out.println("Fetching data by column name for row " + results.getRow() + " : " + date);
			
		}
		
	}
		
		
}
	
