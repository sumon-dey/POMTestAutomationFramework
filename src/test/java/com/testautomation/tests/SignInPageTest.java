package com.testautomation.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.apache.log4j.Logger;
import com.testautomation.base.TestBase;
import com.testautomation.pages.SignInPage;
import com.testautomation.util.Util;

public class SignInPageTest extends TestBase {

	public SignInPage signInPage;
	String sheetName = "DataSheet";
	private static final Logger logger = Logger.getLogger(SignInPageTest.class);

	public SignInPageTest() {
		super();
	}

	@BeforeMethod
	public void setUp() {
		initialization();
		signInPage = new SignInPage();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
		logger.info("All opened browser instances are closed successfully.");
	}

	@Test(priority = 1)
	public void validateSignInPageTitleTest() {
		driver.get(properties.getProperty("SignInPageUrl"));
		System.out.println("Opened URL: " + properties.getProperty("SignInPageUrl"));
		logger.info("Opened URL: " + properties.getProperty("SignInPageUrl"));
		Util.takeScreenshot();
		String signInPageTitle = driver.getTitle();
		logger.info("SignInpage title is: " + signInPageTitle);
		Util.takeScreenshot();
		Assert.assertEquals(signInPageTitle, "Ultimate QA", "SignIn page title is not correct");
	}

	@Test(priority = 2)
	public void loginFunctionalityTest() {
		driver.get(properties.getProperty("SignInPageUrl"));
		System.out.println("Opened URL: " + properties.getProperty("SignInPageUrl"));
		logger.info("Opened URL: " + properties.getProperty("SignInPageUrl"));
		Util.takeScreenshot();
		signInPage.login(Util.base64Decoder(properties.getProperty("Email")),
				Util.base64Decoder(properties.getProperty("Password")));
		System.out.println("Log in is successful in the SignIn Page");
		logger.info("Log in is successful in the SignIn Page");
		Util.takeScreenshot();
	}

	@Test(priority = 3, dataProvider = "getSignInTestData")
	public void loginFunctionalityTestWithMultipleData(String email, String password) {
		driver.get(properties.getProperty("SignInPageUrl"));
		System.out.println("Opened URL: " + properties.getProperty("SignInPageUrl"));
		logger.info("Opened URL: " + properties.getProperty("SignInPageUrl"));
		Util.takeScreenshot();
		signInPage.login(Util.base64Decoder(email), Util.base64Decoder(password));
		System.out.println("Log in is successful in the SignIn Page");
		logger.info("Log in is successful in the SignIn Page");
		Util.takeScreenshot();
	}

	@DataProvider
	public Object[][] getSignInTestData() {
		Object[][] data = Util.getTestData(sheetName);
		return data;
	}

}
