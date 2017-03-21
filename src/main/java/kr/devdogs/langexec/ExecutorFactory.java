package kr.devdogs.langexec;

import kr.devdogs.langexec.core.compile.JavaCompiler;
import kr.devdogs.langexec.core.executor.JavaExecutor;

public class ExecutorFactory {
	public static LanguageExecutor getJavaExecutor() {
		return new JavaExecutor(new JavaCompiler());
	}
}