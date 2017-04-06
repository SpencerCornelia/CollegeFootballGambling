package webscraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import updateDB.updateoffFirstDwns;
import updateDB.updateoffPassYdsAtt;

public class offPassYdsAtt {

	public static void main(String[] args) throws IOException {
		String url = "http://www.cfbstats.com/2016/leader/national/team/offense/split01/category02/sort04.html";
		Document doc = Jsoup.connect(url).get();
		
		Elements tableRowElements = doc.select("table.leaders tr");
		
		Map<String, Double> offPassYdsAttMap = new HashMap<String, Double>();
		
		for (Element row : tableRowElements.subList(1, tableRowElements.size())) {
			String teamName = row.select(".team-name").text();			
			String passYdsAtt = row.select("td:nth-child(8)").text();
			
		    try
		    {
		      // the String to int conversion happens here
		      double passYdsAttDouble = Double.parseDouble(passYdsAtt.trim());
		      offPassYdsAttMap.put(teamName, passYdsAttDouble);
		      System.out.println("teamName = " + teamName + " and passYdsAtt = " + passYdsAttDouble);
		    }
		    catch (NumberFormatException nfe)
		    {
		      System.out.println("NumberFormatException: " + nfe.getMessage());
		    }
		}
		updateoffPassYdsAtt u = new updateoffPassYdsAtt();
		u.receiveMap(offPassYdsAttMap);
	}

}
