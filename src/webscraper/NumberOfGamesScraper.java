package webscraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import driver.Driver;
import updateDB.updateNumOfGames;

public class NumberOfGamesScraper {
	
	public static void main(String[] args) throws IOException {
		String numberGames = "http://www.cfbstats.com/2016/leader/national/team/offense/split01/category09/sort01.html";
		Document doc = Jsoup.connect(numberGames).get();
		
		Elements tableRowElements = doc.select("table.leaders tr");
		
		Map<String, Integer> NumberOfGamesMap = new HashMap<String, Integer>();
		
		for (Element row : tableRowElements.subList(1, tableRowElements.size())) {
			String teamName = row.select(".team-name").text();			
			String numOfGames = row.select("td:nth-child(3)").text();
			// System.out.println("teamName = " + teamName + " and number of games = " + numOfGames);
		    try
		    {
		      // the String to int conversion happens here
		      int numOfGamesInt = Integer.parseInt(numOfGames.trim());
		      // send numOfGamesInt to updateDB class
		      NumberOfGamesMap.put(teamName, numOfGamesInt);
		      
		    }
		    catch (NumberFormatException nfe)
		    {
		      System.out.println("NumberFormatException: " + nfe.getMessage());
		    }
		}
		
		/*  **** test to make sure NumberOfGamesMap is good before sending over ****
		System.out.println("NumberOfGamesMap = " + NumberOfGamesMap);
		*/
		
		
		/* *** when finished testing, comment this out to send to updateDB file ***
		 * */
		updateNumOfGames u = new updateNumOfGames();
		u.receiveMap(NumberOfGamesMap);
	}

}
