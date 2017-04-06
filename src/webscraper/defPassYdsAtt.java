package webscraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import updateDB.updatedefPassPct;
import updateDB.updatedefPassYdsAtt;

public class defPassYdsAtt {

	public static void main(String[] args) throws IOException {
		String url = "http://www.cfbstats.com/2016/leader/national/team/defense/split01/category02/sort04.html";
		Document doc = Jsoup.connect(url).get();
		
		Elements tableRowElements = doc.select("table.leaders tr");
		
		Map<String, Double> defPassYdsAttMap = new HashMap<String, Double>();
		
		for (Element row : tableRowElements.subList(1, tableRowElements.size())) {
			String teamName = row.select(".team-name").text();			
			String passYdsAtt = row.select("td:nth-child(8)").text();
			
		    try
		    {
		      // the String to int conversion happens here
		      double passYdsAttDouble = Double.parseDouble(passYdsAtt.trim());
		      defPassYdsAttMap.put(teamName, passYdsAttDouble);
		      System.out.println("teamName = " + teamName + " and passYdsAttDouble = " + passYdsAttDouble);
		    }
		    catch (NumberFormatException nfe)
		    {
		      System.out.println("NumberFormatException: " + nfe.getMessage());
		    }
		}
		updatedefPassYdsAtt u = new updatedefPassYdsAtt();
		u.receiveMap(defPassYdsAttMap);

	}

}
