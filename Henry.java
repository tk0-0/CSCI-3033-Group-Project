import javafx.scene.paint.*;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.application.Platform;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.event.*;
import javafx.scene.media.*;
import java.io.*;
import javafx.util.Duration;
import javafx.scene.shape.*;

public class Henry extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // First Scene
            /****************************************************/
            VBox vBoxSceneOne = new VBox(20);
            vBoxSceneOne.setAlignment(Pos.CENTER);
            vBoxSceneOne.setStyle("-fx-background-color: #f0f8ff;");


            Circle myCircle = new Circle(150); 
            myCircle.setFill(Color.TRANSPARENT);
            myCircle.setStroke(Color.BLACK);

            

            


            // Image
            Image image = new Image("file:content/2.png");
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            
            imageView.setClip(myCircle);
            
            Circle myCircleTwo = new Circle(5);

            /* 

            // Create a PathTransition to move the ball in a circle
            PathTransition pt = new PathTransition();
            pt.setRate(1);  // Speed of the transition
            pt.setPath(myCircle);  // Set the circular path
            pt.setNode(myCircleTwo);  // Set the ball (circle2) to move along the path
            pt.setCycleCount(Timeline.INDEFINITE);  // Loop indefinitely
            pt.setAutoReverse(true);  // Do not reverse direction
            pt.setDuration(Duration.seconds(3));  // Duration of one full revolution

            pt.play();  // Start the animation
            */
            
            StackPane stackPane = new StackPane(); 
            stackPane.getChildren().addAll(imageView);
            

            // Title Label
            Label sceneOneLabelOne = new Label("Welcome to the Personal Budget Planner");
            sceneOneLabelOne.setFont(Font.font("Arial", FontWeight.BOLD, 19));
            sceneOneLabelOne.setStyle("-fx-text-fill: #333;");

            // Buttons
            Button sceneOneButtonOne = new Button("Start Budget Planning");
            sceneOneButtonOne.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-padding: 10 20;");
            Button sceneOneButtonTwo = new Button("Quit");
            sceneOneButtonTwo.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 10 20;");

            // Adding nodes to the layout
            vBoxSceneOne.getChildren().addAll(stackPane,sceneOneLabelOne, sceneOneButtonOne, sceneOneButtonTwo);
            /****************************************************/

            // Second Scene
            /****************************************************/
            VBox vBoxSceneTwo = new VBox(30);
            vBoxSceneTwo.setAlignment(Pos.CENTER);
            vBoxSceneTwo.setStyle("-fx-background-color: #e8f5e9;");

            // label and combo box
            Label sceneTwoLabelOne = new Label("Select Pay Frequency");
            sceneTwoLabelOne.setFont(Font.font("Arial", FontWeight.BOLD, 18));
            sceneTwoLabelOne.setStyle("-fx-text-fill: #333;");

            ComboBox<String> frequencyComboBox = new ComboBox<>();
            frequencyComboBox.getItems().addAll("Weekly", "Biweekly", "Monthly");
            frequencyComboBox.setStyle("-fx-font-size: 14;");

            // label and text field
            Label incomeLabel = new Label("Enter Income:");
            incomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            incomeLabel.setStyle("-fx-text-fill: #333;");

            TextField incomeField = new TextField();
            incomeField.setPromptText("Enter your income...");
            incomeField.setStyle("-fx-font-size: 14; -fx-padding: 5;");

            HBox inputBox = new HBox(10, incomeLabel, incomeField);
            inputBox.setAlignment(Pos.CENTER);

            // Buttons
            Button sceneTwoButtonOne = new Button("Go Back");
            sceneTwoButtonOne.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; -fx-padding: 10 20;");
            Button sceneTwoButtonTwo = new Button("Confirm");
            sceneTwoButtonTwo.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-padding: 10 20;");

            HBox hBoxSceneTwo = new HBox(20, sceneTwoButtonOne, sceneTwoButtonTwo);
            hBoxSceneTwo.setAlignment(Pos.CENTER);

            vBoxSceneTwo.getChildren().addAll(sceneTwoLabelOne, frequencyComboBox, inputBox, hBoxSceneTwo);
            /****************************************************/

            // Scene Three 
            /****************************************************/
            //Font for the menu
            Font font = new Font("Noteworthy", 15);

            //ComboBoxes for the categories
            ComboBox<String> categories = new ComboBox<>();
            ComboBox<String> subCategories = new ComboBox<>();       
            subCategories.setPrefWidth(200);    subCategories.setVisible(false);

            //Textfields for the custom category and user input amount
            TextField amount = new TextField();
            TextField custom = new TextField();                
            custom.setPrefWidth(200);   custom.setVisible(false);

            //Labels for the Lists and amount
            Label selectCategory = new Label("Select a Category!");           
            selectCategory.setFont(font);

            Label selectSubCategories = new Label("Select a Subcategory!");     
            selectSubCategories.setFont(font);  selectSubCategories.setVisible(false);

            Label amtEnter_message = new Label("Enter Amount:");             
            amtEnter_message.setFont(font);

            //Buttons to continue and add to list
            Button add = new Button("Add to List");      
            add.setFont(font);  add.setPrefWidth(100);
            Button next = new Button("Continue");        
            next.setFont(font); next.setPrefWidth(100);

            //Categories
            categories.getItems().addAll("Home and Utilities", "Food/Groceries", "Health/Personal Care", 
                                         "Personal Insurance", "Savings", "Transportation", 
                                         "Education", "Communication", "Pets", 
                                         "Shopping and Entertainment", "Emergencies", "Travel", 
                                         "Miscellaneous", "Other");

            //Action events for click
            categories.setOnAction(e -> {
                String category = categories.getValue();
                subCategories.getItems().clear();
                custom.setVisible(false);
                selectSubCategories.setVisible(false);
                subCategories.setVisible(false);

                if ("Home and Utilities".equals(category)) { selectSubCategories.setVisible(true); 
                                                             subCategories.setVisible(true); 
                                                             subCategories.getItems().setAll("Rent", "Mortgage", "Water, Electric, etc.", "Home Repair");}
                else if ("Food/Groceries".equals(category)) { selectSubCategories.setVisible(true); 
                                                              subCategories.setVisible(true); 
                                                              subCategories.getItems().setAll("Grocery Shopping", "Fast Food/Restaurant", "Food Delivery"); }
                else if ("Health/Personal Care".equals(category)) { selectSubCategories.setVisible(true); 
                                                                    subCategories.setVisible(true); 
                                                                    subCategories.getItems().setAll("Medicine", "Medical Bill");}
                else if ("Personal Insurance".equals(category)) { selectSubCategories.setVisible(true); 
                                                                  subCategories.setVisible(true);
                                                                  subCategories.getItems().setAll("Health Insurance", "Disability Insurance", "Life Insurance", "Dental Insurance", "Renters Insurance", "Auto Insurance");}
                else if ("Savings".equals(category)) { selectSubCategories.setVisible(false); 
                                                       subCategories.setVisible(false);  
                                                       subCategories.getItems().setAll("");}
                else if ("Transportation".equals(category)) { selectSubCategories.setVisible(true);  
                                                              subCategories.setVisible(true); 
                                                              subCategories.getItems().setAll("Car Payment", "Car Maintenance", "Gas");}
                else if ("Education".equals(category)) { selectSubCategories.setVisible(true); 
                                                         subCategories.setVisible(true); 
                                                         subCategories.getItems().setAll("Tuition", "School Supplies"); }
                else if ("Communication".equals(category)) { selectSubCategories.setVisible(true); 
                                                             subCategories.setVisible(true);  
                                                             subCategories.getItems().setAll("Internet Bill", "Phone Bill", "Phone Payment");}
                else if ("Pets".equals(category)) { selectSubCategories.setVisible(true); 
                                                    subCategories.setVisible(true); 
                                                    subCategories.getItems().setAll("Pet Supplies", "Vet Visit", "Pet Insurance"); }
                else if ("Shopping and Entertainment".equals(category)) { selectSubCategories.setVisible(true); 
                                                                          subCategories.setVisible(true); 
                                                                          subCategories.getItems().setAll("Online Purchase", "In-Person Purchase", "Clothes Shopping", "Streaming Service", "Game"); }
                else if ("Emergencies".equals(category)) { selectSubCategories.setVisible(true);  
                                                           subCategories.setVisible(true); 
                                                           subCategories.getItems().setAll("Funeral", "Family Support");}
                else if ("Travel".equals(category)) { selectSubCategories.setVisible(true); 
                                                      subCategories.setVisible(true);  
                                                      subCategories.getItems().setAll("Lodging", "Plane Ticket", "Car Rental");}
                else if ("Miscellaneous".equals(category)) { selectSubCategories.setVisible(true); 
                                                             subCategories.setVisible(true); 
                                                             subCategories.getItems().setAll("Loan/Debt", "Check", "Withdraw"); }
                else if ("Other".equals(category)) { subCategories.setVisible(false); 
                                                     selectSubCategories.setVisible(false); 
                                                     custom.setVisible(true);}
                    
            }); 
            
            //Creates the category area
            HBox customcat = new HBox(10, custom); 
            customcat.setAlignment(Pos.CENTER);
            VBox category_box = new VBox(10, selectCategory, categories, customcat);  
            category_box.setAlignment(Pos.CENTER);
            VBox subCategory_box = new VBox(10, selectSubCategories, subCategories);   
            subCategory_box.setAlignment(Pos.CENTER);

            //Creates the bottom area 
            HBox amount_area = new HBox(10, amtEnter_message, amount); 
            amount_area.setAlignment(Pos.CENTER);
            HBox bottom_buttons = new HBox(10, add, next); 
            bottom_buttons.setAlignment(Pos.CENTER);

            //Combines the category area and bottom area
            VBox category_areas = new VBox(15, category_box, subCategory_box);
            VBox bottom_area = new VBox(30, amount_area, bottom_buttons);
            VBox menu = new VBox(50, category_areas, bottom_area);
            menu.setAlignment(Pos.CENTER);
            /****************************************************/


            // Scene Four

            VBox vBoxSceneFour = new VBox(); 
            vBoxSceneFour.setAlignment(Pos.CENTER);
            Label sceneFourLabelOne = new Label("Ryans Scene");
            Button sceneFourButtonOne = new Button("Go Back"); 
            


    

            // Audio
            File file = new File("content/money.mp3");
            Media media = new Media(file.toURI().toString()); 
            MediaPlayer player = new MediaPlayer(media);

            // Scenes
            Scene sceneOne = new Scene(vBoxSceneOne, 400, 400);
            Scene sceneTwo = new Scene(vBoxSceneTwo, 400, 400);
            Scene sceneThree = new Scene(menu, 400,400);

            // Button Actions
            sceneOneButtonOne.setOnAction(e -> {
                primaryStage.setScene(sceneTwo);
                player.seek(Duration.millis(1000));
                player.play();

                // reset to the beginning
                player.seek(Duration.ZERO);
            });
            sceneOneButtonTwo.setOnAction(e -> Platform.exit());
            sceneTwoButtonOne.setOnAction(e -> primaryStage.setScene(sceneOne));
            sceneTwoButtonTwo.setOnAction(e -> primaryStage.setScene(sceneThree));
            

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
