package com.example.demo.Model;

import java.util.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class User {
	
	private int userid;
	private String username;
	private String password;
	private boolean admin;
	
	public User() {
		
	}
	
	public User(int userid, String username, String password, boolean admin) {
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.admin = admin;
	}
	
	public int getUserid() {
		return userid;
	}
	
	public void setUserid(int userid) {
		this.userid = userid;
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
