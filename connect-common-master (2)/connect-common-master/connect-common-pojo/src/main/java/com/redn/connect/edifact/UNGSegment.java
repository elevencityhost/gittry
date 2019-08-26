package com.redn.connect.edifact;

public class UNGSegment {

	private String messageGroupId;
	
	private String senderId;
	
	private String senderCodeQualifier;
	
	private String recipientId;
	
	private String recipientCodeQualifier;
	
	private String date;
	
	private String time;
	
	private String groupReferenceNumber;
	
	private String controllingAgency;
	
	private String messageVersion;
	
	private String messageReleaseNumber;
	
	private String associationAssignedCode;

	public String getMessageGroupId() {
		return messageGroupId;
	}

	public void setMessageGroupId(String messageGroupId) {
		this.messageGroupId = messageGroupId;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSenderCodeQualifier() {
		return senderCodeQualifier;
	}

	public void setSenderCodeQualifier(String senderCodeQualifier) {
		this.senderCodeQualifier = senderCodeQualifier;
	}

	public String getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	public String getRecipientCodeQualifier() {
		return recipientCodeQualifier;
	}

	public void setRecipientCodeQualifier(String recipientCodeQualifier) {
		this.recipientCodeQualifier = recipientCodeQualifier;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getGroupReferenceNumber() {
		return groupReferenceNumber;
	}

	public void setGroupReferenceNumber(String groupReferenceNumber) {
		this.groupReferenceNumber = groupReferenceNumber;
	}

	public String getControllingAgency() {
		return controllingAgency;
	}

	public void setControllingAgency(String controllingAgency) {
		this.controllingAgency = controllingAgency;
	}

	public String getMessageVersion() {
		return messageVersion;
	}

	public void setMessageVersion(String messageVersion) {
		this.messageVersion = messageVersion;
	}

	public String getMessageReleaseNumber() {
		return messageReleaseNumber;
	}

	public void setMessageReleaseNumber(String messageReleaseNumber) {
		this.messageReleaseNumber = messageReleaseNumber;
	}
	
	public String getAssociationAssignedCode() { 
		return associationAssignedCode; 
	}
	  
	public void setAssociationAssignedCode(String associationAssignedCode)
	  {
	    this.associationAssignedCode = associationAssignedCode;
	  }
	
	
}
