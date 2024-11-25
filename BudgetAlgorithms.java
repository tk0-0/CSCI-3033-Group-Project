import java.util.*;

// This class implements the algorithms to calculate three different budget plans.
public class BudgetAlgorithms
{
    // All main categories
    private static final String[] categories = {"Home and Utilities", "Food/Groceries", "Health/Personal Care", "Personal Insurance", "Transportation", "Emergencies", "Education", "Communication", "Pets", "Shopping and Entertainment", "Travel", "Miscellaneous", "Other", "Savings"};

    // This function provides the first algorithm, which prioritizes the top 5 main categories.
    // This means that expenses under these top 5 categories will be exhausted first.
    // Categories with less priority have bigger percentages taken from them when trying to break even.
    public static void Algorithm1(double[] expenses, HashMap<Integer,Double> expenseChanges, double monthlyIncome, double total)
    {
        // repeats until break statement (when not over budget)
        outer:
        while(true)
        {
            // iterates through every main category, starting at lowest priority
            for(int i = 12; i >= 0; i--)
            {
                double percent = 0;            // holds percentage to cut from expense
                boolean priorityChange = true; // determines if top 5 categories get cut

                // checks to see if all lower priority items are zeroed-out
                // if so, priorityChange is left as true, and the top 5 categories can get cut
                for(int j = 6; j <= 12; j++)
                    if(expenses[j] > 0.0)
                        priorityChange = false;

                // top 5 categories are skipped if the other categories still have expense amounts
                if(i >= 0 && i <= 4 && !priorityChange)
                    break;

                // sets percentage to be cut from an expense depending on priority level
                // greater index = lower priority
                switch(i)
                {
                    case 12: percent = 0.75; break;
                    case 11: percent = 0.70; break;
                    case 10: percent = 0.60; break;
                    case 9:  percent = 0.55; break;
                    case 8:  percent = 0.50; break;
                    case 7:  percent = 0.45; break;
                    case 6:  percent = 0.30; break;
                    case 5:  percent = 0.25; break;
                    case 4,3,2,1,0: percent = 0.10; break;
                    default: percent = 0.0;
                }

                // checks if after the cut, will the total expenses still be greater than the monthly income
                if((total - (expenses[i] * percent)) > monthlyIncome)
                {
                    // makes sure percentage of expense is at least a penny
                    if((expenses[i] * percent) >= 0.01)
                    {
                        // subtracts percentage of expense from total expenses
                        total -= (expenses[i] * percent);

                        // marks expense change in hash map
                        if(expenses[i] * percent > 0.0 && expenseChanges.get(i) == null)
                            expenseChanges.put(i, (expenses[i] * percent));
                        else if(expenses[i] * percent > 0.0 && expenseChanges.get(i) != null)
                            expenseChanges.put(i, expenseChanges.get(i) + (expenses[i] * percent));
                        
                        // subtracts percentage of expense from the expense
                        expenses[i] -= (expenses[i] * percent);
                    }
                    // handles case where percentage of expense is less than a penny (but no 0)
                    else
                    {
                        // subtracts expense from total
                        total -= expenses[i];

                        // marks expense change in hash map
                        if(expenses[i] > 0.0 && expenseChanges.get(i) == null)
                            expenseChanges.put(i, expenses[i]);
                        else if(expenses[i] > 0.0 && expenseChanges.get(i) != null)
                            expenseChanges.put(i, expenseChanges.get(i) + expenses[i]);
                        
                        // zeroes out expense
                        expenses[i] = 0.0;
                    }
                }
                // case where taking percentage of an expense will lead to more taken out than necessary to break even
                else
                {
                    // makes sure that there is at least a penny difference between total expenses and monthly income
                    if(total - monthlyIncome > 0.0)
                    {
                        // marks expense change in hash map
                        if(total - monthlyIncome > 0.0 && expenseChanges.get(i) == null)
                            expenseChanges.put(i, total - monthlyIncome);
                        else if(total - monthlyIncome > 0.0 && expenseChanges.get(i) != null)
                            expenseChanges.put(i, expenseChanges.get(i) + total - monthlyIncome);
                        
                        // subtracts the amount that will cause the total expenses to break even with monthly income
                        expenses[i] -= (total - monthlyIncome);

                        // total expenses now equals monthly income
                        total = monthlyIncome;
                    }

                    // breaks algorithm loop
                    break outer;
                }
            }
        }
    }

    // This function provides the second algorithm, which cuts 10% of each expense starting at the lowest priority expenses.
    public static void Algorithm2(double[] expenses, HashMap<Integer,Double> expenseChanges, double monthlyIncome, double total)
    {
        // repeats until break statement (when not over budget)
        outer:
        while(true)
        {
            // iterates through every main category, starting at lowest priority
            for(int i = 12; i >= 0; i--)
            {
                // checks if after the cut, will the total expenses still be greater than the monthly income
                if((total - (expenses[i] * 0.1)) > monthlyIncome)
                {
                    // makes sure percentage of expense is at least a penny
                    if((expenses[i] * 0.1) >= 0.01)
                    {
                        // subtracts 10 percent of expense from total expenses
                        total -= (expenses[i] * 0.1);

                        // marks expense change in hash map
                        if(expenses[i] * 0.1 > 0.0 && expenseChanges.get(i) == null)
                            expenseChanges.put(i, expenses[i] * 0.1);
                        else if(expenses[i] * 0.1 > 0.0 && expenseChanges.get(i) != null)
                            expenseChanges.put(i, expenseChanges.get(i) + expenses[i] * 0.1);
                        
                        // subtracts 10 percent of expense from the expense
                        expenses[i] -= (expenses[i] * 0.1);
                    }
                    // handles case where percentage of expense is less than a penny (but no 0)
                    else
                    {
                        // subtracts expense from total
                        total -= expenses[i];

                        // marks expense change in hash map
                        if(expenses[i] > 0.0 && expenseChanges.get(i) == null)
                            expenseChanges.put(i, expenses[i]);
                        else if(expenses[i] > 0.0 && expenseChanges.get(i) != null)
                            expenseChanges.put(i, expenseChanges.get(i) + expenses[i]);
                        
                        // zeroes out expense
                        expenses[i] = 0.0;
                    }
                }
                // case where taking percentage of an expense will lead to more taken out than necessary to break even
                else
                {
                    // makes sure that there is at least a penny difference between total expenses and monthly income
                    if(total - monthlyIncome > 0.0)
                    {
                        // marks expense change in hash map
                        if(total - monthlyIncome > 0.0 && expenseChanges.get(i) == null)
                            expenseChanges.put(i, total - monthlyIncome);
                        else if(total - monthlyIncome > 0.0 && expenseChanges.get(i) != null)
                            expenseChanges.put(i, expenseChanges.get(i) + total - monthlyIncome);
                        
                        // subtracts the amount that will cause the total expenses to break even with monthly income
                        expenses[i] -= (total - monthlyIncome);

                        // total expenses now equals monthly income
                        total = monthlyIncome;
                    }

                    // breaks algorithm loop
                    break outer;
                }
            }
        }
    }

    // This function provides the third algorithm, which zeroes out expenses starting at the lowest priority expenses.
    public static void Algorithm3(double[] expenses, HashMap<Integer,Double> expenseChanges, double monthlyIncome, double total)
    {
        // repeats until break statement (when not over budget)
        outer:
        while(true)
        {
            // iterates through every main category, starting at lowest priority
            for(int i = 12; i >= 0; i--)
            {
                // checks if after the cut, will the total expenses still be greater than the monthly income
                if((total - expenses[i]) > monthlyIncome)
                {
                    // subtracts expense from total
                    total -= expenses[i];

                    // marks expense change in hash map
                    if(expenses[i] > 0.0 && expenseChanges.get(i) == null)
                        expenseChanges.put(i, expenses[i]);
                    else if(expenses[i] > 0.0 && expenseChanges.get(i) != null)
                        expenseChanges.put(i, expenseChanges.get(i) + expenses[i]);
                    
                    // zeroes out expense
                    expenses[i] = 0.0;
                }
                // case where taking percentage of an expense will lead to more taken out than necessary to break even
                else
                {
                    // makes sure that there is at least a penny difference between total expenses and monthly income
                    if(total - monthlyIncome > 0.0)
                    {
                        // marks expense change in hash map
                        if(total - monthlyIncome > 0.0 && expenseChanges.get(i) == null)
                            expenseChanges.put(i, total - monthlyIncome);
                        else if(total - monthlyIncome > 0.0 && expenseChanges.get(i) != null)
                            expenseChanges.put(i, expenseChanges.get(i) + total - monthlyIncome);

                        // subtracts the amount that will cause the total expenses to break even with monthly income
                        expenses[i] -= (total - monthlyIncome);

                        // total expenses now equals monthly income
                        total = monthlyIncome;
                    }

                    // breaks algorithm loop
                    break outer;
                }
            }
        }
    }

    // This function totals the amount of expenses for each main category, and these amounts are returned as an array of doubles.
    public static double[] TotalExpenses(final ArrayList<String> expenseCategories, final ArrayList<Double> expenseAmounts)
    {
        double[] expenses = new double[categories.length];

        // Iterates through all expense entries to total each category.
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
}
