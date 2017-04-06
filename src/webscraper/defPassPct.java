package webscraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import updateDB.updatedefPassAttPerGame;
import updateDB.updatedefPassPct;

public class defPassPct {

	public static void main(String[] args) throws IOException {
		String url = "http://www.cfbstats.com/2016/leader/national/team/defense/split01/category02/sort03.html";
		Document doc = Jsoup.connect(url).get();
		
		Elements tableRowElements = doc.select("table.leaders tr");
		
		Map<String, Double> defPassPctMap = new HashMap<String, Double>();
		
		for (Element row : tableRowElements.subList(1, tableRowElements.size())) {
			String teamName = row.select(".team-name").text();			
			String passPct = row.select("td:nth-child(6)").text();
			
		    try
		    {
		      // the String to int conversion happens here
		      double passPctDouble = Double.parseDouble(passPct.trim());
		      defPassPctMap.put(teamName, passPctDouble);
		      System.out.println("teamName = " + teamName + " and passPctDouble = " + passPctDouble);
		    }
		    catch (NumberFormatException nfe)
		    {
		      System.out.println("NumberFormatException: " + nfe.getMessage());
		    }
		}
		updatedefPassPct u = new updatedefPassPct();
		u.receiveMap(defPassPctMap);
	}

}
