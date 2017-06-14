package com.test.Source;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.test.Utilities.SeleniumUtils;

public class TestBase extends SeleniumUtils{
	private static final Logger logger = LogManager.getLogger(TestBase.class);
	
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
				logger.info("Initialize Chrome browser...");
				
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
				logger.info("Initialize Firefox browser...");
				
				System.setProperty(FIREFOX_DRIVER, FIREFOX_DRIVER_PATH);
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
				
			}
			
			this.wait(30);
			
		}catch(Exception e){
			System.err.println("ERROR_" + this.getClass().getName() + "_openBrowser: " + e.getMessage());
		}
		
		logger.info("Go to " + url);
		driver.get(url);
		
	}
	
	public void openBrowserOnRemote(String browser, String url, String nodeURL, Platform platform){
		DesiredCapabilities cap = null;
		
		try{
			if(browser.toLowerCase().contains(CHROME_FLAG)){
				ChromeOptions options = new ChromeOptions();
		
				options.addArguments("chrome.switches","--disable-extensions");
				options.addArguments("--disable-notifications");
				options.addArguments("disable-infobars");
				options.addArguments("--start-maximized");
				
				cap = DesiredCapabilities.chrome();
				cap.setCapability(ChromeOptions.CAPABILITY, options);
				
			}else if(browser.toLowerCase().contains(FIREFOX_FLAG)){
				cap = DesiredCapabilities.firefox();
				
			}
			
			cap.setPlatform(platform);
			
			driver = new RemoteWebDriver(new URL(nodeURL), cap);
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			
			if(browser.toLowerCase().contains(CHROME_FLAG)){
				seleniumUtil.maximizeScreen(driver);
			}else{
				driver.manage().window().maximize();
			}
			
			driver.get(url);
			
			
		}catch(Exception e){
			System.err.println("ERROR_" + this.getClass().getName() + "_openBrowser: " + e.getMessage());
		}
		
	}
	
	public void closeBrowser(){
		logger.info("Closing browser...");
		driver.quit();
	}
	
	public void enterText(WebElement element, String value){
		if(element.isDisplayed() && element.isEnabled()){
			element.sendKeys(value);
			
			logger.info("Set " + value + " to " + element.getText());
		}
		
		
	}
	
	public void selectFromDropdown(WebElement element, String value){
		if(element.isDisplayed() && element.isEnabled()){
			Select dropdown = new Select(element);
			dropdown.selectByVisibleText(value);
			
			logger.info("Select " + value + " from " + element.getText() + " dropdown");
		}
	}
	
	public void clickObject(WebElement element){
		String name = element.getText();
		
		if(element.isDisplayed() && element.isEnabled()){
			element.click();
			
			logger.info("Click " + name);
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
	
	public WebElement findObject(WebDriver driver, String xPath){
		WebElement element = null;
		
		List<WebElement> aE = driver.findElements(By.xpath(xPath));
		
		for(WebElement e: aE){
			if(e.isDisplayed() && e.isEnabled()){
				
				logger.info(e.getText() + " object found");
				
				element = e;
				break;
			}
		}
		
		if(element == null){
			logger.error(xPath + "was not found.");
		}
		
		return element;
	}

	public void captureScreenshot(String testName) throws Exception{
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/output/temp/" + testName + "_" + timeStamp + ".png";
		
		File screenShotName = new File(path);
		FileUtils.copyFile(srcFile, screenShotName);
		
		String filePath = screenShotName.toString();
		String logPath = "<a href=" + path + " target='_blank' >" + filePath + "</a>";
		Reporter.log(logPath);
		
	}
}
