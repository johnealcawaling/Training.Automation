package com.test.Utilities;

import java.awt.Toolkit;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

public class SeleniumUtils {

	public void maximizeScreen(WebDriver driver) {
		java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point position = new Point(0, 0);
		driver.manage().window().setPosition(position);
		Dimension maximizedScreenSize = 
				new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());
		driver.manage().window().setSize(maximizedScreenSize);
	}
	

	
}
