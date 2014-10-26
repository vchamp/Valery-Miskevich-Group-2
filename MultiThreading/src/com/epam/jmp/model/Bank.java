package com.epam.jmp.model;

import java.util.ArrayList;

import com.epam.jmp.Client;
import com.epam.jmp.io.IO;

public class Bank {

	private static Bank instance;
	

	public synchronized static Bank get() {
		if(instance == null){
			instance = new Bank();
		}
		return instance;
	}
	
	private Bank() {
		super();
	}

	
	public Account getAccount(String name) {
		if(IO.get().accountList().contains(name)){
			return IO.get().read(name);
		}
		return null;
	}
	
	public Account openOrCreateAccount(String name) {
		Account a = getAccount(name);
		if(a == null) {
			a = new Account(name, 0, Currency.BYR);
			IO.get().write(a);
		}
		return a;
	}
	
	public String print() {
		StringBuilder builder = new StringBuilder();
		for(Account a : getClientAccounts()) {
			builder.append(a.print()).append("$$");
		}
		return builder.toString();
	}

	private ArrayList<Account> getClientAccounts() {
		ArrayList<Account> clients = new ArrayList<>();
		for(String s : IO.get().accountList()){
			clients.add(IO.get().read(s));
		}
		return clients;
	}

	public void updateAccount(Account account) {
		IO.get().write(account);
		
	}
}
