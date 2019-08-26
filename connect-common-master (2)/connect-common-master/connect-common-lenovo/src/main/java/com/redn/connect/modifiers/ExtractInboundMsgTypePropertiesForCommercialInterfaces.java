package com.redn.connect.modifiers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mule.api.MuleMessage;
import org.mule.api.transport.PropertyScope;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.Contents;
import com.redn.connect.vo.CustomProps;
import com.redn.connect.vo.EnterpriseHeader.Custom;

public class ExtractInboundMsgTypePropertiesForCommercialInterfaces {

	private void setIDOCSourceTypeProps(CustomProps customProps) {
		Contents contentIDocSourceType = new Contents();
		contentIDocSourceType.setName(ConnectConstants.VAR_IDOC_SOURCE_TYPE);
		contentIDocSourceType.setValue(ConnectConstants.VAR_LENOVO_SERVICES_VALUE);
		customProps.getContents().add(contentIDocSourceType);
	}

	@SuppressWarnings("rawtypes")
	private Map extractMessage(MuleMessage muleMessage) {

		Map payload = (Map) muleMessage.getPayload();

		Map messages = (Map) payload.get("Messages");

		Map D97A = (Map) messages.get("D97A");

		return D97A;
	}

	@SuppressWarnings("rawtypes")
	private void addCustomPropsToCEM(MuleMessage muleMessage, List messagesList) {
		Custom custom = new Custom();
		CustomProps customProps = new CustomProps();

		ConnectEnterpriseMessage cem = (ConnectEnterpriseMessage) muleMessage
				.getProperty(ConnectConstants.ENTERPRISE_MESSAGE, PropertyScope.INVOCATION);
		setIDOCSourceTypeProps(customProps);

		if (messagesList != null) {
			extractRefNumAndSONum(muleMessage, messagesList, customProps);
		}

		custom.setAny(customProps);
		cem.getEnterpriseHeader().setCustom(custom);

		muleMessage.setInvocationProperty(ConnectConstants.ENTERPRISE_MESSAGE, cem);
	}

	@SuppressWarnings("rawtypes")
	private void extractRefNumAndSONum(MuleMessage muleMessage, List messagesList, CustomProps customPropsObj) {

		for (int i = 0; i < messagesList.size(); i++) {

			Map response = (Map) messagesList.get(i);
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
						customPropsObj.getContents().add(content);
						muleMessage.setInvocationProperty(ConnectConstants.PARTNER_ID, partnerID);
					}

					if (((String) group1RFFMap.get("RFF0101")).equalsIgnoreCase("CO")) {

						String referenceNumber = (String) group1RFFMap.get("RFF0102");
						Contents content = new Contents();
						content.setName(ConnectConstants.REFERENCE_NUMBER);
						content.setValue(referenceNumber);
						customPropsObj.getContents().add(content);
						muleMessage.setInvocationProperty(ConnectConstants.REFERENCE_NUMBER, referenceNumber);
					}
				}
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void setORDERSMessageProperties(Object message) {
		
		MuleMessage muleMessage  = (MuleMessage)message;
		Map D97A = extractMessage(muleMessage);
		List ordersMessageList = (List) D97A.get("ORDERS");
		addCustomPropsToCEM(muleMessage, ordersMessageList);
	}

	@SuppressWarnings("rawtypes")
	public void setORDRSPMessageProperties(MuleMessage muleMessage) {

		Map D97A = extractMessage(muleMessage);
		List ordrspMessageList = (List) D97A.get("ORDRSP");
		addCustomPropsToCEM(muleMessage, ordrspMessageList);
	}
	
	@SuppressWarnings("rawtypes")
	public void setORDCHGMessageProperties(MuleMessage muleMessage) {

		Map D97A = extractMessage(muleMessage);
		List ordchgMessageList = (List) D97A.get("ORDCHG");
		addCustomPropsToCEM(muleMessage, ordchgMessageList);
	}

	
}
