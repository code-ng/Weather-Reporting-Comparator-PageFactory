/*
 * Copyright 2020 Nirupam Ghosh (niupamghosh.blr@gmail.com)
 * SPDX-License-Identifier: MIT
 */


package com.weather;

public class Weather {

	float weather;
	String name;

	public float getWeather() {
		return weather;
	}

	public void setWeather(float weather, String name) {
		this.weather = weather;
		this.name = name;
	}

	public Weather(float f) {

		this.weather = f;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
