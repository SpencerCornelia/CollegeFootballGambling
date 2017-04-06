package webscraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import updateDB.updatedef3rdDownPct;
import updateDB.updatedef40PlusPlays;

public class def3rdDownPct {

	public static void main(String[] args) throws IOException {
		String url = "http://www.cfbstats.com/2016/leader/national/team/defense/split01/category25/sort01.html";
		Document doc = Jsoup.connect(url).get();
		
		Elements tableRowElements = doc.select("table.leaders tr");
		
		Map<String, Double> def3rdDownPctMap = new HashMap<String, Double>();
		
		for (Element row : tableRowElements.subList(1, tableRowElements.size())) {
			String teamName = row.select(".team-name").text();			
			String thirdDownPct = row.select("td:nth-child(6)").text();
			
		    try
		    {
		      // the String to int conversion happens here
		      double thirdDownPctDouble = Double.parseDouble(thirdDownPct.trim());
		      def3rdDownPctMap.put(teamName, thirdDownPctDouble);
		      System.out.println("teamName = " + teamName + " and thirdDownPctDouble = " + thirdDownPctDouble);
		    }
		    catch (NumberFormatException nfe)
		    {
		      System.out.println("NumberFormatException: " + nfe.getMessage());
		    }
		}

		updatedef3rdDownPct u = new updatedef3rdDownPct();
		u.receiveMap(def3rdDownPctMap);
	}

}
