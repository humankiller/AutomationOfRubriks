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
	
	private SurveyType surveytype;
	private List<Question> questions;
	
	public Template() {
		
	}
	
	public Template(SurveyType surveytype, List<Question> questions) {
		this.surveytype = surveytype;
		this.questions = questions;
	}

	public SurveyType getSurveytype() {
		return surveytype;
	}

	public void setSurveytype(SurveyType surveytype) {
		this.surveytype = surveytype;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	
}
