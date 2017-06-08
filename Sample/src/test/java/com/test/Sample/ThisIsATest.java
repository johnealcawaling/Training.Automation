package com.test.Sample;

import com.test.Input.ExcelDataLoader;
import com.test.Input.IExcelLoader;
import com.test.Source.TestBase;
import com.test.Utilities.ConfigLoader;
import com.test.Utilities.SeleniumUtils;

import org.testng.annotations.AfterClass;
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
	
	private static IExcelLoader excel;
	private static LinkedHashMap<String, LinkedHashMap<String, String>> inputMap;
	
	private int i = 0;
	
	@BeforeSuite
	@Parameters({"config_path"})
	public void initializeExcelData(String config_path) throws IOException{
		ConfigLoader config = new ConfigLoader(config_path);
		File f = new File("." + config.getExcelPath());
		
		excel = new ExcelDataLoader(f);
		inputMap = excel.getInputMap();
	}
	
	@BeforeClass
	public void setUp(){
		this.openBrowser("http://www.google.com");
	}
	
	@AfterClass
	public void cleanUp(){
		driver.quit();
		System.out.println("DONE");
	}
	
	@DataProvider(name="DataParam")
	public static Object[][] getData(){
		int iSize = inputMap.size();
		
		Object[][] dataParam = new Object[iSize][1];

		int i = 0;
		
		for(String test:inputMap.keySet()){
			LinkedHashMap<String, String> dataMap = inputMap.get(test);
			dataParam[i][0] = dataMap;
			i++;
		}
		
		return dataParam;
	}
	

	@Test(dataProvider="DataParam")
	public void runTest(LinkedHashMap<String, String> map) throws Exception{
		//Open URL
//		driver.get(sBrowser);
//		
//		WebElement element = driver.findElement(By.xpath("//input[@name='q']"));
//
//		element.sendKeys(sSearch);
//		element.sendKeys(Keys.ENTER);
//		
//		String xpathExpression = "//*[@id='hdtb-msb-vis']/div[*]/a[text()='Images']";
//		
//		WebElement obj = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpression)));
//		obj.click();
//		
//		selenium.captureScreenshot(driver, i++);
		
		System.out.println(map);
		
	}
	
}
