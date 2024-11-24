import javafx.scene.paint.*;
import javafx.animation.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.event.*;
import javafx.scene.media.*;
import java.io.*;
import java.util.ArrayList;
import javafx.util.Duration;
import javafx.scene.shape.*;
import java.util.Arrays;

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

    private ArrayList<String> expenseCategories = new ArrayList<>();
    private ArrayList<Double> expenseAmounts = new ArrayList<>();
    private ArrayList<String> expenseSubCategories = new ArrayList<>();

    private static final String[] mainCategories = {"Home and Utilities", "Food/Groceries", "Health/Personal Care", "Personal Insurance", "Transportation", "Emergencies", "Education", "Communication", "Pets", "Shopping and Entertainment", "Travel", "Miscellaneous", "Other", "Savings"};
    private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    private ArrayList<Expense> expenses = new ArrayList<>();
    private double totalAmount = 0.0;

    // Keeps track of changed categories
    private ArrayList<Integer> expenseChanges = new ArrayList<>();
    // Holds all expenses made by user
    private double[] expenseItems; // = Tyler.TotalExpenses(expenseCategories, expenseAmounts);
    private BorderPane root = new BorderPane();
    private HBox hboxbottom = new HBox(20);


    @Override
    public void start(Stage primaryStage)
    {
        // First Scene
        /****************************************************/
        VBox vBoxSceneOne = new VBox(20);
        vBoxSceneOne.setAlignment(Pos.CENTER);
        vBoxSceneOne.setStyle("-fx-background-color: #f0f8ff;");

        // Image
        Image image = new Image("file:content/1.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(130);
        imageView.setFitWidth(130);

        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER); // Center everything in the StackPane

        // Create Title
        String text = "The Personal Budget Planner ";
        double radius = 110; 
        double startAngle = 180; 

        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);
            double angle = startAngle + (360.0 / text.length() * i);

            // Calculate position relative to center
            double x = radius * Math.cos(Math.toRadians(angle));
            double y = radius * Math.sin(Math.toRadians(angle));

            // Create individual Text node for each letter
            Text ch = new Text(String.valueOf(letter));
            ch.setFont(Font.font("Arial", 30));
            ch.setFill(Color.BLACK);
            ch.setRotate(angle + 90); // Rotate each letter to align with the circle
            ch.setTranslateX(x); // Position relative to center
            ch.setTranslateY(y); // Position relative to center

            // Add letter directly to StackPane
            stackPane.getChildren().add(ch);
        }

        Circle sceneOneCircleOne = new Circle(90);
        sceneOneCircleOne.setFill(null);
        sceneOneCircleOne.setStroke(Color.BLACK);
        sceneOneCircleOne.setStrokeWidth(2);

        
        FadeTransition ftrans = new FadeTransition(new Duration(2000), imageView);
        ftrans.setFromValue(1); 
        ftrans.setCycleCount(Timeline.INDEFINITE);
        ftrans.setToValue(0.5);
        ftrans.setAutoReverse(true);
        ftrans.play();

        Circle sceneOneCircleTwo = new Circle(4);
        sceneOneCircleTwo.setStroke(Color.BLACK);

        PathTransition pt = new PathTransition(); 
        pt.setDuration(Duration.millis(5000)); 
        pt.setPath(sceneOneCircleOne);
        pt.setNode(sceneOneCircleTwo); 
        pt.setCycleCount(Timeline.INDEFINITE);
        pt.setInterpolator(Interpolator.LINEAR);
        pt.setAutoReverse(false); 
        pt.play();

        FillTransition filler = new FillTransition(new Duration(2000), sceneOneCircleTwo, Color.GREEN, Color.YELLOW); 
        filler.setCycleCount(Timeline.INDEFINITE);
        filler.setAutoReverse(true);
        filler.play();

        stackPane.getChildren().add(0, imageView);
        stackPane.getChildren().addAll(sceneOneCircleOne,sceneOneCircleTwo);

        // Buttons
        sceneOneButtonOne = new Button("Start Budget Planning");
        sceneOneButtonOne.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-padding: 10 20;");
        sceneOneButtonTwo = new Button("Quit");
        sceneOneButtonTwo.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 10 20;");

        ScaleTransition scale = new ScaleTransition(new Duration(2000), sceneOneButtonOne); 
        scale.setFromX(1); 
        scale.setFromY(1);
        scale.setToX(1.2); 
        scale.setToY(1.2); 
        scale.setCycleCount(Timeline.INDEFINITE);
        scale.setAutoReverse(true);
        
        scale.play(); 

        // Adding nodes to the layout
        vBoxSceneOne.getChildren().addAll(stackPane, sceneOneButtonOne, sceneOneButtonTwo);
        vBoxSceneOne.setPadding(new Insets(20, 0, 0, 0));
        VBox.setMargin(sceneOneButtonOne, new Insets(30, 0, 0, 0));
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
        subCategories.setPrefWidth(200);    
        subCategories.setVisible(false);

        //Textfields for the custom category and user input amount
        TextField amount = new TextField();

        TextField custom = new TextField();                
        custom.setPrefWidth(200);   
        custom.setVisible(false);

        //Labels for the Lists and amount
        Label selectCategory = new Label("Select a Category!");  
        selectCategory.setStyle("-fx-font-size: 15px Arial; -fx-font-weight: bold;");

        Label selectSubCategories = new Label("Select a Subcategory!");     
        selectSubCategories.setStyle("-fx-font-size: 15px Arial; -fx-font-weight: bold;");
        selectSubCategories.setVisible(false);

        Label amtEnter_message = new Label("Enter Amount:");  
        amtEnter_message.setStyle("-fx-font-size: 15px Arial; -fx-font-weight: bold;"); 

        Label goodExpense = new Label();
        goodExpense.setStyle("-fx-font-size: 15px Arial;");
        goodExpense.setVisible(false);
        
        Label errorMessage = new Label("Please Enter Valid Information!!");
        errorMessage.setVisible(false);

        //Buttons for sceneThree: Adding to List, Next Button, Reset Button, go Back button
        Button add = new Button("Add");      
        add.setPrefWidth(90);
        add.setPrefHeight(15);
        add.setStyle("-fx-background-color: #ffa500; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");

        Button next = new Button("Continue");        
        next.setPrefWidth(95);
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
            //Get value of category combobox
            String category = categories.getValue();

            //Make sure subcategory is not visible until selected
            selectSubCategories.setVisible(false);
            subCategories.setVisible(false);
            subCategories.getItems().clear();

            //Make sure other textfield is not visible until "other" is selected
            custom.setVisible(false);

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
            
            //Action event for adding an expense
            add.setOnAction(x -> {
                //Variables to hold user items to add to arrayLists
                String selectedCategory = categories.getValue();
                String enteredAmount = amount.getText();
                String subCategory;

                try{ 
                    //If textbox for amount not empty, category & subcategory is not empty, and the amount the user entered is not less than 0
                    if (!amount.getText().isEmpty() && (categories.getValue() != "Other" || !custom.getText().isEmpty()) && categories.getValue() != null && subCategories.getValue() != null && Double.parseDouble(amount.getText()) >= 0.0) {
                        //If the user had selected "Other"
                        if (custom.isVisible()) {
                            subCategory = custom.getText();
                        }
                        else {
                            subCategory = subCategories.getValue();
                        }
                        
                        //If main expense & amount is not empty
                        if (selectedCategory != null && !enteredAmount.isEmpty()) {
                            expenseCategories.add(selectedCategory);

                            //If the subcategory is empty (for cases of savings)
                            if (subCategory != null) {
                                expenseSubCategories.add(subCategory);
                            } 
                            else {
                                expenseSubCategories.add("");
                            }
                            //Add the amount to the array
                            expenseAmounts.add(Double.parseDouble(enteredAmount));

                            //Output a message saying expense was added
                            goodExpense.setText("Expense Added to List!");
                            goodExpense.setVisible(true);
                    
                            //Reset all values 
                            categories.setValue(null);
                            subCategories.setValue(null);
                            subCategories.setVisible(false);
                            errorMessage.setVisible(false);
                            custom.setVisible(false);
                            custom.clear();
                            amount.clear();
                        }
                    }
                    else {
                        //In case user has not filled out all fields
                        goodExpense.setText("Sorry not all fields have been entered");
                        goodExpense.setStyle("-fx-font-weight: bold");
                        goodExpense.setVisible(true);
                        errorMessage.setVisible(true);
                    }
                }   catch (NumberFormatException ex) {
                        //In case user enters letters in the amount
                        errorMessage.setText("Please Enter Valid Information!");
                        errorMessage.setStyle("-fx-font-weight: bold");
                        errorMessage.setVisible(true);
                    }
            });

            //Reset Button on sceneFour to revert everything back to original
            reset.setOnAction(y -> {
                expenseAmounts.clear();
                expenseCategories.clear();
                expenseSubCategories.clear();

                categories.setValue(null);
                subCategories.setValue(null);
                subCategories.setVisible(false);
                errorMessage.setVisible(false);
                goodExpense.setVisible(false);
                custom.setVisible(false);
                custom.clear();
                amount.clear();
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

        VBox amountw_Error = new VBox(15, amount_area, goodExpense);
        amountw_Error.setAlignment(Pos.CENTER);

        HBox bottom_buttons = new HBox(10, back, reset, add, next); 
        bottom_buttons.setAlignment(Pos.CENTER);

        //Combines the category area and bottom area
        VBox category_areas = new VBox(15, category_box, subCategory_box);

        VBox bottom_area = new VBox(30, amountw_Error, bottom_buttons);

        VBox menu = new VBox(50, category_areas, bottom_area);

        //Holds the whole menu with the error message
        VBox updated_menu = new VBox(10, menu, errorMessage);
        updated_menu.setAlignment(Pos.CENTER);

        //Menu for the Second Scene
        //Make a VBox for the Scenes
        VBox expensesList = new VBox(10);
        expensesList.setPadding(new Insets(5));
        expensesList.setAlignment(Pos.CENTER);

        //ScrollPane to hold the list of expenses
        ScrollPane scrollPane = new ScrollPane(expensesList);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(375, 240);
        scrollPane.setMaxWidth(375);
        scrollPane.setStyle("-fx-background-color: #696969;");

        //ExpenseList Title
        Label expenseList = new Label("Your Expenses: ");
        expenseList.setStyle("-fx-font: 20px Arial; -fx-font-weight: bold;");

        //Show Recommendations Button for sceneFour
        Button calculateExpenses = new Button("Show Recommendations!");
        calculateExpenses.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");

        //Go Back button for sceneFour
        Button goBack = new Button("Go Back");
        goBack.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-weight: bold;");

        //Contains the Buttons for sceneFour
        HBox bottomButtons = new HBox(10, goBack, calculateExpenses);
        bottomButtons.setAlignment(Pos.CENTER);

        //Contains the list of expenses
        VBox list_of_expenses = new VBox(5, expenseList, scrollPane);
        list_of_expenses.setAlignment(Pos.CENTER);

        //Shows the List of expenses and the Buttons on sceneFour
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
                Text expenseText = new Text(expenseCategories.get(i) + ":  " + expenseSubCategories.get(i) + "     $" + String.format("%.2f", expenseAmounts.get(i)));
                expenseText.setStyle("-fx-font-family: Arial; -fx-font-size: 13px;");
                expensesList.getChildren().add(expenseText);
            }

            //Show sceneFour
            primaryStage.setScene(sceneFour);
            primaryStage.show();
        });

        //Action event for the go back button in the 4th scene
        goBack.setOnAction(o -> {
            //Show sceneThree
            primaryStage.setScene(sceneThree);
            errorMessage.setVisible(false);
            goodExpense.setVisible(false);
            primaryStage.show();
        });

        //Go Back to Henrys Scene2
        back.setOnAction(l -> {
            primaryStage.setScene(sceneTwo);
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
                    errorMessage.setVisible(false);
                    goodExpense.setVisible(false);
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

        // Tyler's Code
        Button pie1 = new Button("Budget Plan #1");
        Button list1 = new Button("Show Recommendations");
        Button pie2 = new Button("Budget Plan #2");
        Button list2 = new Button("Show Recommendations");
        Button pie3 = new Button("Budget Plan #3");
        Button list3 = new Button("Show Recommendations");
        Button backS5 = new Button("Go Back");

        VBox vbox1 = new VBox(5,pie1,list1);
        vbox1.setAlignment(Pos.CENTER);
        VBox vbox2 = new VBox(5,pie2,list2);
        vbox2.setAlignment(Pos.CENTER);
        VBox vbox3 = new VBox(5,pie3,list3);
        vbox3.setAlignment(Pos.CENTER);
        HBox hboxtop = new HBox(20, vbox1, vbox2, vbox3);

        root.setTop(hboxtop);
        hboxtop.setAlignment(Pos.CENTER);

        hboxbottom.getChildren().add(backS5);
        root.setBottom(hboxbottom);
        hboxbottom.setAlignment(Pos.CENTER);

        Scene sceneFive = new Scene(root, 1000, 500);

        // Ryan's Calculate Expenses Button Event
        calculateExpenses.setOnAction(e -> {primaryStage.setScene(sceneFive);});

        pie1.setOnAction(e -> {
            root.setCenter(null);
            totalAmount = 0.0;

            expenseItems = Tyler.TotalExpenses(expenseCategories, expenseAmounts);

            for(double expense : expenseAmounts)
                totalAmount += expense;

            if(totalAmount < monthlyIncome)
            {
                double savings = monthlyIncome - totalAmount;
                expenseItems[13] += savings;
            }
            else if(totalAmount != monthlyIncome)
            {
                Tyler.Algorithm1(expenseItems, expenseChanges, monthlyIncome, totalAmount);
            }

            pieChartData.clear();
            expenses.clear();

            createPieChart();
        });

        pie2.setOnAction(e -> {
            root.setCenter(null);
            totalAmount = 0.0;

            expenseItems = Tyler.TotalExpenses(expenseCategories, expenseAmounts);

            for(double expense : expenseAmounts)
                totalAmount += expense;

            if(totalAmount < monthlyIncome)
            {
                double savings = monthlyIncome - totalAmount;
                expenseItems[13] += savings;
            }
            else if(totalAmount != monthlyIncome)
            {
                Tyler.Algorithm2(expenseItems, expenseChanges, monthlyIncome, totalAmount);
            }

            pieChartData.clear();
            expenses.clear();

            createPieChart();
        });

        pie3.setOnAction(e -> {
            root.setCenter(null);
            totalAmount = 0.0;

            expenseItems = Tyler.TotalExpenses(expenseCategories, expenseAmounts);

            for(double expense : expenseAmounts)
                totalAmount += expense;

            if(totalAmount < monthlyIncome)
            {
                double savings = monthlyIncome - totalAmount;
                expenseItems[13] += savings;
            }
            else if(totalAmount != monthlyIncome)
            {
                Tyler.Algorithm3(expenseItems, expenseChanges, monthlyIncome, totalAmount);
            }

            pieChartData.clear();
            expenses.clear();

            createPieChart();
        });
    }

    private double CalculateMonthlyAverage() {
        double monthlyAverage = 0.0;

        if(payFrequency.equals("Weekly"))
            monthlyAverage = income * 4;
        else if(payFrequency.equals("Bi-Weekly"))
            monthlyAverage = income * 2;
        else
            monthlyAverage = income;

        return monthlyAverage;
    }

    private void updatePieChart() {
        pieChartData.clear();

        for (Expense expense : expenses) {
            double percentage;

            if(totalAmount > 0.0)
                percentage = (expense.getAmount() / totalAmount) * 100;
            else
                percentage = 100;
            
            pieChartData.add(new PieChart.Data(expense.getLabel() + " ($" + String.format("%.2f", expense.getAmount()) + ") (" + String.format("%.2f", percentage) + "%)", expense.getAmount()));
        }
    }

    private void createPieChart() {
        for(int i = 0; i < 14; i++)
        if(expenseItems[i] > 0.0)
            expenses.add(new Expense(mainCategories[i], expenseItems[i]));
        
        // Pie chart
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Expense Distribution");
        pieChart.setLegendVisible(true);

        // Apply styling to the PieChart
        pieChart.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");

        // Populate chart data from the ArrayList
        updatePieChart();

        // Reset button clears the pie chart
        Button resetButton = new Button("Clear");
        resetButton.setOnAction(e -> {
            root.setCenter(null);
            hboxbottom.getChildren().remove(resetButton);
        });

        // Add interactivity to Pie Chart (hover effect for both slice and label)
        for (PieChart.Data data : pieChartData) {
            data.getNode().setOnMouseEntered(event -> {
                // Highlight the slice
                data.getNode().setStyle("-fx-effect: dropshadow(gaussian, darkblue, 20, 0, 0, 0);");

                // Emphasize the corresponding label
                pieChart.setTitle(data.getName());
            });

            data.getNode().setOnMouseExited(event -> {
                // Remove slice highlight
                data.getNode().setStyle("-fx-effect: none;");

                // Reset title
                pieChart.setTitle("Expense Distribution");
            });
        }

        root.setCenter(pieChart);
        hboxbottom.getChildren().add(resetButton);
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Expense class
    class Expense {
        private String label;
        private double amount;

        public Expense(String label, double amount) {
            this.label = label;
            this.amount = amount;
        }

        public String getLabel() {
            return label;
        }

        public double getAmount() {
            return amount;
        }
    }
}
