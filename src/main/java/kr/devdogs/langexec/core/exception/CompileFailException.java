package kr.devdogs.langexec.core.exception;

public class CompileFailException extends RuntimeException {
	public CompileFailException(){}
	public CompileFailException(String message) {
		super(message);
	}
	public CompileFailException(Throwable cause) {
		super(cause);
	}
}
