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

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;
import com.idega.block.weather.business.WeatherBusiness;
import com.idega.block.weather.business.WeatherData;
import com.idega.block.weather.business.WeatherSession;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.Block;
import com.idega.presentation.IWContext;
import com.idega.presentation.Image;
import com.idega.presentation.Layer;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.Form;


public abstract class AbstractWeather extends Block {

	private String iWeatherID;
	private String weatherImageLocation = null;
	private String weatherImageType = ".png";
	private String weatherImageWidth = "75";
	
	public void main(IWContext iwc) throws RemoteException {
		IWBundle iwb = getBundle(iwc);
		IWResourceBundle iwrb = getResourceBundle(iwc);
		
		String wID = iwc.getParameter("wstations");
		if (wID != null) {
			iWeatherID = wID;
			getSession(iwc).setWeatherStationID(iWeatherID);
		}
		else if (getSession(iwc).getWeatherStationID() != null) {
			iWeatherID = getSession(iwc).getWeatherStationID();
		}
		else if (iWeatherID != null) {
			getSession(iwc).setWeatherStationID(iWeatherID);
		}
		
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
			Image weatherImage = null;
			if (weatherImageLocation == null) {
				weatherImage = iwb.getImage("/images/"+ data.getWeatherCode() + weatherImageType, data.getWeatherDescription());
			}
			else {
				weatherImage = new Image(weatherImageLocation+data.getWeatherCode()+weatherImageType, data.getWeatherDescription());
			}
			weatherImage.setWidth(weatherImageWidth);
			image.add(weatherImage);
			layer.add(image);
		}
		
		Float temp = data.getTemperature();
		Layer temperature = new Layer(Layer.DIV);
		temperature.setStyleClass("temperature");

		Layer temperatureSign = new Layer(Layer.DIV);
		temperature.setStyleClass("temperatureSign");

		if (temp.floatValue() > 0) {
			temperature.setStyleClass("positive");
			temperatureSign.setStyleClass("positive");
		}
		else if (temp.floatValue() < 0) {
			temperature.setStyleClass("negative");
			temperatureSign.setStyleClass("negative");
		}
		else if (temp.floatValue() == 0) {
			temperature.setStyleClass("zero");
			temperatureSign.setStyleClass("zero");
		}
		
		temperature.add(new Text(temp.toString()) + "&deg;");
		temperature.add(new Text(getBusiness().getTemperatureSign()));
		layer.add(temperature);
		layer.add(temperatureSign);
		
		Layer windspeed = new Layer(Layer.DIV);
		windspeed.setStyleClass("windspeed");
		windspeed.add(new Text(data.getWindspeed() + Text.NON_BREAKING_SPACE + getBusiness().getWindSpeedUnit()));
		layer.add(windspeed);
		
		if (data.getWindDirectionTxt() != null) {
			Layer windDirection = new Layer(Layer.DIV);
			windDirection.setStyleClass("windDirection");
			windDirection.add(new Text(data.getWindDirectionTxt()));
			layer.add(windDirection);
			
			Layer windDirectionImage = new Layer(Layer.DIV);
			windDirectionImage.setStyleClass("windDirectionIcon");
			windDirectionImage.add(iwb.getImage("/images/"+ data.getWindDirectionTxt() + weatherImageType, data.getWindDirectionTxt()));
			layer.add(windDirectionImage);
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
		
		
		Collection weatherStations = getBusiness().getWeatherStations();
		Iterator iter = weatherStations.iterator();
		Form form = new Form();
		DropdownMenu stationsDM = new DropdownMenu("wstations");
		while (iter.hasNext()) {
			WeatherData wd = (WeatherData) iter.next();
			stationsDM.addMenuElement(wd.getID(), wd.getName());
		}
		stationsDM.setSelectedElement(iWeatherID);
		stationsDM.setToSubmit();
		form.add(stationsDM);
		Layer stations = new Layer(Layer.DIV);
		stations.setStyleClass("weatherStations");
		stations.add(form);
		layer.add(stations);
		
		add(layer);
	}
	
	protected abstract WeatherBusiness getBusiness();
	
	protected WeatherSession getSession(IWContext iwc) {
		try {
			return (WeatherSession) IBOLookup.getSessionInstance(iwc, WeatherSession.class);
		}
		catch (IBOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}

	public String getBundleIdentifier() {
		return "com.idega.block.weather";
	}
	
	public void setWeatherImageType(String type) {
		if (type != null && !type.startsWith(".")) {
			type = "."+type;
		}
		weatherImageType = type;
	}
	
	public void setWeatherImageLocation(String location) {
		if (location != null && !location.endsWith("/")) {
			location = location+"/";
		}
		weatherImageLocation = location;
	}
	
	public void setWeatherImageWidth(String width) {
		weatherImageWidth = width;
	}
	
	public void setShowForcast(boolean showForcast) {
	}
	
	public void setWeatherID(String weatherID) {
		iWeatherID = weatherID;
	}
}