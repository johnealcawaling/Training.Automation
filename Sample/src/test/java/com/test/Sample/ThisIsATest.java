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
	
	private final SeleniumUtils seleniumUtil = new SeleniumUtils();		
	private static final Logger logger = Logger.getLogger(ThisIsATest.class);
	
	private IExcelLoader excel;
	private IOutputGenerator generator;
	private	ConfigLoader config;
	
	private static LinkedHashMap<String, LinkedHashMap<String, String>> inputMap;
	private LinkedHashMap<String, String> outputMap = new LinkedHashMap<String, String>();
	
	@BeforeSuite
	@Parameters({"config_path"})
	public void initializeExcelData(String config_path) throws IOException{
		config = new ConfigLoader(config_path);
		excel = new ExcelDataLoader(new File("." + config.getExcelPath()));
		inputMap = excel.getInputMap();
	}
		
	@AfterSuite
	@Parameters({"config_path"})
	public void generateCustomTestResult(String config_path) throws IOException{
		generator = new OutputGenerator(new File("." + config.getOutputPath() + 
				this.getClass().getSimpleName() + ".xlsx"), outputMap);
		generator.generateResult();
		System.out.println("DONE");
	}
	
	@DataProvider(name="DataParam")
	public static Object[][] getData(){
		return DataMapLoader.getDataMap(inputMap);
	}
	
	
	//this must be simplified.
	//implement page factory model
	//organize object repository into property files
	@Test(dataProvider="DataParam")
	public void runTest(String testName, LinkedHashMap<String, String> inputMap) throws Exception{
		String exp = null;
		String act = null;
		
		try{
			openBrowser(inputMap.get("Browser"), config.getURL());
			enterText("//input[@name='q']", inputMap.get("Search"));
			pressEnter();

			String xPath = "//*[@id='hdtb-msb-vis']/div[*]/a[text()='Images']";

			waitForObject(xPath);
			clickObject(xPath);
			
			seleniumUtil.captureScreenshot(driver, testName);
			
			closeBrowser();
		
		}catch(Exception e){
			System.err.println("ERROR_" + this.getClass().getName() + "_runTest: " + e.getMessage());
		}
	
	}
	
}
