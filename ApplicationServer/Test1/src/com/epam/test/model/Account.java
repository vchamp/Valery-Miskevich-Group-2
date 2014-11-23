package com.epam.test.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Account {
	
	private static final String SEPARATOR = ";";
	
	private String ownerName;
	private List<Bill> billList;
	
	public static class Bill {
		int id;
		float ballance;
		Currency currency;
		
		public Bill(float ballance2, Currency currency2) {
			ballance=ballance2;
			currency=currency2;
			id = new Random().nextInt(1000);
		}
		
		public static Bill parse(String input){
			Bill result = null;
			if(input!=null){
				String[] values = input.split(SEPARATOR);
				if(input.length()==2){
					result = new Bill(Float.valueOf(values[1]),Currency.resolveByName(values[2]));
				}
			} 
			return result;
		}
	}
	
	private Account() {
		billList = new ArrayList<Account.Bill>();
	}
	
	public Account(String name, float ballance, Currency currency) {
		this(name);
		Bill bill = new Bill(ballance, currency);
		billList.add(bill);
	}
	
	public Account(String name) {
		this();
		this.ownerName = name;
	}
	
	public void addBill(Bill bill) {
		if(bill != null) {
			billList.add(bill);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(ownerName).append("\n");
		for(Bill b : billList){
			builder.append(b.ballance+SEPARATOR+b.currency.name());
			builder.append("\n");
		}
		return builder.toString();
	}
	
	public String print() {
		StringBuilder builder = new StringBuilder();
		builder.append(ownerName).append("\n");
		for(Bill b : billList){
			builder.append(b.ballance+"\t|\t"+b.currency.name());
			builder.append("\n");
		}
		return builder.toString();
	}

	public String getName() {
		return ownerName;
	}

	public void setName(String name) {
		this.ownerName = name;
	}

	public float getBallance(int billId) {
//		if(billId > billList.size())
//			return -1;
		return billList.get(billId).ballance;
	}

	public void setBallance(int billId, float ballance) {
//		if(billId > billList.size())
//			return;
		billList.get(billId).ballance = ballance;
	}

	public void transfer(int fromBillId, int toBillId, float summ){
//		if(fromBillId > billList.size() || toBillId > billList.size())
//			return;
		Bill fromBill = billList.get(fromBillId);
		Bill toBill = billList.get(toBillId);
		float convertedSumm = Currency.convert(fromBill.currency, toBill.currency, summ);
		depositMoney(toBillId, convertedSumm);
		withdrawMoney(fromBillId, summ);
	}
	
	public void depositMoney(int billId, Float value) {
//		if(billId > billList.size())
//			return;
		billList.get(billId).ballance += value;
		
	}
	
	public void withdrawMoney(int billId, Float value) {
//		if(billId > billList.size())
//			return;
		billList.get(billId).ballance -= value;
	}
	
	
}
