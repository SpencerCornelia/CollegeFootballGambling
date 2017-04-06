package webscraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import updateDB.updateoffPointsPerGame;
import updateDB.updateoffRushAtt;

public class offRushAtt {

	public static void main(String[] args) throws IOException {
		String url = "http://www.cfbstats.com/2016/leader/national/team/offense/split01/category01/sort05.html";
		Document doc = Jsoup.connect(url).get();
		
		Elements tableRowElements = doc.select("table.leaders tr");
		
		Map<String, Double> offRushAttMap = new HashMap<String, Double>();
		
		for (Element row : tableRowElements.subList(1, tableRowElements.size())) {
			String teamName = row.select(".team-name").text();			
			String rushAttPerGame = row.select("td:nth-child(8)").text();
			
		    try
		    {
		      // the String to int conversion happens here
		      double rushAttDouble = Double.parseDouble(rushAttPerGame.trim());
		      offRushAttMap.put(teamName, rushAttDouble);
		      System.out.println("teamName = " + teamName + " and rushAttPerGame = " + rushAttDouble);
		    }
		    catch (NumberFormatException nfe)
		    {
		      System.out.println("NumberFormatException: " + nfe.getMessage());
		    }
		}
		updateoffRushAtt u = new updateoffRushAtt();
		u.receiveMap(offRushAttMap);
	}

}
