package com.redn.connect.ftp.message.dispatchers;
/**
 * @author Laxmi Maram
 * This java class will set all the required values for FTP client instance
 * 
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.transport.AbstractMessageDispatcher;
import org.mule.transport.NullPayload;
import org.mule.transport.ftp.FtpConnector;

import com.redn.connect.ftp.constants.FTPGatewayConstants;
import com.redn.connect.processor.connectconfig.ConnectConfiguration;

public class CustomFtpMessageDispatcher extends AbstractMessageDispatcher {

	protected final FtpConnector connector;
	FTPClient ftpClient;
	
	public CustomFtpMessageDispatcher(OutboundEndpoint endpoint) {
		super(endpoint);
        this.connector = (FtpConnector) endpoint.getConnector();
	}

	@Override
	protected void doDispatch(MuleEvent event) throws Exception {


		MuleMessage muleMessage = event.getMessage();
		ConnectConfiguration connectconfiguration = muleMessage
				.getInvocationProperty(FTPGatewayConstants.DESTINATION_SERVICE_CONFIG_OBJECT);
		
		String sourceFileName = muleMessage.getInvocationProperty(FTPGatewayConstants.SOURCE_FILE_NAME);
		Boolean overrideExistingFileFlag = Boolean.valueOf(connectconfiguration.get(FTPGatewayConstants.OVERRIDE_EXISTING_REMOTE_FILE_FLAG));
		String tmpPrefixValue = connectconfiguration.get(FTPGatewayConstants.TMP_REMOTE_FILE_NAME_PREFIX_VALUE);
		String tmpSuffixValue = connectconfiguration.get(FTPGatewayConstants.TMP_REMOTE_FILE_NAME_SUFFIX_VALUE);
		String tmpRemoteDirectoryPath = connectconfiguration.get(FTPGatewayConstants.TMP_REMOTE_DIRECTORY_LOCATION);
		String actualRemoteDirectoryPath = connectconfiguration.get(FTPGatewayConstants.REMOTE_DIRECTORY_LOCATION);
		String remoteFileAbsolutePath = actualRemoteDirectoryPath + "/" + sourceFileName;
		String outputPattern = muleMessage.getInvocationProperty(FTPGatewayConstants.FTP_REMOTE_FILENAME);
		FtpConnector ftpConnector = (FtpConnector) endpoint.getConnector();
	
		ftpClient = ftpConnector.getFtp(endpoint.getEndpointURI());
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.changeWorkingDirectory("/");
		
		// Set true if file already exists.
		Boolean isFileExists = ftpClient.listFiles(remoteFileAbsolutePath).length > 0;
		
		// File already exists in remote server
		if (null != isFileExists && isFileExists) {

			// If overridden configuration is true then delete existing file and
			// transfer new file
			if (null != overrideExistingFileFlag && overrideExistingFileFlag) {
				if(!ftpClient
						.deleteFile(remoteFileAbsolutePath)){
					throw new Exception("'"+remoteFileAbsolutePath+"' file was not able delete while overriding wil new file.");
				}
				
				implTempFileOrTempDir(event, outputPattern, tmpPrefixValue,
						sourceFileName, tmpSuffixValue, tmpRemoteDirectoryPath,
						actualRemoteDirectoryPath);
			}
		
			// If overridden configuration is false then throw an exception
			else if (null != overrideExistingFileFlag && !overrideExistingFileFlag) {

				throw new IOException("File already exists: "+remoteFileAbsolutePath);
			}
			
			// If overridden configuration is not configured.
			else {
				throw new IOException("File already exists: "+remoteFileAbsolutePath+". "+FTPGatewayConstants.OVERRIDE_EXISTING_REMOTE_FILE_FLAG+"' property is not configured.");
			}
		} else {

			implTempFileOrTempDir(event, outputPattern, tmpPrefixValue,
					sourceFileName, tmpSuffixValue, tmpRemoteDirectoryPath,
					actualRemoteDirectoryPath);
		}
	}

	@Override
	protected MuleMessage doSend(MuleEvent event) throws Exception {
        doDispatch(event);
        return new DefaultMuleMessage(NullPayload.getInstance(), getEndpoint().getMuleContext());
    }

	public void upload(MuleEvent event) throws Exception {

		Object data = event.getMessage().getPayload();
		OutputStream out = connector.getOutputStream(getEndpoint(), event);

		try {
			if (data instanceof InputStream) {
				InputStream is = ((InputStream) data);
				IOUtils.copy(is, out);
//				is.close();
			} else {
				byte[] dataBytes;
				if (data instanceof byte[]) {
					dataBytes = (byte[]) data;
				} else {
					dataBytes = data.toString().getBytes(event.getEncoding());
				}
				IOUtils.write(dataBytes, out);
			}
		} finally {
			out.close();
		}
	}

	private void implTempFileOrTempDir(MuleEvent event, String outputPattern,
			String tmpPrefixValue, String actualFileName,
			String tmpSuffixValue, String tmpRemoteDirectoryPath,
			String actualRemoteDirectoryPath) throws Exception {
		
		// transfer file with 'tmp' prefix and rename to actual
		// filename.
		if ((null != tmpPrefixValue && !tmpPrefixValue.equals(""))
				||null != tmpSuffixValue && !tmpSuffixValue.equals("")) {

			upload(event);
			if (!ftpClient.rename(actualRemoteDirectoryPath + "/" + outputPattern,
					actualRemoteDirectoryPath + "/" + actualFileName)) {
				// Throw an exception if unable to rename from tmp filename to
				// actual
				// filename
				throw new Exception("Unable to rename file from "
						+ actualRemoteDirectoryPath + "/" + outputPattern + " to "
						+ actualRemoteDirectoryPath + "/" + actualFileName);
			}

			// Transfer to temp directory and move it to actual
			// directory
		} else if ((null != tmpRemoteDirectoryPath && !tmpRemoteDirectoryPath.equals(""))) {

			moveTmpDirToActualDir(event, tmpRemoteDirectoryPath, actualRemoteDirectoryPath,
					actualFileName);
		} else {
			upload(event);
		}
	}

	private void moveTmpDirToActualDir(MuleEvent event,
			String tmpRemoteDirectoryPath, String actualRemoteDirectoryPath,
			String actualFileName) throws Exception {
		upload(event);
		if (!ftpClient.rename(tmpRemoteDirectoryPath + "/" + actualFileName,
				actualRemoteDirectoryPath + "/" + actualFileName)) {

			// Throw an exception if unable to move from tmp location to actual
			// target location
			throw new Exception("Unable move file from " + tmpRemoteDirectoryPath
					+ "/" + actualFileName + " to " + actualRemoteDirectoryPath + "/"
					+ actualFileName);
		}
	}
}

