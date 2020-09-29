package com.testautomation.exceptions.exceptionHandling;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.log4j.Logger;
import com.testautomation.exceptions.FileException;

public class FileExceptionHandling {

	private static final Logger logger = Logger.getLogger(FileExceptionHandling.class);

	public static void handlePropertiesFileException(Exception exception, String propertyFileName) {
		if (exception instanceof FileNotFoundException) {
			logger.error("Properties file " + propertyFileName + " is not found in the classpath. \nException Message: "
					+ exception.getMessage());
			throw new FileException("Properties file \"" + propertyFileName
					+ "\" is not found in the classpath. \nException Message: " + exception.getMessage());
		} else if (exception instanceof IOException) {
			logger.error("I/O Exception while loading the Properties file \"" + propertyFileName
					+ "\" \nException Message: " + exception.getMessage());
			throw new FileException("I/O Exception while loading the Properties file \"" + propertyFileName
					+ "\" \nException Message: " + exception.getMessage());
		} else {
			logger.error("Properties file \"" + propertyFileName + "\" could not be loaded. \nException Message: "
					+ exception.getMessage());
			throw new FileException("Properties file \"" + propertyFileName
					+ "\" could not be loaded. \nException Message: " + exception.getMessage());
		}
	}

}
