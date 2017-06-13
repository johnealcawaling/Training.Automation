package com.test.Page;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.test.Source.TestBase;

public class SearchPage extends TestBase{
	
	public static final Logger logger = LogManager.getLogger(SearchPage.class);
	
	private Properties prop;
	private final String name = this.getClass().getSimpleName().toUpperCase();
	private final String path = System.getProperty("user.dir") + "/page/" + name + ".properties";
	private WebDriver driver;
	
	public SearchPage(WebDriver driver){
		//test
	}
}
