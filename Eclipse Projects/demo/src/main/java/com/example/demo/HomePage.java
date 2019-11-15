package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.example.demo.Model.Question;
import com.example.demo.Model.SurveyQuestions;

@RestController
public class HomePage {
	
	@Autowired
	private SurveyQuestions surveyManageService;
	
	@GetMapping("/survey")
	public List<Question> getAllQuestions() {
		return surveyManageService.findAll();
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
	
	@RequestMapping("/results")
	public String resultsPage() {
		return "This is the Results Page, where the administrator will be able to view the statsitics!";
	}
}
