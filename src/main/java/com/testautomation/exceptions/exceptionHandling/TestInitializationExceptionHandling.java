package com.testautomation.exceptions.exceptionHandling;

import org.apache.log4j.Logger;

import com.testautomation.exceptions.BrowserException;

public class TestInitializationExceptionHandling {

	private static final Logger log = Logger.getLogger(TestInitializationExceptionHandling.class);

	public void handleBrowserLaunchException(String message, String browserName) {
		handleBrowserLaunchException(null, message, browserName);
	}

	public void handleBrowserLaunchException(Throwable throwable, String message, String browserName) {
		log.warn(message + browserName);
		throw new BrowserException(message + browserName);
	}

}
