package com.redn.connect.processor.len;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.zorder.len.ZORD05LEN;
import com.redn.connect.zorder.len.ZORD05LEN.IDOC.EDIDC40;

/**
 * 
 * @author
 * 
 * This class is to set control segment fields as flowVars
 */
public class ExtractZORDERIDocProperties implements Callable{

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		MuleMessage message = eventContext.getMessage();
		
		Object idoc = message.getPayload();
		EDIDC40 edidc40;
		if(idoc instanceof ZORD05LEN){
			ZORD05LEN zord05len = (ZORD05LEN)idoc;
			edidc40 = zord05len.getIDOC().getEDIDC40();
		}else if(){
			
		}
		ZORD05LEN zord05len = (ZORD05LEN) message.getPayload();
		EDIDC40 edidc40 = zord05len.getIDOC().getEDIDC40();
		String idocNum = edidc40.getDOCNUM();
		String credat = edidc40.getCREDAT();
		String cretim = edidc40.getCRETIM();
		message.setInvocationProperty("DOCNUM", idocNum);
		message.setInvocationProperty("CREDAT", credat);
		message.setInvocationProperty("CRETIM", cretim);
		return message;
	}

}
