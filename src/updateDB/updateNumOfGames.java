package updateDB;

import java.sql.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class updateNumOfGames {

	public Map<String, Integer> numOfGamesMap;
	
	public static void main(String[] args) {

	}
	
	public void receiveMap(Map <String, Integer> mapReceived) {
		 numOfGamesMap = new HashMap<String, Integer>();
		 Set set = mapReceived.entrySet();
		 Iterator i = set.iterator();
		 
		 while (i.hasNext()) {
			 Map.Entry me = (Map.Entry)i.next();
			 String teamName = (String) me.getKey();
			 int numberOfGamesInt = (int) me.getValue();
			 numOfGamesMap.put(teamName, numberOfGamesInt);
		 }
		 // use this to test before calling the updateDB method
		 System.out.println("numOfGamesMap = " + numOfGamesMap);
		 
		 updateDB(numOfGamesMap);
	}
	
	public void updateDB(Map <String, Integer> updateMap) {
		String DBName = "CollegeFootballStats2016";
		String DBurl = "jdbc:mysql://localhost:3306/" + DBName + "?useSSL=false";
		String DBusername = "root";
		String DBpassword = "Wutangclan25";
		
		 Set set = updateMap.entrySet();
		 Iterator i = set.iterator();
		
		try {
			// 1. Get a connection to database
			Connection myConn = DriverManager.getConnection(DBurl, DBusername, DBpassword);
			
			// 2. Create a statement
			Statement myStmt = myConn.createStatement();
			
			// 3. Execute a SQL Query
			
			int createTableRs;
			 while (i.hasNext()) {
			 	Map.Entry me = (Map.Entry)i.next();
			 	String teamName = (String) me.getKey();
			 	int numberOfGamesInt = (int) me.getValue();
			 	createTableRs = myStmt.executeUpdate(
						 "insert into " + "`" + DBName + "`" + "." + "`" + teamName + "`"
						  + " (gamesPlayed) Values (" + numberOfGamesInt + ");"
				);
				System.out.println("updated " + teamName + " with a value of " + numberOfGamesInt);
		 	 }
				
		} catch (Exception ex) {
			System.out.println("exception is " + ex);
		}
	}
}
