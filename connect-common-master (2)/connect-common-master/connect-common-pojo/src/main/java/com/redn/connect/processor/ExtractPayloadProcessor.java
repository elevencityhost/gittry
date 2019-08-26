package com.redn.connect.processor;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;

/**
 * This processor is used to extract the payload text content
 * from {@link ConnectEnterpriseMessage}
 * 
 * @author Vinay Kumar Thota
 *
 */
public class ExtractPayloadProcessor implements Callable {

	   private static ConnectUtils connectUtils = new ConnectUtils();
	   
       @Override
       public Object onCall(MuleEventContext eventContext) throws Exception {
              
              MuleMessage message = eventContext.getMessage();
              
              Object payload = message.getPayload();
              
              if(!(payload instanceof ConnectEnterpriseMessage)){
                     throw new Exception("Payload is not an instance of ConnectEnterpriseMessage");
              }
              
              ConnectEnterpriseMessage cem = (ConnectEnterpriseMessage) message.getPayload();
              
              String payloadText = connectUtils.getPayloadTextContentFromCEM(cem);
              
              return payloadText;
       }
}