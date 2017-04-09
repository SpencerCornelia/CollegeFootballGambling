package algorithm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

public class Formula {
	
	public static ArrayList<String> opponentsArrayList = new ArrayList<String>();
	public static ArrayList<Integer> scoresArrayList = new ArrayList<Integer>();

	public static void main(String[] args) {

	}
	
	public void receiveOpponentsArray(ArrayList<String> opponentsArray) {
		for (String opp : opponentsArray) {
			opponentsArrayList.add(opp);
		}
	}
	
	public void receiveScoresArray(ArrayList<Integer> scoresArray) {
		for (Integer score : scoresArray) {
			scoresArrayList.add(score);
		}
	}

}

