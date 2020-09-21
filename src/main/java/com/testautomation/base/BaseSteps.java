package com.testautomation.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import com.testautomation.util.Util;
import com.testautomation.util.WebEventListener;

/**
 * This is a base class for the tests. It consists of actions which are common
 * to all the tests. All the test classes will inherit this class. During its
 * object creation, the constructor of this class will load the configuration
 * properties files.
 * 
 * @author Sumon Dey
 * @since 13/06/2020
 * @version 0.1
 *
 *
 */
public class BaseSteps {
	public static WebDriver driver;
	public Properties properties;
	private FileInputStream fileInputStream;
	private File file;
	public static EventFiringWebDriver eventFiringWebDriver;
	public static WebEventListener webEventListener;
	private static final Logger logger = Logger.getLogger(BaseSteps.class);

	public BaseSteps() {

	}

	public void loadConfigProperties() {
		try {
			if (properties == null) {
				properties = new Properties();
				file = new File(System.getProperty("user.dir")
						+ "/src/test/resources/com/testautomation/config/config.properties");
				fileInputStream = new FileInputStream(file);
				properties.load(fileInputStream);
				logger.debug("Configuration properties file is loaded successfully.");
			}
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException while loading the Properties file.");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("I/O Exception while loading the Properties file.");
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("Properties file could not be loaded.");
			e.printStackTrace();
		} finally {
			Util.streamCleanup(fileInputStream);
		}
	}

	/**
	 * This is a common setup method which will form the base to drive the tests.
	 * Actions include initializing browser drivers, launching browsers (with
	 * capabilities), calling web events and timeouts.
	 * 
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
				try {
					System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
					ChromeOptions chromeOptions = new ChromeOptions();
					// setting up chromeDriver-specific capabilities to configure a ChromeDriver
					// session
					chromeOptions.addArguments("start-maximized");
					// WebDriver proxy capability can be added too like below:
					// Proxy proxy = new Proxy();
					// proxy.setHttpProxy("myhttpproxy:3337");
					// chromeOptions.setCapability("proxy", proxy);
					// setting up download directory
					Map<String, Object> prefs = new HashMap<String, Object>();
					prefs.put("download.default_directory", "./downloads");
					chromeOptions.setExperimentalOption("prefs", prefs);
					driver = new ChromeDriver(chromeOptions);
					logger.debug("ChromeDriver has started.");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "firefox":
				try {
					System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
					driver = new FirefoxDriver();
					logger.debug("FirefoxDriver has started.");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				logger.warn(browserName + " is not supported.");
				break;
			}
			eventFiringWebDriver = new EventFiringWebDriver(driver);
			webEventListener = new WebEventListener();
			eventFiringWebDriver.register(webEventListener);
			driver = eventFiringWebDriver;
			driver.manage().deleteAllCookies();
			logger.info("All cookies are deleted.");
			driver.manage().timeouts().pageLoadTimeout(Util.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(Util.IMPLICIT_WAIT, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testCleanUp() {
		driver.quit();
		logger.info("All opened browser instances are closed successfully.");
	}

	public void openURL(String url) {
		driver.get(url);
	}

	public String getPageTitle() {
		return driver.getTitle();
	}

}
