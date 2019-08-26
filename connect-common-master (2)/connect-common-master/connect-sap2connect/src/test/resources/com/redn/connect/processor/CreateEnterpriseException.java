package com.redn.connect.processor;

import java.io.StringReader;
import java.net.InetAddress;
import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;

import org.mule.api.ExceptionPayload;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.redn.connect.enterpriseseservices.sap2Connectgateway.constants.SAP2ConectConstants;
import com.redn.connect.processor.utils.CreateEnterpriseLogger;
import com.redn.connect.processor.utils.ErrorCategory;
import com.redn.connect.processor.utils.Utils;
import com.redn.connect.vo.ConnectEnterpriseException;
import com.redn.connect.vo.ConnectEnterpriseException.OriginalMessage;
import com.redn.connect.vo.ConnectEnterpriseMessage;

public class CreateEnterpriseException implements Callable {
	public Node getMessageBodyFromPayload(String idoc_xml) {

		Node nodeValue = null;

		try {
			nodeValue = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder()
					.parse((new InputSource(new StringReader(idoc_xml))))
					.getDocumentElement();

		} catch (Exception exception) {
		//	emLogger.error(null, "102005111",
		//			"Error occurred while constructing the Enterprise Exception Message: "
		//					+ exception.getMessage());

		}
		return nodeValue;

	}

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception 
	{
		// Creating enterpriseLogger
				CreateEnterpriseLogger logger = new CreateEnterpriseLogger();
		//        emLogge=priseLogger(eventContext.getMessage());

				return prepareEEMessage(eventContext.getMessage());
			}

			public void populateIDOC(MuleMessage message,
					ConnectEnterpriseException errorMessage)
					{
				if (message.getInboundProperty("isIdocHuge") != null
						&& message.getInboundProperty("isIdocHuge").toString()
								.equals("true")) 
				{
			//		emLogger.debug(null, "102005111",
			//				"populating resourceId with the address of idoc saved location with file name");
				}	
				 else {
		//			emLogger.debug(null, "102005111",
		//					"Appending original payload under EnterpriseExceptionMessage ");
					
				}
			}

			public Object prepareEEMessage(MuleMessage message)
			{
			//emloger.debug(null, "102005111",Constructing EnterpriseExceptionMessage started");
				
				ExceptionPayload exceptionPayload = message.getExceptionPayload();
				ConnectEnterpriseException errorMessage = new ConnectEnterpriseException();
			
				try {
					
	              errorMessage.setServiceName(SAP2ConectConstants.SERVICE_NAME);
					//errorMessage.setMachine(InetAddress.getLocalHost().getHostName
	              if (null != message
							.getProperty(SAP2ConectConstants.MESSAGE_ID,
									PropertyScope.SESSION)) {
						
						errorMessage.setMessageId(message
								.getProperty(SAP2ConectConstants.MESSAGE_ID,
										PropertyScope.SESSION).toString());
					} else {
						
						errorMessage.setMessageId(message.getUniqueId().toString());
					}
					errorMessage.setExceptionId(java.util.UUID.randomUUID().toString());
					errorMessage.setCreatedUtc(Utils
							.getDateAsXMLGregorianCalendar(new Date()));
					
					
					// In case of API gateway exception
					if (exceptionPayload == null) {
					 errorMessage.setDescription(SAP2ConectConstants.APIGATEWAY_ERROR_DESCRIPTION);
						
					} else {
						errorMessage.setErrorCode(Integer.toString(exceptionPayload.getCode()));
						errorMessage.setDescription(exceptionPayload.getException()
								.getMessage());
						//errorMessage.setDetails(exceptionPayload.getException()
							//	.getCause().getMessage());
					}
					// In case exception not raised during EM construction
					if (message.getInboundProperty("isEMError") != null
							&& message.getInboundProperty("isEMError").equals("true")) {
							populateIDOC(message, errorMessage);
					}
					errorMessage.setCategory(ErrorCategory.ERROR.getErrorCategory());
					OriginalMessage originalPayload = new OriginalMessage();
					if(null != message.getProperty("eMessage", PropertyScope.INVOCATION)){
		            	ConnectEnterpriseMessage eMessage = (ConnectEnterpriseMessage)message.getProperty("eMessage", PropertyScope.INVOCATION);
		            	originalPayload.setAny(eMessage);
		            }else{
		            	String originalMesage = "originalMessage";
		              originalPayload.setAny(originalMesage);
		            	}
					errorMessage.setOriginalMessage(originalPayload);
					} catch (Exception exception) {
		//			emLogger.error(null, "102005111",
		//					"Error occurred while constructing the Enterprise Exception Message: "
		//							+ exception.getMessage());
					
					return errorMessage;
				} finally {
	//				emLogger.debug(null, "102005111",
	//						"Constructing EnterpriseExceptionMessage completed");
				}

				return errorMessage;	
	}

}
