package webscraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import updateDB.updateoffRushAtt;
import updateDB.updateoffRushAvg;

public class offRushAvg {

	public static void main(String[] args) throws IOException {
		String url = "http://www.cfbstats.com/2016/leader/national/team/offense/split01/category01/sort01.html";
		Document doc = Jsoup.connect(url).get();
		
		Elements tableRowElements = doc.select("table.leaders tr");
		
		Map<String, Double> offRushAvgMap = new HashMap<String, Double>();
		
		for (Element row : tableRowElements.subList(1, tableRowElements.size())) {
			String teamName = row.select(".team-name").text();			
			String rushAvgPerGame = row.select("td:nth-child(9)").text();
			
		    try
		    {
		      // the String to int conversion happens here
		      double rushAvgDouble = Double.parseDouble(rushAvgPerGame.trim());
		      offRushAvgMap.put(teamName, rushAvgDouble);
		      System.out.println("teamName = " + teamName + " and rushAvgPerGame = " + rushAvgDouble);
		    }
		    catch (NumberFormatException nfe)
		    {
		      System.out.println("NumberFormatException: " + nfe.getMessage());
		    }
		}
		updateoffRushAvg u = new updateoffRushAvg();
		u.receiveMap(offRushAvgMap);
	}

}
