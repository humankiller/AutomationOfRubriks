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
		questions.add(new Question(++questionCount, "Question1", 1));
		questions.add(new Question(++questionCount, "Question2", 2));
		questions.add(new Question(++questionCount, "Question3", 3));
		questions.add(new Question(++questionCount, "Question4", 4));
		questions.add(new Question(++questionCount, "Question5", 5));
	}
	
	public List<Question> findAll() {
		return questions;
	}
}
