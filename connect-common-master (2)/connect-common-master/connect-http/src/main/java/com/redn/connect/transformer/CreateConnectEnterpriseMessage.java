package com.redn.connect.transformer;

import java.util.Date;

import org.apache.log4j.Logger;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.config.i18n.CoreMessages;
import org.mule.config.i18n.Message;
import org.mule.transformer.AbstractMessageTransformer;

import com.redn.connect.constants.HTTPConnectConstants;
import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.EnterpriseHeader;

/**
 * 
 * @author Shruthi Kolloju
 * 
 *         This method is used to construct Connect Enterprise Message
 *
 */
public class CreateConnectEnterpriseMessage extends AbstractMessageTransformer {

	ConnectUtils connectUtils = new ConnectUtils();

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {

		try {

            ConnectEnterpriseMessage eMessage = connectUtils.buildConnectEnterprsieMessage((String)message.getPayload());
            EnterpriseHeader eHeader = eMessage.getEnterpriseHeader();
            
            eHeader.setComponent("connect-http");
			eHeader.setMessageId(message.getProperty(HTTPConnectConstants.MESSAGE_ID, PropertyScope.INVOCATION));
			eHeader.setCreatedUtc(connectUtils.getDateAsXMLGregorianCalendar(new Date()));
			if (null != message.getInvocationProperty(HTTPConnectConstants.SENDER_LOGICAL_KEY)) {
				eHeader.setMessageSource(
						message.getInvocationProperty(HTTPConnectConstants.SENDER_LOGICAL_KEY).toString());
			} else {
				eHeader.setMessageSource("");
			}
			eHeader.setAction(message.getInvocationProperty("isAS2Message").toString());
			if (null != message.getInvocationProperty("communicationType")) {
				eHeader.setCommunication(message.getInvocationProperty("communicationType").toString());
			} else {
				eHeader.setCommunication("");
			}
			eHeader.setPriority("5");
			eHeader.setTargetSystem("");
			eHeader.setPartnerId("");

			if (null != message.getInvocationProperty(HTTPConnectConstants.COMMUNICATION_TYPE)
					&& message.getInvocationProperty(HTTPConnectConstants.COMMUNICATION_TYPE)
							.equals(HTTPConnectConstants.CHECK_SYNC_REQUEST)) {
				eHeader.setServiceName(message.getInvocationProperty(HTTPConnectConstants.SERVICE_NAME));
			} else {
				eHeader.setServiceName("");
			}

			return eMessage;

		} catch (Exception exception) {
			Logger.getRootLogger()
					.error(" The error coming while constructing the Connect EnterpriseMessage  : The Reason is "
							+ exception.getMessage() + "", exception);

			Message errorMessage = CoreMessages.failedToCreate(
					"Connect Enterprise Message, Problem while constructing the Connect Enterprise Message and the error is:"
							+ exception.getMessage());
			throw new TransformerException(errorMessage);
		}
	}

}
