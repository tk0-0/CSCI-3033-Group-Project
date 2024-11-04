

import java.io.*;
import java.util.Scanner;


public class MainDriver {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); 
        String firstName, lastName, payFrequency; 
        double monthlyIncome = 0; 
        int runFlag; 
        
        String userFile = "user_data.txt";
        File file = new File(userFile); 
        
        // if current user already has a profile set up then proceed, otherwise create new profile
        if (file.exists()) {
        	try {
        		// br is file reader object
        		BufferedReader br = new BufferedReader(new FileReader(userFile)); 
        		
        		// extracting data from file
        		firstName = br.readLine();
        		lastName = br.readLine();
        		payFrequency = br.readLine();
        		monthlyIncome = Double.parseDouble(br.readLine()); 
        		
        		br.close(); 
        		
        		System.out.println("Welcome Back " + firstName + " " + lastName);  
        		
        		while(true) {
        			System.out.println();
        			System.out.println("1) Create a budget plan."); 
        			System.out.println("2) Manage Profile"); 
        			System.out.println("3) Delete Profile");
        			System.out.println("4) Quit"); 
        			System.out.print("Select an Option: "); 
        			
        			runFlag = input.nextInt(); 
        			input.nextLine(); 
        			
        			if (runFlag == 2) {
        				System.out.println("1) Update name."); 
        				System.out.println("2) Update pay frequency.");
        				System.out.println("3) Update net income frequency.");
        				System.out.println("4) Reset profile"); 
        				System.out.println("5) Return back to main menu"); 
        			}
        			
        			if (runFlag == 3) {
        				String strFlag; 
        				System.out.print("Please confirm to delete profile (Enter Yes or No): "); 
        				
        				strFlag = input.nextLine(); 
        				
        				if (strFlag.equalsIgnoreCase("yes")) {
        					File fileDelete = new File("user_data.txt"); 
            				fileDelete.delete(); 
        				}
        				else {
        					continue; 
        				}
        			}
        			
        			if (runFlag == 4) {
        				break;
        			}
        		}
        	}
        	catch(Exception ex) {
        		return;
        
        		
        	}
        	
        }
        else {
        	
        	System.out.println("Welcome to the Personal Budget Planner!\n");
            System.out.println("1) Start Budget Planning."); 
            System.out.println("2) Quit"); 
            System.out.print("Select an option: "); 
            runFlag = input.nextInt(); 
            // ignore newline
            input.nextLine();


	        while (runFlag != 2) {      
	            if (runFlag == 1) {
	                System.out.print("Enter your First Name: "); 
	                firstName = input.nextLine(); 
	                
	                System.out.print("Enter your Last Name: "); 
	                lastName = input.nextLine(); 
	                
	                System.out.println(); 
	                System.out.print("Enter pay frequency (weekly, biweekly, or monthly): "); 
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