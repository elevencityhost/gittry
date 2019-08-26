package com.redn.connect.notifier.processor;

import java.util.concurrent.CopyOnWriteArrayList;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.notifications.response.NotificationMessageResponse;
import com.redn.connect.notifications.response.SMSMessageResponse;

public class FinalResponseAggregator implements Callable {

	@SuppressWarnings("unchecked")
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage muleMessage = eventContext.getMessage();
		CopyOnWriteArrayList<Object> listOfResponses = (CopyOnWriteArrayList<Object>) muleMessage.getPayload();

		System.out.println("Response ========= " + listOfResponses);
		NotificationMessageResponse notificationMessageResponse = null;
		SMSMessageResponse smsMessageResponse = null;

		for (Object object : listOfResponses) {

			if (object instanceof NotificationMessageResponse) {
				notificationMessageResponse = (NotificationMessageResponse) object;
			}
			if (object instanceof SMSMessageResponse) {
				smsMessageResponse = (SMSMessageResponse) object;
				if (notificationMessageResponse == null) {
					notificationMessageResponse = new NotificationMessageResponse();
				}
				notificationMessageResponse.setSmsMessageResponse(smsMessageResponse);
			}
		}

		muleMessage.setPayload(notificationMessageResponse);
		return muleMessage;
	}
}
