/**
 * @author saiprasad.jonnala
 * 
 * For O3KLEN012 interface
 * 
 * 
 * */

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
import com.redn.connect.zorder.len.ZORD05LEN.IDOC.E1EDK01;
import com.redn.connect.zorder.len.ZORD05LEN.IDOC.E1EDK01.ZE1EDKFT01;
import com.redn.connect.zorder.len.ZORD05LEN.IDOC.E1EDK02;

public class ExtractZORD05LENIDocTypePropertiesForCommercialInterfaces implements Callable {

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

		if (zord05len.getIDOC() != null) {

			E1EDK01 e1edk01 = zord05len.getIDOC().getE1EDK01();
			List<ZE1EDKFT01> ze1edkft01List = e1edk01.getZE1EDKFT01();

			List<E1EDK02> e1edk02ListObj = zord05len.getIDOC().getE1EDK02();

			Contents content = new Contents();
			content.setName(ConnectConstants.VAR_DOCNUM);
			content.setValue(zord05len.getIDOC().getEDIDC40().getDOCNUM());

			customProps.getContents().add(content);

			if (null != ze1edkft01List) {

				for (ZE1EDKFT01 ze1edkft01Obj : ze1edkft01List) {
					
					if(ze1edkft01Obj.getQUAL() != null)
					if (ze1edkft01Obj.getQUAL().equals("020")) {

						String partnerID = ze1edkft01Obj.getVAL3();

						Contents contentPartnerIDObj = new Contents();
						contentPartnerIDObj.setName(ConnectConstants.PARTNER_ID);
						contentPartnerIDObj.setValue(partnerID);

						customProps.getContents().add(contentPartnerIDObj);
						message.setInvocationProperty(ConnectConstants.PARTNER_ID, partnerID);
						break;
					}

				}

			}

			if (null != e1edk02ListObj) {

				for (E1EDK02 e1edk02Obj : e1edk02ListObj) {

					if(e1edk02Obj.getQUALF() != null)
					if (e1edk02Obj.getQUALF().equals("001")) {

						Contents contentRefNum = new Contents();
						contentRefNum.setName(ConnectConstants.REFERENCE_NUMBER);
						contentRefNum.setValue(e1edk02Obj.getBELNR());

						customProps.getContents().add(contentRefNum);
						message.setInvocationProperty(ConnectConstants.REFERENCE_NUMBER, e1edk02Obj.getBELNR());
						break;
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
