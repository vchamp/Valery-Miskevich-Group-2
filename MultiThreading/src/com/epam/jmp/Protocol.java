package com.epam.jmp;

import com.epam.jmp.model.Account;
import com.epam.jmp.model.Bank;

public class Protocol {

	private static final int WAITING = 0;
    private static final int SENT_WELCOME = 1;
    private static final int LOGIN_AS_CLIENT = 2,
    		CLIENT_OPERATIONS = 3;
    private static final int OFFICE_OPERATIONS = 4;
    
    private static final int ANOTHER = 3;


    private int state = WAITING;


    public String processInput(String theInput) {
    	
        String theOutput = null;

        if (state == WAITING) {
            theOutput = "Welcome! Enter as: client [1] ,office [2]";
            state = SENT_WELCOME;
        } else if (state == SENT_WELCOME) {
        	
            if (theInput.equalsIgnoreCase("1")) {
                
            	theOutput = "Enter your name:";
                state = LOGIN_AS_CLIENT;
                
            } else if (theInput.equalsIgnoreCase("2")) {
            	
                theOutput = "Input operations: (search {value}, filter {name, ballance, currency})";
                state = OFFICE_OPERATIONS;
                
            } else {
            	
                theOutput = "You're supposed to \"1\" or \"2\" \n Try again.";
            }
        } else if (state == LOGIN_AS_CLIENT) {
        	
        	Account acc = openOrCreateAccount(theInput);
        	theOutput = printAccount(acc);
        	state = CLIENT_OPERATIONS;
        	
        } else if (state == OFFICE_OPERATIONS) {
        	
            Bank bank = getBank();
            theOutput = printBank(bank);
        }
        return theOutput;
    }
    
    private String printBank(Bank bank) {
		// TODO Auto-generated method stub
		return null;
	}

	private Bank getBank() {
		// TODO Auto-generated method stub
		return null;
	}

	private String printAccount(Account acc) {
		// TODO Auto-generated method stub
		return null;
	}

	private Account openOrCreateAccount(String theInput) {
		//for(String )
		return null;
	}

	static class ClientProtocol {
    	
    }
    
    static class OfficeProtocol {
    	
    }
}
