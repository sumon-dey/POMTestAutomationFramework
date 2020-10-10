package com.testautomation.unitTests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.testautomation.base.BaseSteps;
import com.testautomation.exceptions.BrowserException;
import com.testautomation.exceptions.FileException;

public class BaseStepsClassTest {

	@Test
	public void configPropertiesFileLoadPositiveTest() {
		BaseSteps baseSteps = new BaseSteps();
		String configPropertiesFileName = "config.properties";
		baseSteps.loadConfigProperties(configPropertiesFileName);
	}

	@Test(expectedExceptions = FileException.class)
	public void configPropertiesFileNotFoundExceptionTest() {
		BaseSteps baseSteps = new BaseSteps();
		String configPropertiesFileName = "config1.properties";
		try {
			baseSteps.loadConfigProperties(configPropertiesFileName);
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(),
					"Properties file \"" + configPropertiesFileName + "\" is not found in the classpath.");
			throw e;
		}
	}

	@Test
	public void chromeOptionsSettingTest() {
		BaseSteps baseSteps = new BaseSteps();
		baseSteps.getChromeOptions();
	}

	@Test
	public void chromeSetUpAndLaunchTest() {
		BaseSteps baseSteps = new BaseSteps();
		WebDriver driver = null;
		baseSteps.chromeSetUp(driver);
	}

	@Test
	public void firefoxSetUpAndLaunchTest() {

	}

	@Test
	public void testInitializationTest() {

	}

	@Test
	public void browserNotSupportedTest() {

	}

	@Test
	public void monitorAndLogWebEventSetupTest() {

	}

	@Test
	public void manageCookiesAndTimeoutsTest() {

	}

	@Test
	public void urlPatternMatcherTest() {

	}

	@Test
	public void clickOnWebElementTest() {

	}

}
