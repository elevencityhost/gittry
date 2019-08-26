package com.redn.connect.util;

import org.mule.api.MuleContext;

import com.redn.connect.connectlogger.ConnectLogger;
import com.redn.connect.connectlogger.ConnectionFactory;
/**
 * 
 * @author Shruthi.Kolloju
 * This class creates the logger object
 */
public class LoggerUtil {
	
	 static MuleContext muleContext;
	
	public static ConnectLogger getDatabaseConenction(){
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		//ConnectorConfig connectorConfig = new ConnectorConfig();
		ConnectLogger logger = new ConnectLogger(null, getMuleContext() );
		return logger;
	}

	public static MuleContext getMuleContext() {
		return muleContext;
	}

	

}
