/*
 * Copyright 2020 Nirupam Ghosh (niupamghosh.blr@gmail.com)
 * SPDX-License-Identifier: MIT
 */

package com.ndtvpackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.BaseClass;
import com.utilitypackage.ExcelOperation;


/**
 * 
 * WeatherHomePage page of NDTV
 * @author Nirupam
 *
 */
/**
 * @author nirup
 *
 */
public class WeatherHomePage  {

	private static Logger log = LogManager.getLogger(WeatherHomePage.class.getName());
	ExcelOperation excel = new ExcelOperation();
	WebDriver driver;
	BaseClass base = new BaseClass();
	
	public WeatherHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	// Locator for searchText
	@FindBy(xpath = "//input[@id='searchBox']")
	private WebElement sendValue;

	// Locator for cityAjmer
	@FindBy(xpath = "//input[@id='Ajmer']")
	private WebElement cityAjmer;

	// Locator for cityBengaluru
	@FindBy(xpath = "//input[@id='Bengaluru']")
	private  WebElement cityBengaluru;

	// Locator for selectCityName
	@FindBy(xpath = "//div[@class='outerContainer']")
	private List<WebElement> displayCityName;

	@FindBy(xpath="//div[@class='comment_cont']")
	private WebElement sendText;
	
	// Locator for temperature of Bengaluru
	@FindBy(xpath="//div[@title='Bengaluru']//span[@class='tempRedText']")
	private WebElement tempreatureBengaluru;

	@FindBy(xpath="//div[@class='cityText' and text()='Bengaluru']")
	private WebElement clickTempreatureBengaluru;
	
	
	/**
	 * sendString() - send the city name to text
	 * @param name
	 */
	public void sendString(String name) {
		log.info("Inside sendString() method =============== ");
		boolean b = sendText.isEnabled();
		log.info(" search box is diaplyed "+b);
		log.info("Name sending in search box ->"+name);
		sendValue.sendKeys(name);
		
		log.info("Exit from sendString() method =============== ");
	}
	
	
	/**
	 * isCitySelected()- check city name is selected or not
	 * @param name
	 * @return
	 * @throws InterruptedException
	 */
	public boolean isCitySelected(String cityName) throws InterruptedException {
		log.info("Inside isCitySelected() method =============== ");
		Thread.sleep(5000);
		String name = base.getPropertyValue(cityName);
		log.info("Name received to search for :: "+ name);
		
		Thread.sleep(1000);
		boolean isChecked = false;
		switch(name) {

		case "Ajmer":
			isChecked = cityAjmer.isSelected();
			return isChecked;
		case "Bengaluru":
			isChecked = cityBengaluru.isSelected();
			return isChecked;

		default:
			log.info("Please add city name");
		}
		log.info("Exit from isCitySelected() method =============== ");
		return isChecked;

	}

	
	/**
	 * checkCityCheckBox(String name)()- check city check box
	 * @param name
	 */
	public void checkCityCheckBox(String name) {
		log.info("Inside checkCityCheckBox() method =============== ");
		switch (name) {

		case "Ajmer":
			cityAjmer.click();

		case "Bengaluru":
			cityBengaluru.click();
		default:
			log.info("Please add city name");
		}
		log.info("Exit from checkCityCheckBox() method =============== ");
	}

	/**
	 * selectCheckbox()- select check box
	 * @param name
	 * @throws InterruptedException
	 */
	public void selectCheckbox(String cityName) throws InterruptedException {
		log.info("Inside selectCheckbox() method =============== ");
		
		String name = base.getPropertyValue(cityName);
		sendValue.clear();
		sendValue.sendKeys(name);
		boolean result = isCitySelected(name);

		if (result) {
			return;
		} else {
			checkCityCheckBox(name);
		}
		log.info("Exit from selectCheckbox() method =============== ");
	}



	/**
	 * getTemperature() - get the temperature value
	 * @param cityName
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void getTemperature(String cityName) throws IOException, InterruptedException {
		log.info("Inside getTemperature() method =============== ");
		Thread.sleep(1500);
		String city = base.getPropertyValue(cityName);
		String tempValue="";
		String temp="";
		List<WebElement> l = displayCityName;
		log.info("list size is " + l.size());
		for (int i = 0; i < l.size(); i++) {
			String s = l.get(i).getAttribute("title");
			System.out.println(s);
			if (s.equals(city)) {
				WebElement temperature = tempreatureBengaluru;
				System.out.println(temperature.getText());
				tempValue = temperature.getText();
				temp = tempValue.substring(0, tempValue.length() - 1);

				break;

			}

		}

		String newName = "NDTV_" + city;

		excel.writeExcel(newName, temp, "NDTV");
		
		log.info("Exit from getTemperature() method =============== ");
	}

	
	
	/**
	 * clickBangaloreTemperature() - click on Bnagalore tempreature
	 * @throws Exception
	 */
	public void clickBangaloreTemperature() throws Exception {
		log.info("Inside clickBangaloreTemperature() method =============== ");
		isNameDisplayed();
		Thread.sleep(8000);
		clickTempreatureBengaluru.click();
		log.info(" Going to take screen shot");
		base.takeScreesnShot();
		log.info("Exit from clickBangaloreTemperature() method =============== ");
	}

	public void isNameDisplayed() {
		log.info("Inside isNameDisplayed() method =============== ");
		boolean menu = cityBengaluru.isDisplayed();
		log.info(" Value of menu is :: " + menu);
		if(!menu) {
			WebDriverWait wait = new WebDriverWait(driver,20);
			wait.until(ExpectedConditions.visibilityOf(cityBengaluru));
			
		}
		log.info("Exit from isNameDisplayed() method =============== ");
	}

}
