import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Zach extends Application {

    private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    private ArrayList<Expense> expenses = new ArrayList<>();
    private double totalAmount = 1000; // Example total amount

    @Override
    public void start(Stage primaryStage) {
        // Example expenses added to the ArrayList
        expenses.add(new Expense("Rent", 400));
        expenses.add(new Expense("Groceries", 150));
        expenses.add(new Expense("Utilities", 100));
        expenses.add(new Expense("Entertainment", 75));

        // Pie chart
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Expense Distribution");
        pieChart.setLegendVisible(true);

        // Apply styling to the PieChart
        pieChart.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");

        // Populate chart data from the ArrayList
        updatePieChart();

        // Reset button clears the pie chart
        Button resetButton = new Button("Reset Chart");
        resetButton.setOnAction(e -> {
            expenses.clear();
            pieChartData.clear();
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
        Scene scene = new Scene(root, 600, 500);
        primaryStage.setTitle("Interactive Pie Chart Example");
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

