package kr.devdogs.langexec.core.util;

import java.lang.reflect.Field;

public class ProcessUtils {
	public static synchronized long getPidOfProcess(Process p) {
		long pid = -1;

		try {
			if (p.getClass().getName().equals("java.lang.UNIXProcess")) {
				Field f = p.getClass().getDeclaredField("pid");
				f.setAccessible(true);
				pid = f.getLong(p);
				f.setAccessible(false);
			}
		} catch (Exception e) {
			pid = -1;
		}
		return pid;
	}
}
