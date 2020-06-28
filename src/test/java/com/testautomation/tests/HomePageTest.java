package com.testautomation.tests;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.testautomation.base.TestBase;
import com.testautomation.pages.HomePage;
import com.testautomation.util.Util;

public class HomePageTest extends TestBase {

	public HomePage homePage;

	public HomePageTest() {
		super();
	}

	@BeforeMethod
	public void setUp() {
		initialization();
		homePage = new HomePage();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(priority = 1)
	public void validateHomePageTitleTest() {
		driver.get(prop.getProperty("homePageUrl"));
		String homePageTitle = driver.getTitle();
		Util.takeScreenshot();
		Assert.assertEquals(homePageTitle, "Automation Practice - Ultimate QA", "Home page title is not correct");
	}

	@Test(priority = 2)
	public void validateLogoTextTest() {
		driver.get(prop.getProperty("homePageUrl"));
		Util.takeScreenshot();
		String homePageLogoText = homePage.getHomePageLogo().getText();
		Util.takeScreenshot();
		Assert.assertEquals(homePageLogoText, "Automation Practice", "Home page logo text is not correct");
	}

	@Test(priority = 3)
	public void getHomePageContentLinksTextTest() {
		driver.get(prop.getProperty("homePageUrl"));
		Util.takeScreenshot();
		List<WebElement> homePageContentLinkAsList = homePage.getHomePageContentLinks();
		Util.takeScreenshot();
		for (WebElement webElement : homePageContentLinkAsList) {
			System.out.println(webElement.getText());
		}
	}

}
