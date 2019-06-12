package com.securepay.steps;
import java.util.List;

import com.securepay.webdriver.WebConnector;

import cucumber.api.java.en.And;
import cucumber.api.java.en.When;


public class ApplicationSteps{
	
	WebConnector con;
	
	public ApplicationSteps(WebConnector con) {
		this.con=con;
	}
	
	@When("^User searches for (.*) on (.*)$")
	public void NavigateToULRAndSearchOnGoogle(String textToSearch , String url) {
		con.NavigateToULRAndSearchOnGoogle(textToSearch, url);
	}

	@And("^User fills the contact us form$")
	public void fillContactForm() {
		con.fillContactForm();
		
	}
	
}
