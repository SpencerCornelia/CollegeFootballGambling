package webscraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import updateDB.updateNumOfGames;
import updateDB.updatedef20PlusPlays;

public class def20PlusPlays {

	public static void main(String[] args) throws IOException {
		String url = "http://www.cfbstats.com/2015/leader/national/team/defense/split01/category30/sort02.html";
		Document doc = Jsoup.connect(url).get();
		
		Elements tableRowElements = doc.select("table.leaders tr");
		
		Map<String, Double> def20PlusMap = new HashMap<String, Double>();
		
		for (Element row : tableRowElements.subList(2, tableRowElements.size())) {
			String teamName = row.select(".team-name").text();			
			String twentyPlus = row.select("td:nth-child(5)").text();
			
		    try
		    {
		      // the String to int conversion happens here
		      double twentyPlusDouble = Double.parseDouble(twentyPlus.trim());
		      System.out.println("teamName = " + teamName + " and twentyPlusDouble = " + twentyPlusDouble);
		      def20PlusMap.put(teamName, twentyPlusDouble);
		    }
		    catch (NumberFormatException nfe)
		    {
		      System.out.println("NumberFormatException: " + nfe.getMessage());
		    }
		}
		
		updatedef20PlusPlays u = new updatedef20PlusPlays();
		u.receiveMap(def20PlusMap);

	}

}
