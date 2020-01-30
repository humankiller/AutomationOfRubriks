package com.example.demo.Model;

import java.util.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class User {
	
	private String username;
	private String password;
	private boolean admin;
	
	public User() {
		
	}
	
	public User(String username, String password, boolean admin) {
		this.username = username;
		this.password = password;
		this.admin = admin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	

}
