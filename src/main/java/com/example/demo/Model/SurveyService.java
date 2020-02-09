package com.example.demo.Model;

import java.util.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@Service
@JsonSerialize
public class SurveyService {
	
	DatabaseConnection dbconnect = new DatabaseConnection();
	
	public SurveyService() {
		
	}
	
	private String teamSelected;
	
	private Survey survey = new Survey();
	
	private List<Question> questions = new ArrayList<>();
	
	private int questionNumber;
	
	private int totalScore;
	
	private Team team1 = new Team();
	
	private SurveyQuestions surveyQuestionsToReturn = new SurveyQuestions();
	
	{
	
	
	}
	
	// This is a method to test API exposure
	
	
	
	// test comment
	public SurveyQuestions getSurvey(int surveyid) {
		
		SurveyQuestions surveyQuestionsFromDatabase = new SurveyQuestions();
		
		surveyQuestionsFromDatabase = dbconnect.buildSurveyQuestions(surveyid); // HARDCODED FOR DEMO, BUT CAN TAKE IN ANY NUMBER
		
		//return surveyQuestionsToReturn;
		
		return surveyQuestionsFromDatabase;
	}
	
	public List<Team> findAllTeams() {
		
		List<Team> teamNames = new ArrayList<>();
		
		teamNames = dbconnect.getTeamNames();
		
		return teamNames;
		
	}
	
	public boolean saveScores(int teamid, int surveyid, SurveyQuestions resultsOfSurveyQuestions) {
		
		boolean saveScoreStatus = dbconnect.saveSurveyResults(teamid, surveyid, resultsOfSurveyQuestions);
		
		return saveScoreStatus;
		
		//dbconnect.saveSurveyQuestionsResults(resultsOfSurveyQuestions);
	}
	
	public double calculateScore(SurveyQuestions resultOfSurveyQuestions) {
		
		
		return resultOfSurveyQuestions.getTotalScore();
	}
	
	public String printTeam() {
		return teamSelected;
	}
	
	public List<User> getUsers() {
		
		List<User> usersInDatabase = new ArrayList<>();
		
		usersInDatabase = dbconnect.getUsersFromDatabase();
		
		return usersInDatabase;
		
	}
	
	public boolean addTeam(Team teamToInsert) {
		
		boolean insertStatus = dbconnect.insertTeam(teamToInsert);
		
		return insertStatus;
		
	}
	
	public boolean editTeam(Team newTeamData, String teamNameToEdit) {
		
		boolean editTeamStatus = dbconnect.editTeam(newTeamData, teamNameToEdit);
		
		return editTeamStatus;
		
	}
	
	public boolean deleteTeam(String teamNameToDelete) {
		
		boolean deleteStatus = dbconnect.deleteTeam(teamNameToDelete);
		
		return deleteStatus;
	}
	
	public List<Survey> getSurveys() {
		
		List<Survey> surveys = dbconnect.getSurveys();
		
		return surveys;
		
	}
	
	public ReportOptions fetchReportOptions() {
		
		ReportOptions returnReportOptions = dbconnect.fetchReportOptions();
		
		return returnReportOptions;
	}

}
