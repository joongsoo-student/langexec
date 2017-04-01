package kr.devdogs.langexec;

import java.io.File;

import kr.devdogs.langexec.core.compile.JavaCompiler;
import kr.devdogs.langexec.core.live.JavaLiveConsole;

public class LiveConsoleFactory {
	public static LanguageLiveConsole getJavaLiveConsole(File sourceFile) {
		return new JavaLiveConsole(sourceFile, new JavaCompiler());
	}
}
