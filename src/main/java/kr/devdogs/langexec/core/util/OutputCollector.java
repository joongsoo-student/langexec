package kr.devdogs.langexec.core.util;

import java.io.IOException;
import java.io.InputStream;

public class OutputCollector extends Thread{
	private InputStream m_isInputStream;
	private InputStream m_esErrorStream;
	private Process currentProcess;
	private StringBuilder result;

	public OutputCollector(Process p) {
		this.currentProcess = p;
		m_isInputStream = p.getInputStream();
		m_esErrorStream = p.getErrorStream();
		result = new StringBuilder();
	}

	@Override
	public void run() {
		try {
			while(true) {
				while (m_isInputStream.available() > 0 || m_esErrorStream.available() > 0) {
					InputStream stream = m_isInputStream.available() > 0 ? m_isInputStream : m_esErrorStream;
					byte[] b = new byte[stream.available()];
					stream.read(b);
					result.append(new String(b) + "\n");
					if(!currentProcess.isAlive()) {
						return;
					}
				}
				Thread.sleep(100L);
			}
		} catch (IOException e) {
			result.append(e.getMessage());
		} catch (InterruptedException e) {
			result.append(e.getMessage());
		}
	}

	public String getResult() {
		return result.toString();
	}
}
