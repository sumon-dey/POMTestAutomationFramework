package com.testautomation.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.testautomation.base.TestBase;

public class HomePage extends TestBase {

	/****************************
	 * Page Objects (Object Repository)
	 ***************************/
	@FindBy(xpath = "//h1[text()='Automation Practice']")
	@CacheLookup
	private WebElement homePageLogo;

	@FindBy(xpath = "//div[@class='et_pb_text_inner']/ul/li/a")
	private List<WebElement> homePageContentLinks;

	/****************************
	 * Initializing the Page Factory
	 ***************************/
	public HomePage() {
		PageFactory.initElements(driver, this);
	}

	/****************************
	 * Actions on the page
	 ***************************/
	public WebElement getHomePageLogo() {
		return homePageLogo;
	}

	public List<WebElement> getHomePageContentLinks() {
		return homePageContentLinks;
	}

}
