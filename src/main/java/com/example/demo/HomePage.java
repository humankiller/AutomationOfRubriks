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

	@GetMapping("/teamid={teamid}/surveyid={surveyid}/survey")
	public SurveyQuestions getSelectedSurvey(@PathVariable("surveyid") int surveyid) {
		
		SurveyQuestions surveyQuestionsToReturn = new SurveyQuestions();
		
		surveyQuestionsToReturn = surveyManageService.getSurvey(surveyid);
		
		return surveyQuestionsToReturn;
	}
	
	@GetMapping("/teams")
	public List<Team> teamsToReturn() {
		return surveyManageService.findAllTeams();
	}
	
	@PostMapping("/teamid={teamid}/surveyid={surveyid}/results")
	public ResponseEntity<Boolean> setScores(@PathVariable("teamid") int teamid, @PathVariable("surveyid") int surveyid, @RequestBody SurveyQuestions resultOfSurveyQuestions) {
		
		surveyQuestionsResults = resultOfSurveyQuestions;
		
		boolean saveScoresStatus = surveyManageService.saveScores(teamid, surveyid, resultOfSurveyQuestions);
		surveyManageService.calculateScore(resultOfSurveyQuestions);
		
		return new ResponseEntity<Boolean>(saveScoresStatus, HttpStatus.OK);
	}
	
	
	@GetMapping("teamid={teamid}/surveyid={surveyid}/results")
	public double surveyResults() {
		return surveyManageService.calculateScore(surveyQuestionsResults);
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
	
	@GetMapping("/teamid={teamid}/surveys")
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