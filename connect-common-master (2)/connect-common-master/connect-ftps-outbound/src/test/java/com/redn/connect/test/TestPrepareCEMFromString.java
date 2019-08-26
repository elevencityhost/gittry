package com.redn.connect.test;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

public class TestPrepareCEMFromString implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage muleMessage = eventContext.getMessage();

		File inputXML = new File(
				getClass().getClassLoader().getResource(muleMessage.getInvocationProperty("cemAsString")).getFile());
		FileInputStream fisTargetFile = new FileInputStream(inputXML);

		String cemAsString = IOUtils.toString(fisTargetFile, "UTF-8");

		return cemAsString;
	}

}
