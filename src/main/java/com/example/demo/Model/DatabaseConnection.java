package com.example.demo.Model;

import java.sql.*; // Import java SQL library
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseConnection {
	
	public static void main(String[] args) throws SQLException {
		
	}
	
/*
 * Here is a test function that gets all of the users out of the data out of the database
 ********************************************************************************************************************************************************************
 */
	
	public List<User> getUsersFromDatabase() {
		
		List<User> users = new ArrayList<>();
		
		Connection con = null;
		
		Statement statement = null;
		
		try {
			con = DriverManager.getConnection("jdbc:postgresql://ec2-107-22-239-155.compute-1.amazonaws.com/daknuflimm0laj", "utufnbbozfaphi", "4a7b61f6d36d53dd87d281cc3786acbe2bdcaf7470f7368b46ac370c1c5dbd95");
			statement = con.createStatement(); // Create a "Statement" object to do operations on
			if(con != null) { // Error checking
				System.out.println("Database Connected");
			}
		} catch (SQLException e) {
			System.out.println("Could not connect to the database.");
		}
		
		try {
			
			ResultSet results = statement.executeQuery("SELECT * FROM tblusers");
			
			while(results.next()) { // While there are more rows in the table...
				
				int userid = results.getInt("userid");	// call getInt function w/ parameter "userid" (column w/ data type "integer" in database)
				
				String username = results.getString("username"); // call getString function w/ parameter "username" (column w/ data type "character varying" in database)
				
				String password = results.getString("password"); // call getString function w/ parameter "password" (column w/ data type "character varying" in database)
				
				boolean admin = results.getBoolean("admin"); // call getBoolean function w/ parameter "admin" (column w/ data type "boolean" in database)
				
				User newUser = new User(userid, username, password, admin);
				
				users.add(newUser);
				
			}
			
		} catch(SQLException e) {
			System.out.println("Could not retrieve users from the database.");
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
		
		return users;
	}
	
/* **************************************************************************************************************************************************************** */
	
/*
* Here I will insert a team into the database
********************************************************************************************************************************************************************
*/
	
	public boolean insertTeam(Team teamToInsert) {
		
		System.out.println("Here is the team to insert: " + teamToInsert.getTeamName());
		
		Connection con = null;
		
		Statement statement = null;
		
		try {
			con = DriverManager.getConnection("jdbc:postgresql://ec2-107-22-239-155.compute-1.amazonaws.com/daknuflimm0laj", "utufnbbozfaphi", "4a7b61f6d36d53dd87d281cc3786acbe2bdcaf7470f7368b46ac370c1c5dbd95");
			statement = con.createStatement(); // Create a "Statement" object to do operations on
			if(con != null) { // Error checking
				System.out.println("Database Connected");
			}
		} catch (SQLException e) {
			System.out.println("Could not connect to the database.");
			return false;
		}
		
		// First, we need to check if the team is already in the database
		String checkForDuplicateTeam = "SELECT 1 FROM tblteams WHERE teamname = '" + teamToInsert.getTeamName() + "'"; 
		
		try {
			
			ResultSet results = statement.executeQuery(checkForDuplicateTeam);
			if(results.next() == false) { // No duplicate team was found
				
				System.out.println("Good to insert");
				
				try {
					String insertIntoTeamTable = "INSERT INTO tblteams (teamname) VALUES ('" + teamToInsert.getTeamName() + "')";
					
					statement.executeUpdate(insertIntoTeamTable);
					
					System.out.println("Inserted " + teamToInsert.getTeamName() + " into the database.");
					
					return true;
					
				} catch(SQLException e){
					System.out.println("There was an error when trying to insert the team. " + e);
					return false;
				}
				
				
			} else { // A duplicate team was found
				System.out.println("Found duplicate");
			}
			
			
		} catch(SQLException e){
			System.out.println("Could not add team to the database" + e);
			return false;
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
		
		return false;
			
	}

	
/* **************************************************************************************************************************************************************** */
	
/*
* Here I will edit a team that already exists in the database
********************************************************************************************************************************************************************
*/
	
	public boolean editTeam(Team newTeamData, String teamNameToEdit) {
		
		System.out.println("Team " + teamNameToEdit + " will be changed to " + newTeamData.getTeamName());
		
		Connection con = null;
		
		Statement statement = null;
		
		try {
			con = DriverManager.getConnection("jdbc:postgresql://ec2-107-22-239-155.compute-1.amazonaws.com/daknuflimm0laj", "utufnbbozfaphi", "4a7b61f6d36d53dd87d281cc3786acbe2bdcaf7470f7368b46ac370c1c5dbd95");
			statement = con.createStatement(); // Create a "Statement" object to do operations on
			if(con != null) { // Error checking
				System.out.println("Database Connected");
			}
		} catch (SQLException e) {
			System.out.println("Could not connect to the database.");
			return false;
		}
		
		// First, I must check if the team that is being edited doesn't conflict with any other teams
		
		String checkForDuplicateTeam = "SELECT 1 FROM tblteams WHERE teamname = '" + newTeamData.getTeamName() + "'"; 
		
		try {
			
			ResultSet results = statement.executeQuery(checkForDuplicateTeam);
			if(results.next() == false) { // No duplicate team was found
				
				System.out.println("Good to edit");
				
				try {
					
					String editTeamSQL = "UPDATE tblteams SET teamname = '" + newTeamData.getTeamName() + "' WHERE teamname = '" + teamNameToEdit + "'";
					
					statement.executeUpdate(editTeamSQL);
					
					System.out.println("Team " + teamNameToEdit + " was changed to " + newTeamData.getTeamName());
						
					return true;
					
					
				} catch(SQLException e){
					System.out.println("There was an error when trying to edit the team. " + e);
					return false;
				}
				
				
			} else { // A duplicate team was found
				System.out.println("Found duplicate");
			}
			
		} catch(SQLException e) {
			System.out.println("Could not edit team to the database" + e);
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
		
		return false;
			
	}
	
/* **************************************************************************************************************************************************************** */
	
/*
* Here I will edit a team that already exists in the database
********************************************************************************************************************************************************************
*/
	
	public boolean deleteTeam(String teamNameToDelete) {
		
		System.out.println("Team " + teamNameToDelete + " will be deleted from the database.");
		
		Connection con = null;
		
		Statement statement = null;
		
		try {
			con = DriverManager.getConnection("jdbc:postgresql://ec2-107-22-239-155.compute-1.amazonaws.com/daknuflimm0laj", "utufnbbozfaphi", "4a7b61f6d36d53dd87d281cc3786acbe2bdcaf7470f7368b46ac370c1c5dbd95");
			statement = con.createStatement(); // Create a "Statement" object to do operations on
			if(con != null) { // Error checking
				System.out.println("Database Connected");
			}
		} catch (SQLException e) {
			System.out.println("Could not connect to the database.");
			return false;
		}
		
		// First, I must check if the team that is being deleted is in the database
		
		String checkForDuplicateTeam = "SELECT * FROM tblteams WHERE teamname = '" + teamNameToDelete + "'"; 
		
		try {
			
			ResultSet results = statement.executeQuery(checkForDuplicateTeam);
			if(results.next() == true) { // There is a team in the database with that name, so it is time to delete it
				
				System.out.println("Found team to delete");
				
				int teamid = results.getInt("teamid");
				
				System.out.println("Team ID to delete: " + Integer.toString(teamid));
				
				try {
					
					// First, find TakenSurveys with that teamid
					String findTakenSurveysDependentOnTeam = "SELECT * FROM tbltakensurvey WHERE teamsid = " + Integer.toString(teamid) + ";";
					
					Statement deleteStatement = null;
					
					deleteStatement = con.createStatement();
					
					ResultSet thingsToDelete = statement.executeQuery(findTakenSurveysDependentOnTeam);
					
					while(thingsToDelete.next()) {
						
						// For each TakenSurvey with that teamid, delete all answers with that takensurveyid
						String deleteAnwersDepedentOnTakenSurvey = "DELETE FROM tblanswer WHERE takensurveyid = " + Integer.toString(thingsToDelete.getInt("takensurveyid")) + ";";
						
						deleteStatement.executeUpdate(deleteAnwersDepedentOnTakenSurvey);
						
					}
					
					// Then, delete all TakenSurveys with that teamid
					String deleteTakenSurveysDependentOnTeam = "DELETE FROM tbltakensurvey WHERE teamsid = " + Integer.toString(teamid) + ";";
					
					deleteStatement.executeUpdate(deleteTakenSurveysDependentOnTeam);
					
					// Finally, delete the team
					String deleteTeamSQL = "DELETE FROM tblteams WHERE teamname = '" + teamNameToDelete + "'";
					
					statement.executeUpdate(deleteTeamSQL);
					
					System.out.println("Team " + teamNameToDelete + " was deleted from the database");
						
					return true;
					
					
				} catch(SQLException e){
					System.out.println("There was an error when trying to delete the team. " + e);
					return false;
				}
				
			} else { // A duplicate team was found
				System.out.println("There was no team with that team name to delete in the database");
			}
			
		} catch(SQLException e) {
			System.out.println("Could not delete team from the database" + e);
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
		
		return false;
	
	}
	
/* **************************************************************************************************************************************************************** */
	
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
			System.out.println("Could not retrieve data from the database " + e.getMessage());
			
		}
	
		try {
			
			/*
			 * 1. Create ResultSet object to hold the results
			 * 2. Call function executeQuery on Statement object and pass in SQL code:
			 * 		a. "SELECT *" means select all
			 * 		b. "FROM question_types" means from the table that you give it (in this case question_types)
			 */
			ResultSet results = statement.executeQuery("SELECT * FROM tblteams");
			
			while(results.next()) { // While there are more rows in the table...
				
				int teamid = results.getInt("teamid");
				
				String teamName = results.getString("teamname"); // call getString function w/ parameter "name" (column w/ data type string in database)
				
				Team teamToAdd = new Team(teamid, teamName);
				
				teamNames.add(teamToAdd);
				
			}
			
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
	
//HERE
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public static SurveyQuestions buildSurveyQuestions(int givenSurveyid) {
		
		SurveyQuestions surveyQuestionsToReturn = new SurveyQuestions();
		
		Survey survey = new Survey();
		
		SurveyType typeOfSurvey = new SurveyType();
		
		ArrayList<Question> questions = new ArrayList<>();
		
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
		
		try {
			
			// First, we must look for the survey that the frontend wants to grab
			
			String searchForSurvey = "SELECT * FROM tblsurvey WHERE surveyid = " + Integer.toString(givenSurveyid) + ";";
			
			ResultSet surveyData = statement.executeQuery(searchForSurvey);
			
			while(surveyData.next()) {
				
				survey.setSurveyid(surveyData.getInt("surveyid"));
				
				survey.setName(surveyData.getString("name"));
				
				int surveyTypeKey = surveyData.getInt("surveytypeid");
				
				System.out.println("Here is the surveyTypeId: " + Integer.toString(surveyTypeKey));
					
				String searchForSurveyType = "SELECT * FROM tblsurveytype WHERE surveytypeid = " + Integer.toString(surveyTypeKey) + ";";
				
				Statement statementForSurveyType = null;
				
				statementForSurveyType = con.createStatement();
				
				ResultSet surveyTypeData = statementForSurveyType.executeQuery(searchForSurveyType);
				
				while(surveyTypeData.next()) {
					
					typeOfSurvey.setType(surveyTypeData.getString("type"));
					
					typeOfSurvey.setDescription(surveyTypeData.getString("description"));
					
				}
				
				survey.setTypeOfSurvey(typeOfSurvey);
				
			}
			
			// Next, lets build the array of questions
			
			String findQuestionsInSurvey = "SELECT * FROM tblquestioninsurvey WHERE surveyid = " + Integer.toString(survey.getSurveyid()) + ";";
			
			Statement statementForQuestionsInSurvey = null;
			
			statementForQuestionsInSurvey = con.createStatement();
			
			ResultSet questionsInSurveyData = statementForQuestionsInSurvey.executeQuery(findQuestionsInSurvey);
			
			while(questionsInSurveyData.next()) {
				
				int questionid = questionsInSurveyData.getInt("questionid");
				
				System.out.println("Here's the questionid: " + Integer.toString(questionid));
				
				String getQuestionInformation = "SELECT * FROM tblquestion WHERE questionid = " + Integer.toString(questionid) + ";";
				
				Statement statementForQuestions = null;
				
				statementForQuestions = con.createStatement();
				
				ResultSet questionsData = statementForQuestions.executeQuery(getQuestionInformation);
				
				while(questionsData.next()) {
					
					Question newQuestion = new Question();
					
					newQuestion.setQuestionScore(-1);
					
					newQuestion.setQuestion(questionsData.getString("question"));
					
					newQuestion.setQuestionid(questionsData.getInt("questionid"));
					
					int questionTypeId = questionsData.getInt("questiontypeid");
					
					String getQuestionTypeInformation = "SELECT * FROM tblquestiontype WHERE questiontypeid = " + Integer.toString(questionTypeId) + ";";
					
					Statement statementForQuestionTypes = null;
					
					statementForQuestionTypes = con.createStatement();
					
					ResultSet questionTypesData = statementForQuestionTypes.executeQuery(getQuestionTypeInformation);
					
					while(questionTypesData.next()) {
						
						QuestionType newQuestionType = new QuestionType();
						
						newQuestionType.setType(questionTypesData.getString("type"));
						
						newQuestionType.setNumberOfOptions(questionTypesData.getInt("numberofoptions"));
						
						newQuestionType.setDescription(questionTypesData.getString("description"));
						
						newQuestion.setTypeOfQuestion(newQuestionType);
						
					}
					
					questions.add(newQuestion);
					
				}
				
				surveyQuestionsToReturn.setSurvey(survey);
				
				surveyQuestionsToReturn.setQuestions(questions);
				
				surveyQuestionsToReturn.setTotalScore(0.0);
				
			}
			
		} catch(SQLException e) {
			System.out.println("An error occured when finding survey information. " + e);
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
		
	}
	
	public static List<Survey> getSurveys() {
		
		List<Survey> surveys = new ArrayList<>();
		
		Connection con = null;
		
		Statement statementForSurveys = null;
		
		try {
			con = DriverManager.getConnection("jdbc:postgresql://ec2-107-22-239-155.compute-1.amazonaws.com/daknuflimm0laj", "utufnbbozfaphi", "4a7b61f6d36d53dd87d281cc3786acbe2bdcaf7470f7368b46ac370c1c5dbd95");
			statementForSurveys = con.createStatement(); // Create a "Statement" object to do operations on
			if(con != null) { // Error checking
				System.out.println("Database Connected");
			}
		} catch (SQLException e) {
			System.out.println("Could not connect to the database. HERE");
		}
		
		String findAllSurveys = "SELECT * FROM tblsurvey";
		
		try {
			
			ResultSet surveysData = statementForSurveys.executeQuery(findAllSurveys);
			
			while(surveysData.next()) {
				
				Survey newSurvey = new Survey();
				
				newSurvey.setSurveyid(surveysData.getInt("surveyid"));
				
				newSurvey.setName(surveysData.getString("name"));
				
				int surveyTypeid = surveysData.getInt("surveytypeid");
				
				String findSurveyType = "SELECT * FROM tblsurveytype WHERE surveytypeid = " + Integer.toString(surveyTypeid) + ";";
				
				Statement statementForSurveyType = null;
				
				statementForSurveyType = con.createStatement();
				
				ResultSet surveyTypeData = statementForSurveyType.executeQuery(findSurveyType);
				
				SurveyType typeOfSurvey = new SurveyType();
				
				while(surveyTypeData.next()) {
					
					typeOfSurvey.setType(surveyTypeData.getString("type"));
					
					typeOfSurvey.setDescription(surveyTypeData.getString("description"));
					
				}
				
				newSurvey.setTypeOfSurvey(typeOfSurvey);
				
				surveys.add(newSurvey);
				
			}
			
		} catch(SQLException e) {
			System.out.println("Error when trying to get all of the surveys: " + e);
		}
		
		return surveys;
		
	}
	
	public boolean saveSurveyResults(int teamid, int surveyid, SurveyQuestions resultsOfSurveyQuestions) {
		
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
		
		String addTakenSurveyToDatabase = "INSERT INTO tbltakensurvey (surveyid, teamsid, score) VALUES (" + Integer.toString(surveyid) + ", " + Integer.toString(teamid) + ", " + Double.toString(resultsOfSurveyQuestions.getTotalScore()) + ");";
		
		try {
			
			statement.executeUpdate(addTakenSurveyToDatabase);
			
			int takensurveyid = 0;
			
			String findCorrectTakenSurveyID = "SELECT * FROM tbltakensurvey";
			
			ResultSet takenSurveys = statement.executeQuery(findCorrectTakenSurveyID);
			
			while(takenSurveys.next()) {
				
				if(takenSurveys.isLast()) {
					
					takensurveyid = takenSurveys.getInt("takensurveyid");
					
					System.out.println("This is the last id in the takensurvey table: " + Integer.toString(takensurveyid));
					
					Statement statementForAnswers = null;
					
					statementForAnswers = con.createStatement();
					
					List<Question> questionsToAdd = new ArrayList<>();
					
					questionsToAdd = resultsOfSurveyQuestions.getQuestions();
					
					for(int i = 0; i < questionsToAdd.size(); i++) {
						
						Question question = questionsToAdd.get(i);
						
						int questionid = question.getQuestionid();
						
						int questionScore = question.getQuestionScore();
						
						String addAnswerToDatabase = "INSERT INTO tblanswer (takensurveyid, questionid, answer) VALUES(" + Integer.toString(takensurveyid) + ", " + Integer.toString(questionid) + ", " + Integer.toString(questionScore) + ");";
						
						statementForAnswers.executeUpdate(addAnswerToDatabase);
						
					}
					
					return true;
					
				}
				
			}
			
		} catch(SQLException e) {
			System.out.println("Could not save survey scores. " + e);
		}
		
		return false;
		
	}
	
}