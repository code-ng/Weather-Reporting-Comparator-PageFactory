/*
 * Copyright 2020 Nirupam Ghosh (niupamghosh.blr@gmail.com)
 * SPDX-License-Identifier: MIT
 */


package com.testall;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.base.BaseClass;
import com.utilitypackage.ExcelOperation;
import com.weather.CompareData;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;



/**
 * APITest fetch the tempreatrue of a given city 
 * @author Nirupam
 *
 */
public class APITest{
	private static Logger log = LogManager.getLogger(APITest.class.getName());
	ExcelOperation excel = new ExcelOperation();
	BaseClass base= new BaseClass();
	String response = "";
	//Getting the values from config file
	String key = base.getPropertyValue("key");
	String valueUnit = base.getPropertyValue("valueUnit");
	String cityName = base.getPropertyValue("cityName");
	String getDataOf = base.getPropertyValue("getDataOf");
	String uril= base.getPropertyValue("uril");
	String tempValue = base.getPropertyValue("tempValue");
	String cName = base.getPropertyValue("cName");
	String sCode = base.getPropertyValue("statCode");
	JsonPath js;
	@Test(groups="api",description="API Test from openweathemap.com")
	public void openWeatherTemperatureTest() throws IOException {	

	
		RestAssured.baseURI =uril ;
		//Capturing the values of get response and validate the status code
		String response = 
				given().
					log().all().queryParam("q", cityName)
					.queryParam("appid", key).queryParam("units", valueUnit)
				.when()
					.get(getDataOf)
				.then()
					.assertThat().log().all().statusCode(200).extract().response().asString();

		log.info(response);
		js = new JsonPath(response);
		String tempV = js.getString(tempValue);
		String city = base.getPropertyValue("cityName");
		String newName = "API_"+city;
		//storing the  values in excel sheet for comparing purpose
		excel.writeExcel(newName, tempV,"WeatherAPI");
	}

	@Test(priority=1,description="Validating name from API Test from openweathemap.com")
	public void validateName() {
		
		String getCName = js.getString(cName);
		String city = base.getPropertyValue("cityName");
		Assert.assertEquals(getCName,city);
	}
	
	@Test(priority=2,description="Veryfing statuscode with negative value from API Test from openweathemap.com")
	public void verifyStatusCode() {
		String statusCode = js.getString("cod");
		String code = base.getPropertyValue("statCode");
		Assert.assertEquals(statusCode,code);
	}
	
	
}
