package com.epam.test.jms;

import java.io.Serializable;

public class SampleMessage implements Serializable {

	private String string;
	private Integer integer;
	private Boolean bool;
	private String message;
	

	public SampleMessage(String foo, Integer bar, Boolean baz, String message) {
		super();
		this.string = foo;
		this.integer = bar;
		this.bool = baz;
		this.message = message;
	}

	/**
	* 
	* @return
	* The foo
	*/
	public String getFoo() {
	return string;
	}

	/**
	* 
	* @param foo
	* The foo
	*/
	public void setFoo(String foo) {
	this.string = foo;
	}

	/**
	* 
	* @return
	* The bar
	*/
	public Integer getBar() {
	return integer;
	}

	/**
	* 
	* @param bar
	* The bar
	*/
	public void setBar(Integer bar) {
	this.integer = bar;
	}

	/**
	* 
	* @return
	* The baz
	*/
	public Boolean getBaz() {
	return bool;
	}

	/**
	* 
	* @param baz
	* The baz
	*/
	public void setBaz(Boolean baz) {
	this.bool = baz;
	}

	/**
	* 
	* @return
	* The message
	*/
	public String getMessage() {
	return message;
	}

	/**
	* 
	* @param message
	* The message
	*/
	public void setMessage(String message) {
	this.message = message;
	}

	@Override
	public String toString() {
		return String.format("Message: %s, \n string: %s, \n int: %s, \n boolean: %s", message, string, integer, bool);
	}
}
