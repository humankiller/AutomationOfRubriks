package com.example.demo.Model;

import java.util.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize
public class Template {
	
	private SurveyType typeOfSurvey;
	private List<Question> questions;
	
	public Template() {
		
	}
	
	public Template(SurveyType typeOfSurvey, List<Question> questions) {
		this.typeOfSurvey = typeOfSurvey;
		this.questions = questions;
	}

	public SurveyType getTypeOfSurvey() {
		return typeOfSurvey;
	}

	public void setTypeOfSurvey(SurveyType typeOfSurvey) {
		this.typeOfSurvey = typeOfSurvey;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	
}
