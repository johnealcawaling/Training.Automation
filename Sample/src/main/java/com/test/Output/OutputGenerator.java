package com.test.Output;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.test.Utilities.FileManager;

public class OutputGenerator implements IOutputGenerator{
	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private XSSFCell cell;
	private XSSFRow row;
	private FileInputStream fIn;
	private FileOutputStream fOut;
	private File file;
	private LinkedHashMap<String, String> mapOutput;
	
	private final int FIRST_SHEET = 0;
	private final int ROW_LABEL = 0;
	private final int ROW_START = 1;
	private final int LABEL_1 = 0;
	private final int LABEL_2 = 1;
	private final String LABEL_1_NAME = "TEST NAME";
	private final String LABEL_2_NAME = "STATUS";
	
	
	
	public OutputGenerator(File f, LinkedHashMap<String, String> outputMap) throws IOException{
		FileManager fileManager = new FileManager(f);
		
		mapOutput = outputMap;
		file = f;
		fIn = fileManager.createNewExcel();
		workbook = new XSSFWorkbook(fIn);
	}
	
	public void generateResult(){
		workbook.setSheetName(FIRST_SHEET, "TEST_SHEET");		
		sheet = workbook.getSheetAt(FIRST_SHEET);

		int rowCnt = sheet.getLastRowNum();
		
		if(rowCnt <= 0){
			row = sheet.createRow(ROW_LABEL);
			cell = row.createCell(LABEL_1);
			cell.setCellValue(LABEL_1_NAME);
			cell = row.createCell(LABEL_2);
			cell.setCellValue(LABEL_2_NAME);
		}
		
		List<String> aList = new ArrayList<String>();
		for(String name: mapOutput.keySet()){
			aList.add(name);
		}
		
		for(int i=ROW_START; i<=aList.size(); i++){
			row = sheet.createRow(i);
			String field = aList.get(i-1);
			String value = mapOutput.get(field);
			
			cell = row.createCell(LABEL_1);
			cell.setCellValue(field);
			cell = row.createCell(LABEL_2);
			cell.setCellValue(value);
		}
		
		
		
		//save and close worbook
		try {
			fOut = new FileOutputStream(file);
			workbook.write(fOut);
			workbook.close();
			fOut.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	
	
	public static void main(String[] args) throws IOException{
		File f = new File("/Users/johnealcawaling/git/Training.Automation/Sample/testing/output_excel/test.xlsx");
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("TC1", "PASSED");
		map.put("TC2", "FAILED");
		OutputGenerator o = new OutputGenerator(f,map);
		o.generateResult();
		System.out.println("DONE");
//		System.out.println(map);
	}
}
