package com.testautomation.steps;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
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

	public boolean validateHeading() {
		homePage = new HomePage();
		return homePage.getHomePageHeading().isDisplayed();
	}

	public boolean validateSubHeading() {
		homePage = new HomePage();
		return homePage.getHomePageSubHeading().isDisplayed();
	}

	public boolean validateContentLinksDisplay() {
		homePage = new HomePage();
		try {
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
		} catch (Exception e) {
			logger.error("Exception caught is: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public boolean validateLinksAreEnabled() {
		return false;
	}

}
