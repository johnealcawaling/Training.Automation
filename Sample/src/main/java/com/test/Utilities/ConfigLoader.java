package com.test.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
	
	private Properties prop;
	private final String EXCEL_PATH = "EXCEL_PATH";
	
	public ConfigLoader(String configFile) throws IOException{
		prop = new Properties();
		
		File f = new File("." + configFile);
		InputStream in = new FileInputStream(f);
		
		if(in != null){
			prop.load(in);
			
		}
	}
	
	public String getPropertyValue(String propertyName){
		String propValue = null;
		
		if(propertyName != null){
			propValue = prop.getProperty(propertyName);
		}
		
		return propValue;
		
	}
	
	
	public String getExcelPath(){
		return prop.getProperty(EXCEL_PATH);
	}
}
