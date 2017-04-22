package compareScores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class GetScoresDefense {
	// DB Info
	static String DBName = "Scores2008";
	static String DBurl = "jdbc:mysql://localhost:3306/" + DBName + "?useSSL=false";
	static String DBusername = "root";
	static String DBpassword = "Wutangclan25";
	
	 // array of opponents points per game average
    static ArrayList<Double>opponentsPointsPerGameArrayList = new ArrayList<Double>();
    // array of all of the score differences between team and opponents
    static ArrayList<Double>percentageDifferenceDefense = new ArrayList<Double>();
    // array of all opponents
    static ArrayList<String> arrayListOfOpponents;
    // array of opponents scores
    static ArrayList<Integer> arrayListOfOpponentsScores;

	public static void main(ArrayList<String> opponentsArrayList, ArrayList<Integer> opponentScoresArrayList) throws SQLException {
		arrayListOfOpponents = opponentsArrayList;
		arrayListOfOpponentsScores = opponentScoresArrayList;
		getOpponentOffensiveScoreAverages();
	}

	public static void getOpponentOffensiveScoreAverages() throws SQLException {
		String StatsDBName = "CollegeFootballStats2008";
		String StatsDBurl = "jdbc:mysql://localhost:3306/" + StatsDBName + "?useSSL=false";
		
		for (int i = 0; i < arrayListOfOpponents.size(); i++) {
			String opp = arrayListOfOpponents.get(i);
			Connection myConn = null;
			try {
				// 1. Get a connection to database
				myConn = DriverManager.getConnection(StatsDBurl, DBusername, DBpassword);
				
				// 2. Create a statement
				Statement myStmt = myConn.createStatement();
				
				// 3. Execute a SQL Query
				ResultSet executioner = myStmt.executeQuery(
						"SELECT offPointsPerGame FROM " + "`" + StatsDBName + "`.`" + arrayListOfOpponents.get(i) + "`;"
						);
				
				while (executioner.next()) {
					// grab opponent team name and add to ArrayList
					double offPointsPerGame = executioner.getDouble("offPointsPerGame");
					opponentsPointsPerGameArrayList.add(offPointsPerGame);
					int teamScore = arrayListOfOpponentsScores.get(i);
					compareScore(offPointsPerGame, teamScore);
				}
		
			} catch (Exception ex) {
				System.out.println("exception is " + ex);
			} finally {
				if (myConn != null) {
					myConn.close();
				}
			}
		}
		
		calculateScoreDifference();
	}
	
	public static void compareScore(double opponentPoints, int teamScore) {
		double difference = (teamScore - opponentPoints);
		percentageDifferenceDefense.add(difference);
	}
	
	public static void calculateScoreDifference() {
		double average = 0;
		double total = 0;
		for (int i = 0; i < percentageDifferenceDefense.size(); i++) {
			total += percentageDifferenceDefense.get(i);
		}
		
		average = total / percentageDifferenceDefense.size();
		average = average / 100;
		defensivePrediction(average);
	}
	
	public static void defensivePrediction(double averagePointsPercent) {
		// averagePointsPercent is delta between points given up and opponents points scored per game
		
		for (int i = 0; i < arrayListOfOpponentsScores.size(); i++) {
			// points scored by team in every game of one season
			double points = arrayListOfOpponentsScores.get(i);
			
			// average points given up by opponent
			double avgPointsScored = opponentsPointsPerGameArrayList.get(i);
			
			// predict score of team
			double predictedScore = avgPointsScored + (averagePointsPercent * avgPointsScored);
			
			CalculateScore score = new CalculateScore();
			score.receiveDefensiveScores(points, avgPointsScored, predictedScore); 
		}
	}
}
