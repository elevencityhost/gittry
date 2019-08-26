package com.redn.connect.test;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.ConnectEnterpriseMessage.EnterpriseBody;
import com.redn.connect.vo.EnterpriseHeader;

public class TestPrepareCEMWithOutCustomProps implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		ConnectEnterpriseMessage connectEnterpriseMessage = new ConnectEnterpriseMessage();
		EnterpriseBody enterpriseBody = new EnterpriseBody();

		EnterpriseHeader enterpriseHeader = new EnterpriseHeader();
		enterpriseHeader.setServiceName("o1knbd001");
		enterpriseHeader.setTargetSystem("nbd");
		enterpriseHeader.setComponent("connect-ftps-outbound");

		ConnectUtils connectUtils = new ConnectUtils();
		enterpriseBody.setAny(connectUtils.getNodeForPayload(""));
		connectEnterpriseMessage.setEnterpriseHeader(enterpriseHeader);

		return connectEnterpriseMessage;
	}
}