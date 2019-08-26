package com.redn.connect.transformer;

import java.io.StringReader;
import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.transformer.AbstractMessageTransformer;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.redn.connect.constants.APIManagerConstants;
import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseResponse;
import com.redn.connect.vo.ConnectEnterpriseResponse.ResponseBody;
import com.redn.connect.vo.ResponseHeader;

/**
 * 
 * @author Adarsh Annapureddy
 * 
 *This class is creating of Enterprise response logic
 */

//import com.umgi.es.connectors.enterpriseglobalutilities.EnterpriseGlobalutilitiesConnector;

public class CreateXMLEnterpriseResponse extends AbstractMessageTransformer {
	ConnectUtils connectUtils = new ConnectUtils();

	
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding)
			throws TransformerException {

		ResponseHeader responseHeader = new ResponseHeader();
		ConnectEnterpriseResponse enterpriseResponse;
		String description;
		
		if(null!=message.getProperty(APIManagerConstants.LOGICAL_KEY, PropertyScope.INVOCATION))
		{
			description = "Successfully routed Enterprise Message to JMS Topic";
			responseHeader.setState("Completed");
			responseHeader.setStatus("1");
			responseHeader.setDescription(description);
		}
		responseHeader.setRequestId(message.getProperty("messageId",
				PropertyScope.SESSION).toString());
		responseHeader.setResponseId(java.util.UUID.randomUUID().toString());
		responseHeader.setCreatedUtc(connectUtils
				.getDateAsXMLGregorianCalendar(new Date()));
		enterpriseResponse = new ConnectEnterpriseResponse();
		enterpriseResponse.setResponseHeader(responseHeader);
		if (null!= message.getPayload()) {
			ResponseBody responseBody = new ResponseBody();
			Node responseBodyData = createResponseBodyNodeFromPayload(message);
			responseBody.setAny(responseBodyData);
			enterpriseResponse.setResponseBody(responseBody);
		}
		return enterpriseResponse;
	}
	

	public Node createResponseBodyNodeFromPayload(MuleMessage message) {
		Node nodeValue = null;
		try {
			nodeValue = DocumentBuilderFactory
					.newInstance()
					.newDocumentBuilder()
					.parse((new InputSource(new StringReader(message
							.getPayloadAsString())))).getDocumentElement();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodeValue;

	}

}
