/*
 * this class will receive the predicted score from the algorithm classes, connect to the database, and use
 * the point spread data to compare the predicted score versus the line.  The end goal will be to produce
 * the outcome of predicted score versus the spread and how the prediction did percentage wise.
 */

package compareAgainstSpread;

import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;

public class Handicap {

	public static void main(String[] args) throws SQLException {
		String DBName = "Scores2008";
		String DBurl = "jdbc:mysql://localhost:3306/" + DBName + "?useSSL=false";
		String DBusername = "root";
		String DBpassword = "Wutangclan25";		
		
	}
	
}
