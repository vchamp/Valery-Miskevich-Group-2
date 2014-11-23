package com.epam.test;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.epam.test.io.IO;
import com.epam.test.model.Account;
import com.epam.test.model.Currency;

@ManagedBean
@RequestScoped
public class UserBean {

	@ManagedProperty(value = "#{param.userName}")
	public String userName;
	
	public Account userAccount;

	public float newSum;
	public Currency newCurrency = Currency.BYR;
	
	public void setNewSum(float sum){
		newSum = sum;
	}
	public String getNewSum(){
		return String.valueOf(newSum);
	}
	public void setNewCurrency(String currency){
		newCurrency = Currency.resolveByName(currency);
	}
	
	public String getNewCurrency(){
		return newCurrency.name();
	}
	public void setUserName(String name) {
		if(name == null)return;
		userName = name;
		userAccount = new Account(name, 100, Currency.BYR);
		IO.get().write(userAccount);
//		IO.get().clearAllData();
	}
	
	public String getUserName() {
//		IO.get().accountList().toString();
		return userName;
	}
	
	public String getUserAccount() {
//		IO.get().accountList().toString();
		if(userAccount == null)return "null";
		return userAccount.toString();
	}
	
	public void addBill() {
		goTo("addBillPage.xhtml");
	}
	private void goTo(String path) {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
			externalContext.redirect(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void userPage() {
		goTo("userPage.xhtml");
	}
	public Currency[] getCurrencies(){
		return Currency.values();
	}
}
