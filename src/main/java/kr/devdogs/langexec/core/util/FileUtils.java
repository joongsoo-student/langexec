package kr.devdogs.langexec.core.util;

import java.io.File;

public class FileUtils {
	public static String getAbsolutePath(File file) {
		String[] pathArr = file.getAbsolutePath().split("/");
		StringBuilder path = new StringBuilder();
		for(int i=0; i<pathArr.length-1; i++) {
			path.append(pathArr[i]);
			path.append("/");
		}
		return path.toString();
	}
	
	public static String getFileName(File file) {
		String[] nameArr = file.getName().split("\\.");
		StringBuilder name = new StringBuilder();
		
		for(int i=0; i<nameArr.length-1; i++) {
			name.append(nameArr[i]);
			
			if(i != nameArr.length-2) {
				name.append(".");
			}
		}
		return name.toString();
	}
}
