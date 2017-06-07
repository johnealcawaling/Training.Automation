package com.test.Utilities;

import java.io.File;

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
}
