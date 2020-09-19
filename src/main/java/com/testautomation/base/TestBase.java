package com.testautomation.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.testautomation.util.Util;
import com.testautomation.util.WebEventListener;

/**
 * This is a base class for the tests and acts as a super class for all the test
 * classes. During its object creation, its constructor will load configuration
 * properties file.
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
	public static EventFiringWebDriver eventFiringWebDriver;
	public static WebEventListener webEventListener;
	private static final Logger logger = Logger.getLogger(TestBase.class);

	public TestBase() {
		try {
			properties = new Properties();
			File file = new File(
					System.getProperty("user.dir") + "/src/test/resources/com/testautomation/config/config.properties");
			FileInputStream fileInputStream = new FileInputStream(file);
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
		}
	}

	/**
	 * This is a common setup method which will form the base to drive the tests.
	 * 
	 * @author Sumon Dey
	 * @since 13/06/2020
	 * @version 0.1
	 */
	public static void initialization() {
		try {
			String browserName = properties.getProperty("browser");
			System.out.println("Launched browser is: " + browserName);
			logger.info("Launched browser is: " + browserName);
			// Using "WebDriverManager" to set-up the browser initialization process
			// WebDriverManager.chromedriver().setup();
			switch (browserName.toLowerCase()) {
			case "chrome":
				try {
					System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
					ChromeOptions chromeOptions = new ChromeOptions();
					chromeOptions.addArguments("--start-maximized");
					driver = new ChromeDriver(chromeOptions);
					System.out.println("ChromeDriver is started.");
					logger.info("ChromeDriver is started.");
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
			logger.info("All cookies are deleted.");
			driver.manage().timeouts().pageLoadTimeout(Util.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(Util.IMPLICIT_WAIT, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to take screenshot
	 * 
	 * @author Sumon
	 */
	public static void takeScreenshot() {
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File srcFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		try {
			FileUtils.copyFile(srcFile, new File(currentDir + "\\screenshots\\" + System.currentTimeMillis() + ".png"));
			logger.info("Screenshot file created successfully..");
		} catch (IOException e) {
			logger.error("Failed to create screenshot file.");
			e.printStackTrace();
		}

	}

}
