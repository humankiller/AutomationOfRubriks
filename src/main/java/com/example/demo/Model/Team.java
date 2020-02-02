package com.example.demo.Model;

import java.util.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class Team {
	
	private int teamid;
	private String teamName;
	
	public Team() {
		
	}
	
	public Team(int teamid, String teamName) {
		this.teamid = teamid;
		this.teamName = teamName;
	}

	public int getTeamid() {
		return teamid;
	}

	public void setTeamid(int teamid) {
		this.teamid = teamid;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

}
