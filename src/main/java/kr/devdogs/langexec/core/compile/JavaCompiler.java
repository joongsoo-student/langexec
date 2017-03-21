package kr.devdogs.langexec.core.compile;

import java.io.File;
import java.io.IOException;

import kr.devdogs.langexec.core.exception.CompileFailException;
import kr.devdogs.langexec.core.util.FileUtils;

public class JavaCompiler implements Compiler {
	@Override
	public String compile(File sourceFile) throws IOException {
		String filePath = FileUtils.getAbsolutePath(sourceFile);
		String fileName = FileUtils.getFileName(sourceFile);
		
		ProcessBuilder builder = new ProcessBuilder("javac", sourceFile.getName());
		builder.directory(new File(filePath));
		Process compileProc = builder.start();
		try {
			compileProc.waitFor();
		} catch (InterruptedException e) {
			throw new CompileFailException(e);
		}
		
		String compiledClassPath = filePath + fileName + ".class";
		if(new File(compiledClassPath).exists()) {
			return compiledClassPath;
		}
		return null;
	}
}