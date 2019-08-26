package com.redn.connect.transformer;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
/**
 * 
 * @author Shruthi Kolloju
 * 
 * Removing "\" from the Response payload
 *
 */
public class ModifyResponsePayload implements Callable {

	
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		String xmlStr = null;
		String payload = (String) eventContext.getMessage().getPayload();
		if (payload.startsWith("\"")) {
			String payloadAfterReplacement = payload.replaceFirst("\"", "");
			xmlStr = payloadAfterReplacement.substring(0, payloadAfterReplacement.length() - 1);
		}else{
		
			xmlStr = (String) eventContext.getMessage().getPayload();
		}
		
		String xmlStrAfterReplacement = xmlStr.replaceAll("\\\\\"", "\"");
		Source xmlInput = new StreamSource(new StringReader(xmlStrAfterReplacement));
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		final ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(xmlInput, new StreamResult(streamOut));

		return streamOut;
	}
}
