package kr.devdogs.langexec;

import java.io.File;

import kr.devdogs.langexec.core.compile.JavaCompiler;
import kr.devdogs.langexec.core.live.JavaLiveConsole;
import kr.devdogs.langexec.core.run.JavaRunner;

public class RunnerFactory {
	
	public static LanguageRunner getJavaRunner(File sourceFile) {
		return new JavaRunner(sourceFile, new JavaCompiler());
	}
	
	public static LanguageRunner getJavaRunner(File sourceFile, long timeoutMillis) {
		return new JavaRunner(sourceFile, timeoutMillis, new JavaCompiler());
	}
}