import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.geometry.*;
import javafx.scene.control.*; 

// used to terminate the program when pressing the quit button 
import javafx.application.Platform;

// import for images; 
import javafx.scene.image.*; 

// import for text, font,etc
import javafx.scene.text.*; 

import javafx.event.*;



public class Henry extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
			// First scene
			/********************************************/		
			VBox vBox = new VBox(15); 
			

			Image image = new Image("file:image/2.png");
			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(200);
			imageView.setFitWidth(200);
			
			
			vBox.setAlignment(Pos.CENTER);
			
			Label sceneOnelabelOne = new Label("Welcome to the Personal Budget Planner"); 
			sceneOnelabelOne.setFont(Font.font(18));
			
			Button sceneOneButtonOne = new Button("Start Budget Planning");
			//sceneOneButtonOne.setFont(Font.font("Noteworthy"));
			Button sceneOneButtonTwo = new Button("Quit");
			//sceneOneButtonTwo.setFont(Font.font("Noteworthy"));
			
			vBox.getChildren().addAll(imageView,sceneOnelabelOne, sceneOneButtonOne, sceneOneButtonTwo);
			/********************************************/
			
			
			
			// Second scene
			/********************************************/
			VBox vBoxSceneTwo = new VBox(50);
			vBoxSceneTwo.setAlignment(Pos.CENTER);
			
			HBox hBoxSceneTwo = new HBox(10); 
			hBoxSceneTwo.setAlignment(Pos.CENTER);
			
			
			
			Label sceneTwoLabelOne = new Label("Select Pay Frequency");
			Button sceneTwoButtonOne = new Button("Go Back"); 
			Button sceneTwoButtonTwo = new Button("Confirm"); 
			
			ComboBox<String> nameComboBox = new ComboBox<>();
			nameComboBox.getItems().addAll("Weekly", "Biweekly", "Monthly");
			
			sceneTwoLabelOne.setGraphic(nameComboBox); 
			sceneTwoLabelOne.setGraphicTextGap(5);
			sceneTwoLabelOne.setContentDisplay(ContentDisplay.BOTTOM); 
			
			Label sceneTwoLabelTwo= new Label("Enter Income: $", new TextField());
			sceneTwoLabelTwo.setContentDisplay(ContentDisplay.BOTTOM);
			
			hBoxSceneTwo.getChildren().addAll(sceneTwoButtonOne,sceneTwoButtonTwo);
			vBoxSceneTwo.getChildren().addAll(sceneTwoLabelOne,sceneTwoLabelTwo,hBoxSceneTwo); 
			/********************************************/
			
			
			//root.setCenter(vBoxSceneTwo);
			
			
			Scene sceneOne = new Scene(vBox,400,400);
			Scene sceneTwo = new Scene(vBoxSceneTwo,400,400);
			
			sceneOneButtonOne.setOnAction(e->primaryStage.setScene(sceneTwo));
			sceneOneButtonTwo.setOnAction(e->Platform.exit());
			sceneTwoButtonOne.setOnAction(e->primaryStage.setScene(sceneOne)); 
			
			
			
			
			sceneOne.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(sceneOne);
			primaryStage.show();
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	class ButtonClickHandlerTwo implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			
			
		}
		
		
		
		
		
	}
}
