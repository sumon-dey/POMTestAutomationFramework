package com.testautomation.base;

import java.io.Closeable;
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

import com.testautomation.exceptions.exceptionHandling.TestInitializationExceptionHandling;
import com.testautomation.exceptions.exceptionHandling.FileExceptionHandling;
import com.testautomation.util.Util;
import com.testautomation.util.WebEventListener;

/**
 * This is a base class for the test steps. It consists of actions which are
 * common to the test steps. All the test classes will inherit this class - its
 * properties and behavior
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
	private static final Logger log = Logger.getLogger(BaseSteps.class);

	/**
	 * This method will load the properties configuration file. It will also handle
	 * related exceptions and make use of custom framework exception to display
	 * relevant exception details. The need of using a custom framework exception is
	 * to wrap the default exceptions with meaningful business-level exception
	 * descriptions.
	 * 
	 * @author Sumon Dey
	 * @since 13/06/2020
	 * @version 0.1
	 */
	public void loadConfigProperties() {
		try {
			if (properties == null) {
				propertyFileName = "config.properties";
				String propertyFilePath = System.getProperty("user.dir")
						+ "/src/test/resources/com/testautomation/config/" + propertyFileName;
				properties = new Properties();
				inputStream = new FileInputStream(new File(propertyFilePath));
				properties.load(inputStream);
				log.debug("Configuration properties file is loaded successfully.");
			}
		} catch (Exception e) {
			(new FileExceptionHandling()).handlePropertiesFileException(e, propertyFileName);
		} finally {
			streamCleanup(inputStream);
		}
	}

	/**
	 * This is a common method which cleans up stream resources after performing
	 * null check.
	 * 
	 * @author Sumon Dey
	 * @since 13/06/2020
	 * @version 0.1
	 * 
	 */
	public void streamCleanup(Closeable stream) {
		try {
			if (stream != null) {
				stream.close();
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * This is a common setup method which will form the base to initialize and
	 * drive the tests. The functionalities provided by this method include calling
	 * other methods to set up the browsers, set up the monitoring/logging for web
	 * events and manage cookies/timeouts.
	 * 
	 * @author Sumon Dey
	 * @since 13/06/2020
	 * @version 0.1
	 */
	public void testInitialization() {
		try {
			String browserName = properties.getProperty("Browser");
			log.debug("Launched browser is: " + browserName);
			// We can use "WebDriverManager" to set-up the browser initialization process
			// WebDriverManager.chromedriver().setup();
			switch (browserName.toLowerCase()) {
			case "chrome":
				driver = chromeSetUp(driver);
				break;
			case "firefox":
				driver = firefoxSetUp(driver);
				break;
			default:
				(new TestInitializationExceptionHandling())
						.handleBrowserLaunchException("The browser is not supported: ", browserName);
				break;
			}
			driver = monitorAndLogWebEventSetup(driver);
			manageCookiesAndTimeouts(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * This method will take the WebDriver object, wrap it with EventFiringWebDriver
	 * object (which will register WebDriverListener implementing class) to monitor
	 * and log web events, and return it.
	 * 
	 * @author Sumon Dey
	 * @since 30/09/2020
	 * @version 0.1
	 * 
	 */
	public EventFiringWebDriver monitorAndLogWebEventSetup(WebDriver driver) {
		eventFiringWebDriver = new EventFiringWebDriver(driver);
		webEventListener = new WebEventListener();
		eventFiringWebDriver.register(webEventListener);
		return eventFiringWebDriver;
	}

	/**
	 * This method will initialize the browser driver for Chrome browser and launch
	 * the Chrome browser (with capabilities)
	 * 
	 * @param driver
	 * @return the driver object after adding the capabilties
	 */
	public WebDriver chromeSetUp(WebDriver driver) {
		try {
			System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
			driver = new ChromeDriver(getChromeOptions());
			log.debug("ChromeDriver has started.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return driver;
	}

	public WebDriver firefoxSetUp(WebDriver driver) {
		try {
			System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
			driver = new FirefoxDriver();
			log.debug("FirefoxDriver has started.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return driver;
	}

	public void manageCookiesAndTimeouts(WebDriver driver) {
		driver.manage().deleteAllCookies();
		log.info("All cookies are deleted.");
		driver.manage().timeouts().pageLoadTimeout(Util.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Util.IMPLICIT_WAIT, TimeUnit.SECONDS);
	}

	/**
	 * This method will set up ChromeDriver-specific capabilities to configure and
	 * drive a ChromeDriver session.
	 * 
	 * @return ChromeOptions object (which will manage the capabilties specific to
	 *         the browser session)
	 */
	public ChromeOptions getChromeOptions() {
		ChromeOptions chromeOptions = null;
		try {
			chromeOptions = new ChromeOptions();
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
		log.info("All opened browser instances are closed successfully.");
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
