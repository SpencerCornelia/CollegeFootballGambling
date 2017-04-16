package algorithm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

public class Formula {
	// DB Info
	String DBName = "Test";
	String DBurl = "jdbc:mysql://localhost:3306/" + DBName + "?useSSL=false";
	String DBusername = "root";
	String DBpassword = "Wutangclan25";
	
	public String teamName;
	// array of opponents faced by team one
	public static ArrayList<String> opponentsArrayList = new ArrayList<String>();
	// array for points scored by team one on offense
	public static ArrayList<Integer> scoresArrayList = new ArrayList<Integer>();
	// array for points given up by team one on defense
	public static ArrayList<Integer> opponentsScoresArrayList = new ArrayList<Integer>();

	public static void main(String[] args) {

	}
	
	public void receiveTeamName(String team) {
		teamName = team;
	}
	
	public void receiveTeamOpponentsArray(ArrayList<String> opponentsArray) {
		for (String opp : opponentsArray) {
			opponentsArrayList.add(opp);
		}
	}
	
	public void receiveTeamScoresArray(ArrayList<Integer> scoresArray) {
		for (Integer score : scoresArray) {
			scoresArrayList.add(score);
		}
	}
	
	public void receiveTeamOpponentScoresArray(ArrayList<Integer> oppScoreArray) {
		for (Integer score : oppScoreArray) {
			opponentsScoresArrayList.add(score);
		}
	}
	
	public void teamUpdateDB() {
		for (int i = 0; i < opponentsArrayList.size(); i++) {
			String opponent = opponentsArrayList.get(i);
			int teamScore = scoresArrayList.get(i);
			int opponentScore = opponentsScoresArrayList.get(i);
			
			// check for hawaii as opponent
			if (opponent.contains("'")) {
				teamHawaii(teamScore, opponentScore);
				continue;
			}
			
			// check for San Jose St as opponent
			Boolean b = opponent.startsWith("San Jos");
			if (b) {
				teamSanJose(teamScore, opponentScore);
				continue;
			}
			
			try {
				// 1. Get a connection to database
				Connection myConn = DriverManager.getConnection(DBurl, DBusername, DBpassword);
				
				// 2. Create a statement
				Statement myStmt = myConn.createStatement();
				
				// 3. Execute a SQL Query
				int yourRs = myStmt.executeUpdate(
						"INSERT INTO `" + DBName + "`.`" + teamName + "`" 
						+ " (Opponent, TeamScore, OpponentScore) VALUES(" + "'" + opponent + "'" 
						+ ", " + teamScore
						+ ", " + opponentScore + ");"
						);

			} catch (Exception ex) {
				System.out.println("exception is " + ex);
				System.out.println("Opponent is " + opponent);
			}
		}
	}

	public void teamHawaii(int teamScore, int opponentScore) {
		try {
			// 1. Get a connection to database
			Connection myConn = DriverManager.getConnection(DBurl, DBusername, DBpassword);
			
			// 2. Create a statement
			Statement myStmt = myConn.createStatement();
			
			// 3. Execute a SQL Query
			int yourRs = myStmt.executeUpdate(
					"INSERT INTO `" + DBName + "`.`" + teamName + "`" 
					+ " (Opponent, TeamScore, OpponentScore) VALUES('Hawaii', " + teamScore + ", " + opponentScore + ");"
					);

		} catch (Exception ex) {
			System.out.println("exception is " + ex);
		}
	}

	public void teamSanJose(int teamScore, int opponentScore) {
		try {
			// 1. Get a connection to database
			Connection myConn = DriverManager.getConnection(DBurl, DBusername, DBpassword);
			
			// 2. Create a statement
			Statement myStmt = myConn.createStatement();
			
			// 3. Execute a SQL Query
			int yourRs = myStmt.executeUpdate(
					"INSERT INTO `" + DBName + "`.`" + teamName + "`" 
					+ " (Opponent, TeamScore, OpponentScore) VALUES('San Jose State', " + teamScore + ", " + opponentScore + ");"
					);

		} catch (Exception ex) {
			System.out.println("exception is " + ex);
		}
	}

}

