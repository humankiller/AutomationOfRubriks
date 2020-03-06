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
			
			int surveytypeid = 0;
			
			while(surveyData.next()) {
				
				survey.setSurveyid(surveyData.getInt("surveyid"));
				
				survey.setName(surveyData.getString("name"));
				
				surveytypeid = surveyData.getInt("surveytypeid");
				
				System.out.println("Here is the surveyTypeId: " + Integer.toString(surveytypeid));
					
				String searchForSurveyType = "SELECT * FROM tblsurveytype WHERE surveytypeid = " + Integer.toString(surveytypeid) + ";";
				
				Statement statementForSurveyType = null;
				
				statementForSurveyType = con.createStatement();
				
				ResultSet surveyTypeData = statementForSurveyType.executeQuery(searchForSurveyType);
				
				while(surveyTypeData.next()) {
					
					typeOfSurvey.setSurveytypeid(surveyTypeData.getInt("surveytypeid"));
					
					typeOfSurvey.setType(surveyTypeData.getString("type"));
					
					typeOfSurvey.setDescription(surveyTypeData.getString("description"));
					
				}
				
				survey.setTypeOfSurvey(typeOfSurvey);
				
			}
			
			// Next, lets build the array of questions
			
			String findQuestionsInSurvey = "SELECT * FROM tblquestioninsurvey WHERE surveytypeid = " + Integer.toString(surveytypeid) + ";";
			
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
	
	public static List<SurveyType> getSurveyTypes(boolean showHidden) {
		
		List<SurveyType> surveyTypes = new ArrayList<>();
		
		Connection con = null;
		
		Statement statementForSurveyTypes = null;
		
		try {
			con = DriverManager.getConnection("jdbc:postgresql://ec2-107-22-239-155.compute-1.amazonaws.com/daknuflimm0laj", "utufnbbozfaphi", "4a7b61f6d36d53dd87d281cc3786acbe2bdcaf7470f7368b46ac370c1c5dbd95");
			statementForSurveyTypes = con.createStatement(); // Create a "Statement" object to do operations on
			if(con != null) { // Error checking
				System.out.println("Database Connected");
			}
		} catch (SQLException e) {
			System.out.println("Could not connect to the database. HERE");
		}
		
		String findAllSurveyTypes = "";
		
		if(showHidden == true) {
			findAllSurveyTypes = "SELECT * FROM tblsurveytype"; 
		} else {
			findAllSurveyTypes = "SELECT * FROM tblsurveytype WHERE active";
		}
		
		try {
			
			ResultSet surveyTypesData = statementForSurveyTypes.executeQuery(findAllSurveyTypes);
			
			while(surveyTypesData.next()) {
				
				SurveyType newSurveyType = new SurveyType();
				
				newSurveyType.setSurveytypeid(surveyTypesData.getInt("surveytypeid"));
				
				newSurveyType.setType(surveyTypesData.getString("type"));
				
				newSurveyType.setDescription(surveyTypesData.getString("description"));
				
				surveyTypes.add(newSurveyType);
				
			}
			
		} catch(SQLException e) {
			System.out.println("Could not fetch survey type.  " + e);
		} finally {
			if(con != null) {
				try {
					System.out.println("Closing connection...");
					con.close();
				} catch(SQLException e) {
					
				}
			}
			if(statementForSurveyTypes != null) {
				try {
					System.out.println("Closing statement...");
					statementForSurveyTypes.close();
				} catch(SQLException e) {
					
				}
			}
		}
		
		return surveyTypes;
		
	}
	
	public static List<Survey> getSurveys(int surveytypeid) {
		
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
		
		String findAllSurveys = "";
		
		if(surveytypeid == 0) {
			findAllSurveys = "SELECT * FROM tblsurvey";
		} else {
			findAllSurveys = "SELECT * FROM tblsurvey WHERE surveytypeid = " + Integer.toString(surveytypeid) + ";";
		}
		
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
					
					typeOfSurvey.setSurveytypeid(surveyTypeData.getInt("surveytypeid"));
					
					typeOfSurvey.setType(surveyTypeData.getString("type"));
					
					typeOfSurvey.setDescription(surveyTypeData.getString("description"));
					
				}
				
				newSurvey.setTypeOfSurvey(typeOfSurvey);
				
				surveys.add(newSurvey);
				
			}
			
		} catch(SQLException e) {
			System.out.println("Error when trying to get all of the surveys: " + e);
		} finally {
			if(con != null) {
				try {
					System.out.println("Closing connection...");
					con.close();
				} catch(SQLException e) {
					
				}
			}
			if(statementForSurveys != null) {
				try {
					System.out.println("Closing statement...");
					statementForSurveys.close();
				} catch(SQLException e) {
					
				}
			}
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
	
	public ReportOptions fetchReportOptions() {
		
		ReportOptions returnReportOptions = new ReportOptions();
		
		List<Team> teams = new ArrayList<>();
		
		List<Survey> surveys = new ArrayList<>();
		
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
	
			ResultSet teamsData = statement.executeQuery("SELECT * FROM tblteams");
			
			while(teamsData.next()) { // While there are more rows in the table...
				
				int teamid = teamsData.getInt("teamid");
				
				String teamName = teamsData.getString("teamname"); // call getString function w/ parameter "name" (column w/ data type string in database)
				
				Team teamToAdd = new Team(teamid, teamName);
				
				teams.add(teamToAdd);
				
			}
			
			returnReportOptions.setTeams(teams);
			
			Statement statementForSurveys = con.createStatement();
			
			ResultSet surveysData = statement.executeQuery("SELECT * FROM tblsurvey");
			
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
					
					typeOfSurvey.setSurveytypeid(surveyTypeData.getInt("surveytypeid"));
					
					typeOfSurvey.setType(surveyTypeData.getString("type"));
					
					typeOfSurvey.setDescription(surveyTypeData.getString("description"));
					
				}
				
				newSurvey.setTypeOfSurvey(typeOfSurvey);
				
				surveys.add(newSurvey);
				
				returnReportOptions.setSurveys(surveys);
				
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
		
		return returnReportOptions;
	}
	
	public ArrayList<SurveyQuestions> fetchReport(int teamid, int surveyid) {
		
		ArrayList<SurveyQuestions> report = new ArrayList<>();
		
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
		
		// First, let's get the TakenSurvey information
		
		String queryTakenSurveyInformation = "SELECT * FROM tbltakensurvey WHERE teamsid = " + Integer.toString(teamid) + " AND surveyid = " + Integer.toString(surveyid) + ";";
		
		try {
			
			ResultSet takenSurveyInformation = statement.executeQuery(queryTakenSurveyInformation);
			
			while(takenSurveyInformation.next()) {
				
				SurveyQuestions takenSurvey = new SurveyQuestions();
				
				takenSurvey.setSurvey(null); // Don't need it in the frontend
				
				// Get the takensurveyid to be able to fetch the correct answers
				
				int takensurveyid = takenSurveyInformation.getInt("takensurveyid");
				
				double totalScore = takenSurveyInformation.getDouble("score");
				
				// Create answers array to hold answers in this TakenSurvey
				
				ArrayList<Question> answers = new ArrayList<>();
				
				// Must create a new statement to query answers
				
				Statement statementToFetchAnswers = con.createStatement();
				
				String queryAnswersInformation = "SELECT * FROM tblanswer WHERE takensurveyid = " + Integer.toString(takensurveyid) + ";";
				
				ResultSet answersInformation = statementToFetchAnswers.executeQuery(queryAnswersInformation);
				
				while(answersInformation.next()) {
					
					Question newAnswer = new Question();
					
					int answer = answersInformation.getInt("answer");
					
					// Get the questionid to be able to fetch the correct question
					
					int questionid = answersInformation.getInt("questionid");
					
					String getQuestionInformation = "SELECT * FROM tblquestion WHERE questionid = " + Integer.toString(questionid) + ";";
					
					Statement statementForQuestions = null;
					
					statementForQuestions = con.createStatement();
					
					ResultSet questionsData = statementForQuestions.executeQuery(getQuestionInformation);
					
					while(questionsData.next()) {
						
						newAnswer.setQuestionScore(answer);
						
						newAnswer.setQuestion(questionsData.getString("question"));
						
						newAnswer.setQuestionid(questionsData.getInt("questionid"));
						
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
							
							newAnswer.setTypeOfQuestion(newQuestionType);
							
						}
						
					}
					
					answers.add(newAnswer);
					
				}
				
				takenSurvey.setQuestions(answers);
				
				takenSurvey.setTotalScore(totalScore);
				
				report.add(takenSurvey);
				
			}
			
		} catch(SQLException e) {
			System.out.println("Could not fetch the report.  " + e);
		}  finally {
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
		
		return report;
	}

	
	public boolean verifyAdmin(String uName, String pWord) {
		
		//List<Team> teamNames = new ArrayList<>();
		boolean isAdminBool = false;
		
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
			
			/*
			 * 1. Create ResultSet object to hold the results
			 * 2. Call function executeQuery on Statement object and pass in SQL code:
			 * 		a. "SELECT *" means select all
			 * 		b. "FROM question_types" means from the table that you give it (in this case question_types)
			 */
			ResultSet results = statement.executeQuery("SELECT * FROM tblusers;");
			
			while(results.next()) { // While there are more rows in the table...
				
				//String data = results.getString(1);
				//System.out.println("Fetching data by column index for row " + results.getRow() + " : " + data);
				
				String data1 = results.getString("username"); // call getString function w/ parameter "name" (column w/ data type string in database)
				String data2 = results.getString("password"); // call getString function w/ parameter "name" (column w/ data type string in database)
				System.out.println("Fetching data by username " + results.getRow() + " : " + data1);
				
				if ((uName.equals(data1)) && (pWord.equals(data2)))
				{
					isAdminBool = true;
					break;
				}
				
				//Team teamToAdd = new Team();
				//teamToAdd.setTeamName(data);
				//teamNames.add(teamToAdd);
				
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
		
		return isAdminBool;
		
		
	}
	
	public ArrayList<QuestionType> getQuestionTypes() {
		
		ArrayList<QuestionType> questiontypes = new ArrayList<>();
		
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
		
		String fetchQuestionTypes = "SELECT * FROM tblquestiontype;";
		
		try {
			
			ResultSet questionTypesData = statement.executeQuery(fetchQuestionTypes);
			
			while(questionTypesData.next()) {
				
				QuestionType newQuestionType = new QuestionType();
				
				newQuestionType.setQuestiontypeid(questionTypesData.getInt("questiontypeid"));
				
				newQuestionType.setType(questionTypesData.getString("type"));
				
				newQuestionType.setNumberOfOptions(questionTypesData.getInt("numberofoptions"));
				
				newQuestionType.setDescription(questionTypesData.getString("description"));
				
				questiontypes.add(newQuestionType);
				
			}
			
		} catch(SQLException e) {
			System.out.println("Could not retrieve questiontypes from the database " + e.getMessage());
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
		
		return questiontypes;
	}
	
	public ArrayList<Question> getQuestionsWithTypeQuestionID(int questiontypeid) {
		
		ArrayList<Question> questions = new ArrayList<>();
		
		QuestionType selectedQuestionType = new QuestionType();
		
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
		
		String fetchQuestionTypeData = "SELECT * FROM tblquestiontype WHERE questiontypeid = " + Integer.toString(questiontypeid) + ";";
		
		try {
			
			ResultSet questionTypesData = statement.executeQuery(fetchQuestionTypeData);
			
			while(questionTypesData.next()) {
				
				selectedQuestionType.setQuestiontypeid(questionTypesData.getInt("questiontypeid"));
				
				selectedQuestionType.setType(questionTypesData.getString("type"));
				
				selectedQuestionType.setNumberOfOptions(questionTypesData.getInt("numberofoptions"));
				
				selectedQuestionType.setDescription(questionTypesData.getString("description"));
				
			}
			
			// Now get all of the questions with that specific question type from the database
			
			String fetchQuestionsWithMatchingQuestionID = "SELECT * FROM tblquestion WHERE questiontypeid = " + Integer.toString(questiontypeid) + ";";
			
			Statement questionsStatement = con.createStatement();
			
			ResultSet questionsData = questionsStatement.executeQuery(fetchQuestionsWithMatchingQuestionID);
			
			while(questionsData.next()) {
				
				Question newQuestion = new Question();
				
				newQuestion.setTypeOfQuestion(selectedQuestionType);
				
				newQuestion.setQuestionid(questionsData.getInt("questionid"));
				
				newQuestion.setQuestion(questionsData.getString("question"));
				
				newQuestion.setQuestionScore(-1);
				
				questions.add(newQuestion);
				
			}
			
		} catch(SQLException e) {
			System.out.println("Could not retrieve questions from the database " + e.getMessage());
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
		
		return questions;
		
	}
	
	public boolean createTemplate(Template template) {
		
		boolean createTemplateStatus = false;
		
		String type = template.getTypeOfSurvey().getType();
		
		String description = template.getTypeOfSurvey().getDescription();
		
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
		
		String checkDuplicateSQL = "SELECT * FROM tblsurveytype WHERE type = '" + template.getTypeOfSurvey().getType() + "';";
		
		try {
			
			ResultSet checkDuplicate = statement.executeQuery(checkDuplicateSQL);
			
			if(checkDuplicate.next() == false) { // No duplicates were found in the database
				
				System.out.println("Good to insert survey type");
				
				Statement surveyTypeStatement = con.createStatement();
				
				String insertSurveyTypeSQL = "INSERT INTO tblsurveytype (type, description) VALUES('" + type + "', '" + description + "');";
				
				surveyTypeStatement.executeUpdate(insertSurveyTypeSQL);
				
				surveyTypeStatement.close();
				
				// Now find the surveytypeid of the surveytype that was just inserted
				
				Statement findSurveyTypeIDStatement = con.createStatement();
				
				String findSurveyTypeIDSQL = "SELECT * FROM tblsurveytype WHERE type = '" + type + "';";
				
				ResultSet findSurveyTypeIDData = findSurveyTypeIDStatement.executeQuery(findSurveyTypeIDSQL);
				
				while(findSurveyTypeIDData.next()) {
					
					int surveytypeid = findSurveyTypeIDData.getInt("surveytypeid");
					
					Statement linkStatement = con.createStatement();
					
					for(int i = 0; i < template.getQuestions().size(); i++) {
						
						int questionIDToLink = template.getQuestions().get(i).getQuestionid();
						
						String linkQuestionsToSurveyTypeSQL = "INSERT INTO tblquestioninsurvey (surveytypeid, questionid) VALUES(" + Integer.toString(surveytypeid) + " , " + Integer.toString(questionIDToLink) + ");";
						
						linkStatement.executeUpdate(linkQuestionsToSurveyTypeSQL);
						
					}
					
				}
				
				createTemplateStatus = true;
				
			}
			
		} catch(SQLException e) {
			System.out.println("Could not insert template into database " + e.getMessage());
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
		
		return createTemplateStatus;
	}
	
	public boolean deleteTemplate(int surveytypeid) {
		
		boolean deleteTemplateStatus = false;
		
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
		
		String deleteTemplateSQL = "UPDATE tblsurveytype SET active = 'false' WHERE surveytypeid = " + Integer.toString(surveytypeid) + ";";
		
		try {
			
			statement.executeUpdate(deleteTemplateSQL);
			
			deleteTemplateStatus = true;
			
		} catch(SQLException e) {
			System.out.println("Could not delete template " + e.getMessage());
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
		
		return deleteTemplateStatus;
		
	}
	
	public boolean activateTemplate(int surveytypeid) {
		
		boolean activateTemplateStatus = false;
		
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
		
		String activateTemplateSQL = "UPDATE tblsurveytype SET active = 'true' WHERE surveytypeid = " + Integer.toString(surveytypeid) + ";";
		
		try {
			
			statement.executeUpdate(activateTemplateSQL);
			
			activateTemplateStatus = true;
			
		} catch(SQLException e) {
			System.out.println("Could not delete template " + e.getMessage());
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
		
		return activateTemplateStatus;
		
	}
}