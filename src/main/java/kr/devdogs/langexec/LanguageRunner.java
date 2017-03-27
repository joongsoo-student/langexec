package kr.devdogs.langexec;

import java.io.File;
import java.util.List;

public interface LanguageRunner {
	/**
	 * 
	 * @param sourceFile
	 * @param inputLines
	 * @return outputLines
	 */
	public List<String> run(File sourceFile, List<String> inputLines);
	
}