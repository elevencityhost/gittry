package com.redn.connect.ftp.util;

/**
 * @author Laxshmi Maram
 *  * 
 *  This class will read the following fields from CEM and set them to flow Vars
 *  1. File Name
 *  2. FTPName
 *  3.Interface Name
 *   **/
import java.util.List;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.Contents;
import com.redn.connect.vo.CustomProps;
import com.redn.connect.vo.EnterpriseHeader.Custom;

public class CEMProcessor implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage message = eventContext.getMessage();
		ConnectEnterpriseMessage cem = (ConnectEnterpriseMessage) message.getPayload();
		cem.getEnterpriseHeader().setComponent("connect-file-outbound");
		String filename = cem.getEnterpriseHeader().getMessageSource();
		Custom custom = cem.getEnterpriseHeader().getCustom();
		CustomProps cusprop = (CustomProps) custom.getAny();
		List<Contents> contents = cusprop.getContents();
		String interfacename = null;
		String ftpname = null;
		for (int i = 0; i < contents.size(); i++) {
			Contents content = contents.get(i);
			if (content.getName().equalsIgnoreCase("interface")) {
				interfacename = content.getValue();
			}
			if (content.getName().equalsIgnoreCase("protocol")) {
				ftpname = content.getValue();
			}

			if (content.getName().equalsIgnoreCase(ConnectConstants.VAR_ATTACHMENT_PATH)) {
				filename = content.getValue();
			}

			// Notification Messaage Handling Logic
			if (content.getName().equalsIgnoreCase(ConnectConstants.VAR_FTP_EMAIL_SEND)) {
				eventContext.getMessage().setInvocationProperty(ConnectConstants.VAR_FTP_EMAIL_SEND,
						content.getValue());
				eventContext.getMessage().setInvocationProperty("notificationMessagePayload",
						cem.getEnterpriseBody().getAny() != null ? new ConnectUtils().getPayloadTextContentFromCEM(cem)
								: "");
			}
		}

		eventContext.getMessage().setInvocationProperty("filename", filename);
		eventContext.getMessage().setInvocationProperty("interfacename", interfacename);
		eventContext.getMessage().setInvocationProperty("ftpname", ftpname);

		return message;
	}
}
