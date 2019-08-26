package com.redn.connect.distribution.processor;

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
		String gmString = muleMessage.getProperty("gmInstanceFromMsg", PropertyScope.INVOCATION);
		//System.out.println(" POR - PRN from receiver :::::::::::" + gmString);
		ConnectConfiguration connectConfig = eventContext.getMuleContext().getRegistry().lookupObject("sap2connectDistributionConfigBean");
		String sapGmName = connectConfig.get("connect.sapgw.distribution.sapinstance."+gmString);
		//System.out.println(" GM name is :::::::::::" + sapGmName);
		muleMessage.setProperty("sapGmInstanceName", sapGmName, PropertyScope.INVOCATION);
		return muleMessage;
	}

}
