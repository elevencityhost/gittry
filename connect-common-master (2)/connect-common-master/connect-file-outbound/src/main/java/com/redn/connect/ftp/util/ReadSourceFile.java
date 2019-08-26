package com.redn.connect.ftp.util;
/**
 * @author Laxshmi Maram
 *  *
 *  This class will read the file content from given source absolute path and
 *  set it to the flowVars
 */

import java.io.File;
import java.io.FileInputStream;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

public class ReadSourceFile implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage muleMessage = eventContext.getMessage();
		File file = new File(muleMessage
				.getProperty(SFTPGatewayConstants.SOURCE_ABSOLUTE_FILE_PATH
				, PropertyScope.SESSION).toString());
		FileInputStream fileInputStream = new FileInputStream(file);
		muleMessage.setInvocationProperty(SFTPGatewayConstants.SOURCE_FILE_INPUT_STREAM_OBJECT, fileInputStream);
		
		return fileInputStream;
	}
}

