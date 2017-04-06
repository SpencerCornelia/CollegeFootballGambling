package webscraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import updateDB.updateoff40PlusYds;

public class off40PlusYds {

	public static void main(String[] args) throws IOException {
		String url = "http://www.cfbstats.com/2016/leader/national/team/offense/split01/category30/sort04.html";
		Document doc = Jsoup.connect(url).get();
		
		Elements tableRowElements = doc.select("table.leaders tr");
		
		Map<String, Double> off40PlusYdsMap = new HashMap<String, Double>();
		
		for (Element row : tableRowElements.subList(2, tableRowElements.size())) {
			String teamName = row.select(".team-name").text();			
			String fourtyPlus = row.select("td:nth-child(7)").text();
			
		    try
		    {
		      // the String to int conversion happens here
		      double fourtyPlusDouble = Double.parseDouble(fourtyPlus.trim());
		      off40PlusYdsMap.put(teamName, fourtyPlusDouble);
		      System.out.println("teamName = " + teamName + " and fourtyPlusDouble = " + fourtyPlusDouble);
		    }
		    catch (NumberFormatException nfe)
		    {
		      System.out.println("NumberFormatException: " + nfe.getMessage());
		    }
		}
		updateoff40PlusYds u = new updateoff40PlusYds();
		u.receiveMap(off40PlusYdsMap);
	}

}
