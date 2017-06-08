package com.test.Sample;

import com.test.DataProvider.DataMapLoader;
import com.test.Input.ExcelDataLoader;
import com.test.Input.IExcelLoader;
import com.test.Output.IOutputGenerator;
import com.test.Output.OutputGenerator;
import com.test.Source.TestBase;
import com.test.Utilities.ConfigLoader;
import com.test.Utilities.SeleniumUtils;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.log4testng.Logger;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ThisIsATest extends TestBase {
	
	private final SeleniumUtils selenium = new SeleniumUtils();		
	private static final Logger logger = Logger.getLogger(ThisIsATest.class);
	
	private IExcelLoader excel;
	private	ConfigLoader config;
	
	private static LinkedHashMap<String, LinkedHashMap<String, String>> inputMap;
	private LinkedHashMap<String, String> outputMap = new LinkedHashMap<String, String>();
		
//	private SoftAssert softAssert = new SoftAssert();
	
	@BeforeSuite
	@Parameters({"config_path"})
	public void initializeExcelData(String config_path) throws IOException{
		config = new ConfigLoader(config_path);
		excel = new ExcelDataLoader(new File("." + config.getExcelPath()));
		inputMap = excel.getInputMap();
	}
	
	@BeforeClass
	public void setUp(){
		System.out.println("Initialize run...");
		this.openBrowser("http://www.google.com");
	}
	
	@AfterClass
	public void cleanUp(){
		driver.quit();
	}
	
	@AfterSuite
	@Parameters({"config_path"})
	public void endTest(String config_path) throws IOException{
		IOutputGenerator generator = new OutputGenerator(new File("." + config.getOutputPath() + 
				this.getClass().getSimpleName() + ".xlsx"), outputMap);
		generator.generateResult();
		System.out.println("DONE");
	}
	
	@DataProvider(name="DataParam")
	public static Object[][] getData(){
		return DataMapLoader.getDataMap(inputMap);
	}
	

	@Test(dataProvider="DataParam")
	public void runTest(String testName, LinkedHashMap<String, String> inputMap) throws Exception{
		//Open URL
		Reporter.log("Open browser and go to URL.");
		driver.get(inputMap.get("Browser"));
		
		WebElement element = driver.findElement(By.xpath("//input[@name='q']"));
		
		Reporter.log("Enter text to search.");
		element.sendKeys(inputMap.get("Search"));
		element.sendKeys(Keys.ENTER);
		
		String exp = inputMap.get("Search"); 
		String act = inputMap.get("Search_2");
		
		Reporter.log("Compare text 1 and 2 if similar.");
		if(exp.equalsIgnoreCase(act)){
			outputMap.put(testName, "PASSED");
		}else{
			outputMap.put(testName, "FAILED");
		}
		
		Assert.assertEquals(exp, act);
		
		String xpathExpression = "//*[@id='hdtb-msb-vis']/div[*]/a[text()='Images']";
		
		Reporter.log("Click Image link.");
		WebElement obj = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpression)));
		obj.click();
		
		selenium.captureScreenshot(driver, testName);
		
//		System.out.println(outputMap);
	}
	
}
