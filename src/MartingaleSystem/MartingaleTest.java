package MartingaleSystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MartingaleTest {
	static String year = "2016";
	
	static String teamName = "Alabama";
	
    static String[] teamsList = {"Air Force", "Akron", "Alabama", "Appalachian State", "Arizona", "Arizona State", "Arkansas", "Arkansas State", "Army", "Auburn", "Ball State", "Baylor", "Boise State", "Boston College", "Bowling Green", "Buffalo", "BYU", "California", "Fresno State", "UCLA", "UCF", "Central Michigan", "Charlotte", "Cincinnati", "Clemson", "Colorado", "Colorado State", "Connecticut", "Duke", "Eastern Michigan", "East Carolina", "Florida International", "Florida", "Florida Atlantic", "Florida State", "Georgia", "Georgia Southern", "Georgia Tech", "Hawaii", "Houston", "Idaho", "Illinois", "Indiana", "Iowa", "Iowa State", "Kansas", "Kansas State", "Kent State", "Kentucky", "LSU", "Louisiana Tech", "Louisiana-Lafayette", "Louisiana-Monroe", "Louisville", "Marshall", "Maryland", "Massachusetts", "Memphis", "Miami (Florida)", "Miami (Ohio)", "Michigan", "Michigan State", "Middle Tennessee", "Minnesota", "Mississippi", "Mississippi State", "Missouri", "Navy", "Nebraska", "Nevada", "UNLV", "New Mexico", "New Mexico State", "North Carolina", "North Carolina State", "North Texas", "Northern Illinois", "Northwestern", "Notre Dame", "Ohio", "Ohio State", "Oklahoma", "Oklahoma State", "Old Dominion", "Oregon", "Oregon State", "Penn State", "Pittsburgh", "Purdue", "Rice", "Rutgers", "San Diego State", "San Jose State", "South Alabama", "South Carolina", "South Florida", "USC", "SMU", "Southern Mississippi", "Stanford", "Syracuse", "TCU", "Temple", "Tennessee", "Texas", "Texas A&M", "Texas State", "Texas Tech", "UTEP", "UTSA", "Toledo", "Troy", "Tulane", "Tulsa", "Utah", "Utah State", "Vanderbilt", "Virginia", "Virginia Tech", "Wake Forest", "Washington", "Washington State", "West Virginia", "Western Kentucky", "Western Michigan", "Wisconsin", "Wyoming", "UAB", "Georgia State"};
	static ArrayList<String> teamsArrayList = new ArrayList<String>(Arrays.asList(teamsList));
	
	static int bankroll = 25000;
	static int originalBetSize = 110;
	static double betSize = 110;
	static double bankAccount = 25000;
	static double desiredWinningAmount = 100;
	
	static double pointSpread;
	static int teamScore;
	static int opponentScore;
	static double teamScorePlusSpread;
	static double maxWinningStreak = 0;
	static double currentWinningStreak = 0;
	static double currentLosingStreak = 0;
	static double maxLosingStreak = 0;
	static double currentLossAmount = 0;
	static String opponentName;
	static int getLastGame;
	
	// store point spreads in array
	static ArrayList<Double>pointSpreadArrayList = new ArrayList<Double>();
	// store team scores in array
	static ArrayList<Integer>teamScoresArrayList = new ArrayList<Integer>();
	// store opponent scores in array
	static ArrayList<Integer>opponentScoresArrayList = new ArrayList<Integer>();
	// list opponents in array to check how many have spreads
	static ArrayList<String>opponentNameArrayList = new ArrayList<String>();

	public static void main(String[] args) throws SQLException {
		
			String DBName = "Spreads" + year;
			String SecondDBName = "Scores" + year;
			
			//DB info
			String DBurl = "jdbc:mysql://localhost:3306/" + DBName + "?useSSL=false";
			String DBusername = "root";
			String DBpassword = "Wutangclan25";
			
		
			try {
				// 1. Get a connection to database
				Connection myConn = DriverManager.getConnection(DBurl, DBusername, DBpassword);
				
				// 2. Create a statement
				Statement oneStmt = myConn.createStatement();
				Statement twoStmt = myConn.createStatement();
				Statement threeStmt = myConn.createStatement();
				Statement fourStmt = myConn.createStatement();
				
				// 3. Execute a SQL Query
				ResultSet oneRs;
				oneRs = oneStmt.executeQuery(
					"Select spread from " + DBName + "." + "`" + teamName + "`;"
				);
				
				ResultSet twoRs;
				twoRs = twoStmt.executeQuery(
					"Select TeamScore from " + SecondDBName + "." + "`" + teamName + "`;"
				);
				
				ResultSet threeRs;
				threeRs = threeStmt.executeQuery(
					"Select OpponentScore from " + SecondDBName + "." + "`" + teamName + "`;" 
				);
				
				ResultSet fourRs;
				fourRs = fourStmt.executeQuery(
					"Select Opponent from " + SecondDBName + "." + "`" + teamName + "`;"
				);
				
				// 4. Process the result set			
				while (oneRs.next()) {
					pointSpread = oneRs.getDouble("Spread");
					pointSpreadArrayList.add(pointSpread);
				}
				
				while (twoRs.next()) {
					teamScore = twoRs.getInt("TeamScore");
					teamScoresArrayList.add(teamScore);
				}
				
				while (threeRs.next()) {
					opponentScore = threeRs.getInt("OpponentScore");
					opponentScoresArrayList.add(opponentScore);
				}
				
				while (fourRs.next()) {
					opponentName = fourRs.getString("Opponent");
					opponentNameArrayList.add(opponentName);
				}
				
							
			} catch (Exception ex) {
				System.out.println("exception is " + ex);
			}
			RemoveFaultyOpponents(opponentNameArrayList, teamScoresArrayList, opponentScoresArrayList);
			CalculateResults(pointSpreadArrayList, teamScoresArrayList, opponentScoresArrayList);
	}
	
	public static void RemoveFaultyOpponents(ArrayList<String>OpponentNames, ArrayList<Integer>TeamScores, ArrayList<Integer>OpponentScores) {
		for (int i = 0; i < OpponentNames.size(); i++) {
			int checkMe = teamsArrayList.indexOf(OpponentNames.get(i));
			if (checkMe == -1) {
				TeamScores.remove(i);
				OpponentScores.remove(i);
			}
		}
	}
	
	// use this method to calculate the scores
	// and determine if bet is winner/loser
	// update bank account, max winning streak, and max losing streak
	public static void CalculateResults(ArrayList<Double> Spreads, ArrayList<Integer> TeamScores, ArrayList<Integer> OpponentScores) {
		for (int i = 0; i < Spreads.size(); i++) {
			getLastGame = Spreads.size() - 1;
			pointSpread = Spreads.get(i);
			teamScorePlusSpread = TeamScores.get(i) + pointSpread;
			
			if (teamScorePlusSpread > OpponentScores.get(i)) {
				currentWinningStreak++;
				currentLosingStreak = 0;
				if (currentWinningStreak > maxWinningStreak) {
					maxWinningStreak = currentWinningStreak;
				}
				System.out.println("WIN");
				System.out.println(i);
				bankAccount = bankAccount + desiredWinningAmount;
				betSize = originalBetSize;
				currentLossAmount = 0;
				if (getLastGame == i) {
					System.out.println("LAST GAME = WIN");
				}
			}
			
			if (teamScorePlusSpread < OpponentScores.get(i)) {
				currentLosingStreak++;
				currentWinningStreak = 0;
				if (currentLosingStreak > maxLosingStreak) {
					maxLosingStreak = currentLosingStreak;
				}
				System.out.println("LOSS");
				System.out.println(i);
				currentLossAmount = currentLossAmount + betSize;
				betSize = (desiredWinningAmount + currentLossAmount) / .90909;
				
				if (getLastGame == i) {
					System.out.println("LAST GAME = LOSS");
				}
			}
			
			if (teamScorePlusSpread == OpponentScores.get(i)) {
				continue;
			}
			
		}
		System.out.println("END BANK ROLL");
		System.out.println(bankAccount - bankroll - currentLossAmount);
		System.out.println("MAX WINNING STREAK");
		System.out.println(maxWinningStreak);
		System.out.println("MAX LOSING STREAK");
		System.out.println(maxLosingStreak);
	}

}
