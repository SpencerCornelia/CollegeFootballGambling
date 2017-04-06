package webscraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import updateDB.updateoff3rdDownPct;

public class off3rdDownPct {

	public static void main(String[] args) throws IOException {
		String url = "http://www.cfbstats.com/2016/leader/national/team/offense/split01/category25/sort01.html";
		Document doc = Jsoup.connect(url).get();
		
		Elements tableRowElements = doc.select("table.leaders tr");
		
		Map<String, Double> off3rdDownPctMap = new HashMap<String, Double>();
		
		for (Element row : tableRowElements.subList(1, tableRowElements.size())) {
			String teamName = row.select(".team-name").text();			
			String off3rdDown = row.select("td:nth-child(6)").text();
			
		    try
		    {
		      // the String to int conversion happens here
		      double off3rdDownDouble = Double.parseDouble(off3rdDown.trim());
		      off3rdDownPctMap.put(teamName, off3rdDownDouble);
		      System.out.println("teamName = " + teamName + " and off3rdDownDouble = " + off3rdDownDouble);
		    }
		    catch (NumberFormatException nfe)
		    {
		      System.out.println("NumberFormatException: " + nfe.getMessage());
		    }
		}
		updateoff3rdDownPct u = new updateoff3rdDownPct();
		u.receiveMap(off3rdDownPctMap);
	}

}
