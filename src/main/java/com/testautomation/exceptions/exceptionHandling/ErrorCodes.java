package com.testautomation.exceptions.exceptionHandling;

public enum ErrorCodes {

	VALIDATION_PARSE_ERROR(123);

	private int code;

	ErrorCodes(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
