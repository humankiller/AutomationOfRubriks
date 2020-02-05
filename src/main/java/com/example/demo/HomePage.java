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
	private Survey selectedSurvey = new Survey();
	
	@Autowired
	private SurveyService surveyManageService;
	
	@PutMapping("/survey")
	public ResponseEntity<Boolean> recieveSelectedSurvey(@RequestBody Survey selectedSurveyFromFrontend) {
		selectedSurvey.setSurveyid(selectedSurveyFromFrontend.getSurveyid());
		System.out.println("Here's the surveyid: " + Integer.toString(selectedSurvey.getSurveyid()));
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	@GetMapping("/survey")
	public SurveyQuestions getSelectedSurvey() {
		
		SurveyQuestions surveyQuestionsToReturn = new SurveyQuestions();
		
		surveyQuestionsToReturn = surveyManageService.getSurvey(selectedSurvey.getSurveyid());
		
		return surveyQuestionsToReturn;
	}
	
	@GetMapping("/teams")
	public List<Team> teamsToReturn() {
		return surveyManageService.findAllTeams();
	}
	
	@PutMapping("/results")
	public ResponseEntity<SurveyQuestions> setScores(@RequestBody SurveyQuestions resultOfSurveyQuestions) {
		
		surveyQuestionsResults = resultOfSurveyQuestions;
		
		surveyManageService.saveScores(resultOfSurveyQuestions);
		surveyManageService.calculateScore(resultOfSurveyQuestions);
		
		return new ResponseEntity<SurveyQuestions>(resultOfSurveyQuestions, HttpStatus.OK);
	}
	
	
	@GetMapping("/results")
	public double surveyResults() {
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
	
	@GetMapping("/tblusers")
	public List<User> usersToReturn() {
		return surveyManageService.getUsers();
	}
	
	@GetMapping("/surveys")
	public List<Survey> surveysToReturn() {
		return surveyManageService.getSurveys();
	}
	
	@PostMapping("/addteam")
	public ResponseEntity<Boolean> addTeam(@RequestBody Team teamToInsert) {
		
		Boolean insertStatus =  surveyManageService.addTeam(teamToInsert);
		
		return new ResponseEntity<Boolean>(insertStatus, HttpStatus.OK);
	}
	
	@PutMapping("/editteam/{teamNameToEdit}")
	public ResponseEntity<Boolean> editTeam(@RequestBody Team newTeamData, @PathVariable("teamNameToEdit") String teamNameToEdit) {
		
		Boolean editStatus = surveyManageService.editTeam(newTeamData, teamNameToEdit);
		
		return new ResponseEntity<Boolean>(editStatus, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteteam/{teamNameToDelete}")
	public ResponseEntity<Boolean> deleteTeam(@PathVariable("teamNameToDelete") String teamNameToDelete) {
		
		Boolean deleteStatus = surveyManageService.deleteTeam(teamNameToDelete);
		
		return new ResponseEntity<Boolean>(deleteStatus, HttpStatus.OK);
		
	}
	
}