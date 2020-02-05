package com.example.demo.Model;

import java.util.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class Survey {
	
	private int surveyid;
	private SurveyType typeOfSurvey;
	private String name;
	
	public Survey() {
		
	}
	
	public Survey(int surveyid, SurveyType typeOfSurvey, String name) {
		this.surveyid = surveyid;
		this.typeOfSurvey = typeOfSurvey;
		this.name = name;
	}
	
	public int getSurveyid() {
		return surveyid;
	}

	public void setSurveyid(int surveyid) {
		this.surveyid = surveyid;
	}
	
	public SurveyType getTypeOfSurvey() {
		return typeOfSurvey;
	}
	public void setTypeOfSurvey(SurveyType typeOfSurvey) {
		this.typeOfSurvey = typeOfSurvey;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
