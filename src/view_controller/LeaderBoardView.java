package view_controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import model.WordleAccount;
import model.WordleGame;

/**
 * Displays the leaderboard of all saved accounts.
 */
public class LeaderBoardView extends StatisticsView {
	
	
	
	ObservableList<WordleAccount> observableList;
	
	private Node addContent(WordleAccount wordleAccount, WordleGame game) {
		ArrayList<WordleAccount> accountList = game.getAccountList();
		Collections.sort(accountList, new Comparator<WordleAccount>() {
			@Override
			public int compare(WordleAccount user1, WordleAccount user2) {
				return Integer.compare(user2.getScore(), user1.getScore());
			}
		});
		
		TableView<WordleAccount> tableView = new TableView<>();
		TableColumn<WordleAccount, Integer> rankCol = new TableColumn<>("Rank");
		rankCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(accountList.indexOf(cellData.getValue()) + 1).asObject());
		TableColumn<WordleAccount, String> usernameCol = new TableColumn<>("Username");
		usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
		TableColumn<WordleAccount, Integer> scoreCol = new TableColumn<>("Score");
		scoreCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getScore()).asObject());
		TableColumn<WordleAccount, Integer> gamesPlayedCol = new TableColumn<>("Games Played");
		gamesPlayedCol.setCellValueFactory(new PropertyValueFactory<>("gamesPlayed"));
		
		scoreCol.setSortType(TableColumn.SortType.DESCENDING);
		
		tableView.getColumns().addAll(rankCol, usernameCol, scoreCol, gamesPlayedCol);
		
		ObservableList<WordleAccount> list = FXCollections.observableArrayList(accountList);
        tableView.setItems(list);
		
		return tableView;
	}
	
	
	/**
	 * Gives the leaderboard view
	 * 
	 * @param wordleAccount - the current account logged in
	 * @param backButton - the button to go back to the Wordle game
	 * @param game - the game being played
	 */
	public Scene getView(WordleAccount wordleAccount, Button backButton, WordleGame game) {
		BorderPane statisticsPane = new BorderPane();
		
		getStatisticsView(wordleAccount, backButton);

		Node content = this.addContent(wordleAccount, game);
	    
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
