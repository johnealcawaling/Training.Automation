package com.test.Page;

import java.util.Properties;
import org.openqa.selenium.WebDriver;
import com.test.Source.TestBase;

public class SearchPage extends TestBase{
	
	private Properties prop;
	private final String name = this.getClass().getSimpleName().toUpperCase();
	private final String path = System.getProperty("user.dir") + "/page/" + name + ".properties";
	private WebDriver driver;
	
	public SearchPage(WebDriver driver){
		//test
	}
}
