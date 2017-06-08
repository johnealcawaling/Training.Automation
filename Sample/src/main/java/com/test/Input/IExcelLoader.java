package com.test.Input;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface IExcelLoader {
	
	public LinkedHashMap<String, LinkedHashMap<String, String>> getInputMap();
	
	public void setInputMap(LinkedHashMap<String, LinkedHashMap<String, String>> inputMap);
	
	public XSSFWorkbook getWorkbook();
	
	public void setWorkbook(XSSFWorkbook wb);
	
	public List<String> getTestNameList();
	
	
}
