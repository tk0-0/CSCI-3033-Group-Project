import java.util.*; 

public class Input {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        final String[] categories = {"Home and Utilities", "Food/Groceries", "Health/Personal Care", "Personal Insurance", "Savings", "Transportation", "Education", "Communication", "Pets", "Shopping and Entertainment", "Emergencies", "Travel", "Miscellaneous", "Other"};

        final String[][] subCategories = {{"Rent", "Mortgage", "Water, Electric, etc.", "Home Repair"},
                                          {"Grocery Shopping", "Fast Food/Restaurant", "Food Delivery"},
                                          {"Medicine", "Medical Bill"},
                                          {"Health Insurance", "Disability Insurance", "Life Insurance", "Dental Insurance", "Renters Insurance", "Auto Insurance"},
                                          {""},
                                          {"Car Payment", "Car Maintenance", "Gas"},
                                          {"Tuition", "School Supplies"},
                                          {"Internet Bill", "Phone Bill", "Phone Payment"},
                                          {"Pet Supplies", "Vet Visit", "Pet Insurance"},
                                          {"Online Purchase", "In-Person Purchase", "Clothes Shopping", "Streaming Service", "Game"},
                                          {"Funeral", "Family Support"},
                                          {"Lodging", "Plane Ticket", "Car Rental"},
                                          {"Loan/Debt", "Check", "Withdraw"}};

        BudgetPlan budget = new BudgetPlan();

        int payPeriod = 0;
        int days = 0;
        double income = 0.0;
        double monthlyAverage = 0.0;

        int expenseType;
        int subExpenseType;
        String expenseDescription;
        double expenseAmount;

        System.out.println("Welcome to the Personal Budget Manager!\n");
        
        do{
            System.out.print("Please enter your pay period.\n0 for bi-weekly, 1 for monthly, 2 for yearly, 3 for other: "); 
            payPeriod = scan.nextInt();

        } while(payPeriod < 0 || payPeriod > 3);

        if(payPeriod == 3)
        {
            do {
                System.out.print("After how many days do you get paid? (estimate months to 30 days): ");
                days = scan.nextInt();

                if(days == 0)
                {
                    do {
                    System.out.print("How much money are you limited to monthly?");
                    monthlyAverage = scan.nextDouble();

                    if(monthlyAverage < 0.0)
                        System.out.println("Amount entered needs to be >= 0");

                    } while(monthlyAverage < 0.0);

                    budget.SetIncome(monthlyAverage);
                }

                if(days < 0)
                    System.out.println("You must enter a non-negative number of days.");

            } while(days < 0);
        }

        do {
            System.out.print("\nPlease enter your income over the pay period you entered: "); 
            income = scan.nextDouble();

            if(income < 0.0)
                System.out.println("Amount entered needs to be >= 0");

        } while(income < 0.0);

        if(!(payPeriod == 3 && days == 0))
        {
            monthlyAverage = calculateMonthlyAverage(payPeriod, income, days);
            budget.SetIncome(monthlyAverage);
        }

        while(true)
        {
            System.out.println();
            System.out.println("Expense Categories:");
            for(int i = 1; i <= categories.length; i++)
                System.out.println(i + ": " + categories[i-1]);
            System.out.print("Please enter a category number (or enter \"-1\" to finish): ");

            expenseType = scan.nextInt();

            while((expenseType < 1 || expenseType > categories.length) && expenseType != -1)
            {
                System.out.println("Please enter a valid number.");

                expenseType = scan.nextInt();
            }

            if(expenseType == -1)
                break;
            
            if(!categories[expenseType-1].equals("Other"))
            {
                if(subCategories[expenseType-1][0].equals(""))
                    budget.AddDescription(categories[expenseType-1]);
                else
                {
                    int i;

                    System.out.println();
                    System.out.println("Expense Subcategories:");
                    for(i = 1; i <= subCategories[expenseType-1].length; i++)
                        System.out.println(i + ": " + subCategories[expenseType-1][i-1]);
                    System.out.println(i + ": Other");
                    System.out.print("Please enter a subcategory number (or enter \"-1\" to finish): ");

                    subExpenseType = scan.nextInt();

                    while((subExpenseType < 1 || subExpenseType > subCategories[expenseType-1].length + 1) && subExpenseType != -1)
                    {
                        System.out.println("Please enter a valid number.");

                        subExpenseType = scan.nextInt();
                    }

                    if(subExpenseType == -1)
                        break;

                    if(subExpenseType != subCategories[expenseType-1].length + 1)
                    {
                        budget.AddDescription(subCategories[expenseType-1][subExpenseType-1]);
                    }
                    else
                    {
                        System.out.print("Please enter the expense description: ");

                        scan.nextLine();
                        expenseDescription = scan.nextLine();
                        budget.AddDescription(expenseDescription);
                    }
                }
            }
            else
            {
                System.out.print("Please enter the expense description: ");

                scan.nextLine();
                expenseDescription = scan.nextLine();
                budget.AddDescription(expenseDescription);
            }

            do {
                System.out.print("\nPlease enter the expense amount: ");
                expenseAmount = scan.nextDouble();
    
                if(expenseAmount < 0.0)
                    System.out.println("Amount entered needs to be >= 0");
    
            } while(expenseAmount < 0.0);

            budget.AddAmount(expenseAmount);

            budget.IncrementEntries();
        }

        scan.close();
    }

    public static double calculateMonthlyAverage(final int payPeriod, final double income, final int days)
    {
        double monthlyAverage = 0.0;

        switch(payPeriod)
        {
            case 0:
                monthlyAverage = income * 2;
                break;
            case 1:
                monthlyAverage = income;
                break;
            case 2:
                monthlyAverage = income / 12;
                break;
            case 3:
                monthlyAverage = ((income/days) * 365) / 12;
        }

        return monthlyAverage;
    }
}
