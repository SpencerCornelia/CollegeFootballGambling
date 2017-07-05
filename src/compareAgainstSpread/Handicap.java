/*
 * this class will receive the predicted score from the algorithm classes, connect to the database, and use
 * the point spread data to compare the predicted score versus the line.  The end goal will be to produce
 * the outcome of predicted score versus the spread and how the prediction did percentage wise.
 */

package compareAgainstSpread;

import java.sql.*;
import java.util.ArrayList;

public class Handicap {
	static String year = "2008";
	
	//holds all spreads from the db for the team
	static ArrayList<String> Spreads = new ArrayList<String>();
	
	static String teamName;
	
	static double totalGames = 0;
	static double wins = 0;
	static double winningPercentage;

	
	public static void main(String team) throws SQLException {
		
		//DBName should always be spreads in this class
		String DBName = "Spreads" + year;
		
		//receive team name from calling of main in CalculateScore.java
		teamName = team;
		
		//DB info
		String DBurl = "jdbc:mysql://localhost:3306/" + DBName + "?useSSL=false";
		String DBusername = "root";
		String DBpassword = "Wutangclan25";
		
	
		try {
			// 1. Get a connection to database
			Connection myConn = DriverManager.getConnection(DBurl, DBusername, DBpassword);
			
			// 2. Create a statement
			Statement myStmt = myConn.createStatement();
			
			// 3. Execute a SQL Query
			ResultSet myRs;
			myRs = myStmt.executeQuery(
				"Select spread from " + DBName + "." + "`" + teamName + "`;"
			);
			
			// 4. Process the result set			
			while (myRs.next()) {
				Spreads.add(myRs.getString("Spread"));
			}
			
						
		} catch (Exception ex) {
			System.out.println("exception is " + ex);
		}
		
	}
	
	public static void calculateWin(Integer counter, Double pointsOnOffense, Double pointsOnDefense, Double predictOffenseScore, Double predictDefenseScore) {
		//counter needs to be subtracted because ResultSet in CalculateScore.java index begins at 1
		Integer index = counter - 1;
		
		System.out.println(teamName + " scored " + pointsOnOffense + " and gave up " + pointsOnDefense);
		System.out.println("Predicted score = " + predictOffenseScore + " - " + predictDefenseScore);
		
		Boolean beatSpreadReality = didRealityBeatSpread(index, pointsOnOffense, pointsOnDefense);
		Boolean beatSpreadPrediction = didPredictionBeatSpread(index, predictOffenseScore, predictDefenseScore);
		totalGames++;
		
		if (beatSpreadReality == true && beatSpreadPrediction == true) {
			System.out.println("reality and prediction are true");
			wins++;
		}
		
		if (beatSpreadReality == false && beatSpreadPrediction == false) {
			System.out.println("reality and prediction are false");
			wins++;
		}
		
		if (beatSpreadReality == false && beatSpreadPrediction == true) {
			System.out.println("reality = false & prediction = true");
		}
		
		if (beatSpreadReality == true && beatSpreadPrediction == false) {
			System.out.println("reality = true & prediction = false");
		}
		
	}
	
	public static boolean didRealityBeatSpread(Integer counter, Double pointsOnOffense, Double pointsOnDefense) {
		String spreadString = Spreads.get(counter);
		System.out.println("Spread = " + spreadString);
		double spread = Double.parseDouble(spreadString);
		double total = spread + pointsOnOffense;
		
		if (total > pointsOnDefense) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean didPredictionBeatSpread(Integer counter, Double predictOffenseScore, Double predictDefenseScore) {
		String spreadString = Spreads.get(counter);
		double spread = Double.parseDouble(spreadString);
		double total = spread + predictOffenseScore;
		if (total > predictDefenseScore) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void clearSpreads() {
		winningPercentage = (wins / totalGames);
		System.out.println("Wins = " + wins);
		System.out.println("totalGames = " + totalGames);
		System.out.println("Winning Percentage = " + winningPercentage);
		Spreads.clear();
	}
}
