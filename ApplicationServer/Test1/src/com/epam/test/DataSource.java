package com.epam.test;

import java.math.BigDecimal;
import java.util.List;
import com.epam.test.io.IO;
import com.epam.test.model.Account;

public class DataSource {

	private static DataSource instance = new DataSource();
	
	public static DataSource getInstance() {
		
		return instance;
	}
	
	public static BigDecimal getMoney(String userName, String account) {
		
		if ("Tim".equals(userName)) {
			return new BigDecimal(20);
		} else if ("Tom".equals(userName)) {
			return new BigDecimal(40);
		} else {
			return new BigDecimal(0);
		}
	}
	
	public List<Account> getAccountList(String ownerName) {
		if(ownerName == null || ownerName.length()==0)
			throw new IllegalArgumentException("Owner name is empty");
		Account account = null;
		IO io = IO.get();
		List<String> accountList = io.accountList();
		for(String acc : accountList){
			if(ownerName.equals(acc)){
				account = io.read(acc);
				break;
			}
		}
		return null;
		
	}
}
