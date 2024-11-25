/*
 * Authors:    Tyler Kramer, Henry Ngo, Zachary Taylor, Ryan Thieu 
 * Class:      CSCI 3033-001
 * Instructor: Dr. Rafet Al-Tobasei
 * Project:    Group Project
 * Purpose:    The program allows a user to enter their income and how much 
 *             they spend on different things. The program then offers recommendations 
 *             on how the user’s expenditures can be managed and improved, if needed.
*/

// Various import statements
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.util.Duration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.scene.paint.*;
import javafx.animation.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.scene.media.*;
import java.io.*;
import javafx.scene.shape.*;
import java.util.*;

public class BudgetPlanner extends Application
{
    // All Scenes
    private Scene sceneOne;   // Welcome Menu
    private Scene sceneTwo;   // Enter Income
    private Scene sceneThree; // Add Expenses
    private Scene sceneFour;  // Expense Review
    private Scene sceneFive;  // Budget Plans Viewer

    // All main categories
    private final String[] mainCategories = {"Home and Utilities", "Food/Groceries", "Health/Personal Care", "Personal Insurance", "Transportation", "Emergencies", "Education", "Communication", "Pets", "Shopping and Entertainment", "Travel", "Miscellaneous", "Other", "Savings"};

    // Various income-related information
    private double income;        // amount entered by user
    private double monthlyIncome; // monthly income calculated from income entered by user
    private String payFrequency;  // pay frequency selected by user
    private double totalAmount;   // total amount of expenses

    // Lists of various expense information
    private ArrayList<String> expenseCategories = new ArrayList<>();    // list of main categories of user-entered expense entries
    private ArrayList<String> expenseSubCategories = new ArrayList<>(); // list of subcategories of user-entered expense entries
    private ArrayList<Double> expenseAmounts = new ArrayList<>();       // list of amounts of user-entered expense entries
    private HashMap<Integer,Double> expenseChanges = new HashMap<>();   // hash map for keeping track of which expenses are changed
                                                                        // links index of expenses to the amount needed to cutback
    private double[] expenseItems;                                      // holds the total number of expenses for each main category

    // Data used in pie chart
    private ObservableList<PieChart.Data> pieChartData;      // info on slices in pie chart
    private ArrayList<Expense> expenses = new ArrayList<>(); // list of labels and amounts for slices
    
    // Scene 5 Nodes
    // Used in different scopes, so made private global for ease of use in current file
    private Button backButton5_4 = new Button("Go Back"); // button to go back to scene 4
    private HBox hBoxBottom = new HBox(20);                    // hbox to hold buttons at bottom of pane
    private BorderPane root = new BorderPane();                // root pane for scene 5

    @Override
    public void start(Stage primaryStage)
    {
        // Scene One
        /****************************************************/
        // Creates Audio File
        File file = new File("content/BackgroundMusic.mp3");
        Media media = new Media(file.toURI().toString());
        MediaPlayer player = new MediaPlayer(media);

        Button startButton, quitButton1; // Buttons used in scene 1
        
        // holds all nodes for scene 1
        VBox vBoxSceneOne = new VBox(20);
        vBoxSceneOne.setAlignment(Pos.CENTER);

        // Image of Money Bag
        Image image = new Image("file:content/MoneyBag.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(130);
        imageView.setFitWidth(130);

        // Image of MT Logo for background
        Image image2 = new Image("file:content/MTLogo.png"); 
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitHeight(900);
        imageView2.setFitWidth(900);
        imageView2.setOpacity(0.05);

        StackPane stackPane = new StackPane(); // pane for menu screen (text pattern)
        stackPane.setAlignment(Pos.CENTER);    // center everything in the StackPane

        // Creates title (text to be wrapped into a circle)
        String text = "The Personal Budget Planner ";

        // set radius and starting angle for text in a circle
        double radius = 110; 
        double startAngle = 180; 

        // Creates text going around in a circle
        for(int i = 0; i < text.length(); i++)
        {
            char letter = text.charAt(i);
            double angle = startAngle + (360.0 / text.length() * i);

            // calculates position relative to center
            double x = radius * Math.cos(Math.toRadians(angle));
            double y = radius * Math.sin(Math.toRadians(angle));

            // creates individual Text node for each letter
            Text ch = new Text(String.valueOf(letter));
            ch.setFont(Font.font("Arial", 30));
            ch.setFill(Color.BLACK);
            ch.setRotate(angle + 90); // Rotate each letter to align with the circle
            ch.setTranslateX(x);      // Position relative to center
            ch.setTranslateY(y);      // Position relative to center

            // adds letter directly to StackPane
            stackPane.getChildren().add(ch);
        }

        // Creates circle for menu design
        Circle sceneOneCircleOne = new Circle(90);
        sceneOneCircleOne.setFill(null);
        sceneOneCircleOne.setStroke(Color.BLACK);
        sceneOneCircleOne.setStrokeWidth(2);
        
        // Creates animation for money bag image on menu
        FadeTransition ftrans = new FadeTransition(new Duration(2000), imageView);
        ftrans.setFromValue(1); 
        ftrans.setCycleCount(Timeline.INDEFINITE);
        ftrans.setToValue(0.5);
        ftrans.setAutoReverse(true);
        ftrans.play();

        // Creates circle for menu design
        Circle sceneOneCircleTwo = new Circle(4);
        sceneOneCircleTwo.setStroke(Color.BLACK);

        // Creates path for circles in the menu design
        PathTransition pt = new PathTransition();
        pt.setDuration(Duration.millis(5000));
        pt.setPath(sceneOneCircleOne);
        pt.setNode(sceneOneCircleTwo);
        pt.setCycleCount(Timeline.INDEFINITE);
        pt.setInterpolator(Interpolator.LINEAR);
        pt.setAutoReverse(false);
        pt.play();

        // Makes smaller circle in menu design change colors
        FillTransition filler = new FillTransition(new Duration(2000), sceneOneCircleTwo, Color.GREEN, Color.YELLOW);
        filler.setCycleCount(Timeline.INDEFINITE);
        filler.setAutoReverse(true);
        filler.play();

        // adds nodes to StackPane to create menu design
        stackPane.getChildren().add(0, imageView);
        stackPane.getChildren().addAll(sceneOneCircleOne, sceneOneCircleTwo);

        // start and quit buttons given text and styles
        startButton = new Button("Start Budget Planning");
        startButton.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");
        quitButton1 = new Button("Quit");
        quitButton1.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");

        // Makes start button have a pulsing (scaling) animation
        ScaleTransition scale = new ScaleTransition(new Duration(2000), startButton);
        scale.setFromX(1);
        scale.setFromY(1);
        scale.setToX(1.2);
        scale.setToY(1.2);
        scale.setCycleCount(Timeline.INDEFINITE);
        scale.setAutoReverse(true);
        scale.play();

        // Adds nodes to the layout
        vBoxSceneOne.getChildren().addAll(stackPane, startButton, quitButton1);
        vBoxSceneOne.setPadding(new Insets(20, 0, 0, 0));
        VBox.setMargin(startButton, new Insets(30, 0, 0, 0));

        // Creates StackPane that holds all nodes and background image
        StackPane stackPaneOne = new StackPane(); 
        stackPaneOne.getChildren().addAll(imageView2, vBoxSceneOne);

        // sets scene 1
        sceneOne = new Scene(stackPaneOne, 1000, 500);

        // sets the initial scene and show the stage
        primaryStage.setScene(sceneOne);
        
        // sets stage title
        primaryStage.setTitle("Personal Budget Planner");

        // shows stage
        primaryStage.show();
        /****************************************************/

        // Scene Two
        /****************************************************/
        Button backButton2_1, confirmButton; // Buttons used in scene 2

        // holds all nodes for scene 2
        VBox vBoxSceneTwo = new VBox(30);
        vBoxSceneTwo.setAlignment(Pos.CENTER);

        // Label created for selecting pay period
        Label sceneTwoLabelOne = new Label("Select Pay Frequency");
        sceneTwoLabelOne.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        sceneTwoLabelOne.setStyle("-fx-text-fill: #333;");

        // ComboBox for selecting pay period
        ComboBox<String> frequencyComboBox = new ComboBox<>();
        frequencyComboBox.getItems().addAll("Weekly", "Bi-Weekly", "Monthly", "Yearly");
        frequencyComboBox.setStyle("-fx-font-size: 14;");

        // Label for entering income
        Label incomeLabel = new Label("Enter Income:");
        incomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        incomeLabel.setStyle("-fx-text-fill: #333;");

        // TextField to enter income
        TextField incomeField = new TextField();
        incomeField.setPromptText("Enter your income...");
        incomeField.setStyle("-fx-font-size: 14; -fx-padding: 5;");

        // HBox that holds income Label and income TextField
        HBox inputBox = new HBox(10, incomeLabel, incomeField);
        inputBox.setAlignment(Pos.CENTER);

        // back and confirm buttons given text and styles
        backButton2_1 = new Button("Go Back");
        backButton2_1.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");
        confirmButton = new Button("Confirm");
        confirmButton.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");

        // HBox that holds back and confirm buttons
        HBox hBoxSceneTwo = new HBox(20, backButton2_1, confirmButton);
        hBoxSceneTwo.setAlignment(Pos.CENTER);

        // Creates error Label
        Label errorLabel = new Label("Please Enter All Fields With Valid Info!");
        errorLabel.setStyle("-fx-font-weight: bold");
        errorLabel.setVisible(false);
        
        // Adds nodes to the layout
        vBoxSceneTwo.getChildren().addAll(sceneTwoLabelOne, frequencyComboBox, inputBox, hBoxSceneTwo, errorLabel);

        // Image of MT Logo for background
        ImageView imageView3 = new ImageView(image2);
        imageView3.setFitHeight(900);
        imageView3.setFitWidth(900);
        imageView3.setOpacity(0.05);

        // Creates StackPane that holds all nodes and background image
        StackPane stackPaneTwo = new StackPane(); 
        stackPaneTwo.getChildren().addAll(imageView3,vBoxSceneTwo);
        
        // sets scene 2
        sceneTwo = new Scene(stackPaneTwo, 1000, 500);
        /****************************************************/

        // Scene Three
        /****************************************************/
        // ComboBoxes for the categories
        ComboBox<String> categories = new ComboBox<>();    // ComboBox for Main Categories
        ComboBox<String> subCategories = new ComboBox<>(); // ComboBox for the Sub Categories
        subCategories.setPrefWidth(200);                   // Setting Width
        subCategories.setVisible(false);                   // Changing Visibility

        // Textfields for the custom category and user input amount
        TextField amount = new TextField(); // TextField for user to enter Expense Amount
        TextField custom = new TextField(); // TextField for user to enter Custom Expense Category
        custom.setPrefWidth(200);           // Setting Width
        custom.setVisible(false);           // Changing Visibility

        // Labels for the Category ComboBoxes and User Input TextFields
        Label selectCategory = new Label("Select a Category!");
        selectCategory.setStyle("-fx-font-size: 15px Arial; -fx-font-weight: bold;");

        Label selectSubCategories = new Label("Select a Subcategory!");
        selectSubCategories.setStyle("-fx-font-size: 15px Arial; -fx-font-weight: bold;");
        selectSubCategories.setVisible(false);

        Label enterAmount = new Label("Enter Amount:");
        enterAmount.setStyle("-fx-font-size: 15px Arial; -fx-font-weight: bold;");

        // Labels for status messages for valid expense / invalid expense
        Label statusMessage1 = new Label();
        statusMessage1.setStyle("-fx-font-size: 15px Arial;");
        statusMessage1.setVisible(false);
        
        Label statusMessage2 = new Label();
        statusMessage2.setStyle("-fx-font-weight: bold");
        statusMessage2.setVisible(false);

        // Buttons for scene 3: Adding to List, Next Button to scene 4, Reset Button, Go Back to scene 2 Button
        Button addButton = new Button("Add");
        addButton.setPrefWidth(90);
        addButton.setPrefHeight(15);
        addButton.setStyle("-fx-background-color: #ffa500; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");

        Button nextButton = new Button("Continue");
        nextButton.setPrefWidth(95);
        nextButton.setPrefHeight(15);
        nextButton.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");

        Button resetButton1 = new Button("Reset");
        resetButton1.setPrefWidth(90);
        resetButton1.setPrefHeight(15);
        resetButton1.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");

        Button backButton3_2 = new Button("Go Back");
        backButton3_2.setPrefWidth(90);
        backButton3_2.setPrefHeight(15);
        backButton3_2.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");

        // Add all of the main cateogories to the main category ComboBox
        categories.getItems().addAll(Arrays.asList(mainCategories));  

        // Formats the Custom User Expense Textfield
        HBox customCat = new HBox(10, custom);
        customCat.setAlignment(Pos.CENTER);

        // Combines the main category ComboBox and Labels
        VBox categoryBox = new VBox(10, selectCategory, categories, customCat);
        categoryBox.setAlignment(Pos.CENTER);

        // Combines subcategory Label and ComboBox
        VBox subCategoryBox = new VBox(10, selectSubCategories, subCategories);
        subCategoryBox.setAlignment(Pos.CENTER);

        // Combines User Expense Amount input label and TextField
        HBox amountArea = new HBox(10, enterAmount, amount);
        amountArea.setAlignment(Pos.CENTER);

        // Combines the User Expense Inputs with the status messages
        VBox amountWithStatus = new VBox(15, amountArea, statusMessage1);
        amountWithStatus.setAlignment(Pos.CENTER);

        // Formats the Buttons for scene 3 to be placed at the bottom
        HBox bottomButtonsSceneThree = new HBox(10, backButton3_2, resetButton1, addButton, nextButton);
        bottomButtonsSceneThree.setAlignment(Pos.CENTER);

        // Combines the main category and subcategory ComboBoxes
        VBox categoryAreas = new VBox(15, categoryBox, subCategoryBox);

        // Combines the User Expense Amount and Buttons of scene 3
        VBox bottomArea = new VBox(30, amountWithStatus, bottomButtonsSceneThree);

        // Combines the category and the bottom area
        VBox menu = new VBox(50, categoryAreas, bottomArea);

        // Combines the whole menu with another status message for the user to create scene 3
        VBox updatedMenu = new VBox(10, menu, statusMessage2);
        updatedMenu.setAlignment(Pos.CENTER);
        
        // Image of MT Logo for background
        ImageView imageView4 = new ImageView(image2);
        imageView4.setFitHeight(900);
        imageView4.setFitWidth(900);
        imageView4.setOpacity(0.05);

        // Creates StackPane that holds all nodes and background image
        StackPane stackPaneThree = new StackPane(); 
        stackPaneThree.getChildren().addAll(imageView4,updatedMenu);

        // sets scene 3
        sceneThree = new Scene(stackPaneThree, 1000, 500);
        /****************************************************/

        // Scene Four
        /****************************************************/
        // Makes a VBox for nodes in scene 4
        VBox expensesList = new VBox(10);
        expensesList.setPadding(new Insets(5));
        expensesList.setAlignment(Pos.CENTER);

        // ScrollPane holds the list of expenses
        ScrollPane scrollPane = new ScrollPane(expensesList);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(375, 240);
        scrollPane.setMaxWidth(375);
        scrollPane.setStyle("-fx-background-color: #696969;");

        // Header for the expense list in scene 4
        Label expenseList = new Label("Your Expenses: ");
        expenseList.setStyle("-fx-font: 20px Arial; -fx-font-weight: bold;");

        // Shows Recommendations Button for scene 4 to go to the next scene
        Button recommendationsButton = new Button("Show Recommendations!");
        recommendationsButton.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");

        // Go Back Button for scene 4 to go to scene 3
        Button backButton4_3 = new Button("Go Back");
        backButton4_3.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");

        // Formats the buttons to be placed at the bottom of scene 4
        HBox bottomButtonsSceneFour = new HBox(10, backButton4_3, recommendationsButton);
        bottomButtonsSceneFour.setAlignment(Pos.CENTER);

        // Formats the Expenses List VBox
        VBox listOfExpenses = new VBox(5, expenseList, scrollPane);
        listOfExpenses.setAlignment(Pos.CENTER);

        // Combines the list of expenses and the Buttons of scene 4
        VBox expenseOutput = new VBox(45, listOfExpenses, bottomButtonsSceneFour);
        expenseOutput.setAlignment(Pos.CENTER);

        // Image of MT Logo for background
        ImageView imageView5 = new ImageView(image2);
        imageView5.setFitHeight(900);
        imageView5.setFitWidth(900);
        imageView5.setOpacity(0.05);

        // Creates StackPane that holds all nodes and background image
        StackPane stackPaneFour = new StackPane(); 
        stackPaneFour.getChildren().addAll(imageView5,expenseOutput);

        // sets scene 4
        sceneFour = new Scene(stackPaneFour, 1000, 500);
        /****************************************************/

        // Scene Five
        /****************************************************/
        // Buttons created for each Budget Plan and Recommendation for the user
        Button pie1 = new Button("Budget Plan #1");            
        Button list1 = new Button("Show Recommendations");
        Button pie2 = new Button("Budget Plan #2");
        Button list2 = new Button("Show Recommendations");
        Button pie3 = new Button("Budget Plan #3");
        Button list3 = new Button("Show Recommendations");

        // Button style formatting for each plan and recommendation
        pie1.setPrefHeight(15);
        pie1.setStyle("-fx-background-color: #994fb2; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");
        pie2.setPrefHeight(15);
        pie2.setStyle("-fx-background-color: #f58024; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");
        pie3.setPrefHeight(15);
        pie3.setStyle("-fx-background-color: #7bb662; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");
        list1.setStyle("-fx-background-color: #6fc0db; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");
        list2.setStyle("-fx-background-color: #6fc0db; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");
        list3.setStyle("-fx-background-color: #6fc0db; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");

        VBox vbox1 = new VBox(5, pie1, list1); // Box together the First Pie Chart and its List
        vbox1.setAlignment(Pos.CENTER);
        VBox vbox2 = new VBox(5, pie2, list2); // Box together the Second Pie Chart and its List
        vbox2.setAlignment(Pos.CENTER);
        VBox vbox3 = new VBox(5, pie3, list3); // Box together the Third Pie Chart and its List
        vbox3.setAlignment(Pos.CENTER);

        // Quit Button created for user to exit program
        Button quitButton2 = new Button("Quit");
        quitButton2.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");

        // Boxes all of the Buttons together
        HBox hBoxTop = new HBox(20, vbox1, vbox2, vbox3, quitButton2);

        // Sets the Buttons at the top of the scene
        root.setTop(hBoxTop);
        hBoxTop.setAlignment(Pos.CENTER);

        // Creates a Button to remove the current pie chart/list off the screen
        Button resetButton = new Button("Clear");

        // Button Style Formatting
        backButton5_4.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");
        resetButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");

        // Formatting the Back and Clear buttons to the bottom left corner of scene 5
        hBoxBottom.getChildren().add(backButton5_4);
        root.setBottom(hBoxBottom);
        hBoxBottom.setAlignment(Pos.BOTTOM_LEFT);
        hBoxBottom.setPadding(new Insets(20));

        // Image of MT Logo for background
        ImageView imageView6 = new ImageView(image2);
        imageView6.setFitHeight(900);
        imageView6.setFitWidth(900);
        imageView6.setOpacity(0.05);

        // Creates StackPane that holds all nodes and background image
        StackPane stackPaneFive = new StackPane(); 
        stackPaneFive.getChildren().addAll(imageView6,root);

        // sets scene 5
        sceneFive = new Scene(stackPaneFive, 1000, 500);
        /****************************************************/



        // Scene 1 Events
        /****************************************************/
        // Program Start Button
        startButton.setOnAction(e -> {
            // Sets to Scene 2 and Plays Music
            primaryStage.setScene(sceneTwo);  

            player.play();
            player.setCycleCount(Timeline.INDEFINITE);
        });

        // Exit Button
        quitButton1.setOnAction(e -> Platform.exit());
        /****************************************************/



        // Scene 2 Events
        /****************************************************/
        // Confirm Button to Transition
        confirmButton.setOnAction(e -> {
            try {
                // input validation to make sure nothing is empty / there is a valid income
                if(!incomeField.getText().isEmpty() && frequencyComboBox.getValue() != null && Double.parseDouble(incomeField.getText()) >= 0.0)
                {
                    // parses income as a Double
                    income = Double.parseDouble(incomeField.getText());
                    payFrequency = new String(frequencyComboBox.getValue());
                    monthlyIncome = CalculateMonthlyAverage();

                    // sets to scene 3
                    statusMessage2.setVisible(false);
                    statusMessage1.setVisible(false);
                    primaryStage.setScene(sceneThree);
                }
                // invalid input(s)
                else
                {
                    errorLabel.setVisible(true);
                }
            } catch (NumberFormatException ex) {
                // If parsing fails, show error for invalid income input
                errorLabel.setText("Please enter a valid number for income.");
                errorLabel.setVisible(true);
            }
        });
        /****************************************************/



        // Scene 3 Events
        /****************************************************/
        // Action events for Category Combo Box
        categories.setOnAction(e -> {
            // Get value of category ComboBox
            String category = categories.getValue();

            // Make sure subcategory is not visible until main category is selected
            selectSubCategories.setVisible(false);
            subCategories.setVisible(false);
            subCategories.getItems().clear();

            // Make sure other TextField is not visible until "other" is selected
            custom.setVisible(false);

            // String comparisons for the  main categories
            // Shows the subcategories and their correlated expenses
            if("Home and Utilities".equals(category)) {     selectSubCategories.setVisible(true);
                                                            subCategories.setVisible(true);
                                                            subCategories.getItems().setAll("Rent", "Mortgage", "Water, Electric, etc.", "Home Repair");     }

            else if("Food/Groceries".equals(category)) {    selectSubCategories.setVisible(true);
                                                            subCategories.setVisible(true);
                                                            subCategories.getItems().setAll("Grocery Shopping", "Fast Food/Restaurant", "Food Delivery");    }

            else if("Health/Personal Care".equals(category)) {  selectSubCategories.setVisible(true);
                                                                subCategories.setVisible(true);
                                                                subCategories.getItems().setAll("Medicine", "Medical Bill");  }

            else if("Personal Insurance".equals(category)) {    selectSubCategories.setVisible(true);
                                                                subCategories.setVisible(true);
                                                                subCategories.getItems().setAll("Health Insurance", "Disability Insurance", "Life Insurance", "Dental Insurance", "Renters Insurance", "Auto Insurance");    }

            else if("Savings".equals(category)) {   selectSubCategories.setVisible(false);
                                                    subCategories.setVisible(false);
                                                    subCategories.getItems().setAll("");
                                                    subCategories.setValue(" ");   }

            else if("Transportation".equals(category)) {    selectSubCategories.setVisible(true);
                                                            subCategories.setVisible(true);
                                                            subCategories.getItems().setAll("Car Payment", "Car Maintenance", "Gas");    }

            else if("Education".equals(category)) {     selectSubCategories.setVisible(true);
                                                        subCategories.setVisible(true);
                                                        subCategories.getItems().setAll("Tuition", "School Supplies");     }

            else if("Communication".equals(category)) {     selectSubCategories.setVisible(true);
                                                            subCategories.setVisible(true);
                                                            subCategories.getItems().setAll("Internet Bill", "Phone Bill", "Phone Payment");     }

            else if("Pets".equals(category)) {  selectSubCategories.setVisible(true);
                                                subCategories.setVisible(true);
                                                subCategories.getItems().setAll("Pet Supplies", "Vet Visit", "Pet Insurance");  }

            else if("Shopping and Entertainment".equals(category)) {    selectSubCategories.setVisible(true);
                                                                        subCategories.setVisible(true);
                                                                        subCategories.getItems().setAll("Online Purchase", "In-Person Purchase", "Clothes Shopping", "Streaming Service", "Game");    }

            else if("Emergencies".equals(category)) {   selectSubCategories.setVisible(true);
                                                        subCategories.setVisible(true);
                                                        subCategories.getItems().setAll("Funeral", "Family Support");   }

            else if("Travel".equals(category)) {    selectSubCategories.setVisible(true);
                                                    subCategories.setVisible(true);
                                                    subCategories.getItems().setAll("Lodging", "Plane Ticket", "Car Rental");    }

            else if("Miscellaneous".equals(category)) {     selectSubCategories.setVisible(true);
                                                            subCategories.setVisible(true);
                                                            subCategories.getItems().setAll("Loan/Debt", "Check", "Withdraw");     }

            else if("Other".equals(category)) {     subCategories.setVisible(false);
                                                    selectSubCategories.setVisible(false);
                                                    custom.setVisible(true);
                                                    subCategories.setValue(custom.getText());     }
            
            // Action event for adding an expense
            addButton.setOnAction(x -> {
                // Variables to hold user items to add to ArrayLists
                String selectedCategory = categories.getValue();
                String enteredAmount = amount.getText();
                String subCategory;

                // Make sure status messages are not showing
                statusMessage2.setVisible(false);
                statusMessage1.setVisible(false);

                try { 
                    // If textbox for amount is not empty, category & subcategory is not empty, and the amount the user entered is not less than 0
                    if(!amount.getText().isEmpty() && (categories.getValue() != "Other" || !custom.getText().isEmpty()) && categories.getValue() != null && subCategories.getValue() != null && Double.parseDouble(amount.getText()) >= 0.0) {
                        // If the user had selected "Other"
                        if(custom.isVisible())
                            subCategory = custom.getText();
                        else
                            subCategory = subCategories.getValue();
                        
                        // If main expense and amount is not empty
                        if(selectedCategory != null && !enteredAmount.isEmpty())
                        {
                            expenseCategories.add(selectedCategory);

                            // If the subcategory is empty (for cases of savings)
                            if(subCategory != null)
                                expenseSubCategories.add(subCategory);
                            else
                                expenseSubCategories.add("");

                            // Add the amount to the ArrayList
                            expenseAmounts.add(Double.parseDouble(enteredAmount));

                            // Output a message saying expense was added
                            statusMessage1.setText("Expense Added to List!");
                            statusMessage1.setVisible(true);
                    
                            // Reset all values 
                            categories.setValue(null);
                            subCategories.setValue(null);
                            subCategories.setVisible(false);
                            custom.setVisible(false);
                            custom.clear();
                            amount.clear();
                        }
                    }
                    else {
                        if(!(!amount.getText().isEmpty() && (categories.getValue() != "Other" || !custom.getText().isEmpty()) && categories.getValue() != null && subCategories.getValue() != null))
                        {
                            // In case user has not filled out all fields
                            statusMessage1.setText("Not All Fields Have Been Entered!");
                            statusMessage1.setStyle("-fx-font-weight: bold");
                            statusMessage1.setVisible(true);
                        }

                        if(Double.parseDouble(amount.getText()) < 0.0)
                        {   
                            // In case user has entered a negative amount
                            statusMessage2.setText("Please Enter Valid Information!");
                            statusMessage2.setVisible(true);
                        }
                    }
                }   catch (NumberFormatException ex) {
                        // In case user enters letters in the amount
                        statusMessage2.setText("Please Enter Valid Information!");
                        statusMessage2.setVisible(true);
                    }
            });

            // Reset Button on sceneThree to revert everything back to original
            resetButton1.setOnAction(y -> {
                // Clear All the ArrayLists
                expenseAmounts.clear();               
                expenseCategories.clear();
                expenseSubCategories.clear();

                // Clear all of the comboBoxes and hide the SubCategory
                categories.setValue(null);
                subCategories.setValue(null);
                subCategories.setVisible(false);
                
                // Hide the Status Messages and Custom Expense Box
                statusMessage1.setVisible(false);
                custom.setVisible(false);

                // Clear the textfields of any information
                custom.clear();
                amount.clear();

                // Display Message
                statusMessage2.setText("Reset Complete!");
                statusMessage2.setVisible(true);
            });
        });

        
        // Action event for the continue button
        nextButton.setOnAction(e -> {
            // Clear the VBox list if it has elements already
            expensesList.getChildren().clear();
            // For loop to add each expense to the VBox list
            for(int i = 0; i < expenseCategories.size(); i++)
            {
                // Creating a Text for the expenses and adding it to the expenseslist box
                Text expenseText = new Text(expenseCategories.get(i) + ":  " + expenseSubCategories.get(i) + "     $" + String.format("%.2f", expenseAmounts.get(i)));
                expenseText.setStyle("-fx-font-family: Arial; -fx-font-size: 13px;");
                expensesList.getChildren().add(expenseText);
            }

            // Show sceneFour
            primaryStage.setScene(sceneFour);
            primaryStage.show();
        });
        /****************************************************/



        // Scene 4 Events
        /****************************************************/
        // Ryan's Calculate Expenses Button Event
        recommendationsButton.setOnAction(e -> {
            // Moves to Recommendation Scene and adds the back button for the bottomleft scene
            primaryStage.setScene(sceneFive);

            hBoxBottom.getChildren().clear();

            hBoxBottom.getChildren().add(backButton5_4);
        });
        /****************************************************/



        // Scene 5 Events
        /****************************************************/
        // Quit the program once user is satisfied
        quitButton2.setOnAction(e -> Platform.exit());

        // Clear button removes the chart from the scene and hides the clear button after
        resetButton.setOnAction(e -> {
            root.setCenter(null);

            hBoxBottom.getChildren().remove(resetButton);
        });

        // Show the Expense List for Plan1 and the Clear Button
        list1.setOnAction(e -> {
            AlgorithmCall(1);

            hBoxBottom.getChildren().add(resetButton);

            CreateList();
        });

        // Show the Expense List for Plan2 and the Clear Button
        list2.setOnAction(e -> {
            AlgorithmCall(2);

            hBoxBottom.getChildren().add(resetButton);

            CreateList();
        });

        // Show the Expense List for Plan3 and the Clear Button
        list3.setOnAction(e -> {
            AlgorithmCall(3);

            hBoxBottom.getChildren().add(resetButton);

            CreateList();
        });

        // Show the Pie Chart for Plan1 and the Clear Button
        pie1.setOnAction(e -> {
            AlgorithmCall(1);

            hBoxBottom.getChildren().add(resetButton);

            CreatePieChart();
        });

        // Show the Pie Chart for Plan2 and the Clear Button
        pie2.setOnAction(e -> {
            AlgorithmCall(2);

            hBoxBottom.getChildren().add(resetButton);

            CreatePieChart();
        });

        // Show the Pie Chart for Plan3 and the Clear Button
        pie3.setOnAction(e -> {
            AlgorithmCall(3);

            hBoxBottom.getChildren().add(resetButton);

            CreatePieChart();
        });
        /****************************************************/



        // Back Button Events
        /****************************************************/
        // When user clicks back on Scene2 (Income Input Scene), it goes back to Scene1 (The Start Menu)
        backButton2_1.setOnAction(e -> {
            primaryStage.setScene(sceneOne);
            errorLabel.setVisible(false);
            incomeField.setText("");
            frequencyComboBox.setValue(null);
            player.stop();
        });

        // When user clicks back on Scene3 (The Expense Input Scene), it goes back to scene2 (The Income Input Scene)
        backButton3_2.setOnAction(e -> primaryStage.setScene(sceneTwo));

        // When user clicks back on Scene4 (The Full Expense List Output), it goes back to scene3 (The Expense Input Scene)
        backButton4_3.setOnAction(e -> {
            // Show sceneThree
            primaryStage.setScene(sceneThree);
            statusMessage2.setVisible(false);
            statusMessage1.setVisible(false);
            primaryStage.show();
        });
        
        // When user clicks back on Scene5 (Recommendation Scene), it goes back to scene4 (Full Expense List Output)
        backButton5_4.setOnAction(e -> {
            root.setCenter(null);

            primaryStage.setScene(sceneFour);
        });
        /****************************************************/
    }

    // Calculates the Monthly Income of the User according to the Pay Frequency
    private double CalculateMonthlyAverage()
    {
        double monthlyAverage = 0.0;

        if(payFrequency.equals("Weekly"))              // Paid Weekly
            monthlyAverage = income * 4;
        else if(payFrequency.equals("Bi-Weekly"))      // Paid every other Week
            monthlyAverage = income * 2;
        else if(payFrequency.equals("Yearly"))         // Paid once in the Year
            monthlyAverage = income / 12;
        else
            monthlyAverage = income;                            // Paid Monthly

        return monthlyAverage;
    }
    
    // Calculates the Pie Chart / Expense List for Scene4 when called
    private void AlgorithmCall(final int num)
    {
        // Add the Back button to Scen4 and clear the scene
        hBoxBottom.getChildren().clear();
        hBoxBottom.getChildren().add(backButton5_4);

        root.setCenter(null);
        totalAmount = 0.0;

        expenseChanges.clear();

        // Hold the number of expenses for each category
        expenseItems = BudgetAlgorithms.TotalExpenses(expenseCategories, expenseAmounts);

        // Calculate Total Amount of Expenses
        for(double expense : expenseAmounts)
            totalAmount += expense;

        // Calculation of Expenses when Amount is greater than the income of the User
        if((expenseItems[13] > 0.0) && (totalAmount > monthlyIncome))
        {
            totalAmount -= expenseItems[13];
            
            expenseChanges.put(13, expenseItems[13]);

            expenseItems[13] = 0;
        }
        // Calculation of Expenses when Amount is Less than the Income of the User
        if(totalAmount < monthlyIncome)
        {
            double savings = monthlyIncome - totalAmount;

            if(expenseChanges.containsKey(13))
                expenseChanges.put(13, expenseChanges.get(13) - savings);
            else
                expenseChanges.put(13, -savings);

            expenseItems[13] += savings;
        }
        // Calculation of each plan for the given expense and income of the user
        else if(totalAmount != monthlyIncome)
        {
            switch(num)
            {
                case 1:
                    BudgetAlgorithms.Algorithm1(expenseItems, expenseChanges, monthlyIncome, totalAmount);
                    break;
                case 2:
                    BudgetAlgorithms.Algorithm2(expenseItems, expenseChanges, monthlyIncome, totalAmount);
                    break;
                case 3:
                    BudgetAlgorithms.Algorithm3(expenseItems, expenseChanges, monthlyIncome, totalAmount);
                    break;
            }
        }
    }

    // Updating the Pie Chart according to the information given by the user
    private void UpdatePieChart()
    {
        // Clears the data and and creates the new chart with updated information
        pieChartData.clear();

        for(Expense expense : expenses)
        {
            double percentage;

            percentage = (expense.getAmount() / monthlyIncome) * 100;
            
            pieChartData.add(new PieChart.Data(expense.getLabel() + " ($" + String.format("%.2f", expense.getAmount()) + ") (" + String.format("%.2f", percentage) + "%)", expense.getAmount()));
        }
    }

    // Creating the Pie Chart according to the information given by the user
    private void CreatePieChart()
    {
        expenses.clear();

        for(int i = 0; i < expenseItems.length; i++)
            if(expenseItems[i] > 0.0)
                expenses.add(new Expense(mainCategories[i], expenseItems[i]));
        
        pieChartData = FXCollections.observableArrayList();
        // Populate chart data from the ArrayList
        UpdatePieChart();

        // Pie chart
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Expense Distribution");
        pieChart.setLegendVisible(true);

        // Apply styling to the PieChart
        pieChart.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");

        // Add interactivity to Pie Chart (hover effect for both slice and label)
        for(PieChart.Data data : pieChartData)
        {
            data.getNode().setOnMouseEntered(e -> {
                // Highlight the slice
                data.getNode().setStyle("-fx-effect: dropshadow(gaussian, darkblue, 20, 0, 0, 0);");

                // Emphasize the corresponding label
                pieChart.setTitle(data.getName());
            });

            data.getNode().setOnMouseExited(e -> {
                // Remove slice highlight
                data.getNode().setStyle("-fx-effect: none;");

                // Reset title
                pieChart.setTitle("Expense Distribution");
            });
        }

        root.setCenter(pieChart);
    }

    private void CreateList()
    {
        // ExpenseList Title
        Label expenseList = new Label("Expense Cutbacks: ");
        expenseList.setStyle("-fx-font: 20px Arial; -fx-font-weight: bold;");

        // Make a VBox for the Scenes
        VBox expensesList = new VBox(10);
        expensesList.setPadding(new Insets(5));
        expensesList.setAlignment(Pos.CENTER);

        // ScrollPane to hold the list of expenses
        ScrollPane scrollPane = new ScrollPane(expensesList);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(375, 240);
        scrollPane.setMaxWidth(375);
        scrollPane.setStyle("-fx-background-color: #696969;");

        // Contains the list of expenses
        VBox listOfExpenses = new VBox(5, expenseList, scrollPane);
        listOfExpenses.setAlignment(Pos.CENTER);

        // Clear the VBox list if it has elements already
        expensesList.getChildren().clear();
        // For loop to add each expense to the VBox list
        for(int i = 0; i < expenseItems.length; i++)
        {
            if(expenseChanges.containsKey(i) && expenseChanges.get(i) > 0.0)
            {
                Text expenseText = new Text(mainCategories[i] + ":  Cutback $" + String.format("%.2f", expenseChanges.get(i)));
                expenseText.setStyle("-fx-font-family: Arial; -fx-font-size: 13px;");
                expensesList.getChildren().add(expenseText);
            }
        }

        root.setCenter(listOfExpenses);
    }

    // Expense class used by CreatePieChart
    // creates label for slice and amount in slice
    class Expense
    {
        private String label;
        private double amount;

        // Constructor to initialize label and amount
        public Expense(String label, double amount) {
            this.label = label;
            this.amount = amount;
        }

        // Getter for label
        public String getLabel() {
            return label;
        }

        // Getter for amount
        public double getAmount() {
            return amount;
        }
    }
    
    // main launches GUI
    public static void main(String[] args)
    {
        launch(args);
    }
}
