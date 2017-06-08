package com.test.Input;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;

import com.test.Utilities.FileManager;


public class ExcelDataLoader implements IExcelLoader{
	
	private File inFile = null;
	private FileInputStream fIn = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	
	private final int FIRST_SHEET = 0;
	private final int ROW_FIELD_LABEL = 0;
	private final int ROW_FIELD_VALUE_START = 1;
	private final int COL_TEST_NUM = 0;
	private final int COL_FIELD_LABEL_START = 1;
	
	private LinkedHashMap<String, LinkedHashMap<String, String>> mapInput;
	
	public ExcelDataLoader(File f) throws IOException{
		FileManager fileManager = new FileManager(f);
		
		inFile = fileManager.getFile();
		
		if(inFile.isFile()){
			fIn = new FileInputStream(inFile);
			workbook = new XSSFWorkbook(fIn);
		}

		
	}
	
	public LinkedHashMap<String, LinkedHashMap<String, String>> getInputMap(){
		// TODO Auto-generated method stub
		
		if(inFile != null){
			LinkedHashMap<String, LinkedHashMap<String, String>> inputMap = 
					new LinkedHashMap<String, LinkedHashMap<String, String>>();
			
			sheet = workbook.getSheetAt(0);
			int rowCnt = sheet.getLastRowNum();
			XSSFRow rowField = sheet.getRow(ROW_FIELD_LABEL);
			
			if(rowCnt > 0){
				
				for(int i=ROW_FIELD_VALUE_START; i<rowCnt+1; i++){
					row = sheet.getRow(i);
					int colCnt = row.getLastCellNum();
					cell = row.getCell(COL_TEST_NUM);
					
					//get test name
					String testName = cell.getStringCellValue();
			
					LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
					
					if(colCnt > 0){
						for(int j=COL_FIELD_LABEL_START; j<colCnt; j++){
							XSSFCell cellField = rowField.getCell(j);
							
							//get field name and field value
							String fieldName = cellField.getStringCellValue();
							String fieldValue = row.getCell(j).getStringCellValue();
							
							//get value per field
							fieldMap.put(fieldName, fieldValue);
						}
						
					}
					
					//get field and value per test name
					inputMap.put(testName, fieldMap);
				
				}
				
			}
			
			//pass map to return variable
			mapInput = inputMap;
			
		} 
		
		//close inputstream and workbook
		try {
			workbook.close();
			fIn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Error: " + e.getMessage());
		}
		
		return mapInput;
	}
	

	public void setInputMap(LinkedHashMap<String, LinkedHashMap<String, String>> inputMap) {
		// TODO Auto-generated method stub
		mapInput = inputMap;
	}
	
	public XSSFWorkbook getWorkbook() {
		// TODO Auto-generated method stub
		return workbook;
	}

	public void setWorkbook(XSSFWorkbook wb) {
		// TODO Auto-generated method stub
		workbook = wb;
		
	}
	
	public List<String> getTestNameList() {
		// TODO Auto-generated method stub
		List<String> aList = new ArrayList();
		
		sheet = workbook.getSheetAt(FIRST_SHEET);
		int rowCnt = sheet.getLastRowNum();
		
		for(int i=ROW_FIELD_VALUE_START; i<rowCnt+1; i++){
			row = sheet.getRow(i);
			cell = row.getCell(COL_TEST_NUM);
			
			String testName = cell.getStringCellValue();
			aList.add(testName);
		}
		
		try {
			workbook.close();
			fIn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Error: " + e.getMessage());
		}
		
		return aList;
	}
	

}
