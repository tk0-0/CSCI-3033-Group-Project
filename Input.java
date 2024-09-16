import java.util.Scanner; 

public class Input {
    public static void main(String[] args) {
        int run = 0; 
        int userOption;

        Scanner input = new Scanner(System.in);

        System.out.println("Personal Budget Manager");
        
        while (run >= 0)
        {
            System.out.print("Select an option or enter -1 to exit: "); 
            userOption = input.nextInt();

            if (userOption == -1)
                run = -1;
        }

        input.close();
    }
}
