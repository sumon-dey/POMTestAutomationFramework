package com.testautomation.steps;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.testautomation.base.BasePage;
import com.testautomation.base.BaseSteps;
import com.testautomation.pages.ComplicatedPage;
import com.testautomation.pages.HomePage;
import com.testautomation.util.Util;

/**
 * 
 * Acts as a middle layer between the Business Layer containing the tests
 * (scenarios/requirements/business intent) and the WebPage layer containing the
 * Web Elements (Object Repository/Page Objects). The main aim of this class is
 * to hold the implementation details (locating elements and performing actions)
 * of individual steps required to perform the testing of the tests present in
 * the Business Layer. Inherits from the BaseSteps class.
 * 
 * @author Sumon Dey
 * @since 13/06/2020
 * @version 0.1
 *
 */
public class HomePageSteps extends BaseSteps {

	public HomePage homePage;
	private static final Logger log = Logger.getLogger(HomePageSteps.class);

	public boolean isHeadingDisplayed() {
		homePage = new HomePage();
		Util.takeScreenshot();
		return homePage.getHomePageHeading().isDisplayed();
	}

	public boolean isSubHeadingDisplayed() {
		homePage = new HomePage();
		Util.takeScreenshot();
		return homePage.getHomePageSubHeading().isDisplayed();
	}

	public boolean areAllContentLinksDisplayed() {
		homePage = new HomePage();
		boolean isPresent = true;
		Util.takeScreenshot();
		List<WebElement> homePageContentLinkList = homePage.getHomePageAllContentLinks();
		for (WebElement eachHomePageContentLink : homePageContentLinkList) {
			log.info("Checking Link: " + eachHomePageContentLink.getText());
			if (!eachHomePageContentLink.isDisplayed()) {
				log.error("Link not getting displayed: " + eachHomePageContentLink.getText());
				Util.takeScreenshot();
				isPresent = false;
			}
		}
		return isPresent;
	}

	public boolean areAllContentLinksEnabled() {
		homePage = new HomePage();
		boolean isEnabled = true;
		Util.takeScreenshot();
		List<WebElement> homePageContentLinkList = homePage.getHomePageAllContentLinks();
		for (WebElement eachHomePageContentLink : homePageContentLinkList) {
			log.info("Checking Link: " + eachHomePageContentLink.getText());
			if (!eachHomePageContentLink.isEnabled()) {
				log.error("Link: " + eachHomePageContentLink.getText() + " -> is not enabled.");
				isEnabled = false;
			}
		}
		return isEnabled;
	}

	/**
	 * Checks all the functionalities of the link e.g. whether the links are
	 * enabled, are not broken and can navigate to the correct pages when clicked.
	 * 
	 * @return boolean functionality working correctly or not
	 * @author Sumon Dey
	 * @since 13/06/2020
	 * @version 0.1
	 */
	public boolean areAllContentLinksFunctioningCorrectly() {
		homePage = new HomePage();
		clickOn(homePage.getBigPageWithManyElementsLink());
		checkCurrentURLForContentLinkValidation(homePage, "complicated-page");
		checkCurrentPageTitleForContentLinkValidation(homePage, "Complicated Page - Ultimate QA");
		navigateBack();
		clickOn(homePage.getFakeLandingPageLink());
		checkCurrentURLForContentLinkValidation(homePage, "fake-landing-page");
		checkCurrentPageTitleForContentLinkValidation(homePage, "Fake landing page - Ultimate QA");
		navigateBack();
		clickOn(homePage.getFakePricingPageLink());
		checkCurrentURLForContentLinkValidation(homePage, "automation/fake-pricing-page");
		checkCurrentPageTitleForContentLinkValidation(homePage, "Fake pricing page - Ultimate QA");
		navigateBack();
		clickOn(homePage.getFillOutFormsLink());
		checkCurrentURLForContentLinkValidation(homePage, "filling-out-forms");
		checkCurrentPageTitleForContentLinkValidation(homePage, "Filling Out Forms - Ultimate QA");
		navigateBack();
		clickOn(homePage.getLearnHowToAutomateApplicationLink());
		checkCurrentURLForContentLinkValidation(homePage, "sample-application-lifecycle-sprint-1");
		checkCurrentPageTitleForContentLinkValidation(homePage,
				"Sample Application Lifecycle - Sprint 1 - Ultimate QA");
		navigateBack();
		clickOn(homePage.getLoginAutomationLink());
		checkCurrentURLForContentLinkValidation(homePage, "users/sign_in");
		checkCurrentPageTitleForContentLinkValidation(homePage, "Ultimate QA");
		navigateBack();
		clickOn(homePage.getInteractionsWithSimpleElementsLink());
		checkCurrentURLForContentLinkValidation(homePage, "simple-html-elements-for-automation");
		checkCurrentPageTitleForContentLinkValidation(homePage, "Simple HTML Elements For Automation - Ultimate QA");
		navigateBack();
		return true;
	}

	public void checkCurrentURLForContentLinkValidation(HomePage homePage, String resource) {
		Util.takeScreenshot();
		if (resource.equals("users/sign_in")) {
			Assert.assertEquals(urlPatternMatcher(homePage.getCurrentUrl(), resource),
					"https://courses.ultimateqa.com/" + resource, "The current url is not matching");
		} else {
			Assert.assertEquals(urlPatternMatcher(homePage.getCurrentUrl(), resource),
					"https://ultimateqa.com/" + resource, "The current url is not matching");
		}
		log.debug("The current url is matching.");

	}

	public void checkCurrentPageTitleForContentLinkValidation(HomePage homePage, String expectedPageTitle) {
		Util.takeScreenshot();
		Assert.assertEquals(homePage.getPageTitle(), expectedPageTitle, "The page title is not matching");
		log.debug("The page title is matching");
	}

	public void searchUsingSearchIcon(String searchText, String expectedUrl) {
		homePage = new HomePage();
		clickOn(homePage.getSearchIcon());
		enterText(homePage.getSearchTextBox(), searchText);
		clickOn(homePage.getComplicatedPageLink());
		if (searchText.equals("Complicated Page")) {
			Assert.assertEquals(driver.getCurrentUrl(), expectedUrl);
		}
	}

}
