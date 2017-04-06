package webscraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import updateDB.updatedef20PlusPlays;
import updateDB.updatedef30PlusPlays;

public class def30PlusPlays {

	public static void main(String[] args) throws IOException {
		String url = "http://www.cfbstats.com/2016/leader/national/team/defense/split01/category30/sort03.html";
		Document doc = Jsoup.connect(url).get();
		
		Elements tableRowElements = doc.select("table.leaders tr");
		
		Map<String, Double> def30PlusMap = new HashMap<String, Double>();
		
		for (Element row : tableRowElements.subList(2, tableRowElements.size())) {
			String teamName = row.select(".team-name").text();			
			String thirtyPlus = row.select("td:nth-child(6)").text();
			
		    try
		    {
		      // the String to int conversion happens here
		      double thirtyPlusDouble = Double.parseDouble(thirtyPlus.trim());
		      System.out.println("teamName = " + teamName + " and thirtyPlusDouble = " + thirtyPlusDouble);
		      def30PlusMap.put(teamName, thirtyPlusDouble);
		    }
		    catch (NumberFormatException nfe)
		    {
		      System.out.println("NumberFormatException: " + nfe.getMessage());
		    }
		}
		
		updatedef30PlusPlays u = new updatedef30PlusPlays();
		u.receiveMap(def30PlusMap);

	}

}
