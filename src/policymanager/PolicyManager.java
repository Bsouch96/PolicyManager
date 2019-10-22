/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package policymanager;

import java.util.*;
import java.io.*;


/**
 *
 * @author Souchys
 */
public class PolicyManager
{
    public static void main(String[] args)
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Hello and welcome to Gadget Protector.");

        boolean exit = false;
        
        do
        {
            System.out.println("\nPress 1 to start a new policy\nPress 2 to display a summary of policies\nPress 3 to display a summary of policies for a selected month,\nPress 4 to find and display a policy\nPress 0 to exit.");         
            int menuOption = keyboard.nextInt();
            
            switch (menuOption)
            {
               case 1:
                    System.out.println("You have selected option 1.");
                    getOption1();   
                    break;
                case 2:
                    System.out.println("You have selected option 2.");
                    getOption2();           
                    break;
                case 3:
                    System.out.println("You have selected option 3.");    
                    getOption3();
                    break;
                case 4:
                    System.out.println("You have selected option 4.");
                    getOption4();
                    break;
                case 0:
                    System.out.println("Thank you for your custom at Gadget Protector today.");
                    exit = true;
                    break;
                default:
                    System.out.println("Error! Invalid menu option.");
                    break;
            }
        }            
        while (!exit);                
    }

    //Method to obtain all the policy details.
    public static void getOption1()
    {
        Scanner keyboard = new Scanner(System.in);
        Policy newPolicy = new Policy();
        
        System.out.println("Please enter your name. (eg. Ben Souch)");
        String name = getName(keyboard);
        newPolicy.setClientName(name);
        
        System.out.println("Now, please enter your reference code. (eg. BS123S)");
        String refCode = getRefCode(keyboard);
        newPolicy.setRefCode(refCode);
        
        System.out.println("Now, please enter how many gadgets you would like to insure. We accept a maximum of 5 on one policy. (eg. 3)");
        int numOfGadgets = getNumOfGadgets(keyboard);
        newPolicy.setNumOfGadgets(numOfGadgets);
        newPolicy.setNumOfGadgetsArrIndex();
        
        System.out.println("Now, please enter the value of your most expensive item in pounds. (eg. 500)");
        int gadgetValue = getGadgetValue(keyboard);
        newPolicy.setGadgetValue(gadgetValue);
        newPolicy.setGadgetValueCat();
        newPolicy.setGadgetCatArrIndex();
        
        System.out.println("Excess charges start at £30. For each additional £10, up to a maximum of £70, your premium is discounted by 5%. (eg. 50)");
        int excess = getExcess(keyboard);
        newPolicy.setExcess(excess);
        
        System.out.println("Please choose 'M' for monthly installments or 'A' for an annual sum. If you wish to pay annually, you are rewarded with a further 10% off of your premium.");
        String payTerms = getPayTerms(keyboard);
        newPolicy.setPayTerms(payTerms);
        
        newPolicy.setDiscount();

        newPolicy.setPremium();
        
        newPolicy.setDate();
        
        newPolicy.isRejectedPolicy();

        writePolicy(newPolicy.policyToString(), newPolicy.isRejectedPolicy());
        
        newPolicy.setSummaryPayTerms();
        
        newPolicy.setSummaryNumOfGadgetsDisplay();
        
        newPolicy.displaySummary();
    }

    //Method to get the client's name.
    public static String getName(Scanner keyboard)
    {
        String clientName = keyboard.nextLine();
        String validateClientName = checkName(keyboard, clientName);
        
        System.out.println("Thank you.");
        
        return validateClientName;
    }
    
    //Validation of input for client's name.    
    public static String checkName(Scanner keyboard, String clientNameIn)
    {      
        while (!clientNameIn.matches("^\\b[A-Z][A-Za-z\\-' ]*{3,20}$"))
        {
            System.out.println("Error! Please enter your name.");
            clientNameIn = keyboard.nextLine();
        }
        
        return clientNameIn;
    }
    
    //Method to get the reference code from the user.
    public static String getRefCode(Scanner keyboard)
    {
        String ref = keyboard.nextLine();
        String validateRef = checkRefCode(keyboard, ref);

        System.out.println("Thank you.");
        
        return validateRef;
    }
    
    //Method to validate the input for the client's reference code.
    public static String checkRefCode(Scanner keyboard, String refIn)
    {        
        //Validation to ensure reference code length is equal to 6 characters.
        while (refIn.length() != 6)
        {
            System.out.println("Error! Your reference code should be 6 characters long.");
            refIn = keyboard.nextLine();
        }
        
        //Validation to ensure specific characters at their relevant index.
        while (!(Character.isUpperCase(refIn.charAt(0)) && Character.isUpperCase(refIn.charAt(1)) && Character.isDigit(refIn.charAt(2)) && Character.isDigit(refIn.charAt(3)) && Character.isDigit(refIn.charAt(4)) && Character.isUpperCase(refIn.charAt(5))))
        {
            System.out.println("Error! Please enter a valid reference code.");
            refIn = keyboard.nextLine();
        }
        
        return refIn;
    }
    
    //Method to get the client's number of gadgets.
    public static int getNumOfGadgets(Scanner keyboard)
    {
        int gadgets = keyboard.nextInt();
        int validateGadgets = checkNumOfGadgets(keyboard, gadgets);
        System.out.println("Thank you.");   
        
        return validateGadgets;
    }
    
    //Method to validate the client's input.
    public static int checkNumOfGadgets(Scanner keyboard, int gadgetsIn)
    {
        while (gadgetsIn <1)
        {
            System.out.println("Error! Please enter a positive number of gadgets.");
            gadgetsIn = keyboard.nextInt();
        }
        
        return gadgetsIn;
    }
    
    //Method to get the value of the most expensive gadget from the user.
    public static int getGadgetValue(Scanner keyboard)
    {
        int clientGadgetValue = keyboard.nextInt();
        int validateGadgetValue = checkGadgetValue(keyboard, clientGadgetValue);
        validateGadgetValue *= 100; //Times the gadget value by 100 to get the value in pence.
                                    //Caution, not timesing this value by 100 changes it's array index and gadget value category value later in the program.
        System.out.println("Thank you.");
        
        return validateGadgetValue;
    }
    
    //Method to validate user input for the gadget value.
    public static int checkGadgetValue(Scanner keyboard, int gadgetValueIn)
    {
        while (gadgetValueIn <1)
        {
            System.out.println("Error! Please enter a value greater than 0.");
            gadgetValueIn = keyboard.nextInt();
        }
        
        return gadgetValueIn;
    }    
    
    //Method to get the excess from the client
    public static int getExcess(Scanner keyboard)
    {
        int clientExcess = keyboard.nextInt();
        int validateClientExcess = checkClientExcess(keyboard, clientExcess);
        validateClientExcess *= 100; //Times by 100 to get value of excess in pence.
        
        System.out.println("Thank you.");
        
        return validateClientExcess;
    }
    
    //Method to validate the user inputfor excess.
    public static int checkClientExcess(Scanner keyboard, int clientExcessIn)
    {
        while (!(clientExcessIn >=30 && clientExcessIn <=70 && clientExcessIn % 10 == 0))
        {
            System.out.println("Error! Please enter your excess between 30 and 70. Ensure it is a multiple of 10.");
            clientExcessIn = keyboard.nextInt();
        }    
        
        return clientExcessIn;
    }    
   
    //Method to get the payment terms from the user.
    public static String getPayTerms(Scanner keyboard)
    {
        String clientPayTerms = keyboard.next();
        String validateClientPayTerms = checkPayTerms(keyboard, clientPayTerms);

        return validateClientPayTerms;
    }
    
    //Method to validate the client's input for their payment terms.
    public static String checkPayTerms(Scanner keyboard, String clientPayTermsIn)
    {
        while (!(clientPayTermsIn.matches("A") || clientPayTermsIn.matches("M")))
        {
            System.out.println("Error! Please choose your payment terms. M for monthly, A for annual.");
            clientPayTermsIn = keyboard.next();
        }
        
        return clientPayTermsIn;        
    }
    
    //Method to write policy.
    public static void writePolicy(String policy, boolean rejectedPolicy)
    {
        PrintWriter printer = null;

        try
        {
            FileWriter writer = new FileWriter("policy.txt", true);
            BufferedWriter buffer = new BufferedWriter(writer);
            printer = new PrintWriter(buffer);

            printer.println(policy);
        }
        catch (IOException e)
        {
            System.out.println("Error! File does not exist.");
        }
        finally
        {
            printer.close();
        }
    }
    
    //Method to get data and display option 2.
    public static void getOption2()
    {
        System.out.println("Coming soon!");
    }
    
    //Method to get data for option 3.
    public static void getOption3()
    {
        System.out.println("Coming soon!");        
    }    
    
    //Method to get data for option 4.
    public static void getOption4()
    {
        System.out.println("Coming soon!");        
    }    
}