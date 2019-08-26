package com.redn.connect.ftp.util;

import java.util.ArrayList;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.Contents;
import com.redn.connect.vo.CustomProps;
import com.redn.connect.vo.EnterpriseHeader.Custom;

public class NotificationMessageProcessor implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		// TODO Auto-generated method stub

		MuleMessage muleMessage = eventContext.getMessage();

		// processedFileNames

		String processedFileNames = muleMessage.getProperty("processedFileNames", PropertyScope.INVOCATION);

		ConnectEnterpriseMessage connectEnterpriseMessage = (ConnectEnterpriseMessage) muleMessage
				.getInvocationProperty(ConnectConstants.VAR_ENTERPRISE_MESSAGE);

		if (connectEnterpriseMessage.getEnterpriseBody().getAny() != null) {

			if (connectEnterpriseMessage.getEnterpriseBody().getAny() instanceof Custom) {

				Custom customObj = connectEnterpriseMessage.getEnterpriseHeader().getCustom();

				if ((customObj != null && customObj.getAny() != null) && (customObj.getAny() instanceof CustomProps)) {

					CustomProps customProps = (CustomProps) customObj.getAny();
					ArrayList<Contents> arrayListObj = (ArrayList<Contents>) customProps.getContents();

					for (Contents contentsObj : arrayListObj) {

						if (contentsObj.getName().equals(ConnectConstants.VAR_ATTACHMENT_PATH)
								&& (processedFileNames != null && !processedFileNames.isEmpty())) {

							contentsObj.setValue(processedFileNames);

						}

					}

				}
			}

		}
		return connectEnterpriseMessage;
	}

}
