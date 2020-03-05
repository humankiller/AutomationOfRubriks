package com.example.demo.Model;

import java.util.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class QuestionType {
	
	private int questiontypeid;
	private String type; // change this to "type" to match tblquestiontype
	private String description;
	private int numberOfOptions;
	
	public QuestionType() {
		
	}
	
	public QuestionType(int questiontypeid, String type, String description, int numberOfOptions) {
		this.questiontypeid = questiontypeid;
		this.type = type;
		this.description = description;
		this.numberOfOptions = numberOfOptions;
	}

	public int getQuestiontypeid() {
		return questiontypeid;
	}

	public void setQuestiontypeid(int questiontypeid) {
		this.questiontypeid = questiontypeid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNumberOfOptions() {
		return numberOfOptions;
	}

	public void setNumberOfOptions(int numberOfOptions) {
		this.numberOfOptions = numberOfOptions;
	}
	
	

}
