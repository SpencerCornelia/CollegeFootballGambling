package algorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Scraper {

	public static ArrayList<String> opponentsArray = new ArrayList<String>();
	public static ArrayList<Integer> scoresArray = new ArrayList<Integer>();
	public static ArrayList<Integer> opponentsScoresArray = new ArrayList<Integer>();

	public static void main(String[] args)  throws IOException {
		String year = "2017";
		
		// grab the document from team 1 and send to parseDoc method
		String team1 = "2636";
		String team1Name = "UTSA";
		String url = "http://www.espn.com/college-football/team/schedule/_/id/" + team1 + "/year/" + year;
		Document doc = Jsoup.connect(url).get();
		parseDoc(doc);

		// send scoresArray and opponentsArray to Formula class
		Formula one = new Formula();
		one.receiveTeamName(team1Name);
		one.receiveTeamOpponentsArray(opponentsArray);
		one.receiveTeamScoresArray(scoresArray);
		one.receiveTeamOpponentScoresArray(opponentsScoresArray);
		one.teamUpdateDB();
	}
	
	public static void parseDoc(Document doc) {


		Elements tableRowElements = doc.select("table.tablehead tr");
		
		for (Element row : tableRowElements) {
			String teamName = row.select(".team-name a").text();
			if (teamName.length() > 1) {
				teamOpponentsTeamOne(teamName);
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
	
	public static void teamOpponentsTeamOne(String opponent) {
		opponentsArray.add(opponent);
	}
}
