/*
 * $Id: WeatherSessionBean.java,v 1.2 2006/04/09 11:40:05 laddi Exp $
 * Created on Nov 25, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.weather.business;

import com.idega.business.IBOSessionBean;


public class WeatherSessionBean extends IBOSessionBean implements WeatherSession{

	private String stationID = null;

	
	public String getWeatherStationID() {
		return this.stationID;
	}

	public void setWeatherStationID(String stationID) {
		this.stationID = stationID;
	}

}
