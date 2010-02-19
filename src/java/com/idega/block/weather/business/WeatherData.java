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
	private String windDirection;
	private String weatherDescription;
	private String weatherCode;
	private String clearance;
	 
	public String getClearance() {
		return this.clearance;
	}
	
	public void setClearance(String clearance) {
		this.clearance = clearance;
	}
	
	public String getID() {
		return this.id;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Float getTemperature() {
		return this.temperature;
	}
	
	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}
	
	public String getWeatherCode() {
		return this.weatherCode;
	}
	
	public void setWeatherCode(String weatherCode) {
		this.weatherCode = weatherCode;
	}
	
	public String getWeatherDescription() {
		return this.weatherDescription;
	}
	
	public void setWeatherDescription(String weatherDescription) {
		this.weatherDescription = weatherDescription;
	}
	
	public String getWindDirection() {
		return this.windDirection;
	}
	
	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}
	
	public Float getWindspeed() {
		return this.windspeed;
	}
	
	public void setWindspeed(Float windspeed) {
		this.windspeed = windspeed;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}
	
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("name:").append(getName());
		builder.append("&");
		builder.append("id:").append(getID());
		builder.append("&");
		builder.append("time:").append(getTimestamp().toString());
		builder.append("&");
		builder.append("temperature:").append(getTemperature());
		builder.append("&");
		builder.append("windSpeed:").append(getWindspeed());
		builder.append("&");
		builder.append("windDirection:").append(getWindDirection());
		builder.append("&");
		builder.append("description:").append(getWeatherDescription());
		builder.append("&");
		builder.append("code:").append(getWeatherCode());
		builder.append("&");
		builder.append("clearance:").append(getClearance());
		
		return builder.toString();
	}
}