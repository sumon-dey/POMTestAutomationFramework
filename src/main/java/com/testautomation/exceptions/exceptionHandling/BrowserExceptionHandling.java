package com.testautomation.exceptions.exceptionHandling;

import org.apache.log4j.Logger;

import com.testautomation.exceptions.BrowserException;

public class BrowserExceptionHandling {

	private static final Logger logger = Logger.getLogger(BrowserExceptionHandling.class);

	public static void handleBrowserLaunchException(String message, String browserName) {
		logger.warn(message + browserName);
		throw new BrowserException(message + browserName);
	}

}
