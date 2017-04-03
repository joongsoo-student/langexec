package kr.devdogs.langexec;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RunnerSample {
	public static void main(String[] arg) {
		try(Scanner scan = new Scanner(System.in)) {
			File sourceFile = new File("/Users/st/Test2.java");
			LanguageRunner runner = RunnerFactory.getJavaRunner(sourceFile, 10000);

			// Making Input
			String[] arr = {"aaa", "bbb", "ccc"};
			List<String> list = Arrays.stream(arr).map((String str)->str).collect(Collectors.toList());

			// Run & get Result
			for(int i=0; i<10; i++) {
				System.out.println(i);
				RunningOutput result = runner.run(list);
				System.out.println("output : " + result.getOutputLines());
				System.out.println("time : " + result.getRunningTime());
				System.out.println("===============================");
			}
		}
	}
}
