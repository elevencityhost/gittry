package com.redn.connect.smsservice.pojo;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;

public class DeliveryStatus implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String dtDone;
	private String applicationId;
	private String dtSubmit;
	private String sSender;
	@JsonIgnore
	private double iCharge;
	private String requestId;
	private String sStatus;
	private String sMessageId;
	private String sMobileNo;
	@JsonIgnore
	private int iMCCMNC;
	@JsonIgnore
	private double iCostPerSms;
	public String getDtDone() {
		return dtDone;
	}
	public void setDtDone(String dtDone) {
		this.dtDone = dtDone;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getDtSubmit() {
		return dtSubmit;
	}
	public void setDtSubmit(String dtSubmit) {
		this.dtSubmit = dtSubmit;
	}
	public String getsSender() {
		return sSender;
	}
	public void setsSender(String sSender) {
		this.sSender = sSender;
	}
	public double getiCharge() {
		return iCharge;
	}
	public void setiCharge(double iCharge) {
		this.iCharge = iCharge;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getsStatus() {
		return sStatus;
	}
	public void setsStatus(String sStatus) {
		this.sStatus = sStatus;
	}
	public String getsMessageId() {
		return sMessageId;
	}
	public void setsMessageId(String sMessageId) {
		this.sMessageId = sMessageId;
	}
	public String getsMobileNo() {
		return sMobileNo;
	}
	public void setsMobileNo(String sMobileNo) {
		this.sMobileNo = sMobileNo;
	}
	public int getiMCCMNC() {
		return iMCCMNC;
	}
	public void setiMCCMNC(int iMCCMNC) {
		this.iMCCMNC = iMCCMNC;
	}
	public double getiCostPerSms() {
		return iCostPerSms;
	}
	public void setiCostPerSms(double iCostPerSms) {
		this.iCostPerSms = iCostPerSms;
	}
	
}
