/*
 * Copyright 2020 Nirupam Ghosh (niupamghosh.blr@gmail.com)
 * SPDX-License-Identifier: MIT
 */


package com.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import com.utilitypackage.ExcelOperation;

/**
 * This a Base class which holds all the common functionality for performing the
 * test
 * 
 * @author Nirupam
 * 
 */

public class BaseClass {

	private static Logger log = LogManager.getLogger(BaseClass.class.getName());
	public static WebDriver driver;
	ExcelOperation excel = new ExcelOperation();
	public static Properties prop;
	public static Properties proplocator;
	String directoryPath = System.getProperty("user.dir");

	/**
	 * Create constructor to initialize the properties file
	 */
	public BaseClass() {

		try {
			// Get the system path
			String filePath = System.getProperty("user.dir");
			log.info("");
			log.info("Project Directory path :: " + filePath);
			// Path to config properties file
			String configFilePath = filePath + "\\config.properties";
			File file = new File(configFilePath);
			log.info("Going to load config properties file");
			prop = new Properties();
			FileInputStream fileInput = new FileInputStream(file);
			// load config properties file
			prop.load(fileInput);


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * generateFirefoxPath() - to create firefox Driver Path
	 * 
	 * @return
	 */
	private String generateFirefoxPath() {
		log.info("Inside generateFirefoxPath() method =============== ");
		String firefoxPath = getPropertyValue("firefoxDriver");
		String path = directoryPath + firefoxPath;
		log.info("Exit from generateFirefoxPath() method =============== ");
		return path;
	}

	public String getFirefoxPath() {
		return generateFirefoxPath();
	}

	/**
	 * generateChromePath() - to create chrome Driver Path
	 * 
	 * @return
	 */
	private String generateChromePath() {
		log.info("Inside generateChromePath() method =============== ");
		String chromePath = getPropertyValue("chromeDriver");
		String path = directoryPath + chromePath;
		log.info("Exit from generateChromePath() method =============== ");
		return path;
	}

	public String getChromePath() {
		return generateChromePath();
	}

	public static Map mapping() {
		Map<String, String> hmap = new HashMap<>();
		hmap.put("firefoxDriver", "webdriver.gecko.driver");
		hmap.put("chromeDriver", "webdriver.chrome.driver");

		return hmap;
	}

	/**
	 * Initialize the browser
	 * 
	 * @throws InterruptedException
	 */
	public void initiliaze() throws InterruptedException {
		log.info("Inside initiliaze() method =============== ");
		String browserName = getBrowser();
		log.info("Browser selected :: " + browserName);

		if (browserName.equals("firefox")) {
			String firefoxDriver = mapping().get("firefoxDriver").toString();
			System.setProperty(firefoxDriver, getFirefoxPath());
			driver = new FirefoxDriver();
		}
		else if (browserName.equals("chrome")) {
			String chromeDriver = mapping().get("chromeDriver").toString();
			System.setProperty(chromeDriver, generateChromePath());
			driver = new ChromeDriver();
		} else {
			// log.error("Driver not present, please look into");
		}
		String url = getPropertyValue("urlName");
		driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(12, TimeUnit.SECONDS);
		driver.get(url);
		log.info("Exit from initiliaze() method =============== ");
	}

	/**
	 * getWeatherPageTitle() - return the weather page title
	 * 
	 * @return
	 */
	public String getWeatherPageTitle() {
		log.info("Inside getWeatherPageTitle() method =============== ");
		String title = prop.getProperty("weatherPageTitle");
		if (title != null) {
			return title;
		} else {
			throw new RuntimeException("Weather Page title not available");
		}
	}

	/**
	 * getBrowser() - return browser name
	 * 
	 * @return
	 */
	public String getBrowser() {
		log.info("Inside getBrowser() method =============== ");
		String browserName = prop.getProperty("browser");
		System.out.println(browserName);
		if (browserName != null) {
			return browserName;
		} else {
			throw new RuntimeException(" BrowserName not available");
		}
	}

	/**
	 * getPropertyValue() - Generic function to return the value from config
	 * property file
	 * 
	 * @param name
	 * @return
	 */
	public String getPropertyValue(String name) {
		log.info("Inside getPropertyValue() method =============== ");
		String value = prop.getProperty(name);
	//	System.out.println(value);
		if (value != null) {
			return value;
		} else {
			throw new RuntimeException(" Value is not available -> " + name);
		}
	}

	/**
	 * getPageTitle() - return current page title
	 * 
	 * @return
	 */
	public String getPageTitle() {
		return driver.getTitle().trim();
	}

	/**
	 * takeScreesnShot() - Generic function for taking screen shot
	 * 
	 * @param driver
	 * @throws Exception
	 */
	public void takeScreesnShot() throws Exception {
		log.info("Inside takeScreesnShot() method =============== ");
		// Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot = ((TakesScreenshot) driver);

		// Call getScreenshotAs method to create image file
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

		String filePath = System.getProperty("user.dir");
		String path = prop.getProperty("screenShotCapture");

		// Open the current date and time
		String fileName = "cityTemperature";
		String timestamp = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss").format(new Date());

		String screenShotpath = filePath + path + fileName + timestamp + ".png";
		log.info("Screenshot stored at " + screenShotpath);

		// Move image file to new destination
		File destFile = new File(screenShotpath);

		// Copy file at destination
		FileUtils.copyFile(SrcFile, destFile);
		log.info("Exit from takeScreesnShot() method =============== ");
	}

		
	@AfterTest
	public void exit() {
		driver.quit();
	}

}
