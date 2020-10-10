package com.testautomation.unitTests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.testautomation.base.BaseSteps;
import com.testautomation.exceptions.BrowserException;
import com.testautomation.exceptions.FileException;

public class BaseStepsClassTest {

	@Test
	public void configPropertiesFileLoadPositiveTest() {
		try {
			BaseSteps baseSteps = new BaseSteps();
			String configPropertiesFileName = "config.properties";
			baseSteps.loadConfigProperties(configPropertiesFileName);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test(expectedExceptions = FileException.class)
	public void configPropertiesFileNotFoundExceptionTest() {
		String configPropertiesFileName = "config1.properties";
		try {
			BaseSteps baseSteps = new BaseSteps();
			baseSteps.loadConfigProperties(configPropertiesFileName);
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(),
					"Properties file \"" + configPropertiesFileName + "\" is not found in the classpath.");
			throw e;
		}
	}

	@Test
	public void chromeOptionsSettingTest() {
		try {
			BaseSteps baseSteps = new BaseSteps();
			baseSteps.getChromeOptions();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void chromeSetUpAndLaunchTest() {
		try {
			BaseSteps baseSteps = new BaseSteps();
			WebDriver driver = null;
			baseSteps.chromeSetUp(driver);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void firefoxSetUpAndLaunchTest() {
		try {
			BaseSteps baseSteps = new BaseSteps();
			WebDriver driver = null;
			baseSteps.firefoxSetUp(driver);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testInitializationTest() {
		try {
			BaseSteps baseSteps = new BaseSteps();
			System.setProperty("Browser", "Chrome");
			baseSteps.testInitialization();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test(expectedExceptions = BrowserException.class)
	public void browserNotSupportedTest() {
		try {
			BaseSteps baseSteps = new BaseSteps();
			System.setProperty("Browser", "Edge");
			baseSteps.testInitialization();
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(), "The browser is not supported: Edge");
			throw e;
		}
	}

	@Test
	public void monitorAndLogWebEventSetupTest() {
		try {
			BaseSteps baseSteps = new BaseSteps();
			WebDriver driver = new ChromeDriver();
			baseSteps.monitorAndLogWebEventSetup(driver);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void manageCookiesAndTimeoutsTest() {
		try {
			BaseSteps baseSteps = new BaseSteps();
			WebDriver driver = new ChromeDriver();
			baseSteps.manageCookiesAndTimeouts(driver);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void urlPatternMatcherTest() {
		try {
			BaseSteps baseSteps = new BaseSteps();
			Assert.assertEquals(
					baseSteps.urlPatternMatcher("https://courses.ultimateqa.com/users/sign_in/", "users/sign_in"),
					"https://courses.ultimateqa.com/users/sign_in");
			Assert.assertEquals(
					baseSteps.urlPatternMatcher("https://courses.ultimateqa.com/users/sign_in", "users/sign_in"),
					"https://courses.ultimateqa.com/users/sign_in");
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
