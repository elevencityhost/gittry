package com.redn.testflow;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.EnterpriseHeader;

public class PrepareCEM implements Callable{

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage muleMessage = eventContext.getMessage();
		
		ConnectUtils connectUtils = new ConnectUtils();
		ConnectEnterpriseMessage connectEnterpriseMessage = connectUtils.buildConnectEnterprsieMessage(muleMessage.getPayloadAsString());
		EnterpriseHeader enterpriseHeader = connectEnterpriseMessage.getEnterpriseHeader();
		
		
		String action = muleMessage.getInboundProperty("action");
		String communication =  muleMessage.getInboundProperty("communication");
		String serviceName =  muleMessage.getInboundProperty("serviceName");
		String message_type =  muleMessage.getInboundProperty("message_type");
		String sourceSystem =  muleMessage.getInboundProperty("sourceSystem");
		
		
		enterpriseHeader.setAction(action);
		enterpriseHeader.setCommunication(communication);
		enterpriseHeader.setMessageId(muleMessage.getUniqueId());
		enterpriseHeader.setMessageSource(sourceSystem);
		enterpriseHeader.setServiceName(serviceName);
		enterpriseHeader.setSourceSystem(sourceSystem);
		
		
		muleMessage.setOutboundProperty("message_type", message_type);
		
		muleMessage.setPayload(connectEnterpriseMessage);
		
		return muleMessage;
	}
	
}
