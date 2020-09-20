package com.testautomation.tests;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.testautomation.base.TestBase;
import com.testautomation.pages.HomePage;
import com.testautomation.util.RetryAnalyzer;
import com.testautomation.util.Util;

public class HomePageTest extends TestBase {

	public HomePage homePage;
	private static final Logger logger = Logger.getLogger(HomePageTest.class);

	public HomePageTest() {
		super();
	}

	/**
	 * This will run before every test present in the HomePageTest class. Action
	 * involves setting up the precondition to run the tests
	 * 
	 * @author Sumon Dey
	 * @since 13/06/2020
	 * @version 0.1
	 */
	@BeforeMethod
	public void setUp() {
		initialization();
		homePage = new HomePage();
	}

	/**
	 * This will run after every test present in the HomePageTest class. Action
	 * involves the closing of the browser instances.
	 * 
	 * @author Sumon Dey
	 * @since 13/06/2020
	 * @version 0.1
	 */
	@AfterMethod
	public void tearDown() {
		driver.quit();
		logger.info("All opened browser instances are closed successfully.");
	}

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class)
	public void validateHomePageTitleTest() {
		openURL(properties.getProperty("HomePageUrl"));
		Util.takeScreenshot();
		String homePageTitle = homePage.getHomePageTitle();
		logger.info("Homepage title is: " + homePageTitle);
		Util.takeScreenshot();
		Assert.assertEquals(homePageTitle, "Automation Practice - Ultimate QA", "Home page title is not correct");
	}

	@Test(priority = 2)
	public void validateLogoTextTest() {
		openURL(properties.getProperty("HomePageUrl"));
		Util.takeScreenshot();
		String homePageLogoText = homePage.getHomePageLogoText();
		logger.info("Logo text is: " + homePageLogoText);
		Util.takeScreenshot();
		Assert.assertEquals(homePageLogoText, "Automation Practice", "Home page logo text is not correct");
	}

	@Test(priority = 3)
	public void validateHomePageContentLinksTest() {
		ArrayList<String> expectedContentLinkList = new ArrayList<String>() {
			{
				add("Big page with many elements");
				add("Fake Landing Page");
				add("Fake Pricing Page");
				add("Fill out forms");
				add("Learn how to automate an application that evolves over time");
				add("Login automation");
				add("Interactions with simple elements");
			}
		};
		openURL(properties.getProperty("HomePageUrl"));
		Util.takeScreenshot();
		Assert.assertTrue(expectedContentLinkList.equals(homePage.getHomePageContentLinks()));
	}

}
