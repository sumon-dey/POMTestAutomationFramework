package com.testautomation.unitTests;

import org.testng.annotations.Test;

import com.testautomation.base.BaseSteps;

public class UnitTests {
	
	@Test
	public  void launchBrowser() {
		BaseSteps testBase=new BaseSteps();
		testBase.testInitialization();
	}

}
