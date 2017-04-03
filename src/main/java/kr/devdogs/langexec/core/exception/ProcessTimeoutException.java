package kr.devdogs.langexec.core.exception;

public class ProcessTimeoutException extends RuntimeException {
	public ProcessTimeoutException(){}
	public ProcessTimeoutException(String message) {
		super(message);
	}
	public ProcessTimeoutException(Throwable cause) {
		super(cause);
	}
}
