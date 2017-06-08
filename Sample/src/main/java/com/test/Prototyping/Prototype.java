package com.test.Prototyping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Prototype {
	public static void main(String[] args) throws IOException{
//		WebDriver driver;
//		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/driver/chromedriver");
//		driver = new ChromeDriver();
	
//		Properties p = new Properties();
//		File f = new File("/Users/johnealcawaling/Documents/workspace/Sample/config/config.properties");
//		FileInputStream fIn = new FileInputStream(f);
//		p.load(fIn);
//		
//		String x = p.getProperty("EXCEL_PATH");
//		System.out.println(x);
		
		String[][] s = new String[1][4];
		
		s[0][0] = "x";
		s[0][1] = "y";
		
		System.out.println(s.toString());
	}
}
