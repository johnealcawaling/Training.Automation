package com.test.Utilities;

import java.awt.Toolkit;
import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class SeleniumUtils {

	public static void maximizeScreen(WebDriver driver) {
		java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point position = new Point(0, 0);
		driver.manage().window().setPosition(position);
		Dimension maximizedScreenSize = 
				new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());
		driver.manage().window().setSize(maximizedScreenSize);
	}
	
	public void captureScreenshot(WebDriver driver, String testName) throws Exception{
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile, new File("./output/temp/" + testName + ".png"));
		
	}
	
}
