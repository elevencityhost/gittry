package com.redn.connect.smsservice.processor;

import java.util.ArrayList;
import java.util.List;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.util.CaseInsensitiveHashMap;

import com.redn.connect.smsservice.constants.SmsServiceConstants;
import com.redn.connect.smsservice.pojo.DeliveryStatus;

/**
 * This class is used to prepare dlr response object from the payload data received from sms_dlr_notification table 
 * @author Jigyasa.Arora
 *
 */
public class PrepareDlrResponseObject implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		MuleMessage muleMessage = eventContext.getMessage();
		List<DeliveryStatus> dlrStatusList = new ArrayList<DeliveryStatus>();
		List<Object> databaseList = (List<Object>) muleMessage.getPayload();
		String dateDone = SmsServiceConstants.DATE_DONE;
		String dateSubmit = SmsServiceConstants.DATE_SUBMIT;
		String applicationId = SmsServiceConstants.APP_ID;
		String sender = SmsServiceConstants.SENDER;
		String requestId = SmsServiceConstants.REQ_ID;
		String status = SmsServiceConstants.STATUS;
		String messageId = SmsServiceConstants.MESSAGE_ID;
		String mobileNo = SmsServiceConstants.MOBILE_NO;
		for (int i = 0; i < databaseList.size(); i++) {
			CaseInsensitiveHashMap obj =  (CaseInsensitiveHashMap) databaseList.get(i);
			DeliveryStatus dlrStatus = new DeliveryStatus();
			dlrStatus.setDtDone(obj.get(dateDone)!= null?obj.get(dateDone).toString():"");
	        dlrStatus.setDtSubmit(obj.get(dateSubmit)!= null?obj.get(dateSubmit).toString():"");
			dlrStatus.setApplicationId((String) obj.get(applicationId));
			dlrStatus.setsSender((String) obj.get(sender));
			dlrStatus.setRequestId((String) obj.get(requestId));
			dlrStatus.setsStatus((String) obj.get(status));
			dlrStatus.setsMessageId((String) obj.get(messageId));
			dlrStatus.setsMobileNo((String)obj.get(mobileNo));
			dlrStatusList.add(dlrStatus);
		}
		DlrStatusResponse dlrStatusResponse = new DlrStatusResponse();
		dlrStatusResponse.setDlrStatus(dlrStatusList);
		return dlrStatusResponse;
	}

}
