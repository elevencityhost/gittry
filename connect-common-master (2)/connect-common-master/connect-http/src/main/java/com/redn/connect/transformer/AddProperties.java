package com.redn.connect.transformer;

import org.mule.api.MuleEventContext;

import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;
import com.redn.connect.processor.connectconfig.ConnectConfiguration;

/**
 * @author ShravaniMerugu
 * 
 *         This class adding file properties to the CEM
 */

public class AddProperties implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		MuleMessage message = eventContext.getMessage();

		String messageSource = message.getProperty("source", PropertyScope.INVOCATION);
		String relativePath = message.getProperty("relativePath", PropertyScope.INVOCATION);
		
		ConnectConfiguration connectConfiguration = eventContext.getMuleContext().getRegistry()
				.lookupObject("connectConfigBean");
		String serviceNameIdentificationKey = "connect.connecthttp." + relativePath + "." + messageSource
				+ ".serviceName";

		String serviceName = connectConfiguration.get(serviceNameIdentificationKey);

		message.setProperty("serviceName", serviceName, PropertyScope.INVOCATION);
		

		String fileArchiveLocationForRequest = connectConfiguration
				.get("connect.connecthttp.request.fileArchiveLocation");
		String fileArchiveLocationForResponse = connectConfiguration
				.get("connect.connecthttp.response.fileArchiveLocation");

		message.setProperty("fileArchiveLocationForRequest", fileArchiveLocationForRequest,
				PropertyScope.INVOCATION);
		message.setProperty("fileArchiveLocationForResponse", fileArchiveLocationForResponse,
				PropertyScope.INVOCATION);

		return message;
	}

}