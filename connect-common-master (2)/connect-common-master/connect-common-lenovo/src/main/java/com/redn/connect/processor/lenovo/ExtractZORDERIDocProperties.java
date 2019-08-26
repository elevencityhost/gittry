package com.redn.connect.processor.lenovo;

import java.math.BigDecimal;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.zorder.len.ZORD05LEN;
import com.redn.connect.zorder.len.ZORD05LEN.IDOC.EDIDC40;

public class ExtractZORDERIDocProperties implements Callable{

	private String senderId ;
	
	private String recipientId;
	
	private String qualifierCode;
		
	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	public String getQualifierCode() {
		return qualifierCode;
	}

	public void setQualifierCode(String qualifierCode) {
		this.qualifierCode = qualifierCode;
	}

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		MuleMessage message = eventContext.getMessage();
		ZORD05LEN zord05len = (ZORD05LEN) message.getPayload();
		EDIDC40 edidc40 = zord05len.getIDOC().getEDIDC40();
		String idocNum = edidc40.getDOCNUM();
		String credat = edidc40.getCREDAT();
		String cretim = edidc40.getCRETIM();
		//IDoc sends time in hhmmss format, so take only hhmm as UNG v4 supports only 4 digits in time
		if(cretim != null && cretim.length() > 4){
			cretim = cretim.substring(0,4);
		}
		message.setInvocationProperty(ConnectConstants.CONST_DOCUMENT_NUMBER, idocNum);
		message.setInvocationProperty(ConnectConstants.CONST_CRE_DAT, new BigDecimal(credat));
		message.setInvocationProperty(ConnectConstants.CONST_CRE_TIM, new BigDecimal(cretim));
		message.setInvocationProperty(ConnectConstants.CONST_UNB_0201, senderId);
		message.setInvocationProperty(ConnectConstants.CONST_UNB_0301, recipientId);
		message.setInvocationProperty(ConnectConstants.CONST_UNB_0302, qualifierCode);
		return message;
	}

}
