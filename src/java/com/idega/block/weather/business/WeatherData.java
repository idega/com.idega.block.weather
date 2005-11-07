/*
 * $Id$
 * Created on Nov 3, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.weather.business;

import java.sql.Timestamp;

public class WeatherData {
	
	private Timestamp timestamp;
	private String id;
	private String name;
	private Float temperature;
	private Float windspeed;
	private Float windDirection;
	private String windDirectionTxt;
	private String weatherDescription;
	private String weatherCode;
	private String weatherCodeURL;
	private String clearance;
	 
	public String getClearance() {
		return clearance;
	}
	
	public void setClearance(String clearance) {
		this.clearance = clearance;
	}
	
	public String getID() {
		return id;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Float getTemperature() {
		return temperature;
	}
	
	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}
	
	public String getWeatherCode() {
		return weatherCode;
	}
	
	public void setWeatherCode(String weatherCode) {
		this.weatherCode = weatherCode;
	}
	
	public String getWeatherCodeURL() {
		return weatherCodeURL;
	}
	
	public void setWeatherCodeURL(String weatherCodeURL) {
		this.weatherCodeURL = weatherCodeURL;
	}
	
	public String getWeatherDescription() {
		return weatherDescription;
	}
	
	public void setWeatherDescription(String weatherDescription) {
		this.weatherDescription = weatherDescription;
	}
	
	public Float getWindDirection() {
		return windDirection;
	}
	
	public void setWindDirection(Float windDirection) {
		this.windDirection = windDirection;
	}
	
	public String getWindDirectionTxt() {
		return windDirectionTxt;
	}
	
	public void setWindDirectionTxt(String windDirectionTxt) {
		this.windDirectionTxt = windDirectionTxt;
	}
	
	public Float getWindspeed() {
		return windspeed;
	}
	
	public void setWindspeed(Float windspeed) {
		this.windspeed = windspeed;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}