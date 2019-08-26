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
import com.redn.connect.zasn.len.ZASNLEN;
import com.redn.connect.zasn.len.ZASNLEN.IDOC.E1EDL20;
import com.redn.connect.zasn.len.ZASNLEN.IDOC.E1EDL20.ZE1EDL20;

public class ExtractZASNLENIDocTypeInterfacesProperties implements Callable{

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


		ZASNLEN zASNLen = (ZASNLEN) message.getPayload();

		List<E1EDL20> e1EDL20List = zASNLen.getIDOC().getE1EDL20();

		Contents content = new Contents();
		content.setName(ConnectConstants.VAR_DOCNUM);
		content.setValue(zASNLen.getIDOC().getEDIDC40().getDOCNUM());
		customProps.getContents().add(content);

		for (E1EDL20 E1EDL20Obj : e1EDL20List) {

			ZE1EDL20 ze1edl20Obj = E1EDL20Obj.getZE1EDL20();

			Contents contentRefNum = new Contents();
			contentRefNum.setName(ConnectConstants.REFERENCE_NUMBER);
			contentRefNum.setValue(ze1edl20Obj.getVBELN());
			customProps.getContents().add(contentRefNum);

			Contents contentPartnerObj = new Contents();
			contentPartnerObj.setName(ConnectConstants.PARTNER_ID);
			contentPartnerObj.setValue(ze1edl20Obj.getPARTNERID());
			customProps.getContents().add(contentPartnerObj);

			message.setInvocationProperty(ConnectConstants.REFERENCE_NUMBER, ze1edl20Obj.getVBELN());
			message.setInvocationProperty(ConnectConstants.PARTNER_ID, ze1edl20Obj.getPARTNERID());
		}
	
		custom.setAny(customProps);
		cem.getEnterpriseHeader().setCustom(custom);
		message.setInvocationProperty(ConnectConstants.ENTERPRISE_MESSAGE, cem);
		
		return message;
	}
}
