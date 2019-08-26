package com.redn.connect.transformer;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.config.i18n.CoreMessages;
import org.mule.config.i18n.Message;
import org.mule.transformer.AbstractMessageTransformer;
import org.w3c.dom.Node;

import com.redn.connect.connectfileinbound.constants.FPConstants;
import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.ConnectEnterpriseMessage.EnterpriseBody;
import com.redn.connect.vo.EnterpriseHeader;
import com.redn.connect.vo.OutputProps;
/**
 * @author Sai Prasad Jonnala
 *
 * This java class creates the Enterprise Message 
 */
public class CreateConnectEnterpriseMessage extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {

		try {
			// creating the resource object
			
			ConnectUtils connectUtils = new ConnectUtils();
			ConnectEnterpriseMessage eMessage = new ConnectEnterpriseMessage();
			EnterpriseHeader eHeader = new EnterpriseHeader();
			EnterpriseBody eBody=new EnterpriseBody();

			EnterpriseHeader.Custom custom = new EnterpriseHeader.Custom();
			OutputProps outputProps = message.getProperty(FPConstants.OUTPUT_PROPS, PropertyScope.INVOCATION);
			  
			// setting the filesize as any type
			if(!outputProps.getCustomProperties().isUseCustomProps()){
				/*custom.setAny(new JAXBElement<String>(new QName("FileSize"),
					String.class, new String(message.getInboundProperty(FPConstants.FILE_SIZE).toString())));*/
				
				Node payloadNode = connectUtils.getNodeForPayload(new String(message.getInboundProperty(FPConstants.FILE_SIZE).toString()));
				custom.setAny(payloadNode);

			}
			String fileName = message
				.getInboundProperty(FPConstants.ORIGINAL_FILENAME);
		    String directory = outputProps.getTargetDirectory();

		    // added resource Id check for trigger file
		    
			String interfaceName=outputProps.getInterfaceName();
			eHeader.setServiceName(interfaceName);
			eHeader.setSourceSystem(interfaceName+FPConstants.INBOUND);
			eHeader.setAction(FPConstants.PUBLISH);
			eHeader.setMessageSource(directory+File.separator+fileName);
			eHeader.setComponent("connect-file-inbound");

			// setting randomUUID as messageId
			if(null != message.getProperty("messageId", PropertyScope.SESSION))
			eHeader.setMessageId(message.getProperty("messageId", PropertyScope.SESSION).toString());
			else
				eHeader.setMessageId(java.util.UUID.randomUUID().toString());
			//eHeader.setThreadId(java.util.UUID.randomUUID().toString());
			Long timestamp = (Long) message
					.getInboundProperty(FPConstants.TIMESTAMP);
			Timestamp timeStamp = new Timestamp(timestamp);
			Date fileCreation_date = new Date(timeStamp.getTime());
			// createdUtc= file polling time
			eHeader.setCreatedUtc(connectUtils.getDateAsXMLGregorianCalendar(fileCreation_date));
			eHeader.setCreatedUtc(connectUtils.getDateAsXMLGregorianCalendar(new Date()));
			//eBody.setAny(fileName);
			eMessage.setEnterpriseBody(eBody);
			eMessage.setEnterpriseHeader(eHeader);
			connectUtils.updateConnectEnterprsieMessagePayload(eMessage,fileName);
			
			message.setProperty(FPConstants.VAR_ENTERPRISE_MESSAGE, eMessage, PropertyScope.INVOCATION); 
			
			return eMessage;
		} catch (Exception exception) {
/*			
			Logger.getRootLogger().error(" The error coming while constructing the EnterpriseMessage  : The Reason is "
							+ exception.getMessage() + "", exception);*/

			// Converting any exception raises while transforming as TransformerException
			Message errorMessage = CoreMessages.failedToCreate("Enterprise Message, Problem while constructing the Enterprise Message and the error is:"+exception.getMessage());
			throw new TransformerException(errorMessage);
		}
	
	}
	
}
