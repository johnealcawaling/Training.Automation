package com.test.Prototyping;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.Source.TestBase;

public class Prototype {
	public static void main(String[] args) throws Exception{
		WebDriver driver;
		System.setProperty("webdriver.gecko.driver", "/Users/johnealcawaling/git/Training.Automation/Sample/driver/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		driver.get("http://www.qa.nixplay.com/getaccess/");
		
		WebElement e = driver.findElement(By.xpath("html/body/div[1]/p[2]/a"));
		e.click();
		System.out.println("DONE");
	}
	
	
	
	
	     
	    public static String capture(WebDriver driver,String screenShotName) throws IOException{
	        TakesScreenshot ts = (TakesScreenshot)driver;
	        File source = ts.getScreenshotAs(OutputType.FILE);
	        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	        String dest=System.getProperty("user.dir") + "/output/temp/" + screenShotName + "_" + timeStamp + ".png";
	        File destination = new File(dest);
	        FileUtils.copyFile(source, destination);        
	                     
	        return dest;
	    }
	    
}
