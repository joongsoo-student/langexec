package kr.devdogs.langexec;

import kr.devdogs.langexec.core.compile.JavaCompiler;

public class CompilerFactory {
	public static LanguageCompiler getJavaCompiler() {
		return new JavaCompiler();
	}
}
