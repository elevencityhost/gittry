package com.redn.connect.processor;

import java.io.File;
import java.util.List;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.exception.Category;
import com.redn.connect.exception.ConnectException;
import com.redn.connect.ftp.constants.FTPConstants;
import com.redn.connect.processor.connectconfig.ConnectConfiguration;
import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.Contents;
import com.redn.connect.vo.CustomProps;
import com.redn.connect.vo.EnterpriseHeader.Custom;

public class CEMWithCustomProperties implements Callable {

	ConnectUtils utils = new ConnectUtils();

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		ConnectConfiguration connectConfiguration = eventContext.getMuleContext().getRegistry()
				.lookupObject("connectConfigBean");
		MuleMessage message = eventContext.getMessage();
		ConnectEnterpriseMessage cemWithCustomProps = message
				.getInvocationProperty(FTPConstants.CONNECT_ENTERPRISE_MESSAGE);
		List<String> allFiles = message.getInvocationProperty(FTPConstants.LIST_OF_FILES);
		//String stagingPath = connectConfiguration.get(FTPConstants.CONNECT + FTPConstants.FTPINBOUND + FTPConstants.LOCAL_DIR);
		
		String serviceName = cemWithCustomProps.getEnterpriseHeader().getServiceName();
		String stagingPath = connectConfiguration.get(FTPConstants.CONNECT + serviceName + FTPConstants.LOCAL_DIR);
		
		

		if(null == stagingPath || (stagingPath.isEmpty())){
			
			throw new ConnectException(FTPConstants.ERROR_CODE_STAGING_CONFIG_MISSING,
					FTPConstants.ERROR_STAGING_CONFIG_MISSING, Category.DATA, FTPConstants.MULE);
		}

		if ((null != allFiles) && allFiles.size() > 0) {

			StringBuilder filenames = new StringBuilder();
			for (String fileName : allFiles) {

				filenames.append(stagingPath + File.separator + fileName);
				filenames.append(",");

			}

			Custom custom = new Custom();
			CustomProps customprops = new CustomProps();
			Contents contents = new Contents();

			contents.setName(ConnectConstants.VAR_ATTACHMENT_PATH);
			contents.setValue((filenames.substring(0, filenames.length() - 1)));
			customprops.getContents().add(contents);

			custom.setAny(customprops);

			cemWithCustomProps.getEnterpriseHeader().setCustom(custom);

		}
		return cemWithCustomProps;
	}
}
