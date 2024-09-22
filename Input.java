import java.util.Scanner; 

public class Input {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int payPeriod = 0;
        double income = 0.0;
        double monthlyAverage = 0.0;

        String expenseDescription;
        double expenseAmount;
        int entries = 0;

        final int maxEntries = 100;
        String expenseDes[] = new String[maxEntries];
        double expenseAmounts[] = new double[maxEntries];

        System.out.println("Welcome to the Personal Budget Manager!\n");
        
        System.out.print("Please enter your pay period.\n0 for bi-weekly, 1 for monthly, 2 for yearly: "); 
        payPeriod = scan.nextInt();

        System.out.print("\nPlease enter your income: "); 
        income = scan.nextDouble();

        System.out.println();

        do {
            System.out.print("Enter expense description (or 'done' to finish): ");
            scan.nextLine();
            expenseDescription = scan.nextLine();

            if(expenseDescription.compareTo("done") == 0)
                break;

            expenseDes[entries] = expenseDescription;

            do {
                System.out.print("Enter expense amount (in dollars): ");

                expenseAmount = scan.nextDouble();

                if(expenseAmount < 0)
                    System.out.println("Error: expense amount must be >= 0");

            } while(expenseAmount < 0);

            expenseAmounts[entries] = expenseAmount;

            entries++;

        } while(true);

        monthlyAverage = calculateMonthlyAverage(payPeriod, income);

        System.out.println("\nPay Period: " + payPeriod);
        System.out.printf("Income: $%.2f\n", income);
        System.out.printf("Monthly Average: $%.2f\n", monthlyAverage);

        scan.close();
    }

    public static double calculateMonthlyAverage(final int payPeriod, final double income)
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
        }

        return monthlyAverage;
    }
}
