package com.redn.connect.smsservice.processor;


import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.exception.Category;
import com.redn.connect.exception.ConnectException;
import com.redn.connect.notifications.request.SMSMessage;
import com.redn.connect.processor.connectconfig.ConnectConfiguration;
import com.redn.connect.smsservice.constants.SmsServiceConstants;


/**
 * This class is used to prepare sms data 
 * @author Jigyasa.Arora
 *
 */
public class PrepareSMSData implements Callable{
	

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		ConnectConfiguration connectConfiguration = eventContext.getMuleContext().getRegistry()
				.lookupObject("connectConfigBean");
		MuleMessage muleMessage = eventContext.getMessage();
		SMSMessage smsMessage = (SMSMessage)muleMessage.getPayload();
		String message = smsMessage.getMessages();
		String destination = smsMessage.getDestinations();
		String applicationId = smsMessage.getCommonAttributesMessage().getApplicationID();
		String requestId = smsMessage.getCommonAttributesMessage().getRequestID();
		checkmandatoryValidations(message, destination, applicationId,requestId);
		muleMessage.setInvocationProperty(SmsServiceConstants.APPLICATION_ID, applicationId);
		muleMessage.setInvocationProperty(SmsServiceConstants.REQUEST_ID, requestId);
		String source = smsMessage.getSource();
		validateMessageAndSourceLength(message,source);
		String path = "";
		String replacedMessage = "";
		boolean isMultipleDestination = checkMultipleMessages(smsMessage);
		  if(isMultipleDestination){
			   boolean isMessageAndDestinationSame = validateMessageWithDestination(smsMessage);
			   if(isMessageAndDestinationSame){
				   path = connectConfiguration.get(SmsServiceConstants.BULK_MESSAGES_PATH);
		           String[] messageArray = message.split(SmsServiceConstants.MESSAGE_SPLIT_SYMBOL_WITH_ESCAPE);
		           for (int i = 0; i < messageArray.length; i++) {
		        	   replacedMessage = replacedMessage.concat(convertToUnicode(messageArray[i]));
		            	if(messageArray.length-1 != i){
		            		replacedMessage = replacedMessage.concat(SmsServiceConstants.SET_BULK_MESSAGE_SPLIT_SYMBOL);
		    	        }
		           }
			   }else{
				   throw new ConnectException(SmsServiceConstants.ERROR_CODE_INVALID_MESSAGE_DESTINATION, SmsServiceConstants.ERROR_DESC_INVALID_MESSAGE_DESTINATION, 
						   Category.DATA, SmsServiceConstants.EXCEPTION_ORIGIN_REDINGTON_GULF);
			   }
		  }else{
			   path =connectConfiguration.get(SmsServiceConstants.SINGLE_MESSAGE_PATH);
			   replacedMessage = convertToUnicode(message);
		  }
		  
		  setProperties(replacedMessage,destination,path,source,muleMessage,connectConfiguration);
		return muleMessage;
	}
	
	public boolean validateMessageWithDestination(SMSMessage smsMessage){
		  boolean isMessageAndDestinationSameorNot = false;
		  String[] messageList = smsMessage.getMessages().split(SmsServiceConstants.MESSAGE_SPLIT_SYMBOL_WITH_ESCAPE);
		  String[] numberList  = smsMessage.getDestinations().split(SmsServiceConstants.DESTINATION_SPLIT_SYMBOL);
		  if(messageList.length == numberList.length){
			  isMessageAndDestinationSameorNot = true;
		  }
		  return isMessageAndDestinationSameorNot;
		 }
		 
	 public boolean checkMultipleMessages(SMSMessage smsMessage){
		  boolean isMultipleDestination = false;
		  String message  = smsMessage.getMessages();
		  boolean check = message.contains(SmsServiceConstants.MESSAGE_SPLIT_SYMBOL);
		  if(check){
			  isMultipleDestination= check;
		  }
		  return isMultipleDestination;
		 }
	
	public void setProperties(String message, String destination,String path,String source ,MuleMessage muleMessage,ConnectConfiguration connectConfiguration) throws Exception{
		  muleMessage.setInvocationProperty(SmsServiceConstants.SOURCE, source);
		  muleMessage.setInvocationProperty(SmsServiceConstants.TYPE,connectConfiguration.get(SmsServiceConstants.REQUIRED_TYPE));
		  muleMessage.setInvocationProperty(SmsServiceConstants.DLR,connectConfiguration.get(SmsServiceConstants.REQUIRED_DELIVERY));
		  muleMessage.setInvocationProperty(SmsServiceConstants.DESTINATION,destination);
		  muleMessage.setInvocationProperty(SmsServiceConstants.MESSAGE_CONTENT,message);
		  muleMessage.setInvocationProperty(SmsServiceConstants.PATH, path);
	}
	
	private String convertToUnicode(String regText) { 
		char[] chars = regText.toCharArray();
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < chars.length; i++) { 
			String iniHexString = Integer.toHexString((int) chars[i]);
			if (iniHexString.length() == 1) {
				iniHexString = "000" + iniHexString;
			} else if (iniHexString.length() == 2) { 
				iniHexString = "00" + iniHexString; 
			} else if (iniHexString.length() == 3) {
				iniHexString = "0" + iniHexString; 
			} 
			hexString.append(iniHexString); 
		} 
		System.out.println(hexString);
		return hexString.toString(); 
	}
	
	/**
	 * This message is used to check message length is greater than 160 character 
	 * @param message
	 * @return
	 */
	 public boolean checkMessageLength(String message){
		 boolean isValidMessage = true;
		 if(message.length() > SmsServiceConstants.MESSAGE_LENGTH){
			 isValidMessage = false;
		  }
		  return isValidMessage;
	 }
		
	 /**
	  * This method is used to check source length should be 18 if numeric and 11 if alphanumeric
	  * @param source
	  * @return
	  */
	 public boolean isValidSource(String source){
		 boolean isValidMessage = true;
		 boolean isNumeric = isNumeric(source);
		 if(isNumeric){
			 if(source.length() > SmsServiceConstants.NUMERIC_SOURCE_LENGTH){
				 isValidMessage = false;
		     }
		 }else{
	    	 if(source.length() > SmsServiceConstants.ALPHANUMERIC_SOURCE_LENGTH){
		    		 isValidMessage = false;
	    	 }
	     }
		 return isValidMessage;
	}
		 
		 
	 public boolean isNumeric(String str){
		 for (char c : str.toCharArray()){
	          if (!Character.isDigit(c)) {
	        	  return false;
	          }
	      }
		 return true;
	  }
		  
	  
	
	 /**
	  * This message is used to check mandatory json fields
	  * @param message
	  * @param destination
	  * @param applicationId
	  * @param requestId
	  * @throws Exception
	  */
	 public void checkmandatoryValidations(String message,String destination, String applicationId,String requestId)throws Exception{
		  if(isEmptyString(message)){
			  throw new ConnectException(SmsServiceConstants.ERROR_CODE_MISSING_MESSAGE,SmsServiceConstants.ERROR_DESC_MISSING_MESSAGE,
		        Category.DATA, SmsServiceConstants.EXCEPTION_ORIGIN_REDINGTON_GULF);
		  }
		  if(isEmptyString(destination)){
			  throw new ConnectException(SmsServiceConstants.ERROR_CODE_MISSING_DESTINATION, SmsServiceConstants.ERROR_DESC_MISSING_DESTINATION,
					  Category.DATA, SmsServiceConstants.EXCEPTION_ORIGIN_REDINGTON_GULF);
		  }
		  if(isEmptyString(applicationId)){
		   throw new ConnectException(SmsServiceConstants.ERROR_CODE_APPLICATION_ID_UNAVAILABLE, SmsServiceConstants.ERROR_DESC_APPLICATION_ID_UNAVAILABLE, 
		        Category.DATA, SmsServiceConstants.EXCEPTION_ORIGIN_REDINGTON_GULF);
		  }
		  if(isEmptyString(requestId)){
		   throw new ConnectException(SmsServiceConstants.ERROR_CODE_REQUEST_ID_UNAVAILABLE, SmsServiceConstants.ERROR_DESC_REQUEST_ID_UNAVAILABLE, 
		        Category.DATA, SmsServiceConstants.EXCEPTION_ORIGIN_REDINGTON_GULF);
		  }
	 }
	 
	 public boolean isEmptyString(final String str) {
		  boolean isValid = true;
		  if (str != null && str.length() > 0) {
		   isValid = false;
		  }
		  return isValid;
	  }
	
	 /**
	  * This message is used to throw exception if message length is greater than 160 character and 
	  * source length should be 18 if numeric and 11 if alphanumeric  
	  * @param message
	  * @param source
	  * @throws Exception
	  */
	 public void validateMessageAndSourceLength(String message,String source)throws Exception{
		 String[] messageList = message.split(SmsServiceConstants.MESSAGE_SPLIT_SYMBOL_WITH_ESCAPE);
		 for(int j = 0; j <messageList.length; j++){
			 if(!checkMessageLength( messageList[j])){
				  throw new ConnectException(SmsServiceConstants.ERROR_CODE_MESSAGE_LENGTH,SmsServiceConstants.ERROR_DESC_MESSAGE_LENGTH,
						  Category.DATA, SmsServiceConstants.EXCEPTION_ORIGIN_REDINGTON_GULF);
			  }
		 }
		 if(!isValidSource(source)){
			  throw new ConnectException(SmsServiceConstants.ERROR_CODE_SOURCE_LENGTH, SmsServiceConstants.ERROR_DESC_SOURCE_LENGTH,
					  Category.DATA, SmsServiceConstants.EXCEPTION_ORIGIN_REDINGTON_GULF);
		 }
	 }

}
