package com.securepay.reports;

import java.io.File;
import java.util.Date;

import org.openqa.selenium.WebDriverInfo;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
	private static ExtentReports extent;
    public static String screenshotFolderPath;
	
	public static synchronized ExtentReports getInstance(String reportPath) {
		if (extent == null) {
			
			String fileName = "SuiteReport.html";
    		Date d = new Date();
    		
    		// Create the Report Folder
    		String folderName = d.toString().replace(":", "_").replace(" ", "_");
    		screenshotFolderPath = reportPath + File.separator + folderName + File.separator + "screenshots";
    		new File(screenshotFolderPath).mkdirs();
    		reportPath = reportPath + File.separator + folderName + File.separator + fileName;

			extent = new ExtentReports(reportPath, true, DisplayOrder.OLDEST_FIRST);
			extent.loadConfig(new File(System.getProperty("user.dir") + File.separator + "ReportsConfig.xml"));
			extent.addSystemInfo("Selenium Version", "3.141.59");
			//extent.addSystemInfo("Platform", System.getProperty("os.name"));
			//extent.addSystemInfo("Environment", "Test Challenge QA");
			
		}
		return extent;
	}
}
