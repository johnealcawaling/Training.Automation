package com.test.Source;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.Utilities.SeleniumUtils;

public class TestBase {

	public WebDriver driver;
	
	private final String PARENT_FOLDER_PATH = System.getProperty("user.dir");
	
	private final String CHROME_FLAG = "chrome";
	private final String FIREFOX_FLAG = "firefox";
	
	private final String CHROME_DRIVER = "webdriver.chrome.driver";
	private final String FIREFOX_DRIVER = "webdriver.gecko.driver";
	private final String CHROME_DRIVER_PATH = PARENT_FOLDER_PATH + "/driver/chromedriver";
	private final String FIREFOX_DRIVER_PATH = PARENT_FOLDER_PATH + "/driver/geckodriver";
	
	private final SeleniumUtils seleniumUtil = new SeleniumUtils();
	
	public void openBrowser(String browser, String url){
		
		try{
			
			if(browser.toLowerCase().contains(CHROME_FLAG)){
			
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
				seleniumUtil.maximizeScreen(driver);
			
			}else if(browser.toLowerCase().contains(FIREFOX_FLAG)){
				
				System.setProperty(FIREFOX_DRIVER, FIREFOX_DRIVER_PATH);
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
				
			}
			
			this.wait(30);
			
		}catch(Exception e){
			System.err.println("ERROR_" + this.getClass().getName() + "_openBrowser: " + e.getMessage());
		}
		
		driver.get(url);
		
	}
	
	public void closeBrowser(){
		driver.quit();
	}
	
	public void enterText(String xPath, String value){
		enterText(xPath, value, false);
	}
	
	public void enterText(String xPath, String value, boolean blnEnter){
		WebElement elem = null;
		
		List<WebElement> aE = driver.findElements(By.xpath(xPath));
		
		for(WebElement e: aE){
			if(e.isDisplayed() && e.isEnabled()){
				e.sendKeys(value);
				elem = e;
				break;
			}
		}
		
		if(blnEnter){
			elem.sendKeys(Keys.ENTER);
		}
	}
	
	public void selectFromDropdown(String value){
		
	}
	
	public void clickObject(String xPath){
		List<WebElement> aE = driver.findElements(By.xpath(xPath));
		for(WebElement e: aE){
			if(e.isDisplayed() && e.isEnabled()){
				e.click();
				break;
			}
		}
	}
	
	public void wait(int seconds){
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}

	
	public void pressEnter(){
		Actions action = new Actions(driver);
		action.sendKeys(Keys.ENTER).build().perform();
	}
	
	public void waitForObject(String xPath){
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));
	}
	
	

	
}
