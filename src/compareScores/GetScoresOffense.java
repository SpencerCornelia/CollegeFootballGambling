package compareScores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class GetScoresOffense {
	// DB Info
	static String DBName = "Scores2008";
	static String DBurl = "jdbc:mysql://localhost:3306/" + DBName + "?useSSL=false";
	static String DBusername = "root";
	static String DBpassword = "Wutangclan25";
	
	static String teamName = "Air Force";
	// array of opponents faced by team one
    static ArrayList<String> opponentsArrayList = new ArrayList<String>();
    // array of scores for team
    static ArrayList<Integer>teamScoresArrayList = new ArrayList<Integer>();
    // array of scores for the opponent
    static ArrayList<Integer>opponentScoresArrayList = new ArrayList<Integer>();
    // array of opponents points per game average
    static ArrayList<Double>opponentsPointsPerGameArrayList = new ArrayList<Double>();
    // array of all of the score differences between team and opponents
    static ArrayList<Double>percentageDifferenceOffense = new ArrayList<Double>();
   
    // use this to check if tables exist
    static String[] teamsList = {"Air Force", "Akron", "Alabama", "Appalachian State", "Arizona", "Arizona State", "Arkansas", "Arkansas State", "Army", "Auburn", "Ball State", "Baylor", "Boise State", "Boston College", "Bowling Green", "Buffalo", "BYU", "California", "Fresno State", "UCLA", "UCF", "Central Michigan", "Charlotte", "Cincinnati", "Clemson", "Colorado", "Colorado State", "Connecticut", "Duke", "Eastern Michigan", "East Carolina", "Florida International", "Florida", "Florida Atlantic", "Florida State", "Georgia", "Georgia Southern", "Georgia Tech", "Hawaii", "Houston", "Idaho", "Illinois", "Indiana", "Iowa", "Iowa State", "Kansas", "Kansas State", "Kent State", "Kentucky", "LSU", "Louisiana Tech", "Louisiana-Lafayette", "Louisiana-Monroe", "Louisville", "Marshall", "Maryland", "Massachusetts", "Memphis", "Miami (Florida)", "Miami (Ohio)", "Michigan", "Michigan State", "Middle Tennessee", "Minnesota", "Mississippi", "Mississippi State", "Missouri", "Navy", "Nebraska", "Nevada", "UNLV", "New Mexico", "New Mexico State", "North Carolina", "North Carolina State", "North Texas", "Northern Illinois", "Northwestern", "Notre Dame", "Ohio", "Ohio State", "Oklahoma", "Oklahoma State", "Old Dominion", "Oregon", "Oregon State", "Penn State", "Pittsburgh", "Purdue", "Rice", "Rutgers", "San Diego State", "San Jose State", "South Alabama", "South Carolina", "South Florida", "USC", "SMU", "Southern Mississippi", "Stanford", "Syracuse", "TCU", "Temple", "Tennessee", "Texas", "Texas A&M", "Texas State", "Texas Tech", "UTEP", "UTSA", "Toledo", "Troy", "Tulane", "Tulsa", "Utah", "Utah State", "Vanderbilt", "Virginia", "Virginia Tech", "Wake Forest", "Washington", "Washington State", "West Virginia", "Western Kentucky", "Western Michigan", "Wisconsin", "Wyoming", "UAB", "Georgia State"};
	static ArrayList<String> teamsArrayList = new ArrayList<String>(Arrays.asList(teamsList));
    
	public static void main(String[] args) {
		scores();
		
		// clear all Array Lists after calculation in order to be empty for use by next team
		opponentsArrayList.clear();
		teamScoresArrayList.clear();
		opponentScoresArrayList.clear();
		percentageDifferenceOffense.clear();
	}
	
	public static void scores() {
		
		try {
			// 1. Get a connection to database
			Connection myConn = DriverManager.getConnection(DBurl, DBusername, DBpassword);
			
			// 2. Create a statement
			Statement myStmt = myConn.createStatement();
			
			// 3. Execute a SQL Query
			ResultSet executioner = myStmt.executeQuery(
					"SELECT * FROM " + "`" + DBName + "`.`" + teamName + "`;"
					);
			while (executioner.next()) {
				// grab opponent team name and add to ArrayList				
				String opponent = executioner.getString("Opponent");
				// check to see if opponent exists in database
				int checkMe = teamsArrayList.indexOf(opponent);
				if (checkMe != -1) {
					opponentsArrayList.add(opponent);
				}	
				// grab team score and add it to ArrayList
				int teamScore = executioner.getInt("TeamScore");
				if (checkMe != -1) {
					teamScoresArrayList.add(teamScore);
				}
				// grab opponents scores and add it to ArrayList
				int opponentScore = executioner.getInt("OpponentScore");
				if (checkMe != -1) {
					opponentScoresArrayList.add(opponentScore);
				}
			}
	
		} catch (Exception ex) {
			System.out.println("exception is " + ex);
		}
		
		getOpponentDefensiveScoreAverages(opponentsArrayList);
	}
	
	// this method looks up each opponent and saves their average points given up
	public static void getOpponentDefensiveScoreAverages(ArrayList<String> opponentsArrayList) {
		
		String StatsDBName = "CollegeFootballStats2008";
		String StatsDBurl = "jdbc:mysql://localhost:3306/" + StatsDBName + "?useSSL=false";
		
		for (int i = 0; i < opponentsArrayList.size(); i++) {
			String opp = opponentsArrayList.get(i);
			int checkMe = teamsArrayList.indexOf(opp);
			// make sure team shows in teamsArrayList to avoid error looking up DB info
			if (checkMe != -1) {
				try {
					// 1. Get a connection to database
					Connection myConn = DriverManager.getConnection(StatsDBurl, DBusername, DBpassword);
					
					// 2. Create a statement
					Statement myStmt = myConn.createStatement();
					
					// 3. Execute a SQL Query
					ResultSet executioner = myStmt.executeQuery(
							"SELECT defPointsPerGame FROM " + "`" + StatsDBName + "`.`" + opponentsArrayList.get(i) + "`;"
							);
					
					while (executioner.next()) {
						// grab opponent team name and add to ArrayList
						double defPointsPerGame = executioner.getDouble("defPointsPerGame");
						opponentsPointsPerGameArrayList.add(defPointsPerGame);
						int teamScore = teamScoresArrayList.get(i);
						compareScore(defPointsPerGame, teamScore);
					}
			
				} catch (Exception ex) {
					System.out.println("exception is " + ex);
				}
			}
		}
		
		calculateScoreDifference();
	}
	
	public static void compareScore(double opponentPoints, int teamScore) {
		double difference = (teamScore - opponentPoints);
		percentageDifferenceOffense.add(difference);
	}
	
	public static void calculateScoreDifference() {
		double average = 0;
		double total = 0;
		for (int i = 0; i < percentageDifferenceOffense.size(); i++) {
			total += percentageDifferenceOffense.get(i);
		}
		
		average = total / percentageDifferenceOffense.size();
		offensivePrediction(average);
	}
	
	public static void offensivePrediction(double averagePointsPercent) {
		// averagePointsPercent is delta between points scored and opponents points given up
		
		for (int i = 0; i < teamScoresArrayList.size(); i++) {
			// points scored by team in every game of one season
			double points = teamScoresArrayList.get(i);
			
			// average points given up by opponent
			double avgPointsGivenUp = opponentsPointsPerGameArrayList.get(i);
			
			// predict score of team
			double predictedScore = avgPointsGivenUp + (averagePointsPercent * avgPointsGivenUp);
			
			CalculateScore score = new CalculateScore();
			score.receiveOffensiveScores(teamName, points, avgPointsGivenUp, predictedScore); 
			GetScoresDefense d = new GetScoresDefense();
			d.main(null);
		}
	}

}

