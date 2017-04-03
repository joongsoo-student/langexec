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
	public RunningOutput run(List<String> inputLines);
	public RunningOutput run();
}