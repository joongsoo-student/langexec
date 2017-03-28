package kr.devdogs.langexec.core.live;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import kr.devdogs.langexec.CustomOnOutputListener;
import kr.devdogs.langexec.LanguageCompiler;
import kr.devdogs.langexec.LanguageLiveConsole;
import kr.devdogs.langexec.core.exception.CompileFailException;
import kr.devdogs.langexec.core.exception.RunningFailedException;
import kr.devdogs.langexec.core.output.OutputDelegate;
import kr.devdogs.langexec.core.event.ProcessEventListener;
import kr.devdogs.langexec.core.util.FileUtils;

public class JavaLiveConsole implements LanguageLiveConsole, ProcessEventListener {
	private LanguageCompiler complier;
	private OutputDelegate outputDelegate;
	private List<String> outputLine;
	private Process currentProcess;
	private BufferedWriter processWriter;
	private String compiledClassPath;
	private List<CustomOnOutputListener> outputListenerList;
	private boolean running;
	
	public JavaLiveConsole(File sourceFile, LanguageCompiler compiler) {
		this.complier = compiler;
		this.outputLine = new ArrayList<>();
		this.outputListenerList = new ArrayList<>();
		
		this.compiledClassPath = compile(sourceFile);
		String filePath = FileUtils.getAbsolutePath(sourceFile);
		String fileName = FileUtils.getFileName(sourceFile);
		
		try {
			ProcessBuilder builder = new ProcessBuilder("java", fileName);
			builder.directory(new File(filePath));
			this.currentProcess = builder.start();
			
			OutputStream stdin = this.currentProcess.getOutputStream();
			this.processWriter = new BufferedWriter(new OutputStreamWriter(stdin));
			
			this.outputDelegate = new OutputDelegate(this.currentProcess, this);
			this.outputDelegate.start();
			this.running = true;
		} catch(Exception e ) {
			throw new RunningFailedException(e);
		}
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
	
	
	@Override
	public void writeLine(String line) {
		try {
			processWriter.write(line);
			processWriter.newLine();
			processWriter.flush();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<String> getOutputLines() {
		return outputLine;
	}
	
	@Override
	public synchronized void onOutput(String output) {
		outputLine.add(output);
		for(CustomOnOutputListener lis : outputListenerList) {
			lis.onOutput(output);
		}
	}

	@Override
	public void onProcessDestroy() {
		this.running = false;
		
		// Thread Kill
		try {
			outputDelegate.join(2000);
		} catch (InterruptedException e) {} 
		try {
			if(outputDelegate.isAlive()) {
				outputDelegate.destroy();
			}
		}catch(NoSuchMethodError err) {}
		
		File compileFile = new File(compiledClassPath);
		if(compileFile.exists()) {
			compileFile.delete();
		}
	}

	@Override
	public void addOnOutputListener(CustomOnOutputListener outputListener) {
		this.outputListenerList.add(outputListener);
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}
}