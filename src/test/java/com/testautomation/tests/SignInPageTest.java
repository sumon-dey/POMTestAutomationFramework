package com.testautomation.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.testautomation.base.TestBase;
import com.testautomation.pages.SignInPage;

public class SignInPageTest extends TestBase {

	public SignInPage signInPage;

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
	}

	@Test(priority = 1)
	public void validateSignInPageTitleTest() {
		driver.get(prop.getProperty("signInPageUrl"));
		String signInPageTitle = driver.getTitle();
		Assert.assertEquals(signInPageTitle, "Ultimate QA", "SignIn page title is not correct");
	}

	@Test(priority = 2)
	public void loginFunctionalityTest() {
		driver.get(prop.getProperty("signInUrl"));
		signInPage.login(prop.getProperty("email"), prop.getProperty("password"));
	}

}
