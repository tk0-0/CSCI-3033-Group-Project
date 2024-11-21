import java.util.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Tyler extends Application
{
    static final String[] categories = {"Home and Utilities", "Food/Groceries", "Health/Personal Care", "Personal Insurance", "Savings", "Transportation", "Education", "Communication", "Pets", "Shopping and Entertainment", "Emergencies", "Travel", "Miscellaneous", "Other"};

    static ArrayList<String> expenseCategories = new ArrayList<>();
    static ArrayList<String> expenseSubCategories = new ArrayList<>();
    static ArrayList<Double> expenseAmounts = new ArrayList<>();
    static double monthlyIncome = 2000;

    public static void main(String[] args)
    {
        // holds the expenses spent for each main category
        double[] expenses = TotalExpenses();
        ArrayList<Integer> expenseChanges = new ArrayList<>();

        // first make sure that monthly income is exceeded; if it is already fine, put the remaining in savings (only one pie chart)

        double total = 0;
        for(double expense: expenseAmounts)
            total += expense;

        if(total < monthlyIncome)
        {
            // savings = monthlyIncome - total;
            // expenses[13] += savings;
            // return expenses;
        }
        else if(total == monthlyIncome)
        {
            // nothing changes
            // return expenses;
        }

        // index in expenses double array represents priority rankings

        // Algorithm 1: Priority of top 4, starts at bottom of priority list, goes up to 5, cutting set percentages until breaking even
            // Percentage Cuts: 12-0: 75%, 70%, 60%, 55%, 50%, 45%, 30%, 25%, 10%, 10%, 10%, 10%, 10%
        while(true)
        {
            for(int i = 12; i >= 0; i--)
            {
                double percent = 0;
                boolean priorityChange = true;

                for(int j = 6; j <= 12; j++)
                    if(expenses[j] > 0)
                        priorityChange = false;

                if(i >= 0 && i <= 4 && !priorityChange)
                    break;

                switch(i) {
                    case 12: percent = 0.75; break;
                    case 11: percent = 0.70; break;
                    case 10: percent = 0.60; break;
                    case 9:  percent = 0.55; break;
                    case 8:  percent = 0.50; break;
                    case 7:  percent = 0.45; break;
                    case 6:  percent = 0.30; break;
                    case 5:  percent = 0.25; break;
                    case 4,3,2,1,0: percent = 0.10;
                    default: percent = 0.0;
                }

                if((total - (expenses[i] * percent)) > monthlyIncome)
                {
                    expenses[i] -= (expenses[i] * percent);
                    total -= (expenses[i] * percent);
                    expenseChanges.add(i);
                }
                else
                {
                    if(total - monthlyIncome > 0.0)
                    {
                        expenses[i] -= (total - monthlyIncome);
                        total = monthlyIncome;
                        expenseChanges.add(i);
                    }

                    break;
                }
            }
        }
        // Algorithm 2: Cut 10% from bottom of priority list
        // Algorithm 3: 0 out everything from bottom of list

        //launch(args);
    }

    public static double[] TotalExpenses()
    {
        double[] expenses = new double[categories.length];

        for(int i = 0; i < expenseAmounts.size(); i++)
        {
            if(expenseCategories.get(i).equals("Home and Utilities"))
                expenses[0] += expenseAmounts.get(i);
            else if(expenseCategories.get(i).equals("Food/Groceries"))
                expenses[1] += expenseAmounts.get(i);
            else if(expenseCategories.get(i).equals("Health/Personal Care"))
                expenses[2] += expenseAmounts.get(i);
            else if(expenseCategories.get(i).equals("Personal Insurance"))
                expenses[3] += expenseAmounts.get(i);
            else if(expenseCategories.get(i).equals("Transportation"))
                expenses[4] += expenseAmounts.get(i);
            else if(expenseCategories.get(i).equals("Emergencies"))
                expenses[5] += expenseAmounts.get(i);
            else if(expenseCategories.get(i).equals("Education"))
                expenses[6] += expenseAmounts.get(i);
            else if(expenseCategories.get(i).equals("Communication"))
                expenses[7] += expenseAmounts.get(i);
            else if(expenseCategories.get(i).equals("Pets"))
                expenses[8] += expenseAmounts.get(i);
            else if(expenseCategories.get(i).equals("Shopping and Entertainment"))
                expenses[9] += expenseAmounts.get(i);
            else if(expenseCategories.get(i).equals("Travel"))
                expenses[10] += expenseAmounts.get(i);
            else if(expenseCategories.get(i).equals("Miscellaneous"))
                expenses[11] += expenseAmounts.get(i);
            else if(expenseCategories.get(i).equals("Savings"))
                expenses[13] += expenseAmounts.get(i);
            else
                expenses[12] += expenseAmounts.get(i);
        }

        return expenses;
    }

    @Override
    public void start(Stage primaryStage)
    {
		try
        {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,500,500);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
        catch(Exception e)
        {
			e.printStackTrace();
		}
	}
}
