package com.redn.connect.processor;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

public class ReplaceUNOCwithUNOY implements Callable{

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		MuleMessage message = eventContext.getMessage();
		String payloadString = message.getPayloadAsString();
		String replacedString = "";
		replacedString = payloadString.replace("UNOC", "UNOY");
		return replacedString;
	}

}
