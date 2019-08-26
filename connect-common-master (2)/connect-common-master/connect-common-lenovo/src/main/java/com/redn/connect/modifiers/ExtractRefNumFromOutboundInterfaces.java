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
import com.redn.connect.zorder.len.ZORD05LEN;
import com.redn.connect.zorder.len.ZORD05LEN.IDOC.E1EDK02;
import com.redn.connect.zorder.len.ZORD05LEN.IDOC.E1EDKA1;
import com.redn.connect.zostrpt01.len.ZOSTRPT01;
import com.redn.connect.zostrpt01.len.ZOSTRPT01.IDOC.ZE1EDK34;
import com.redn.connect.zostrpt01.len.ZOSTRPT01.IDOC.ZE1EDK34.ZE1ADRM1;
import com.redn.connect.zostrpt01.len.ZOSTRPT01.IDOC.ZE1EDK34.ZE1EDP31;
import com.redn.connect.zostrpt01.len.ZOSTRPT01.IDOC.ZE1EDK34.ZE1EDP31.ZE1EDP19;

public class ExtractRefNumFromOutboundInterfaces implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage message = eventContext.getMessage();

		ConnectEnterpriseMessage cem = (ConnectEnterpriseMessage) message
				.getProperty(ConnectConstants.ENTERPRISE_MESSAGE, PropertyScope.INVOCATION);

		String serviceName = cem.getEnterpriseHeader().getServiceName();

		Custom custom = cem.getEnterpriseHeader().getCustom();
		if (custom == null) {
			custom = new Custom();
		}

		CustomProps customProps = new CustomProps();

		if (serviceName.equalsIgnoreCase("o3klen002") || serviceName.equalsIgnoreCase("o3klen003")) {

			prepareDataFor002And003Interfaces(message, customProps);

		} else if (serviceName.equalsIgnoreCase("o3klen006")) {

			prepareDataFor006Interface(message, customProps);

		} else if (serviceName.equalsIgnoreCase("o3klen007")) {

			prepareDataFor007Interface(message, customProps);

		} else if (serviceName.equalsIgnoreCase("o3klen008") || serviceName.equalsIgnoreCase("o3klen009")) {

			Contents content = new Contents();
			content.setName(ConnectConstants.VAR_DOCNUM);
			
			String docnum = message.getProperty(ConnectConstants.VAR_DOCNUM, PropertyScope.INVOCATION);
			content.setValue(docnum);
			customProps.getContents().add(content);
		}

		custom.setAny(customProps);
		cem.getEnterpriseHeader().setCustom(custom);
		message.setInvocationProperty(ConnectConstants.ENTERPRISE_MESSAGE, cem);
		return message;
	}

	private void prepareDataFor006Interface(MuleMessage message, CustomProps customProps) {
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
	}

	private void prepareDataFor007Interface(MuleMessage message, CustomProps customProps) {

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

				if (ZE1EDP19Obj != null && ZE1EDP19Obj.getQUALF().equals("002")) {

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
	}

	private void prepareDataFor002And003Interfaces(MuleMessage message, CustomProps customProps) {

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
	}

}