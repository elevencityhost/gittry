package com.redn.connect.cem.modifiers;

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

public class AddCEMPropsForINVOICInterfaces implements Callable {

	@SuppressWarnings("rawtypes")
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage message = eventContext.getMessage();

		Map payload = (Map) message.getPayload();

		Map messages = (Map) payload.get("Messages");

		Map D97A = (Map) messages.get("D97A");

		List invoiceMessageList = (List) D97A.get("INVOIC");

		ConnectEnterpriseMessage cem = (ConnectEnterpriseMessage) message
				.getProperty(ConnectConstants.ENTERPRISE_MESSAGE, PropertyScope.INVOCATION);

		Custom custom = new Custom();
		CustomProps customProps = new CustomProps();

		Contents contentIDocSourceType = new Contents();
		contentIDocSourceType.setName(ConnectConstants.VAR_IDOC_SOURCE_TYPE);
		contentIDocSourceType.setValue(ConnectConstants.VAR_REDHAT_DISTRIBUTION_VALUE);
		customProps.getContents().add(contentIDocSourceType);

		if (invoiceMessageList != null)
			for (int i = 0; i < invoiceMessageList.size(); i++) {

				Map invoiceMessageMap = (Map) invoiceMessageList.get(i);
				HashMap heading = (HashMap) invoiceMessageMap.get("Heading");
				List group1List = (List) heading.get("0110_Segment_group_1");
				//HashMap detailsMap = (HashMap) invoiceMessageMap.get("Detail");

				if (group1List != null)
					for (int count = 0; count < group1List.size(); count++) {

						HashMap group1Map = (HashMap) group1List.get(count);

						// RFF
						HashMap RFF = (HashMap) group1Map.get("0120_RFF");
						String RFF0101 = (String) RFF.get("RFF0101");
						String RFF0102 = (String) RFF.get("RFF0102");
						if ("ON".equals(RFF0101)) {

							Contents content = new Contents();
							content.setName(ConnectConstants.REFERENCE_NUMBER);
							content.setValue(RFF0102);
							customProps.getContents().add(content);
							message.setInvocationProperty(ConnectConstants.REFERENCE_NUMBER, RFF0102);
							break;
						}
					}

			/*	List group25List = (List) detailsMap.get("1030_Segment_group_25");
				if (group25List != null)
					for (int count = 0; count < group25List.size(); count++) {

						HashMap group25Map = (HashMap) group25List.get(count);
						// LIN
						HashMap LIN = (HashMap) group25Map.get("1040_LIN");

						if (LIN != null && !LIN.isEmpty()) {
							String LIN0302 = (String) LIN.get("LIN0302");
							if ("VP".equals(LIN0302)) {
								String LIN0301 = (String) LIN.get("LIN0301");
								Contents content = new Contents();
								content.setName(ConnectConstants.PARTNER_ID);
								content.setValue(LIN0301);
								customProps.getContents().add(content);
								message.setInvocationProperty(ConnectConstants.PARTNER_ID, LIN0301);
								break;

							}

						}
					}*/				
			}
			
		custom.setAny(customProps);
		cem.getEnterpriseHeader().setCustom(custom);
		message.setInvocationProperty(ConnectConstants.ENTERPRISE_MESSAGE, cem);
				
		return message;
	}
}