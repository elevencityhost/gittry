package com.redn.connect.sapgw.processor;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import com.redn.connect.processor.connectconfig.ConnectConfiguration;

public class Connect2SAPProcessor implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		ConnectConfiguration connectConfiguration = eventContext.getMuleContext().getRegistry().lookupObject("connect2sapCommonConfiguration");
		MuleMessage message = eventContext.getMessage();
		String queueName = message.getProperty("SAPDestinationQueue", PropertyScope.INVOCATION);
		
		if( queueName != null) {
			String destinationQueueName = connectConfiguration.get(queueName);
			
			message.setProperty("ZuulSAPDestinationQueue", destinationQueueName, PropertyScope.INVOCATION);
		}
				
		return message;
	}

}
