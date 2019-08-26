package com.redn.connect.test.emailservice;

import java.util.Date;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.Contents;
import com.redn.connect.vo.CustomProps;
import com.redn.connect.vo.EnterpriseHeader;
import com.redn.connect.vo.EnterpriseHeader.Custom;

public class TestPrepareCEMWithCustomProps implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage muleMessage = eventContext.getMessage();

		String emailMessageContent = muleMessage.getPayload().toString();
		ConnectUtils connectUtils = new ConnectUtils();
		ConnectEnterpriseMessage connectEnterpriseMessage = connectUtils
				.buildConnectEnterprsieMessage(emailMessageContent);
		EnterpriseHeader enterpriseHeader = connectEnterpriseMessage.getEnterpriseHeader();
		enterpriseHeader.setMessageId(muleMessage.getUniqueId());
		enterpriseHeader.setAction("Non AS2");
		enterpriseHeader.setCommunication("Sync");
		enterpriseHeader.setServiceName("NotificationService");
		enterpriseHeader.setComponent("connect-internal-http");
		enterpriseHeader.setMessageSource("RedingtonGulf");
		enterpriseHeader.setCreatedUtc(connectUtils.getDateAsXMLGregorianCalendar(new Date()));
		enterpriseHeader.setPriority("5");

		Custom custom = new Custom();

		CustomProps customProps = new CustomProps();

		Contents contentsAttachment1 = new Contents();
		contentsAttachment1.setName(ConnectConstants.VAR_ATTACHMENT_PATH);
		contentsAttachment1.setValue("E:\\empmaster.xls");

		Contents contentsAttachment2 = new Contents();
		contentsAttachment2.setName(ConnectConstants.VAR_ATTACHMENT_PATH);
		contentsAttachment2.setValue("E:\\sample.txt");

		customProps.getContents().add(contentsAttachment1);
		customProps.getContents().add(contentsAttachment2);

		custom.setAny(customProps);
		connectEnterpriseMessage.getEnterpriseHeader().setCustom(custom);

		return connectEnterpriseMessage;
	}
}
