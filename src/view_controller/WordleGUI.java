// Shaun Wyllie
package view_controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;

// import java.awt.event.ActionListener;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.WordleAccount;
import model.WordleGame;

/**
 * Allows the user to play a game of Wordle; coordinates game activities and input.
 */
public class WordleGUI extends Application {
	Label[] boxes = new Label[30];
	Button q;
	Button w;
	Button e;
	Button r;
	Button t;
	Button y;
	Button u;
	Button i;
	Button o;
	Button p;
	Button a;
	Button s;
	Button d;
	Button f;
	Button g;
	Button h;
	Button j;
	Button k;
	Button l;
	Button z;
	Button x;
	Button c;
	Button v;
	Button b;
	Button n;
	Button m;
	Button enterBtn;
	Button backBtn;
	MenuBar menuBar;
	Menu gameOptionsMenu;
	Menu accountOptionsMenu;
	Menu appearanceMenu;
	MenuItem lightOption;
	MenuItem darkOption;
	MenuItem newGameOption;
	MenuItem helpOption;
	MenuItem logoutOption;
	MenuItem loginOption;
	MenuItem createAccountOption;
	MenuItem statisticsOption;
	MenuItem leaderBoardOption;
	boolean darkMode;
	int currentBox = 0;
	int letterCount = 0;
	int rowCount = 0;
	int maxBoxes = 30;
	int wordIndex = 0;
	String userWord;
	ArrayList<String> wordsGuessed;
	private WordleGame game;
	String text = "";
	Stage stage;
	Scene scene;
	private ArrayList<String> wordList = new ArrayList<String>();

	public static void main(String[] args) {
		launch(args);
	}

	public static BorderPane everything = new BorderPane();
	private BorderPane labelPane = new BorderPane();
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		Scanner fileScanner = new Scanner(new File("WordList.txt"));
		while (fileScanner.hasNextLine()) {
			wordList.add(fileScanner.nextLine());
		}
		game = new WordleGame();
		createMenuBar();
		showWordle();
		registerHandlers();
		darkMode = false;
		wordsGuessed = new ArrayList<>();
		
		scene = new Scene(everything, 700, 800);
		scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent key) -> {
			enterBtn.requestFocus();
			String keyString = key.getText();
			// System.out.println(keyString);
			if (keyString.length() == 1) {
				Character keyChar = keyString.charAt(0);
				if (Character.isAlphabetic(keyChar)) {
					pressKey(keyString.toUpperCase());
				}
			} else if (key.getCode() == KeyCode.BACK_SPACE) {
				pressBack();
			} else if (key.getCode() == KeyCode.ENTER) {
				System.out.println("Enter");
				pressEnter();
			}
		});
		
		stage.setScene(scene);
		stage.show();
		LoginCreateAccountGUI login = new LoginCreateAccountGUI(game, this);
	}
	
	private void startRound() {
		
		// After Enter is hit we get in this function and can run the rest of what we want to do.
		// WordleGame game has already been passed the guess by the time this function starts up.
		// So we would use game here. The print was to test the function is used after every click of enter.
		// If you want to see where we pass to WordleGame game its on line 756
		guess(userWord);
		checkGameOver();
	}
	
	private void guess(String userWord) {
		String results[] = game.guessWord(userWord);
		wordsGuessed.add(userWord);
		ScaleTransition[] transitions = new ScaleTransition[5];
		
		for (int i = 4; i >= 0; i--) {
			int boxIndex = currentBox - i - 1;
			
			if (results[4 - i].equals("correct")) {
				Label backOfBox = new Label(); 
				boxes[boxIndex].setBackground(new Background(
						new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
				backOfBox = boxes[boxIndex];
				backOfBox.setPrefSize(50, 50);
				boxes[boxIndex] = backOfBox;
			} else if (results[4 - i].equals("wrong space")) {
				Label backOfBox = new Label(); 
				boxes[boxIndex].setBackground(new Background(
						new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
				backOfBox = boxes[boxIndex];
				backOfBox.setPrefSize(50, 50);
				boxes[boxIndex] = backOfBox;
			} else {
				Label backOfBox = new Label(); 
				boxes[boxIndex].setBackground(new Background(
						new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
				backOfBox = boxes[boxIndex];
				backOfBox.setPrefSize(50, 50);
				boxes[boxIndex] = backOfBox;
			}
			
			ScaleTransition st2 = new ScaleTransition(Duration.millis(350), boxes[boxIndex]);
			st2.setFromY(0);
			st2.setToY(1);
			transitions[i] = st2;
		}
		
		SequentialTransition st = new SequentialTransition();
		st.getChildren().addAll(transitions[4], transitions[3], transitions[2], transitions[1], transitions[0]);
		st.setCycleCount(1);
		st.play();
		
	}
	
	private void checkGameOver() {
		if (game.isOver()) {
			endGame();
		}
	}
	
	private void endGame() {
		Label endGameLabel = new Label();
		endGameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 26));
		endGameLabel.setStyle("-fx-text-fill: black;");
		endGameLabel.setPadding(new Insets(30));
		if (game.won()) {
			if (game.getCurUser() != null) {
				game.getCurUser().addWin(game.getNumGuesses());
			}
			game.saveAccounts();
			endGameLabel.setText("                   The word was " + game.getCorrectWord().toUpperCase() + "\nCongratulations, you guessed it in " + game.getNumGuesses() + " guess(es).");
			playWinAnimation();
			playWinSound();
		} else {
			if (game.getCurUser() != null) {
				game.getCurUser().addLoss();
			}
			game.saveAccounts();
			endGameLabel.setText("The word was " + game.getCorrectWord().toUpperCase() +"\n           You lost.");
		}
		everything.setBottom(endGameLabel);
		BorderPane.setAlignment(endGameLabel, Pos.CENTER);
	}
	
	private void playWinAnimation() {
		WinAnimation winAnimationLeft = new WinAnimation(darkMode);
		WinAnimation winAnimationRight = new WinAnimation(darkMode);
		labelPane.setLeft(winAnimationLeft);
		labelPane.setRight(winAnimationRight);
		winAnimationLeft.animate();
		winAnimationRight.animate();
	}
	
	private void playWinSound() {
		File songFile = new File("WinSound.mp3");
		URI uri = songFile.toURI();

		Media media = new Media(uri.toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}
	
	private void pressKey(String key) {
		if (letterCount < 5) {
			boxes[currentBox].setText(key);
			boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
			boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
			if (currentBox < 30) {

				currentBox++;
				letterCount++;

			}
		}

		if (letterCount < 0) {
			letterCount = 0; // set to 0 if less than 0
		} else if (letterCount > 5) {
			letterCount = 5; // set to 5 if greater than 5
		}
	}
	
	private void pressBack() {
		if (currentBox > rowCount * 5) {
			if (currentBox == 30) {
				boxes[currentBox - 1].setText("");
				letterCount--;
			}
			currentBox--;
			if (currentBox != 30) {
				boxes[currentBox].setText("");
				letterCount--;
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}

	}
	
	private void checkLetters() {
		if (wordsGuessed.size() > 0) {	
			for (String word : wordsGuessed) {
			    for (char ch = 'A'; ch <= 'Z'; ch++) {
			    	if (word.indexOf(ch) >= 0) {
			    		Button button;
			            switch(ch) {
			                case 'A':
			                    button = a;
			                    break;
			                case 'B':
			                    button = b;
			                    break;
			                case 'C':
			                    button = c;
			                    break;
			                case 'D':
			                    button = d;
			                    break;
			                case 'E':
			                    button = e;
			                    break;
			                case 'F':
			                    button = f;
			                    break;
			                case 'G':
			                    button = g;
			                    break;
			                case 'H':
			                    button = h;
			                    break;
			                case 'I':
			                    button = i;
			                    break;
			                case 'J':
			                    button = j;
			                    break;
			                case 'K':
			                    button = k;
			                    break;
			                case 'L':
			                    button = l;
			                    break;
			                case 'M':
			                    button = m;
			                    break;
			                case 'N':
			                    button = n;
			                    break;
			                case 'O':
			                    button = o;
			                    break;
			                case 'P':
			                    button = p;
			                    break;
			                case 'Q':
			                    button = q;
			                    break;
			                case 'R':
			                    button = r;
			                    break;
			                case 'S':
			                    button = s;
			                    break;
			                case 'T':
			                    button = t;
			                    break;
			                case 'U':
			                    button = u;
			                    break;
			                case 'V':
			                    button = v;
			                    break;
			                case 'W':
			                    button = w;
			                    break;
			                case 'X':
			                    button = x;
			                    break;
			                case 'Y':
			                    button = y;
			                    break;
			                case 'Z':
			                    button = z;
			                    break;
			                default:
			                    continue;
			            }
			    	    int index = game.getCorrectWord().toUpperCase().indexOf(ch);
			    	    String buttonStyle = button.getStyle();
			    	    if (!buttonStyle.contains("-fx-background-color: green;")) {
				    	    if (index == word.indexOf(ch)) {
				    	    	button.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: green;");
				    	    } else if (index == -1) {
				    	    	button.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: #363636;");
				    	    } else {
				    	    	button.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: yellow;");
				    	    }
			    	    }
			    	}
			    }
		    }
		}
	}
	
	private void pressEnter() {
		
		for (int i = wordIndex; i < wordIndex + 5; i++) {
			text += boxes[i].getText();
		}
		
		if (wordList.contains(text.toLowerCase())) {
			
			if (letterCount == 5) {
				letterCount = 0;
				maxBoxes = maxBoxes - 5;
				rowCount++;

				wordIndex = wordIndex + 5;
				userWord = text;
				text = "";
				startRound();
				checkLetters();

			}
			
		} else {
			text = "";
			
			for (int i = wordIndex; i < wordIndex + 5; i ++) {
				TranslateTransition translateTransition = new TranslateTransition();
				translateTransition.setDuration(Duration.millis(75));
				translateTransition.setNode(boxes[i]);
				translateTransition.setByX(5);
				translateTransition.setByX(-5);
				translateTransition.setCycleCount(6);
				translateTransition.setAutoReverse(true);
				translateTransition.play();
			}
		}
	}
	
	private void createMenuBar() {
		menuBar = new MenuBar();
		gameOptionsMenu = new Menu("Game");
		accountOptionsMenu = new Menu("Account");
		appearanceMenu = new Menu("Appearance");
		lightOption = new MenuItem("Light Mode");
		darkOption = new MenuItem("Dark Mode");
		newGameOption = new MenuItem("New Game");
		helpOption = new MenuItem("How To Play");
		statisticsOption = new MenuItem("Statistics");
		logoutOption = new MenuItem("Log Out");
		loginOption = new MenuItem("Log In");
		createAccountOption = new MenuItem("Create Account");
		leaderBoardOption = new MenuItem("Leaderboard");
		
		appearanceMenu.getItems().add(lightOption);
		appearanceMenu.getItems().add(darkOption);
		gameOptionsMenu.getItems().add(newGameOption);
		gameOptionsMenu.getItems().add(appearanceMenu);
		gameOptionsMenu.getItems().add(helpOption);
		
		
		menuBar.getMenus().add(gameOptionsMenu);
		menuBar.getMenus().add(accountOptionsMenu);
	}
	
	public void addAccountMenu() {
		accountOptionsMenu.getItems().clear();
		if (game.getCurUser() == null) {
			accountOptionsMenu.getItems().add(createAccountOption);
			accountOptionsMenu.getItems().add(loginOption);
		} else {
			accountOptionsMenu.getItems().add(logoutOption);
			accountOptionsMenu.getItems().add(statisticsOption);
			accountOptionsMenu.getItems().add(leaderBoardOption);
		}
	}
	

	private void registerHandlers() {
		q.setOnAction(new pressQ());
		backBtn.setOnAction(new pressbackBtn());
		w.setOnAction(new pressW());
		e.setOnAction(new pressE());
		r.setOnAction(new pressR());
		t.setOnAction(new pressT());
		y.setOnAction(new pressY());
		u.setOnAction(new pressU());
		i.setOnAction(new pressI());
		o.setOnAction(new pressO());
		p.setOnAction(new pressP());
		a.setOnAction(new pressA());
		s.setOnAction(new pressS());
		d.setOnAction(new pressD());
		f.setOnAction(new pressF());
		g.setOnAction(new pressG());
		h.setOnAction(new pressH());
		j.setOnAction(new pressJ());
		k.setOnAction(new pressK());
		l.setOnAction(new pressL());
		z.setOnAction(new pressZ());
		x.setOnAction(new pressX());
		c.setOnAction(new pressC());
		v.setOnAction(new pressV());
		b.setOnAction(new pressB());
		n.setOnAction(new pressN());
		m.setOnAction(new pressM());
		enterBtn.setOnAction(new pressEnter());
		lightOption.setOnAction(new setLightMode());
		darkOption.setOnAction(new setDarkMode());
		newGameOption.setOnAction(new newGame());
		helpOption.setOnAction(new showHelp());
		statisticsOption.setOnAction(new statisticsHandler());
		leaderBoardOption.setOnAction(new leaderBoardHandler());
		logoutOption.setOnAction(new accountHandler(this));
		loginOption.setOnAction(new accountHandler(this));
		createAccountOption.setOnAction(new accountHandler(this));
	}

	private class pressQ implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("Q");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}
	}

	private class pressW implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("W");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}
	}

	private class pressE implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("E");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}
		}
	}

	private class pressR implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("R");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}
		}
	}

	private class pressT implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("T");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}
		}
	}

	private class pressY implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("Y");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}
		}
	}

	private class pressU implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("U");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}
	}

	private class pressI implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("I");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}
	}

	private class pressO implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("O");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}
	}

	private class pressP implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("P");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}
	}

	private class pressA implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("A");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}
	}

	private class pressS implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("S");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}
	}

	private class pressD implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("D");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}
	}

	private class pressF implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("F");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}
	}

	private class pressG implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("G");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}
	}

	private class pressH implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("H");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}
	}

	private class pressJ implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("J");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}
	}

	private class pressK implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("K");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}
	}

	private class pressL implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("L");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}
	}

	private class pressZ implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("Z");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}
	}

	private class pressX implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("X");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}
	}

	private class pressC implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("C");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}
	}

	private class pressV implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("V");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}
	}

	private class pressB implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("B");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}
	}

	private class pressN implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("N");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}
	}

	private class pressM implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if (letterCount < 5) {
				boxes[currentBox].setText("M");
				boxes[currentBox].setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set the font size and weight
				boxes[currentBox].setAlignment(Pos.CENTER); // Center the text within the box
				if (currentBox < 30) {

					currentBox++;
					letterCount++;

				}
			}

			if (letterCount < 0) {
				letterCount = 0; // set to 0 if less than 0
			} else if (letterCount > 5) {
				letterCount = 5; // set to 5 if greater than 5
			}

		}
	}

	private class pressEnter implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			pressEnter();
		}
	}

	private class pressbackBtn implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			pressBack();
		}
	}
	

	private class statisticsHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			// Create a button for coming back from statistics
			if (game.getCurUser() != null) {
				Button backButton = new Button("Back to Wordle");
				backButton.setOnAction(event -> setBackToWordle());
				
				StatisticsView statisticsView = new StatisticsView();
				WordleAccount wordleAccount = game.getCurUser();
				stage.setScene(statisticsView.getView(wordleAccount, backButton, game));
				stage.show();
			}
		}

		private void setBackToWordle() {
			WordleAccount wordleAccount = game.getCurUser();
			newGame newGame = new newGame();

			newGame.handle(null);
			stage.setScene(scene);
			stage.show();
		}
	}

	private class leaderBoardHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			// Create a button for coming back from statistics
			Button backButton = new Button("Back to Wordle");
			backButton.setOnAction(event -> setBackToWordle());
			
			LeaderBoardView leaderBoardView = new LeaderBoardView();
			WordleAccount wordleAccount = game.getCurUser();
			stage.setScene(leaderBoardView.getView(wordleAccount, backButton, game));
			stage.show();
		}

		private void setBackToWordle() {
			WordleAccount wordleAccount = game.getCurUser();
			newGame newGame = new newGame();
			newGame.handle(null);
			stage.setScene(scene);
			stage.show();
		}
	}
	
	private class accountHandler implements EventHandler<ActionEvent> {
		WordleGUI wordleGUI;
		
		public accountHandler(WordleGUI wordleGUI) {
			this.wordleGUI = wordleGUI;
		}
		
		@Override
		public void handle(ActionEvent arg0) {
			game.logout();
			LoginCreateAccountGUI newLoginCreateAccountGUI = new LoginCreateAccountGUI(game, wordleGUI);
		}		
	}
	
	private class setLightMode implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			BackgroundFill backgroundFill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
			everything.setBackground(new Background(backgroundFill));
			
			q.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			w.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			e.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			r.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			t.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			y.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			u.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			i.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			o.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			p.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			a.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			s.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			d.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			f.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			g.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			h.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			j.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			k.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			l.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			z.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			x.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			c.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			v.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			b.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			n.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			m.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			enterBtn.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			backBtn.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			
			for (int i = rowCount * 5; i < boxes.length; i++) {
				Label box = boxes[i];
				box.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
			}
			
			darkMode = false;
			checkLetters();
		}
		
	}
	
	private class setDarkMode implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			BackgroundFill backgroundFill = new BackgroundFill(Color.web("#515151"), CornerRadii.EMPTY, Insets.EMPTY);
			everything.setBackground(new Background(backgroundFill));
			
			q.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			w.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			e.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			r.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			t.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			y.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			u.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			i.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			o.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			p.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			a.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			s.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			d.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			f.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			g.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			h.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			j.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			k.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			l.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			z.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			x.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			c.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			v.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			b.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			n.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			m.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			enterBtn.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			backBtn.setStyle("-fx-border-color: grey; -fx-text-fill: grey; -fx-background-color: black;");
			
			for (int i = rowCount * 5; i < boxes.length; i++) {
				Label box = boxes[i];
				box.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: dimgrey;");
			}
			
			darkMode = true;
			checkLetters();
			
		}
		
	}
	
	private class newGame implements EventHandler<ActionEvent> {

		WordleAccount account = game.getCurUser();

		@Override
		public void handle(ActionEvent arg0) {
			currentBox = 0;
			letterCount = 0;
			rowCount = 0;
			maxBoxes = 30;
			wordIndex = 0;
			userWord = "";
			text = "";
			wordsGuessed.clear();
			
			labelPane.setLeft(null);
			labelPane.setRight(null);

			
			try {
				game = new WordleGame();
				if (account == null) {
					game.setCurUser(null);
				}
				else {
					game.setCurUser(account.getUsername());
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			showWordle();
			registerHandlers();
		}
		
	}
	
	private class showHelp implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			HowToPlayGUI howToPlayGUI = new HowToPlayGUI();
		}
		
	}
	
	public void showWordle() {
		// Create a label for the Wordle title
		Label titleLabel = new Label("Wordle");
		titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
		titleLabel.setPadding(new Insets(20, 0, 0, 0));
		titleLabel.setStyle("-fx-text-fill: black;");
		labelPane.setCenter(titleLabel);
		
		VBox topBox = new VBox();
		topBox.getChildren().addAll(menuBar, labelPane);
		topBox.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(topBox, Pos.CENTER); // set the alignment of the label within the BorderPane
		everything.setTop(topBox);
		// everything.setPadding(new Insets(20, 0, 0, 0));
		
		// Create a button for statistics
		// Button statisticsButton = new Button("Statistics");
		// BorderPane.setAlignment(statisticsButton, Pos.TOP_RIGHT);
		// everything.setRight(statisticsButton);
		// statisticsButton.setOnAction(new StatisticsHandler());
		

		// Create a grid pane to hold the Wordle boxes
		GridPane wordleGrid = new GridPane();
		wordleGrid.setAlignment(Pos.CENTER);
		wordleGrid.setHgap(5);
		wordleGrid.setVgap(5);
		wordleGrid.setPadding(new Insets(-100, 0, 0, 0));

		// Create an array to hold the Wordle boxes

		// Create six rows of Wordle boxes
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				Label box = new Label();
				box.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-background-color: white;");
				box.setPrefSize(50, 50);
				wordleGrid.add(box, j, i);

				// Add the box to the array
				int index = i * 5 + j;
				boxes[index] = box;
			}
		}

		// Add the Wordle grid to the center of the border pane
		everything.setCenter(wordleGrid);

		// Create an HBox to hold the on-screen keyboard
		VBox keyboardBox = new VBox();
		keyboardBox.setSpacing(5);
		keyboardBox.setPadding(new Insets(0, 0, 40, 180));

		// Create an VBox to hold the on-screen keyboard
		HBox row1 = new HBox();
		row1.setSpacing(5);

		HBox row2 = new HBox();
		row2.setSpacing(5);
		row2.setPadding(new Insets(0, 0, 0, 15));

		HBox row3 = new HBox();
		row3.setSpacing(5);

		// Create the on-screen keyboard
		q = new Button("Q");
		q.setStyle("-fx-border-color: black; -fx-background-color: white;");
		q.setPrefSize(30, 30);
		row1.getChildren().add(q);

		q.setOnAction(e -> {
			boxes[0].setText("Q");
		});

		w = new Button("W");
		w.setStyle("-fx-border-color: black; -fx-background-color: white;");
		w.setPrefSize(30, 30);
		row1.getChildren().add(w);

		e = new Button("E");
		e.setStyle("-fx-border-color: black; -fx-background-color: white;");
		e.setPrefSize(30, 30);
		row1.getChildren().add(e);

		r = new Button("R");
		r.setStyle("-fx-border-color: black; -fx-background-color: white;");
		r.setPrefSize(30, 30);
		row1.getChildren().add(r);

		t = new Button("T");
		t.setStyle("-fx-border-color: black; -fx-background-color: white;");
		t.setPrefSize(30, 30);
		row1.getChildren().add(t);

		y = new Button("Y");
		y.setStyle("-fx-border-color: black; -fx-background-color: white;");
		y.setPrefSize(30, 30);
		row1.getChildren().add(y);

		u = new Button("U");
		u.setStyle("-fx-border-color: black; -fx-background-color: white;");
		u.setPrefSize(30, 30);
		row1.getChildren().add(u);

		i = new Button("I");
		i.setStyle("-fx-border-color: black; -fx-background-color: white;");
		i.setPrefSize(30, 30);
		row1.getChildren().add(i);

		o = new Button("O");
		o.setStyle("-fx-border-color: black; -fx-background-color: white;");
		o.setPrefSize(30, 30);
		row1.getChildren().add(o);

		p = new Button("P");
		p.setStyle("-fx-border-color: black; -fx-background-color: white;");
		p.setPrefSize(30, 30);
		row1.getChildren().add(p);

		a = new Button("A");
		a.setStyle("-fx-border-color: black; -fx-background-color: white;");
		a.setPrefSize(30, 30);
		row2.getChildren().add(a);

		s = new Button("S");
		s.setStyle("-fx-border-color: black; -fx-background-color: white;");
		s.setPrefSize(30, 30);
		row2.getChildren().add(s);

		d = new Button("D");
		d.setStyle("-fx-border-color: black; -fx-background-color: white;");
		d.setPrefSize(30, 30);
		row2.getChildren().add(d);

		f = new Button("F");
		f.setStyle("-fx-border-color: black; -fx-background-color: white;");
		f.setPrefSize(30, 30);
		row2.getChildren().add(f);

		g = new Button("G");
		g.setStyle("-fx-border-color: black; -fx-background-color: white;");
		g.setPrefSize(30, 30);
		row2.getChildren().add(g);

		h = new Button("H");
		h.setStyle("-fx-border-color: black; -fx-background-color: white;");
		h.setPrefSize(30, 30);
		row2.getChildren().add(h);

		j = new Button("J");
		j.setStyle("-fx-border-color: black; -fx-background-color: white;");
		j.setPrefSize(30, 30);
		row2.getChildren().add(j);

		k = new Button("K");
		k.setStyle("-fx-border-color: black; -fx-background-color: white;");
		k.setPrefSize(30, 30);
		row2.getChildren().add(k);

		l = new Button("L");
		l.setStyle("-fx-border-color: black; -fx-background-color: white;");
		l.setPrefSize(30, 30);
		row2.getChildren().add(l);

		enterBtn = new Button("Enter");
		enterBtn.setStyle("-fx-border-color: black; -fx-background-color: white;");
		enterBtn.setPrefSize(50, 30);
		enterBtn.setDefaultButton(true);
		enterBtn.requestFocus();
		row3.getChildren().add(enterBtn);

		z = new Button("Z");
		z.setStyle("-fx-border-color: black; -fx-background-color: white;");
		z.setPrefSize(30, 30);
		row3.getChildren().add(z);

		x = new Button("X");
		x.setStyle("-fx-border-color: black; -fx-background-color: white;");
		x.setPrefSize(30, 30);
		row3.getChildren().add(x);

		c = new Button("C");
		c.setStyle("-fx-border-color: black; -fx-background-color: white;");
		c.setPrefSize(30, 30);
		row3.getChildren().add(c);

		v = new Button("V");
		v.setStyle("-fx-border-color: black; -fx-background-color: white;");
		v.setPrefSize(30, 30);
		row3.getChildren().add(v);

		b = new Button("B");
		b.setStyle("-fx-border-color: black; -fx-background-color: white;");
		b.setPrefSize(30, 30);
		row3.getChildren().add(b);

		n = new Button("N");
		n.setStyle("-fx-border-color: black; -fx-background-color: white;");
		n.setPrefSize(30, 30);
		row3.getChildren().add(n);

		m = new Button("M");
		m.setStyle("-fx-border-color: black; -fx-background-color: white;");
		m.setPrefSize(30, 30);
		row3.getChildren().add(m);

		backBtn = new Button("Back");
		backBtn.setStyle("-fx-border-color: black; -fx-background-color: white;");
		backBtn.setPrefSize(50, 30);
		row3.getChildren().add(backBtn);

		// Add the rows to the keyboard VBox
		keyboardBox.getChildren().addAll(row1, row2, row3);

		// Add the on-screen keyboard to the bottom of the border pane
		everything.setBottom(keyboardBox);
		BackgroundFill backgroundFill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
		everything.setBackground(new Background(backgroundFill));
		
		if (darkMode) {
			new setDarkMode().handle(null);;
		}
	}
	
	
}
