package com.odelowebapp.authentication.test;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ApplicationScoped
public class TestApplicationBean implements Serializable {

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
