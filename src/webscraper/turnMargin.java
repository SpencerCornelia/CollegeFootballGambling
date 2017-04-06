package webscraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import updateDB.updateoffYardsPerPlay;
import updateDB.updateturnMargin;

public class turnMargin {

	public static void main(String[] args) throws IOException {
		String url = "http://www.cfbstats.com/2016/leader/national/team/offense/split01/category12/sort01.html";
		Document doc = Jsoup.connect(url).get();
		
		Elements tableRowElements = doc.select("table.leaders tr");
		
		Map<String, Double> turnMarginMap = new HashMap<String, Double>();
		
		for (Element row : tableRowElements.subList(1, tableRowElements.size())) {
			String teamName = row.select(".team-name").text();			
			String turnoverMargin = row.select("td:nth-child(11)").text();
			
		    try
		    {
		      // the String to int conversion happens here
		      double turnoverMarginDouble = Double.parseDouble(turnoverMargin.trim());
		      turnMarginMap.put(teamName, turnoverMarginDouble);
		      System.out.println("teamName = " + teamName + " and turnoverMarginDouble = " + turnoverMarginDouble);
		    }
		    catch (NumberFormatException nfe)
		    {
		      System.out.println("NumberFormatException: " + nfe.getMessage());
		    }
		}
		updateturnMargin u = new updateturnMargin();
		u.receiveMap(turnMarginMap);
	}

}
