package com.redn.connect.processor;

import java.util.Date;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.ftp.constants.FTPConstants;
import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;

/**
 * @author Shruthi.Kolloju
 *
 *This class is to create CEM with header properties
 */

public class CreateHeaderProperties implements Callable{

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage message = eventContext.getMessage();
		ConnectUtils connectUtils = new ConnectUtils();
		ConnectEnterpriseMessage cem = connectUtils.buildConnectEnterprsieMessage("");
		
		cem.getEnterpriseHeader().setMessageId(message.getMessageRootId());
		cem.getEnterpriseHeader().setCreatedUtc(connectUtils.getDateAsXMLGregorianCalendar(new Date()));
		cem.getEnterpriseHeader().setAction(FTPConstants.NON_AS2);
		cem.getEnterpriseHeader().setMessageSource("");
		cem.getEnterpriseHeader().setSourceSystem(message.getInvocationProperty(FTPConstants.SOURCE));
		cem.getEnterpriseHeader().setCommunication("");
		cem.getEnterpriseHeader().setPriority("5");
		cem.getEnterpriseHeader().setTargetSystem("");
		cem.getEnterpriseHeader().setPartnerId("");
		cem.getEnterpriseHeader().setComponent(FTPConstants.COMPONENT_NAME);
		cem.getEnterpriseHeader().setServiceName(message.getInvocationProperty(FTPConstants.SOURCE));
		
		
		
		message.setInvocationProperty(FTPConstants.CONNECT_ENTERPRISE_MESSAGE, cem);
		message.setInvocationProperty(ConnectConstants.ENTERPRISE_MESSAGE, cem);
		
		message.setPayload(cem); 
		 

		
		return message;
	}

}
