package com.redn.connect.notifications.response;

import java.io.Serializable;

public class SMSMessageResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String responseCode;
	private String responseDescription;
	private String status;
	private String messageIDs;

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessageIDs() {
		return messageIDs;
	}

	public void setMessageIDs(String messageIDs) {
		this.messageIDs = messageIDs;
	}
}
