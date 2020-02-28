package com.example.demo.Model;

import java.util.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class adminLogin {
	
	private String username;
	private String password;
	private boolean admin;

	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String pass) {
		this.password = pass;
	}
}
