package com.redn.test.connect;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.processor.connectconfig.ConnectConfiguration;

public class InvalidCEM implements Callable {

	ConnectConfiguration connectConfig;
	
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		
		return "";
		}

	

}

