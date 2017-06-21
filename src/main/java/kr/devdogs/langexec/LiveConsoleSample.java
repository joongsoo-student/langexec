package kr.devdogs.langexec;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LiveConsoleSample {
	public static void main(String[] arg) {
		try(Scanner scan = new Scanner(System.in)) {
			File sourceFile = new File("/Users/st/Test.java");
			LanguageLiveConsole console = LiveConsoleFactory.getJavaLiveConsole(sourceFile);
			
			console.addOnOutputListener(new CustomOnOutputListener() {
				@Override
				public void onOutput(String output) {
					System.out.print(output);
				}
			});
			
			do {
				String line = scan.nextLine();
				try{
				console.writeLine(line);
				}catch(Exception e) {}
			}while(console.isRunning());
		}
	}
}