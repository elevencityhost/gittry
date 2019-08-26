package com.redn.connect.processor;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import com.redn.connect.processor.connectconfig.ConnectConfiguration;

/**
 * 
 * @author Mahasweta.Das
 *
 *         This class Setting Connect enterprise message properties
 */
public class AllocateEnterpriseMessageProperties implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		MuleMessage message = eventContext.getMessage();

		ConnectConfiguration connectConfiguration = eventContext.getMuleContext().getRegistry()
				.lookupObject("sap2connectCommonConfigBean");

		if (null != message.getProperty("IDOC_MESSAGE_ID", PropertyScope.INVOCATION)) {
			message.setProperty("messageId", message.getProperty("IDOC_MESSAGE_ID", PropertyScope.INVOCATION),
					PropertyScope.SESSION);
		} else {
			message.setProperty("messageId", message.getUniqueId().toString(), PropertyScope.SESSION);
		}
		message.setProperty("messageSource", "FICO-OUTBOUND", PropertyScope.SESSION);
		message.setProperty("messageAction", "Publish", PropertyScope.SESSION);

		/*
		 * if(null != message.getProperty("DOCNUM" , PropertyScope .INVOCATION))
		 * { message.setProperty("resourceId", message.getProperty("DOCNUM",
		 * PropertyScope.INVOCATION), PropertyScope.SESSION); }
		 * 
		 * // ResourceName resourceName =
		 * message.getProperty("IDOCTYP",PropertyScope.INVOCATION);
		 * 
		 * //MESSAGE_TYPE if(null !=
		 * message.getProperty("MESTYP",PropertyScope.INVOCATION)) {
		 * resourceName =
		 * resourceName+"-"+message.getProperty("MESTYP",PropertyScope.
		 * INVOCATION); }
		 */

		String messageCode = message.getProperty("MESCOD", PropertyScope.INVOCATION);
		messageCode = messageCode.trim();
		String messageFunction = message.getProperty("MESFCT", PropertyScope.INVOCATION);
		String basicTypeOfIDOC = message.getProperty("BasicTypeOfIDOC", PropertyScope.INVOCATION);
		String serviceNameIdentificationKey = "connect.sap2connect.serviceName." + basicTypeOfIDOC + "."
				+ messageCode.trim() + "." + messageFunction;
		String serviceName = connectConfiguration.get(serviceNameIdentificationKey);

		// Logic For Fetching Service Name For Banking Interfaces
		
		if (serviceName == null || serviceName.isEmpty()) {
		
			String PEXR2002_PAYRUNDT = message.getProperty("PAYRUNDT", PropertyScope.INVOCATION);
			
			if (PEXR2002_PAYRUNDT != null && !PEXR2002_PAYRUNDT.isEmpty()) {

				serviceNameIdentificationKey = "connect.sap2connect.serviceName." + basicTypeOfIDOC + "."
						+ PEXR2002_PAYRUNDT.trim();
				serviceName = connectConfiguration.get(serviceNameIdentificationKey);
			
			}
		}
		message.setProperty("serviceName", serviceName, PropertyScope.SESSION);

		String fileArchiveLocation = connectConfiguration.get("connect.sap2connect.fileArchiveLocation");
		if (serviceName != null)
			message.setProperty("fileArchiveLocation", fileArchiveLocation, PropertyScope.SESSION);
		else
			message.setProperty("fileArchiveLocation", fileArchiveLocation + "/Unknown", PropertyScope.SESSION);

		return message;
	}

}
