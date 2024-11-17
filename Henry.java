import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.application.Platform;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.event.*;

public class Henry extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // First Scene
            VBox vBoxSceneOne = new VBox(20);
            vBoxSceneOne.setAlignment(Pos.CENTER);
            vBoxSceneOne.setStyle("-fx-background-color: #f0f8ff;");

            // Image
            Image image = new Image("file:image/2.png");
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);

            // Title Label
            Label sceneOneLabelOne = new Label("Welcome to the Personal Budget Planner");
            sceneOneLabelOne.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            sceneOneLabelOne.setStyle("-fx-text-fill: #333;");

            // Buttons
            Button sceneOneButtonOne = new Button("Start Budget Planning");
            sceneOneButtonOne.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-padding: 10 20;");
            Button sceneOneButtonTwo = new Button("Quit");
            sceneOneButtonTwo.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 10 20;");

            // Adding nodes to the layout
            vBoxSceneOne.getChildren().addAll(imageView, sceneOneLabelOne, sceneOneButtonOne, sceneOneButtonTwo);

            // Second Scene
            VBox vBoxSceneTwo = new VBox(30);
            vBoxSceneTwo.setAlignment(Pos.CENTER);
            vBoxSceneTwo.setStyle("-fx-background-color: #e8f5e9;");

            // Dropdown and Label
            Label sceneTwoLabelOne = new Label("Select Pay Frequency");
            sceneTwoLabelOne.setFont(Font.font("Arial", FontWeight.BOLD, 18));
            sceneTwoLabelOne.setStyle("-fx-text-fill: #333;");

            ComboBox<String> frequencyComboBox = new ComboBox<>();
            frequencyComboBox.getItems().addAll("Weekly", "Biweekly", "Monthly");
            frequencyComboBox.setStyle("-fx-font-size: 14;");

            // Input Field and Label
            Label incomeLabel = new Label("Enter Income:");
            incomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            incomeLabel.setStyle("-fx-text-fill: #333;");

            TextField incomeField = new TextField();
            incomeField.setPromptText("Enter your income...");
            incomeField.setStyle("-fx-font-size: 14; -fx-padding: 5;");

            HBox inputBox = new HBox(10, incomeLabel, incomeField);
            inputBox.setAlignment(Pos.CENTER);

            // Navigation Buttons
            Button sceneTwoButtonOne = new Button("Go Back");
            sceneTwoButtonOne.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; -fx-padding: 10 20;");
            Button sceneTwoButtonTwo = new Button("Confirm");
            sceneTwoButtonTwo.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-padding: 10 20;");

            HBox hBoxSceneTwo = new HBox(20, sceneTwoButtonOne, sceneTwoButtonTwo);
            hBoxSceneTwo.setAlignment(Pos.CENTER);

            vBoxSceneTwo.getChildren().addAll(sceneTwoLabelOne, frequencyComboBox, inputBox, hBoxSceneTwo);

            // Scenes
            Scene sceneOne = new Scene(vBoxSceneOne, 400, 400);
            Scene sceneTwo = new Scene(vBoxSceneTwo, 400, 400);

            // Button Actions
            sceneOneButtonOne.setOnAction(e -> primaryStage.setScene(sceneTwo));
            sceneOneButtonTwo.setOnAction(e -> Platform.exit());
            sceneTwoButtonOne.setOnAction(e -> primaryStage.setScene(sceneOne));
            sceneTwoButtonTwo.setOnAction(e -> System.out.println("Confirmed income and frequency"));

            // Set Initial Scene
            primaryStage.setScene(sceneOne);
            primaryStage.setTitle("Personal Budget Planner");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
