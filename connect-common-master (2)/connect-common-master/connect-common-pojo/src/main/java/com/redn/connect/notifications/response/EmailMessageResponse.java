package com.redn.connect.notifications.response;

import java.io.Serializable;

public class EmailMessageResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String responseCode;
	private String responseDescription;
	private String status;

	public EmailMessageResponse() {

	}

	public EmailMessageResponse(String responseCode, String responseDescription, String status) {
		this.responseCode = responseCode;
		this.responseDescription = responseDescription;
		this.status = status;
	}

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
}
