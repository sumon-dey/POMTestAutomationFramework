package com.testautomation.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.testautomation.base.TestBase;

/**
 * This class acts as a object repository and consists of the Page Objects
 * present in the Home Page.
 * 
 * @author Sumon Dey
 * @since 13/06/2020
 * @version 0.1
 *
 */
public class HomePage extends TestBase {

	// ***************************************
	// Page Objects (Object Repository)
	// ***************************************
	@FindBy(xpath = "//h1[text()='Automation Practice']")
	@CacheLookup
	private WebElement homePageLogo;

	@FindBy(xpath = "//div[@class='et_pb_text_inner']/ul/li/a")
	private List<WebElement> homePageContentLinks;

	// ***************************************
	// Initializing the Page Factory
	// ***************************************
	public HomePage() {
		PageFactory.initElements(driver, this);
	}

	public String getHomePageTitle() {
		return driver.getTitle();
	}

	public String getHomePageLogoText() {
		return homePageLogo.getText();
	}

	public List<String> getHomePageContentLinks() {
		ArrayList<String> homePageContentLinksText = new ArrayList<>();
		for (WebElement homePageContentLink : homePageContentLinks) {
			homePageContentLinksText.add(homePageContentLink.getText());
		}
		return homePageContentLinksText;
	}

}
