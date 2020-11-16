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
		homePage.checkPopupWindow();
		homePage.clickEnhancedTab();
		boolean weatherDisplay = homePage.isWeatherDisplay();
		//System.out.println(weatherDisplay);
		if(weatherDisplay) {
			homePage.clickWeatherLink();
		}
		//takeScreesnShot();
		String expectedWeatherPageTitle = getWeatherPageTitle();
		String actualWeatherPageTitle = homePage.getPageTitle();
		Assert.assertEquals(actualWeatherPageTitle, expectedWeatherPageTitle);
		
	}
	

	
}
