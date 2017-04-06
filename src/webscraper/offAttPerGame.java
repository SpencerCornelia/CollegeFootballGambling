package webscraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import updateDB.updateoffAttPerGame;

public class offAttPerGame {

	public static void main(String[] args) throws IOException {
		String url = "http://www.cfbstats.com/2016/leader/national/team/offense/split01/category02/sort05.html";
		Document doc = Jsoup.connect(url).get();
		
		Elements tableRowElements = doc.select("table.leaders tr");
		
		Map<String, Double> offAttPerGameMap = new HashMap<String, Double>();
		
		for (Element row : tableRowElements.subList(1, tableRowElements.size())) {
			String teamName = row.select(".team-name").text();			
			String offAttPerGame = row.select("td:nth-child(12)").text();
			
		    try
		    {
		      // the String to int conversion happens here
		      double offAttPerGameDouble = Double.parseDouble(offAttPerGame.trim());
		      offAttPerGameMap.put(teamName, offAttPerGameDouble);
		      System.out.println("teamName = " + teamName + " and passYdsAtt = " + offAttPerGameDouble);
		    }
		    catch (NumberFormatException nfe)
		    {
		      System.out.println("NumberFormatException: " + nfe.getMessage());
		    }
		}
		updateoffAttPerGame u = new updateoffAttPerGame();
		u.receiveMap(offAttPerGameMap);
	}

}
