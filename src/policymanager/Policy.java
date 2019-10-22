package policymanager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Policy
{
    private String todaysDate;
    private String refCode;
    private int numOfGadgets;
    private int gadgetValue;
    private int excess;
    private int premium;
    private String payTerms;
    private String name;
    
    private int discount;
    private int gadgetValueCat;
    private int numOfGadgetsArrIndex;
    private int gadgetValueCatArrIndex;
    private String summaryPayTerms;
    private String summaryNumOfGadgets;
    
    //Constructor initialiser
    Policy()
    {
        todaysDate = null;
        refCode = null;
        numOfGadgets = 0;
        gadgetValue = 0;
        excess = 0;
        premium = 0;
        payTerms = null;
        name = null;
        discount = 0;
        gadgetValueCat = 0;
        numOfGadgetsArrIndex = 0;
        gadgetValueCatArrIndex = 0;
        summaryPayTerms = null;
        summaryNumOfGadgets = null;
    }
    
    //Method to acquire today's date for the summary.
    public void setDate()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        
        todaysDate = sdf.format(cal.getTime());
    }
    
    //Method to return the stored value of todaysDate.    
    public String getTodaysDate()
    {
        return this.todaysDate;
    }
    
    //Method to set the client's reference code.
    public void setRefCode(String refCodeIn)
    {
        refCode = refCodeIn;
    }
    
    //Method to return the stored value of refCode.
    public String getRefCode()
    {
        return this.refCode;
    }
    
    //Method to set the number of gadgets.
    public void setNumOfGadgets(int numOfGadgetsIn)
    {
        numOfGadgets = numOfGadgetsIn;
    }
    
    //Method to return the stored value of numOfGadgets.
    public int getNumOfGadgets()
    {
        return this.numOfGadgets;
    }
    
    //Method to set the client's most expensive item.
    public void setGadgetValue(int numOfGadgetsIn)
    {
        gadgetValue = numOfGadgetsIn;
    }
    
    //Method to return the stored value of getGadgetValue.
    public int getGadgetValue()
    {
        return this.gadgetValue;
    }
    
    //Method to set excess from client.
    public void setExcess(int clientExcess)
    {
        excess = clientExcess;    

    }
    
    //Method to return the stored value of excess.
    public int getExcess()
    {
        return this.excess;
    }
    
    //Method to use stored variables and work out the client's final premium.
    public void setPremium()
    {
        int arrayPremium = getMonthlyPremium();
        int discountPrice = 0;
        
        //Determines the final premium for the annual option.
        if (payTerms.matches("A"))
        {
            discountPrice = (arrayPremium * 12 * discount) / 10000; //Divide by 10000 to return to pence. Built up from excess and discount.
            arrayPremium = arrayPremium * 12 - discountPrice;
        }
        else if (payTerms.matches("M") && discount != 0) //Determines the final premium for the monthly option.
        {
            discountPrice = (arrayPremium * discount) / 10000;
            arrayPremium -= discountPrice;
        }
        
        premium = arrayPremium;
    }
    
    //Method to return the stored value of premium.
    public int getPremium()
    {
        return this.premium;
    }
    
    //Method to get and validate client's choice of payment terms.
    public void setPayTerms(String clientPayTerms)
    {
        payTerms = clientPayTerms;
    }
    
    //Method to return the payment terms.
    public String getPayTerms()
    {
        return this.payTerms;
    }
    
    //Method to set the client's name.
    public void setClientName(String clientName)
    {
        name = clientName;
    }
    
    //Method to return the stored value of client's name.
    public String getName()
    {
        return this.name;
    }
    
    //Method to set discount if the client is eligable.
    public void setDiscount()
    {
        if (excess >3000)
        {
            discount = (excess - 3000)/10*5;
        }
        
        if (payTerms.matches("A"))
        {
            discount += 1000;
        }
    }
    
    //Method to return the contents of the class for writing to file.
    public String policyToString()
    {

        if(isRejectedPolicy())
        {
            premium = -1;
            payTerms = "R";
        }
        
        return todaysDate + "\t" + refCode + "\t" + numOfGadgets + "\t" + gadgetValue/100 + "\t" + excess/100 + "\t" + premium + "\t" + payTerms + "\t" + name;
    }
    
    //Method to define the price category based on the value of the client's gadget.
    public void setGadgetValueCat()
    {
        gadgetValueCat = 0;
        
        if (gadgetValue <= 50000)
        {
            gadgetValueCat = 500;
        }
        else if (gadgetValue >50000 && gadgetValue <=80000)
        {
            gadgetValueCat = 800;
        }
        else if (gadgetValue >80000 && gadgetValue <=100000)
        {
            gadgetValueCat = 1000;
        }
    }
    
    //Method to get the array index from the number of gadgets. This is used to get the premium.
    public void setNumOfGadgetsArrIndex()
    {
        switch (numOfGadgets)
        {
            case 1:
                numOfGadgetsArrIndex = 0;
                break;  
            case 2:
                numOfGadgetsArrIndex = 1;
                break;             
            case 3:
                numOfGadgetsArrIndex = 1;
                break;
            case 4:
                numOfGadgetsArrIndex = 2;
                break;    
            case 5:
                numOfGadgetsArrIndex = 2;
                break;
        }            
    }
    
    //Method to get the array index of the gadget value. This is used to get the premium.
    public void setGadgetCatArrIndex()
    {
        switch (gadgetValueCat)
        {
            case 500:
                gadgetValueCatArrIndex = 0;
                break;  
                
            case 800:
                gadgetValueCatArrIndex = 1;
                break; 
                
            case 1000:
                gadgetValueCatArrIndex = 2;
                break;
        }            
    }
    
    //Method using an array to get the client's monthly premium.
    public int getMonthlyPremium()
    {
        //This array decides the monthly premium based on the number of gadgets and their price category.
        int monthlyPremiumArr[][] = 
        {
            {599, 715, 830},
            {1099, 1335, 1555},
            {1599, 1960, 2282}
        };
        
        int clientMonthlyPremium = monthlyPremiumArr[numOfGadgetsArrIndex][gadgetValueCatArrIndex];

        return clientMonthlyPremium;
    }
    
    //Method to check if a policy is rejected and, if true, adjusting variables accordingly.
    public boolean isRejectedPolicy()
    {
        if (numOfGadgets >5 || gadgetValue >100000)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    //Method to title the payment terms premium in the summary.
    public void setSummaryPayTerms()
    {
        if(payTerms.matches("A"))
        {
            summaryPayTerms = "Annual";
        }
        else
        {
            summaryPayTerms = "Monthly";
        }
    }    
    
    //Method to define how the number of items will be displayed on the summary message.
    public void setSummaryNumOfGadgetsDisplay()
    {
        switch (numOfGadgets)
        {
            case 1:
                String numOfGadgetsOne = "One";
                summaryNumOfGadgets = numOfGadgetsOne;
                break;
            case 2:
                String numOfGadgetsTwo = "Two";
                summaryNumOfGadgets = numOfGadgetsTwo;
                break;
            case 3:
                String numOfGadgetsThree = "Three";
                summaryNumOfGadgets = numOfGadgetsThree;
                break;
            case 4:
                String numOfGadgetsFour = "Four";
                summaryNumOfGadgets = numOfGadgetsFour;
            break;
            case 5:
                String numOfGadgetsFive = "Five";
                summaryNumOfGadgets = numOfGadgetsFive;
            break;
            default:
                summaryNumOfGadgets = String.valueOf(numOfGadgets);
        } 
    }    
    
    //Method to display the summary of the policy and includes variables to daisy chain writing of the policy.
    public void displaySummary()
    {      
        System.out.printf("+==========================================+\n");
        System.out.printf("|" + "%44s", "|\n");
        System.out.printf("|  Client: " + "%-32s", name);
        System.out.printf("|\n");
        System.out.printf("|" + "%44s", "|\n");
        System.out.printf("|    Date: " + todaysDate + "         Ref: " + "%-7s", refCode);
        System.out.printf("|\n");
        System.out.printf("|   Terms: " + payTerms + "                 Items: " + "%-7s", summaryNumOfGadgets);
        System.out.printf("|\n");
        System.out.printf("|  Excess: £" + "%5.2f ", (double)excess / 100);
        System.out.printf("%27s", "|\n");
        System.out.printf("|" + "%44s", "|\n");
        
        if (isRejectedPolicy())
        {
            String rejectGadgetLimit = "Rejected";              
            System.out.printf("|" + "%1$8s%2$25s%3$11s", summaryPayTerms, rejectGadgetLimit, "|\n");
            System.out.printf("| Premium: -1" + "%32s", "|\n");
        }
        else
        {
            System.out.printf("|" + "%1$8s %2$24s %3$10s", summaryPayTerms,  "Limit per", "|\n");
            System.out.printf("| Premium: £" + "%6.2f", (double)premium/100);
            System.out.printf("%1$18s%2$-7s%3$2s", "Gadget: ", gadgetValueCat, "|\n");
        }
        
        System.out.printf("|" + "%44s", "|\n");
        System.out.printf("+==========================================+\n");
    }
}