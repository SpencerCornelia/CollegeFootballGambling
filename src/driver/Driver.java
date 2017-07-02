/*
 * this class does all testing with DB interaction
 * this class also provides a way to create new tables for all FCS teams for a new year
 * this class also includes a way to update the DB when ESPN provides team names that don't coincide with the DB
 */

package driver;
import java.sql.*;
import java.util.ArrayList;

public class Driver {

	public static void main(String[] args) {
		String DBName = "Scores2008";
		String DBurl = "jdbc:mysql://localhost:3306/" + DBName + "?useSSL=false";
		String DBusername = "root";
		String DBpassword = "Wutangclan25";
		
		// list of all FCS teams
		String[] teamsList = {"Air Force", "Akron", "Alabama", "Appalachian State", "Arizona", "Arizona State", "Arkansas", "Arkansas State", "Army", "Auburn", "Ball State", "Baylor", "Boise State", "Boston College", "Bowling Green", "Buffalo", "BYU", "California", "Fresno State", "UCLA", "UCF", "Central Michigan", "Charlotte", "Cincinnati", "Clemson", "Colorado", "Colorado State", "Connecticut", "Duke", "Eastern Michigan", "East Carolina", "Florida International", "Florida", "Florida Atlantic", "Florida State", "Georgia", "Georgia Southern", "Georgia Tech", "Hawaii", "Houston", "Idaho", "Illinois", "Indiana", "Iowa", "Iowa State", "Kansas", "Kansas State", "Kent State", "Kentucky", "LSU", "Louisiana Tech", "Louisiana-Lafayette", "Louisiana-Monroe", "Louisville", "Marshall", "Maryland", "Massachusetts", "Memphis", "Miami (Florida)", "Miami (Ohio)", "Michigan", "Michigan State", "Middle Tennessee", "Minnesota", "Mississippi", "Mississippi State", "Missouri", "Navy", "Nebraska", "Nevada", "UNLV", "New Mexico", "New Mexico State", "North Carolina", "North Carolina State", "North Texas", "Northern Illinois", "Northwestern", "Notre Dame", "Ohio", "Ohio State", "Oklahoma", "Oklahoma State", "Old Dominion", "Oregon", "Oregon State", "Penn State", "Pittsburgh", "Purdue", "Rice", "Rutgers", "San Diego State", "San Jose State", "South Alabama", "South Carolina", "South Florida", "USC", "SMU", "Southern Mississippi", "Stanford", "Syracuse", "TCU", "Temple", "Tennessee", "Texas", "Texas A&M", "Texas State", "Texas Tech", "UTEP", "UTSA", "Toledo", "Troy", "Tulane", "Tulsa", "Utah", "Utah State", "Vanderbilt", "Virginia", "Virginia Tech", "Wake Forest", "Washington", "Washington State", "West Virginia", "Western Kentucky", "Western Michigan", "Wisconsin", "Wyoming", "UAB", "Georgia State"};
		
		// list of all teams incorrectly added to DB from ESPN scores scraping
		String[] teamToEditList = {"Cal", "W Michigan", "Miami (OH)", "Appalachian St", "Ga Southern", "Washington St", "UMass", "E Michigan", "UConn", "Cent Michigan", "New Mexico St", "UL Monroe", "UL Lafayette", "Southern Miss", "Ole Miss", "Mid Tennessee", "FIU", "FAU", "Miss St", "NC State", "Miami", "San José State", "W Kentucky", "LA Tech", "N Illinois", "Pitt"};
		
		// list of updated names to update in DB - make sure this list mirrors teamToEditList
		String [] teamEditList = {"California", "Western Michigan", "Miami (Ohio)", "Appalachian State", "Georgia Southern", "Washington State", "Massachusetts", "Eastern Michigan", "Connecticut", "Central Michigan", "New Mexico State", "Louisiana-Monroe", "Louisiana-Lafayette", "Southern Mississippi", "Mississippi", "Middle Tennessee", "Florida International", "Florida Atlantic", "Mississippi State", "North Carolina State", "Miami (Florida)", "San Jose State", "Western Kentucky", "Louisiana Tech", "Northern Illinois", "Pittsburgh"};
		
		try {
			// 1. Get a connection to database
			Connection myConn = DriverManager.getConnection(DBurl, DBusername, DBpassword);
			
			// 2. Create a statement
			Statement myStmt = myConn.createStatement();
			Statement twoStmt = myConn.createStatement();
			Statement threeStmt = myConn.createStatement();
			
			// 3. Execute a SQL Query
			
			// use these two resultset statements as examples
			// ResultSet myRs = myStmt.executeQuery("select * from CollegeFootballStats.Test");
			// int yourRs = twoStmt.executeUpdate("insert into CollegeFootballStats.Test (PassYards) Values (10000)");
			
			// ONLY USE THIS FOR CLEARING ALL TABLES
			/*
			int createTableRs;
			for (int i = 0; i < teamsList.length; i++) {
				createTableRs = threeStmt.executeUpdate(
					"TRUNCATE" + " `" + DBName + "`.`" + teamsList[i] + "`" 
				);
			}
			*/
			// *** use this for all of the teams that get inputed incorrectly as Opponents
			// *** need to run this after every week of inputting teams from ESPN scores
			// *** make sure DB = scoresYY
			/*
			int createTableRs;
			for (int i = 0; i < teamsList.length; i++) {
				for (int j = 0; j < teamEditList.length; j++) {	
					createTableRs = threeStmt.executeUpdate(
						"UPDATE " + "`" + DBName + "`.`" + teamsList[i] + "` "
					    + "SET `Opponent` = '" + teamEditList[j] +"' "
						+ "WHERE `Opponent`='" + teamToEditList[j] + "'"		
					);	
				}
			}
			*/
			
			// ***** Use updateNumOfGames.java file for creating first row of data in a new table *****
			//   ***  This is query for creating new CollegeFootballStats schema in DB 
			/*
			int createTableRs;
			for (int i = 0; i < teamsList.length; i++) {	
				createTableRs = threeStmt.executeUpdate(
						 "CREATE TABLE" + " `" + DBName + "`.`" + teamsList[i] + "` (" 
						 + "`gamesPlayed` INT(11), "
						 + "`offPointsPerGame` DOUBLE, "
						 + "`offRushAvg` DOUBLE, "
						 + "`offRushAtt` DOUBLE, "
						 + "`offPassPct` DOUBLE, "
						 + "`offPassYdsAtt` DOUBLE, "
						 + "`offAttPerGame` DOUBLE, "
						 + "`offYardsPerPlay` DOUBLE, "
						 + "`offFirstDwns` DOUBLE, "
						 + "`off20PlusYds` DOUBLE, "
						 + "`off30PlusYds` DOUBLE, "
						 + "`off40PlusYds` DOUBLE, "
						 + "`defPointsPerGame` DOUBLE, "
						 + "`defRushAvg` DOUBLE, "
						 + "`defPassPct` DOUBLE, "
						 + "`defPassYdsAtt` DOUBLE, "
						 + "`defPassAttPerGame` DOUBLE, "
						 + "`defYdsPerPlay` DOUBLE, "
						 + "`defTFL` DOUBLE, "
						 + "`def20PlusPlays` DOUBLE, "
						 + "`def30PlusPlays` DOUBLE, "
						 + "`def40PlusPlays` DOUBLE, "
						 + "`turnMargin` DOUBLE, "
						 + "`off3rdDownPct` DOUBLE, "
						 + "`offRZScorePct` DOUBLE, "
						 + "`offRZTDPct` DOUBLE, "
						 + "`def3rdDownPct` DOUBLE, "
						 + "`defRZScorePct` DOUBLE, "
						 + "`defRZTDPct` DOUBLE"
						 + ")"
				);
			}
			*/
			/*
			int createTableRs;
			for (int i = 0; i < teamsList.length; i++) {
				createTableRs = threeStmt.executeUpdate(
						"CREATE TABLE" + " `" + DBName + "`.`" + teamsList[i] + "` ("
						+ "`Opponent` INT NULL, "
						+ "`Spread` DOUBLE NULL, "
						+ "`TotalPoints` INT NULL);"
				);
			}
			*/
			
			// ***  This is for creating Scores schema ***
			/*
			int createTableRs;
			for (int i = 0; i < teamsList.length; i++) {	
				createTableRs = threeStmt.executeUpdate(
						 "CREATE TABLE" + " `" + DBName + "`.`" + teamsList[i] + "` (" 
					   + "`Opponent` VARCHAR(50) NULL, "
					   + "`TeamScore` INT NULL, "
					   + "`OpponentScore` INT NULL);"
				);
			}
			*/

			// 4. Process the result set
			/*
			while (myRs.next()) {
				System.out.println(myRs.getString(""));
			}
			*/
			
			// this updates Spread DB --> insert values into teamName, teams, spread, and totals
			/*
			String teamName = "";
			String[] teams = {};
			String[] spread = {};
			Double[] totals = {};
			
			int createTableRs;
			for (int i = 0; i < teams.length; i++) {	
				createTableRs = threeStmt.executeUpdate(
					"INSERT INTO " + "`" + DBName + "`.`" + teamName + "` "
					+ "(`Opponent`, `Spread`, `TotalPoints`) "
					+ "VALUES (" + "'" + teams[i] + "'" + ", " + spread[i] + ", " + totals[i] + ");"
				);
			}
			*/
		} catch (Exception ex) {
			System.out.println("exception is " + ex);
		}
		
	}

}
