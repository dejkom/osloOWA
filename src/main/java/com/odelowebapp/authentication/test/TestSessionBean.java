package com.odelowebapp.authentication.test;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class TestSessionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String testString;

	public String getTestString() {
		return testString;
	}

	public void setTestString(String testString) {
		this.testString = testString;
	}
	
	public void submit() {
		
	}
	
	

}
