package kr.devdogs.langexec.core.run;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import kr.devdogs.langexec.LanguageCompiler;
import kr.devdogs.langexec.LanguageRunner;
import kr.devdogs.langexec.RunningOutput;
import kr.devdogs.langexec.core.exception.CompileFailException;
import kr.devdogs.langexec.core.exception.ProcessTimeoutException;
import kr.devdogs.langexec.core.exception.RunningFailedException;
import kr.devdogs.langexec.core.output.OutputDelegate;
import kr.devdogs.langexec.core.event.ProcessEventListener;
import kr.devdogs.langexec.core.util.FileUtils;
import kr.devdogs.langexec.core.util.ProcessUtils;

public class JavaRunner implements LanguageRunner, ProcessEventListener {
	private LanguageCompiler complier;
	private File sourceFile;
	private String compiledClassPath;
	private long timeoutMillis;
	private boolean processTimeout;
	private RunningOutput output;

	public JavaRunner(File sourceFile, LanguageCompiler compiler) {
		this(sourceFile, 10000L, compiler);
	}
	
	public JavaRunner(File sourceFile, long timeoutMillis, LanguageCompiler compiler) {
		this.complier = compiler;
		this.sourceFile = sourceFile;
		this.compiledClassPath = compile(sourceFile);
		this.timeoutMillis = timeoutMillis;
		this.processTimeout = true;
	}
	
	
	@Override
	public RunningOutput run() {
		return run(null);
	}
	
	@Override
	public RunningOutput run(List<String> inputLines) {
		this.output = new RunningOutput();
		File compiledFile = new File(compiledClassPath);
		
		if(compiledFile.exists()) {
			long startTime = System.currentTimeMillis();
			execute(sourceFile, inputLines);
			long endTime = System.currentTimeMillis();
			
			output.setRunningTime(endTime-startTime);
		} else {
			throw new CompileFailException("Class file not created");
		}
		return this.output;
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
	
	
	private synchronized void execute(File sourceFile, List<String> inputLines) {
		String filePath = FileUtils.getAbsolutePath(sourceFile);
		String fileName = FileUtils.getFileName(sourceFile);
		
		try {
			ProcessBuilder builder = new ProcessBuilder("java", fileName);
			builder.directory(new File(filePath));
			Process process = builder.start();
			System.out.println(ProcessUtils.getPidOfProcess(process));
			
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
			wait(this.timeoutMillis);
			
			if(this.processTimeout) {
				throw new ProcessTimeoutException();
			}
		} catch(Exception e ) {
			throw new RunningFailedException(e);
		}
	}

	@Override
	public void onOutput(String outputLines) {
		String[] lines = outputLines.split("\n");
		for(String line:lines) {
			this.output.getOutputLines().add(line);
		}
	}

	@Override
	public synchronized void onProcessDestroy() {
		this.processTimeout = false;
		notify();
	}
}