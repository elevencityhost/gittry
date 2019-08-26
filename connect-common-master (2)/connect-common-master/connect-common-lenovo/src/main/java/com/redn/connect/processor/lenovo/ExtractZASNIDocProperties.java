package com.redn.connect.processor.lenovo;

import java.math.BigDecimal;
import java.util.List;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.zasn.len.ZASNLEN;
import com.redn.connect.zasn.len.ZASNLEN.IDOC.E1EDL20;
import com.redn.connect.zasn.len.ZASNLEN.IDOC.E1EDL20.E1EDT13;
import com.redn.connect.zasn.len.ZASNLEN.IDOC.EDIDC40;

public class ExtractZASNIDocProperties implements Callable{

	
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
		ZASNLEN zASNlen = (ZASNLEN) message.getPayload();
		EDIDC40 edidc40 = zASNlen.getIDOC().getEDIDC40();
		String idocNum = edidc40.getDOCNUM();
		String credat = edidc40.getCREDAT();
		String cretim = edidc40.getCRETIM();
		String shipTime = null;
		
		if(cretim != null && cretim.length() > 4){
			cretim = cretim.substring(0,4);
		}
		
		List<E1EDL20> E1EDL20List = zASNlen.getIDOC().getE1EDL20();
		
		for(E1EDL20 e1edl20 : E1EDL20List){
			List<E1EDT13> e1edt13List = e1edl20.getE1EDT13();
			for(int i = 0 ; i < e1edt13List.size(); i++)
			{
				E1EDT13 e1edt13 = e1edt13List.get(i);
				if("499".equals(e1edt13.getQUALF()))
				{
					shipTime = e1edt13.getTZONEBEG();
				}
				
			}
		}
		
		message.setInvocationProperty(ConnectConstants.CONST_DOCUMENT_NUMBER, idocNum);
		message.setInvocationProperty(ConnectConstants.CONST_CRE_DAT, new BigDecimal(credat));
		message.setInvocationProperty(ConnectConstants.CONST_CRE_TIM, new BigDecimal(cretim));
		message.setInvocationProperty(ConnectConstants.CONST_UNB_0201, senderId);
		message.setInvocationProperty(ConnectConstants.CONST_UNB_0301, recipientId);
		message.setInvocationProperty(ConnectConstants.CONST_UNB_0302, qualifierCode);
		message.setInvocationProperty(ConnectConstants.SHIP_TIME, shipTime);
		return message;
	}

}
