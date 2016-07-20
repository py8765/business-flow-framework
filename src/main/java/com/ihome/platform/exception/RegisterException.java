package com.ihome.platform.exception;

public class RegisterException extends AbstractException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8526761178899752993L;

	public RegisterException() {
		super();
	}

	public RegisterException(String message) {
		super(message);
	}

	public RegisterException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public RegisterException(Throwable throwable) {
		super(throwable);
	}
}
