import java.util.*;

public class BudgetPlan {
    private double monthlyIncome;
    private int entries;

    private ArrayList<String> expenseDes = new ArrayList<>();
    private ArrayList<Double> expenseAmounts = new ArrayList<>();

    public BudgetPlan()
    {
        monthlyIncome = 0;
        entries = 0;
    }

    public void SetIncome(double amount)
    {
        monthlyIncome = amount;
    }

    public void AddDescription(String des)
    {
        expenseDes.add(des);
    }

    public void AddAmount(double amount)
    {
        expenseAmounts.add(amount);
    }

    public void IncrementEntries()
    {
        entries++;
    }

    public int GetEntries()
    {
        return entries;
    }
}
