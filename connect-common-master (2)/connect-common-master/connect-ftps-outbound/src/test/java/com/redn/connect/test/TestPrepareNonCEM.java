package com.redn.connect.test;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.vo.ConnectEnterpriseMessage.EnterpriseBody;

public class TestPrepareNonCEM implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		
		//returning non CEM object
		
		return new EnterpriseBody();
	}
}
