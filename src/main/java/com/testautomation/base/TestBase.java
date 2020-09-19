package com.testautomation.base;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.apache.log4j.Logger;
import com.testautomation.util.Util;
import com.testautomation.util.WebEventListener;
import org.openqa.selenium.Proxy;

/**
 * This is a base class for the tests and acts as a super class for all the test
 * classes. During its object creation, its constructor will load the
 * configuration properties files.
 * 
 * @author Sumon Dey
 * @since 13/06/2020
 * @version 0.1
 *
 *
 */
public class TestBase {
	public static WebDriver driver;
	public static Properties properties;
	private FileInputStream fileInputStream;
	private File file;
	public static EventFiringWebDriver eventFiringWebDriver;
	public static WebEventListener webEventListener;
	private static final Logger logger = Logger.getLogger(TestBase.class);

	public TestBase() {
		try {
			properties = new Properties();
			file = new File(
					System.getProperty("user.dir") + "/src/test/resources/com/testautomation/config/config.properties");
			fileInputStream = new FileInputStream(file);
			properties.load(fileInputStream);
			logger.info("Configuration properties file is loaded successfully.");
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
			streamCleanup(fileInputStream);
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
	public static void streamCleanup(Closeable stream) {
		try {
			if (stream != null) {
				stream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	public static void initialization() {
		try {
			String browserName = properties.getProperty("Browser");
			System.out.println("Launched browser is: " + browserName);
			logger.info("Launched browser is: " + browserName);
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
					System.out.println("ChromeDriver has started.");
					logger.info("ChromeDriver has started.");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "firefox":
				try {
					System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
					driver = new FirefoxDriver();
					System.out.println("FirefoxDriver is started.");
					logger.info("FirefoxDriver is started.");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				System.out.println(browserName + " is not supported.");
				logger.info(browserName + " is not supported.");
				break;
			}
			eventFiringWebDriver = new EventFiringWebDriver(driver);
			webEventListener = new WebEventListener();
			eventFiringWebDriver.register(webEventListener);
			driver = eventFiringWebDriver;
			driver.manage().deleteAllCookies();
			System.out.println("All cookies are deleted.");
			logger.info("All cookies are deleted.");
			driver.manage().timeouts().pageLoadTimeout(Util.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(Util.IMPLICIT_WAIT, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openURL(String url) {
		driver.get(url);
		System.out.println("Opened URL: " + properties.getProperty("HomePageUrl"));
		logger.info("Opened URL: " + properties.getProperty("HomePageUrl"));
	}

}
