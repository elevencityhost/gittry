/***
 * 
 * @author SaiPrasad.Jonnala
 * 
 * {@link MuleMessage}
 * {@link ConnectUtils}
 * {@link ConnectEnterpriseMessage}
 * {@link ConnectException}
 * 
 * CreateCEMAndAppendPayload - It contains the logic to build ConnectEnterpriseMessage.
 * Before building the CEM it validates the received inboundAttachments and payload content received.
 * Appropriate exceptions are thrown based on the scenarios.
 * 
 * */

package com.redn.connect.proccessor;

import java.io.File;
import java.util.Date;

import javax.activation.DataHandler;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.constants.InternalHTTPConstants;
import com.redn.connect.exception.Category;
import com.redn.connect.exception.ConnectException;
import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.Contents;
import com.redn.connect.vo.CustomProps;
import com.redn.connect.vo.EnterpriseHeader;
import com.redn.connect.vo.EnterpriseHeader.Custom;

public class CreateCEMAndAppendPayload implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		ConnectUtils connectUtils = new ConnectUtils();
		ConnectEnterpriseMessage connectEnterpriseMessage = null;

		MuleMessage muleMessage = eventContext.getMessage();
		DataHandler dataHandler = muleMessage.getInboundAttachment(InternalHTTPConstants.VAR_PAYLOAD);

		if (!muleMessage.getInboundAttachmentNames().isEmpty()) {

			if (dataHandler != null) {

				String payloadReceived = (String) dataHandler.getContent();
				connectEnterpriseMessage = createCEM(connectUtils, muleMessage, payloadReceived);

			} else {

				throw new ConnectException(InternalHTTPConstants.ERROR_CODE_MANDATORY_DATA_MISSING,
						" Inbound Attachment with name 'payload' is missing in inbound attachments ", Category.DATA,
						InternalHTTPConstants.EXCEPTION_ORIGIN_REDINGTON_GULF);
			}
		} else if (muleMessage.getPayload() != null) {
			connectEnterpriseMessage = createCEM(connectUtils, muleMessage, muleMessage.getPayload().toString());
		} else {

			throw new ConnectException(InternalHTTPConstants.ERROR_CODE_MANDATORY_DATA_MISSING,
					" Inbound Attachment with name 'payload' and main payload is missing in inbound attachments ",
					Category.DATA, InternalHTTPConstants.EXCEPTION_ORIGIN_REDINGTON_GULF);
		}

		muleMessage.setProperty(ConnectConstants.ENTERPRISE_MESSAGE, connectEnterpriseMessage,
				PropertyScope.INVOCATION);
		muleMessage.setPayload(connectEnterpriseMessage);
		return muleMessage;
	}

	private ConnectEnterpriseMessage createCEM(ConnectUtils connectUtils, MuleMessage muleMessage,
			String payloadReceived) throws Exception {

		ConnectEnterpriseMessage connectEnterpriseMessage = connectUtils.buildConnectEnterprsieMessage(payloadReceived);
		EnterpriseHeader enterpriseHeader = connectEnterpriseMessage.getEnterpriseHeader();

		String sourceQueryParam = muleMessage.getOutboundProperty(InternalHTTPConstants.VAR_SOURCE_QUERY_PARAM)
				.toString();
		String[] sourceQueryParamArray = sourceQueryParam.split("_");
		System.out.println(sourceQueryParamArray.length+"********************* SourceQueryPARAM ==================="+sourceQueryParamArray);
		if (sourceQueryParamArray.length >= 2) {

			enterpriseHeader.setMessageId(muleMessage.getUniqueId());
			enterpriseHeader.setAction(InternalHTTPConstants.ACTION_CONSTANT);
			enterpriseHeader.setCommunication(InternalHTTPConstants.COMMUNICATION_CONSTANT);
			enterpriseHeader.setServiceName(sourceQueryParamArray[1]);
			enterpriseHeader.setComponent(InternalHTTPConstants.COMPONENT_NAME_CONSTANT);
			enterpriseHeader.setMessageSource(sourceQueryParamArray[0]);
			enterpriseHeader.setCreatedUtc(connectUtils.getDateAsXMLGregorianCalendar(new Date()));
			enterpriseHeader.setPriority("5");

			String attachmentNames = muleMessage.getProperty(InternalHTTPConstants.VAR_ATTACHMENT_NAMES,
					PropertyScope.INVOCATION);
			String fileProcessingLocation = muleMessage.getProperty(InternalHTTPConstants.VAR_FILE_PROCESSING_LOCATION,
					PropertyScope.INVOCATION);

			Custom customObj = enterpriseHeader.getCustom();

			if (customObj == null) {
				customObj = new Custom();
			}

			CustomProps customPropsObj = null;
			if (customObj.getAny() != null) {
				customPropsObj = (CustomProps) customObj.getAny();
			} else {
				customPropsObj = new CustomProps();
			}

			if (attachmentNames != null) {

				String[] receivedAttachmentNames = attachmentNames.split(",");
				for (String individualAttachmentName : receivedAttachmentNames) {
					Contents contentAttachment = new Contents();
					contentAttachment.setName(ConnectConstants.VAR_ATTACHMENT_PATH);
					contentAttachment.setValue(fileProcessingLocation + File.separator + individualAttachmentName);
					customPropsObj.getContents().add(contentAttachment);
				}
			}

			customObj.setAny(customPropsObj);
			enterpriseHeader.setCustom(customObj);
		} else {
			
			throw new ConnectException(
					InternalHTTPConstants.ERROR_CODE_HTTP_QUERY_PARAM_SOURCE_NOT_AS_PER_SPECIFICATION,
					InternalHTTPConstants.ERROR_CODE_HTTP_QUERY_PARAM_SOURCE_NOT_AS_PER_SPECIFICATION_DESC,
					Category.DATA, InternalHTTPConstants.EXCEPTION_ORIGIN_REDINGTON_GULF);
		}

		return connectEnterpriseMessage;
	}
}
