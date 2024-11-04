package Group_Project; 

import java.util.Scanner; 
// import for writing and reader from a file 
import java.io.*;


public class MainDriver {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); 
        String firstName, lastName, payFrequency; 
        double monthlyIncome = 0; 
        
        String userFile = "user_data.txt";
        File file = new File(userFile); 

        if (file.exists()) {
        	try {
        		BufferedReader br = new BufferedReader(new FileReader(userFile)); 

        		firstName = br.readLine();
        		lastName = br.readLine();
        		payFrequency = br.readLine();
        		monthlyIncome = Double.parseDouble(br.readLine()); 
        		
        		br.close(); 
        		
        		System.out.println("Welcome Back " + firstName + " " + lastName);  
        	
        		
        		
        	}
        	catch(Exception ex) {
        		System.out.print("LOL");
        
        		
        	}
        	
        }
        else {
        	
        	System.out.println("Welcome to the Personal Budget Planner!\n");
            System.out.println("1) Start Budget Planning."); 
            System.out.println("2) Quit"); 
            System.out.print("Select an option: "); 
            int runFlag = input.nextInt(); 
            input.nextLine(); // ignore newline


	        while (runFlag != 2) {      
	            if (runFlag == 1) {
	                System.out.print("Enter your First Name: "); 
	                firstName = input.nextLine(); 
	                
	                System.out.print("Enter your Last Name: "); 
	                lastName = input.nextLine(); 
	                
	                System.out.println(); 
	                System.out.print("Please enter pay frequency (weekly, biweekly, or monthly): "); 
	                payFrequency = input.nextLine(); 
	                
	                if  (payFrequency.equalsIgnoreCase("weekly")) {
	                    System.out.print("Enter weekly net income (after taxes): "); 
	                    monthlyIncome = input.nextDouble() * 4;
	                }
	                else if (payFrequency.equalsIgnoreCase("biweekly")) {
	                    System.out.print("Enter biweekly net income (after taxes): "); 
	                    monthlyIncome = input.nextDouble() * 2;
	                }
	                else if (payFrequency.equalsIgnoreCase("monthly")) {
	                    System.out.print("Enter monthly net income (after taxes): "); 
	                    monthlyIncome = input.nextDouble();
	                }
	                else {
	                    System.out.println("Invalid pay frequency!");
	                }
	                
	                // Clear the buffer
	                input.nextLine(); 
	
	                // Append user data to the file
	                try {
	                	BufferedWriter bw  = new BufferedWriter(new FileWriter("user_data.txt"));
	                	bw.write(firstName + "\n" + lastName + "\n");
	                	bw.write(payFrequency + "\n");
	                	bw.write(monthlyIncome + "\n");
	                	
	                	
	                	bw.close(); 
	                }
	                catch(Exception ex) {
	                	return; 
	                }
	                break;
	            }
	            System.out.println();
	            System.out.println("Invalid Option, try again!"); 
	            System.out.println("1) Start Budget Planning."); 
	            System.out.println("2) Quit"); 
	            System.out.print("Select an option: "); 
	            runFlag = input.nextInt(); 
	            // Clear the buffer
	            input.nextLine();  
	        }
        }
        input.close(); 
    }   
}