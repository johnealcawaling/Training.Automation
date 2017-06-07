package com.test.Sample;

import com.test.Input.ExcelDataLoader;
import com.test.Source.TestBase;
import com.test.Utilities.FileManager;
import com.test.Utilities.SeleniumUtils;

import org.testng.Reporter;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.log4testng.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ThisIsATest extends TestBase {
	private final SeleniumUtils selenium = new SeleniumUtils();		
	private static final Logger logger = Logger.getLogger(ThisIsATest.class);
	
	private int i = 0;
	
	@BeforeSuite
	@Parameters({"config_path"})
	public void initializeExcelData(String excel_path) throws IOException{
//		ExcelDataLoader inputLoader = new ExcelDataLoader(new File(System.getProperty("user.dir") + excel_path));
		File f = new File("/Users/johnealcawaling/Documents/workspace/Sample/input/Test_Data.xlsx");
		FileInputStream fIn = new FileInputStream(f);
		XSSFWorkbook workbook = new XSSFWorkbook(fIn);
		XSSFSheet sheet = workbook.getSheetAt(0);
		int rowCount = sheet.getLastRowNum();
		
		for(int i=0; i<rowCount; i++){
			XSSFRow row = sheet.getRow(i);
			XSSFCell cell = row.getCell(0);
			String x = cell.getStringCellValue();
			System.out.println(x);
		}
		
		fIn.close();
		workbook.close();
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
	public static Object[][]creds(){
		return new Object[][]{{"http://www.google.com","Apple"}};
	}
	
	@Test(dataProvider="DataParam")
	public void runTest(String sBrowser, String sSearch) throws Exception{
		//Open URL
		driver.get(sBrowser);
		
		WebElement element = driver.findElement(By.xpath("//input[@name='q']"));

		element.sendKeys(sSearch);
		element.sendKeys(Keys.ENTER);
		
		String xpathExpression = "//*[@id='hdtb-msb-vis']/div[*]/a[text()='Images']";
		
		WebElement obj = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpression)));
		obj.click();
		
		selenium.captureScreenshot(driver, i++);
		
	}
	
}
