package com.testautomation.base;

import java.util.Set;

import org.openqa.selenium.WebDriver;

public class BasePage {

	public static WebDriver driver;

	public String getPageTitle() {
		return driver.getTitle();
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public String getCurrentPageSource() {
		return driver.getPageSource();
	}

	public String getPageWindowHandle() {
		return driver.getWindowHandle();
	}

	public Set<String> getPageWindowHandles() {
		return driver.getWindowHandles();
	}
}
