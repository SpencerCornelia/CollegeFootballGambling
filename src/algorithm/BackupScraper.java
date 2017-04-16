package algorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class BackupScraper {

	public static ArrayList<String> opponentsArrayTeamOne = new ArrayList<String>();
	public static ArrayList<Integer> scoresArrayTeamOne = new ArrayList<Integer>();
	public static ArrayList<Integer> opponentsScoresArrayTeamOne = new ArrayList<Integer>();
	
	public static ArrayList<String> opponentsArrayTeamTwo = new ArrayList<String>();
	public static ArrayList<Integer> scoresArrayTeamTwo = new ArrayList<Integer>();
	public static ArrayList<Integer> opponentsScoresArrayTeamTwo = new ArrayList<Integer>();
	
	public static void main(String[] args)  throws IOException {
		String year = "2017";
		
		// grab the document from team 1 and send to parseDoc method
		String team1 = "2636";
		String team1Name = "UTSA";
		String url = "http://www.espn.com/college-football/team/schedule/_/id/" + team1 + "/year/" + year;
		Document doc = Jsoup.connect(url).get();
		parseDocTeamOne(doc);

		// grab the document from team 2 and send to parseDoc method		
		/*
		String team2 = "275";
		String team2Name = "Wisconsin";
		String url2 = "http://www.espn.com/college-football/team/schedule/_/id/" + team2 + "/year/" + year;
		Document doc2 = Jsoup.connect(url2).get();
		parseDocTeamTwo(doc2);
		 */
		// send scoresArray and opponentsArray to Formula class
		/*
		Formula one = new Formula();
		one.receiveTeamOneName(team1Name);
		one.receiveTeamOneOpponentsArray(opponentsArrayTeamOne);
		one.receiveTeamOneScoresArray(scoresArrayTeamOne);
		one.receiveTeamOneOpponentScoresArray(opponentsScoresArrayTeamOne);
		one.teamOneUpdateDB();
		/*
		Formula two = new Formula();
		two.receiveTeamTwoName(team2Name);
		two.receiveTeamTwoOpponentsArray(opponentsArrayTeamTwo);
		two.receiveTeamTwoScoresArray(scoresArrayTeamTwo);
		two.receiveTeamTwoOpponentScoresArray(opponentsScoresArrayTeamTwo);
		two.teamTwoUpdateDB();
		*/
	}
	
	public static void parseDocTeamOne(Document doc) {


		Elements tableRowElements = doc.select("table.tablehead tr");
		
		for (Element row : tableRowElements) {
			String teamName = row.select(".team-name a").text();
			if (teamName.length() > 1) {
				teamOpponentsTeamOne(teamName);
				String scoreText = row.select(".score").text();
				
				// if game gets canceled look for this text
				if (scoreText.equalsIgnoreCase("canceled")) {
					teamScoreTeamOne(true, -1, -1);
					continue;
				}
				
				String game = row.select(".game-status").text();
				
				if (game.equalsIgnoreCase("@")) {
					teamScoreTeamOne(true, -1, -1);
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
						teamScoreTeamOne(win, -1, -1);
						continue;
					}
					
					String winningScore = score.substring(0, dashIndex);
					int winningScoreInt = Integer.parseInt(winningScore);
					String losingScore = score.substring(dashIndex + 1);
					int losingScoreInt = Integer.parseInt(losingScore);
					teamScoreTeamOne(win, winningScoreInt, losingScoreInt);
				
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
						teamScoreTeamOne(win, -1, -1); 
						continue;
					}
					
					String winningScore = score.substring(0, dashIndex);
					int winningScoreInt = Integer.parseInt(winningScore);
					String losingScore = score.substring(dashIndex + 1);
					int losingScoreInt = Integer.parseInt(losingScore);
					teamScoreTeamOne(win, winningScoreInt, losingScoreInt);
				}
			}
			
		}
	}
	
	public static void parseDocTeamTwo(Document doc) {
		
		Elements tableRowElements = doc.select("table.tablehead tr");
		
		for (Element row : tableRowElements) {
			String teamName = row.select(".team-name a").text();
			if (teamName.length() > 1) {
				teamOpponentsTeamTwo(teamName);
				String scoreText = row.select(".score").text();
				
				// if game gets canceled look for this text
				if (scoreText.equalsIgnoreCase("canceled")) {
					teamScoreTeamTwo(true, -1, -1);
					continue;
				}
				
				String game = row.select(".game-status").text();
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
						teamScoreTeamTwo(win, -1, -1); 
						continue;
					}
					
					String winningScore = score.substring(0, dashIndex);
					int winningScoreInt = Integer.parseInt(winningScore);
					String losingScore = score.substring(dashIndex + 1);
					int losingScoreInt = Integer.parseInt(losingScore);
					teamScoreTeamTwo(win, winningScoreInt, losingScoreInt);
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
						teamScoreTeamTwo(win, -1, -1); 
						continue;
					}
					
					String winningScore = score.substring(0, dashIndex);
					int winningScoreInt = Integer.parseInt(winningScore);
					String losingScore = score.substring(dashIndex + 1);
					int losingScoreInt = Integer.parseInt(losingScore);
					teamScoreTeamTwo(win, winningScoreInt, losingScoreInt);
				}
			}
			
		}
		
	}

	// Team One
	
	public static void teamScoreTeamOne(boolean win, int winningScore, int losingScore) {
		if (win) {
			scoresArrayTeamOne.add(winningScore);
			opponentsScoresArrayTeamOne.add(losingScore);
		} else {
			scoresArrayTeamOne.add(losingScore);
			opponentsScoresArrayTeamOne.add(winningScore);
		}
	}
	
	public static void teamOpponentsTeamOne(String opponent) {
		opponentsArrayTeamOne.add(opponent);
	}
	
	// Team Two

	public static void teamScoreTeamTwo(boolean win, int winningScore, int losingScore) {
		
		if (win) {
			scoresArrayTeamTwo.add(winningScore);
			opponentsScoresArrayTeamTwo.add(losingScore);
		} else {
			scoresArrayTeamTwo.add(losingScore);
			opponentsScoresArrayTeamTwo.add(winningScore);
		}
	}
	
	public static void teamOpponentsTeamTwo(String opponent) {
		opponentsArrayTeamTwo.add(opponent);
	}
}
