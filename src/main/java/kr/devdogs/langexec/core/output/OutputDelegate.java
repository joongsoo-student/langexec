package kr.devdogs.langexec.core.output;

import java.io.IOException;
import java.io.InputStream;

import kr.devdogs.langexec.core.event.ProcessEventListener;

public class OutputDelegate extends Thread {
	private InputStream m_isInputStream;
	private InputStream m_esErrorStream;
	private Process currentProcess;
	private ProcessEventListener listener;

	public OutputDelegate(Process p, ProcessEventListener listener) {
		this.currentProcess = p;
		this.m_isInputStream = p.getInputStream();
		this.m_esErrorStream = p.getErrorStream();
		this.listener = listener;
	}

	@Override
	public void run() {
		
		try {
			while(true) {
				if(m_isInputStream.available() > 0 || m_esErrorStream.available() > 0) {
					InputStream stream = m_isInputStream.available() > 0 ? m_isInputStream : m_esErrorStream;
					byte[] b = new byte[stream.available()];
					stream.read(b);
					String output = new String(b);
					listener.onOutput(output);
				}
				if(!currentProcess.isAlive()) {
					break;
				}
				Thread.sleep(100L);
			}
		} catch (IOException e) {
			listener.onOutput(e.getMessage());
		} catch (InterruptedException e) {
			listener.onOutput(e.getMessage());
		}
		listener.onProcessDestroy();
	}
}
