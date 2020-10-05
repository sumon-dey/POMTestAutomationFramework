package com.testautomation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.testautomation.base.BasePage;

import lombok.Getter;

/**
 * Subclass of the "BasePage" class and acts as an object repository. Consists
 * of the Page Objects present in the "Complicated Page". Lombok has been used
 * to auto-generate the getters for the fields.
 * 
 * @author Sumon Dey
 * @since 13/06/2020
 * @version 0.1
 *
 */
@Getter
public class ComplicatedPage extends BasePage {

	// ***************************************
	// Page Objects (Object Repository)
	// ***************************************
	@FindBy(xpath = "//div[@class='et_pb_text_inner']/h2")
	private WebElement complicatedPageHeading1;

	@FindBy(id = "Section_of_Buttons")
	private WebElement complicatedPageHeading2;

	@FindBy(id = "Section_of_Social_Media_Follows")
	private WebElement complicatedPageHeading3;

	@FindBy(id = "Section_of_Random_Stuff")
	private WebElement complicatedPageHeading4;

	@FindBy(xpath = "//div[@class='et_pb_text_inner']/ul/li[1]")
	private WebElement complicatedPageSubHeading1;

	@FindBy(xpath = "//div[@class='et_pb_text_inner']/ul/li[2]")
	private WebElement complicatedPageSubHeading2;

	// ***************************************
	// Initializing the Page Factory
	// ***************************************
	public ComplicatedPage() {
		PageFactory.initElements(driver, this);
	}

}
