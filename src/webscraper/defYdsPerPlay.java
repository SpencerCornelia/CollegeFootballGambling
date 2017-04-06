package webscraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import updateDB.updatedefTFL;
import updateDB.updatedefYdsPerPlay;

public class defYdsPerPlay {

	public static void main(String[] args) throws IOException {
		String url = "http://www.cfbstats.com/2016/leader/national/team/defense/split01/category10/sort02.html";
		Document doc = Jsoup.connect(url).get();
		
		Elements tableRowElements = doc.select("table.leaders tr");
		
		Map<String, Double> defYdsPerPlayMap = new HashMap<String, Double>();
		
		for (Element row : tableRowElements.subList(1, tableRowElements.size())) {
			String teamName = row.select(".team-name").text();			
			String yardsPerPlay = row.select("td:nth-child(8)").text();
			
		    try
		    {
		      // the String to int conversion happens here
		      double yardsPerPlayDouble = Double.parseDouble(yardsPerPlay.trim());
		      defYdsPerPlayMap.put(teamName, yardsPerPlayDouble);
		      System.out.println("teamName = " + teamName + " and yardsPerPlayDouble = " + yardsPerPlayDouble);
		    }
		    catch (NumberFormatException nfe)
		    {
		      System.out.println("NumberFormatException: " + nfe.getMessage());
		    }
		}
		updatedefYdsPerPlay u = new updatedefYdsPerPlay();
		u.receiveMap(defYdsPerPlayMap);
	}

}
