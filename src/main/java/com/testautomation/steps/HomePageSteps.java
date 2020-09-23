package com.testautomation.steps;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.testautomation.base.BaseSteps;
import com.testautomation.pages.HomePage;

/**
 * 
 * This class acts as a middle layer between the Business Layer containing the
 * tests (scenarios/requirements/business intent) and the WebPage layer
 * containing the Web Elements (Object Repository/Page Objects). The main aim of
 * this class is to hold the implementation details (locating elements and
 * performing actions) of individual steps required to perform the testing of
 * the tests present in the Business Layer.
 * 
 * @author Sumon Dey
 * @since 13/06/2020
 * @version 0.1
 *
 */
public class HomePageSteps extends BaseSteps {

	public HomePage homePage;
	private static final Logger logger = Logger.getLogger(HomePageSteps.class);

	public HomePageSteps() {
		super();
	}

	public boolean isHeadingDisplayed() {
		homePage = new HomePage();
		return homePage.getHomePageHeading().isDisplayed();
	}

	public boolean isSubHeadingDisplayed() {
		homePage = new HomePage();
		return homePage.getHomePageSubHeading().isDisplayed();
	}

	public boolean areAllContentLinksDisplayed() {
		homePage = new HomePage();
		boolean isPresent = true;
		List<WebElement> homePageContentLinkList = homePage.getHomePageAllContentLinks();
		for (WebElement eachHomePageContentLink : homePageContentLinkList) {
			logger.info("Checking Link: " + eachHomePageContentLink.getText());
			if (!eachHomePageContentLink.isDisplayed()) {
				logger.error("Link not getting displayed: " + eachHomePageContentLink.getText());
				isPresent = false;
			}
		}
		return isPresent;
	}

	public boolean areAllContentLinksEnabled() {
		homePage = new HomePage();
		boolean isEnabled = true;
		List<WebElement> homePageContentLinkList = homePage.getHomePageAllContentLinks();
		for (WebElement eachHomePageContentLink : homePageContentLinkList) {
			logger.info("Checking Link: " + eachHomePageContentLink.getText());
			if (!eachHomePageContentLink.isEnabled()) {
				logger.error("Link: " + eachHomePageContentLink.getText() + " -> is not enabled.");
				isEnabled = false;
			}
		}
		return isEnabled;
	}

	public boolean areAllContentLinksFunctioningCorrectly() {
		homePage = new HomePage();
		clickOn(homePage.getBigPageWithManyElementsLink());
		Assert.assertEquals(urlPatternMatcher(homePage.getCurrentUrl(), "complicated-page"),
				"https://ultimateqa.com/complicated-page", "The current url is not matching");
		logger.debug("The current url is matching.");
		Assert.assertEquals(homePage.getPageTitle(), "Complicated Page - Ultimate QA",
				"The page title is not matching");
		logger.debug("The page title is matching");
		navigateBack();
		clickOn(homePage.getFakeLandingPageLink());
		Assert.assertEquals(urlPatternMatcher(homePage.getCurrentUrl(), "fake-landing-page"),
				"https://ultimateqa.com/fake-landing-page", "The current url is not matching");
		logger.debug("The current url is matching.");
		Assert.assertEquals(homePage.getPageTitle(), "Fake landing page - Ultimate QA",
				"The page title is not matching");
		logger.debug("The page title is matching");
		navigateBack();
		clickOn(homePage.getFakePricingPageLink());
		Assert.assertEquals(urlPatternMatcher(homePage.getCurrentUrl(), "fake-pricing-page"),
				"https://ultimateqa.com/automation/fake-pricing-page", "The current url is not matching");
		logger.debug("The current url is matching.");
		Assert.assertEquals(homePage.getPageTitle(), "Fake pricing page - Ultimate QA",
				"The page title is not matching");
		logger.debug("The page title is matching");
		navigateBack();
		clickOn(homePage.getFillOutFormsLink());
		Assert.assertEquals(urlPatternMatcher(homePage.getCurrentUrl(), "filling-out-forms"),
				"https://ultimateqa.com/filling-out-forms", "The current url is not matching");
		logger.debug("The current url is matching.");
		Assert.assertEquals(homePage.getPageTitle(), "Filling Out Forms - Ultimate QA",
				"The page title is not matching");
		logger.debug("The page title is matching");
		navigateBack();
		clickOn(homePage.getLearnHowToAutomateApplicationLink());
		Assert.assertEquals(urlPatternMatcher(homePage.getCurrentUrl(), "sample-application-lifecycle-sprint-1"),
				"https://ultimateqa.com/sample-application-lifecycle-sprint-1", "The current url is not matching");
		logger.debug("The current url is matching.");
		Assert.assertEquals(homePage.getPageTitle(), "Sample Application Lifecycle - Sprint 1 - Ultimate QA",
				"The page title is not matching");
		logger.debug("The page title is matching");
		navigateBack();
		clickOn(homePage.getLoginAutomationLink());
		Assert.assertEquals(urlPatternMatcher(homePage.getCurrentUrl(), "sign_in"),
				"https://courses.ultimateqa.com/users/sign_in", "The current url is not matching");
		logger.debug("The current url is matching.");
		Assert.assertEquals(homePage.getPageTitle(), "Ultimate QA", "The page title is not matching");
		logger.debug("The page title is matching");
		navigateBack();
		clickOn(homePage.getInteractionsWithSimpleElementsLink());
		Assert.assertEquals(urlPatternMatcher(homePage.getCurrentUrl(), "simple-html-elements-for-automation"),
				"https://ultimateqa.com/simple-html-elements-for-automation", "The current url is not matching");
		logger.debug("The current url is matching.");
		Assert.assertEquals(homePage.getPageTitle(), "Simple HTML Elements For Automation - Ultimate QA",
				"The page title is not matching");
		logger.debug("The page title is matching");
		navigateBack();
		return true;
	}

}
