package com.redn.connect.modifiers;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.Contents;
import com.redn.connect.vo.CustomProps;
import com.redn.connect.vo.EnterpriseHeader.Custom;

public class ExtractDOCNUMForInventoryInterfaces implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage message = eventContext.getMessage();

		ConnectEnterpriseMessage cem = (ConnectEnterpriseMessage) message
				.getProperty(ConnectConstants.ENTERPRISE_MESSAGE, PropertyScope.INVOCATION);

		Custom custom = cem.getEnterpriseHeader().getCustom();
		if (custom == null) {
			custom = new Custom();
		}

		CustomProps customProps = new CustomProps();

		Contents content = new Contents();
		content.setName(ConnectConstants.VAR_DOCNUM);

		String docnum = message.getProperty(ConnectConstants.VAR_DOCNUM, PropertyScope.INVOCATION);
		content.setValue(docnum);
		customProps.getContents().add(content);

		custom.setAny(customProps);
		cem.getEnterpriseHeader().setCustom(custom);
		message.setInvocationProperty(ConnectConstants.ENTERPRISE_MESSAGE, cem);
		return message;
	}
}
