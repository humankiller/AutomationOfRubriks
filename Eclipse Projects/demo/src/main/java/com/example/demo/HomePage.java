package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.example.demo.Model.Question;
import com.example.demo.Model.SurveyQuestions;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class HomePage {
	
	@Autowired
	private SurveyQuestions surveyManageService;
	
	@GetMapping("/survey")
	public List<Question> getAllQuestions() {
		return surveyManageService.findAll();
	}
	
	@PutMapping("/results")
	public ResponseEntity<Integer[]> setScores(@RequestBody Integer[] questionScores) {
		
		for(int i = 0; i < questionScores.length; i++) {
			System.out.println(questionScores[i]);
		}
		List<Integer> scores = Arrays.asList(questionScores);
		surveyManageService.saveScores(scores);
		surveyManageService.calculateScore();
		
		return new ResponseEntity<Integer[]>(questionScores, HttpStatus.OK);
	}
	
	
	@GetMapping("/results")
	public int surveyResults() {
		return surveyManageService.calculateScore();
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
