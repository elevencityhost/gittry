package com.redn.connect.test.internalhttp;

import javax.activation.DataHandler;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.message.ds.ByteArrayDataSource;
import org.mule.message.ds.StringDataSource;
import org.mule.transformer.AbstractMessageTransformer;

public class TestPrepareCEMWithAttachments extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {

		String attachmentContent = "Test Data Part Of Attachment";

		
		try {
			message.addOutboundAttachment("payload", new DataHandler(new StringDataSource("TestAttachmentPayloadData")));
			for (int noOfAttachments = 0; noOfAttachments < 3; noOfAttachments++) {

				ByteArrayDataSource byteArrayDataSource = new ByteArrayDataSource(attachmentContent.getBytes("UTF-8"),
						"binary/octet-stream", "fileName" + noOfAttachments);
				message.addOutboundAttachment("attachmentName" + noOfAttachments, new DataHandler(byteArrayDataSource));

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		message.setPayload(null);
		return message;

	}

}
