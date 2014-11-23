package com.epam.test;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class TestBean {

	public String getString() {
		return "written by bean";
	}
	
	public String getStringForXhtml() {
		return "written by bean for xhtml";
	}
}
