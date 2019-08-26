package com.redn.connect.notifications.request;

import java.io.IOException;
import java.io.Serializable;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

public class NotificationMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private CommonAttributesMessage commonAttributesMessage;

	private EmailMessage emailMessage;

	private SMSMessage smsMessage;
	
	public EmailMessage getEmailMessage() {
		return emailMessage;
	}

	public void setEmailMessage(EmailMessage emailMessage) {

		this.emailMessage = emailMessage;
	}

	@Override
	public String toString() {
		
		if(emailMessage == null){
			this.emailMessage = new EmailMessage();
		}
		this.emailMessage.setCommonAttributesMessage(this.commonAttributesMessage);
		
		if(smsMessage == null){
			this.smsMessage = new SMSMessage();
		}
		this.smsMessage.setCommonAttributesMessage(this.commonAttributesMessage);
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = "";
		try {
			json = ow.writeValueAsString(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("NotificationMessage JSON Data ====== " + json);
		return json;
	}

	public CommonAttributesMessage getCommonAttributesMessage() {
		return commonAttributesMessage;
	}

	public void setCommonAttributesMessage(CommonAttributesMessage commonAttributesMessage) {
		this.commonAttributesMessage = commonAttributesMessage;
	}

	public SMSMessage getSmsMessage() {
		return smsMessage;
	}

	public void setSmsMessage(SMSMessage smsMessage) {
		this.smsMessage = smsMessage;
	}
	
	
}
