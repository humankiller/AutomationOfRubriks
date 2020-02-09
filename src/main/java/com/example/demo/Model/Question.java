package com.example.demo.Model;

import java.util.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

// I need a class that will define all the attributes about a question
@JsonSerialize
public class Question {
	
	private int questionid;
	private QuestionType typeOfQuestion; // This is the type of question that the question is (i.e. 5 point type)
	private String question; // This is the actual question
	private int questionScore; // This is the score that the user gives for that question(Default value will be -1)
	
	// Need an empty constructor
	public Question() {
		
	}
	
	// Need an overloaded constructor to set elements
	public Question(QuestionType typeOfQuestion, String ques, int quesScore) {
		this.typeOfQuestion = typeOfQuestion;
		this.question = ques;
		this.questionScore = quesScore;
	}
	
	// Need getter methods to expose API on port 8081
	
	public int getQuestionid() {
		return questionid;
	}

	public void setQuestionid(int questionid) {
		this.questionid = questionid;
	}
	
	public String getQuestion() {
		return question;
	}

	public int getQuestionScore() {
		return questionScore;
	}
	
	// Need setter methods to set individual fields
	
	public void setQuestion(String ques) {
		this.question = ques;
	}
	
	public void setQuestionScore(int quesScore) {
		this.questionScore = quesScore;
	}

	public QuestionType getTypeOfQuestion() {
		return typeOfQuestion;
	}

	public void setTypeOfQuestion(QuestionType typeOfQuestion) {
		this.typeOfQuestion = typeOfQuestion;
	}
	
	
	
	
	
}