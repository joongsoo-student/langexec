package kr.devdogs.langexec;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RunnerSample {
	public static void main(String[] arg) {
		for(int i=0; i<10; i++) {
			try(Scanner scan = new Scanner(System.in)) {
				File sourceFile = new File("/Users/st/Test2.java");
				LanguageRunner runner = RunnerFactory.getJavaExecutor(sourceFile);
				
				String[] arr = {"aaa", "bbb", "ccc"};
				List<String> list = Arrays.stream(arr).map((String str)->str).collect(Collectors.toList());
				List<String> result = runner.run(sourceFile, list);
				System.out.println(result);
			}
		}
	}
}
