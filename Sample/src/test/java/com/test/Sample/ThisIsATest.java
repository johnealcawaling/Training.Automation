package com.test.Sample;

import com.test.DataProvider.DataMapLoader;
import com.test.Input.ExcelDataLoader;
import com.test.Input.IExcelLoader;
import com.test.Output.IOutputGenerator;
import com.test.Output.OutputGenerator;
import com.test.Page.HomePage;
import com.test.Source.TestBase;
import com.test.Utilities.ConfigLoader;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
//import org.testng.log4testng.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

public class ThisIsATest{		
	private static final Logger logger = LogManager.getLogger(ThisIsATest.class);
	
	private IExcelLoader excel;
	private IOutputGenerator generator;
	private	ConfigLoader config;
	
	private static LinkedHashMap<String, LinkedHashMap<String, String>> inputMap;
	private LinkedHashMap<String, String> outputMap = new LinkedHashMap<String, String>();
	private final TestBase helper = new TestBase();
	
	private HomePage homepage;
	
	
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
	
	@BeforeTest
	public void initializeDriverAndPages() throws Exception{
		String browser = config.getBrowser();
		String url = config.getURL();
	
		helper.openBrowser(browser, url);
		//this is the part where page class are initiated
		homepage = new HomePage(helper.driver);
		
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
		try{	
			Reporter.log("Start testing...");
			
			homepage.searchBox().sendKeys(inputMap.get("Search"));
			helper.pressEnter();
			homepage.link_Image().click();
			
			helper.captureScreenshot(helper.driver, testName);
			
			helper.closeBrowser();
			
			Reporter.log("End testing...");
		
		}catch(Exception e){
			logger.error(this.getClass().getName() + " execution failed");
			System.err.println("ERROR_" + this.getClass().getName() + "_runTest: " + e.getMessage());
		}
	
	}
	
}
