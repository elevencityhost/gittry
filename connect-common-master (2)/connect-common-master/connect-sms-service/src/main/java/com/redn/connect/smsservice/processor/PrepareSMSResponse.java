package com.redn.connect.smsservice.processor;


import java.util.ArrayList;
import java.util.List;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.exception.Category;
import com.redn.connect.exception.ConnectException;
import com.redn.connect.notifications.response.SMSMessageResponse;
import com.redn.connect.smsservice.constants.SmsServiceConstants;
import com.redn.connect.smsservice.pojo.DeliveryStatus;


/**
 * This class is used to prepare response received from route mobile API to store in database .
 * @author Jigyasa.Arora
 *
 */
public class PrepareSMSResponse implements Callable{

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		MuleMessage muleMessage = eventContext.getMessage();
		String responseMessage = (String)muleMessage.getPayload();
		List<DeliveryStatus> dlrList= new ArrayList<DeliveryStatus>();
		String messageIds = "";
		String messageId = "";
		String destination = "";
		SMSMessageResponse smsResponse = new SMSMessageResponse();
		String[] responseList = responseMessage.split(",");
		for(int i= 0 ; i < responseList.length; i++){
			DeliveryStatus dlrStatus = new DeliveryStatus();
	        String response = responseList[i];
	        if(response.contains(":")){
	            String[] responseArray = response.split(":");
	            messageId = responseArray[1];
	            String[] destinationArray = responseArray[0].split("\\|");
	            destination = destinationArray[1];
           }else if(response.contains("|")){
	            String[] responseArray = response.split("\\|");
	            messageId = responseArray[2];
	            destination = responseArray[1];
           }else{
        	   checkErrorCode(responseMessage);
           }
	        String newMessageId = messageId.replaceAll("\n", "");
	        messageIds = messageIds.concat(newMessageId);
        	if(responseList.length-1 != i){
        		messageIds= messageIds.concat(",");
	        }
        	dlrStatus.setsMessageId(messageId);
        	dlrStatus.setsMobileNo(destination);
        	dlrStatus.setsStatus(SmsServiceConstants.UNDELIVERED_STATUS);
        	dlrList.add(dlrStatus);
	    }
		muleMessage.setInvocationProperty("dlrList", dlrList);
		smsResponse.setMessageIDs(messageIds);
		smsResponse.setStatus(SmsServiceConstants.SUCCESS_STATUS);
		smsResponse.setResponseCode(SmsServiceConstants.SUCCESS_CODE);
		smsResponse.setResponseDescription(SmsServiceConstants.SUCCESS_CODE_DESC);
		muleMessage.setPayload(smsResponse);
		return muleMessage;
	}
	
	public void checkErrorCode(String errorCode)throws Exception{
		int code = Integer.valueOf(errorCode);
		if(code == SmsServiceConstants.ERROR_CODE_1702){
			throw new ConnectException(SmsServiceConstants.ERROR_CODE_BAD_REQUEST, SmsServiceConstants.ERROR_CODE_1702_DESC,
		    		Category.TECH, SmsServiceConstants.EXCEPTION_ORIGIN_SMS_VENDOR);
		}
		if(code == SmsServiceConstants.ERROR_CODE_1703){
			throw new ConnectException(SmsServiceConstants.ERROR_CODE_BAD_REQUEST, SmsServiceConstants.ERROR_CODE_1703_DESC,
		    		Category.TECH, SmsServiceConstants.EXCEPTION_ORIGIN_SMS_VENDOR);
		}
		if(code == SmsServiceConstants.ERROR_CODE_1704){
			throw new ConnectException(SmsServiceConstants.ERROR_CODE_BAD_REQUEST, SmsServiceConstants.ERROR_CODE_1704_DESC,
		    		Category.TECH, SmsServiceConstants.EXCEPTION_ORIGIN_SMS_VENDOR);
		}
		if(code == SmsServiceConstants.ERROR_CODE_1705){
			throw new ConnectException(SmsServiceConstants.ERROR_CODE_BAD_REQUEST, SmsServiceConstants.ERROR_CODE_1705_DESC,
		    		Category.TECH, SmsServiceConstants.EXCEPTION_ORIGIN_SMS_VENDOR);
		}
	}
}
