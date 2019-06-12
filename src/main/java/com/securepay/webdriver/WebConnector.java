package com.securepay.webdriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.securepay.reports.ExtentManager;

public class WebConnector {

	WebDriver driver;
	public String name;
	public Properties locator;
	public Properties testdata;
	public ExtentReports rep;
	public ExtentTest scenario;

	public WebConnector() {
		if (locator == null) {

			try {
				locator = new Properties();
				testdata = new Properties();

				FileInputStream fslocator = new FileInputStream(
						System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
								+ File.separator + "resources" + File.separator + "Locators.properties");

				FileInputStream fstestdata = new FileInputStream(
						System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
								+ File.separator + "resources" + File.separator + "testdata.properties");

				locator.load(fslocator);
				testdata.load(fstestdata);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void openBrowser(String browserName) {

		if (browserName.equals("Mozilla")) {
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "null");
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + File.separator + "drivers" + File.separator + "geckodriver");
			driver = new FirefoxDriver();
		} else if (browserName.equals("Chrome")) {
			System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
			System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
					System.getProperty("user.dir") + File.separator + "drivers" + File.separator + "chromedriver");
			driver = new ChromeDriver();
		} else if (browserName.equals("Edge")) {
			System.setProperty(EdgeDriverService.EDGE_DRIVER_EXE_PROPERTY,
					System.getProperty("user.dir") + File.separator + "drivers" + File.separator + "msedgedriver.exe");
			driver = new EdgeDriver();
		}

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		// infoLog("Opened Browser " + browserName);
	}

	public void navigate(String urlKey) {
		driver.get(urlKey);
	}

	public void click(String objectKey) {
		getObject(objectKey).click();
	}

	public void type(String objectKey, String data) {
		getObject(objectKey).sendKeys(data);
	}

	public void select(String objectKey, String data) {
		Select s = new Select(getObject(objectKey));
		s.selectByVisibleText(data);
	}

	public void clear(String objectKey) {
		getObject(objectKey).clear();
	}

	public void verifyPageTitle(String title) {
		String actualTitle = driver.getTitle();
		if (!title.equalsIgnoreCase(actualTitle)) {
			reportSoftFailure("Expected Title: " + title + " , Actual Title: " + actualTitle);
		}
	}

	public void moveMouse(String objectKey) {
		Actions action = new Actions(driver);
		action.moveToElement(getObject(objectKey)).perform();
		wait(2);
	}

	public WebElement getObject(String objectKey) {
		WebElement e = null;
		WebDriverWait wait = new WebDriverWait(driver, 10);

		try {
			if (objectKey.endsWith("_xpath")) {
				e = driver.findElement(By.xpath(locator.getProperty(objectKey)));// present
				wait.until(
						ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(locator.getProperty(objectKey))));
			} else if (objectKey.endsWith("_id")) {
				e = driver.findElement(By.id(locator.getProperty(objectKey)));// present
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(locator.getProperty(objectKey))));
			} else if (objectKey.endsWith("_name")) {
				e = driver.findElement(By.name(locator.getProperty(objectKey)));// present
				wait.until(
						ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name(locator.getProperty(objectKey))));
			} else if (objectKey.endsWith("_css")) {
				e = driver.findElement(By.cssSelector(locator.getProperty(objectKey)));// present
				wait.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.cssSelector(locator.getProperty(objectKey))));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			reportFailure("Unable to extract Object " + objectKey);
		}
		return e;
	}

	public boolean isElementPresent(String objectKey) {
		List<WebElement> e = null;

		if (objectKey.endsWith("_xpath")) {
			e = driver.findElements(By.xpath(locator.getProperty(objectKey)));// present
		} else if (objectKey.endsWith("_id")) {
			e = driver.findElements(By.id(locator.getProperty(objectKey)));// present
		} else if (objectKey.endsWith("_name")) {
			e = driver.findElements(By.name(locator.getProperty(objectKey)));// present
		} else if (objectKey.endsWith("_css")) {
			e = driver.findElements(By.cssSelector(locator.getProperty(objectKey)));// present
		}
		if (e.size() == 0)
			return false;
		else
			return true;
	}

	public void NavigateToULRAndSearchOnGoogle(String textToSearch, String url) {
		infoLog("Navigate to url " + url);
		navigate(url);
		infoLog("Type text " + textToSearch + " in search field");
		type("Google.search_name", textToSearch + Keys.ENTER);
		// infoLog("Click on Google.submit_name button");
		// click("Google.submit_name");
	}

	public void fillContactForm() {

		Faker faker = new Faker();
		String firstname = faker.name().firstName();
		String lastname = faker.name().lastName();
		String phone = faker.phoneNumber().cellPhone();
		String company = faker.company().name();

		String[] reasons = { "Support", "Accounts" };
		int randomNumber = new Random().nextInt(reasons.length);

		infoLog("Enter First Name " + firstname);
		type("Contact.firstname_name", firstname);

		infoLog("Enter Last Name " + lastname);
		type("Contact.lastname_name", lastname);

		infoLog("Enter Email Address John");
		type("Contact.email_name", firstname + "." + lastname + "@gmail.com");

		infoLog("Enter Phone John");
		type("Contact.phone_name", phone);

		infoLog("Enter URL John");
		type("Contact.url_name", "https://" + company + ".com");

		infoLog("Enter Company John");
		type("Contact.company_name", company);

		infoLog("Select Reason John");
		select("Contact.reason_name", reasons[randomNumber]);
	}

	public void clickAndWait(String xpathExpTarget, String xpathExpWait, int maxTime) {
		for (int i = 0; i < maxTime; i++) {
			// click
			getObject(xpathExpTarget).click();
			if (isElementPresent(xpathExpWait)
					&& driver.findElement(By.id(locator.getProperty(xpathExpWait))).isDisplayed()) {
				// if present - success.. return
				return;
			} else {
				// else wait for 1 sec
				wait(1);
			}

		}
		// 10 seconds over - for loop - comes here
		reportFailure("Target element coming after clicking on " + xpathExpTarget);
	}

	public void waitForPageToLoad(String object) {

		for (int i = 1; i < 10; i++) {
			if (isElementPresent(object)) {
				if (getObject(object).isDisplayed()) {
					return;
				}
			}
			wait(2);
		}
		reportFailure("Object Not Found on the Page " + object);
	}

	public void wait(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/********** logging **************/
	public void infoLog(String msg) {
		scenario.log(LogStatus.INFO, msg);
	}

	public void reportSoftFailure(String errMsg) {
		scenario.log(LogStatus.FAIL, errMsg);
		takeSceenShot();
	}

	public void reportFailure(String errMsg) {
		scenario.log(LogStatus.FAIL, errMsg);
		takeSceenShot();
		assert (false);
	}

	public void takeSceenShot() {
		Date d = new Date();
		String screenshotFile = d.toString().replace(":", "_").replace(" ", "_") + ".png";
		// take screenshot
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		System.out.println(ExtentManager.screenshotFolderPath + File.separator + screenshotFile);
		srcFile.renameTo(new File(ExtentManager.screenshotFolderPath + File.separator + screenshotFile));
		scenario.log(LogStatus.FAIL, "Screenshot-> "
				+ scenario.addScreenCapture(ExtentManager.screenshotFolderPath + File.separator + screenshotFile));
	}

	/************** Reporting ******************/
	public void quit() {
		if (rep != null)
			rep.flush();
		if (driver != null)
			driver.quit();
	}

	public void initReports(String scenarioName) {
		String reportPath = System.getProperty("user.dir") + File.separator + "Reports";
		rep = ExtentManager.getInstance(reportPath);
		scenario = rep.startTest(scenarioName);
		scenario.log(LogStatus.INFO, "Starting " + scenarioName);
	}

}
