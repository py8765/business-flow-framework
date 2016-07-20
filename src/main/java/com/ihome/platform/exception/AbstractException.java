package com.ihome.platform.exception;

public class AbstractException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -201482006428441298L;

	public AbstractException() {
		super();
	}

	public AbstractException(String message) {
		super(message);
	}

	public AbstractException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public AbstractException(Throwable throwable) {
		super(throwable);
	}

}
