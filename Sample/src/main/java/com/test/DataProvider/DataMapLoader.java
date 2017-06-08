package com.test.DataProvider;

import java.util.LinkedHashMap;

public class DataMapLoader {
	
	public static Object[][] getDataMap(LinkedHashMap<String, LinkedHashMap<String, String>> map){
		
		int iSize = map.size();
		Object[][] dataParam = null;
		
		if(iSize > 0){
			
			dataParam = new Object[iSize][2];
	
			int i = 0;
			
			for(String test:map.keySet()){
				LinkedHashMap<String, String> dataMap = map.get(test);
				dataParam[i][0] = test;
				dataParam[i][1] = dataMap;
				i++;
			}		
		
		}
		
		return dataParam;
		
	}
}
