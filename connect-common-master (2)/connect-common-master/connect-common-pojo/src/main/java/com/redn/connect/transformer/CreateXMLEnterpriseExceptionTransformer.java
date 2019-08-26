package com.redn.connect.transformer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.mule.api.ExceptionPayload;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.transformer.AbstractMessageTransformer;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.exception.Category;
import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseException;
import com.redn.connect.vo.ConnectEnterpriseException.OriginalMessage;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.EnterpriseHeader;


public class CreateXMLEnterpriseExceptionTransformer extends AbstractMessageTransformer {
	ConnectUtils connectUtils = new ConnectUtils();

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding)
			throws TransformerException {
		
		//Create CEE instance
		ConnectEnterpriseException enterpriseException = new ConnectEnterpriseException();
		ExceptionPayload exceptionPayload = message.getExceptionPayload();
		Throwable exception = null;
		if(exceptionPayload != null){
			exception = exceptionPayload.getException();
		}
		
		//Set Exception ID
		enterpriseException.setExceptionId(java.util.UUID.randomUUID().toString());
				
		//Set created date
		enterpriseException.setCreatedUtc(connectUtils.getDateAsXMLGregorianCalendar(new Date()));
		
		//set retryable 
		Boolean retryable = message.getProperty(ConnectConstants.VAR_RETRYABLE, PropertyScope.INVOCATION);
		if( retryable != null)
			enterpriseException.setRetryable(retryable);
		else{
			enterpriseException.setRetryable(false);
		}
		
		//Set Message ID
		Object messageId = message.getProperty(ConnectConstants.VAR_MESSAGE_ID, PropertyScope.INVOCATION);
		if (messageId != null) {
			enterpriseException.setMessageId(messageId.toString());
		} else {
			enterpriseException.setMessageId(java.util.UUID.randomUUID().toString());
		}
		
		//Set Error Code
		Object errorCode =  message.getProperty(ConnectConstants.VAR_ERROR_CODE,
				PropertyScope.INVOCATION);
		if (errorCode != null) {			
				enterpriseException.setErrorCode(errorCode.toString());
		} else {
			if(exceptionPayload != null){
				enterpriseException.setErrorCode(String.valueOf(exceptionPayload
						.getCode()));
			}
		}
		
		//Set Error Description
		Object errorDesc =  message.getProperty(ConnectConstants.VAR_ERROR_DESCRIPTION,
				PropertyScope.INVOCATION);
		if (errorDesc != null) {			
				enterpriseException.setDescription(errorDesc.toString());
		} else {
			if(exception != null){
				enterpriseException.setDescription(exception.getMessage());
			}
		}

		//Set Category
		Object errorCategory =  message.getProperty(ConnectConstants.VAR_ERROR_CATEGORY,
				PropertyScope.INVOCATION);
		if (errorCategory != null) {			
				enterpriseException.setCategory(errorCategory.toString());
		} else {
			enterpriseException.setCategory(Category.UNKNOWN.name());
		}
		
		//Set Origin
		Object errorOrigin =  message.getProperty(ConnectConstants.VAR_ERROR_ORIGIN,
				PropertyScope.INVOCATION);
		if (errorOrigin != null){			
			enterpriseException.setExceptionOrigin(errorOrigin.toString());
		} else {
			enterpriseException.setExceptionOrigin(ConnectConstants.CONST_UNKNOWN);
		}
		
		
		//Set Hostname
		try {
			enterpriseException.setInstanceId(InetAddress.getLocalHost()
					.getHostName());
		} catch (UnknownHostException e) {
			enterpriseException.setInstanceId(ConnectConstants.CONST_LOCALHOST);
		}
		
		
		//Set Service Name and original message
		ConnectEnterpriseMessage enterpriseMessage =  
				message.getProperty(ConnectConstants.VAR_ENTERPRISE_MESSAGE,
				PropertyScope.INVOCATION);
		
		if (enterpriseMessage != null) {
			
			OriginalMessage originalMessage = new OriginalMessage();
			originalMessage.setAny(enterpriseMessage);
			enterpriseException.setOriginalMessage(originalMessage);
			
			EnterpriseHeader enterpriseHeader = enterpriseMessage.getEnterpriseHeader();
			
			
			if (enterpriseHeader != null && enterpriseHeader.getServiceName() != null) {
				enterpriseException.setServiceName((enterpriseMessage
						.getEnterpriseHeader().getServiceName()));
			}
			
			//set component name v1.1
			if( enterpriseHeader != null && enterpriseHeader.getComponent() != null) {
				enterpriseException.setComponent(enterpriseMessage.getEnterpriseHeader().getComponent());
			}
		}
	
		else {
			enterpriseException.setServiceName(message.getInvocationProperty("serviceName"));
			//message.getInvocationProperty("SourceSystem")
		}
		return enterpriseException;
	}
}
