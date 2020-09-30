package com.testautomation.exceptions;

import com.testautomation.exceptions.exceptionHandling.ErrorCodes;

public class FileException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Integer errorCode;

	public FileException() {

	}

	public FileException(String message) {
		super(message);
	}

	public FileException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileException(Throwable cause) {
		super(cause);
	}

	public FileException(String message, Throwable cause, ErrorCodes errorCode) {
		super(message, cause);
		this.errorCode = errorCode.getCode();
	}

	public FileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
