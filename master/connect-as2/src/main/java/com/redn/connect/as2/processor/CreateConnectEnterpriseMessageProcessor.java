package com.redn.connect.as2.processor;

import java.util.Date;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.as2.constants.AS2Constants;
import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.EnterpriseHeader;

/**
 * @author Vinay Kumar Thota
 * 
 * This class is used to build the Connect Enterprise Message
 *
 */
public class CreateConnectEnterpriseMessageProcessor implements Callable {

	ConnectUtils connectUtils = new ConnectUtils();

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		
		MuleMessage message = eventContext.getMessage();
		String payload = (String) message.getPayload();
		
		ConnectEnterpriseMessage cem = connectUtils.buildConnectEnterprsieMessage(payload);
		
		//Set Headers
		EnterpriseHeader header = cem.getEnterpriseHeader();
		header.setMessageId(message.getMessageRootId());
		header.setCreatedUtc(connectUtils.getDateAsXMLGregorianCalendar(new Date()));
		header.setAction(AS2Constants.CONST_ACTION_AS2);
		header.setMessageSource("");
		header.setSourceSystem(message.getInvocationProperty(AS2Constants.VAR_SOURCE_SYSTEM));
		header.setCommunication("");
		header.setPriority("5");
		header.setTargetSystem("");
		header.setPartnerId("");
		header.setComponent("connect-as2-receive");
		
		return cem;
	}

}
