package webscraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import updateDB.updateoffFirstDwns;

public class offFirstDwns {

	public static void main(String[] args) throws IOException {
		String url = "http://www.cfbstats.com/2016/leader/national/team/offense/split01/category13/sort01.html";
		Document doc = Jsoup.connect(url).get();
		
		Elements tableRowElements = doc.select("table.leaders tr");
		
		Map<String, Double> offFirstDwnsMap = new HashMap<String, Double>();
		
		for (Element row : tableRowElements.subList(1, tableRowElements.size())) {
			String teamName = row.select(".team-name").text();			
			String firstDowns = row.select("td:nth-child(8)").text();
			
		    try
		    {
		      // the String to int conversion happens here
		      double firstDownsDouble = Double.parseDouble(firstDowns.trim());
		      offFirstDwnsMap.put(teamName, firstDownsDouble);
		      System.out.println("teamName = " + teamName + " and firstDownsDouble = " + firstDownsDouble);
		    }
		    catch (NumberFormatException nfe)
		    {
		      System.out.println("NumberFormatException: " + nfe.getMessage());
		    }
		}
		updateoffFirstDwns u = new updateoffFirstDwns();
		u.receiveMap(offFirstDwnsMap);
	}

}
