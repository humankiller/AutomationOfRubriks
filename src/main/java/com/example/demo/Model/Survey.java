package com.example.demo.Model;

import java.util.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Survey {
	
	private SurveyType typeOfSurvey;
	private String name;
	
	public Survey() {
		
	}
	
	public Survey(SurveyType typeOfSurvey, String name) {
		this.typeOfSurvey = typeOfSurvey;
		this.name = name;
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
