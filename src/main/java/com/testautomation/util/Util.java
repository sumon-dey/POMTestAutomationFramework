package com.testautomation.util;

import org.openqa.selenium.WebElement;
import com.testautomation.base.TestBase;

public class Util extends TestBase {

	public static long PAGE_LOAD_TIMEOUT = 80;
	public static long IMPLICIT_WAIT = 10;

	public void switchToFrame(int index) {
		driver.switchTo().frame(index);
	}

	public void switchToFrame(String nameOrId) {
		driver.switchTo().frame(nameOrId);
	}

	public void switchToFrame(WebElement webElement) {
		driver.switchTo().frame(webElement);
	}

}
