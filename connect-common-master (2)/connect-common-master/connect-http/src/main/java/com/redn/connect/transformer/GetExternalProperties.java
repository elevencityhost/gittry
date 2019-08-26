package com.redn.connect.transformer;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.constants.HTTPConnectConstants;
import com.redn.connect.processor.connectconfig.ConnectConfiguration;

/**
 * 
 * @author Shruthi Kolloju
 * 
 * This class is to get external properties.
 *
 */
public class GetExternalProperties implements Callable{

	public Object onCall(MuleEventContext eventContext) throws Exception {

		ConnectConfiguration connectConfig = eventContext.getMuleContext()
				.getRegistry().lookupObject("connectConfigBean");
		
		String senderPath=eventContext.getMessage().getInvocationProperty(HTTPConnectConstants.SENDER_LOGICAL_KEY);
		String setSenderCommCheckKey = "connect."+senderPath+".comm";
		String commValue=connectConfig.get(setSenderCommCheckKey);
		if(null!=commValue)
		{
			if(!(commValue.equalsIgnoreCase("Sync")||commValue.equalsIgnoreCase("Async")))
			{
				throw new IllegalArgumentException("Invalid value for Communication");
			}
		}
		else
		{
			throw new IllegalArgumentException("No value for Communication is present ");
		}
		
		if(commValue.equalsIgnoreCase(HTTPConnectConstants.CHECK_SYNC_REQUEST)){
			
			String sourceOfSynchronousRequest = eventContext.getMessage().getInvocationProperty(HTTPConnectConstants.SOURCE);
			String serviceName = connectConfig.get("connect."+senderPath+"."+sourceOfSynchronousRequest);
			eventContext.getMessage().setInvocationProperty(HTTPConnectConstants.SERVICE_NAME, serviceName);
			
		}
		eventContext.getMessage().setInvocationProperty("isAS2Message", "Non AS2");
		eventContext.getMessage().setInvocationProperty("communicationType", commValue);
		
		return eventContext.getMessage().getPayload();
	}

}
