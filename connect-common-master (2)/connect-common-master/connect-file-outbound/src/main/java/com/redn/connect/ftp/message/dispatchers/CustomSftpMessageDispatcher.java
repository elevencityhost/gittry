package com.redn.connect.ftp.message.dispatchers;
/**
 * @author Laxshmi Maram
 *  *
 *  This java class will set all the required values for SFTP client instance
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.transport.AbstractMessageDispatcher;
import org.mule.transport.sftp.SftpClient;
import org.mule.transport.sftp.SftpConnector;
import org.mule.transport.sftp.SftpUtil;
import org.mule.transport.sftp.notification.SftpNotifier;

import com.redn.connect.ftp.util.SFTPGatewayConstants;
import com.redn.connect.processor.connectconfig.ConnectConfiguration;


public class CustomSftpMessageDispatcher extends AbstractMessageDispatcher {

	private SftpConnector connector;
	private SftpUtil sftpUtil = null;
	SftpClient client = null;

	public CustomSftpMessageDispatcher(OutboundEndpoint endpoint) {
		super(endpoint);
		connector = (SftpConnector) endpoint.getConnector();
		sftpUtil = new SftpUtil(endpoint);
	}

	@Override
	protected void doDisconnect() throws Exception {
		// no op
	}

	@Override
	protected void doDispose() {
		// no op
	}

	@Override
	protected void doDispatch(MuleEvent event) throws Exception {

		MuleMessage muleMessage = event.getMessage();
		ConnectConfiguration connectconfiguration = muleMessage
				.getInvocationProperty(SFTPGatewayConstants.DESTINATION_SERVICE_CONFIG_OBJECT);

		Boolean isFileExists = null;
		String sourceFileName = muleMessage
				.getInvocationProperty(SFTPGatewayConstants.SOURCE_FILE_NAME);
		Boolean overrideExistingFileFlag = Boolean
				.valueOf(connectconfiguration
						.get(SFTPGatewayConstants.OVERRIDE_EXISTING_REMOTE_FILE_FLAG));
		String tmpFileNamePrefix = connectconfiguration
				.get(SFTPGatewayConstants.TMP_REMOTE_FILE_NAME_PREFIX_FLAG);
		String tmpFileNameSuffix = connectconfiguration
				.get(SFTPGatewayConstants.TMP_REMOTE_FILE_NAME_PREFIX_FLAG);
		
		String tmpRemoteDirectoryPath = connectconfiguration
				.get(SFTPGatewayConstants.TMP_REMOTE_DIRECTORY_LOCATION);
		String actualRemoteDirectoryPath = connectconfiguration
				.get(SFTPGatewayConstants.REMOTE_DIRECTORY_LOCATION);
		String remoteFileAbsolutePath = actualRemoteDirectoryPath + "/"
				+ sourceFileName;
		String outputPattern = muleMessage
				.getInvocationProperty(SFTPGatewayConstants.SFTP_REMOTE_FILENAME);
		Boolean sftpAuthenticationTypePpkUsrPwdFlag = Boolean
				.valueOf(connectconfiguration
						.get(SFTPGatewayConstants.SFTP_AUTHENTICATION_TYPE_PPK_USR_PWD));
		Boolean sftpAuthenticationTypePpkUsrOnlyFlag = Boolean
				.valueOf(connectconfiguration
						.get(SFTPGatewayConstants.SFTP_AUTHENTICATION_TYPE_PPK_USR_ONLY));
		String identityFile = connectconfiguration
				.get(SFTPGatewayConstants.SFTP_PRIVATE_KEY_ABSOLUTE_PATH);
		String passphrase = connectconfiguration
				.get(SFTPGatewayConstants.SFTP_PASSPHRASE);

		// Set identity file location to connector.
		if ((null != sftpAuthenticationTypePpkUsrPwdFlag && sftpAuthenticationTypePpkUsrPwdFlag)
				|| (null != sftpAuthenticationTypePpkUsrOnlyFlag && sftpAuthenticationTypePpkUsrOnlyFlag)) {
			
			connector.setIdentityFile(identityFile);
			if (null != sftpAuthenticationTypePpkUsrPwdFlag
					&& sftpAuthenticationTypePpkUsrPwdFlag) {
				
				// Set passphrase when authentication type is with privatekey, username and passphrase 
				connector.setPassphrase(passphrase);
			}
		}

		String serviceName = (event.getFlowConstruct() == null) ? "UNKNOWN SERVICE"
				: event.getFlowConstruct().getName();
		SftpNotifier notifier = new SftpNotifier(connector, event.getMessage(),
				endpoint, serviceName);

		// Get SFTP client object
		client = connector.createSftpClient(endpoint, notifier);

		try {
			try {
				// Set true if file already exists.
				isFileExists = client.listFiles(remoteFileAbsolutePath).length > 0;
			} catch (IOException ioe) {
				isFileExists = false;
			}

			// File already exists in remote server
			if (null != isFileExists && isFileExists) {

				// If overridden configuration is true then delete existing file
				// and
				// transfer new file
				if (null != overrideExistingFileFlag
						&& overrideExistingFileFlag) {

					// Delete existing file
					client.deleteFile(remoteFileAbsolutePath);

					// Check temporary filename or directory and upload
					implTempFileOrTempDir(event, outputPattern,
							tmpFileNamePrefix, tmpFileNameSuffix, sourceFileName, 
							tmpRemoteDirectoryPath, actualRemoteDirectoryPath);
				}

				// If overridden configuration is false then throw an exception
				else if (null != overrideExistingFileFlag
						&& !overrideExistingFileFlag) {

					throw new IOException("File already exists: "+sourceFileName);
				}

				// If overridden configuration is not configured.
				else {

					throw new Exception(
							"Overriden Configuration is not available.");
				}
			} else {

				// Check temporary filename or directory and upload
				implTempFileOrTempDir(event, outputPattern, tmpFileNamePrefix,
						tmpFileNameSuffix, sourceFileName,
						tmpRemoteDirectoryPath, actualRemoteDirectoryPath);
			}
		} finally {
			if (client != null) {
				// If the connection fails, the client will be null, otherwise
				// disconnect.
				connector.releaseClient(endpoint, client);
				connector.setIdentityFile(null);
				connector.setPassphrase(null);
			}
		}

	}

	private void upload(MuleEvent event) throws Exception {

		String filename = buildFilename(event);
		InputStream inputStream = generateInputStream(event);
		if (logger.isDebugEnabled()) {
			logger.debug("Writing file to: " + endpoint.getEndpointURI() + " ["
					+ filename + "]");
		}

		boolean useTempDir = false;
		String transferFilename = null;

		try {
			String destDir = endpoint.getEndpointURI().getPath();

			if (logger.isDebugEnabled()) {
				logger.debug("Connection setup successful, writing file.");
			}

			// Duplicate Handling
			filename = client.duplicateHandling(destDir, filename,
					sftpUtil.getDuplicateHandling());
			transferFilename = filename;

			useTempDir = sftpUtil.isUseTempDirOutbound();
			if (useTempDir) {
				// TODO move to a init-method like doConnect?
				// cd to tempDir and create it if it doesn't already exist
				sftpUtil.cwdToTempDirOnOutbound(client, destDir);

				// Add unique file-name (if configured) for use during transfer
				// to
				// temp-dir
				boolean addUniqueSuffix = sftpUtil
						.isUseTempFileTimestampSuffix();
				if (addUniqueSuffix) {
					transferFilename = sftpUtil
							.createUniqueSuffix(transferFilename);
				}
			}

			// send file over sftp
			// choose appropriate writing mode
			if (sftpUtil.getDuplicateHandling().equals(
					SftpConnector.PROPERTY_DUPLICATE_HANDLING_APPEND)) {
				client.storeFile(transferFilename, inputStream,
						SftpClient.WriteMode.APPEND);
			} else {
				client.storeFile(transferFilename, inputStream);
			}

			if (useTempDir) {
				// Move the file to its final destination
				client.rename(transferFilename, destDir + "/" + filename);
			}

			logger.info("Successfully wrote file '" + filename + "' to "
					+ endpoint.getEndpointURI());
		} catch (Exception e) {
			logger.error(
					"Unexpected exception attempting to write file, message was: "
							+ e.getMessage(), e);

			sftpUtil.setErrorOccurredOnInputStream(inputStream);

			if (useTempDir) {
				// Cleanup the remote temp dir from the not fullt completely
				// transferred file!
				String tempDir = sftpUtil.getTempDirOutbound();
				sftpUtil.cleanupTempDir(client, transferFilename, tempDir);
			}
			throw e;
		} finally {
//			inputStream.close();
		}
	}

	private void implTempFileOrTempDir(MuleEvent event, String outputPattern,
			String tmpFilenamePrefix, String tmpFileNameSuffix,
			String actualFileName, String tmpDirectoryPath,
			String actualDirectoryPath) throws Exception {

		// transfer file with 'tmp' prefix and rename to actual
		// filename.
		if ((null != tmpFilenamePrefix && !tmpFilenamePrefix.equals("")) 
				|| (null != tmpFileNameSuffix && !tmpFileNameSuffix.equals(""))) {

			upload(event);
			client.rename(outputPattern, actualDirectoryPath + "/"
					+ actualFileName);

			// Transfer to temp directory and move it to actual
			// directory
		} else if (null != tmpDirectoryPath && !tmpDirectoryPath.equals("")) {

			// Upload file to temporary location and move it to actual location.
			moveTmpDirToActualDir(event, tmpDirectoryPath, actualDirectoryPath,
					actualFileName);
		} else {

			// Upload file to remote server
			upload(event);
		}
	}

	private void moveTmpDirToActualDir(MuleEvent event,
			String tmpDirectoryPath, String actualDirectoryPath,
			String actualFileName) throws Exception {

		// Upload file to remote server
		upload(event);

		// Move file from temporary location to actual location.
		client.rename(actualFileName, actualDirectoryPath + "/"
				+ actualFileName);
	}

	private InputStream generateInputStream(MuleEvent event) {
		Object data = event.getMessage().getPayload();
		// byte[], String, or InputStream payloads supported.

		byte[] buf;
		InputStream inputStream;

		if (data instanceof byte[]) {
			buf = (byte[]) data;
			inputStream = new ByteArrayInputStream(buf);
		} else if (data instanceof InputStream) {
			inputStream = (InputStream) data;
		} else if (data instanceof String) {
			inputStream = new ByteArrayInputStream(((String) data).getBytes());
		} else {
			throw new IllegalArgumentException(
					"Unexpected message type: java.io.InputStream, byte[], or String expected. Got "
							+ data.getClass().getName());
		}
		return inputStream;
	}

	@SuppressWarnings("deprecation")
	private String buildFilename(MuleEvent event) {
		MuleMessage muleMessage = event.getMessage();
		String outPattern = (String) endpoint
				.getProperty(SftpConnector.PROPERTY_OUTPUT_PATTERN);
		if (outPattern == null) {
			outPattern = (String) muleMessage.getProperty(
					SftpConnector.PROPERTY_OUTPUT_PATTERN,
					connector.getOutputPattern());
		}
		String filename = connector.getFilenameParser().getFilename(
				muleMessage, outPattern);
		if (filename == null) {
			filename = (String) event.getMessage().findPropertyInAnyScope(
					SftpConnector.PROPERTY_FILENAME, null);
		}
		return filename;
	}

	@Override
	protected MuleMessage doSend(MuleEvent event) throws Exception {
		doDispatch(event);
		return event.getMessage();
	}

}
