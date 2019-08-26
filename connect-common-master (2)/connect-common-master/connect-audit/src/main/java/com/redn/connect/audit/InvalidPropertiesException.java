package com.redn.connect.audit;
/**
 * * *@author  Laxshmi Maram
 
 * 
 **/
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class InvalidPropertiesException extends AbstractMessageTransformer{

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding)
			throws TransformerException {
    	throw new RuntimeException("Invalid Property Name(s)" +message.getInvocationProperty("invalidProperties"));
	}

}
