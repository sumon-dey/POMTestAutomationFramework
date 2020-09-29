package com.testautomation.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.testautomation.exceptions.exceptionHandling.FileExceptionHandling;
import com.testautomation.util.Util;
import com.testautomation.util.WebEventListener;

/**
 * This is a base class for the test steps. It consists of actions which are
 * common to the test steps. All the test classes will inherit this class.
 * 
 * @author Sumon Dey
 * @since 13/06/2020
 * @version 0.1
 *
 *
 */
public class BaseSteps extends BasePage {

	public Properties properties;
	private InputStream inputStream;
	private String propertyFileName;
	public static EventFiringWebDriver eventFiringWebDriver;
	public static WebEventListener webEventListener;
	public static int explicit_timeout = 30;
	private static final Logger logger = Logger.getLogger(BaseSteps.class);

	public BaseSteps() {

	}

	/**
	 * This method will load the configuration files.
	 * 
	 * @author Sumon Dey
	 * @since 13/06/2020
	 * @version 0.1
	 */
	public void loadConfigProperties() {
		try {
			if (properties == null) {
				propertyFileName = "config1.properties";
				String propertyFilePath = System.getProperty("user.dir")
						+ "/src/test/resources/com/testautomation/config/" + propertyFileName;
				properties = new Properties();
				inputStream = new FileInputStream(new File(propertyFilePath));
				properties.load(inputStream);
				logger.debug("Configuration properties file is loaded successfully.");
			}
		} catch (Exception e) {
			FileExceptionHandling.handlePropertiesFileException(e, propertyFileName);
		} finally {
			Util.streamCleanup(inputStream);
		}
	}

	/**
	 * This is a common setup method which will form the base to drive the tests.
	 * Actions include initializing the browser drivers for each browser, launching
	 * the browsers (with capabilities), calling web events and declaring implicit/
	 * payload timeouts.
	 * 
	 * @author Sumon Dey
	 * @since 13/06/2020
	 * @version 0.1
	 */
	public void testInitialization() {
		try {
			String browserName = properties.getProperty("Browser");
			logger.debug("Launched browser is: " + browserName);
			// Using "WebDriverManager" to set-up the browser initialization process
			// WebDriverManager.chromedriver().setup();
			switch (browserName.toLowerCase()) {
			case "chrome":
				driver = chromeSetUp(driver);
				break;
			case "firefox":
				driver = firefoxSetUp(driver);
				break;
			default:
				logger.warn(browserName + " is not supported.");
				break;
			}
			eventFiringWebDriver = new EventFiringWebDriver(driver);
			webEventListener = new WebEventListener();
			eventFiringWebDriver.register(webEventListener);
			driver = eventFiringWebDriver;
			manageCookiesAndTimeouts(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public WebDriver chromeSetUp(WebDriver driver) {
		try {
			System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
			driver = new ChromeDriver(getChromeOptions());
			logger.debug("ChromeDriver has started.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return driver;
	}

	public WebDriver firefoxSetUp(WebDriver driver) {
		try {
			System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
			driver = new FirefoxDriver();
			logger.debug("FirefoxDriver has started.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return driver;
	}

	public void manageCookiesAndTimeouts(WebDriver driver) {
		driver.manage().deleteAllCookies();
		logger.info("All cookies are deleted.");
		driver.manage().timeouts().pageLoadTimeout(Util.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Util.IMPLICIT_WAIT, TimeUnit.SECONDS);
	}

	public ChromeOptions getChromeOptions() {
		ChromeOptions chromeOptions = null;
		try {
			chromeOptions = new ChromeOptions();
			// setting up chromeDriver-specific capabilities to configure a ChromeDriver
			// session
			chromeOptions.addArguments("--start-maximized");
			chromeOptions.addArguments("--disable-extensions");
			chromeOptions.addArguments("--disable-plugins");
			chromeOptions.addArguments("--disable-infobars");
			// WebDriver proxy capability can be added too like below:
			// Proxy proxy = new Proxy();
			// proxy.setHttpProxy("myhttpproxy:3337");
			// chromeOptions.setCapability("proxy", proxy);
			// setting up download directory
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("download.default_directory", "./downloads");
			chromeOptions.setExperimentalOption("prefs", prefs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chromeOptions;
	}

	/**
	 * This method consists of common actions to perform cleanup activities after
	 * the tests are run.
	 * 
	 * @author Sumon Dey
	 * @since 13/06/2020
	 * @version 0.1
	 */
	public void testCleanUp() {
		driver.quit();
		logger.info("All opened browser instances are closed successfully.");
	}

	public void openURL(String url) {
		driver.get(url);
	}

	public void navigateBack() {
		driver.navigate().back();
	}

	public void navigateForward() {
		driver.navigate().forward();
	}

	public void performPageRefresh() {
		driver.navigate().refresh();
	}

	public void clickOn(WebElement webElement) {
		waitForWebElementToBeVisibleAndClickable(webElement);
		webElement.click();
	}

	private void waitForWebElementToBeVisibleAndClickable(WebElement webElement) {
		new WebDriverWait(driver, Util.EXPLICIT_WAIT).until(ExpectedConditions.visibilityOf(webElement));
		new WebDriverWait(driver, Util.EXPLICIT_WAIT).until(ExpectedConditions.elementToBeClickable(webElement));
	}

	/**
	 * This method will take URL as input and format it to remove the last forward
	 * slash (if present). Sometimes, the url changes dynamically to add forward
	 * slash(/) at the end and remaining times it doesn't. So, it should not matter
	 * whether the forward slash is present at the end or not, as long as the
	 * remaining url string remains as expected.
	 * 
	 * 
	 * @param url
	 * @param stringToMatch
	 * @return
	 */
	public String urlPatternMatcher(String url, String stringToMatch) {
		Matcher matcher = Pattern.compile(stringToMatch + "\\/").matcher(url);
		if (matcher.find()) {
			url = url.replaceFirst(stringToMatch + "\\/", stringToMatch);
		}
		return url;
	}

}
