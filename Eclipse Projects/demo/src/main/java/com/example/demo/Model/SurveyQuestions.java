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
public class SurveyQuestions {
	
	private static List<Question> questions = new ArrayList<>();
	
	private static int questionCount = 0;
	
	// This is a method to test API exposure
	static {
		questions.add(new Question(++questionCount, "The development team organizes itself to accomplish the sprint goals", -1));
		questions.add(new Question(++questionCount, "The team determines how to deliver small increments of potentially releasable functionality", -1));
		questions.add(new Question(++questionCount, "The team ensures stores are written to deliver vertical slices of functionality", -1));
		questions.add(new Question(++questionCount, "Question4", -1));
		questions.add(new Question(++questionCount, "Question5", -1));
	}
	
	public List<Question> findAll() {
		return questions;
	}
}
