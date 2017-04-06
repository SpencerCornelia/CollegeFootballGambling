package webscraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import updateDB.updatedef40PlusPlays;

public class def40PlusPlays {

	public static void main(String[] args) throws IOException {
		String url = "http://www.cfbstats.com/2016/leader/national/team/defense/split01/category30/sort04.html";
		Document doc = Jsoup.connect(url).get();
		
		Elements tableRowElements = doc.select("table.leaders tr");
		
		Map<String, Double> def40PlusMap = new HashMap<String, Double>();
		
		for (Element row : tableRowElements.subList(2, tableRowElements.size())) {
			String teamName = row.select(".team-name").text();			
			String fourtyPlus = row.select("td:nth-child(7)").text();
			
		    try
		    {
		      // the String to int conversion happens here
		      double fourtyPlusDouble = Double.parseDouble(fourtyPlus.trim());
		      def40PlusMap.put(teamName, fourtyPlusDouble);
		      System.out.println("teamName = " + teamName + " and fourtyPlusDouble = " + fourtyPlusDouble);
		    }
		    catch (NumberFormatException nfe)
		    {
		      System.out.println("NumberFormatException: " + nfe.getMessage());
		    }
		}
		
		updatedef40PlusPlays u = new updatedef40PlusPlays();
		u.receiveMap(def40PlusMap);

	}

}
