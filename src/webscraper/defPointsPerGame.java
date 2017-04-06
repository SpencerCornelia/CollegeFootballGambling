package webscraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import updateDB.updatedefPointsPerGame;

public class defPointsPerGame {

	public static void main(String[] args) throws IOException {
		String url = "http://www.cfbstats.com/2016/leader/national/team/defense/split01/category09/sort01.html";
		Document doc = Jsoup.connect(url).get();
		
		Elements tableRowElements = doc.select("table.leaders tr");
		
		Map<String, Double> defPointsPerGameMap = new HashMap<String, Double>();
		
		for (Element row : tableRowElements.subList(1, tableRowElements.size())) {
			String teamName = row.select(".team-name").text();			
			String ptsPerGame = row.select("td:nth-child(10)").text();
			
		    try
		    {
		      // the String to int conversion happens here
		      double ptsPerGameDouble = Double.parseDouble(ptsPerGame.trim());
		      defPointsPerGameMap.put(teamName, ptsPerGameDouble);
		      System.out.println("teamName = " + teamName + " and ptsPerGameDouble = " + ptsPerGameDouble);
		    }
		    catch (NumberFormatException nfe)
		    {
		      System.out.println("NumberFormatException: " + nfe.getMessage());
		    }
		}
		
		updatedefPointsPerGame u = new updatedefPointsPerGame();
		u.receiveMap(defPointsPerGameMap);

	}

}
