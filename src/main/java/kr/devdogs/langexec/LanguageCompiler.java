package kr.devdogs.langexec;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface LanguageCompiler {
	/**
	 * 
	 * @param sourceFile
	 * @return class file path
	 * @throws IOException
	 */
	public String compile(File sourceFile) throws IOException;
	/**
	 * 
	 * @param sourceFile
	 * @return outputLogLines
	 * @throws IOException
	 */
	public List<String> compileAndGetLog(File sourceFile) throws IOException;
}
