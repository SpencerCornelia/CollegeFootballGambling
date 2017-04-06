package webscraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import updateDB.updatedefRZScorePct;
import updateDB.updatedefRushAvg;

public class defRZScorePct {

	public static void main(String[] args) throws IOException {
		String url = "http://www.cfbstats.com/2016/leader/national/team/defense/split01/category27/sort01.html";
		Document doc = Jsoup.connect(url).get();
		
		Elements tableRowElements = doc.select("table.leaders tr");
		
		Map<String, Double> defRZScorePctMap = new HashMap<String, Double>();
		
		for (Element row : tableRowElements.subList(1, tableRowElements.size())) {
			String teamName = row.select(".team-name").text();			
			String redzonePct = row.select("td:nth-child(6)").text();
			
		    try
		    {
		      // the String to int conversion happens here
		      double redzonePctDouble = Double.parseDouble(redzonePct.trim());
		      defRZScorePctMap.put(teamName, redzonePctDouble);
		      System.out.println("teamName = " + teamName + " and redzonePctDouble = " + redzonePctDouble);
		    }
		    catch (NumberFormatException nfe)
		    {
		      System.out.println("NumberFormatException: " + nfe.getMessage());
		    }
		}
		updatedefRZScorePct u = new updatedefRZScorePct();
		u.receiveMap(defRZScorePctMap);
	}

}
