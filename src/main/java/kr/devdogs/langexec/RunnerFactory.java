package kr.devdogs.langexec;

import kr.devdogs.langexec.core.compile.JavaCompiler;
import kr.devdogs.langexec.core.execute.JavaRunner;

public class RunnerFactory {
	public static LanguageRunner getJavaExecutor() {
		return new JavaRunner(new JavaCompiler());
	}
}