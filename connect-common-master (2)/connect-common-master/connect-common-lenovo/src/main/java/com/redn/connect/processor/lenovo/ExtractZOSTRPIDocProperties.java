package com.redn.connect.processor.lenovo;

import java.math.BigDecimal;
import java.util.List;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.zostrpt01.len.ZOSTRPT01;
import com.redn.connect.zostrpt01.len.ZOSTRPT01.IDOC.EDIDC40;
import com.redn.connect.zostrpt01.len.ZOSTRPT01.IDOC.ZE1EDK34;

public class ExtractZOSTRPIDocProperties implements Callable{

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
		ZOSTRPT01 zostrpt01  = (ZOSTRPT01)message.getPayload();
		EDIDC40 eDIDC40  =zostrpt01.getIDOC().getEDIDC40();
		String idocNum = eDIDC40.getDOCNUM();
		String credat = eDIDC40.getCREDAT();
		String cretim = eDIDC40.getCRETIM();
		String timeZone = null;
		if(cretim != null && cretim.length() > 4) {
			cretim = cretim.substring(0,4);
		}
		ZE1EDK34 ze1edk34 = (ZE1EDK34) zostrpt01.getIDOC().getZE1EDK34();
		List<ZOSTRPT01 .IDOC.ZE1EDK34 .ZE1EDKFT01> ze1edkft01list = ze1edk34.getZE1EDKFT01();
		
		for(ZOSTRPT01 .IDOC.ZE1EDK34 .ZE1EDKFT01 ze1edkft01 : ze1edkft01list){
			
			if("036".equals(ze1edkft01.getQUAL()))
			{
				timeZone = ze1edkft01.getVAL1();
			}
			
		}
		
		message.setInvocationProperty(ConnectConstants.CONST_DOCUMENT_NUMBER, idocNum);
		message.setInvocationProperty(ConnectConstants.CONST_CRE_DAT, new BigDecimal(credat));
		message.setInvocationProperty(ConnectConstants.CONST_CRE_TIM, new BigDecimal(cretim));
		message.setInvocationProperty(ConnectConstants.CONST_UNB_0201, senderId);
		message.setInvocationProperty(ConnectConstants.CONST_UNB_0301, recipientId);
		message.setInvocationProperty(ConnectConstants.CONST_UNB_0302, qualifierCode);
		message.setInvocationProperty(ConnectConstants.TIME_ZONE, timeZone);
		return message;
	}

}
