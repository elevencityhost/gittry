package com.redn.connect.sapgw.processor;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import com.redn.connect.sapgw.constants.Connect2sapCommonConstants;
import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.Contents;
import com.redn.connect.vo.CustomProps;

public class SAPDestinationProcessor implements Callable {
	
	@SuppressWarnings("unchecked")
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage message = eventContext.getMessage();
		Object obj = message.getPayload();
		
		if( obj instanceof ConnectEnterpriseMessage) {
			 			ConnectEnterpriseMessage cem = (ConnectEnterpriseMessage) obj;

	 				
			CustomProps customProp = ConnectUtils.getCustomPropsFromCEM(cem);
			
			if( customProp != null && !customProp.getContents().isEmpty()) {
				List<Contents> contents = customProp.getContents();
				if( contents != null ) {
					for(Contents content : contents) {
	 					if(content.getName().equalsIgnoreCase(Connect2sapCommonConstants.VAR_IDOC_SOURCE_TYPE)) {
	 						String sapVendorName = content.getValue();
	 						message.setProperty(Connect2sapCommonConstants.VAR_IDOC_SOURCE_TYPE, sapVendorName,
	 								PropertyScope.INVOCATION);
	 					}
					}
				}
			}
	 				
	 			
		}

		

		return message;
	}

}
