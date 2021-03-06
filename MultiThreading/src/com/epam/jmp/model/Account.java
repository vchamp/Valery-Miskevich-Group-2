package com.epam.jmp.model;

public class Account {
	
	private static final String SEPARATOR = ";";
	private String name;
	private float ballance;
	private Currency currency;
	
	public Account(String name, float ballance, Currency currency) {
		super();
		this.name = name;
		this.ballance = ballance;
		this.currency = currency;
	}
	
	public Account(String input) {
		super();
		String[] values = input.split(SEPARATOR);
		
		this.name = values[0];
		this.ballance = Float.valueOf(values[1]);
		this.currency = Currency.resolveByName(values[2]);
	}
	
	@Override
	public String toString() {
		
		return name + SEPARATOR + ballance + SEPARATOR + currency.name();
	}
	
	public String print() {
		
		return name + "\t|\t" + ballance + "\t|\t" + currency.name();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getBallance() {
		return ballance;
	}

	public void setBallance(float ballance) {
		this.ballance = ballance;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		ballance = Currency.convert(this.currency, currency, ballance);
		this.currency = currency;
	}

	public void depositMoney(Float value) {
		ballance+=value;
		
	}
	
	public void withdrawMoney(Float value) {
		ballance-=value;
	}
	
	
}
