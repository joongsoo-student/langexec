package kr.devdogs.langexec.core.executor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import kr.devdogs.langexec.LanguageExecutor;
import kr.devdogs.langexec.core.compile.Compiler;
import kr.devdogs.langexec.core.exception.CompileFailException;
import kr.devdogs.langexec.core.exception.RunningFailedException;
import kr.devdogs.langexec.core.util.FileUtils;
import kr.devdogs.langexec.core.util.OutputCollector;

public class JavaExecutor implements LanguageExecutor {
	private Compiler complier;
	private OutputCollector outputCollector;
	
	public JavaExecutor(Compiler compiler) {
		this.complier = compiler;
	}
	
	@Override
	public String run(File sourceFile, List<String> inputLines) {
		String compiledClassPath = compile(sourceFile);
		String runResult = execute(sourceFile, inputLines);
		new File(compiledClassPath).delete();
		return runResult;
	}
	
	
	private String compile(File sourceFile) {
		String compiledFilePath = null;
		try {
			compiledFilePath = this.complier.compile(sourceFile);
		}catch(IOException e) {
			throw new CompileFailException(e);
		}

		if(compiledFilePath == null) {
			throw new CompileFailException();
		}
		return compiledFilePath;
	}
	
	
	private String execute(File sourceFile, List<String> inputLines) {
		String filePath = FileUtils.getAbsolutePath(sourceFile);
		String fileName = FileUtils.getFileName(sourceFile);
		
		try {
			ProcessBuilder builder = new ProcessBuilder("java", fileName);
			builder.directory(new File(filePath));
			Process process = builder.start();
			
			OutputStream stdin = process.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
			
			OutputCollector outputThread = new OutputCollector(process);
			outputThread.start();
			for(String temp: inputLines) {
				try {
						writer.write(temp);
						writer.newLine();
						writer.flush();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			outputThread.join();
			return outputThread.getResult();
		} catch(Exception e ) {
			throw new RunningFailedException(e);
		}
	}
}