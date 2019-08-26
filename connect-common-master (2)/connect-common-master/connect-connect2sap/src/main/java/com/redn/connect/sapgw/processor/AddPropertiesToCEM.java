package com.redn.connect.sapgw.processor;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.processor.connectconfig.ConnectConfiguration;
import com.redn.connect.sapgw.constants.Connect2sapCommonConstants;
import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.EnterpriseHeader;

/**
 * 
 * @author Prathyusha.V
 * 
 * This class would be used for adding file properties to the CEM
 */

public class AddPropertiesToCEM implements Callable {
	
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		ConnectConfiguration connectConfiguration = eventContext.getMuleContext().getRegistry().lookupObject("connect2sapCommonConfiguration");
		MuleMessage message = eventContext.getMessage();
		ConnectEnterpriseMessage connectEnterpriseMessage = (ConnectEnterpriseMessage)message.getProperty(Connect2sapCommonConstants.VAR_ENTERPRISE_MESSAGE,PropertyScope.INVOCATION);
		EnterpriseHeader enterpriseHeader = connectEnterpriseMessage.getEnterpriseHeader();
		String serviceName = enterpriseHeader.getServiceName();
		
		
		//String cretim =message.getProperty("cretim",PropertyScope.INVOCATION);
		String sonumber= message.getProperty(ConnectConstants.REFERENCE_NUMBER,PropertyScope.INVOCATION); 
		String eh=message.getProperty(Connect2sapCommonConstants.VAR_MESSAGE_ID,PropertyScope.INVOCATION);
		
		String fileArchiveLocation = connectConfiguration.get(Connect2sapCommonConstants.FILE_ARCHIVE_LOCATION);
		
		String out = serviceName+"-"+sonumber+"-"+eh+"-"+ConnectUtils.getCurrentTimeAsString()+".xml";
		
		message.setProperty("outputFileName",out.trim(),PropertyScope.INVOCATION);
		message.setProperty("fileArchiveLocationOfConnect2SAP",fileArchiveLocation, PropertyScope.INVOCATION);
		
		return message;
	}
	


}