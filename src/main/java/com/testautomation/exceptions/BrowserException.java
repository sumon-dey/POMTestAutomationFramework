package com.testautomation.exceptions;

import com.testautomation.exceptions.exceptionHandling.ErrorCodes;

public class BrowserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Integer errorCode;

	public BrowserException() {

	}

	public BrowserException(String message) {
		super(message);
	}

	public BrowserException(String message, Throwable cause) {
		super(message, cause);
	}

	public BrowserException(Throwable cause) {
		super(cause);
	}

	public BrowserException(String message, Throwable cause, ErrorCodes errorCode) {
		super(message, cause);
		this.errorCode = errorCode.getCode();
	}

	public BrowserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
