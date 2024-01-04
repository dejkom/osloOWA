package com.odelowebapp.authentication.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.security.SecureRandom;

@Named
@ViewScoped
public class TestViewBean implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(TestViewBean.class);
	SecureRandom rng = new SecureRandom();

	private static final long serialVersionUID = 1L;
	
	private String testString;
	private int id;

	public String getTestString() {
		return testString;
	}

	public void setTestString(String testString) {
		this.testString = testString;
	}
	
	public void submit() {
		logger.info("submitted to viewscoped bean #" + id);
	}

	@PostConstruct
	public void init() {
		this.id = rng.nextInt(1000);
		logger.info("Initialising " + this.getClass().getSimpleName() + " with " + id);
	}

}
