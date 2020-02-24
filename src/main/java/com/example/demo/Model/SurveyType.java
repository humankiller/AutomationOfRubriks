package com.example.demo.Model;

import java.util.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class SurveyType {
	
	private int surveytypeid;
	private String type;
	private String description;
	
	public SurveyType() {
		
	}
	
	public SurveyType(int surveytypeid, String type, String description) {
		this.surveytypeid = surveytypeid;
		this.type = type;
		this.description = description;
	}

	public int getSurveytypeid() {
		return surveytypeid;
	}

	public void setSurveytypeid(int surveytypeid) {
		this.surveytypeid = surveytypeid;
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

	
}
