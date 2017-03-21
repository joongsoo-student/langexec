package kr.devdogs.langexec;

import java.io.File;
import java.util.List;

public interface LanguageExecutor {
	public String run(File sourceFile, List<String> inputLines);
}
