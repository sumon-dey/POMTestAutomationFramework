package com.testautomation.util;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import com.testautomation.base.BaseSteps;

public class WebEventListener extends BaseSteps implements WebDriverEventListener {

	private static final Logger logger = Logger.getLogger(WebEventListener.class);

	@Override
	public void beforeAlertAccept(WebDriver driver) {

	}

	@Override
	public void afterAlertAccept(WebDriver driver) {

	}

	@Override
	public void afterAlertDismiss(WebDriver driver) {

	}

	@Override
	public void beforeAlertDismiss(WebDriver driver) {

	}

	@Override
	public void beforeNavigateTo(String url, WebDriver driver) {
		logger.debug("Opening URL: " + url);
	}

	@Override
	public void afterNavigateTo(String url, WebDriver driver) {
		logger.debug("Opened URL: " + url);
	}

	@Override
	public void beforeNavigateBack(WebDriver driver) {
		logger.debug("Navigating back to the previous page");
	}

	@Override
	public void afterNavigateBack(WebDriver driver) {
		logger.debug("Navigated back to the previous page");
	}

	@Override
	public void beforeNavigateForward(WebDriver driver) {
		logger.debug("Navigating forward to the next page");
	}

	@Override
	public void afterNavigateForward(WebDriver driver) {
		logger.debug("Navigated forward to the next page");
	}

	@Override
	public void beforeNavigateRefresh(WebDriver driver) {
		logger.debug("Refreshing Page...");
	}

	@Override
	public void afterNavigateRefresh(WebDriver driver) {
		logger.debug("Page Refreshed");
	}

	@Override
	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		logger.debug("Trying to find Element By: " + by.toString());
	}

	@Override
	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		logger.debug("Successfully found Element By: " + by.toString());
	}

	@Override
	public void beforeClickOn(WebElement element, WebDriver driver) {
		logger.debug("Trying to click on: " + element.toString());
	}

	@Override
	public void afterClickOn(WebElement element, WebDriver driver) {
		logger.debug("Successfully clicked on: " + element.toString());
	}

	@Override
	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

	}

	@Override
	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

	}

	@Override
	public void beforeScript(String script, WebDriver driver) {

	}

	@Override
	public void afterScript(String script, WebDriver driver) {

	}

	@Override
	public void beforeSwitchToWindow(String windowName, WebDriver driver) {

	}

	@Override
	public void afterSwitchToWindow(String windowName, WebDriver driver) {

	}

	@Override
	public void onException(Throwable throwable, WebDriver driver) {
		logger.error("Exception occured: " + throwable);
		Util.takeScreenshot();
	}

	@Override
	public <X> void beforeGetScreenshotAs(OutputType<X> target) {
		logger.debug("Taking Screenshot...");
	}

	@Override
	public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {

	}

	@Override
	public void beforeGetText(WebElement element, WebDriver driver) {

	}

	@Override
	public void afterGetText(WebElement element, WebDriver driver, String text) {

	}

}
