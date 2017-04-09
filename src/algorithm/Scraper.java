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
	
	public static void main(String[] args)  throws IOException {
		String teamID = "2005";
		String year = "2008";
		String url = "http://www.espn.com/college-football/team/schedule/_/id/" + teamID + "/year/" + year;
		Document doc = Jsoup.connect(url).get();
		
		Elements tableRowElements = doc.select("table.tablehead tr");
		
		for (Element row : tableRowElements) {
			String teamName = row.select(".team-name a").text();
			if (teamName.length() > 1) {
				teamOpponents(teamName);
				String gameStatus = row.select(".greenfont").text();
				if (gameStatus.length() > 0) {
					String score = row.select(".score").text();
					int dashIndex = score.indexOf("-");
					String winningScore = score.substring(0, dashIndex);
					int winningScoreInt = Integer.parseInt(winningScore);
					String losingScore = score.substring(dashIndex + 1);
					int losingScoreInt = Integer.parseInt(losingScore);
					teamScore(gameStatus.length(), winningScoreInt, losingScoreInt);
				} else {					
					String score = row.select(".score").text();
					int dashIndex = score.indexOf("-");
					String winningScore = score.substring(0, dashIndex);
					int winningScoreInt = Integer.parseInt(winningScore);
					String losingScore = score.substring(dashIndex + 1);
					int losingScoreInt = Integer.parseInt(losingScore);
					teamScore(gameStatus.length(), winningScoreInt, losingScoreInt);
				}
			}
			
		}
	}
	
	public static void teamScore(int gameStatus, int winningScore, int losingScore) {
		//System.out.println("winningScore = " + winningScore);
		//System.out.println("losingScore = " + losingScore);
		
		if (gameStatus > 0) {
			scoresArray.add(winningScore);
		} else {
			scoresArray.add(losingScore);
		}
	}
	
	public static void teamOpponents(String opponent) {
		opponentsArray.add(opponent);
	}
	
	// send scoresArray and opponentsArray to DB class
}
