import javafx.scene.paint.*;
import javafx.animation.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.application.Platform;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.event.*;
import javafx.scene.media.*;
import java.io.*;
import javafx.util.Duration;
import javafx.scene.shape.*;

public class MainScene extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Create an instance of Henry and pass the stage
        Henry henry = new Henry(primaryStage);

        // Audio Files
        File file = new File("content/monkey.mp3");
        Media media = new Media(file.toURI().toString()); 
        MediaPlayer player = new MediaPlayer(media);

        Scene sceneOne = henry.getSceneOne();
        Scene sceneTwo = henry.getSceneTwo();

        // Button Actions
        henry.sceneOneButtonOne.setOnAction(e -> {
            primaryStage.setScene(sceneTwo);

            player.play();

            // reset to the beginning
            player.seek(Duration.ZERO);
        });

        // Exit button 
        henry.sceneOneButtonTwo.setOnAction(e -> Platform.exit());

        // Go back button
        henry.sceneTwoButtonOne.setOnAction(e -> {
            primaryStage.setScene(sceneOne); 
            henry.errorLabel.setVisible(false);
        });

        // Confirm button
        henry.sceneTwoButtonTwo.setOnAction(e -> {
            try {
                if (!henry.incomeField.getText().isEmpty() && henry.frequencyComboBox.getValue() != null) {
                    // Try to parse income as a Double
                    henry.income = Double.parseDouble(henry.incomeField.getText()); 
                    henry.payFrequency = new String(henry.frequencyComboBox.getValue());
                    henry.incomeField.setText("");
                    henry.frequencyComboBox.setValue(null);
        
                    // Goes back to the first scene
                    primaryStage.setScene(sceneOne);
                    henry.errorLabel.setVisible(false); 
                    henry.errorLabel.setText("Please fill in all information!");
                }
                else {
                    henry.errorLabel.setVisible(true); 
                }
            } catch (NumberFormatException ex) {
                // If parsing fails, show error for invalid income input
                henry.errorLabel.setText("Please enter a valid number for income.");
                henry.errorLabel.setVisible(true);
            }
        });



        // Set the initial scene and show the stage
        primaryStage.setScene(sceneOne);
        



        primaryStage.setTitle("Personal Budget Planner");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}