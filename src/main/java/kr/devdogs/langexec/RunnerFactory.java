package kr.devdogs.langexec;

import java.io.File;

import kr.devdogs.langexec.core.compile.JavaCompiler;
import kr.devdogs.langexec.core.execute.JavaRunner;
import kr.devdogs.langexec.core.live.JavaLiveConsole;

public class RunnerFactory {
	public static LanguageRunner getJavaExecutor(File sourceFile) {
		return new JavaRunner(new JavaCompiler());
	}
	
}