package com.epam.jmp;

import java.util.regex.Pattern;

import com.epam.jmp.io.IO;
import com.epam.jmp.model.Account;
import com.epam.jmp.model.Bank;
import com.epam.jmp.model.Currency;

public class Protocol {

	private static final String NEW_LINE = System.getProperty("line.separator");
	
	private static final int WAITING = 0;
    private static final int SENT_WELCOME = 1;
    private static final int LOGIN_AS_CLIENT = 2,
    		CLIENT_OPERATIONS = 3;
    private static final int OFFICE_OPERATIONS = 4;
    
    private static final int ANOTHER = 3;


    private int state = WAITING;

    private Account currentAccount;

    public String processInput(String theInput) {
    	
    	if(theInput != null && theInput.equals("quit")){
    		return "Bye.";
    	}
    	
        String theOutput = null;

        if (state == WAITING) {
            theOutput = "Welcome! $$ Enter as: client [1], office [2]";
            state = SENT_WELCOME;
        } else if (state == SENT_WELCOME) {
        	
            if (theInput.equalsIgnoreCase("1")) {
                
            	theOutput = "Enter your name:";
                state = LOGIN_AS_CLIENT;
                
            } else if (theInput.equalsIgnoreCase("2")) {
            	
                theOutput = "Input operations: (show all, search {value}, filter {name, ballance, currency})";
                state = OFFICE_OPERATIONS;
                
            } else {
            	
                theOutput = "You're supposed to \"1\" or \"2\" \n Try again.";
            }
        } else if (state == LOGIN_AS_CLIENT) {
        	if(checkName(theInput)){
	        	currentAccount = Bank.get().openOrCreateAccount(theInput);
	        	theOutput = currentAccount.print() + "$$"
	        			+ "To add money enter: add {value} $$"
	        			+ "To remove money enter: remove {value} $$"
	        			+ "To exchange currency enter: exchange {value} (avilable: EUR, USD, GBP, RUR, BYR)  $$"
	        			+ "To close enter: qiut";
	        	
	        	state = CLIENT_OPERATIONS;
        	} else {
        		theOutput = "Please, enter correct name";
        	}
        	
        } else if (state == CLIENT_OPERATIONS) {
        	
        	String[] data = theInput.split(" ", 2);
        	
        	if(theInput.startsWith("add ")) {
        		
        		if(checkNumberFormat(data[1])){
        			currentAccount.depositMoney(Float.valueOf(data[1]));
        			Bank.get().updateAccount(currentAccount);
        			theOutput = currentAccount.print() + " $$ Enter another operation: ";
        		} else {
        			theOutput = "Check number format";
        		}
        		
        	} else if(theInput.startsWith("remove ")) {
        		
        		if(checkNumberFormat(data[1])){
        			currentAccount.withdrawMoney(Float.valueOf(data[1]));
        			Bank.get().updateAccount(currentAccount);
        			theOutput = currentAccount.print() + " $$ Enter another operation: ";
        		} else {
        			theOutput = "Check number format.";
        		}
        		
        	} else if(theInput.startsWith("exchange ")) {
        		Currency c = Currency.resolveByName(data[1]);
        		if(c == null) {
        			theOutput = "Wrong currency name.";
        		} else {
        			currentAccount.setCurrency(c);
        			Bank.get().updateAccount(currentAccount);
        			theOutput = currentAccount.print() + " $$ Enter another operation: ";
        		}
        	} else {
        		theOutput = "Please, enter correct command";
        	}

        } else if (state == OFFICE_OPERATIONS) {
        	
        	if(theInput.equalsIgnoreCase("show all")) {
        		
        		theOutput = Bank.get().print();
        		
        	} else if(theInput.startsWith("search ")) {
        		
        	} else if(theInput.equalsIgnoreCase("filter ")) {
        		
        	} else {
        	
        		theOutput = "Please, enter correct command";
            }
        }
        return theOutput;
    }
    

	private boolean checkNumberFormat(String string) {
		if(Pattern.matches("^[\\d]+\\.?[\\d]*$", string)){
			return true;
		} else
			return false;
	}


	private boolean checkName(String theInput) {
		if(Pattern.matches("^[\\w]{1,50}-?[\\w]{1,50}$", theInput)){
			return true;
		} else
			return false;
	}


}
