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
	
	// TODO finish implementing the admin login
	
	//@PutMapping("/admin/login")
	//public boolean adminLogin(@RequestBody ) {
		
	//}
	
	@GetMapping("/admin/login/username={username}/password={password}")
	public ResponseEntity<Boolean> login(@PathVariable("username") String username, @PathVariable("password") String password) {
		
		Boolean confirmedLogin = surveyManageService.verifyAdminLogin(username, password);
		
		return new ResponseEntity<Boolean>(confirmedLogin, HttpStatus.OK);
	}

	@GetMapping("/teamid={teamid}/surveytypes/showhidden={showHidden}")
	public List<SurveyType> surveyTypesToReturn(@PathVariable("showHidden") boolean showHidden) {
		System.out.println("I'm HERE!");
		return surveyManageService.getSurveyTypes(showHidden);
	}
	
	@GetMapping("/teamid={teamid}/surveytypeid={surveytypeid}/surveys")
	public List<Survey> surveysToReturn(@PathVariable("surveytypeid") int surveytypeid) {
		return surveyManageService.getSurveys(surveytypeid);
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
	
	@GetMapping("/admin/reportoptions")
	public ResponseEntity<ReportOptions> fetchReportOptions() {
		
		ReportOptions returnReportOptions = surveyManageService.fetchReportOptions();
		
		return new ResponseEntity<ReportOptions>(returnReportOptions, HttpStatus.OK);
	}
	
	@GetMapping("/admin/report/teamid={teamid}/surveyid={surveyid}")
	public ArrayList<SurveyQuestions> fetchReport(@PathVariable("teamid") int teamid, @PathVariable("surveyid") int surveyid) {
		
		ArrayList<SurveyQuestions> report = new ArrayList<>();
		
		report = surveyManageService.fetchReport(teamid, surveyid);
		
		return report;
		
	}
	
	@GetMapping("/admin/createtemplate/getquestiontypes")
	public ArrayList<QuestionType> getQuestionTypes() {
		
		ArrayList<QuestionType> questiontypes = surveyManageService.getQuestionTypes();
		
		return questiontypes;
	}
	
	@GetMapping("/admin/createtemplate/getquestions/questiontypeid={questiontypeid}")
	public ArrayList<Question> getQuestionsWithQuestionTypeID(@PathVariable("questiontypeid") int questiontypeid) {
		
		ArrayList<Question> questions = surveyManageService.getQuestionsWithQuestionTypeID(questiontypeid);
		
		return questions;
	}
	
	@PostMapping("/admin/createtemplate")
	public ResponseEntity<Boolean> createTemplate(@RequestBody Template template) {
		
		Boolean createTemplateStatus = surveyManageService.createTemplate(template);
		
		return new ResponseEntity<Boolean>(createTemplateStatus, HttpStatus.OK);
	}
	
	@PutMapping("/admin/deletetemplate/surveytypeid={surveytypeid}")
	public ResponseEntity<Boolean> deleteTemplate(@PathVariable("surveytypeid") int surveytypeid) {
		
		System.out.println("Im here");
		
		Boolean deleteTemplateStatus = surveyManageService.deleteTemplate(surveytypeid);
		
		return new ResponseEntity<Boolean>(deleteTemplateStatus, HttpStatus.OK);
		
	}
	
}