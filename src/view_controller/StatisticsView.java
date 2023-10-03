package view_controller;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.WordleAccount;
import model.WordleGame;

/**
 * Displays the current account's statistics.
 */
public class StatisticsView {
	Label statistics;
	Label playedTimes;
	Label winPercentage;
	Label currentStreak;
	Label maxStreak;
	VBox playedBox;
	VBox winBox;
	VBox curStreakBox;
	VBox maxStreakBox;
	HBox allBox;
	VBox statisticsBox;
	
	private Node addContent(WordleAccount wordleAccount) {
		// Configuring category and NumberAxis   
	    NumberAxis xaxis= new NumberAxis(0, 10, 1);  
	    CategoryAxis yaxis = new CategoryAxis(); 
	    xaxis.setLabel("Played Times");  
	    yaxis.setLabel("Guesses");  
		
		// Configuring BarChart   
	    BarChart<Number, String> bar = new BarChart(xaxis, yaxis);  
	    bar.setTitle("Guess Distribution");  

	    // Configuring Series for XY chart   
	    XYChart.Series<Number, String> series = new XYChart.Series<>();
	    series.getData().add(new XYChart.Data(wordleAccount.getWinRecord().get(1), "guessOneTime"));  
	    series.getData().add(new XYChart.Data(wordleAccount.getWinRecord().get(2), "guessTwoTime"));
	    series.getData().add(new XYChart.Data(wordleAccount.getWinRecord().get(3), "guessThreeTime"));  
	    series.getData().add(new XYChart.Data(wordleAccount.getWinRecord().get(4), "guessFourTime"));  
	    series.getData().add(new XYChart.Data(wordleAccount.getWinRecord().get(5), "guessFiveTime"));  
	    series.getData().add(new XYChart.Data(wordleAccount.getWinRecord().get(6), "guessSixTime"));  
	    
	    // Adding series to the barchart   
	    bar.getData().add(series);
	    
	    return bar;
	}

	@SuppressWarnings("exports")
	/**
	 * Gives statistics labels for the current account
	 * 
	 * @param wordleAccount - the account to show statistics for
	 * @param backButton - the button to go back to the wordle game.
	 */
	public void getStatisticsView(WordleAccount wordleAccount, Button backButton) {	
		
		// Statistics
		statistics = new Label("Statistics");
		playedTimes = new Label("Played");
		winPercentage = new Label("Win %");
		currentStreak = new Label("Current Streak");
		maxStreak = new Label("Max Streak");
		playedBox = new VBox();
		winBox = new VBox();
		curStreakBox = new VBox();
		maxStreakBox = new VBox();
		allBox = new HBox();
		statisticsBox = new VBox();
		Label played = new Label(Integer.toString(wordleAccount.getGamesPlayed()));
		Label win = new Label(String.valueOf(wordleAccount.getWinPct()));
		Label curS = new Label(Integer.toString(wordleAccount.getWinStreak()));
		Label maxS = new Label(Integer.toString(wordleAccount.getMaxStreak()));
		
		playedBox.getChildren().addAll(played, playedTimes);
		winBox.getChildren().addAll(win, winPercentage);
		curStreakBox.getChildren().addAll(curS, currentStreak);
		maxStreakBox.getChildren().addAll(maxS, maxStreak);
		allBox.getChildren().addAll(playedBox, winBox, curStreakBox, maxStreakBox);
		statisticsBox.getChildren().addAll(statistics, allBox);
		playedBox.setPadding(new Insets(20, 20, 20, 20));
		winBox.setPadding(new Insets(20, 20, 20, 20));
		curStreakBox.setPadding(new Insets(20, 20, 20, 20));
		maxStreakBox.setPadding(new Insets(20, 20, 20, 20));
		statisticsBox.setPadding(new Insets(50, 50, 50, 50));
		statisticsBox.setSpacing(50);

	}
	
	/**
	 * Gives the statistics view
	 * 
	 * @param wordleAccount - the account to show statistics for
	 * @param backButton - the button to take the user back to the wordle game
	 * @param game - the game being played
	 * @return
	 */
	public Scene getView(WordleAccount wordleAccount, Button backButton, WordleGame game) {
		BorderPane statisticsPane = new BorderPane();
		
		getStatisticsView(wordleAccount, backButton);

		Node content = this.addContent(wordleAccount);
	    
	    // configuring group and scene   
	    Group root = new Group();  
	    root.getChildren().add(content);
	    statisticsBox.getChildren().add(root);
	    statisticsPane.setCenter(statisticsBox);
	    statisticsPane.setRight(backButton);
	    
	    
	    Scene scene = new Scene(statisticsPane, 700, 800);  
	    
	    
	    return scene;
	}
}
