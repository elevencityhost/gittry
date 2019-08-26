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
import com.redn.connect.zorder.len.ZORD05LEN;
import com.redn.connect.zorder.len.ZORD05LEN.IDOC.E1EDK02;
import com.redn.connect.zorder.len.ZORD05LEN.IDOC.E1EDKA1;

public class ExtractZORD05LENIDocTypeInterfacesProperties implements Callable{

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

		ZORD05LEN zord05len = (ZORD05LEN) message.getPayload();

		Contents content = new Contents();
		content.setName(ConnectConstants.VAR_DOCNUM);
		content.setValue(zord05len.getIDOC().getEDIDC40().getDOCNUM());

		customProps.getContents().add(content);

		if (zord05len.getIDOC() != null) {

			List<E1EDKA1> e1edka1List = zord05len.getIDOC().getE1EDKA1();

			for (E1EDKA1 e1edka1Obj : e1edka1List) {

				if (e1edka1Obj.getPARVW().equals("SE")) {

					Contents contentPartnerIDObj = new Contents();
					contentPartnerIDObj.setName(ConnectConstants.PARTNER_ID);
					contentPartnerIDObj.setValue(e1edka1Obj.getPARTN());

					customProps.getContents().add(contentPartnerIDObj);
					message.setInvocationProperty(ConnectConstants.PARTNER_ID, e1edka1Obj.getPARTN());

				}
			}

			List<E1EDK02> e1edk02ListObj = zord05len.getIDOC().getE1EDK02();

			for (E1EDK02 e1edk02Obj : e1edk02ListObj) {

				if (e1edk02Obj.getQUALF().equals("001")) {

					Contents contentRefNum = new Contents();
					contentRefNum.setName(ConnectConstants.REFERENCE_NUMBER);
					contentRefNum.setValue(e1edk02Obj.getBELNR());

					customProps.getContents().add(contentRefNum);

					message.setInvocationProperty(ConnectConstants.REFERENCE_NUMBER, e1edk02Obj.getBELNR());
				}
			}
		}
	
		custom.setAny(customProps);
		cem.getEnterpriseHeader().setCustom(custom);
		message.setInvocationProperty(ConnectConstants.ENTERPRISE_MESSAGE, cem);
		
		return message;
	}
}
