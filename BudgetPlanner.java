import javafx.scene.paint.*;
import javafx.animation.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.application.Platform;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.event.*;
import javafx.scene.media.*;
import java.io.*;
import java.util.ArrayList;
import javafx.util.Duration;
import javafx.scene.shape.*;

public class BudgetPlanner extends Application {

    private Scene sceneOne;
    private Scene sceneTwo;

    public double income;
    public double monthlyIncome;
    public String payFrequency;

    public MediaPlayer player;
    public Button sceneOneButtonOne, sceneOneButtonTwo, sceneTwoButtonOne, sceneTwoButtonTwo;
    public ComboBox<String> frequencyComboBox;
    public TextField incomeField;
    public Label errorLabel;

    public ArrayList<String> expenseCategories = new ArrayList<>();
    public ArrayList<String> expenseAmounts = new ArrayList<>();
    public ArrayList<String> expenseSubCategories = new ArrayList<>();

    @Override
    public void start(Stage primaryStage)
    {
        // First Scene
        /****************************************************/
        VBox vBoxSceneOne = new VBox(20);
        vBoxSceneOne.setAlignment(Pos.CENTER);
        vBoxSceneOne.setStyle("-fx-background-color: #f0f8ff;");

        // Image
        Image image = new Image("file:content/6.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        
        
        

        // Title Label
        Text sceneOneTextOne = new Text("Welcome to the Personal Budget Planner");
        StrokeTransition strans = new StrokeTransition(new Duration(3000), sceneOneTextOne, Color.GREEN, Color.WHITE);
        strans.setCycleCount(Timeline.INDEFINITE);
        strans.play();
        sceneOneTextOne.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        sceneOneTextOne.setStyle("-fx-text-fill: #333;");
        sceneOneTextOne.setStrokeWidth(.4);

        Pane paneOne = new Pane();
        String text = "The Personal Budget Planner ";
        double radius = 120; 

        // Adjust the starting angle (180 degrees for the left side)
        double startAngle = 180;

        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);
            // Adjust each character's angle based on the starting angle
            double angle = startAngle + (360.0 / text.length() * i);

            // Calculate x and y position for each letter
            double x = 180 + radius * Math.cos(Math.toRadians(angle));
            double y = 210 + radius * Math.sin(Math.toRadians(angle));

            Text ch = new Text(String.valueOf(letter));
            ch.setFont(Font.font("times new roman", 30));
            ch.setFill(Color.BLACK);
            // Generate random color for each letter
            //ch.setStroke(Color.color(Math.random(), Math.random(), Math.random()));
            ch.setX(x);
            ch.setY(y);
            ch.setRotate(angle + 90); // Align the rotation to the tangent of the circle
            paneOne.getChildren().add(ch);
        }


        VBox vBoxTitle = new VBox();

        vBoxTitle.getChildren().addAll(paneOne);
        vBoxTitle.setTranslateY(-50);

        StackPane stackPane = new StackPane(); 
        stackPane.getChildren().addAll(imageView,vBoxTitle);

        RotateTransition rtrans  = new RotateTransition(new Duration(5000), imageView); 
        rtrans.setFromAngle(0); 
        rtrans.setToAngle(360);
        rtrans.setAutoReverse(false);
        rtrans.setInterpolator(Interpolator.LINEAR);
        rtrans.setCycleCount(Timeline.INDEFINITE);
        rtrans.play();    

        // Buttons
        sceneOneButtonOne = new Button("Start Budget Planning");
        sceneOneButtonOne.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-padding: 10 20;");
        sceneOneButtonTwo = new Button("Quit");
        sceneOneButtonTwo.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 10 20;");

        // Adding nodes to the layout
        vBoxSceneOne.getChildren().addAll(stackPane, sceneOneButtonOne, sceneOneButtonTwo);
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
        frequencyComboBox.getItems().addAll("Weekly", "Bi-Weekly", "Monthly");
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

        errorLabel = new Label("Please fill in all information with valid info!"); 
        errorLabel.setVisible(false);
        
        vBoxSceneTwo.getChildren().addAll(sceneTwoLabelOne, frequencyComboBox, inputBox, hBoxSceneTwo, errorLabel);
        
        /****************************************************/
        
        // Scenes
        sceneOne = new Scene(vBoxSceneOne, 400, 400);
        sceneTwo = new Scene(vBoxSceneTwo, 400, 400);

        // Audio Files
        File file = new File("content/money1.mp3");
        Media media = new Media(file.toURI().toString()); 
        MediaPlayer player = new MediaPlayer(media);

        // Button Actions
        sceneOneButtonOne.setOnAction(e -> {
            primaryStage.setScene(sceneTwo);
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
            incomeField.setText("");
            frequencyComboBox.setValue(null);
        });

        // Set the initial scene and show the stage
        primaryStage.setScene(sceneOne);
        
        primaryStage.setTitle("Personal Budget Planner");
        primaryStage.show();










        // Ryan's Code
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
        selectCategory.setStyle("-fx-font-size: 15px Arial; -fx-font-weight: bold;");

        Label selectSubCategories = new Label("Select a Subcategory!");     
        selectSubCategories.setStyle("-fx-font-size: 15px Arial; -fx-font-weight: bold;");
        selectSubCategories.setVisible(false);

        Label amtEnter_message = new Label("Enter Amount:");  
        amtEnter_message.setStyle("-fx-font-size: 15px Arial; -fx-font-weight: bold;"); 
        
        Label errorMessage = new Label("Please Enter Valid Information!!");
        errorMessage.setVisible(false);

        //Buttons to continue and add to list
        Button add = new Button("Add");      
        add.setPrefWidth(90);
        add.setPrefHeight(15);
        add.setStyle("-fx-background-color: #ffa500; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");

        Button next = new Button("Continue");        
        next.setPrefWidth(90);
        next.setPrefHeight(15);
        next.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");

        Button reset = new Button("Reset");
        reset.setPrefWidth(90);
        reset.setPrefHeight(15);
        reset.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");

        Button back = new Button("Go Back");
        back.setPrefWidth(90);
        back.setPrefHeight(15);
        back.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");
        //Categories
        categories.getItems().addAll(   "Home and Utilities", "Food/Groceries", "Health/Personal Care", 
                                        "Personal Insurance", "Savings", "Transportation", 
                                        "Education", "Communication", "Pets", 
                                        "Shopping and Entertainment", "Emergencies", "Travel", 
                                        "Miscellaneous", "Other"    );

        //Action events for Category Combo Box
        categories.setOnAction(e -> {
            String category = categories.getValue();
            subCategories.getItems().clear();
            custom.setVisible(false);
            selectSubCategories.setVisible(false);
            subCategories.setVisible(false);

            //String Comparisons
            if ("Home and Utilities".equals(category)) {    selectSubCategories.setVisible(true); 
                                                            subCategories.setVisible(true); 
                                                            subCategories.getItems().setAll("Rent", "Mortgage", "Water, Electric, etc.", "Home Repair");    }

            else if ("Food/Groceries".equals(category)) {   selectSubCategories.setVisible(true); 
                                                            subCategories.setVisible(true); 
                                                            subCategories.getItems().setAll("Grocery Shopping", "Fast Food/Restaurant", "Food Delivery");   }

            else if ("Health/Personal Care".equals(category)) { selectSubCategories.setVisible(true); 
                                                                subCategories.setVisible(true); 
                                                                subCategories.getItems().setAll("Medicine", "Medical Bill");    }

            else if ("Personal Insurance".equals(category)) {   selectSubCategories.setVisible(true); 
                                                                subCategories.setVisible(true);
                                                                subCategories.getItems().setAll("Health Insurance", "Disability Insurance", "Life Insurance", "Dental Insurance", "Renters Insurance", "Auto Insurance");   }

            else if ("Savings".equals(category)) {  selectSubCategories.setVisible(false); 
                                                    subCategories.setVisible(false);  
                                                    subCategories.getItems().setAll("");
                                                    subCategories.setValue(" ");    }

            else if ("Transportation".equals(category)) {   selectSubCategories.setVisible(true);  
                                                            subCategories.setVisible(true); 
                                                            subCategories.getItems().setAll("Car Payment", "Car Maintenance", "Gas");   }

            else if ("Education".equals(category)) {    selectSubCategories.setVisible(true); 
                                                        subCategories.setVisible(true); 
                                                        subCategories.getItems().setAll("Tuition", "School Supplies");  }

            else if ("Communication".equals(category)) {    selectSubCategories.setVisible(true); 
                                                            subCategories.setVisible(true);  
                                                            subCategories.getItems().setAll("Internet Bill", "Phone Bill", "Phone Payment");    }

            else if ("Pets".equals(category)) { selectSubCategories.setVisible(true); 
                                                subCategories.setVisible(true); 
                                                subCategories.getItems().setAll("Pet Supplies", "Vet Visit", "Pet Insurance");  }

            else if ("Shopping and Entertainment".equals(category)) {   selectSubCategories.setVisible(true); 
                                                                        subCategories.setVisible(true); 
                                                                        subCategories.getItems().setAll("Online Purchase", "In-Person Purchase", "Clothes Shopping", "Streaming Service", "Game");  }

            else if ("Emergencies".equals(category)) {  selectSubCategories.setVisible(true);  
                                                        subCategories.setVisible(true); 
                                                        subCategories.getItems().setAll("Funeral", "Family Support");   }

            else if ("Travel".equals(category)) {   selectSubCategories.setVisible(true); 
                                                    subCategories.setVisible(true);  
                                                    subCategories.getItems().setAll("Lodging", "Plane Ticket", "Car Rental");   }

            else if ("Miscellaneous".equals(category)) {    selectSubCategories.setVisible(true); 
                                                            subCategories.setVisible(true); 
                                                            subCategories.getItems().setAll("Loan/Debt", "Check", "Withdraw");  }

            else if ("Other".equals(category)) {    subCategories.setVisible(false); 
                                                    selectSubCategories.setVisible(false); 
                                                    custom.setVisible(true);
                                                    subCategories.setValue(custom.getText());   }
            
            //Action event for add
            add.setOnAction(x -> {
                String selectedCategory = categories.getValue();
                String subCategory;
                String enteredAmount = amount.getText();
                try{ 
                    if (!amount.getText().isEmpty() && categories.getValue() != null && subCategories.getValue() != null && Double.parseDouble(amount.getText()) >= 0.0){
                        if (custom.isVisible()) {
                            subCategory = custom.getText();
                        }
                        else {
                            subCategory = subCategories.getValue();
                        }
                    
                        if (selectedCategory != null && !enteredAmount.isEmpty()) {
                            expenseCategories.add(selectedCategory);

                            if (subCategory != null) {
                                expenseSubCategories.add(subCategory);
                            } 
                            else {
                                expenseSubCategories.add("");
                            }

                            expenseAmounts.add(enteredAmount);
                    
                            categories.setValue(null);
                            subCategories.setValue(null);
                            custom.clear();
                            amount.clear();
                            subCategories.setVisible(false);
                            custom.setVisible(false);
                            errorMessage.setVisible(false);
                        }
                    }
                    else {
                        errorMessage.setVisible(true);
                    }
                }   catch (NumberFormatException ex) {
                        errorMessage.setText("Please Enter Valid Information!");
                        errorMessage.setVisible(true);
                    }
            });

            reset.setOnAction(y -> {
                expenseAmounts.clear();
                expenseCategories.clear();
                expenseSubCategories.clear();

                categories.setValue(null);
                subCategories.setValue(null);
                custom.clear();
                amount.clear();
                subCategories.setVisible(false);
                custom.setVisible(false);
                errorMessage.setVisible(false);
            });
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
        HBox bottom_buttons = new HBox(10, back, reset, add, next); 
        bottom_buttons.setAlignment(Pos.CENTER);

        //Combines the category area and bottom area
        VBox category_areas = new VBox(15, category_box, subCategory_box);
        VBox bottom_area = new VBox(50, amount_area, bottom_buttons);
        VBox menu = new VBox(50, category_areas, bottom_area);

        VBox updated_menu = new VBox(10, menu, errorMessage);
        updated_menu.setAlignment(Pos.CENTER);

        //Menu for the Second Scene
        //Make a VBox for the Scenes
        VBox expensesList = new VBox(10);
        expensesList.setPadding(new Insets(5));
        expensesList.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane(expensesList);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(375, 240);
        scrollPane.setMaxWidth(375);
        scrollPane.setStyle("-fx-background-color: #696969;");

        Label expenseList = new Label("Your Expenses: ");
        expenseList.setStyle("-fx-font: 20px Arial; -fx-font-weight: bold;");
        Button calculateExpenses = new Button("Show Recommendations!");
        calculateExpenses.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");
        Button goBack = new Button("Go Back");
        goBack.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");

        HBox bottomButtons = new HBox(10, goBack, calculateExpenses);
        bottomButtons.setAlignment(Pos.CENTER);
        VBox list_of_expenses = new VBox(5, expenseList, scrollPane);
        list_of_expenses.setAlignment(Pos.CENTER);
        VBox expenseOutput = new VBox(45, list_of_expenses, bottomButtons);
        expenseOutput.setAlignment(Pos.CENTER);

        //Background color for the Scenes
        Scene sceneThree = new Scene(updated_menu, 400, 400);
        updated_menu.setStyle("-fx-background-color: #f0f8ff;");
        Scene sceneFour = new Scene(expenseOutput, 400, 400);
        expenseOutput.setStyle("-fx-background-color: #f0f8ff;");

        //Action event for the continue button
        next.setOnAction(p -> {
            //Clear the VBox list if it has elements already
            expensesList.getChildren().clear();
            //For loop to add each expense to the VBox list
            for (int i = 0; i < expenseCategories.size(); i++) {
                Label expenseLabel = new Label(expenseCategories.get(i) + ":  " + expenseSubCategories.get(i) + "     $" + expenseAmounts.get(i));
                expenseLabel.setStyle("-fx-font-family: 'Brush Script MT', cursive; -fx-font-size: 15px;");
                expensesList.getChildren().add(expenseLabel);
            }

            //Show sceneFour
            primaryStage.setScene(sceneFour);
            primaryStage.show();
        });

        back.setOnAction(l -> {
            primaryStage.setScene(sceneTwo);
        });

        //Action event for the go back button in the 4th scene
        goBack.setOnAction(o -> {
            //Show sceneThree
            primaryStage.setScene(sceneThree);
            primaryStage.show();
        });

        // Henry's Confirm button
        sceneTwoButtonTwo.setOnAction(e -> {
            try {
                if (!incomeField.getText().isEmpty() && frequencyComboBox.getValue() != null && Double.parseDouble(incomeField.getText()) >= 0.0) {
                    // Try to parse income as a Double
                    income = Double.parseDouble(incomeField.getText()); 
                    payFrequency = new String(frequencyComboBox.getValue());
                    monthlyIncome = CalculateMonthlyAverage();
        
                    // Goes back to the first scene
                    primaryStage.setScene(sceneThree);
                }
                else {
                    errorLabel.setVisible(true); 
                }
            } catch (NumberFormatException ex) {
                // If parsing fails, show error for invalid income input
                errorLabel.setText("Please enter a valid number for income.");
                errorLabel.setVisible(true);
            }
        });
    }

    public double CalculateMonthlyAverage() {
        double monthlyAverage = 0.0;

        if(payFrequency.equals("Weekly"))
            monthlyAverage = income * 4;
        else if(payFrequency.equals("Bi-Weekly"))
            monthlyAverage = income * 2;
        else
            monthlyAverage = income;

        return monthlyAverage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
