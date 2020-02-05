package com.example.demo.Model;

import java.util.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize
public class SurveyQuestions {
	
	private Survey survey;
	
	private List<Question> questions;
	
	private double totalScore;
	
	public SurveyQuestions() {
		
	}
	
	public SurveyQuestions(Survey survey, List<Question> questions, double totalScore) {
		this.survey = survey;
		this.questions = questions;
		this.totalScore = totalScore;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	public double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}
	
	
	
	
	
	
}