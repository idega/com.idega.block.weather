/*
 * $Id$
 * Created on Nov 4, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.weather;

import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWBundleStartable;
import com.idega.idegaweb.include.GlobalIncludeManager;


public class IWBundleStarter implements IWBundleStartable {

	public void start(IWBundle starterBundle) {
		GlobalIncludeManager.getInstance().addBundleStyleSheet("com.idega.block.weather", "/style/weather.css");
	}

	public void stop(IWBundle starterBundle) {
	}
}
