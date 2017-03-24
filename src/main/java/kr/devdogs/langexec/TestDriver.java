package kr.devdogs.langexec;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestDriver {
	public static void main(String[] arg) {
		Scanner scan;
		try {
			scan = new Scanner(new File("/Users/st/Test2.txt"));
			List<String> inputs = new ArrayList<>();
			String str = null;
			while(scan.hasNextLine() && (str = scan.nextLine()) != null) {
				inputs.add(str);
			}
			File input = new File("/Users/st/Test3.java");
			System.out.println(RunnerFactory.getJavaExecutor().run(input, inputs));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
