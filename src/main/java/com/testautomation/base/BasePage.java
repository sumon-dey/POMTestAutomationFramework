package com.testautomation.base;

import org.openqa.selenium.WebDriver;

public class BasePage {

	public static WebDriver driver;

	public String getPageTitle() {
		return driver.getTitle();
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}
}
