package com.redn.connect.as2.test.util;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import com.redn.connect.as2.constants.AS2Constants;
import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;

public class BuildCEM implements Callable{

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		
		MuleMessage message = eventContext.getMessage();
		
		ConnectUtils utils1 = new ConnectUtils();
		ConnectEnterpriseMessage cem = utils1.buildConnectEnterprsieMessage(message.getPayloadAsString());
		cem.getEnterpriseHeader().setTargetSystem("lenovo");
		
		message.setProperty(AS2Constants.VAR_SERVICE_NAME, "testService", PropertyScope.INBOUND);
		
		return cem;
	}

}
