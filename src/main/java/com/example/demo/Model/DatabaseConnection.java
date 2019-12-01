package com.example.demo.Model;

import java.sql.*;

public class DatabaseConnection {
	
	public static void main(String[] args) throws SQLException {
		
		Connection con = DriverManager.getConnection("jdbc:postgresql://ec2-107-22-239-155.compute-1.amazonaws.com/daknuflimm0laj", "utufnbbozfaphi", "4a7b61f6d36d53dd87d281cc3786acbe2bdcaf7470f7368b46ac370c1c5dbd95");
		
		if(con != null) {
			System.out.println("Database Connected");
		} else {
			System.out.println("Database Not Connected");
		}
	}
		
		
}
	
