package com.testautomation.steps;

import com.testautomation.base.BaseSteps;
import com.testautomation.pages.HomePage;

public class HomePageSteps extends BaseSteps {

	public HomePage homePage;

	public HomePageSteps() {
		super();
	}

	public boolean checkHeading() {
		homePage = new HomePage();
		return homePage.getHomePageHeading().isDisplayed();
	}

	public boolean checkSubHeading() {
		homePage = new HomePage();
		return homePage.getHomePageSubHeading().isDisplayed();
	}

}
