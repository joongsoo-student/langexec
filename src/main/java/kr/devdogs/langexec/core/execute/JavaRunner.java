package kr.devdogs.langexec.core.execute;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import kr.devdogs.langexec.LanguageCompiler;
import kr.devdogs.langexec.LanguageRunner;
import kr.devdogs.langexec.core.exception.CompileFailException;
import kr.devdogs.langexec.core.exception.RunningFailedException;
import kr.devdogs.langexec.core.output.OutputDelegate;
import kr.devdogs.langexec.core.event.ProcessEventListener;
import kr.devdogs.langexec.core.util.FileUtils;

public class JavaRunner implements LanguageRunner, ProcessEventListener {
	private LanguageCompiler complier;
	private List<String> resultList;
	
	public JavaRunner(LanguageCompiler compiler) {
		this.complier = compiler;
		this.resultList = new ArrayList<>();
	}
	
	@Override
	public List<String> run(File sourceFile, List<String> inputLines) {
		String compiledClassPath = compile(sourceFile);
		List<String> runResult = execute(sourceFile, inputLines);
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
	
	/**
	 * 
	 * @param sourceFile
	 * @param inputLines
	 * @return outputLines
	 */
	private synchronized List<String> execute(File sourceFile, List<String> inputLines) {
		String filePath = FileUtils.getAbsolutePath(sourceFile);
		String fileName = FileUtils.getFileName(sourceFile);
		
		try {
			ProcessBuilder builder = new ProcessBuilder("java", fileName);
			builder.directory(new File(filePath));
			Process process = builder.start();
			
			OutputStream stdin = process.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
			
			OutputDelegate outputThread = new OutputDelegate(process, this);
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
			wait();
			return this.resultList;
		} catch(Exception e ) {
			throw new RunningFailedException(e);
		}
	}

	@Override
	public void onOutput(String outputLines) {
		this.resultList.add(outputLines);
	}

	@Override
	public synchronized void onProcessDestroy() {
		notify();
	}
}