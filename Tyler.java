import java.util.*;

public class Tyler
{
    private static final String[] categories = {"Home and Utilities", "Food/Groceries", "Health/Personal Care", "Personal Insurance", "Transportation", "Emergencies", "Education", "Communication", "Pets", "Shopping and Entertainment", "Travel", "Miscellaneous", "Other", "Savings"};

    public static void Algorithm1(double[] expenses, HashMap<Integer,Double> expenseChanges, double monthlyIncome, double total)
    {
        expenseChanges.clear();

        // Algorithm 1: Priority of top 4, starts at bottom of priority list, goes up to 5, cutting set percentages until breaking even
            // Percentage Cuts: 12-0: 75%, 70%, 60%, 55%, 50%, 45%, 30%, 25%, 10%, 10%, 10%, 10%, 10%
        outer:
        while(true)
        {
            for(int i = 12; i >= 0; i--)
            {
                double percent = 0;
                boolean priorityChange = true;

                for(int j = 6; j <= 12; j++)
                    if(expenses[j] > 0.0)
                        priorityChange = false;

                if(i >= 0 && i <= 4 && !priorityChange)
                    break;

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

                if((total - (expenses[i] * percent)) > monthlyIncome)
                {
                    if((expenses[i] * percent) >= 0.01)
                    {
                        total -= (expenses[i] * percent);

                        if(expenses[i] * percent > 0.0 && expenseChanges.get(i) == null)
                            expenseChanges.put(i, (expenses[i] * percent));
                        else if(expenses[i] * percent > 0.0 && expenseChanges.get(i) != null)
                            expenseChanges.put(i, expenseChanges.get(i) + (expenses[i] * percent));
                        
                        expenses[i] -= (expenses[i] * percent);
                    }
                    else
                    {
                        total -= expenses[i];

                        if(expenses[i] > 0.0 && expenseChanges.get(i) == null)
                            expenseChanges.put(i, expenses[i]);
                        else if(expenses[i] > 0.0 && expenseChanges.get(i) != null)
                            expenseChanges.put(i, expenseChanges.get(i) + expenses[i]);
                        
                        expenses[i] = 0.0;
                    }
                }
                else
                {
                    if(total - monthlyIncome > 0.0)
                    {
                        if(total - monthlyIncome > 0.0 && expenseChanges.get(i) == null)
                            expenseChanges.put(i, total - monthlyIncome);
                        else if(total - monthlyIncome > 0.0 && expenseChanges.get(i) != null)
                            expenseChanges.put(i, expenseChanges.get(i) + total - monthlyIncome);
                        
                        expenses[i] -= (total - monthlyIncome);

                        total = monthlyIncome;
                    }

                    break outer;
                }
            }
        }
    }

    public static void Algorithm2(double[] expenses, HashMap<Integer,Double> expenseChanges, double monthlyIncome, double total)
    {
        expenseChanges.clear();

        // Algorithm 2: Cut 10% from bottom of priority list
        outer:
        while(true)
        {
            for(int i = 12; i >= 0; i--)
            {
                if((total - (expenses[i] * 0.1)) > monthlyIncome)
                {
                    if((expenses[i] * 0.1) >= 0.01)
                    {
                        total -= (expenses[i] * 0.1);

                        if(expenses[i] * 0.1 > 0.0 && expenseChanges.get(i) == null)
                            expenseChanges.put(i, expenses[i] * 0.1);
                        else if(expenses[i] * 0.1 > 0.0 && expenseChanges.get(i) != null)
                            expenseChanges.put(i, expenseChanges.get(i) + expenses[i] * 0.1);
                        
                        expenses[i] -= (expenses[i] * 0.1);
                    }
                    else
                    {
                        total -= expenses[i];

                        if(expenses[i] > 0.0 && expenseChanges.get(i) == null)
                            expenseChanges.put(i, expenses[i]);
                        else if(expenses[i] > 0.0 && expenseChanges.get(i) != null)
                            expenseChanges.put(i, expenseChanges.get(i) + expenses[i]);
                        
                        expenses[i] = 0.0;
                    }
                }
                else
                {
                    if(total - monthlyIncome > 0.0)
                    {
                        if(total - monthlyIncome > 0.0 && expenseChanges.get(i) == null)
                            expenseChanges.put(i, total - monthlyIncome);
                        else if(total - monthlyIncome > 0.0 && expenseChanges.get(i) != null)
                            expenseChanges.put(i, expenseChanges.get(i) + total - monthlyIncome);
                        
                        expenses[i] -= (total - monthlyIncome);

                        total = monthlyIncome;
                    }

                    break outer;
                }
            }
        }
    }

    public static void Algorithm3(double[] expenses, HashMap<Integer,Double> expenseChanges, double monthlyIncome, double total)
    {
        expenseChanges.clear();

        // Algorithm 3: 0 out everything from bottom of list
        outer:
        while(true)
        {
            for(int i = 12; i >= 0; i--)
            {
                if((total - expenses[i]) > monthlyIncome)
                {
                    total -= expenses[i];

                    if(expenses[i] > 0.0 && expenseChanges.get(i) == null)
                        expenseChanges.put(i, expenses[i]);
                    else if(expenses[i] > 0.0 && expenseChanges.get(i) != null)
                        expenseChanges.put(i, expenseChanges.get(i) + expenses[i]);
                    
                    expenses[i] = 0.0;
                }
                else
                {
                    if(total - monthlyIncome > 0.0)
                    {
                        if(total - monthlyIncome > 0.0 && expenseChanges.get(i) == null)
                            expenseChanges.put(i, total - monthlyIncome);
                        else if(total - monthlyIncome > 0.0 && expenseChanges.get(i) != null)
                            expenseChanges.put(i, expenseChanges.get(i) + total - monthlyIncome);

                        expenses[i] -= (total - monthlyIncome);

                        total = monthlyIncome;
                    }

                    break outer;
                }
            }
        }
    }

    public static double[] TotalExpenses(final ArrayList<String> expenseCategories, final ArrayList<Double> expenseAmounts)
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
}
