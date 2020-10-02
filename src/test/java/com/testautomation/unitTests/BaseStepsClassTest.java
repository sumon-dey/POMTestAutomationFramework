package com.testautomation.unitTests;

import org.testng.annotations.Test;

import com.testautomation.base.BaseSteps;

public class BaseStepsClassTest {

	@Test(priority = 1)
	public void configPropertiesFileLoadPositiveTest() {
		BaseSteps baseSteps = new BaseSteps();
		String configPropertiesFileName = "config.properties";
		baseSteps.loadConfigProperties(configPropertiesFileName);
	}

	@Test(priority = 2)
	public void configPropertiesFileNotFoundExceptiontest() {
		BaseSteps baseSteps = new BaseSteps();
		String configPropertiesFileName = "config1.properties";
		baseSteps.loadConfigProperties(configPropertiesFileName);
	}

}
