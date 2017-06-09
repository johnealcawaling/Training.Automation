package com.test.Page;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.test.Source.TestBase;

public class HomePage extends TestBase{
	
	private Properties prop;
	private final String name = this.getClass().getSimpleName().toUpperCase();
	private final String path = System.getProperty("user.dir") + "/page/" + name + ".properties";
	private WebDriver driver;
	
	public HomePage(WebDriver driver) throws Exception{
		this.driver = driver;
		
		prop = new Properties();
		File f = new File(path);
		InputStream in = new FileInputStream(f);
		
		if(f.isFile() && f.exists()){
			prop.load(in);
		}
	
	}
	
	public WebElement searchBox(){
		return findObject(driver, prop.getProperty("TEXT_BOX"));
	}
	
	public WebElement link_Image(){
		return findObject(driver, prop.getProperty("IMAGE_LINK"));
	}
	
}
