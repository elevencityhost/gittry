/**
 * 
 * @author SaiPrasad.Jonnala
 * {@link NotificationMessage}
 * {@link EmailMessage}
 *
 * SetEmailServicePayload - This class is responsible to extract the EmailMessage Object from MuleMessage payload.
 * This EmailMessage object extracted will become the actual payload inside CEM there after.
 * 
 * ***/

package com.redn.connect.notifier.processor;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;
import org.w3c.dom.Node;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.constants.NotifierConstants;
import com.redn.connect.notifications.request.EmailMessage;
import com.redn.connect.notifications.request.NotificationMessage;
import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.ConnectEnterpriseMessage.EnterpriseBody;

public class SetEmailMessageAsPayload implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		// TODO Auto-generated method stub

		MuleMessage muleMessage = eventContext.getMessage();
		ConnectEnterpriseMessage connectEnterpriseMessage = (ConnectEnterpriseMessage) muleMessage
				.getProperty(ConnectConstants.VAR_ENTERPRISE_MESSAGE, PropertyScope.INVOCATION);

		ConnectEnterpriseMessage connectEnterpriseMessageForEmail = new ConnectEnterpriseMessage();
		connectEnterpriseMessageForEmail.setEnterpriseBody(new EnterpriseBody());
		connectEnterpriseMessageForEmail.setEnterpriseHeader(connectEnterpriseMessage.getEnterpriseHeader());

		NotificationMessage notificationMessage = (NotificationMessage) muleMessage.getPayload();
		EmailMessage emailMessage = notificationMessage.getEmailMessage();

		if (emailMessage.getSubject() != null && !emailMessage.getSubject().isEmpty()) {
			muleMessage.setInvocationProperty("emailMessageVar", emailMessage);
		}
		ConnectUtils connectUtils = new ConnectUtils();
		Node emailMessageAsPayload = connectUtils.getNodeForPayload(emailMessage.toString());
		connectEnterpriseMessageForEmail.getEnterpriseBody().setAny(emailMessageAsPayload);
		muleMessage.setPayload(connectEnterpriseMessageForEmail);
		return muleMessage;
	}
}
