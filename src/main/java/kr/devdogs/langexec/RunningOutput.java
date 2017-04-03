package kr.devdogs.langexec;

import java.util.ArrayList;
import java.util.List;

public class RunningOutput {
	private List<String> outputLines;
	private long runningTime;
	
	public RunningOutput() {
		outputLines = new ArrayList<>();
		runningTime = 0;
	}
	public RunningOutput(List<String> outputLines, long runningTime) {
		super();
		this.outputLines = outputLines;
		this.runningTime = runningTime;
	}
	
	public List<String> getOutputLines() {
		return outputLines;
	}
	public void setOutputLines(List<String> outputLines) {
		this.outputLines = outputLines;
	}
	public long getRunningTime() {
		return runningTime;
	}
	public void setRunningTime(long runningTime) {
		this.runningTime = runningTime;
	}
}
