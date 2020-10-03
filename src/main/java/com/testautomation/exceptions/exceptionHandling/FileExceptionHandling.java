package com.testautomation.exceptions.exceptionHandling;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.log4j.Logger;
import com.testautomation.exceptions.FileException;

public class FileExceptionHandling {

	private static final Logger log = Logger.getLogger(FileExceptionHandling.class);

	/**
	 * Calls the FileException custom exception class with different exception
	 * messages depending on the exception caught.
	 * 
	 * @param throwable        A Throwable object
	 * @param propertyFileName Name of the properties file
	 * @author Sumon Dey
	 * @since 13/06/2020
	 * @version 0.1
	 */
	public void handlePropertiesFileException(Throwable throwable, String propertyFileName) {
		if (throwable instanceof FileNotFoundException) {
			log.error("Properties file " + propertyFileName + " is not found in the classpath. \nException Message: "
					+ throwable.getMessage());
			throw new FileException("Properties file \"" + propertyFileName
					+ "\" is not found in the classpath. \nException Message: " + throwable.getMessage(), throwable,
					ErrorCodes.VALIDATION_PARSE_ERROR);
		} else if (throwable instanceof IOException) {
			log.error("I/O Exception while loading the Properties file \"" + propertyFileName
					+ "\" \nException Message: " + throwable.getMessage());
			throw new FileException("I/O Exception while loading the Properties file \"" + propertyFileName
					+ "\" \nException Message: " + throwable.getMessage(), throwable,
					ErrorCodes.VALIDATION_PARSE_ERROR);
		} else {
			log.error("Properties file \"" + propertyFileName + "\" could not be loaded. \nException Message: "
					+ throwable.getMessage());
			throw new FileException("Properties file \"" + propertyFileName
					+ "\" could not be loaded. \nException Message: " + throwable.getMessage(), throwable,
					ErrorCodes.VALIDATION_PARSE_ERROR);
		}
	}

}
