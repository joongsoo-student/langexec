package kr.devdogs.langexec.core.compile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import kr.devdogs.langexec.LanguageCompiler;
import kr.devdogs.langexec.core.exception.CompileFailException;
import kr.devdogs.langexec.core.util.FileUtils;
import kr.devdogs.langexec.core.util.OutputCollector;

public class JavaCompiler implements LanguageCompiler {
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
	
	@Override
	public List<String> compileAndGetLog(File sourceFile) throws IOException {
		String filePath = FileUtils.getAbsolutePath(sourceFile);
		String fileName = FileUtils.getFileName(sourceFile);
		ProcessBuilder builder = new ProcessBuilder("javac", sourceFile.getName());
		builder.directory(new File(filePath));
		Process compileProc = builder.start();
		
		OutputCollector outputThread = new OutputCollector(compileProc);
		outputThread.start();
		try {
			compileProc.waitFor();
			outputThread.join(2000); 
			if(outputThread.isAlive()) {
				outputThread.destroy();
			}
		} catch (InterruptedException e) {
			throw new CompileFailException(e);
		} catch(NoSuchMethodError err) {
			
		}
		
		String compiledClassPath = filePath + fileName + ".class";
		File compiledFile = new File(compiledClassPath);
		if(compiledFile.exists()) {
			compiledFile.delete();
			return null;
		} else {
			return outputThread.getResult();
		}
	}
}