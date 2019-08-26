package com.redn.connect.test;

import java.io.File;
import java.util.List;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.ConnectEnterpriseMessage.EnterpriseBody;
import com.redn.connect.vo.Contents;
import com.redn.connect.vo.CustomProps;
import com.redn.connect.vo.EnterpriseHeader;
import com.redn.connect.vo.EnterpriseHeader.Custom;

public class TestPrepareCEMWithMultipleAttachments implements Callable {

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

		Custom customObj = new Custom();
		CustomProps customPropsObj = new CustomProps();
		List<Contents> contentsList = customPropsObj.getContents();

		File file1 = new File(
				getClass().getClassLoader().getResource("ENBD_0000000001235027_2017-08-03-160846.xml").getFile());
		File file2 = new File(
				getClass().getClassLoader().getResource("ENBD_0000000001235027_2017-08-01-002946.xml").getFile());

		Contents contentsObj = new Contents();
		contentsObj.setName("attachmentPath");
		
		contentsObj.setValue(file1.getAbsolutePath() + "," + file2.getAbsolutePath());
		contentsList.add(contentsObj);
		customObj.setAny(customPropsObj);
		enterpriseHeader.setCustom(customObj);
		connectEnterpriseMessage.setEnterpriseHeader(enterpriseHeader);
		
		return connectEnterpriseMessage;
	}
}
