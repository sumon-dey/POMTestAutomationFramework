package com.testautomation.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.testautomation.base.BasePage;

import lombok.Getter;

/**
 * Subclass of the "BasePage" class and acts as an object repository. Consists
 * of the Page Objects present in the "Home Page". Lombok has been used to
 * auto-generate the getters for the fields.
 * 
 * @author Sumon Dey
 * @since 13/06/2020
 * @version 0.1
 *
 */
@Getter
public class HomePage extends BasePage {

	// ***************************************
	// Page Objects (Object Repository)
	// ***************************************
	@FindBy(xpath = "//div[@class='header-content']/h1")
	@CacheLookup
	private WebElement homePageHeading;

	@FindBy(xpath = "//div[@class='header-content']/span")
	private WebElement homePageSubHeading;

	@FindBy(xpath = "//div[@class='et_pb_text_inner']/ul/li/a")
	private List<WebElement> homePageAllContentLinks;

	@FindBy(xpath = "//a[text()='Big page with many elements']")
	private WebElement bigPageWithManyElementsLink;

	@FindBy(xpath = "//a[text()='Fake Landing Page']")
	private WebElement fakeLandingPageLink;

	@FindBy(xpath = "//a[text()='Fake Pricing Page']")
	private WebElement fakePricingPageLink;

	@FindBy(xpath = "//a[text()='Fill out forms']")
	private WebElement fillOutFormsLink;

	@FindBy(xpath = "//a[text()='Learn how to automate an application that evolves over time']")
	private WebElement learnHowToAutomateApplicationLink;

	@FindBy(xpath = "//a[text()='Login automation']")
	private WebElement loginAutomationLink;

	@FindBy(xpath = "//a[text()='Interactions with simple elements']")
	private WebElement interactionsWithSimpleElementsLink;

	@FindBy(xpath = "//*[@id='et_search_icon']")
	private WebElement searchIcon;

	@FindBy(xpath = "//input[@id='jetpack-instant-search__box-input-1']")
	private WebElement searchTextBox;

	@FindBy(xpath = "//a[text()='Complicated Page']")
	private WebElement complicatedPageLink;

	// ***************************************
	// Initializing the Page Factory
	// ***************************************
	public HomePage() {
		PageFactory.initElements(driver, this);
	}

}
