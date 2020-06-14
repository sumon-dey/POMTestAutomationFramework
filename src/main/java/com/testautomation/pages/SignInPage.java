package com.testautomation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.testautomation.base.TestBase;

public class SignInPage extends TestBase {

	/****************************
	 * Page Objects (Object Repository)
	 ***************************/
	@FindBy(xpath = "//img[@title='Ultimate QA']")
	private WebElement signInPageLogo;

	@FindBy(xpath = "//h1[text()='Welcome Back!']")
	private WebElement welcomeMessage;

	@FindBy(id = "user[email]")
	private WebElement emailTextField;

	@FindBy(id = "user[password]")
	private WebElement passwordTextField;

	@FindBy(id = "user[remember_me]")
	private WebElement rememberMeCheckbox;

	@FindBy(css = ".form__forgot-password")
	private WebElement forgotPasswordLink;

	@FindBy(css = "input[value='Sign in']")
	private WebElement signInButton;

	@FindBy(xpath = "//a[contains(text(),'Create a new account')]")
	private WebElement createANewAccountLink;

	/****************************
	 * Initializing the Page Factory
	 ***************************/
	public SignInPage() {
		PageFactory.initElements(driver, this);
	}
	
	/****************************
	 * Actions on the page
	 ***************************/
	public void login(String email, String password) {
		emailTextField.sendKeys(email);
		passwordTextField.sendKeys(password);
		signInButton.click();
	}
}
