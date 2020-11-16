package com.testall;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.base.BaseClass;
import com.ndtvpackage.HomePage;


public class HomePageTest extends BaseClass {


	private static Logger log = LogManager.getLogger(HomePageTest.class.getName());
	BaseClass base;
	HomePage homePage;
	@BeforeTest
	public void beforeRun() {
		
		//base.initiliaze();
	}
	

	@Test(groups = "nd",description="API Test from ndtv.com")
	public void launch_Home_Page() throws Exception {
		

		initiliaze();
		homePage= new HomePage(driver);
		// Click on pop up on diplaying
		homePage.checkPopupWindow();
		//Click on ... on main page
		homePage.clickEnhancedTab();
		
		// Validate whether 'Weather ' icon display or not
		boolean weatherDisplay = homePage.isWeatherDisplay();
		if(weatherDisplay) {
			//Click on Weather test
			homePage.clickWeatherLink();
		}
		String expectedWeatherPageTitle = getWeatherPageTitle();
		String actualWeatherPageTitle = homePage.getPageTitle();
		//Post click validate the weather page title
		Assert.assertEquals(actualWeatherPageTitle, expectedWeatherPageTitle);
		
	}
	

	
}
