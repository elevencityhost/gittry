package com.redn.connect.smsservice.processor;

import java.util.Date;

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
		String smsContent = muleMessage.getPayload().toString();
		ConnectUtils connectUtils = new ConnectUtils();
		ConnectEnterpriseMessage connectEnterpriseMessage = connectUtils
				.buildConnectEnterprsieMessage(smsContent);
		EnterpriseHeader enterpriseHeader = connectEnterpriseMessage.getEnterpriseHeader();
		enterpriseHeader.setMessageId(muleMessage.getUniqueId());
		enterpriseHeader.setAction("Non AS2");
		enterpriseHeader.setCommunication("Sync");
		enterpriseHeader.setServiceName("NotificationService");
		enterpriseHeader.setComponent("connect-internal-http");
		enterpriseHeader.setMessageSource("RedingtonGulf");
		enterpriseHeader.setCreatedUtc(connectUtils.getDateAsXMLGregorianCalendar(new Date()));
		enterpriseHeader.setPriority("5");
		
		return connectEnterpriseMessage;
		
	}

}
