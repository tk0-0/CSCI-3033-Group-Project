import java.awt.Color;
import java.util.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.control.*;

public class Ryan extends Application
{
    ArrayList<String> expenseCategories = new ArrayList<>();
    ArrayList<String> expenseAmounts = new ArrayList<>();
    ArrayList<String> expenseSubCategories = new ArrayList<>();
    @Override
    public void start(Stage primaryStage){
        try{
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
            //selectCategory.setFont(font);

            Label selectSubCategories = new Label("Select a Subcategory!");     
            //selectSubCategories.setFont(font);  
            selectSubCategories.setVisible(false);

            Label amtEnter_message = new Label("Enter Amount:");             
            //mtEnter_message.setFont(font);

            //Buttons to continue and add to list
            Button add = new Button("Add to List");      
            //add.setFont(font);  
            add.setPrefWidth(100);
            Button next = new Button("Continue");        
            //next.setFont(font); 
            next.setPrefWidth(100);
            Button reset = new Button("Reset");
            reset.setPrefWidth(100);

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
                                                       subCategories.getItems().setAll("");
                                                        subCategories.setValue(" ");}
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
                                                     custom.setVisible(true);
                                                     subCategories.setValue(custom.getText());}
                add.setOnAction(x -> {
                    String selectedCategory = categories.getValue();
                    String subCategory;
                    String enteredAmount = amount.getText();
                
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
                    }
                });

                reset.setOnAction(y -> {
                    expenseAmounts.clear();
                    expenseCategories.clear();
                    expenseSubCategories.clear();
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
            HBox bottom_buttons = new HBox(10, reset, add, next); 
            bottom_buttons.setAlignment(Pos.CENTER);

            //Combines the category area and bottom area
            VBox category_areas = new VBox(15, category_box, subCategory_box);
            VBox bottom_area = new VBox(30, amount_area, bottom_buttons);
            VBox menu = new VBox(50, category_areas, bottom_area);
            menu.setAlignment(Pos.CENTER);

            VBox expensesList = new VBox(10);
            expensesList.setPadding(new Insets(5));

            ScrollPane scrollPane = new ScrollPane(expensesList);
            scrollPane.setFitToWidth(true);
            scrollPane.setPrefSize(375, 240);
            scrollPane.setMaxWidth(375);
            scrollPane.setStyle("-fx-background-color: #696969;");

            Button calculateExpenses = new Button("Show Recommendations");
            Button goBack = new Button("Go Back");

            HBox bottomButtons = new HBox(10, goBack, calculateExpenses);
            bottomButtons.setAlignment(Pos.CENTER);

            VBox expenseOutput = new VBox(60, scrollPane, bottomButtons);
            expenseOutput.setAlignment(Pos.CENTER);

            Scene scene3 = new Scene(menu, 400, 400);
            menu.setStyle("-fx-background-color: #f0f8ff;");
            Scene scene4 = new Scene(expenseOutput, 400, 400);
            expenseOutput.setStyle("-fx-background-color: #f0f8ff;");
            primaryStage.setScene(scene3);
            primaryStage.show();

            next.setOnAction(p -> {
                expensesList.getChildren().clear();
                for (int i = 0; i < expenseCategories.size(); i++) {
                    Label expenseLabel = new Label(expenseCategories.get(i) + ":     " + expenseSubCategories.get(i) + "\t$" + expenseAmounts.get(i));
                    expensesList.getChildren().add(expenseLabel);
                }

                primaryStage.setScene(scene4);
                primaryStage.show();
            });

            goBack.setOnAction(o -> {
                primaryStage.setScene(scene3);
                primaryStage.show();
            });
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}



