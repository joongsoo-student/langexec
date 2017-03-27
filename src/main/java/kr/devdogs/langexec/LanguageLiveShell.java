package kr.devdogs.langexec;

import java.util.List;

public interface LanguageLiveShell {
	public void writeLine(String line);
	public List<String> getOutputLines();
	public void addOnOutputListener(CustomOnOutputListener outputListener);
	public boolean isRunning();
}
