package com.redn.connect.processor.connecteh;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import com.redn.connect.connecteh.constants.CEHConstants;
import com.redn.connect.exception.Category;
import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseException;
import com.redn.connect.vo.ConnectEnterpriseException.OriginalMessage;
import com.redn.connect.vo.ConnectEnterpriseMessage;
/**
 * 
 * @author Prathyusha.V
 * 
 *   This clas is used to prepare email body with ConnectEnterpriseException 
 *   in string format and set it as payload.
 */
public class SMTPComponentHandler implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
				
		Object payload = eventContext.getMessage().getPayload();
		
		StringBuilder connectException = new StringBuilder();
		
			ConnectEnterpriseException connectEnterpriseException = (ConnectEnterpriseException) payload;  // type casting the payload to the ConnectEnterprise exception
			
			connectException.append("MessageId -> "+connectEnterpriseException.getMessageId() +"\n" +
			"ServiceName -> "+connectEnterpriseException.getServiceName() +"\n"+
			"Component -> "+connectEnterpriseException.getComponent() +"\n"+
			"CreatedUtc -> "+connectEnterpriseException.getCreatedUtc() +"\n"+
			"ExceptionId -> "+connectEnterpriseException.getExceptionId() +"\n"+
			"ErrorDescription -> "+connectEnterpriseException.getDescription() +"\n"+
			"ErrorCode -> "+connectEnterpriseException.getErrorCode() +"\n"+
			"Category -> "+Category.toCategory(connectEnterpriseException.getCategory()).getDesc() +"\n");
			if( connectEnterpriseException.getOriginalMessage() != null ) {
				String textContent;
				
				OriginalMessage body = connectEnterpriseException.getOriginalMessage();
				Object object = body.getAny();
				if( object instanceof ConnectEnterpriseMessage ) {
					ConnectEnterpriseMessage cem = (ConnectEnterpriseMessage) object;
					ConnectUtils connectUtils = new ConnectUtils();
					textContent = connectUtils.getPayloadTextContentFromCEM(cem);
					connectException.append("\n\n\n" +"OriginalMessage -> "+textContent +"\n");
				}
			}
			
			connectException.append("\n\n\n"+"ConnectEnterpriseException -> \n\n" + eventContext.getMessage().getProperty(CEHConstants.VAR_EXCEPTION_MSG_NATIVE,PropertyScope.INVOCATION));
	
	return connectException;
	}

}
