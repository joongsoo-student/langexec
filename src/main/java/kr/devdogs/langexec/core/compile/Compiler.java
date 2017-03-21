package kr.devdogs.langexec.core.compile;

import java.io.File;
import java.io.IOException;

public interface Compiler {
	public String compile(File sourceFile) throws IOException;
}
