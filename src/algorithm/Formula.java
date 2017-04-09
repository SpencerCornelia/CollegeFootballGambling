package algorithm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

public class Formula {
	// array of opponents faced by team one
	public static ArrayList<String> teamOneOpponentsArrayList = new ArrayList<String>();
	// array for points scored by team one on offense
	public static ArrayList<Integer> teamOneScoresArrayList = new ArrayList<Integer>();
	// array for points given up by team one on defense
	public static ArrayList<Integer> teamOneOpponentsScoresArrayList = new ArrayList<Integer>();

	// array of opponents faced by team one
	public static ArrayList<String> teamTwoOpponentsArrayList = new ArrayList<String>();
	// array for points scored by team one on offense
	public static ArrayList<Integer> teamTwoScoresArrayList = new ArrayList<Integer>();
	// array for points given up by team one on defense
	public static ArrayList<Integer> teamTwoOpponentsScoresArrayList = new ArrayList<Integer>();


	public static void main(String[] args) {

	}
	
	// Team One
	// an array of all the teams the focused team played
	public void receiveTeamOneOpponentsArray(ArrayList<String> opponentsArray) {
		for (String opp : opponentsArray) {
			teamOneOpponentsArrayList.add(opp);
		}
	}
	
	// array for score of team --> use this to measure offense of focused team
	public void receiveTeamOneScoresArray(ArrayList<Integer> scoresArray) {
		for (Integer score : scoresArray) {
			teamOneScoresArrayList.add(score);
		}
	}
	
	// array for the score of the opponents --> use this to measure defense of focused team
	public void receiveTeamOneOpponentScoresArray(ArrayList<Integer> oppScoreArray) {
		for (Integer score : oppScoreArray) {
			teamOneOpponentsScoresArrayList.add(score);
		}System.out.println("teamOneOpponentsScoresArrayList = " + teamOneOpponentsScoresArrayList);
	}
	
	// Team Two
	// an array of all the teams the focused team played
	public void receiveTeamTwoOpponentsArray(ArrayList<String> opponentsArray) {
		for (String opp : opponentsArray) {
			teamTwoOpponentsArrayList.add(opp);
		}
	}
	
	// array for score of team --> use this to measure offense of focused team
	public void receiveTeamTwoScoresArray(ArrayList<Integer> scoresArray) {
		for (Integer score : scoresArray) {
			teamTwoScoresArrayList.add(score);
		}
	}
	
	// array for the score of the opponents --> use this to measure defense of focused team
	public void receiveTeamTwoOpponentScoresArray(ArrayList<Integer> oppScoreArray) {
		for (Integer score : oppScoreArray) {
			teamTwoOpponentsScoresArrayList.add(score);
		}
		System.out.println("teamTwoOpponentsScoresArrayList = " + teamTwoOpponentsScoresArrayList);
	}

}

