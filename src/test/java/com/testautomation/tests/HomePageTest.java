package com.testautomation.tests;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.testautomation.base.TestBase;
import com.testautomation.pages.HomePage;

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
	public void validateHomePageTitle() {
		driver.get(prop.getProperty("homePageUrl"));
		String homePageTitle = driver.getTitle();
		Assert.assertEquals(homePageTitle, "Automation Practice - Ultimate QA");
	}

	@Test(priority = 2)
	public void validateLogoText() {
		driver.get(prop.getProperty("homePageUrl"));
		String homePageLogoText = homePage.getHomePageLogo().getText();
		Assert.assertEquals(homePageLogoText, "Automation Practice");
	}

	@Test(priority = 3)
	public void getHomePageContentLinksText() {
		driver.get(prop.getProperty("homePageUrl"));
		List<WebElement> homePageContentLinkAsList = homePage.getHomePageContentLinks();
		for (WebElement webElement : homePageContentLinkAsList) {
			System.out.println(webElement.getText());
		}
	}

}
