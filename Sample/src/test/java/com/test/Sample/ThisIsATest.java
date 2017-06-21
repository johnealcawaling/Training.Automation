package com.test.Sample;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
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
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

public class ThisIsATest{		
	private static final Logger logger = LogManager.getLogger(ThisIsATest.class);
	
	private IExcelLoader excel;
	private IOutputGenerator generator;
	private	ConfigLoader config;
	
	private static LinkedHashMap<String, LinkedHashMap<String, String>> inputMap;
	private LinkedHashMap<String, String> outputMap = new LinkedHashMap<String, String>();
	private final TestBase helper = new TestBase();
	
	private ExtentReports extent;
	private ExtentTest test;
	
	private HomePage homepage;
	
	@BeforeSuite
	@Parameters({"config_path"})
	public void initializeExcelData(String config_path) throws IOException{
		config = new ConfigLoader(config_path);
		excel = new ExcelDataLoader(new File("." + config.getExcelPath()));
		inputMap = excel.getInputMap();
		
		extent = new ExtentReports("./output/report/" + this.getClass().getSimpleName() + ".html");
		extent.loadConfig(new File("./config/extent-config.xml"));
	}
		
	@AfterSuite
	@Parameters({"config_path"})
	public void generateCustomTestResult(String config_path) throws IOException{
		generator = new OutputGenerator(new File("." + config.getOutputPath() + 
				this.getClass().getSimpleName() + ".xlsx"), outputMap);
		generator.generateResult();
		System.out.println("DONE");
	}
	
	@BeforeClass
	public void initializeDriverAndPages() throws Exception{
		String browser = config.getBrowser();
		String url = config.getURL();
	
		helper.openBrowser(browser, url);
		//this is the part where page class are initiated
		homepage = new HomePage(helper.driver);
		
	}
	
	@BeforeTest
	public void setupExtentReport(Method m){
		test = extent.startTest(m.getName());
		test.assignAuthor("user.name");
		test.assignCategory("This is a test");
	}
	
	@AfterTest
	public void cleanUp(){
		helper.closeBrowser();
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
			
			Reporter.log("Enter text");
			test.log(LogStatus.INFO, "Enter text");
			helper.enterText(homepage.searchBox(), inputMap.get("Search"));
			Reporter.log("Press enter");
			test.log(LogStatus.INFO, "Press enter");
			helper.pressEnter();
			helper.captureScreenshot(testName);
			Reporter.log("Click Image link");
			test.log(LogStatus.INFO, "Click Image link");
			helper.clickObject(homepage.link_Image());
			helper.captureScreenshot(testName + "1");
			Reporter.log("Close browser");
			test.log(LogStatus.INFO, "Close browser");
			
			Reporter.log("End testing...");
		
		}catch(Exception e){
			logger.error(this.getClass().getName() + " execution failed");
			System.err.println("ERROR_" + this.getClass().getName() + "_runTest: " + e.getMessage());
		}
	
	}
	
}
