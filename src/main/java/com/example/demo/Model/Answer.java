package com.example.demo.Model;

import java.util.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class Answer {
	
	private int answerid;
	private Question question;
	private int answer;
	
	public Answer() {
		
	}
	
	public Answer(int answerid, Question question, int answer) {
		this.answerid = answerid;
		this.question = question;
		this.answer = answer;
	}

	public int getAnswerid() {
		return answerid;
	}

	public void setAnswerid(int answerid) {
		this.answerid = answerid;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}
	
	

}
