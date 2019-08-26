package com.redn.connect.modifiers;

import java.util.List;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.Contents;
import com.redn.connect.vo.CustomProps;
import com.redn.connect.vo.EnterpriseHeader.Custom;
import com.redn.connect.zostrpt01.len.ZOSTRPT01;
import com.redn.connect.zostrpt01.len.ZOSTRPT01.IDOC.ZE1EDK34;
import com.redn.connect.zostrpt01.len.ZOSTRPT01.IDOC.ZE1EDK34.ZE1ADRM1;
import com.redn.connect.zostrpt01.len.ZOSTRPT01.IDOC.ZE1EDK34.ZE1EDP31;
import com.redn.connect.zostrpt01.len.ZOSTRPT01.IDOC.ZE1EDK34.ZE1EDP31.ZE1EDP19;

public class ExtractZOSTRPT01IDocTypeInterfacesProperties implements Callable{

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

		ZOSTRPT01 zostrpt01 = (ZOSTRPT01) message.getPayload();

		ZE1EDK34 ZE1EDK34Obj = zostrpt01.getIDOC().getZE1EDK34();

		Contents content = new Contents();
		content.setName(ConnectConstants.VAR_DOCNUM);
		content.setValue(zostrpt01.getIDOC().getEDIDC40().getDOCNUM());

		customProps.getContents().add(content);

		if (ZE1EDK34Obj != null) {

			List<ZE1EDP31> ZE1EDP31List = ZE1EDK34Obj.getZE1EDP31();

			for (ZE1EDP31 ZE1EDP31Obj : ZE1EDP31List) {

				ZE1EDP19 ZE1EDP19Obj = ZE1EDP31Obj.getZE1EDP19();

				if(ZE1EDP19Obj != null && ZE1EDP19Obj.getQUALF() != null)
				if (ZE1EDP19Obj.getQUALF().equals("002")) {

					Contents contentRefNum = new Contents();
					contentRefNum.setName(ConnectConstants.REFERENCE_NUMBER);
					contentRefNum.setValue(ZE1EDP19Obj.getSORD());

					customProps.getContents().add(contentRefNum);

					message.setInvocationProperty(ConnectConstants.REFERENCE_NUMBER, ZE1EDP19Obj.getSORD());
				}

			}

			List<ZE1ADRM1> ZE1ADRM1List = ZE1EDK34Obj.getZE1ADRM1();

			if (ZE1ADRM1List != null) {

				for (ZE1ADRM1 ZE1ADRM1Obj : ZE1ADRM1List) {

					String partnerQ = ZE1ADRM1Obj.getPARTNERQ();

					if (partnerQ != null && partnerQ.equals("LF")) {

						Contents contentPartnerIDObj = new Contents();
						contentPartnerIDObj.setName(ConnectConstants.PARTNER_ID);
						contentPartnerIDObj.setValue(ZE1ADRM1Obj.getPARTNERID());

						customProps.getContents().add(contentPartnerIDObj);

						message.setInvocationProperty(ConnectConstants.PARTNER_ID, ZE1ADRM1Obj.getPARTNERID());
					}
				}
			}

		}
	
		custom.setAny(customProps);
		cem.getEnterpriseHeader().setCustom(custom);
		message.setInvocationProperty(ConnectConstants.ENTERPRISE_MESSAGE, cem);
		return message;
	}
}
