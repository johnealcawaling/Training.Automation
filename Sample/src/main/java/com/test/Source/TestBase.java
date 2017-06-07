package com.test.Source;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.test.Utilities.SeleniumUtils;

public class TestBase {

	public WebDriver driver;
	
	private final String PARENT_FOLDER_PATH = System.getProperty("user.dir");
	private final String CHROME_DRIVER = "webdriver.chrome.driver";
	private final String CHROME_DRIVER_PATH = PARENT_FOLDER_PATH + "/driver/chromedriver";
	
	private final SeleniumUtils selenium = new SeleniumUtils();
	
	public void openBrowser(String sUrl){
		
		try{
			System.setProperty(CHROME_DRIVER, CHROME_DRIVER_PATH);
			
			//Add chrome options
			ChromeOptions options = new ChromeOptions();
			options.addArguments("chrome.switches","--disable-extensions");
			options.addArguments("--disable-notifications");
			options.addArguments("disable-infobars");
			options.addArguments("--start-maximized");
			//Implement options
			DesiredCapabilities capabilites = DesiredCapabilities.chrome();
			capabilites.setCapability(ChromeOptions.CAPABILITY, options);
			//Open browser
			driver = new ChromeDriver(capabilites);
			
			
			selenium.maximizeScreen(driver);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
		}catch(Exception e){
			System.err.println("Error: " + e.getMessage());
		}
		
	}
	
	

	
}
