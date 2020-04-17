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

	@GetMapping("/surveytypes/showhidden={showHidden}")
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
	
	@GetMapping("/admin/report/time1={time1}/time2={time2}/surveyid={surveyid}")
	public ResponseEntity<ArrayList<SurveyQuestions>> fetchReport(@PathVariable("time1") String time1, @PathVariable("time2") String time2, @PathVariable("surveyid") int surveyid) {
		
		ArrayList<SurveyQuestions> report = new ArrayList<>();
		
		report = surveyManageService.fetchReportWithTime(surveyid, time1, time2);
		
		return new ResponseEntity<ArrayList<SurveyQuestions>>(report, HttpStatus.OK);
		
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
	
	@GetMapping("/fetchsurveytype/surveytypeid={surveytypeid}")
	public ResponseEntity<SurveyType> fetchSurveyType(@PathVariable("surveytypeid") int surveytypeid) {
		
		SurveyType surveyTypeToReturn = surveyManageService.fetchSurveyType(surveytypeid);
		
		return new ResponseEntity<SurveyType>(surveyTypeToReturn, HttpStatus.OK);
	}
	
	@PutMapping("/admin/deletetemplate/surveytypeid={surveytypeid}")
	public ResponseEntity<Boolean> deleteTemplate(@PathVariable("surveytypeid") int surveytypeid) {
		
		Boolean deleteTemplateStatus = surveyManageService.deleteTemplate(surveytypeid);
		
		return new ResponseEntity<Boolean>(deleteTemplateStatus, HttpStatus.OK);
		
	}
	
	@PutMapping("/admin/activatetemplate/surveytypeid={surveytypeid}")
	public ResponseEntity<Boolean> activateTemplate(@PathVariable("surveytypeid") int surveytypeid) {
		
		Boolean activateTemplateStatus = surveyManageService.activateTemplate(surveytypeid);
		
		return new ResponseEntity<Boolean>(activateTemplateStatus, HttpStatus.OK);
		
	}
	
	@GetMapping("/admin/gettemplateinformation/surveytypeid={surveytypeid}")
	public ResponseEntity<Template> getTemplateInformation(@PathVariable("surveytypeid") int surveytypeid) {
		
		Template template = surveyManageService.getTemplateInformation(surveytypeid);
		
		return new ResponseEntity<Template>(template, HttpStatus.OK);
	}
	
	@PutMapping("/admin/edittemplate")
	public ResponseEntity<Boolean> editTemplate(@RequestBody Template updatedTemplate) {
		
		Boolean completionStatus = surveyManageService.editTemplate(updatedTemplate);
				
		return new ResponseEntity<Boolean>(completionStatus, HttpStatus.OK);
	}
	
	@PostMapping("/admin/insertquestiontype")
	public ResponseEntity<Boolean> insertQuestionType(@RequestBody QuestionType typeOfQuestion) {
		
		Boolean completionStatus = surveyManageService.insertQuestionType(typeOfQuestion);
		
		return new ResponseEntity<Boolean>(completionStatus, HttpStatus.OK);
	}
	
	@PutMapping("/admin/editquestiontype/{questionTypeToEdit}")
	public ResponseEntity<Boolean> editQuestionType(@RequestBody QuestionType editedTypeOfQuestion, @PathVariable("questionTypeToEdit") String questionTypeToEdit) {
		
		Boolean completionStatus = surveyManageService.editQuestionType(editedTypeOfQuestion, questionTypeToEdit);
		
		return new ResponseEntity<Boolean>(completionStatus, HttpStatus.OK);
	}
	
	@PostMapping("/admin/questiontypeid={questiontypeid}/addquestion")
	public ResponseEntity<Boolean> insertQuestion(@RequestBody Question questionToInsert, @PathVariable("questiontypeid") int questiontypeid) {
		
		Boolean completionStatus = surveyManageService.insertQuestion(questionToInsert, questiontypeid);
		
		return new ResponseEntity<Boolean>(completionStatus, HttpStatus.OK);
	}
	
	@PutMapping("/admin/editquestion")
	public ResponseEntity<Boolean> editQuestion(@RequestBody Question newQuestionData) {
		
		Boolean completionStatus = surveyManageService.editQuestion(newQuestionData);
		
		return new ResponseEntity<Boolean>(completionStatus, HttpStatus.OK);
	}
	
	@PutMapping("/admin/editsurvey/{surveyNameToEdit}")
	public ResponseEntity<Boolean> editSurvey(@RequestBody Survey newSurveyData, @PathVariable("surveyNameToEdit") String surveyNameToEdit) {
		
		Boolean editStatus = surveyManageService.editSurveyName(newSurveyData, surveyNameToEdit);
		
		return new ResponseEntity<Boolean>(editStatus, HttpStatus.OK);
	}
	
	@PutMapping("/admin/deletesurvey/{surveyid}")
	public ResponseEntity<Boolean> deleteSurvey(@PathVariable("surveyid") int surveyid) {
		
		Boolean deleteSurvey = surveyManageService.deleteSurveyName(surveyid);
		
		return new ResponseEntity<Boolean>(deleteSurvey, HttpStatus.OK);
		
	}
	
	@PostMapping("/admin/addsurvey/{surveytypeid}")
	public ResponseEntity<Boolean> insertSurvey(@RequestBody Survey surveyToInsert, @PathVariable("surveytypeid") int surveytypeid) {
		
		Boolean completionStatus = surveyManageService.insertSurvey(surveyToInsert, surveytypeid);
		
		return new ResponseEntity<Boolean>(completionStatus, HttpStatus.OK);
	}
}