package webscraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import updateDB.updateoffRZScorePct;
import updateDB.updateoffRZTDPct;

public class offRZTDPct {

	public static void main(String[] args) throws IOException {
		String url = "http://www.cfbstats.com/2016/leader/national/team/offense/split01/category27/sort05.html";
		Document doc = Jsoup.connect(url).get();
		
		Elements tableRowElements = doc.select("table.leaders tr");
		
		Map<String, Double> offRZTDPctMap = new HashMap<String, Double>();
		
		for (Element row : tableRowElements.subList(1, tableRowElements.size())) {
			String teamName = row.select(".team-name").text();			
			String redzoneTDPct = row.select("td:nth-child(8)").text();
			
		    try
		    {
		      // the String to int conversion happens here
		      double redzoneTDPctDouble = Double.parseDouble(redzoneTDPct.trim());
		      offRZTDPctMap.put(teamName, redzoneTDPctDouble);
		      System.out.println("teamName = " + teamName + " and redzoneTDPctDouble = " + redzoneTDPctDouble);
		    }
		    catch (NumberFormatException nfe)
		    {
		      System.out.println("NumberFormatException: " + nfe.getMessage());
		    }
		}
		updateoffRZTDPct u = new updateoffRZTDPct();
		u.receiveMap(offRZTDPctMap);
	}

}
