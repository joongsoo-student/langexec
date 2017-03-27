package kr.devdogs.langexec;

import java.io.File;

import kr.devdogs.langexec.core.compile.JavaCompiler;
import kr.devdogs.langexec.core.liveshell.JavaLiveShell;

public class LiveShellFactory {
	public static LanguageLiveShell getJavaLiveShell(File sourceFile) {
		return new JavaLiveShell(sourceFile, new JavaCompiler());
	}
}
