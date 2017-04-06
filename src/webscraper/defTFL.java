package webscraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import updateDB.updatedefRZTDPct;
import updateDB.updatedefTFL;

public class defTFL {

	public static void main(String[] args) throws IOException {
		String url = "http://www.cfbstats.com/2016/leader/national/team/defense/split01/category10/sort02.html";
		Document doc = Jsoup.connect(url).get();
		
		Elements tableRowElements = doc.select("table.leaders tr");
		
		Map<String, Double> defTFLMap = new HashMap<String, Double>();
		
		for (Element row : tableRowElements.subList(1, tableRowElements.size())) {
			String teamName = row.select(".team-name").text();			
			String ydsPerPlay = row.select("td:nth-child(8)").text();
			
		    try
		    {
		      // the String to int conversion happens here
		      double defTFLDouble = Double.parseDouble(ydsPerPlay.trim());
		      defTFLMap.put(teamName, defTFLDouble);
		      System.out.println("teamName = " + teamName + " and ydsPerPlayDouble = " + defTFLDouble);
		    }
		    catch (NumberFormatException nfe)
		    {
		      System.out.println("NumberFormatException: " + nfe.getMessage());
		    }
		}
		updatedefTFL u = new updatedefTFL();
		u.receiveMap(defTFLMap);
	}

}
