package webscraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import updateDB.updateoff20PlusYds;

public class off20PlusYds {

	public static void main(String[] args) throws IOException {
		String url = "http://www.cfbstats.com/2016/leader/national/team/offense/split01/category30/sort02.html";
		Document doc = Jsoup.connect(url).get();
		
		Elements tableRowElements = doc.select("table.leaders tr");
		
		Map<String, Double> off20PlusYdsMap = new HashMap<String, Double>();
		
		for (Element row : tableRowElements.subList(2, tableRowElements.size())) {
			String teamName = row.select(".team-name").text();			
			String twentyPlus = row.select("td:nth-child(5)").text();
			
		    try
		    {
		      // the String to int conversion happens here
		      double twentyPlusDouble = Double.parseDouble(twentyPlus.trim());
		      off20PlusYdsMap.put(teamName, twentyPlusDouble);
		      System.out.println("teamName = " + teamName + " and twentyPlus = " + twentyPlusDouble);
		    }
		    catch (NumberFormatException nfe)
		    {
		      System.out.println("NumberFormatException: " + nfe.getMessage());
		    }
		}
		updateoff20PlusYds u = new updateoff20PlusYds();
		u.receiveMap(off20PlusYdsMap);
	}

}
