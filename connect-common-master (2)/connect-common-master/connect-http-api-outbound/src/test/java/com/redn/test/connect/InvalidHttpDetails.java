package com.redn.test.connect;

import java.util.Date;
import java.util.HashMap;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.processor.connectconfig.ConnectConfiguration;
import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.EnterpriseHeader;
import com.redn.connect.vo.HTTPDetailsVo;

public class InvalidHttpDetails implements Callable {

	ConnectConfiguration connectConfig;
	
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage message = eventContext.getMessage();
		ConnectUtils utils = new ConnectUtils();

		ConnectEnterpriseMessage cem = utils.buildConnectEnterprsieMessage("");
		EnterpriseHeader header = new EnterpriseHeader();

		header.setMessageId(message.getMessageRootId());
		header.setCreatedUtc(utils.getDateAsXMLGregorianCalendar(new Date()));
		header.setAction("Non AS2");
		header.setMessageSource("o1kgsp001");
		header.setSourceSystem("o1kgsp001");
		header.setCommunication("");
		header.setPriority("5");
		header.setTargetSystem("CDWReq");
		header.setPartnerId("");
		header.setServiceName("o1kgsp001");
		header.setComponent("test-cem");
		cem.setEnterpriseHeader(header);

		HTTPDetailsVo httpDetails = new HTTPDetailsVo(); 
		httpDetails.setHost("172.20.102.192");
		httpDetails.setPort("80");
		httpDetails.setBasePath("/eSPMs/rest/exchange");
		httpDetails.setPath("/GetClaims");
		httpDetails.setMethod("GET");
		
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Key","b65687193cb7bb53b6674e0f45300609");
		headerMap.put("Type","CCR");
		 
		httpDetails.setHeaders(headerMap);

		
		return cem;
	}

	

}

