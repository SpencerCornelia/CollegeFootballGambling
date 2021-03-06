package updateDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class updatedef40PlusPlays {
	
	public Map<String, Double> fourtyPlusMap;

	public static void main(String[] args) {

	}
	
	public void receiveMap(Map <String, Double> mapReceived) {
		 fourtyPlusMap = new HashMap<String, Double>();
		 Set set = mapReceived.entrySet();
		 Iterator i = set.iterator();
		 
		 while (i.hasNext()) {
			 Map.Entry me = (Map.Entry)i.next();
			 String teamName = (String) me.getKey();
			 double def40PlusPlaysDouble = (double) me.getValue();
			 fourtyPlusMap.put(teamName, def40PlusPlaysDouble);
		 }

		 updateDB(fourtyPlusMap);
	}
	
	public void updateDB(Map <String, Double> updateMap) {
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
			 	double fourtyPlusDouble = (double) me.getValue();
			 	createTableRs = myStmt.executeUpdate(
						  "UPDATE " + "`" + DBName + "`" + "." + "`" + teamName + "` "
						  + "SET def40PlusPlays=" + fourtyPlusDouble + ";" 
				);
				System.out.println("updated " + teamName + " with a value of " + fourtyPlusDouble);
		 	 }
				
		} catch (Exception ex) {
			System.out.println("exception is " + ex);
		}
	}
}
