package com.redn.connect.processor.utils;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import com.redn.connect.processor.connectconfig.ConnectConfiguration;

public class GmInstanceIdentifier implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		// TODO Auto-generated method stub
		MuleMessage muleMessage = eventContext.getMessage();
		String gmString = muleMessage.getProperty("gmInstanceName", PropertyScope.INVOCATION);
		ConnectConfiguration connectConfig = eventContext.getMuleContext().getRegistry().lookupObject("sap2connectCommonConfigBean");
		String sapGmName = connectConfig.get("connect.sap2connect.sap.instance."+gmString);
		muleMessage.setProperty("sapGmInstanceName", sapGmName, PropertyScope.INVOCATION);
		return muleMessage;
	}

}
