package com.test.Prototyping;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.management.DescriptorRead;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Prototype {
	public static void main(String[] args) throws MalformedURLException{
		ExtentReports extent;
		ExtentTest test;
		
		WebDriver driver;
		
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		
		extent = new ExtentReports("/Users/johnealcawaling/git/Training.Automation/Sample/output/report/REPORT_"+timeStamp+".html", true);
		extent.loadConfig(new File("/Users/johnealcawaling/git/Training.Automation/Sample/config/extent-config.xml"));
		test = extent.startTest("SampleTest");
		test.assignAuthor("Johneal Cawaling");
		test.assignCategory("Sample_Report");
		
		
		System.setProperty("webdriver.gecko.driver", "/Users/johnealcawaling/git/Training.Automation/Sample/driver/geckodriver");
		
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		test.log(LogStatus.INFO, "Open browser and go to URL");
		
		driver.get("http://www.stratpoint.com");
		
		List<WebElement> aE = driver.findElements(By.xpath("//*[@id='service-categories']/h4[1]"));
		for(WebElement e: aE){
			if(e.isDisplayed() && e.isEnabled()){
				e.sendKeys("");
				test.log(LogStatus.PASS, "Element found");
			}
		}
		
		
		extent.endTest(test);
		extent.flush();
		extent.close();
		
		System.out.println("DONE");
		
	}
}
