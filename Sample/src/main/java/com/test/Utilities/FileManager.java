package com.test.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileManager {
	
	private File file;
	
	public FileManager(File f){
		file = f;
	}
	
	public void setFile(File f){
		file = f;
	}
	
	public File getFile(){
		File f = null;
		
		if(file.isFile() && file.exists()){
			f = file;
		}
		
		return f;
	}
	
	public FileInputStream createNewExcel() throws IOException{
		FileInputStream fIn = null;
		File f = file;
		
		if(f.exists() && f.isFile()){
			f.delete();
		}
		
		XSSFWorkbook wb = new XSSFWorkbook();
		FileOutputStream fOut = new FileOutputStream(f);
		wb.createSheet();
		wb.write(fOut);
		wb.close();
			
		fOut.close();
		fIn = new FileInputStream(f);
		
		return fIn;
		
	}
}
