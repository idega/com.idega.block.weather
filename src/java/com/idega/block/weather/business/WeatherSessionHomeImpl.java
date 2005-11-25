/*
 * $Id: WeatherSessionHomeImpl.java,v 1.1 2005/11/25 12:09:11 gimmi Exp $
 * Created on Nov 25, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.weather.business;

import com.idega.business.IBOHomeImpl;


/**
 * <p>
 * TODO gimmi Describe Type WeatherSessionHomeImpl
 * </p>
 *  Last modified: $Date: 2005/11/25 12:09:11 $ by $Author: gimmi $
 * 
 * @author <a href="mailto:gimmi@idega.com">gimmi</a>
 * @version $Revision: 1.1 $
 */
public class WeatherSessionHomeImpl extends IBOHomeImpl implements WeatherSessionHome {

	protected Class getBeanInterfaceClass() {
		return WeatherSession.class;
	}

	public WeatherSession create() throws javax.ejb.CreateException {
		return (WeatherSession) super.createIBO();
	}
}
