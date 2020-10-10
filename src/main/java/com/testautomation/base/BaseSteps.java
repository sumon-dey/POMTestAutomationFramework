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
import com.testautomation.exceptions.exceptionHandling.FileExceptionHandling;
import com.testautomation.exceptions.exceptionHandling.TestInitializationExceptionHandling;
import com.testautomation.util.Util;
import com.testautomation.util.WebEventListener;

/**
 * Base class for the test steps. Consists of actions which are common to the
 * test steps. The test classes will inherit this class - its properties and
 * behavior. Inherits from the BasePage class.
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
	 * Loads the properties configuration file. Also handles related exceptions and
	 * makes use of custom framework exception to display relevant exception
	 * details. The need of using a custom framework exception is to wrap the
	 * default exceptions with meaningful business-level exception descriptions.
	 * 
	 * @param Name
	 *            of the Configuration Properties file
	 * @author Sumon Dey
	 * @since 13/06/2020
	 * @version 0.1
	 * 
	 */
	public void loadConfigProperties(String configPropertiesFileName) {
		try {
			if (properties == null) {
				propertyFileName = configPropertiesFileName;
				log.debug("Configuration properties file to be loaded is: \"" + propertyFileName + "\".");
				String propertyFilePath = System.getProperty("user.dir")
						+ "/src/test/resources/com/testautomation/config/" + propertyFileName;
				properties = new Properties();
				inputStream = new FileInputStream(new File(propertyFilePath));
				properties.load(inputStream);
				log.debug("Configuration properties file \"" + propertyFileName + "\"is loaded successfully.");
			}
		} catch (Exception e) {
			(new FileExceptionHandling()).handlePropertiesFileException(e, propertyFileName);
		} finally {
			streamCleanup(inputStream);
		}
	}

	/**
	 * Closes stream resources after performing null check operation.
	 * 
	 * @param stream
	 *            Any stream object implementing the Closeable interface
	 * @author Sumon Dey
	 * @since 13/06/2020
	 * @version 0.1
	 * 
	 */
	public void streamCleanup(Closeable stream) {
		try {
			if (stream != null) {
				stream.close();
				log.debug("Stream is closed successfully.");
			}
		} catch (Exception e) {
			log.error(e);
		}
		log.info("Stream cleanup complete.");
	}

	/**
	 * Forms the base to initialize and drive the tests. The functionalities
	 * provided include calling other methods to set up the browsers, setting up the
	 * monitoring/logging for web events and manage cookies/timeouts.
	 * 
	 * @author Sumon Dey
	 * @since 13/06/2020
	 * @version 0.1
	 */
	public void testInitialization() {
		try {
			String browserName = "";
			if (System.getProperty("Browser") != null && !System.getProperty("Browser").isEmpty()) {
				browserName = System.getProperty("Browser");
			} else {
				browserName = properties.getProperty("Browser");
			}
			log.debug("Browser(s) to be launched: " + browserName);
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
			log.error(e);
			throw e;
		}
	}

	/**
	 * 
	 * Takes the WebDriver object, wraps it with EventFiringWebDriver object (which
	 * will register WebDriverListener implementing class) to monitor and log web
	 * events, and returns it.
	 * 
	 * @param driver
	 *            A WebDriver type object
	 * @return eventFiringWebDriver A EventFiringWebDriver type object
	 * @author Sumon Dey
	 * @since 30/09/2020
	 * @version 0.1
	 * 
	 */
	public EventFiringWebDriver monitorAndLogWebEventSetup(WebDriver driver) {
		try {
			eventFiringWebDriver = new EventFiringWebDriver(driver);
			webEventListener = new WebEventListener();
			eventFiringWebDriver.register(webEventListener);
			log.debug("WebDriverEventListener is registered.");
		} catch (Exception e) {
			log.error(e);
		}
		return eventFiringWebDriver;
	}

	/**
	 * Initializes the browser driver (chromedriver) for Chrome and launches Chrome
	 * (with capabilities). To get the capabilities, it calls the getChromeOptions()
	 * method.
	 * 
	 * @param driver
	 *            A WebDriver type object
	 * @return driver A WebDriver type object
	 * @author Sumon Dey
	 * @since 30/09/2020
	 * @version 0.1
	 */
	public WebDriver chromeSetUp(WebDriver driver) {
		try {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + File.separator + "drivers" + File.separator + "chromedriver.exe");
			driver = new ChromeDriver(getChromeOptions());
			log.debug("ChromeDriver has started.");
		} catch (Exception e) {
			log.error(e);
		}
		return driver;
	}

	/**
	 * Initializes the browser driver (geckodriver) for Firefox and launches Firefox
	 * 
	 * @param driver
	 *            A WebDriver type object
	 * @return driver A WebDriver type object
	 * @author Sumon Dey
	 * @since 30/09/2020
	 * @version 0.1
	 */
	public WebDriver firefoxSetUp(WebDriver driver) {
		try {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + File.separator + "drivers" + File.separator + "geckodriver.exe");
			driver = new FirefoxDriver();
			log.debug("FirefoxDriver has started.");
		} catch (Exception e) {
			log.error(e);
		}
		return driver;
	}

	/**
	 * Deletes cookies and defines default Timeouts (PageLoadTimeout and
	 * ImplicitWait)
	 * 
	 * @param driver
	 *            A WebDriver type object
	 * @author Sumon Dey
	 * @since 30/09/2020
	 * @version 0.1
	 * 
	 */
	public void manageCookiesAndTimeouts(WebDriver driver) {
		try {
			driver.manage().deleteAllCookies();
			log.info("All cookies are deleted.");
			driver.manage().timeouts().pageLoadTimeout(Util.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(Util.IMPLICIT_WAIT, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * Sets up ChromeDriver-specific capabilities to configure and drive a
	 * ChromeDriver session.
	 * 
	 * @return chromeOptions A ChromeOptions object (which will manage the
	 *         capabilties specific to the browser session)
	 * @author Sumon Dey
	 * @since 30/09/2020
	 * @version 0.1
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
			/* Setting up default download directory */
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("download.default_directory", System.getProperty("user.dir") + File.separator + "downloads");
			chromeOptions.setExperimentalOption("prefs", prefs);
		} catch (Exception e) {
			log.error(e);
		}
		return chromeOptions;
	}

	/**
	 * Consists of all the common actions required to perform cleanup activities
	 * after the tests are run.
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
	 * Takes URL as input and format its to remove the last forward slash (if
	 * present). Sometimes, the URL changes dynamically to add forward slash(/) at
	 * the end and remaining times it doesn't. So, it should not matter whether the
	 * forward slash is present at the end or not, as long as the remaining URL
	 * string remains as expected.
	 * 
	 * @param url
	 *            The url String to be formatted
	 * @param stringToMatch
	 *            The String to be matched with
	 * @return url The formatted URL string
	 * @author Sumon Dey
	 * @since 13/06/2020
	 * @version 0.1
	 */
	public String urlPatternMatcher(String url, String stringToMatch) {
		Matcher matcher = Pattern.compile(stringToMatch + "\\/").matcher(url);
		if (matcher.find()) {
			url = url.replaceFirst(stringToMatch + "\\/", stringToMatch);
		}
		return url;
	}

}
