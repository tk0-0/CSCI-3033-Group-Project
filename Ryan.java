import java.util.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class Ryan extends Application
{
    @Override
    public void start(Stage primaryStage){
        try{
            BorderPane root = new BorderPane();
            ComboBox<String> cats = new ComboBox<>();
            Label selectCat = new Label("Select a Category!");
            cats.getItems().addAll("Home and Utilities", "Food/Groceries", "Health/Personal Care", "Personal Insurance", "Savings", "Transportation", "Education", "Communication", "Pets", "Shopping and Entertainment", "Emergencies", "Travel", "Miscellaneous", "Other");
            VBox vbox = new VBox(10);
            vbox.getChildren().addAll(selectCat, cats);
            vbox.setPadding(new Insets(60,100,0,100));
            vbox.setAlignment(Pos.TOP_CENTER);
            root.setCenter(vbox);
        
            Scene scene = new Scene(root, 400, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }


}



