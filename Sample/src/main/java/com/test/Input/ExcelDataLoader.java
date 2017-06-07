package com.test.Input;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;

import com.test.Utilities.FileManager;


public class ExcelDataLoader implements IExcelLoader{
	
	private File inFile = null;
	private FileInputStream fIn;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private XSSFRow row;
	private XSSFCell cell;
	
	private final int FIRST_SHEET = 0;
	
	public ExcelDataLoader(File f) throws IOException{
		FileManager fileManager = new FileManager(f);
		
		inFile = fileManager.getFile();
		
		inFile = new File("/Users/johnealcawaling/Documents/workspace/Sample/input/Test_Data.xlsx");
		
		if(inFile.isFile()){
			fIn = new FileInputStream(inFile);
			System.out.println(inFile);
			
//			workbook = new XSSFWorkbook(fIn);
			System.out.println("I AM HERE");
		}

		
	}

	public LinkedHashMap<String, String> getInputMap() {
		// TODO Auto-generated method stub
		if(inFile != null){
			LinkedHashMap<String, String> inputMap = new LinkedHashMap<String, String>();
			
			int rowCnt = sheet.getLastRowNum();
			
			for(int i=0; i<rowCnt; i++){
				row = sheet.getRow(i);
				cell = row.getCell(0);
				
				System.out.println(cell.getStringCellValue());
			}
			
			
		}
		
		return null;
	}
	
	public static void main(String[] args) throws IOException{
//		String sFile = System.getProperty("user.dir") + "/input/Test_Data.xlsx";
//		ExcelDataLoader excel = new ExcelDataLoader(new File(sFile));
//		excel.getInputMap();
//		System.out.println("TEST");
		
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

	
}
