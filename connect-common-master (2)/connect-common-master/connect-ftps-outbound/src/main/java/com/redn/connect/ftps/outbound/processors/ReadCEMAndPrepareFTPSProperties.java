package com.redn.connect.ftps.outbound.processors;

import java.util.Arrays;
import java.util.List;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.exception.Category;
import com.redn.connect.exception.ConnectException;
import com.redn.connect.ftps.outbound.constants.FTPSOutboundConstants;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.Contents;
import com.redn.connect.vo.CustomProps;
import com.redn.connect.vo.EnterpriseHeader;
import com.redn.connect.vo.EnterpriseHeader.Custom;

public class ReadCEMAndPrepareFTPSProperties implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		List<String> listOfFileLocations = null;
		MuleMessage muleMessage = eventContext.getMessage();
		ConnectEnterpriseMessage connectEnterpriseMessage = (ConnectEnterpriseMessage) muleMessage.getPayload();
		EnterpriseHeader enterpriseHeader = connectEnterpriseMessage.getEnterpriseHeader();
		Custom customObj = enterpriseHeader.getCustom();

		if (customObj != null) {

			CustomProps customPropsObj = (CustomProps) customObj.getAny();
			if (customPropsObj != null) {

				List<Contents> contentsList = customPropsObj.getContents();

				if (contentsList == null || contentsList.isEmpty()) {

					// throw exception

					throw new ConnectException(FTPSOutboundConstants.ERROR_CODE_CONTENTS_LIST_EMPTY,
							FTPSOutboundConstants.ERROR_CONTENTS_LIST_EMPTY_DESC, Category.DATA,
							ConnectConstants.CONST_EXCEPTION_ORIGIN_MULE_ESB_NS);
				}

				for (Contents contentsObj : contentsList) {
					if (contentsObj.getName() != null
							&& contentsObj.getName().equals(ConnectConstants.VAR_ATTACHMENT_PATH)) {

						String listOfAttachmentNames = contentsObj.getValue();
						listOfFileLocations = Arrays.asList(listOfAttachmentNames.split(","));

					}
				}

			} else {

				throw new ConnectException(FTPSOutboundConstants.ERROR_CODE_CUSTOM_PROPS_OBJ_EMPTY,
						FTPSOutboundConstants.ERROR_CODE_CUSTOM_PROPS_OBJ_EMPTY_DESC, Category.DATA,
						ConnectConstants.CONST_EXCEPTION_ORIGIN_MULE_ESB_NS);

			}
		} else {

			throw new ConnectException(FTPSOutboundConstants.ERROR_CODE_CUSTOM_OBJ_NOT_FOUND,
					FTPSOutboundConstants.ERROR_CODE_CUSTOM_OBJ_NOT_FOUND_DESC, Category.DATA,
					ConnectConstants.CONST_EXCEPTION_ORIGIN_MULE_ESB_NS);
		}

		if (listOfFileLocations == null || listOfFileLocations.isEmpty()) {

			// throw exception

			throw new ConnectException(FTPSOutboundConstants.ERROR_CODE_FILE_LOCATIONS_LIST_EMPTY,
					FTPSOutboundConstants.ERROR_FILE_LOCATIONS_LIST_EMPTY_DESC, Category.DATA,
					ConnectConstants.CONST_EXCEPTION_ORIGIN_MULE_ESB_NS);
		}

		muleMessage.setInvocationProperty(FTPSOutboundConstants.LIST_OF_FILE_LOCATIONS, listOfFileLocations);

		String targetSystem = enterpriseHeader.getTargetSystem();

		if (targetSystem == null || targetSystem.isEmpty()) {
			throw new ConnectException(FTPSOutboundConstants.ERROR_CODE_TARGET_SYSTEM_IS_EMPTY,
					FTPSOutboundConstants.ERROR_CODE_TARGET_SYSTEM_IS_EMPTY_DESC, Category.DATA,
					ConnectConstants.CONST_EXCEPTION_ORIGIN_MULE_ESB_NS);
		}

		targetSystem = targetSystem.toLowerCase() + FTPSOutboundConstants.VAR_TARGET_SYSTEM_SEND_FLOW_SUFFIX;
		muleMessage.setInvocationProperty(FTPSOutboundConstants.VAR_TARGET_SYSTEM_SEND_FLOW, targetSystem);

		return muleMessage;
	}
}
