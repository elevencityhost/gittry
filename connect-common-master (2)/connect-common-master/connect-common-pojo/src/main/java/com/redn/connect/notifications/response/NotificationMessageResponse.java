package com.redn.connect.notifications.response;

import java.io.Serializable;

import com.redn.connect.notifications.request.CommonAttributesMessage;

public class NotificationMessageResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private CommonAttributesMessage commonAttributesMessage;
	private EmailMessageResponse emailMessageResponse;
	private SMSMessageResponse smsMessageResponse;

	public EmailMessageResponse getEmailMessageResponse() {
		return emailMessageResponse;
	}

	public void setEmailMessageResponse(EmailMessageResponse emailMessageResponse) {
		this.emailMessageResponse = emailMessageResponse;
	}

	public SMSMessageResponse getSmsMessageResponse() {
		return smsMessageResponse;
	}

	public void setSmsMessageResponse(SMSMessageResponse smsMessageResponse) {
		this.smsMessageResponse = smsMessageResponse;
	}

	public CommonAttributesMessage getCommonAttributesMessage() {
		return commonAttributesMessage;
	}

	public void setCommonAttributesMessage(CommonAttributesMessage commonAttributesMessage) {
		this.commonAttributesMessage = commonAttributesMessage;
	}

}
