package com.redn.connect.notifier.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.notifications.request.CommonAttributesMessage;
import com.redn.connect.notifications.request.EmailMessage;
import com.redn.connect.notifications.request.NotificationMessage;
import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.EnterpriseHeader;

public class TestPrepareCEMWithNotificationMessageObj implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage muleMessage = eventContext.getMessage();

		ConnectUtils connectUtils = new ConnectUtils();

		NotificationMessage notificationMessage = new NotificationMessage();

		CommonAttributesMessage commonAttributesMessage = new CommonAttributesMessage();
		commonAttributesMessage.setApplicationID("redington");
		commonAttributesMessage.setRequestID("request1234");

		ArrayList<String> toList = new ArrayList<String>();
		toList.add("saiprasad.jonnala@invenio-solutions.com");
		toList.add("shruthi.kolloju@invenio-solutions.com");

		ArrayList<String> bccList = new ArrayList<String>();
		bccList.add("jigyasa.arora@invenio-solutions.com");

		ArrayList<String> ccList = new ArrayList<String>();
		bccList.add("shravani.merugu@invenio-solutions.com");

		EmailMessage emailMessage = new EmailMessage();
		// emailMessage.setApplicationID("redington");
		emailMessage.setBccList(bccList);
		emailMessage.setBody("Test Message Content");
		emailMessage.setCcList(ccList);
		emailMessage.setKeyValuePairs(new HashMap<String, String>());
		emailMessage.setReplyTo("contactsaiprasadj@gmail.com");
		// emailMessage.setRequestID(muleMessage.getUniqueId());
		emailMessage.setSubject("Test Email");
		emailMessage.setTemplateID("index");
		emailMessage.setToList(toList);

		notificationMessage.setCommonAttributesMessage(commonAttributesMessage);
		notificationMessage.setEmailMessage(emailMessage);
		
		ConnectEnterpriseMessage connectEnterpriseMessage = connectUtils
				.buildConnectEnterprsieMessage(notificationMessage.toString());
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