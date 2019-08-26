package com.redn.connect.as2.processor;

import org.mule.api.MuleContext;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.context.MuleContextAware;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.as2.constants.AS2Constants;
import com.redn.connect.exception.Category;
import com.redn.connect.exception.ConnectException;
import com.redn.connect.processor.connectconfig.ConnectConfiguration;

/**
 * @author Vinay Kumar Thota
 * 
 * This class is used to derive the source system based on
 * AS2-From in the request. For AS2-From value, source system is
 * derived from the configuration file
 * If no source system can be derived an exception is thrown.
 *
 */
public class DeriveSourceSystemProcessor implements Callable, MuleContextAware {

	ConnectConfiguration connectConfig;
	
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		
		MuleMessage message = eventContext.getMessage();
		
		String as2From = message.getInboundProperty(AS2Constants.VAR_AS2_FROM);
		
		if(as2From == null){
			throw new ConnectException(AS2Constants.ERROR_CODE_AS2_FROM_NOT_PRESENT, "AS2-From is null", Category.DATA, AS2Constants.CONST_EXCEPTION_ORIGIN_PARTNER_SYSTEM);
		}
		
		
        String sourceSystemKey =  AS2Constants.PROP_SOURCE_SYSTEM_PREFIX + as2From;

        String sourceSystem = connectConfig.get(sourceSystemKey);
        
        if(sourceSystem == null){
        	throw new ConnectException(AS2Constants.ERROR_CODE_SOURCE_SYSTEM_DERIVATION, "Can not derivce Source System using AS2-From: " + as2From, Category.DATA, AS2Constants.CONST_EXCEPTION_ORIGIN_PARTNER_SYSTEM);
        }
        
        message.setInvocationProperty(AS2Constants.VAR_SOURCE_SYSTEM, sourceSystem );
        
        String messageTypeKey =  AS2Constants.PROP_MESSAGE_TYPE_PREFIX + as2From;

        String messageType = connectConfig.get(messageTypeKey);
        
        if(messageType == null){
        	throw new ConnectException(AS2Constants.ERROR_CODE_MESSAGE_TYPE_DERIVATION, "Can not derivce message type for AS2-From: " + as2From, Category.DATA, AS2Constants.CONST_EXCEPTION_ORIGIN_CONNECT_AS2);
        }
        
        message.setOutboundProperty(AS2Constants.VAR_MESSAGE_TYPE, messageType );
        
		return message;
	}

	@Override
	public void setMuleContext(MuleContext context) {
		connectConfig = 
				context.getRegistry().lookupObject(AS2Constants.BEAN_CONNECT_CONFIG);
	}

}