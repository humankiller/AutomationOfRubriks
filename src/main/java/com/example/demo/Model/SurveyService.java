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
	
		SurveyType developerSurvey = new SurveyType("Developer", "A survey for the developer");
		survey.setTypeOfSurvey(developerSurvey);
		survey.setName("Pre-Dojo Survey");
		
		QuestionType fivePointType = new QuestionType("5 point question type", "A survey with a 5 point scale");
		
		questions.add(new Question(fivePointType, "The development team organizes itself to accomplish the sprint goals", -1));
		questions.add(new Question(fivePointType, "The team determines how to deliver small increments of potentially releasable functionality", -1));
		questions.add(new Question(fivePointType, "The team ensures stories are written to deliver vertical slices of functionality", -1));
		questions.add(new Question(fivePointType, "The team right-sizes stories so they can be completed in 1-3 days", -1));
		questions.add(new Question(fivePointType, "The team is cross-functional, with all the skills necessary to deliver product value", -1));
		questions.add(new Question(fivePointType, "Individual team members may have specialized skills and areas of focus, but accountability belongs to team as a whole", -1));
		questions.add(new Question(fivePointType, "The team takes accountability for meeting their sprint commitments", -1));
		questions.add(new Question(fivePointType, "Team members raise impediments", -1));
		questions.add(new Question(fivePointType, "The team swarms (works collaboratively) when needed to complete sprint work", -1));
		questions.add(new Question(fivePointType, "The team collaborates", -1));
		questions.add(new Question(fivePointType, "The team has open communications", -1));
		questions.add(new Question(fivePointType, "The team has fun", -1));
		questions.add(new Question(fivePointType, "The team confronts challenges impacting the team", -1));
		
		surveyQuestionsToReturn.setSurvey(survey);
		surveyQuestionsToReturn.setQuestions(questions);
		surveyQuestionsToReturn.setQuestionNumber(questions.size());
		surveyQuestionsToReturn.setTotalScore(0);
		
		
		List<SurveyQuestions> surveysToPopulateTeam = new ArrayList<>();
		surveysToPopulateTeam.add(surveyQuestionsToReturn);
		team1.setSurveys(surveysToPopulateTeam);
		team1.setTeamName("Team 1");
		
		
		//surveyQuestionsToReturn = new SurveyQuestions(survey, questions, questions.size(), 0);
	
	}
	
	// This is a method to test API exposure
	
	
	
	// test comment
	public SurveyQuestions findAll() {
		
		SurveyQuestions surveyQuestionsFromDatabase = new SurveyQuestions();
		
		surveyQuestionsFromDatabase = dbconnect.buildSurveyQuestionsObject(6); // Hardcoded
		
		//return surveyQuestionsToReturn;
		
		return surveyQuestionsFromDatabase;
	}
	
	public List<Team> findAllTeams() {
		
		List<Team> teamNames = new ArrayList<>();
		
		teamNames = dbconnect.getTeamNames();
		
		return teamNames;
		
	}
	
	public void saveScores(SurveyQuestions resultsOfSurveyQuestions) {
		
		/*
		for(int i = 0; i < questionScores.size(); i++) {
			questions.get(i).setQuestionScore(questionScores.get(i));
		}
		
		*/
		
		//dbconnect.saveSurveyQuestionsResults(resultsOfSurveyQuestions);
	}
	
	public int calculateScore(SurveyQuestions resultOfSurveyQuestions) {
		
		/*
		for(int i = 0; i < questions.size(); i++) {
			finalScore += questions.get(i).getQuestionScore();
		}
		
		*/
		
		return resultOfSurveyQuestions.getTotalScore();
	}
	
	public void saveSelectedTeam(String selectedTeam) {
		teamSelected = selectedTeam;
	}
	
	public String printTeam() {
		return teamSelected;
	}

}
