package com.example.demo.Model;

import java.util.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

// I need a class that will define all the attributes about a question
@JsonSerialize
public class Question {
	
	private int questionNumber; // This is the question #
	private String question; // This is the actual question
	private int questionScore; // This is the score that the user gives for that question(Default value will be -1)
	
	// Need an empty constructor
	public Question() {
		
	}
	
	// Need an overloaded constructor to set elements
	public Question(int quesNum, String ques, int quesScore) {
		this.questionNumber = quesNum;
		this.question = ques;
		this.questionScore = quesScore;
	}
	
	// Need getter methods to expose API on port 8081
	public int getQuestionNumber() {
		return questionNumber;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public int getQuestionScore() {
		return questionScore;
	}
	
	// Need setter methods to set individual fields
	public void setQuestionNumber(int quesNum) {
		this.questionNumber = quesNum;
	}
	
	public void setQuestion(String ques) {
		this.question = ques;
	}
	
	public void setQuestionScore(int quesScore) {
		this.questionScore = quesScore;
	}
	
	
	
	
	
}
