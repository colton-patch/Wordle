package view_controller;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * The window which displays the rules of the game
 */
public class HowToPlayGUI {

    Stage stage;
    Scene scene;
    GridPane everything;
    ImageView topPic = new ImageView(new Image(new File("topPic.png").toURI().toString()));
    ImageView midPic = new ImageView(new Image(new File("midPic.png").toURI().toString()));
    ImageView botPic = new ImageView(new Image(new File("botPic.png").toURI().toString()));

    public HowToPlayGUI() {
        stage = new Stage();
        stage.setTitle("How To Play");

        everything = new GridPane();

        layoutScene();
        
        scene = new Scene(everything, 500, 500);

        stage.setScene(scene);
        stage.show();
    }

    private void layoutScene() {
        everything.setPadding(new Insets(10));
        everything.setVgap(10);
        everything.setHgap(10);
        everything.setAlignment(Pos.CENTER);

        Label howToPlayLabel = new Label("How To Play");
        howToPlayLabel.setStyle("-fx-font-size: 24pt; -fx-text-fill: black;");

        Label instructionsLabel = new Label("Instructions:");
        instructionsLabel.setStyle("-fx-font-size: 18pt; -fx-text-fill: black;");

        Label guessLabel = new Label("Guess the Wordle in 6 tries.");
        guessLabel.setStyle("-fx-text-fill: black;");

        Label wordLengthLabel = new Label("Each guess must be a valid 5-letter word.");
        wordLengthLabel.setStyle("-fx-text-fill: black;");

        Label colorLabel = new Label("The color of the tiles will change to show how close your guess was to the word.");
        colorLabel.setStyle("-fx-text-fill: black;");

        Label examplesLabel = new Label("Examples:");
        examplesLabel.setStyle("-fx-font-size: 18pt; -fx-text-fill: black;");

        Label wLabel = new Label("W is in the word and in the correct spot.");
        wLabel.setStyle("-fx-text-fill: black;");

        Label iLabel = new Label("I is in the word but in the wrong spot.");
        iLabel.setStyle("-fx-text-fill: black;");

        Label uLabel = new Label("U is not in the word in any spot.");
        uLabel.setStyle("-fx-text-fill: black;");

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> stage.close());
        
     

        HBox buttonBox = new HBox(closeButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);

        everything.add(howToPlayLabel, 0, 0);
        everything.add(instructionsLabel, 0, 1);
        everything.add(guessLabel, 0, 2);
        everything.add(wordLengthLabel, 0, 3);
        everything.add(colorLabel, 0, 4);
        everything.add(examplesLabel, 0, 5);
        everything.add(wLabel, 0, 7);
        everything.add(topPic, 0, 6);
        everything.add(iLabel, 0, 9);
        everything.add(midPic, 0, 8);
        everything.add(uLabel, 0, 11);
        everything.add(botPic, 0, 10);
        everything.add(buttonBox, 0, 12);
    }

}
