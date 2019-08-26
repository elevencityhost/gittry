package com.redn.connect.sapgw.services.constants;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.EnterpriseHeader;
import com.redn.connect.vo.ConnectEnterpriseMessage.EnterpriseBody;

public class PrapareCEM implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		ConnectEnterpriseMessage msg = new ConnectEnterpriseMessage();
		EnterpriseBody value = new EnterpriseBody();
		EnterpriseHeader header = new EnterpriseHeader();
		
		 //Set edifact xml to EnterpriseMessage body    
    	JAXBElement<Object> jaxbElement = new JAXBElement(new QName("payload"), Object.class, eventContext.getMessage().getPayload());
		
		value.setAny(jaxbElement);
		header.setMessageId("123456");
		header.setSourceSystem("Lenovo");
		msg.setEnterpriseBody(value);
		msg.setEnterpriseHeader(header);
		return msg;
	}

}
