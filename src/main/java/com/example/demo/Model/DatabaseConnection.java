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
		
		Connection con = openConn();
		Statement statement = openState(con);
		
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
			closeConn(con);
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
		
		Connection con = openConn();
		Statement statement = openState(con);
		
		// First, we must check if there is a team in the database that already has this team name
		int itemInDatabase = itemInDatabase(con, "tblteams", "teamname", teamToInsert.getTeamName());
		if (itemInDatabase == 0) { // There is team in the database with this teamname, so we're good to insert the team
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
		} else { // A team with that team name was found.  Cannot insert.
			System.out.println("Found duplicate in tblteams with teamid = " + Integer.toString(itemInDatabase) + "; Cannot insert into the database.");
		}
		
		closeConn(con); // Close the connection once all operations are done.
		return false;
	}

	
/* **************************************************************************************************************************************************************** */
	
/*
* Here I will edit a team that already exists in the database
********************************************************************************************************************************************************************
*/
	
	public boolean editTeam(Team newTeamData, String teamNameToEdit) {
		
		System.out.println("Team " + teamNameToEdit + " will be changed to " + newTeamData.getTeamName());
		
		Connection con = openConn();
		Statement statement = openState(con);
		
		// First, I must check if the team that is being edited doesn't conflict with any other teams
		int itemInDatabase = itemInDatabase(con, "tblteams", "teamname", newTeamData.getTeamName());
		if(itemInDatabase == 0) {
			
				
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
			System.out.println("There is already a teamname in the database that has the edited name w/ primary key = " + Integer.toString(itemInDatabase) + " in tblteams.");
		}
		
		closeConn(con);
		return false;
	}
	
/* **************************************************************************************************************************************************************** */
	
/*
* Here I will edit a team that already exists in the database
********************************************************************************************************************************************************************
*/
	
	public boolean deleteTeam(String teamNameToDelete) {
		
		System.out.println("Team " + teamNameToDelete + " will be deleted from the database.");
		
		Connection con = openConn();
		Statement statement = openState(con);
		
		// First, I must check if the team that is being deleted is in the database
		
		int itemInDatabase = itemInDatabase(con, "tblteams", "teamname", teamNameToDelete);
		if(itemInDatabase != 0) { // There is a team in the database that matches this name, and its primary key is itemInDatabase
			
			System.out.println("Found team to delete");
			System.out.println("Team ID to delete: " + Integer.toString(itemInDatabase));
			
			try {
				
				// First, find TakenSurveys with that teamid
				String findTakenSurveysDependentOnTeam = "SELECT * FROM tbltakensurvey WHERE teamsid = " + Integer.toString(itemInDatabase) + ";";
				
				Statement deleteStatement = openState(con);
				
				ResultSet thingsToDelete = statement.executeQuery(findTakenSurveysDependentOnTeam);
				
				while(thingsToDelete.next()) {
					
					// For each TakenSurvey with that teamid, delete all answers with that takensurveyid
					String deleteAnwersDepedentOnTakenSurvey = "DELETE FROM tblanswer WHERE takensurveyid = " + Integer.toString(thingsToDelete.getInt("takensurveyid")) + ";";
					deleteStatement.executeUpdate(deleteAnwersDepedentOnTakenSurvey);
				}
				
				// Then, delete all TakenSurveys with that teamid
				String deleteTakenSurveysDependentOnTeam = "DELETE FROM tbltakensurvey WHERE teamsid = " + Integer.toString(itemInDatabase) + ";";
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
			
		} else { // There is no team in the database with that teamname, because itemInDatabase = 0
			System.out.println("There was no team with that team name to delete in the database");
		}
		
		closeConn(con);
		return false;
	}
	
/* **************************************************************************************************************************************************************** */
	
	public List<Team> getTeamNames() {
		
		List<Team> teamNames = new ArrayList<>();
		
		Connection con = openConn();
		Statement statement = openState(con);
	
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
			closeConn(con);
		}
		
		return teamNames;
	}
	
	public SurveyQuestions buildSurveyQuestions(int givenSurveyid) {
		
		SurveyQuestions surveyQuestionsToReturn = new SurveyQuestions();
		Survey survey = new Survey();
		SurveyType typeOfSurvey = new SurveyType();
		ArrayList<Question> questions = new ArrayList<>();
		
		Connection con = openConn();
		Statement statement = openState(con);
		
		try {
			
			survey = getTheSurvey(con, givenSurveyid);
			int surveytypeid = survey.getTypeOfSurvey().getSurveytypeid();
			
			// Next, lets build the array of questions
			
			String findQuestionsInSurvey = "SELECT * FROM tblquestioninsurvey WHERE surveytypeid = " + Integer.toString(surveytypeid) + ";";
			
			Statement statementForQuestionsInSurvey = openState(con);
			
			ResultSet questionsInSurveyData = statementForQuestionsInSurvey.executeQuery(findQuestionsInSurvey);
			
			while(questionsInSurveyData.next()) {
				
				int questionid = questionsInSurveyData.getInt("questionid");
				System.out.println("Here's the questionid: " + Integer.toString(questionid));
				String getQuestionInformation = "SELECT * FROM tblquestion WHERE questionid = " + Integer.toString(questionid) + ";";
				
				Statement statementForQuestions = openState(con);
				
				ResultSet questionsData = statementForQuestions.executeQuery(getQuestionInformation);
				
				getQuestion(questionsData, questions, con);
				
				
				//******************************
				// REPLACE WITH getQuestion*****
				//******************************
				/*
				while(questionsData.next()) {
					
					getQuestion(questionsData, questions, con);
					
					
					
					Question newQuestion = new Question();
					
					newQuestion.setQuestionScore(-1);
					newQuestion.setQuestion(questionsData.getString("question"));
					newQuestion.setQuestionid(questionsData.getInt("questionid"));
					
					int questionTypeId = questionsData.getInt("questiontypeid");
					String getQuestionTypeInformation = "SELECT * FROM tblquestiontype WHERE questiontypeid = " + Integer.toString(questionTypeId) + ";";
					
					Statement statementForQuestionTypes = openState(con);
					
					ResultSet questionTypesData = statementForQuestionTypes.executeQuery(getQuestionTypeInformation);
					
					//**********************************
					// REPLACE WITH getQuestionType*****
					//**********************************
					while(questionTypesData.next()) { 
						
						QuestionType newQuestionType = new QuestionType();
						
						newQuestionType.setType(questionTypesData.getString("type"));
						newQuestionType.setNumberOfOptions(questionTypesData.getInt("numberofoptions"));
						newQuestionType.setDescription(questionTypesData.getString("description"));
						newQuestion.setTypeOfQuestion(newQuestionType);
					}
					//**********************************
					// REPLACE WITH getQuestionType*****
					//**********************************
					questions.add(newQuestion);
					
					
				}
				//******************************
				// REPLACE WITH getQuestion*****
				//******************************
				 */
				
				surveyQuestionsToReturn.setSurvey(survey);
				surveyQuestionsToReturn.setQuestions(questions);
				surveyQuestionsToReturn.setTotalScore(0.0);
			}
		} catch(SQLException e) {
			System.out.println("An error occured when finding survey information. " + e);
		} finally {
			closeConn(con);
		}
		
		return surveyQuestionsToReturn;
		
	}

	
	public List<SurveyType> getSurveyTypes(boolean showHidden) {
		
		List<SurveyType> surveyTypes = new ArrayList<>();
		
		Connection con = openConn();
		Statement statementForSurveyTypes = openState(con);
		
		String findAllSurveyTypes = "";
		
		if(showHidden == true) {
			findAllSurveyTypes = "SELECT * FROM tblsurveytype"; 
		} else {
			findAllSurveyTypes = "SELECT * FROM tblsurveytype WHERE active";
		}
		
		try {
			
			ResultSet surveyTypesData = statementForSurveyTypes.executeQuery(findAllSurveyTypes);
			
			while(surveyTypesData.next()) {
				
				SurveyType newSurveyType = getTheSurveyType(con, surveyTypesData.getInt("surveytypeid"));
				surveyTypes.add(newSurveyType);
			}
			
		} catch(SQLException e) {
			System.out.println("Could not fetch survey type.  " + e);
		} finally {
			closeConn(con);
		}
		
		return surveyTypes;
		
	}
	
	public List<Survey> getSurveys(int surveytypeid) {
		
		List<Survey> surveys = new ArrayList<>();
		
		Connection con = openConn();
		Statement statementForSurveys = openState(con);
		
		String findAllSurveys = "";
		
		if(surveytypeid == 0) {
			findAllSurveys = "SELECT * FROM tblsurvey";
		} else {
			findAllSurveys = "SELECT * FROM tblsurvey WHERE surveytypeid = " + Integer.toString(surveytypeid) + ";";
		}
		
		try {
			
			ResultSet surveysData = statementForSurveys.executeQuery(findAllSurveys);
			
			while(surveysData.next()) {
				Survey newSurvey = getTheSurvey(con, surveysData.getInt("surveyid"));
				surveys.add(newSurvey);
			}
		} catch(SQLException e) {
			System.out.println("Error when trying to get all of the surveys: " + e);
		} finally {
			closeConn(con);
		}
		
		return surveys;
	}
	
	public boolean saveSurveyResults(int teamid, int surveyid, SurveyQuestions resultsOfSurveyQuestions) {
		
		Connection con = openConn();
		Statement statement = openState(con);
		
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
					
					Statement statementForAnswers = openState(con);
					
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
			closeConn(con);
		}
		
		return false;
	}
	
	public boolean insertSurvey(Survey surveyToInsert, int surveytypeid) {
		
		boolean completionStatus = false;
		Connection con = openConn();
		Statement statement = openState(con);
		
		/* First, we will check if the survey is already in the database, and, if it is, if that question has the same question type */
		int surveyInDatabase = itemInDatabase(con, "tblsurvey", "survey", surveyToInsert.getName());
		int surveyInDatabaseWithSameSurveyType = intInDatabase(con, "tblquestion", "questiontyepid", surveytypeid);
		
		/* If these numbers are 0 or are not the same, then you are good to insert. */
		if(surveyInDatabase == 0 || surveyInDatabaseWithSameSurveyType == 0 || surveyInDatabase != surveyInDatabaseWithSameSurveyType) {
			try {
				
				String insertSQL = "INSERT INTO tblsurvey (surveytypeid, survey) VALUES(" + Integer.toString(surveytypeid) + ", '" + surveyToInsert.getName() + "');";
				statement.executeUpdate(insertSQL);
				System.out.println("Inserted survey into the database!");
				completionStatus = true;
				
			} catch(SQLException e) {
				System.out.println("An error occured when trying to add survey.  " + e);
				completionStatus = false;
			}
		} else { // The question was found in tblsurvey with the same survey type, so we should not add it to the database.
			System.out.println("A duplicate survey with the same survey type was found in the database; cannot insert.");
			completionStatus = false;
		}
		
		closeConn(con); // Close the connection at the end of the operation.
		return completionStatus;
	}

		public boolean editSurveyName(Survey newSurveyData, String surveyNameToEdit) {
			
			System.out.println("Survey " + surveyNameToEdit + " will be changed to " + newSurveyData.getName());
			
			Connection con = openConn();
			Statement statement = openState(con);
			
			// First, I must check if the survey that is being edited doesn't conflict with any other surveys
			int itemInDatabase = itemInDatabase(con, "tblsurvey", "name", newSurveyData.getName());
			if(itemInDatabase == 0) {
				
					
				System.out.println("Good to edit");
				
				try {
					
					String editSurveySQL = "UPDATE tblsurvey SET name = '" + newSurveyData.getName() + "' WHERE name = '" + surveyNameToEdit + "';";
					statement.executeUpdate(editSurveySQL);
					System.out.println("Survey " + surveyNameToEdit + " was changed to " + newSurveyData.getName());

					return true;

				} catch(SQLException e){
					System.out.println("There was an error when trying to edit the survey. " + e);
					return false;
				}	
			} else { // A duplicate survey was found
				System.out.println("There is already a surveyname in the database that has the edited name w/ primary key = " + Integer.toString(itemInDatabase) + " in tblsurvey.");
			}
			
			closeConn(con);
			return false;
		}
		
		
		public boolean deleteSurvey(int surveyid) {
			
			boolean deleteSurveyStatus = false;
			
			Connection con = openConn();
			Statement statement = openState(con);
			
			String deleteSurveySQL = "UPDATE tblsurvey SET active = 'false' WHERE surveyid = " + Integer.toString(surveyid) + ";";
			
			try {
				statement.executeUpdate(deleteSurveySQL);
				deleteSurveyStatus = true;
				
			} catch(SQLException e) {
				System.out.println("Could not delete survey " + e.getMessage());
			} finally {
				closeConn(con);
			}
			
			return deleteSurveyStatus;
		}
	
	public ReportOptions fetchReportOptions() {
		
		ReportOptions returnReportOptions = new ReportOptions();
		
		List<Team> teams = new ArrayList<>();
		List<Survey> surveys = new ArrayList<>();
		
		Connection con = openConn();
		Statement statement = openState(con);
	
		try {
			ResultSet teamsData = statement.executeQuery("SELECT * FROM tblteams");
			
			while(teamsData.next()) { // While there are more rows in the table...
				int teamid = teamsData.getInt("teamid");
				
				String teamName = teamsData.getString("teamname"); // call getString function w/ parameter "name" (column w/ data type string in database)
				Team teamToAdd = new Team(teamid, teamName);
				teams.add(teamToAdd);
				
			}
			
			returnReportOptions.setTeams(teams);
			
			Statement statementForSurveys = openState(con);
			
			ResultSet surveysData = statement.executeQuery("SELECT * FROM tblsurvey");
			
			while(surveysData.next()) {
				
				Survey newSurvey = getTheSurvey(con, surveysData.getInt("surveyid"));
				surveys.add(newSurvey);
				returnReportOptions.setSurveys(surveys);
			}
		} catch (SQLException e) {
			System.out.println("Could not retrieve data from the database " + e.getMessage());
		} finally {
			closeConn(con);
		}
		
		return returnReportOptions;
	}
	
	public ArrayList<SurveyQuestions> fetchReport(int teamid, int surveyid) {
		
		ArrayList<SurveyQuestions> report = new ArrayList<>();
		
		Connection con = openConn();
		Statement statement = openState(con);
		
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
					
					Statement statementForQuestions = openState(con);
					
					ResultSet questionsData = statementForQuestions.executeQuery(getQuestionInformation);
					
					//getQuestion(questionsData, answers, con);
					
					while(questionsData.next()) {
						
						newAnswer.setQuestionScore(answer);
						newAnswer.setQuestion(questionsData.getString("question"));
						newAnswer.setQuestionid(questionsData.getInt("questionid"));
						
						int questionTypeId = questionsData.getInt("questiontypeid");
						String getQuestionTypeInformation = "SELECT * FROM tblquestiontype WHERE questiontypeid = " + Integer.toString(questionTypeId) + ";";
						
						Statement statementForQuestionTypes = openState(con);
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
			closeConn(con);
		}
		
		return report;
	}

	
	public boolean verifyAdmin(String uName, String pWord) {
		
		boolean isAdminBool = false;
		
		Connection con = openConn();
		Statement statement = openState(con);
		
		try {
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
			} 
		} catch (SQLException e) {
			System.out.println("Could not retrieve data from the database " + e.getMessage());
		} finally {
			closeConn(con);
		}
		
		return isAdminBool;
	}
	
	public ArrayList<QuestionType> getQuestionTypes() {
		
		ArrayList<QuestionType> questiontypes = new ArrayList<>();
		
		Connection con = openConn();
		Statement statement = openState(con);
		
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
			closeConn(con);
		}
		
		return questiontypes;
	}
	
	public ArrayList<Question> getQuestionsWithTypeQuestionID(int questiontypeid) {
		
		ArrayList<Question> questions = new ArrayList<>();
		
		QuestionType selectedQuestionType = new QuestionType();
		
		Connection con = openConn();
		Statement statement = openState(con);
		
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
			Statement questionsStatement = openState(con);
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
			closeConn(con);
		}
		
		return questions;
	}
	
	public boolean createTemplate(Template template) {
		
		boolean createTemplateStatus = false;
		
		String type = template.getTypeOfSurvey().getType();
		String description = template.getTypeOfSurvey().getDescription();
		
		Connection con = openConn();
		Statement statement = openState(con);
		
		String checkDuplicateSQL = "SELECT * FROM tblsurveytype WHERE type = '" + template.getTypeOfSurvey().getType() + "';";
		
		try {
			
			ResultSet checkDuplicate = statement.executeQuery(checkDuplicateSQL);
			
			if(checkDuplicate.next() == false) { // No duplicates were found in the database
				System.out.println("Good to insert survey type");
				Statement surveyTypeStatement = openState(con);
				String insertSurveyTypeSQL = "INSERT INTO tblsurveytype (type, description) VALUES('" + type + "', '" + description + "');";
				
				surveyTypeStatement.executeUpdate(insertSurveyTypeSQL);
				surveyTypeStatement.close();
				
				// Now find the surveytypeid of the surveytype that was just inserted
				
				Statement findSurveyTypeIDStatement = openState(con);
				
				String findSurveyTypeIDSQL = "SELECT * FROM tblsurveytype WHERE type = '" + type + "';";
				ResultSet findSurveyTypeIDData = findSurveyTypeIDStatement.executeQuery(findSurveyTypeIDSQL);
				
				while(findSurveyTypeIDData.next()) {
					
					int surveytypeid = findSurveyTypeIDData.getInt("surveytypeid");
					Statement linkStatement = openState(con);
					
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
			closeConn(con);
		}
		
		return createTemplateStatus;
	}
	
	public boolean deleteTemplate(int surveytypeid) {
		
		boolean deleteTemplateStatus = false;
		
		Connection con = openConn();
		Statement statement = openState(con);
		
		String deleteTemplateSQL = "UPDATE tblsurveytype SET active = 'false' WHERE surveytypeid = " + Integer.toString(surveytypeid) + ";";
		
		try {
			statement.executeUpdate(deleteTemplateSQL);
			deleteTemplateStatus = true;
			
		} catch(SQLException e) {
			System.out.println("Could not delete template " + e.getMessage());
		} finally {
			closeConn(con);
		}
		
		return deleteTemplateStatus;
	}
	
	public boolean activateTemplate(int surveytypeid) {
		
		boolean activateTemplateStatus = false;
		
		Connection con = openConn();
		Statement statement = openState(con);
		
		String activateTemplateSQL = "UPDATE tblsurveytype SET active = 'true' WHERE surveytypeid = " + Integer.toString(surveytypeid) + ";";
		
		try {
			statement.executeUpdate(activateTemplateSQL);
			activateTemplateStatus = true;
			
		} catch(SQLException e) {
			System.out.println("Could not delete template " + e.getMessage());
		} finally {
			closeConn(con);
		}
		
		return activateTemplateStatus;
	}
	
	/**
	 * This function checks if the given item (String) is in the given table in the database.
	 * @author Derek
	 * @param con	The connection of the parent function so that this function can create a statement.
	 * @param tblName	The name of the table that you want to check for a duplicate.
	 * @param collumnName	The attribute that can not have a duplicate.
	 * @param item	The item that needs to be checked.  (String)
	 * @return If the item is found, it will return the primary key of the item.  If it is not found, it will return 0.
	 */
	public int itemInDatabase(Connection con, String tblName, String collumnName, String item) {
		
		int itemPrimaryKey = 0;
		Statement statement = openState(con);
		String sql = "SELECT * FROM " + tblName + " WHERE " + collumnName + " = '" + item + "';";
		try {
			ResultSet results = statement.executeQuery(sql);
			if(results.next() == true) {
				itemPrimaryKey = results.getInt(1); // Gets the primary key of the item from the table, no matter what the collumn name is.
			} else {
				itemPrimaryKey = 0;
			}
		} catch(SQLException e) {
			System.out.println("Could not check if " + item + " with collumn name " + collumnName + " is in table " + tblName + ".  " + e);
		}
		
		// We don't want to close the connection because it is still needed for the operation that comes after this function call
		
		return itemPrimaryKey;
	}
	
	/**
	 * This function checks if the given item (int) is in the given table in the database.
	 * @author Derek
	 * @param con	The connection of the parent function so that this function can create a statement.
	 * @param tblName	The name of the table that you want to check for a duplicate.
	 * @param collumnName	The attribute that can not have a duplicate.
	 * @param item	The item that needs to be checked.  (int)
	 * @return If the item is found, it will return the primary key of the item.  If it is not found, it will return 0.
	 */
	public int intInDatabase(Connection con, String tblName, String collumnName, int item) {
		
		int itemPrimaryKey = 0;
		Statement statement = openState(con);
		String sql = "SELECT * FROM " + tblName + " WHERE " + collumnName + " = '" + Integer.toString(item) + "';";
		try {
			ResultSet results = statement.executeQuery(sql);
			if(results.next() == true) {
				itemPrimaryKey = results.getInt(1); // Gets the primary key of the item from the table, no matter what the collumn name is.
			} else {
				itemPrimaryKey = 0;
			}
		} catch(SQLException e) {
			System.out.println("Could not check if " + Integer.toString(item) + " with collumn name " + collumnName + " is in table " + tblName + ".  " + e);
		}
		
		// We don't want to close the connection because it is still needed for the operation that comes after this function call
		
		return itemPrimaryKey;
	}
	
	/**
	 * This function adds a question to the database.  Still need to learn checking criteria.
	 * @author Derek
	 * @param Question	The question that will be added to the database.
	 * @param questiontypeid	The questiontypeid that will be connected to the question.
	 * @return The completion status of the insert question operation (true if successful; false if unsuccessful).
	 */
	public boolean insertQuestion(Question questionToInsert, int questiontypeid) {
		
		boolean completionStatus = false;
		Connection con = openConn();
		Statement statement = openState(con);
		
		/* First, we will check if the question is already in the database, and, if it is, if that question has the same question type */
		int questionInDatabase = itemInDatabase(con, "tblquestion", "question", questionToInsert.getQuestion());
		int questionInDatabaseWithSameQuestionType = intInDatabase(con, "tblquestion", "questiontyepid", questiontypeid);
		
		/* If these numbers are 0 or are not the same, then you are good to insert. */
		if(questionInDatabase == 0 || questionInDatabaseWithSameQuestionType == 0 || questionInDatabase != questionInDatabaseWithSameQuestionType) {
			try {
				
				String insertSQL = "INSERT INTO tblquestion (questiontypeid, question) VALUES(" + Integer.toString(questiontypeid) + ", '" + questionToInsert.getQuestion() + "');";
				statement.executeUpdate(insertSQL);
				System.out.println("Inserted question into the database!");
				completionStatus = true;
				
			} catch(SQLException e) {
				System.out.println("An error occured when trying to add question.  " + e);
				completionStatus = false;
			}
		} else { // The question was found in tblquestion with the same question type, so we should not add it to the database.
			System.out.println("A duplicate question with the same question type was found in the database; cannot insert.");
			completionStatus = false;
		}
		
		closeConn(con); // Close the connection at the end of the operation.
		return completionStatus;
	}
	
	/** 
	 * This function adds a question type to the database.
	 * @author Derek
	 * @param typeOfQuestion	The question type that will be added to the database.
	 * @return The completion status of the insert question type operation (true if successful; false if unsuccessful).
	 */
	
	public boolean insertQuestionType(QuestionType typeOfQuestion) {
		
		boolean completionStatus = false;
		Connection con = openConn();
		Statement statement = openState(con);
		
		/* First, we will check if the question type is already in the database by using the itemInDatabase function. */
		int itemInDatabase = itemInDatabase(con, "tblquestiontype", "type", typeOfQuestion.getType());
		if(itemInDatabase == 0) { // If itemInDatabase = 0, this means that we are good to insert
			try {
				
				String insertSQL = "INSERT INTO tblquestiontype (type, description, numberofoptions) VALUES('" + typeOfQuestion.getType() + "', '" + typeOfQuestion.getDescription() + "', " + Integer.toString(typeOfQuestion.getNumberOfOptions()) + ");";
				statement.executeUpdate(insertSQL);
				System.out.println("Inserted into the database!");
				completionStatus = true;
				
			} catch(SQLException e) {
				System.out.println("There was an error when inserting.  " + e);
				completionStatus = false;
			}
		} else { // An item with the same type was found in the tblquestiontype table, so we can not insert.
			System.out.println("A duplicate item with primary key = " + Integer.toString(itemInDatabase) + " was found in the tblquestiontype table; cannnot insert.");
			completionStatus = false;
		}
		
		closeConn(con); // Close the connection at the end of the operation.
		return completionStatus;
	}
	
	/**
	 * This function updates a question type in the database.
	 * @author Derek
	 * @param editedTypeOfQuestion	The question type that will replace the question type with the given type.
	 * @param questionTypeToEdit	The type of question type that will be edited.
	 * @return The completion status of the edit question type operation (true if successful; false if unsuccessful).
	 */
	public boolean editQuestionType(QuestionType editedTypeOfQuestion, String questionTypeToEdit) {
		
		boolean completionStatus = false;
		Connection con = openConn();
		Statement statement = openState(con);
		
		/* First, we will check if the question type with the type specified in the questionTypeToEdit is already in the database by using the itemInDatabase function. */
		int itemInDatabase = itemInDatabase(con, "tblquestiontype", "type", questionTypeToEdit);
		if(itemInDatabase != 0) { // There is a questiontype in the database with that type, so we are good to perform the update.
			
			System.out.println("Performing update on questiontype with primary key = " + Integer.toString(itemInDatabase));
			String updateSQL = "UPDATE tblquestiontype SET type = '" + editedTypeOfQuestion.getType() + "', description = '" + editedTypeOfQuestion.getDescription() + "', numberofoptions = " + Integer.toString(itemInDatabase) + " WHERE questiontypeid = " + Integer.toString(itemInDatabase) + ";";
			try {
				statement.executeUpdate(updateSQL);
				completionStatus = true;
			} catch(SQLException e) {
				System.out.println("An error occured when trying to edit the questiontype with primary key = " + Integer.toString(itemInDatabase) + " in the tblquestiontype table; cannot perform update.  " + e);
				completionStatus = false;
			}
		} else { // There is not a questiontype in the database with that type, so we cannot perform the update.
			System.out.println("There is no questiontype in the database with that type; cannot perform update.");
			completionStatus = false;
		}
		
		closeConn(con); // Close the connection at the end of the operation.
		return completionStatus;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	// Name: openConnState
	// Parameter(s): Connection and Statement
	// Description:	
	///////////////////////////////////////////////////////////////////////////////////////
	public Connection openConn() {
		Connection con = null;
		
		try {
			con = DriverManager.getConnection("jdbc:postgresql://ec2-107-22-239-155.compute-1.amazonaws.com/daknuflimm0laj", "utufnbbozfaphi", "4a7b61f6d36d53dd87d281cc3786acbe2bdcaf7470f7368b46ac370c1c5dbd95");
			
			if(con != null) { // Error checking
				//System.out.println("Database Connected");
			}
		} catch (SQLException e) {
			System.out.println("Could not connect to the database.  " + e);
		}
		
		return con;
	}
	
	public Statement openState(Connection con) {
		
		Statement statement = null;
		
		try {
			statement = con.createStatement(); // Create a "Statement" object to do operations on
			
			if(statement != null) { // Error checking
				//System.out.println("Statement Connected");
			}
		} catch (SQLException e) {
			System.out.println("Could not connect to the database.  " + e);
		}
		
		return statement;
	}
	
	/** 
	 * @author Octavio
	 * This closes a connection.
	 * @param con The connection to the database.
	 * @return Nothing; simply deletes the connection.
	 */
	public void closeConn(Connection con) {
		if(con != null) {
			try {
				System.out.println("Closing connection...");
				con.close();
			} catch(SQLException e) {
				
			}
		}
	}
	
	/**
	 * @author Derek and Octavio
	 * This function gets the survey type and returns it.
	 * @param con The connection to the database.
	 * @param surveytypeid The ID of the survey type.
	 * @return SurveyType object
	 */
	
	public SurveyType getTheSurveyType(Connection con, int surveytypeid) {
		
		SurveyType typeOfSurvey = new SurveyType();
		Statement statement = openState(con);
		String findSurveyType = "SELECT * FROM tblsurveytype WHERE surveytypeid = " + Integer.toString(surveytypeid) + ";";
		
		try {
			ResultSet surveyTypeData = statement.executeQuery(findSurveyType);
			
			while(surveyTypeData.next()) {
				typeOfSurvey.setSurveytypeid(surveyTypeData.getInt("surveytypeid"));
				typeOfSurvey.setType(surveyTypeData.getString("type"));
				typeOfSurvey.setDescription(surveyTypeData.getString("description"));					
			}
		} catch(SQLException e) {
			System.out.println("Could not get the surveytype.  " + e);
		}
		
		return typeOfSurvey;
		
	}
	
	/**
	 * @author Derek and Octavio
	 * This function gets the survey and returns it.
	 * @param con The connection to the database.
	 * @param surveyid The ID of the survey type.
	 * @return Survey object
	 */
	public Survey getTheSurvey(Connection con, int surveyid) {
		
		Survey theSurvey = new Survey();
		Statement statement = openState(con);
		String findTheSurvey = "SELECT * FROM tblsurvey WHERE surveyid = " + Integer.toString(surveyid) + ";";
		
		try {
			
			ResultSet surveyData = statement.executeQuery(findTheSurvey);
			
			while(surveyData.next()) {
				
				theSurvey.setSurveyid(surveyData.getInt("surveyid"));
				theSurvey.setName(surveyData.getString("name"));
				int surveytypeid = surveyData.getInt("surveytypeid");
				
				theSurvey.setTypeOfSurvey(getTheSurveyType(con, surveytypeid));

			}
			
		} catch(SQLException e) {
			System.out.println("Could not get the survey.  " + e);
		}
		
		return theSurvey;
		
	}
	
	
	
	//*********************
	//***NATHAN'S CHANGE***
	//*********************
	public void getQuestion(ResultSet quesData, ArrayList<Question> questions, Connection con) {
		try {
			while(quesData.next()) {
				
				Question newQuestion = new Question();
				
				newQuestion.setQuestionScore(-1);
				newQuestion.setQuestion(quesData.getString("question"));
				newQuestion.setQuestionid(quesData.getInt("questionid"));
				
				int questionTypeId = quesData.getInt("questiontypeid");
				String getQuestionTypeInformation = "SELECT * FROM tblquestiontype WHERE questiontypeid = " + Integer.toString(questionTypeId) + ";";
				
				Statement statementForQuestionTypes = openState(con);
				
				ResultSet questionTypesData = statementForQuestionTypes.executeQuery(getQuestionTypeInformation);
				
				getQuestionType(questionTypesData, newQuestion); // calls inner while loop
				
				questions.add(newQuestion);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void getQuestionType(ResultSet typeData, Question newQuestion){
		try {
			while(typeData.next()) { 
				
				QuestionType newQuestionType = new QuestionType();
				newQuestionType.setType(typeData.getString("type"));
				newQuestionType.setNumberOfOptions(typeData.getInt("numberofoptions"));
				newQuestionType.setDescription(typeData.getString("description"));
				newQuestion.setTypeOfQuestion(newQuestionType);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//*********************
	//***NATHAN'S CHANGE***
	//*********************
}