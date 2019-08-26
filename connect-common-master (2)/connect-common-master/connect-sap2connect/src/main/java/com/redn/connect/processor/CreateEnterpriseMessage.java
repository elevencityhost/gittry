package com.redn.connect.processor;

import java.util.Date;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import com.redn.connect.sap2connect.common.constants.SAP2ConnectConstants;
import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.EnterpriseHeader;

/**
 * 
 * @author Sai Prasad Jonnala
 *
 *This class create Connect Enterprise message 
 */

public class CreateEnterpriseMessage implements Callable {
	

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception 
	{
		MuleMessage message = null;
		try {
			String fileName=null;
			message = eventContext.getMessage();
	           ConnectUtils connectUtils = new ConnectUtils();
	            ConnectEnterpriseMessage connectEnterpriseMessage = connectUtils.buildConnectEnterprsieMessage(message.getPayloadAsString());
	            EnterpriseHeader eHeader = connectEnterpriseMessage.getEnterpriseHeader();
	            
	        connectEnterpriseMessage.getEnterpriseHeader().setComponent("connect-sap2connect");
	        
			if(null != message.getProperty(SAP2ConnectConstants.IDOC_MESSAGE_ID,PropertyScope.OUTBOUND))
			{
				eHeader.setMessageId(message.getProperty(SAP2ConnectConstants.IDOC_MESSAGE_ID,PropertyScope.OUTBOUND).toString());
				
			}else{
				eHeader.setMessageId(message.getUniqueId().toString());	
			}
			eHeader.setCreatedUtc((connectUtils.getDateAsXMLGregorianCalendar(new Date())));
	
			
			if (message.getProperty("isIdocHuge", PropertyScope.OUTBOUND)
					.toString().equals("true")) {

				fileName = message.getProperty(SAP2ConnectConstants.IDOC_FILE_LOCATION,PropertyScope.OUTBOUND)+ "/"
						+ message.getProperty(SAP2ConnectConstants.IDOC_FILE_NAME,PropertyScope.OUTBOUND)+".xml";
			}
			connectEnterpriseMessage.setEnterpriseHeader(eHeader);
			message.setOutboundProperty("isEMError", "false");
			
			String serviceName = message.getProperty(SAP2ConnectConstants.VAR_SERVICE_NAME,PropertyScope.SESSION);
			eHeader.setAction(SAP2ConnectConstants.VAR_NON_AS2);
			eHeader.setCommunication(SAP2ConnectConstants.VAR_ASYNC);
			eHeader.setServiceName(serviceName);
			
			 connectEnterpriseMessage.setEnterpriseHeader(eHeader);

	            return connectEnterpriseMessage;
		} catch (Exception enterpriseMessageException) {
			message.setOutboundProperty("isEMError", "true");
			throw new Exception(enterpriseMessageException);
		} finally {
	//		emLogger.debug(null, "102005111",
	//				"Constructing EnterpriceMessage is completed");
		}

		

	}
}
