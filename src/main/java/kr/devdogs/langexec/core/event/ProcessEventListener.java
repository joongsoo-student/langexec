package kr.devdogs.langexec.core.event;


public interface ProcessEventListener {
	public void onProcessDestroy();
	public void onOutput(String output);
}
