package com.example.demo.Model;

import java.util.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class Team {
	
	private List<SurveyQuestions> surveys;
	private String teamName;
	
	public Team() {
		
	}
	
	public Team(List<SurveyQuestions> surveys, String teamName) {
		this.surveys = surveys;
		this.teamName = teamName;
	}

	public List<SurveyQuestions> getSurveys() {
		return surveys;
	}

	public void setSurveys(List<SurveyQuestions> surveys) {
		this.surveys = surveys;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	

}
