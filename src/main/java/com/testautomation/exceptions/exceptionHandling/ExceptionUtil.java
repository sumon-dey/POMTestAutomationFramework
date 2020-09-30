package com.testautomation.exceptions.exceptionHandling;

public class ExceptionUtil {

	/**
	 * Get the root cause of a particular {@code Throwable}
	 * 
	 * @Sumon Dey
	 * @since 30/09/2020
	 * @version 0.1
	 * @param t exception
	 * @return exception root cause
	 */
	@SuppressWarnings("unchecked")
	static <T extends Throwable> T rootCause(Throwable t) {
		Throwable cause = t.getCause();
		if (cause != null && cause != t) {
			return rootCause(cause);
		}
		return (T) t;
	}

}
