package compareScores;

import java.util.ArrayList;

public class CalculateScore {
	// DB info
	static String DBName = "Scores2008";
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
	
	public void calculateFinalScore() {
		for (int i = 0; i < offensivePoints.size(); i++) {
			System.out.println(team + " scored " + offensivePoints.get(i) + " and gave up " + defensivePoints.get(i));
			System.out.println("Predicted score = " + offensivePredictedScore.get(i) + " - " + defensivePredictedScore.get(i));
		}
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
