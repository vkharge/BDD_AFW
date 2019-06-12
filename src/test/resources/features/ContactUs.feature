@ContactUs
Feature: ContactUs Functionality Feature
   	In order to validate ContactUs functionality works as expected,
	I want to run the cucumber test to verify it

Background: User opens google and searches for SecurePay
  Given User open Mozilla browser
  When User searches for SecurePay on http://www.google.com.au
  Then User should wait for page to load with object Google.securepaylink_xpath on new page
  When User click on Google.securepaylink_xpath
  Then User should wait for page to load with object Home.solutionlink_xpath on new page
  And Verify that page with title 'SecurePay online payment and eCommerce solutions for businesses12' should be displayed
	
  @ContactUsTest
  Scenario: Contact_SecurePay
  	When User moves mouse to Home.supportlink_xpath
    When User click on Home.contactuslink_xpath
    Then User should wait for page to load with object Contact.header_xpath on new page
    And Verify that page with title 'Contact Us – SecurePay – Sales – Support – Accounts' should be displayed
    When User fills the contact us form