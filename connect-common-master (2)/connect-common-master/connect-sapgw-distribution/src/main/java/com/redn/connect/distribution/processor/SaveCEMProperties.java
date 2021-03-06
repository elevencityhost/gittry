package com.redn.connect.distribution.processor;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.vo.ConnectEnterpriseMessage;

public class SaveCEMProperties implements Callable{

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		
		
		MuleMessage message = eventContext.getMessage();
		
		if( message.getProperty(ConnectConstants.VAR_ENTERPRISE_MESSAGE, PropertyScope.SESSION) != null)  {
			ConnectEnterpriseMessage cem = message.getProperty(ConnectConstants.VAR_ENTERPRISE_MESSAGE, PropertyScope.INVOCATION);
			cem.getEnterpriseHeader().setComponent("connect-sapgw-distribution");
			message.setProperty(ConnectConstants.VAR_ENTERPRISE_MESSAGE, cem, PropertyScope.INVOCATION);
			message.setProperty(ConnectConstants.VAR_MESSAGE_ID, cem.getEnterpriseHeader().getMessageId(), PropertyScope.INVOCATION);
			message.setProperty(ConnectConstants.VAR_MESSAGE_SOURCE, cem.getEnterpriseHeader().getMessageSource(), PropertyScope.INVOCATION);
			message.setProperty(ConnectConstants.VAR_MESSAGE_ACTION, cem.getEnterpriseHeader().getAction(), PropertyScope.INVOCATION);
			message.setProperty(ConnectConstants.VAR_SERVICE_NAME, cem.getEnterpriseHeader().getServiceName(), PropertyScope.INVOCATION);
			message.setProperty(ConnectConstants.VAR_TARGET_SYSTEM, cem.getEnterpriseHeader().getTargetSystem(), PropertyScope.INVOCATION);
			message.setProperty(ConnectConstants.VAR_SOURCE_SYSTEM, cem.getEnterpriseHeader().getSourceSystem(), PropertyScope.INVOCATION);
			message.setProperty(ConnectConstants.VAR_COMMUNICATION, cem.getEnterpriseHeader().getCommunication(), PropertyScope.INVOCATION);
			message.setProperty(ConnectConstants.VAR_PRIORITY, cem.getEnterpriseHeader().getPriority(), PropertyScope.INVOCATION);
			
		}
		return message;
	}

}
