package com.redn.connect.util;

import org.mule.api.MuleContext;

import com.redn.connect.connectlogger.ConnectLogger;

public class LoggerUtil {
	
static MuleContext muleContext;
	
	public static ConnectLogger getDatabaseConenction(){
		
		ConnectLogger logger = new ConnectLogger(null, getMuleContext() );
		return logger;
	}

	public static MuleContext getMuleContext() {
		return muleContext;
	}

}
