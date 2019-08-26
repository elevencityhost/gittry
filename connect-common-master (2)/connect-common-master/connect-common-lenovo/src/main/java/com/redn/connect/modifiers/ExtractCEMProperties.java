package com.redn.connect.modifiers;

import java.util.List;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.Contents;
import com.redn.connect.vo.CustomProps;

public class ExtractCEMProperties implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage muleMessage = eventContext.getMessage();

		Object obj = muleMessage.getPayload();

		if (obj instanceof ConnectEnterpriseMessage) {
			ConnectEnterpriseMessage cem = (ConnectEnterpriseMessage) obj;

			if (cem.getEnterpriseHeader().getCustom() != null) {
				
				CustomProps customProp  = ConnectUtils.getCustomPropsFromCEM(cem);

				if (!customProp.getContents().isEmpty()) {
					List<Contents> contents = customProp.getContents();

					for (Contents contentObj : contents) {

						if (contentObj.getName().equalsIgnoreCase(ConnectConstants.VAR_IDOC_SOURCE_TYPE)) {

							muleMessage.setProperty(ConnectConstants.VAR_IDOC_SOURCE_TYPE, contentObj.getValue(),
									PropertyScope.INVOCATION);

						} else if (contentObj.getName().equalsIgnoreCase(ConnectConstants.PARTNER_ID)) {

							muleMessage.setProperty(ConnectConstants.PARTNER_ID, contentObj.getValue(),
									PropertyScope.INVOCATION);

						} else if (contentObj.getName().equalsIgnoreCase(ConnectConstants.REFERENCE_NUMBER)) {

							muleMessage.setProperty(ConnectConstants.REFERENCE_NUMBER, contentObj.getValue(),
									PropertyScope.INVOCATION);
						}

					}
				}

			}
		}

		return muleMessage;
	}
}
