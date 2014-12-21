package com.alex.dzeshko;

public class ByAliasBean {
	private String message;

	public void setMessage(String message){
		this.message  = message;
	}
	
	public void getMessage(){
		System.out.println("Your Message : " + message);
	}
}
