package com.redn.connect.cem.modifiers;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.Contents;
import com.redn.connect.vo.CustomProps;
import com.redn.connect.vo.EnterpriseHeader.Custom;
import com.redn.connect.zord05.ZORD05RED;
import com.redn.connect.zord05.ZORD05RED.IDOC;
import com.redn.connect.zord05.ZORD05RED.IDOC.E1EDK01;
import com.redn.connect.zord05.ZORD05RED.IDOC.E1EDK01.ZE1EDK01;

public class AddCEMPropsForZORD05HPInterfaces implements Callable {

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
		
		ZORD05RED zord05red = (ZORD05RED) message.getPayload();
		IDOC idoc = zord05red.getIDOC();

		if (idoc != null && idoc.getE1EDK01() != null) {

			E1EDK01 e1edk01 = idoc.getE1EDK01();
			ZE1EDK01 ze1edk01 = e1edk01.getZE1EDK01();

			Contents content = new Contents();
			content.setName(ConnectConstants.VAR_DOCNUM);
			content.setValue(zord05red.getIDOC().getEDIDC40().getDOCNUM());
			customProps.getContents().add(content);

			Contents contentRefNum = new Contents();
			contentRefNum.setName(ConnectConstants.REFERENCE_NUMBER);
			contentRefNum.setValue(ze1edk01.getSORN());
			customProps.getContents().add(contentRefNum);

			message.setInvocationProperty(ConnectConstants.REFERENCE_NUMBER, ze1edk01.getSORN());

		

			custom.setAny(customProps);
			cem.getEnterpriseHeader().setCustom(custom);
			message.setInvocationProperty(ConnectConstants.ENTERPRISE_MESSAGE, cem);
		}

		return message;

	}
}
