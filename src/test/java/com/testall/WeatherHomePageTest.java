package com.testall;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.base.BaseClass;
import com.ndtvpackage.WeatherHomePage;



public class WeatherHomePageTest extends BaseClass {

	private static Logger log = LogManager.getLogger(WeatherHomePageTest.class.getName());
	WeatherHomePage weatherPage;

	
	@Test(groups = "nd",description="API Test from ndtv.com")
	public void weathertest() throws IOException {
		
		weatherPage = new WeatherHomePage(driver);
		try {
		// check city is already selected or not
		boolean isCitySelect =	weatherPage.isCitySelected("cityName");
		log.info(isCitySelect);
		
		if(!isCitySelect) {
			//if not select select the checkbox
			weatherPage.selectCheckbox("cityName");
		}
		//Get the city temperatur
		weatherPage.getTemperature("cityName");
		//Click on the cityname and take snapshot
		weatherPage.clickBangaloreTemperature();
		} 
		catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	
}
