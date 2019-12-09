package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.example.demo.Model.*;

@CrossOrigin(origins = {"https://automation-of-rubriks.herokuapp.com", "http://localhost:3000"})
@RestController
public class HomePage {
	
	private SurveyQuestions surveyQuestionsResults;
	
	@Autowired
	private SurveyService surveyManageService;
	
	@GetMapping("/survey")
	public SurveyQuestions surveyQuestionsToReturn() {
		return surveyManageService.findAll();
	}
	
	@GetMapping("/teams")
	public List<Team> teamsToReturn() {
		return surveyManageService.findAllTeams();
	}
	
	@PutMapping("/results")
	public ResponseEntity<SurveyQuestions> setScores(@RequestBody SurveyQuestions resultOfSurveyQuestions) {
		
		/*
		for(int i = 0; i < questionScores.length; i++) {
			System.out.println(questionScores[i]);
		}
		
		List<Integer> scores = Arrays.asList(questionScores);
		*/
		
		surveyQuestionsResults = resultOfSurveyQuestions;
		
		surveyManageService.saveScores(resultOfSurveyQuestions);
		surveyManageService.calculateScore(resultOfSurveyQuestions);
		
		return new ResponseEntity<SurveyQuestions>(resultOfSurveyQuestions, HttpStatus.OK);
	}
	
	
	@GetMapping("/results")
	public int surveyResults() {
		return surveyManageService.calculateScore(surveyQuestionsResults);
	}
	
	@PutMapping("/teamselect")
	public ResponseEntity<String> test(@RequestBody String selectedTeam) {
		surveyManageService.saveSelectedTeam(selectedTeam);
		return new ResponseEntity<String>(selectedTeam, HttpStatus.OK);
	}
	
	@GetMapping("/teamselected")
	public String printSelectedTeam() {
		return surveyManageService.printTeam();
	}
	
	@RequestMapping("/home")
	public String sayHello() {
		return "You have reached the Home Page for StateFarm's Rubriks";
	}
	
	/*
	@RequestMapping("/survey")
	public String surveyPage() {
		return "This is the Survey Page, where people will take the survey!";
	}
	*/
	
}