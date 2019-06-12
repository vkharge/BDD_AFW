package com.securepay.steps;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.securepay.webdriver.WebConnector;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GenericSteps {

	WebConnector con;
	
	public GenericSteps(WebConnector con) {
		this.con = con;
	}
	
	@Before
	public void before(Scenario s) {
		System.out.println("***Before*** "+s.getName());
		con.initReports(s.getName());
	}
	
	@After
	public void after() {
		System.out.println("***After***");
		if (con != null) {
			con.quit();
		}
		
	}
	
	@Given("^User open (.*) browser$")
	public void openBrowser(String browserName) {		
		con.infoLog("Opening Browser " +browserName);
		con.openBrowser(browserName);
	}
	
	@And("^User navigate to (.*)$")
	public void navigate(String url) {
		con.infoLog("Navigating to "+ url);
		con.navigate(url);
	}

	@And("^User type '(.*)' in (.*) field$")
	public void type(String data,String locatorKey) {
		con.infoLog("Typing in "+locatorKey+". Data "+data);
		con.type(locatorKey, data);
	}
	@And("^User clear (.*) field$")
	public void clear(String locatorKey) {
		con.infoLog("Clearing in "+locatorKey);
		con.clear(locatorKey);
	}
	
	@When("^User click on (.*)$")
	public void click(String locatorKey) {
		con.infoLog("Clicking on "+ locatorKey);
		con.click(locatorKey);	
	}

	
	@And("^User click (.*) and wait for (.*)$")
	public void clickAndWait(String src,String target) {
		con.infoLog("Clicking on "+ src);
		con.clickAndWait(src, target, 20);	
	}
	
	@And("User select (.*) from (.*) field")
	public void select(String data,String locatorKey) {
		con.infoLog("Selecting from "+ locatorKey);
		con.select(locatorKey,data);
	}

	@Then("^User should wait for page to load with object (.*) on new page$")
	public void waitForPageToLoad(String locatorKey) {
		con.infoLog("Wait for the page to Load with Object " + locatorKey + " on Page ");
		con.waitForPageToLoad(locatorKey);
	}
	
	@Then("^Verify that page with title '(.*)' should be displayed$")
	public void verifyPageTitle(String title) {
		con.infoLog("Verify title of Page " + title);
		con.verifyPageTitle(title);
	}
	
	@Then("^User moves mouse to (.*)$")
	public void moveMouse(String locatorKey) {
		con.infoLog("Move Mouse to Object " + locatorKey);
		con.moveMouse(locatorKey);
	}
}
