package com.redn.connect.ftps.outbound.processors;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.FileUtils;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.exception.Category;
import com.redn.connect.exception.ConnectException;
import com.redn.connect.ftps.outbound.constants.FTPSOutboundConstants;
import com.redn.connect.processor.connectconfig.ConnectConfiguration;

public class PrepareFileAsStreamObject implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage muleMessage = eventContext.getMessage();
		ConnectConfiguration connectConfiguration = eventContext.getMuleContext().getRegistry()
				.lookupObject("connectConfigBean");

		String fileNameAndLocation = (String) muleMessage.getPayload();

		if (fileNameAndLocation != null && !fileNameAndLocation.isEmpty()) {

			File fileObj = new File(fileNameAndLocation);
			
			String archivingLocation = connectConfiguration.get("connect.ftps.outbound.archiving.location");
			if (archivingLocation == null || archivingLocation.isEmpty()) {
				throw new ConnectException(FTPSOutboundConstants.ERROR_CODE_ARCHIVING_LOCATION_NOT_EXIST,
						FTPSOutboundConstants.ERROR_CODE_ARCHIVING_LOCATION_NOT_EXIST_DESC, Category.DATA,
						ConnectConstants.CONST_EXCEPTION_ORIGIN_MULE_ESB_NS);
			}
			
			FileInputStream fileInputStream = null;

			if (fileObj.exists()) {

				fileInputStream = new FileInputStream(fileObj);

			} else {

				throw new ConnectException(FTPSOutboundConstants.ERROR_CODE_FILE_NOT_EXIST,
						FTPSOutboundConstants.ERROR_CODE_FILE_NOT_EXIST_DESC, Category.DATA,
						ConnectConstants.CONST_EXCEPTION_ORIGIN_MULE_ESB_NS);
			}

			File fileArchiving = new File(archivingLocation + File.separator
					+ muleMessage.getInvocationProperty(FTPSOutboundConstants.VAR_ARCHIVING_FILE_NAME_Pattern));
			FileUtils.copyFile(fileObj, fileArchiving);
			muleMessage.setProperty(FTPSOutboundConstants.VAR_FILE_NAME, fileObj.getName(), PropertyScope.INVOCATION);
			muleMessage.setProperty(FTPSOutboundConstants.VAR_STREAM_OBJECT, fileInputStream, PropertyScope.INVOCATION);

		} else {

			throw new ConnectException(FTPSOutboundConstants.ERROR_CODE_FILE_LOCATIONS_LIST_NAME_EMPTY,
					FTPSOutboundConstants.ERROR_FILE_LOCATIONS_LIST_NAME_EMPTY_DESC, Category.DATA,
					ConnectConstants.CONST_EXCEPTION_ORIGIN_MULE_ESB_NS);
		}

		return muleMessage;
	}
}
