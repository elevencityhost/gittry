package com.redn.connect.connectlogger.config;

import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.components.Configuration;

@Configuration(friendlyName = "Configuration")
public class ConnectorConfig {
	
    /**
     * Logger Name
     */
    @Configurable
	private String loggerName;
	
    
    
      

	/**
	 * Get Logger Name
	 */
	public String getLoggerName() {
		return loggerName;
	}

	/**
	 * Set Logger Name
	 * 
	 * @param loggerName
	 */
	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}

}