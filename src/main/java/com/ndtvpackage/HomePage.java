/*
 * Copyright 2020 Nirupam Ghosh (niupamghosh.blr@gmail.com)
 * SPDX-License-Identifier: MIT
 */

package com.ndtvpackage;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.BaseClass;

/**
 * 
 * Home page of NDTV
 * @author Nirupam
 *
 */
public class HomePage {
	
	private static Logger log = LogManager.getLogger(HomePage.class.getName());
	
	static WebDriver driver;
	BaseClass base = new BaseClass();
	public HomePage(WebDriver driver) {
	this.driver =driver;
		PageFactory.initElements(driver,this);
		
	}
	// Locator for menu
	@FindBy(xpath="//a[@id='h_sub_menu']")
	private WebElement menuLink;
	
	//Locator for weather link
	@FindBy(linkText="WEATHER")
	private WebElement weather;
	
	//Locator for popup
	@FindBy(xpath="//div[@class='noti_wrap']")
	private static List<WebElement> popUp;
	
	//Locator for nothanks
	@FindBy(xpath="//div[@class='noti_btnwrap']//a[text()='No Thanks']")
	private static WebElement noThanks;
	
	
	/**
	 * isMenuDisplayed() - Validating whether menu icon displaying
	 */
	public void isMenuDisplayed() {
		log.info("Inside isMenuDisplayed() method =============== ");
		boolean menu = menuLink.isDisplayed();
		if(!menu) {
			WebDriverWait wait = new WebDriverWait(driver,20);
			wait.until(ExpectedConditions.visibilityOf(menuLink));
		}
		log.info("Exit from isMenuDisplayed() method =============== ");
	}
	
	/**
	 * clickEnhancedTab() - click on menu icon displaying
	 */
	public void clickEnhancedTab() {
		log.info("Inside clickEnhancedTab() method =============== ");
		isMenuDisplayed();
		menuLink.click();
		log.info("Exit from clickEnhancedTab() method =============== ");
	}
	
	/**
	 * clickWeatherLink() - click on Weather link
	 * @throws InterruptedException
	 */
	public void clickWeatherLink() throws InterruptedException {
		log.info("Inside clickWeatherLink() method =============== ");
		weather.click();
		Thread.sleep(2000);
		log.info("Exit from clickWeatherLink() method =============== ");
	
	}
	
	/**
	 * isWeatherDisplay() - Validating whether  displaying
	 */
	public boolean isWeatherDisplay() {
		log.info("Inside isWeatherDisplay() method =============== ");
		return weather.isDisplayed();
	}
	
	/**
	 * getPageTitle() - Fetching page title
	 */
	public String getPageTitle() {
		log.info("Inside getPageTitle() method =============== ");
		return driver.getTitle().trim();
	}
	
	
	
	/**
	 * checkPopupWindow() - checking pop up window is displaying or not 
	 * @throws InterruptedException
	 */
	public void checkPopupWindow() throws InterruptedException {
		log.info("Inside checkPopupWindow() method =============== ");
		for (int i = 0; i < 20; i++) {
			Thread.sleep(1000);
			System.out.println("Wait for " + i + "seconds for pop up");
			log.info("Wait for " + i + "seconds for pop up");
			boolean value = poupCheck();
			if (value) {
				break;
			}
		}
		log.info("Exit from checkPopupWindow() method =============== ");
	}
	
	
	private boolean poupCheck() {
		log.info("Inside poupCheck() method =============== ");
		try {

			if (popUp.size() > 0) {
				log.info(" Going to take screen shot");
				base.takeScreesnShot();
				log.info("Popup displayed clicking No Thanks");
				
				noThanks.click();
				return true;
			} else {
				log.info(" Popup did not appear");
				return false;
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		log.info("Exit from poupCheck() method =============== ");
		return false;
	}
	
}
