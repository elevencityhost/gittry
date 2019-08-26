package com.redn.connect.notifications.request;

import java.io.IOException;
import java.io.Serializable;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

public class SMSMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CommonAttributesMessage commonAttributesMessage;
	
	private String source;
	
	private String destinations;
	
	private String messages;

	public CommonAttributesMessage getCommonAttributesMessage() {
		return commonAttributesMessage;
	}

	public void setCommonAttributesMessage(CommonAttributesMessage commonAttributesMessage) {
		this.commonAttributesMessage = commonAttributesMessage;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestinations() {
		return destinations;
	}

	public void setDestinations(String destinations) {
		this.destinations = destinations;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
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

	

}
