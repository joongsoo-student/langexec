package kr.devdogs.langexec;

import java.io.File;
import java.util.Scanner;

public class LiveConsoleSample {
	public static void main(String[] arg) {
		try(Scanner scan = new Scanner(System.in)) {
			File sourceFile = new File("/Users/st/Test.java");
			LanguageLiveConsole shell = LiveConsoleFactory.getJavaLiveConsole(sourceFile);
			
			shell.addOnOutputListener(new CustomOnOutputListener() {
				@Override
				public void onOutput(String output) {
					System.out.print(output);
				}
			});
			
			while(shell.isRunning()) {
				String line = scan.nextLine();
				shell.writeLine(line);
			}
		}
	}
}
