package com.redn.connect.modifiers;

import java.util.ArrayList;
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

public class ExtractRefNumFromInboundInterfaces implements Callable {

	@SuppressWarnings("rawtypes")
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage message = eventContext.getMessage();
		ArrayList<String> messageInfoList = new ArrayList<String>();

		Map payload = (Map) message.getPayload();

		Map messages = (Map) payload.get("Messages");

		Map D97A = (Map) messages.get("D97A");

		// TODO - Check for Errors and throw an exception

		ArrayList<String> messageInfoMapRetrieved = prepareMessageInfo(messageInfoList, message);

		if (messageInfoMapRetrieved.size() >= 4) {

			List orderResponse = (List) D97A.get(messageInfoMapRetrieved.get(0));

			ConnectEnterpriseMessage cem = (ConnectEnterpriseMessage) message
					.getProperty(ConnectConstants.ENTERPRISE_MESSAGE, PropertyScope.INVOCATION);
			

		    Custom custom=new Custom();
			CustomProps cusprop= new CustomProps();
			
			Contents contentIDocSourceType=new Contents();
			contentIDocSourceType.setName(ConnectConstants.VAR_IDOC_SOURCE_TYPE);
			contentIDocSourceType.setValue(ConnectConstants.VAR_LENOVO_SERVICES_VALUE);
			cusprop.getContents().add(contentIDocSourceType);
			

			for (int i = 0; i < orderResponse.size(); i++) {

				Map response = (Map) orderResponse.get(i);
				HashMap heading = (HashMap) response.get("Heading");
				List Group2 = (List) heading.get(messageInfoMapRetrieved.get(1));

				for (int count = 0; count < Group2.size(); count++) {

					HashMap group3 = (HashMap) Group2.get(count);

					// NAD
					HashMap NAD = (HashMap) group3.get(messageInfoMapRetrieved.get(2));

					if (NAD != null && !NAD.isEmpty()) {

						String NAD01 = (String) NAD.get("NAD01");
						if ("SE".equals(NAD01)) {
							String NAD0201 = (String) NAD.get("NAD0201");
							Contents content=new Contents();
							content.setName(ConnectConstants.PARTNER_ID);
							content.setValue(NAD0201);
							
							cusprop.getContents().add(content);
							
							message.setInvocationProperty(ConnectConstants.PARTNER_ID, NAD0201);
							break;
						}
					}
				}

				List Group1 = (List) heading.get(messageInfoMapRetrieved.get(3));
				if (Group1 != null) {
					for (int iGrp1 = 0; iGrp1 < Group1.size(); iGrp1++) {

						HashMap group1 = (HashMap) Group1.get(iGrp1);

						HashMap RFF = (HashMap) group1.get(messageInfoMapRetrieved.get(4));
						String RFF0101 = (String) RFF.get("RFF0101");

						if ("CO".equals(RFF0101)) {
							String RFF0102 = (String) RFF.get("RFF0102");
							
							Contents content=new Contents();
							content.setName(ConnectConstants.REFERENCE_NUMBER);
							content.setValue(RFF0102);
							
							cusprop.getContents().add(content);
							
							message.setInvocationProperty(ConnectConstants.REFERENCE_NUMBER,RFF0102);
							break;
						}
					}

				}

			}
			
			
			custom.setAny(cusprop);
			cem.getEnterpriseHeader().setCustom(custom);
			
			message.setInvocationProperty(ConnectConstants.ENTERPRISE_MESSAGE,cem);

		}
		return message;
	}

	public ArrayList<String> prepareMessageInfo(ArrayList<String> messageInfoList, MuleMessage muleMessage) {

		String serviceName = muleMessage.getProperty(ConnectConstants.VAR_SERVICE_NAME, PropertyScope.INVOCATION);

		if (serviceName != null) {

			if (serviceName.equalsIgnoreCase("i3klen001")) {

				messageInfoList.add("ORDERS");
				messageInfoList.add("0110_Segment_group_2");
				messageInfoList.add("0120_NAD");

			} else if (serviceName.equalsIgnoreCase("i3klen004")) {

				messageInfoList.add("ORDRSP");
				messageInfoList.add("0140_Segment_group_3");
				messageInfoList.add("0150_NAD");

			} else if (serviceName.equalsIgnoreCase("i3klen005")) {

				messageInfoList.add("ORDCHG");
				messageInfoList.add("0140_Segment_group_3");
				messageInfoList.add("0150_NAD");

			}

			messageInfoList.add("0080_Segment_group_1");
			messageInfoList.add("0090_RFF");

		}

		return messageInfoList;
	}
}