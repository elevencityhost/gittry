package com.redn.connect.proccessor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.activation.DataHandler;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.constants.InternalHTTPConstants;
import com.redn.connect.exception.Category;
import com.redn.connect.exception.ConnectException;
import com.redn.connect.processor.connectconfig.ConnectConfiguration;
import com.redn.connect.util.ConnectUtils;

public class ArchiveReceivedPayload implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		ConnectConfiguration connectConfiguration = eventContext.getMuleContext().getRegistry()
				.lookupObject(InternalHTTPConstants.VAR_CONNECT_CONFIG_BEAN);
		MuleMessage muleMessage = eventContext.getMessage();

		if (!muleMessage.getInboundAttachmentNames().isEmpty()) {
			DataHandler dataHandler = muleMessage.getInboundAttachment(InternalHTTPConstants.VAR_PAYLOAD);
			if (dataHandler != null) {

				String payloadReceived = (String) dataHandler.getContent();
				archivePayload(muleMessage, payloadReceived, connectConfiguration);
			}
		} else if (!((String) muleMessage.getPayload()).isEmpty()) {
			archivePayload(muleMessage, (String) muleMessage.getPayload(), connectConfiguration);
		}
		return muleMessage;
	}

	public void archivePayload(MuleMessage muleMessage, String payload, ConnectConfiguration connectConfiguration)
			throws IOException, ConnectException {

		String archivingLocationFromZUUL = connectConfiguration.get("connect.internal.http.write.archiving.location");

		if (archivingLocationFromZUUL != null && !archivingLocationFromZUUL.isEmpty()) {

			String fileName = InternalHTTPConstants.COMPONENT_NAME_CONSTANT + InternalHTTPConstants.VAR_HYPHEN
					+ muleMessage.getUniqueId() + InternalHTTPConstants.VAR_HYPHEN
					+ ConnectUtils.getCurrentTimeAsString() + InternalHTTPConstants.VAR_ARCHIVING_FILE_NAME_EXTENSION;
			File fileArchiveLocation = new File(archivingLocationFromZUUL + File.separator + fileName);
			FileWriter fw = new FileWriter(fileArchiveLocation);
			fw.write(payload);
			fw.close();
		} else {

			throw new ConnectException(InternalHTTPConstants.ERROR_CODE_ARCHIVE_LOCATION_EMPTY,
					InternalHTTPConstants.ERROR_CODE_ARCHIVE_LOCATION_EMPTY_DESC, Category.DATA,
					InternalHTTPConstants.EXCEPTION_ORIGIN_REDINGTON_GULF);

		}
	}
}
