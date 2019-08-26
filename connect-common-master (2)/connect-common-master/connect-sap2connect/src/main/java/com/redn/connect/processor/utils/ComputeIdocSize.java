package com.redn.connect.processor.utils;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
/**
 * 
 * @author Mahasweta.Das
 *
 *This java class checking file size of message 
 */
public class ComputeIdocSize implements Callable {
	
	private static final long MEGABYTE = 1024L * 1024L;


	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		MuleMessage message = eventContext.getMessage();
		// Creating enterpriseLogger
		String payload = message.getPayloadAsString();
		long size = 0L;
		// computing payload size in bytes
		size = payload.getBytes().length;
		// bytes to megabytes
		

		// IDOC_THRESHOLD
		//int thresholdValue = 1;
		long thresholdValue = MEGABYTE; //Default set to 1MB
		if (message.getOutboundProperty("IDOC_THRESHOLD") != null) {
//			thresholdValue = Integer.parseInt(message.getOutboundProperty("IDOC_THRESHOLD").toString());
			thresholdValue = Long.parseLong(message.getOutboundProperty("IDOC_THRESHOLD").toString());
		}

		if (size >= thresholdValue) {
			message.setOutboundProperty("isIdocHuge", true);
		} else {
			message.setOutboundProperty("isIdocHuge", false);
		}
		return message;
	}

}
