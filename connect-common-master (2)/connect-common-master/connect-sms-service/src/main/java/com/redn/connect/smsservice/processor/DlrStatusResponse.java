package com.redn.connect.smsservice.processor;

import java.io.Serializable;
import java.util.List;

import com.redn.connect.smsservice.pojo.DeliveryStatus;

public class DlrStatusResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<DeliveryStatus> dlrStatus;

	public List<DeliveryStatus> getDlrStatus() {
		return dlrStatus;
	}

	public void setDlrStatus(List<DeliveryStatus> dlrStatus) {
		this.dlrStatus = dlrStatus;
	}

}
