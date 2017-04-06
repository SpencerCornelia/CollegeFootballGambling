package webscraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import updateDB.updateoffPassYdsAtt;
import updateDB.updateoffPointsPerGame;

public class offPointsPerGame {

	public static void main(String[] args) throws IOException {
		String scoringOffense = "http://www.cfbstats.com/2016/leader/national/team/offense/split01/category09/sort01.html";
		Document doc = Jsoup.connect(scoringOffense).get();
		
		Elements tableRowElements = doc.select("table.leaders tr");
		
		Map<String, Double> offPointsPerGameMap = new HashMap<String, Double>();
		
		for (Element row : tableRowElements.subList(1, tableRowElements.size())) {
			String teamName = row.select(".team-name").text();			
			String pointsPerGame = row.select("td:nth-child(10)").text();
			
		    try
		    {
		      // the String to int conversion happens here
		      double pointsPerGameDouble = Double.parseDouble(pointsPerGame.trim());
		      offPointsPerGameMap.put(teamName, pointsPerGameDouble);
		      System.out.println("teamName = " + teamName + " and pointsPerGame = " + pointsPerGameDouble);
		    }
		    catch (NumberFormatException nfe)
		    {
		      System.out.println("NumberFormatException: " + nfe.getMessage());
		    }
		}
		updateoffPointsPerGame u = new updateoffPointsPerGame();
		u.receiveMap(offPointsPerGameMap);
	}

}
