package com.testautomation.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Base64;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import com.testautomation.base.BaseSteps;

public class Util extends BaseSteps {

	public static long PAGE_LOAD_TIMEOUT = 80;
	public static long IMPLICIT_WAIT = 10;
	public static long EXPLICIT_WAIT = 30;
	static String TESTDATA_SHEET_PATH = "./src/test/resources/com/testautomation/testdata/TestData.xlsx";
	static Workbook workbook;
	static Sheet sheet;
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	private static final Logger logger = Logger.getLogger(Util.class);

	public void switchToFrame(int index) {
		driver.switchTo().frame(index);
	}

	public void switchToFrame(String nameOrId) {
		driver.switchTo().frame(nameOrId);
	}

	public void switchToFrame(WebElement webElement) {
		driver.switchTo().frame(webElement);
	}

	/**
	 * This utlity method will fetch data from the excel file for data-driven
	 * testing and return the fetched data as a two-dimensional object array.
	 * 
	 * @author Sumon Dey
	 * @since 13/06/2020
	 * @version 0.1
	 * @param sheetName
	 * @return two-dimensional object array
	 */
	public static Object[][] getTestData(String sheetName) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			workbook = WorkbookFactory.create(fileInputStream);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = workbook.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
			}
		}
		return data;
	}

	/**
	 * This utlitiy method will help to take screenshots of the browser whenever
	 * called and store those inside the "screenshots" folder under the root
	 * directory.
	 * 
	 * @author Sumon Dey
	 * @since 13/06/2020
	 * @version 0.1
	 */
	public static void takeScreenshot() {
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(sourceFile, new File(System.getProperty("user.dir") + "\\screenshots\\Screenshot_"
					+ System.currentTimeMillis() + ".png"));
		} catch (IOException e) {
			logger.error("Failed to create screenshot file.");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will take Base64 encoded data as input, decode the data and
	 * return the decoded value
	 * 
	 * @param encodedString
	 * @return decoded value as String
	 */
	public static String base64Decoder(String encodedString) {
		byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
		String decodedString = new String(decodedBytes);
		return decodedString;
	}

	public static String dateTimeFormatter() {
		return "[ " + simpleDateFormat.format(new Timestamp(System.currentTimeMillis())) + " ] ";
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

}
