/**
 * 
 * @author SaiPrasad.Jonnala
 * {@link ConnectEnterpriseMessage}
 * {@link ConnectUtils}
 * 
 * ExtractPayloadFromCEM - This class contains the logic to retrieve the payload data from ConnectEnterpriseMessage
 * 
 * ***/

package com.redn.connect.notifier.processor;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;

public class ExtractPayloadFromCEM implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage muleMessage = eventContext.getMessage();
		ConnectEnterpriseMessage connectEnterpriseMessage = (ConnectEnterpriseMessage) muleMessage.getPayload();

		ConnectUtils connectUtils = new ConnectUtils();
		String payloadAsText = connectUtils.getPayloadTextContentFromCEM(connectEnterpriseMessage);

		muleMessage.setPayload(payloadAsText);

		return muleMessage;
	}
}
