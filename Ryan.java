import java.util.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.control.*;

public class Ryan extends Application
{
    @Override
    public void start(Stage primaryStage){
        try{
            //Font for the menu
            Font font = new Font("Noteworthy", 15);

            //ComboBoxes for the categories
            ComboBox<String> categories = new ComboBox<>();
            ComboBox<String> subCategories = new ComboBox<>();       subCategories.setPrefWidth(200);       subCategories.setVisible(false);

            //Textfields for the custom category and user input amount
            TextField amount = new TextField();
            TextField custom = new TextField();                custom.setPrefWidth(200);        custom.setVisible(false);

            //Labels for the Lists and amount
            Label selectCategory = new Label("Select a Category!");           selectCategory.setFont(font);
            Label selectSubCategories = new Label("Select a Subcategory!");     selectSubCategories.setFont(font);
            selectSubCategories.setVisible(false);
            Label amtEnter_message = new Label("Enter Amount:");             amtEnter_message.setFont(font);

            //Buttons to continue and add to list
            Button add = new Button("Add to List");      add.setFont(font);                 add.setPrefWidth(100);
            Button next = new Button("Continue");        next.setFont(font);                next.setPrefWidth(100);

            //Categories
            categories.getItems().addAll("Home and Utilities", "Food/Groceries", "Health/Personal Care", "Personal Insurance", "Savings", "Transportation", "Education", "Communication", "Pets", "Shopping and Entertainment", "Emergencies", "Travel", "Miscellaneous", "Other");

            categories.setOnAction(e -> {
                String category = categories.getValue();
                subCategories.getItems().clear();
                custom.setVisible(false);
                selectSubCategories.setVisible(false);
                subCategories.setVisible(false);

                if ("Home and Utilities".equals(category)) {selectSubCategories.setVisible(true); subCategories.setVisible(true); subCategories.getItems().setAll("Rent", "Mortgage", "Water, Electric, etc.", "Home Repair");}
                else if ("Food/Groceries".equals(category)) {selectSubCategories.setVisible(true); subCategories.setVisible(true); subCategories.getItems().setAll("Grocery Shopping", "Fast Food/Restaurant", "Food Delivery"); }
                else if ("Health/Personal Care".equals(category)) {selectSubCategories.setVisible(true); subCategories.setVisible(true); subCategories.getItems().setAll("Medicine", "Medical Bill");}
                else if ("Personal Insurance".equals(category)) {selectSubCategories.setVisible(true); subCategories.setVisible(true); subCategories.getItems().setAll("Health Insurance", "Disability Insurance", "Life Insurance", "Dental Insurance", "Renters Insurance", "Auto Insurance");}
                else if ("Savings".equals(category)) {selectSubCategories.setVisible(false); subCategories.setVisible(false);  subCategories.getItems().setAll("");}
                else if ("Transportation".equals(category)) {selectSubCategories.setVisible(true);  subCategories.setVisible(true); subCategories.getItems().setAll("Car Payment", "Car Maintenance", "Gas");}
                else if ("Education".equals(category)) {selectSubCategories.setVisible(true); subCategories.setVisible(true); subCategories.getItems().setAll("Tuition", "School Supplies"); }
                else if ("Communication".equals(category)) {selectSubCategories.setVisible(true); subCategories.setVisible(true);  subCategories.getItems().setAll("Internet Bill", "Phone Bill", "Phone Payment");}
                else if ("Pets".equals(category)) {selectSubCategories.setVisible(true); subCategories.setVisible(true); subCategories.getItems().setAll("Pet Supplies", "Vet Visit", "Pet Insurance"); }
                else if ("Shopping and Entertainment".equals(category)) {selectSubCategories.setVisible(true); subCategories.setVisible(true); subCategories.getItems().setAll("Online Purchase", "In-Person Purchase", "Clothes Shopping", "Streaming Service", "Game"); }
                else if ("Emergencies".equals(category)) {selectSubCategories.setVisible(true);  subCategories.setVisible(true); subCategories.getItems().setAll("Funeral", "Family Support");}
                else if ("Travel".equals(category)) {selectSubCategories.setVisible(true); subCategories.setVisible(true);  subCategories.getItems().setAll("Lodging", "Plane Ticket", "Car Rental");}
                else if ("Miscellaneous".equals(category)) {selectSubCategories.setVisible(true); subCategories.setVisible(true); subCategories.getItems().setAll("Loan/Debt", "Check", "Withdraw"); }
                else if ("Other".equals(category)) {subCategories.setVisible(false); selectSubCategories.setVisible(false); custom.setVisible(true);}
                    
            }); 
            
            HBox customcat = new HBox(10, custom); customcat.setAlignment(Pos.CENTER);
            VBox category_box = new VBox(10, selectCategory, categories, customcat);  category_box.setAlignment(Pos.CENTER);
            VBox subCategory_box = new VBox(10, selectSubCategories, subCategories);   subCategory_box.setAlignment(Pos.CENTER);

            HBox amount_area = new HBox(10, amtEnter_message, amount); amount_area.setAlignment(Pos.CENTER);
            HBox bottom_buttons = new HBox(10, add, next); bottom_buttons.setAlignment(Pos.CENTER);

            VBox category_areas = new VBox(15, category_box, subCategory_box);
            VBox bottom_area = new VBox(30, amount_area, bottom_buttons);
            VBox menu = new VBox(50, category_areas, bottom_area);
            menu.setAlignment(Pos.CENTER);

            Scene scene = new Scene(menu, 400, 400);
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



