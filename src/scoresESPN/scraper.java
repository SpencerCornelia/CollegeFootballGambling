/*
 * this class scrapes ESPN to grab the scores of all FBS games
 * and sends the Team Name, Opponent Name, Team Score, and Opponent Score
 * to the Formula.java class.
 * the parseDoc method deals with parsing the information from ESPN
 */
package scoresESPN;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class scraper {

	public static ArrayList<String> opponentsArray = new ArrayList<String>();
	public static ArrayList<Integer> scoresArray = new ArrayList<Integer>();
	public static ArrayList<Integer> opponentsScoresArray = new ArrayList<Integer>();

	public static void main(String[] args)  throws IOException {
		String year = "2016";
		
		String[] teamsList = {"Air Force", "Akron", "Alabama", "Appalachian State", "Arizona", "Arizona State", "Arkansas", "Arkansas State", "Army", "Auburn", "Ball State", "Baylor", "Boise State", "Boston College", "Bowling Green", "Buffalo", "BYU", "California", "Fresno State", "UCLA", "UCF", "Central Michigan", "Charlotte", "Cincinnati", "Clemson", "Colorado", "Colorado State", "Connecticut", "Duke", "Eastern Michigan", "East Carolina", "Florida International", "Florida", "Florida Atlantic", "Florida State", "Georgia", "Georgia Southern", "Georgia Tech", "Hawaii", "Houston", "Idaho", "Illinois", "Indiana", "Iowa", "Iowa State", "Kansas", "Kansas State", "Kent State", "Kentucky", "LSU", "Louisiana Tech", "Louisiana-Lafayette", "Louisiana-Monroe", "Louisville", "Marshall", "Maryland", "Massachusetts", "Memphis", "Miami (Florida)", "Miami (Ohio)", "Michigan", "Michigan State", "Middle Tennessee", "Minnesota", "Mississippi", "Mississippi State", "Missouri", "Navy", "Nebraska", "Nevada", "UNLV", "New Mexico", "New Mexico State", "North Carolina", "North Carolina State", "North Texas", "Northern Illinois", "Northwestern", "Notre Dame", "Ohio", "Ohio State", "Oklahoma", "Oklahoma State", "Old Dominion", "Oregon", "Oregon State", "Penn State", "Pittsburgh", "Purdue", "Rice", "Rutgers", "San Diego State", "San Jose State", "South Alabama", "South Carolina", "South Florida", "USC", "SMU", "Southern Mississippi", "Stanford", "Syracuse", "TCU", "Temple", "Tennessee", "Texas", "Texas A&M", "Texas State", "Texas Tech", "UTEP", "UTSA", "Toledo", "Troy", "Tulane", "Tulsa", "Utah", "Utah State", "Vanderbilt", "Virginia", "Virginia Tech", "Wake Forest", "Washington", "Washington State", "West Virginia", "Western Kentucky", "Western Michigan", "Wisconsin", "Wyoming", "UAB", "Georgia State"};
		String[] teamsIDList = {"2005", "2006", "333", "2026", "12", "9", "8", "2032", "349", "2", "2050", "239", "68", "103", "189", "2084", "252", "25", "278", "26", "2116", "2117", "2429", "2132", "228", "38", "36", "41", "150", "2199", "151", "2229", "57", "2226", "52", "61", "290", "59", "62", "248", "70", "356", "84", "2294", "66", "2305", "2306", "2309", "96", "99", "2348", "309", "2433", "97", "276", "120", "113", "235", "2390", "193", "130", "127", "2393", "135", "145", "344", "142", "2426", "158", "2440", "2439", "167", "166", "153", "152", "249", "2459", "77", "87", "195", "194", "201", "197", "295", "2483", "204", "213", "221", "2509", "242", "164", "21", "23", "6", "2579", "58", "30", "2567", "2572", "24", "183", "2628", "218", "2633", "251", "245", "326", "2641", "2638", "2636", "2649", "2653", "2655", "202", "254", "328", "238", "258", "259", "154", "264", "154", "264", "265", "277", "98", "2711", "275", "2751", "5", "2247"};
		
		// **** during testing, it seemed to get overloaded.  Was able to get around 23 before it lost communication
		for (int i = 0; i < teamsList.length; i++) {
			// grab the document and send to parseDoc method
			String teamName = teamsList[i];
			String teamID = teamsIDList[i];
			String url = "http://www.espn.com/college-football/team/schedule/_/id/" + teamID + "/year/" + year;
			Document doc = Jsoup.connect(url).get();
			parseDoc(doc);
			
			// send scoresArray and opponentsArray to Formula class
			updateScoresDB one = new updateScoresDB();
			one.receiveTeamName(teamName);
			one.receiveTeamOpponentsArray(opponentsArray);
			one.receiveTeamScoresArray(scoresArray);
			one.receiveTeamOpponentScoresArray(opponentsScoresArray);
			one.teamUpdateDB();
			
			// clear out all array lists so next team has clean slate
			opponentsArray.clear();
			scoresArray.clear();
			opponentsScoresArray.clear();
		}
	}
	
	public static void parseDoc(Document doc) {
		
		Elements tableRowElements = doc.select("table.tablehead tr");
		
		for (Element row : tableRowElements) {
			String teamName = row.select(".team-name a").text();
			if (teamName.length() > 1) {
				teamOpponents(teamName);
				String scoreText = row.select(".score").text();
				
				// if game gets canceled look for this text
				if (scoreText.equalsIgnoreCase("canceled")) {
					teamScore(true, -1, -1);
					continue;
				}
				
				String game = row.select(".game-status").text();
				
				if (game.equalsIgnoreCase("@")) {
					teamScore(true, -1, -1);
					continue;
				}
				String gameStatus = game.substring(2);
				Boolean win = gameStatus.contains("W");
				

				if (win) {
					String score = row.select(".score").text();
					
					// looks for games that ended in OT and removes the two letters from score
					if (score.contains("O")) {
						score = score.replace("O", "");
						score = score.replace("T", "");
						score = score.substring(0, score.length() - 1);
					}
					if (score.contains(" ")) {
						score = score.substring(0, score.length() - 1);
					}
					int dashIndex = score.indexOf("-");
					
					// check if game is cancelled
					if (dashIndex == -1) { 
						teamScore(win, -1, -1);
						continue;
					}
					
					String winningScore = score.substring(0, dashIndex);
					int winningScoreInt = Integer.parseInt(winningScore);
					String losingScore = score.substring(dashIndex + 1);
					int losingScoreInt = Integer.parseInt(losingScore);
					teamScore(win, winningScoreInt, losingScoreInt);
				
				} else {					
					String score = row.select(".score").text();					
					
					// looks for games that ended in OT and removes the two letters from score
					if (score.contains("O")) {
						score = score.replace("O", "");
						score = score.replace("T", "");
						score = score.substring(0, score.length() - 1);
					}
					if (score.contains(" ")) {
						score = score.substring(0, score.length() - 1);
					}
					int dashIndex = score.indexOf("-");
					
					// check if game is cancelled
					if (dashIndex == -1) { 
						teamScore(win, -1, -1); 
						continue;
					}
					
					String winningScore = score.substring(0, dashIndex);
					int winningScoreInt = Integer.parseInt(winningScore);
					String losingScore = score.substring(dashIndex + 1);
					int losingScoreInt = Integer.parseInt(losingScore);
					teamScore(win, winningScoreInt, losingScoreInt);
				}
			}
			
		}
	}


	public static void teamScore(boolean win, int winningScore, int losingScore) {
		if (win) {
			scoresArray.add(winningScore);
			opponentsScoresArray.add(losingScore);
		} else {
			scoresArray.add(losingScore);
			opponentsScoresArray.add(winningScore);
		}
	}
	
	public static void teamOpponents(String opponent) {
		opponentsArray.add(opponent);
	}
}
