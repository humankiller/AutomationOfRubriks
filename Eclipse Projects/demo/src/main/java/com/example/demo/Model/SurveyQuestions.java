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
		questions.add(new Question(++questionCount, "The team ensures stories are written to deliver vertical slices of functionality", -1));
		questions.add(new Question(++questionCount, "The team right-sizes stories so they can be completed in 1-3 days", -1));
		questions.add(new Question(++questionCount, "The team is cross-functional, with all the skills necessary to deliver product value", -1));
		questions.add(new Question(++questionCount, "Individual team members may have specialized skills and areas of focus, but accountability belongs to team as a whole", -1));
		questions.add(new Question(++questionCount, "The team takes accountability for meeting their sprint commitments", -1));
		questions.add(new Question(++questionCount, "Team members raise impediments", -1));
		questions.add(new Question(++questionCount, "The team swarms (works collaboratively) when needed to complete sprint work", -1));
		questions.add(new Question(++questionCount, "The team collaborates", -1));
		questions.add(new Question(++questionCount, "The team has open communications", -1));
		questions.add(new Question(++questionCount, "The team has fun", -1));
		questions.add(new Question(++questionCount, "The team confronts challenges impacting the team", -1));
	}
	
	public List<Question> findAll() {
		return questions;
	}
}
