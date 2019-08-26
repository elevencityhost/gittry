package com.redn.connect.processor;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.constants.HTTPOutboundConstants;
import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.HTTPDetailsVo;

/**
 * 
 * @author Shruthi.Kolloju This class is to form httpDetails object as
 *         invocation variable and extract content from file
 */
public class ExtractFileAndHttpDetails implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage message = eventContext.getMessage();
		ConnectUtils utils = new ConnectUtils();
		HTTPDetailsVo httpDetails = (HTTPDetailsVo) message.getPayload();
		message.setInvocationProperty(HTTPOutboundConstants.HTTP_DETAILS, httpDetails);
		ConnectEnterpriseMessage cem = message.getInvocationProperty(ConnectConstants.VAR_ENTERPRISE_MESSAGE);

		String fileLocation = utils.getContentValue(cem, ConnectConstants.VAR_ATTACHMENT_PATH);
		String dataToSend = "";
		
		if (null != fileLocation && !("".equals(fileLocation))) {
			dataToSend = utils.readDataFromFile(fileLocation);
		}
		return dataToSend;
	}

}
