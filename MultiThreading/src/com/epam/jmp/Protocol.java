package com.epam.jmp;

import java.util.regex.Pattern;

import com.epam.jmp.model.Account;
import com.epam.jmp.model.Bank;
import com.epam.jmp.model.Currency;

public class Protocol {

	private static final int WAITING = 0;
    private static final int SENT_WELCOME = 1;
    private static final int LOGIN_AS_CLIENT = 2,
    		CLIENT_OPERATIONS = 3;
    private static final int OFFICE_OPERATIONS = 4;
    

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
            	
                theOutput = "Enter operation: (show all, search {value}, filter {> ballance, < balance}, add {account name}, remove {account name})";
                state = OFFICE_OPERATIONS;
                
            } else {
            	
                theOutput = "You're supposed to \"1\" or \"2\" $$ Try again.";
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
        	} 
        	else {
        		theOutput = "Please, enter correct command";
        	}

        } else if (state == OFFICE_OPERATIONS) {
        	
        	
        	if(theInput.equalsIgnoreCase("show all")) {
        		
        		theOutput = Bank.get().print();
        		
        	} else if(theInput.startsWith("search ")) {
        		
        		String[] data = theInput.split(" ", 2);
        		String reults = Bank.get().search(data[1]);
        		theOutput = reults;
        		
        	} else if(theInput.equalsIgnoreCase("filter ")) {
        		
        		String[] data = theInput.split(" ", 3);
        		
        		if(data.length!=3 && (data[1].equals(">") || data[1].equals("<")) && checkNumberFormat(data[2])) 
        			theOutput = "Please, enter correct command";
        		else {
        			String results = Bank.get().filter(data[1], data[2]);
        			theOutput = results;
        		}
        		
        	} else if(theInput.startsWith("add ")) {
        		String[] data = theInput.split(" ", 2);
        		if(checkName(data[1])) {
        			theOutput = Bank.get().openOrCreateAccount(data[1]).print();
        		} else {
        			theOutput = "Enter correct account name.";
        		}
        		
        	} else if(theInput.startsWith("remove ")) {
        		String[] data = theInput.split(" ", 2);
        		if(checkName(data[1])) {
        			if(Bank.get().deleteAccount(data[1])){
        				theOutput = "Account "+data[1]+ " deleted.";
        			} else {
        				theOutput = "Error deleting "+data[1]+ ". ";
        			}
        		} else {
        			theOutput = "Enter correct account name.";
        		}
        	} 
        	else {
        	
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
