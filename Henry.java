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

public class Henry {
    private Scene sceneOne;
    private Scene sceneTwo;
    public double income; 
    public String payFrequency; 
    public MediaPlayer player;
    public Button sceneOneButtonOne, sceneOneButtonTwo, sceneTwoButtonOne, sceneTwoButtonTwo;
    public ComboBox<String> frequencyComboBox; 
    public TextField incomeField;
    public Label errorLabel; 

    public Henry(Stage primaryStage) {
        // First Scene
        /****************************************************/
        VBox vBoxSceneOne = new VBox(20);
        vBoxSceneOne.setAlignment(Pos.CENTER);
        vBoxSceneOne.setStyle("-fx-background-color: #f0f8ff;");

        // Image
        Image image = new Image("file:content/2.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        
        StackPane stackPane = new StackPane(); 
        stackPane.getChildren().addAll(imageView);
        

        // Title Label
        Label sceneOneLabelOne = new Label("Welcome to the Personal Budget Planner");
        sceneOneLabelOne.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        sceneOneLabelOne.setStyle("-fx-text-fill: #333;");

        // Buttons
        sceneOneButtonOne = new Button("Start Budget Planning");
        sceneOneButtonOne.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-padding: 10 20;");
        sceneOneButtonTwo = new Button("Quit");
        sceneOneButtonTwo.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 10 20;");

        // Adding nodes to the layout
        vBoxSceneOne.getChildren().addAll(stackPane,sceneOneLabelOne, sceneOneButtonOne, sceneOneButtonTwo);
        /****************************************************/

        // Second Scene
        /****************************************************/
        VBox vBoxSceneTwo = new VBox(30);
        vBoxSceneTwo.setAlignment(Pos.CENTER);
        vBoxSceneTwo.setStyle("-fx-background-color: #f0f8ff;");

        // label and combo box
        Label sceneTwoLabelOne = new Label("Select Pay Frequency");
        sceneTwoLabelOne.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        sceneTwoLabelOne.setStyle("-fx-text-fill: #333;");

        frequencyComboBox = new ComboBox<>();
        frequencyComboBox.getItems().addAll("Weekly", "Biweekly", "Monthly");
        frequencyComboBox.setStyle("-fx-font-size: 14;");

        // label and text field
        Label incomeLabel = new Label("Enter Income:");
        incomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        incomeLabel.setStyle("-fx-text-fill: #333;");

        incomeField = new TextField();
        incomeField.setPromptText("Enter your income...");
        incomeField.setStyle("-fx-font-size: 14; -fx-padding: 5;");

        HBox inputBox = new HBox(10, incomeLabel, incomeField);
        inputBox.setAlignment(Pos.CENTER);

        // Buttons
        sceneTwoButtonOne = new Button("Go Back");
        sceneTwoButtonOne.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; -fx-padding: 10 20;");
        sceneTwoButtonTwo = new Button("Confirm");
        sceneTwoButtonTwo.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-padding: 10 20;");

        HBox hBoxSceneTwo = new HBox(20, sceneTwoButtonOne, sceneTwoButtonTwo);
        hBoxSceneTwo.setAlignment(Pos.CENTER);

        errorLabel = new Label("Please fill in all information!"); 
        errorLabel.setVisible(false);
        

        vBoxSceneTwo.getChildren().addAll(sceneTwoLabelOne, frequencyComboBox, inputBox, hBoxSceneTwo, errorLabel);
        
        /****************************************************/

        // Scenes
        sceneOne = new Scene(vBoxSceneOne, 400, 400);
        sceneTwo = new Scene(vBoxSceneTwo, 400, 400);


        /* 

        // Audio
        File file = new File("content/money.mp3");
        Media media = new Media(file.toURI().toString()); 
        MediaPlayer player = new MediaPlayer(media);
        

        // Button Actions
        sceneOneButtonOne.setOnAction(e -> {
            primaryStage.setScene(sceneTwo);
            player.seek(Duration.millis(1000));
            player.play();

            // reset to the beginning
            player.seek(Duration.ZERO);
        });

        // Exit button 
        sceneOneButtonTwo.setOnAction(e -> Platform.exit());


        // Go back button
        sceneTwoButtonOne.setOnAction(e -> {
            primaryStage.setScene(sceneOne); 
            errorLabel.setVisible(false);
        });

        // Confirm button
        sceneTwoButtonTwo.setOnAction(e -> {
            if (!incomeField.getText().isEmpty() && !frequencyComboBox.getValue().isEmpty()) {
                errorLabel.setVisible(false);
                income = Double.parseDouble(incomeField.getText()); 
                payFrequency = new String(frequencyComboBox.getValue());
                incomeField.setText("");
                frequencyComboBox.setValue(null);


                // goes back to first scene;
                primaryStage.setScene(sceneOne);
            }
            else if (incomeField.getText().isEmpty() || frequencyComboBox.getValue().isEmpty()) {
                errorLabel.setVisible(true);
            
                
            }

        });
        */
            
    }
    
    public Scene getSceneOne() {
        return sceneOne;
    }

    public Scene getSceneTwo() {
        return sceneTwo;
    }
}
