package kr.devdogs.langexec;

import java.io.File;

import kr.devdogs.langexec.core.compile.JavaCompiler;
import kr.devdogs.langexec.core.live.JavaLiveConsole;

public class LiveShellFactory {
	public static LanguageLiveConsole getJavaLiveShell(File sourceFile) {
		return new JavaLiveConsole(sourceFile, new JavaCompiler());
	}
}
