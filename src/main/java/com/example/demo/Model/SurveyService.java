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
	
	public List<SurveyType> getSurveyTypes(boolean showHidden) {
		
		List<SurveyType> surveyTypes = new ArrayList<>();
		
		surveyTypes = dbconnect.getSurveyTypes(showHidden);
		
		return surveyTypes;
		
	}
	
	public List<Survey> getSurveys(int surveytypeid) {
		
		List<Survey> surveys = dbconnect.getSurveys(surveytypeid);
		
		return surveys;
		
	}
	
	public ReportOptions fetchReportOptions() {
		
		ReportOptions returnReportOptions = dbconnect.fetchReportOptions();
		
		return returnReportOptions;
	}
	
	public ArrayList<SurveyQuestions> fetchReport(int teamid, int surveyid) {
		
		ArrayList<SurveyQuestions> report = dbconnect.fetchReport(teamid, surveyid);
		
		return report;
		
	}

	public boolean verifyAdminLogin(String userName, String password) {
		
		//List<Team> teamNames = new ArrayList<>();
		
		boolean isAdmin = dbconnect.verifyAdmin(userName, password);
		
		return isAdmin;
		
	}
	
	public ArrayList<QuestionType> getQuestionTypes() {
		
		ArrayList<QuestionType> questiontypes = dbconnect.getQuestionTypes();
		
		return questiontypes;
	}
	
	public ArrayList<Question> getQuestionsWithQuestionTypeID(int questiontypeid) {
		
		ArrayList<Question> questions = dbconnect.getQuestionsWithTypeQuestionID(questiontypeid);
		
		return questions;
	}
	
	public boolean createTemplate(Template template) {
		
		boolean createTemplateStatus = dbconnect.createTemplate(template);
		
		return createTemplateStatus;
	}

}
