package view_controller;

import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.WordleAccount;
import model.WordleGame;

/**
 * Allows the user to create an account, login to an existing one, or play as a guest.
 */
public class LoginCreateAccountGUI{
	
	Label loginLabel;
	Button loginButton;
	Label createAccountLabel;
	Button createAccountButton;
	Label playAsGuestLabel;
	Button playAsGuestButton;
	TextField usernameText;
	Label usernameLabel;
	PasswordField passwordText;
	Label passwordLabel;
	Button completeLoginButton;
	Button completeCreateAccountButton;
	Button cancelButton;
	Stage stage;
	Scene scene;
	GridPane everything;
	Alert guestAlert;
	Alert existingUsernameAlert;
	Alert createdAlert;
	Alert invalidLoginAlert;
	WordleGame game;
	WordleGUI wordleGUI;
	
	public LoginCreateAccountGUI(WordleGame game, WordleGUI wordleGUI) {
		this.wordleGUI = wordleGUI;
		stage = new Stage();
		stage.setTitle("Login");
		everything = new GridPane();
		this.game = game;
		layoutScene();
		scene = new Scene(everything, 400, 400);
		stage.setScene(scene);
		stage.show();
	}

	private void layoutScene() {
		
		everything.getChildren().clear();
		
		everything.setPadding(new Insets(10));
		everything.setVgap(10);
		everything.setHgap(10);
		
		loginLabel = new Label("Login");
		usernameLabel = new Label("Username");
		passwordLabel = new Label("Password");
		usernameText = new TextField();
		passwordText = new PasswordField();
		loginButton = new Button("Login");
		createAccountLabel = new Label("Create Account");
		createAccountButton = new Button("Create new account");
		playAsGuestButton = new Button("Continue as guest");
		cancelButton = new Button("Cancel");
		completeLoginButton = new Button("Login");
		completeCreateAccountButton = new Button("Create account");
		
		everything.add(loginButton, 12, 12);
		everything.add(createAccountButton, 12, 13);
		everything.add(playAsGuestButton, 12, 14);
		
		loginButton.setOnAction(new LoginHandler());
		
		createAccountButton.setOnAction(new CreateAccountHandler());
		
		playAsGuestButton.setOnAction(new GuestHandler());
	}
	
	private void layoutCreateAccount() {
		everything.getChildren().clear();
		everything.add(createAccountLabel, 12, 12);
		everything.add(usernameLabel, 12, 13);
		everything.add(usernameText, 13, 13);
		everything.add(passwordLabel, 12, 14);
		everything.add(passwordText, 13, 14);
		everything.add(cancelButton, 12, 15);
		everything.add(completeCreateAccountButton, 13, 15);
		cancelButton.setOnAction(new CancelHandler());
		completeCreateAccountButton.setOnAction(event -> {
			String username = usernameText.getText();
			String password = passwordText.getText();
			// If username is already taken
			if (game.createAccount(username, password) == false) {
				existingUsernameAlert = new Alert(AlertType.ERROR);
				existingUsernameAlert.setHeaderText("Username already exists");
				existingUsernameAlert.setContentText("Choose different username or log in to existing account.");
				Optional<ButtonType> result = existingUsernameAlert.showAndWait();
				if (result.get() == ButtonType.OK) {
					layoutCreateAccount();
				}
			}
			else {
				createdAlert = new Alert(AlertType.NONE);
				createdAlert.setTitle("Success");
				createdAlert.setHeaderText("Account created");
				createdAlert.setContentText("Log in to play");
				createdAlert.getDialogPane().getButtonTypes().add(ButtonType.OK);
				Optional<ButtonType> result = createdAlert.showAndWait();
				if (result.get() == ButtonType.OK) {
					layoutScene();
				}
			}
		});
	}
	
	private void layoutLogin() {
		everything.getChildren().clear();
		everything.add(loginLabel, 12, 12);
		everything.add(usernameLabel, 12, 13);
		everything.add(usernameText, 13, 13);
		everything.add(passwordLabel, 12, 14);
		everything.add(passwordText, 13, 14);
		everything.add(cancelButton, 12, 15);
		everything.add(completeLoginButton, 13, 15);
		cancelButton.setOnAction(new CancelHandler());
		completeLoginButton.setOnAction(event -> {
			String username = usernameText.getText();
			String password = passwordText.getText();
			if (game.login(username, password) == false) {
				invalidLoginAlert = new Alert(AlertType.ERROR);
				invalidLoginAlert.setHeaderText("Invalid username or password.");
				Optional<ButtonType> result = invalidLoginAlert.showAndWait();
				if (result.get() == ButtonType.OK) {
					usernameText.clear();
					passwordText.clear();
					layoutLogin();
				}
			}
			else {
				if (wordleGUI != null) {
					wordleGUI.addAccountMenu();
				}
				stage.close();
			}
		});
	}
	
	private class LoginHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			layoutLogin();
		}
	}
	
	private class CreateAccountHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			layoutCreateAccount();
		}
		
	}
	
	private class GuestHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			guestAlert = new Alert(AlertType.CONFIRMATION);
			guestAlert.setTitle("Play as guest");
			guestAlert.setHeaderText("Statistics will not be saved if playing as guest.");
			guestAlert.setContentText("Press OK to continue.");
			Optional<ButtonType> result = guestAlert.showAndWait();
			if (result.get() == ButtonType.OK) {
				if (wordleGUI != null) {
					wordleGUI.addAccountMenu();
				}
				stage.close();
			}
			else {
				layoutScene();
			}
		}
		
	}
	
	private class CancelHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			layoutScene();
		}
		
	}
	
}
