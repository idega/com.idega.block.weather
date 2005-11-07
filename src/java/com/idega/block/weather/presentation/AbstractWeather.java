/*
 * $Id$
 * Created on Nov 4, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.weather.presentation;

import com.idega.block.weather.business.WeatherBusiness;
import com.idega.block.weather.business.WeatherData;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.Block;
import com.idega.presentation.IWContext;
import com.idega.presentation.Image;
import com.idega.presentation.Layer;
import com.idega.presentation.text.Text;


public abstract class AbstractWeather extends Block {

	private boolean iShowForcast = false;
	private String iWeatherID;
	
	public void main(IWContext iwc) {
		IWBundle iwb = getBundle(iwc);
		IWResourceBundle iwrb = getResourceBundle(iwc);
		
		if (iWeatherID == null) {
			add(new Text(iwrb.getLocalizedString("no_station_selected", "No weather station selected")));
			return;
		}
		
		WeatherData data = getBusiness().getWeather(iWeatherID);
		if (data == null) {
			add(new Text(iwrb.getLocalizedString("no_data_found_for_station", "No data found for weather station")));
			return;
		}
		
		Layer layer = new Layer();
		layer.setStyleClass("weather");
		
		Layer station = new Layer(Layer.DIV);
		station.setStyleClass("weatherStation");
		station.add(new Text(data.getName()));
		layer.add(station);
		
		if (data.getWeatherCode() != null) {
			Layer image = new Layer(Layer.DIV);
			image.setStyleClass("image");
			Image weatherImage = iwb.getImage("/images/"+ data.getWeatherCode() + ".png", data.getWeatherDescription());
			weatherImage.setWidth("75");
			image.add(weatherImage);
			layer.add(image);
		}
		
		Layer temperature = new Layer(Layer.DIV);
		temperature.setStyleClass("temperature");
		temperature.add(new Text(data.getTemperature().toString()) + "&deg;");
		layer.add(temperature);
		
		Layer windspeed = new Layer(Layer.DIV);
		windspeed.setStyleClass("windspeed");
		windspeed.add(new Text(data.getWindspeed() + "m/s"));
		layer.add(windspeed);
		
		if (data.getWindDirectionTxt() != null) {
			Layer windDirection = new Layer(Layer.DIV);
			windDirection.setStyleClass("windDirection");
			windDirection.add(new Text(data.getWindDirectionTxt()));
			layer.add(windDirection);
		}
		
		if (data.getClearance() != null) {
			Layer clearance = new Layer(Layer.DIV);
			clearance.setStyleClass("clearance");
			clearance.add(new Text(data.getClearance()));
			layer.add(clearance);
		}
		
		Layer time = new Layer(Layer.DIV);
		time.setStyleClass("time");
		time.add(new Text(data.getTimestamp().toString()));
		layer.add(time);
		
		add(layer);
	}
	
	protected abstract WeatherBusiness getBusiness();

	public String getBundleIdentifier() {
		return "com.idega.block.weather";
	}
	
	public void setShowForcast(boolean showForcast) {
		iShowForcast = showForcast;
	}
	
	public void setWeatherID(String weatherID) {
		iWeatherID = weatherID;
	}
}