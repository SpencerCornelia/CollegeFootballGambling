package compareScores;

import java.sql.SQLException;
import java.util.ArrayList;

import compareAgainstSpread.Handicap;

public class CalculateScore {
	static String year = "2008";
	
	//DB info
	static String DBName = "Scores" + year;
	static String DBurl = "jdbc:mysql://localhost:3306/" + DBName + "?useSSL=false";
	static String DBusername = "root";
	static String DBpassword = "Wutangclan25";
	
	static String team;
	
	// three ArrayLists dealing with offensive numbers
	static ArrayList<Double> offensivePoints = new ArrayList<Double>();   
	static ArrayList<Double> opponentAvgPointsGivenUp = new ArrayList<Double>();
	static ArrayList<Double> offensivePredictedScore = new ArrayList<Double>();
	
	// three ArrayLists dealing with defensive numbers
	static ArrayList<Double> defensivePoints = new ArrayList<Double>();
	static ArrayList<Double> opponentAvgPointsScored = new ArrayList<Double>();
	static ArrayList<Double> defensivePredictedScore = new ArrayList<Double>();
	
	public void main(String[] args) {
		
	}
	
	public void receiveOffensiveScores(String teamName, double points, double avgPointsGivenUp, double predictedScore) {
		team = teamName;
		offensivePoints.add(points);
		opponentAvgPointsGivenUp.add(avgPointsGivenUp);
		offensivePredictedScore.add(predictedScore);
	}
	
	public void receiveDefensiveScores(double points, double avgPointsScored, double predictedScore) {
		defensivePoints.add(points);
		opponentAvgPointsScored.add(avgPointsScored);
		defensivePredictedScore.add(predictedScore);
	}
	
	// this method gets called from GetScoresOffense class
	public void calculateFinalScore() throws SQLException {
		//call the main class once so that the ArrayList can have all necessary data
		Handicap.main(team);
		for (int i = 0; i < offensivePoints.size(); i++) {
			Double pointsOnOffense = offensivePoints.get(i);
			Double pointsOnDefense = defensivePoints.get(i);
			Double predictOffenseScore = offensivePredictedScore.get(i);
			Double predictDefenseScore = defensivePredictedScore.get(i);
			
			Integer counter = i + 1;
			Handicap.calculateWin(counter, pointsOnOffense, pointsOnDefense, predictOffenseScore, predictDefenseScore);
		}
		Handicap.clearSpreads();
	}

	
	public void clearArrayLists() {
		offensivePoints.clear();
		opponentAvgPointsGivenUp.clear();
		offensivePredictedScore.clear();
		defensivePoints.clear();
		opponentAvgPointsScored.clear();
		defensivePredictedScore.clear();
	}
}
