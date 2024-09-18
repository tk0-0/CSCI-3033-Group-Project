import java.util.Scanner; 

public class Input {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int payPeriod = 0;
        double income = 0.0;
        double monthlyAverage = 0.0;

        final int maxEntries = 100;
        String expenseDes[] = new String[maxEntries];
        String expenseAmounts[] = new String[maxEntries];

        System.out.println("Welcome to the Personal Budget Manager!\n");
        
        System.out.print("Please enter your pay period.\n0 for bi-weekly, 1 for monthly, 2 for yearly: "); 
        payPeriod = scan.nextInt();

        System.out.print("\nPlease enter your income: "); 
        income = scan.nextDouble();

        monthlyAverage = calculateMonthlyAverage(payPeriod, income);

        System.out.println("Pay Period: " + payPeriod);
        System.out.printf("Income: $%.2f\n", income);
        System.out.printf("Monthly Average: $%.2f\n", monthlyAverage);

        scan.close();
    }

    static double calculateMonthlyAverage(final int payPeriod, final double income)
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
