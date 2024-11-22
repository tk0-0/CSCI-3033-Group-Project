import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;

public class Zach extends Application {
    private static final String[] categories = {"Home and Utilities", "Food/Groceries", "Health/Personal Care", "Personal Insurance", "Transportation", "Emergencies", "Education", "Communication", "Pets", "Shopping and Entertainment", "Travel", "Miscellaneous", "Other", "Savings"};
    // Will come from Ryan's code
    private ArrayList<String> expenseCategories = new ArrayList<>(Arrays.asList("Home and Utilities", "Food/Groceries", "Communication", "Shopping and Entertainment"));
    private ArrayList<Double> expenseAmounts = new ArrayList<>(Arrays.asList(1400.00, 500.00, 150.00, 100.00));

    // Will come from Henry's code
    private double monthlyIncome = 2000;

    // Keeps track of changed categories
    private ArrayList<Integer> expenseChanges = new ArrayList<>();
    // Holds all expenses made by user
    private double[] expenseItems = Tyler.TotalExpenses(expenseCategories, expenseAmounts);

    private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    private ArrayList<Expense> expenses = new ArrayList<>();
    private double totalAmount = 0.0;
    
    @Override
    public void start(Stage primaryStage) {
        for(double expense : expenseAmounts)
            totalAmount += expense;

        if(totalAmount < monthlyIncome)
        {
            double savings = monthlyIncome - totalAmount;
            expenseItems[13] += savings;
        }
        else if(totalAmount != monthlyIncome)
        {
            //Tyler.Algorithm1(expenseItems, expenseChanges, monthlyIncome, totalAmount);
            //Tyler.Algorithm2(expenseItems, expenseChanges, monthlyIncome, totalAmount);
            Tyler.Algorithm3(expenseItems, expenseChanges, monthlyIncome, totalAmount);
        }

        for(int i = 0; i < 14; i++)
            if(expenseItems[i] > 0.0)
                expenses.add(new Expense(categories[i], expenseItems[i]));
        
        // Pie chart
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Expense Distribution");
        pieChart.setLegendVisible(true);

        // Apply styling to the PieChart
        pieChart.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");

        // Populate chart data from the ArrayList
        updatePieChart();

        // Reset button clears the pie chart
        Button resetButton = new Button("Go Back");
        resetButton.setOnAction(e -> {
            //TO DO
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

        // Layout
        HBox buttonBox = new HBox(10, resetButton);
        buttonBox.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setCenter(pieChart);
        root.setBottom(buttonBox);

        // Scene setup
        Scene scene = new Scene(root, 1000, 500);
        primaryStage.setTitle("Budget Plan");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updatePieChart() {
        pieChartData.clear();
        for (Expense expense : expenses) {
            double percentage = (expense.getAmount() / totalAmount) * 100;
            pieChartData.add(new PieChart.Data(expense.getLabel() + " ($" + String.format("%.2f", expense.getAmount()) + ") (" + String.format("%.2f", percentage) + "%)", expense.getAmount()));
        }
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

