package com.redn.connect.notifications.request;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

public class EmailMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String, String> keyValuePairs;

	private CommonAttributesMessage commonAttributesMessage;

	private String body;

	private String templateID;

	private String replyTo;

	private String subject;

	private List<String> ccList;

	private List<String> toList;

	private List<String> bccList;

	private List<String> attachments;

	public Map<String, String> getKeyValuePairs() {

		if (keyValuePairs == null) {
			keyValuePairs = new HashMap<String, String>();
		}

		return keyValuePairs;
	}

	public void setKeyValuePairs(Map<String, String> keyValuePairs) {
		this.keyValuePairs = keyValuePairs;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTemplateID() {
		return templateID;
	}

	public void setTemplateID(String templateID) {
		this.templateID = templateID;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<String> getCcList() {
		if (ccList == null) {
			ccList = new ArrayList<String>();
		}
		return ccList;
	}

	public void setCcList(List<String> ccList) {
		this.ccList = ccList;
	}

	public List<String> getToList() {
		if (toList == null) {
			toList = new ArrayList<String>();
		}

		return toList;
	}

	public void setToList(List<String> toList) {
		this.toList = toList;
	}

	public List<String> getBccList() {
		if (bccList == null) {
			bccList = new ArrayList<String>();
		}
		return bccList;
	}

	public void setBccList(List<String> bccList) {
		this.bccList = bccList;
	}

	@Override
	public String toString() {

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = "";
		try {
			json = ow.writeValueAsString(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	public List<String> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}

	public CommonAttributesMessage getCommonAttributesMessage() {
		return commonAttributesMessage;
	}

	public void setCommonAttributesMessage(CommonAttributesMessage commonAttributesMessage) {
		this.commonAttributesMessage = commonAttributesMessage;
	}
}