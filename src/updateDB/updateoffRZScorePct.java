package updateDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class updateoffRZScorePct {

	public Map<String, Double> offRZScorePctMap;

	public static void main(String[] args) {

	}
	
	public void receiveMap(Map <String, Double> mapReceived) {
		 offRZScorePctMap = new HashMap<String, Double>();
		 Set set = mapReceived.entrySet();
		 Iterator i = set.iterator();
		 
		 while (i.hasNext()) {
			 Map.Entry me = (Map.Entry)i.next();
			 String teamName = (String) me.getKey();
			 double offRZScorePctDouble = (double) me.getValue();
			 offRZScorePctMap.put(teamName, offRZScorePctDouble);
		 }

		 updateDB(offRZScorePctMap);
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
			 	double offRZScorePctDouble = (double) me.getValue();
			 	createTableRs = myStmt.executeUpdate(
						  "UPDATE " + "`" + DBName + "`" + "." + "`" + teamName + "` "
						  + "SET offRZScorePct=" + offRZScorePctDouble + ";" 
				);
				System.out.println("updated " + teamName + " with a value of " + offRZScorePctDouble);
		 	 }
				
		} catch (Exception ex) {
			System.out.println("exception is " + ex);
		}
	}
}
