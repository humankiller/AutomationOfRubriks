package com.example.demo.Model;

import java.sql.*; // Import java SQL library
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseConnection {
	
	//public static Connection con;
	
	//public static Statement statement;
	
	public static void main(String[] args) throws SQLException {
		
		
		//statement = establishConnection();
		
		/*
		 * 1. Create ResultSet object to hold the results
		 * 2. Call function executeQuery on Statement object and pass in SQL code:
		 * 		a. "SELECT *" means select all
		 * 		b. "FROM question_types" means from the table that you give it (in this case question_types)
		 */
		
		/*
	
		ResultSet results = statement.executeQuery("SELECT * FROM question_types");
		
		while(results.next()) { // While there are more rows in the table...
			
			String data = results.getString(1);
			System.out.println("Fetching data by column index for row " + results.getRow() + " : " + data);
			
			data = results.getString("name"); // call getString function w/ parameter "name" (column w/ data type string in database)
			System.out.println("Fetching data by column name for row " + results.getRow() + " : " + data);
			
			data = results.getString("description"); // call getString function w/ parameter "description" (column w/ data type string in database)
			System.out.println("Fetching data by column name for row " + results.getRow() + " : " + data);
			
			Date date = results.getDate("created"); // call getDate function w/ parameter "created" (column w/ data type timezone in database)
			System.out.println("Fetching data by column name for row " + results.getRow() + " : " + date);
			
		}
		
		*/
		
		//int[] questionIDs = {3, 4, 5}; // TESTING
		
		//SurveyQuestions surveyQuestions = buildSurveyQuestionsObject(6); // TESTING
		
		
	}
	
	public List<Team> getTeamNames() {
		
		List<Team> teamNames = new ArrayList<>();
		
		Connection con = null;
		
		Statement statement = null;
		
		try {
			
			con = DriverManager.getConnection("jdbc:postgresql://ec2-107-22-239-155.compute-1.amazonaws.com/daknuflimm0laj", "utufnbbozfaphi", "4a7b61f6d36d53dd87d281cc3786acbe2bdcaf7470f7368b46ac370c1c5dbd95");
			
			statement = con.createStatement(); // Create a "Statement" object to do operations on
			
			if(con != null) { // Error checking
				System.out.println("Database Connected");
			}
			
			
		} catch (SQLException e) {
			System.out.println("Could not connect to the database. HERE");
			
		}
		
		/*
		
		if(con == null) {
			
			statement = establishConnection();
			
		}
		
		*/
		
		try {
			
			/*
			 * 1. Create ResultSet object to hold the results
			 * 2. Call function executeQuery on Statement object and pass in SQL code:
			 * 		a. "SELECT *" means select all
			 * 		b. "FROM question_types" means from the table that you give it (in this case question_types)
			 */
			ResultSet results = statement.executeQuery("SELECT * FROM teams");
			
			while(results.next()) { // While there are more rows in the table...
				
				String data = results.getString(1);
				System.out.println("Fetching data by column index for row " + results.getRow() + " : " + data);
				
				data = results.getString("name"); // call getString function w/ parameter "name" (column w/ data type string in database)
				System.out.println("Fetching data by column name for row " + results.getRow() + " : " + data);
				
				Team teamToAdd = new Team();
				
				teamToAdd.setTeamName(data);
				
				teamNames.add(teamToAdd);
				
			}
			
			//con.close();
			
			
		} catch (SQLException e) {
			
			System.out.println("Could not retrieve data from the database " + e.getMessage());
		} finally {
			
			if(con != null) {
				
				try {
					
					System.out.println("Closing connection...");
					
					con.close();
					
				} catch(SQLException e) {
					
				}
				
			}
			
			if(statement != null) {
				
				try {
					
					System.out.println("Closing statement...");
					
					statement.close();
					
				} catch(SQLException e) {
					
				}
				
			}
			
		}
		
		return teamNames;
		
		
	}
	
	public static SurveyQuestions buildSurveyQuestionsObject(int id) {
		
		SurveyQuestions surveyQuestionsToReturn = new SurveyQuestions();
		
		Connection con = null;
		
		Statement statement = null;
		
		try {
			
			con = DriverManager.getConnection("jdbc:postgresql://ec2-107-22-239-155.compute-1.amazonaws.com/daknuflimm0laj", "utufnbbozfaphi", "4a7b61f6d36d53dd87d281cc3786acbe2bdcaf7470f7368b46ac370c1c5dbd95");
			
			statement = con.createStatement(); // Create a "Statement" object to do operations on
			
			if(con != null) { // Error checking
				System.out.println("Database Connected");
			}
			
			
		} catch (SQLException e) {
			System.out.println("Could not connect to the database. HERE");
			
		}
		
		/*
		
		if(con == null) {
			
			statement = establishConnection();
			
		}
		
		*/
		
		try {
			
			/*
			 * 1. Create ResultSet object to hold the results
			 * 2. Call function executeQuery on Statement object and pass in SQL code:
			 * 		a. "SELECT *" means select all
			 * 		b. "FROM question_types" means from the table that you give it (in this case question_types)
			 */
			ResultSet results = statement.executeQuery("SELECT * FROM surveys");
			
			while(results.next()) { // While there are more rows in the table...
				
				int tempSurveyId = results.getInt("id");
				
				if(tempSurveyId == id) {
					
					/* First, we need to get the survey object */
					
					int surveyTypesId = results.getInt("survey_types_id");
					
					System.out.println("Here is the survey type id: " + Integer.toString(surveyTypesId));
					
					SurveyType surveyType = getSurveyType(surveyTypesId);
					
					System.out.println("Im here2");
					
					String surveyName = results.getString("name");
					
					Survey survey = new Survey(surveyType, surveyName);
					
					surveyQuestionsToReturn.setSurvey(survey);
					
					/* Next, we need the ArrayList of question objects */ 
					
					String stringOfQuestionIdsBeforeConversion = results.getString("array_of_questions_ids");
					
					String[] spaces = stringOfQuestionIdsBeforeConversion.split(" ");
					
					int[] arrayOfQuestionIds = new int[spaces.length];
					
					for(int i = 0; i < spaces.length; i++) {
						
						String questionIdAsString = spaces[i];
						
						arrayOfQuestionIds[i] = (Integer.parseInt(questionIdAsString));
						
					}
					
					List<Question> questionsInSurvey = new ArrayList<>();
					
					questionsInSurvey = getQuestions(arrayOfQuestionIds);
					
					surveyQuestionsToReturn.setQuestions(questionsInSurvey);
					
					/* Next, we need the number of questions */
					
					int questionNumber = questionsInSurvey.size();
					
					surveyQuestionsToReturn.setQuestionNumber(questionNumber);
					
					/* Last, we need the total score (we'll set it to 0 */
					
					int totalScore = 0;
					
					surveyQuestionsToReturn.setTotalScore(totalScore);
					
				}
				
			}
			
			//con.close();
			
		} catch (SQLException e) {
			
			System.out.println("Could not retrieve data from the database " + e.getMessage());
			
		} finally {
			
			if(con != null) {
				
				try {
					
					System.out.println("Closing connection...");
					
					con.close();
					
				} catch(SQLException e) {
					
				}
				
			}
			
			if(statement != null) {
				
				try {
					
					System.out.println("Closing statement...");
					
					statement.close();
					
				} catch(SQLException e) {
					
				}
				
			}
			
		}
		
		return surveyQuestionsToReturn;
		
		//ResultSet results = statement.executeQuery("")
		
		
	}
	
	
	// A method that sets up the connection so that we can just call a function
	public static Statement establishConnection() {
		
		Connection con = null;
		
		Statement statement = null;
		
		try {
			
			con = DriverManager.getConnection("jdbc:postgresql://ec2-107-22-239-155.compute-1.amazonaws.com/daknuflimm0laj", "utufnbbozfaphi", "4a7b61f6d36d53dd87d281cc3786acbe2bdcaf7470f7368b46ac370c1c5dbd95");
			
			statement = con.createStatement(); // Create a "Statement" object to do operations on
			
			if(con != null) { // Error checking
				System.out.println("Database Connected");
			}
			
			return statement;
			
		} catch (SQLException e) {
			System.out.println("Could not connect to the database. HERE");
			
			return null;
		}
		
	}
	
	public static List<Question> getQuestions(int[] questionIndexes) {
		
		List<Question> questions = new ArrayList<>();
		
		Connection con = null;
		
		Statement statement = null;
		
		try {
			
			con = DriverManager.getConnection("jdbc:postgresql://ec2-107-22-239-155.compute-1.amazonaws.com/daknuflimm0laj", "utufnbbozfaphi", "4a7b61f6d36d53dd87d281cc3786acbe2bdcaf7470f7368b46ac370c1c5dbd95");
			
			statement = con.createStatement(); // Create a "Statement" object to do operations on
			
			if(con != null) { // Error checking
				System.out.println("Database Connected");
			}
			
			
		} catch (SQLException e) {
			System.out.println("Could not connect to the database. HERE");
			
		}
		
		/*
		
		if(con == null) {
			
			statement = establishConnection();
			
		}
		
		*/
		
		try {
			
			ResultSet results = statement.executeQuery("SELECT * FROM questions");
			
			while(results.next()) {
				
				Question questionToAdd = new Question();
				
				int id = results.getInt("id");
				
				String idAsString = Integer.toString(id);
				
				System.out.println("Here's the ID: " + idAsString);
				
				for(int i = 0; i < questionIndexes.length; i++) {
					
					if(id == questionIndexes[i]) {
						
						String question = results.getString("question");
						
						System.out.println("Here is a question: " + question);
						
						int questionTypeId = results.getInt("question_types_id");
						
						String questionTypeAsString = Integer.toString(questionTypeId);
						
						System.out.println("Here is the Question Type ID: " + questionTypeAsString);
						
						QuestionType questionType = getQuestionType(questionTypeId);
						
						questionToAdd.setTypeOfQuestion(questionType);
						
						questionToAdd.setQuestionScore(-1); // HARDCOODED
						
						questionToAdd.setQuestion(question);
						
						questions.add(questionToAdd);
						
					}
					
				}
				
			}
			
			//con.close();
			
		} catch (SQLException e) {
			System.out.println("QUESTION ERROR");
			
		} finally {
			
			if(con != null) {
				
				try {
					
					System.out.println("Closing connection...");
					
					con.close();
					
				} catch(SQLException e) {
					
				}
				
			}
			
			if(statement != null) {
				
				try {
					
					System.out.println("Closing statement...");
					
					statement.close();
					
				} catch(SQLException e) {
					
				}
				
			}
			
		}
		
		System.out.println("The questions have been returned.");
		
		return questions;
	}
	
	public static QuestionType getQuestionType(int id) {
		
		String idAsString = Integer.toString(id);
		
		Connection con = null;
		
		Statement statement = null;
		
		try {
			
			con = DriverManager.getConnection("jdbc:postgresql://ec2-107-22-239-155.compute-1.amazonaws.com/daknuflimm0laj", "utufnbbozfaphi", "4a7b61f6d36d53dd87d281cc3786acbe2bdcaf7470f7368b46ac370c1c5dbd95");
			
			statement = con.createStatement(); // Create a "Statement" object to do operations on
			
			if(con != null) { // Error checking
				System.out.println("Database Connected");
			}
			
			
		} catch (SQLException e) {
			System.out.println("Could not connect to the database. HERE");
			
		}
		
		/*
		
		if(con == null) {
			
			statement = establishConnection();
			
		}
		
		*/
		
		QuestionType questionTypeToReturn = new QuestionType();
		
		try {
			ResultSet results = statement.executeQuery("SELECT * FROM question_types");
			
			while(results.next()) {
				
				int tempId = results.getInt("id");
				
				if(tempId == id) {
					
					String questionTypeName = results.getString("name");
					
					questionTypeToReturn.setName(questionTypeName);
					
					String questionTypeDesc = results.getString("description");
					
					questionTypeToReturn.setDescription(questionTypeDesc);
					
				}
				
			}
			
			//con.close();
			
		} catch (SQLException e) {
			System.out.println("QUESTION TYPE ERROR");
			
		} finally {
			
			if(con != null) {
				
				try {
					
					System.out.println("Closing connection...");
					
					con.close();
					
				} catch(SQLException e) {
					
				}
				
			}
			
			if(statement != null) {
				
				try {
					
					System.out.println("Closing statement...");
					
					statement.close();
					
				} catch(SQLException e) {
					
				}
				
			}
			
		}
		
		return questionTypeToReturn;
		
	}
	
	public static SurveyType getSurveyType(int id) {
		
		String idAsString = Integer.toString(id);
		
		Connection con = null;
		
		Statement statement = null;
		
		try {
			
			con = DriverManager.getConnection("jdbc:postgresql://ec2-107-22-239-155.compute-1.amazonaws.com/daknuflimm0laj", "utufnbbozfaphi", "4a7b61f6d36d53dd87d281cc3786acbe2bdcaf7470f7368b46ac370c1c5dbd95");
			
			statement = con.createStatement(); // Create a "Statement" object to do operations on
			
			if(con != null) { // Error checking
				System.out.println("Database Connected");
			}
			
			
		} catch (SQLException e) {
			System.out.println("Could not connect to the database. HERE");
			
		}
		
		/*
		
		if(con == null) {
			
			statement = establishConnection();
			
		}
		
		*/
		
		System.out.println("Im here3");
		
		SurveyType surveyTypeToReturn = new SurveyType();
		
		try {
			ResultSet results = statement.executeQuery("SELECT * FROM question_types");
			
			while(results.next()) {
				
				int tempId = results.getInt("id");
				
				if(tempId == id) {
					
					String surveyTypeName = results.getString("name");
					
					surveyTypeToReturn.setName(surveyTypeName);
					
					String surveyTypeDesc = results.getString("description");
					
					surveyTypeToReturn.setDescription(surveyTypeDesc);
					
				}
				
			}
			
			//con.close();
			
		} catch (SQLException e) {
			System.out.println("SURVEY TYPE ERROR");
			
		} finally {
			
			if(con != null) {
				
				try {
					
					System.out.println("Closing connection...");
					
					con.close();
					
				} catch(SQLException e) {
					
				}
				
			}
			
			if(statement != null) {
				
				try {
					
					System.out.println("Closing statement...");
					
					statement.close();
					
				} catch(SQLException e) {
					
				}
				
			}
			
		}
		
		return surveyTypeToReturn;
		
	}
		
}
	
