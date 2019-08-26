/**
 * 
 * @author Jigyasa.Arora
 * {@link NotificationMessage}
 * {@link SMSMessage}
 *
 * SetSMSServicePayload - This class is responsible to extract the SMSMessage Object from MuleMessage payload.
 * This SMSMessage object extracted will become the actual payload inside CEM there after.
 * 
 * ***/

package com.redn.connect.notifier.processor;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;
import org.w3c.dom.Node;
import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.notifications.request.NotificationMessage;
import com.redn.connect.notifications.request.SMSMessage;
import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.ConnectEnterpriseMessage.EnterpriseBody;

public class SetSMSMessageAsPayload implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		MuleMessage muleMessage = eventContext.getMessage();
		ConnectEnterpriseMessage connectEnterpriseMessage = (ConnectEnterpriseMessage) muleMessage
				.getProperty(ConnectConstants.VAR_ENTERPRISE_MESSAGE, PropertyScope.INVOCATION);

		ConnectEnterpriseMessage connectEnterpriseMessageForSMS = new ConnectEnterpriseMessage();
		connectEnterpriseMessageForSMS.setEnterpriseBody(new EnterpriseBody());
		connectEnterpriseMessageForSMS.setEnterpriseHeader(connectEnterpriseMessage.getEnterpriseHeader());

		connectEnterpriseMessageForSMS.getEnterpriseHeader()
				.setCustom(connectEnterpriseMessage.getEnterpriseHeader().getCustom());

		NotificationMessage notificationMessage = (NotificationMessage) muleMessage.getPayload();
		SMSMessage smsMessage = notificationMessage.getSmsMessage();

		if (smsMessage.getSource() != null && !smsMessage.getSource().isEmpty()) {
			muleMessage.setInvocationProperty("smsMessageVar", smsMessage);
		}
		ConnectUtils connectUtils = new ConnectUtils();
		Node smsMessageAsPayload = connectUtils.getNodeForPayload(smsMessage.toString());
		connectEnterpriseMessageForSMS.getEnterpriseBody().setAny(smsMessageAsPayload);
		String CEMAsXML = connectUtils.jaxbCEMObjectToXML(connectEnterpriseMessageForSMS);
		muleMessage.setPayload(CEMAsXML);
		return muleMessage;
	}

}
