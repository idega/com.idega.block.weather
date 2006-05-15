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
import java.text.NumberFormat;
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
	
	private boolean iShowWeatherStations = true;
	private boolean iRoundValues = false;
	private boolean iShowWindUnit = true;
	
	protected final static String IW_WEATHER_BUNDLE_IDENTIFIER = "com.idega.block.weather";
	
	public void main(IWContext iwc) throws RemoteException {
		IWBundle iwb = getBundle(iwc);
		IWResourceBundle iwrb = getResourceBundle(iwc);
		
		String wID = iwc.getParameter("wstations");
		if (wID != null) {
			this.iWeatherID = wID;
			getSession(iwc).setWeatherStationID(this.iWeatherID);
		}
		else if (getSession(iwc).getWeatherStationID() != null) {
			this.iWeatherID = getSession(iwc).getWeatherStationID();
		}
		else if (this.iWeatherID != null) {
			getSession(iwc).setWeatherStationID(this.iWeatherID);
		}
		
		if (this.iWeatherID == null) {
			add(new Text(iwrb.getLocalizedString("no_station_selected", "No weather station selected")));
			return;
		}
		
		WeatherData data = getBusiness().getWeather(this.iWeatherID);
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
			if (this.weatherImageLocation == null) {
				weatherImage = iwb.getImage("/images/"+ data.getWeatherCode() + this.weatherImageType, data.getWeatherDescription());
			}
			else {
				weatherImage = new Image(this.weatherImageLocation+data.getWeatherCode()+this.weatherImageType, data.getWeatherDescription());
			}
			weatherImage.setWidth(this.weatherImageWidth);
			image.add(weatherImage);
			layer.add(image);
		}
		
		Float temp = data.getTemperature();
		Layer temperature = new Layer(Layer.DIV);
		temperature.setStyleClass("temperature");

		if (temp.floatValue() > 0) {
			temperature.setStyleClass("positive");
		}
		else if (temp.floatValue() < 0) {
			temperature.setStyleClass("negative");
		}
		else if (temp.floatValue() == 0) {
			temperature.setStyleClass("zero");
		}
		
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(this.iRoundValues ? 0 : 1);
		format.setMinimumFractionDigits(this.iRoundValues ? 0 : 1);
		
		temperature.add(new Text(format.format(temp.doubleValue()) + "&deg;"));
		temperature.add(new Text(getBusiness().getTemperatureSign()));
		layer.add(temperature);
		
		Float windspeedValue = data.getWindspeed();
		Layer windspeed = new Layer(Layer.DIV);
		windspeed.setStyleClass("windspeed");
		windspeed.add(new Text(format.format(windspeedValue.floatValue())));
		if (this.iShowWindUnit) {
			windspeed.add(new Text(Text.NON_BREAKING_SPACE + getBusiness().getWindSpeedUnit()));
		}
		layer.add(windspeed);
		
		if (data.getWindDirectionTxt() != null) {
			Layer windDirection = new Layer(Layer.DIV);
			windDirection.setStyleClass("windDirection");
			windDirection.add(new Text(data.getWindDirectionTxt()));
			layer.add(windDirection);
			
			Layer windDirectionImage = new Layer(Layer.DIV);
			windDirectionImage.setStyleClass("windDirectionIcon");
			windDirectionImage.add(iwb.getImage("/images/"+ data.getWindDirectionTxt() + this.weatherImageType, data.getWindDirectionTxt()));
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
		
		if (this.iShowWeatherStations) {
			Collection weatherStations = getBusiness().getWeatherStations();
			Iterator iter = weatherStations.iterator();
			Form form = new Form();
			DropdownMenu stationsDM = new DropdownMenu("wstations");
			while (iter.hasNext()) {
				WeatherData wd = (WeatherData) iter.next();
				stationsDM.addMenuElement(wd.getID(), wd.getName());
			}
			stationsDM.setSelectedElement(this.iWeatherID);
			stationsDM.setToSubmit();
			form.add(stationsDM);
			Layer stations = new Layer(Layer.DIV);
			stations.setStyleClass("weatherStations");
			stations.add(form);
			layer.add(stations);
		}
		
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
	
	public void setWeatherImageType(String type) {
		if (type != null && !type.startsWith(".")) {
			type = "."+type;
		}
		this.weatherImageType = type;
	}
	
	public void setWeatherImageLocation(String location) {
		if (location != null && !location.endsWith("/")) {
			location = location+"/";
		}
		this.weatherImageLocation = location;
	}
	
	public void setWeatherImageWidth(String width) {
		this.weatherImageWidth = width;
	}
	
	public void setShowForcast(boolean showForcast) {
	}
	
	public void setWeatherID(String weatherID) {
		this.iWeatherID = weatherID;
	}
	
	public void setShowWeatherStations(boolean showWeatherStations) {
		this.iShowWeatherStations = showWeatherStations;
	}

	
	public void setToRoundValues(boolean roundValues) {
		this.iRoundValues = roundValues;
	}

	
	public void setShowWindUnit(boolean showWindUnit) {
		this.iShowWindUnit = showWindUnit;
	}
	
	public String getBundleIdentifier() {
		return IW_WEATHER_BUNDLE_IDENTIFIER;
	}
}