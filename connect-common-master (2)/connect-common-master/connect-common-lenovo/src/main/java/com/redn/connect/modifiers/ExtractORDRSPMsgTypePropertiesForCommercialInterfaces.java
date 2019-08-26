package com.redn.connect.modifiers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.Contents;
import com.redn.connect.vo.CustomProps;
import com.redn.connect.vo.EnterpriseHeader.Custom;

public class ExtractORDRSPMsgTypePropertiesForCommercialInterfaces implements Callable {

	@SuppressWarnings("rawtypes")
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage muleMessage = eventContext.getMessage();
		Map payload = (Map) muleMessage.getPayload();

		Map messages = (Map) payload.get("Messages");

		Map D97A = (Map) messages.get("D97A");

		List ordrspMessageList = (List) D97A.get("ORDRSP");

		ConnectEnterpriseMessage cem = (ConnectEnterpriseMessage) muleMessage
				.getProperty(ConnectConstants.ENTERPRISE_MESSAGE, PropertyScope.INVOCATION);

		Custom custom = new Custom();
		CustomProps cusprop = new CustomProps();

		Contents contentIDocSourceType = new Contents();
		contentIDocSourceType.setName(ConnectConstants.VAR_IDOC_SOURCE_TYPE);
		contentIDocSourceType.setValue(ConnectConstants.VAR_LENOVO_SERVICES_VALUE);
		cusprop.getContents().add(contentIDocSourceType);

		for (int i = 0; i < ordrspMessageList.size(); i++) {

			Map response = (Map) ordrspMessageList.get(i);
			HashMap heading = (HashMap) response.get("Heading");
			List Group1 = (List) heading.get("0080_Segment_group_1");

			for (int count = 0; count < Group1.size(); count++) {

				HashMap group1Map = (HashMap) Group1.get(count);
				HashMap group1RFFMap = (HashMap) group1Map.get("0090_RFF");

				if (group1RFFMap.get("RFF0101") != null) {

					if (((String) group1RFFMap.get("RFF0101")).equalsIgnoreCase("ZPD")) {

						String partnerID = (String) group1RFFMap.get("RFF0102");

						Contents content = new Contents();
						content.setName(ConnectConstants.PARTNER_ID);
						content.setValue(partnerID);
						cusprop.getContents().add(content);
						muleMessage.setInvocationProperty(ConnectConstants.PARTNER_ID, partnerID);
					}

					if (((String) group1RFFMap.get("RFF0101")).equalsIgnoreCase("CO")) {

						String referenceNumber = (String) group1RFFMap.get("RFF0102");
						Contents content = new Contents();
						content.setName(ConnectConstants.REFERENCE_NUMBER);
						content.setValue(referenceNumber);
						cusprop.getContents().add(content);
						muleMessage.setInvocationProperty(ConnectConstants.REFERENCE_NUMBER, referenceNumber);
					}
				}
			}
		}

		custom.setAny(cusprop);
		cem.getEnterpriseHeader().setCustom(custom);

		muleMessage.setInvocationProperty(ConnectConstants.ENTERPRISE_MESSAGE, cem);

		return muleMessage;
	}
}
