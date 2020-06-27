package com.testautomation.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebElement;
import com.testautomation.base.TestBase;

public class Util extends TestBase {

	public static long PAGE_LOAD_TIMEOUT = 80;
	public static long IMPLICIT_WAIT = 10;
	public static String TESTDATA_SHEET_PATH = "./src/main/java/com/testautomation/testdata/TestData.xlsx";
	static Workbook workbook;
	static Sheet sheet;

	public void switchToFrame(int index) {
		driver.switchTo().frame(index);
	}

	public void switchToFrame(String nameOrId) {
		driver.switchTo().frame(nameOrId);
	}

	public void switchToFrame(WebElement webElement) {
		driver.switchTo().frame(webElement);
	}

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

}
