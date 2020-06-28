package com.testautomation.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.testautomation.util.Util;
import com.testautomation.util.WebEventListener;

public class TestBase {
	public static WebDriver driver;
	public static Properties prop;
	public static EventFiringWebDriver eventFiringWebDriver;
	public static WebEventListener webEventListener;

	public TestBase() {
		try {
			prop = new Properties();
			File file = new File(
					System.getProperty("user.dir") + "/src/main/java/com/testautomation/config/config.properties");
			FileInputStream fileInputStream = new FileInputStream(file);
			prop.load(fileInputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void initialization() {
		String browserName = prop.getProperty("browser");
		System.out.println("Browser is: " + browserName);
		switch (browserName.toLowerCase()) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case "firefox":
			System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		}
		eventFiringWebDriver = new EventFiringWebDriver(driver);
		webEventListener = new WebEventListener();
		eventFiringWebDriver.register(webEventListener);
		driver = eventFiringWebDriver;
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Util.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Util.IMPLICIT_WAIT, TimeUnit.SECONDS);
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
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
