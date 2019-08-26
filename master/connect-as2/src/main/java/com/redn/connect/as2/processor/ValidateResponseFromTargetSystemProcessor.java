package com.redn.connect.as2.processor;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.as2.constants.AS2Constants;
import com.redn.connect.exception.Category;
import com.redn.connect.exception.ConnectException;

/**
 * @author Vinay Kumar Thota
 * 
 * This class is used to validate the response from target system.
 * If there is an error in processing the AS2 message on target system,
 * an exception is thrown.
 *
 */
public class ValidateResponseFromTargetSystemProcessor implements Callable {
	
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		
		MuleMessage message = eventContext.getMessage();
		
		String response = (String) message.getPayload();
		
		String targetSystem = message.getInvocationProperty(AS2Constants.VAR_TARGET_SYSTEM);
		
		String originalMessageId = getOriginalMessageID(response);
		String dispositionValue = getDispositionValue(response);
		
		message.setInvocationProperty(AS2Constants.VAR_ORGNL_MESSAGE_ID, originalMessageId);
		message.setInvocationProperty(AS2Constants.VAR_DISPOSITION, dispositionValue);
		
		//Check for any errors
		if(dispositionValue != null && dispositionValue.toLowerCase().contains("error")){
			throw new ConnectException(AS2Constants.ERROR_CODE_FAILURE_MDN, 
					"Failure MDN received from Target System: " + targetSystem + ". MDN Response is: " + dispositionValue, 
					Category.DATA, AS2Constants.CONST_EXCEPTION_ORIGIN_PARTNER_SYSTEM);
		}
		
		return message;
	}
	
	
	private static String getOriginalMessageID(String response){
		//Get the original message id 
		String originalMessageId = null;
		int indexOfOriginalMsgId = response.indexOf(AS2Constants.CONST_ORGNL_MSGID);
		if(indexOfOriginalMsgId != -1){
			int indexOfNewLine = response.indexOf("\n", indexOfOriginalMsgId);
			if(indexOfNewLine != -1){
				originalMessageId = response.substring(indexOfOriginalMsgId+AS2Constants.CONST_ORGNL_MSGID.length(), indexOfNewLine);
			}
		}
		
		return originalMessageId;
	}
	
	private static String getDispositionValue(String response){
		//Get the disposition value
		String dispositionValue = null;
		int indexOfDisposition = response.indexOf(AS2Constants.CONST_DISPOSITION);
		if(indexOfDisposition != -1){
			int indexOfNewLine = response.indexOf("\n", indexOfDisposition);
			if(indexOfNewLine != -1){
				dispositionValue = response.substring(indexOfDisposition+AS2Constants.CONST_DISPOSITION.length(), indexOfNewLine);
			}
		}
		
		return dispositionValue;
	}
	
	public static void main(String args[]){
		
		String value1 = "Final-Recipient: rfc822; 662424795TEST\nOriginal-Message-ID: 91e40a00-0280-11e7-ba97-02a4180001a5\n"
				+ "Disposition: automatic-action/MDN-sent-automatically; processed\nReceived-Content-MIC: 42DgF1DfiUpcswCqRriZ8jLQt7w=, sha1";
		
		String originalMessageID =  ValidateResponseFromTargetSystemProcessor.getOriginalMessageID(value1);
		
		String disposition =  ValidateResponseFromTargetSystemProcessor.getDispositionValue(value1);
		
		System.out.println("origMsgId:" + originalMessageID);
		System.out.println("disp:" + disposition);
	}

}