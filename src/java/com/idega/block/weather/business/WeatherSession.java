/*
 * $Id: WeatherSession.java,v 1.1 2005/11/25 12:09:11 gimmi Exp $
 * Created on Nov 25, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.weather.business;

import com.idega.business.IBOSession;


/**
 * <p>
 * TODO gimmi Describe Type WeatherSession
 * </p>
 *  Last modified: $Date: 2005/11/25 12:09:11 $ by $Author: gimmi $
 * 
 * @author <a href="mailto:gimmi@idega.com">gimmi</a>
 * @version $Revision: 1.1 $
 */
public interface WeatherSession extends IBOSession {

	/**
	 * @see com.idega.block.weather.business.WeatherSessionBean#getWeatherStationID
	 */
	public String getWeatherStationID() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.weather.business.WeatherSessionBean#setWeatherStationID
	 */
	public void setWeatherStationID(String stationID) throws java.rmi.RemoteException;
}
