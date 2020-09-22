package com.testautomation.tests;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.testautomation.steps.HomePageSteps;
import com.testautomation.util.RetryAnalyzer;
import com.testautomation.util.Util;

public class HomePageTest {

	public HomePageSteps homePageSteps;
	private static final Logger logger = Logger.getLogger(HomePageTest.class);

	/**
	 * This method will run before every test present in the HomePageTest class.
	 * Action involves setting up the preconditions to run the tests.
	 * 
	 * @author Sumon Dey
	 * @since 13/06/2020
	 * @version 0.1
	 */
	@BeforeMethod
	public void setUp() {
		homePageSteps = new HomePageSteps();
		homePageSteps.loadConfigProperties();
		homePageSteps.testInitialization();
	}

	/**
	 * This method will run after every test present in the HomePageTest class.
	 * Action involves cleaning up the test setup after each test execution.
	 * 
	 * @author Sumon Dey
	 * @since 13/06/2020
	 * @version 0.1
	 */
	@AfterMethod
	public void tearDown() {
		homePageSteps.testCleanUp();
	}

	// Scenario 1
	@Test(description = "To an user, HomePage headings and title should get correctly displayed.", priority = 1, retryAnalyzer = RetryAnalyzer.class)
	public void homePage_HeadingAndTitleValidation_Test() {
		homePageSteps.openURL(homePageSteps.properties.getProperty("HomePageUrl"));
		Util.takeScreenshot();
		String homePageTitle = homePageSteps.getPageTitle();
		logger.debug("HomePage title is: " + homePageTitle);
		Assert.assertEquals(homePageTitle, "Automation Practice - Ultimate QA", "HomePage title does not match.");
		logger.debug("HomePage title matches.");
		Assert.assertTrue(homePageSteps.isHeadingDisplayed(), "HomePage heading does not match.");
		logger.debug("HomePage heading matches.");
		Assert.assertTrue(homePageSteps.isSubHeadingDisplayed(), "HomePage subheading does not match.");
		logger.debug("HomePage subheading matches.");
	}

	// Scenario 2
	@Test(description = "For an user, all the HomePage content links should work correctly.", priority = 2, retryAnalyzer = RetryAnalyzer.class)
	public void homePage_contentLinksValidation_Test() {
		homePageSteps.openURL(homePageSteps.properties.getProperty("HomePageUrl"));
		Util.takeScreenshot();
		Assert.assertTrue(homePageSteps.areAllContentLinksDisplayed(),
				"All the HomePage Content Links are not getting displayed.");
		logger.debug("All the HomePage Content links are getting displayed.");
		Assert.assertTrue(homePageSteps.areAllContentLinksEnabled(),
				"All the HomePage Content Links are not in enabled condition.");
		logger.debug("All the HomePage Content links are in enabled condition.");
		homePageSteps.checkAllContentLinkFunction();
		logger.debug("All the HomePage Content links are functioning correctly.");
	}
}
