package com.testautomation.unitTests;

import org.testng.annotations.Test;

import com.testautomation.base.TestBase;

public class UnitTests {
	
	@Test
	public  void launchBrowser() {
		TestBase testBase=new TestBase();
		testBase.initialization();
	}

}
