package com.example.demo.Model;

import java.util.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class TakenSurvey {
	
	private int takensurveyid;
	private double totalScore;
	private ArrayList<Answer> answers;
	
	public TakenSurvey() {
		
	}
	
	public TakenSurvey(int takensurveyid, int totalScore, ArrayList<Answer> answers) {
		this.takensurveyid = takensurveyid;
		this.totalScore = totalScore;
		this.answers = answers;
	}

	public int getTakensurveyid() {
		return takensurveyid;
	}

	public void setTakensurveyid(int takensurveyid) {
		this.takensurveyid = takensurveyid;
	}

	public double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}
	
	

}
