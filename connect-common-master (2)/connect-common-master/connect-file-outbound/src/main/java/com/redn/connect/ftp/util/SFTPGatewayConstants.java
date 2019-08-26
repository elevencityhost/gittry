package com.redn.connect.ftp.util;
/**
 * @author Laxshmi Maram
 *  *
 *  This class is used to declare constants for the SFTP Gateway 
 */
public class SFTPGatewayConstants {

	public static final String SERVICE_CONFIG_BEAN =  "sftpGatewayServiceConfigBean"; 
	public static final String SOURCE_ABSOLUTE_FILE_PATH = "sourceAbsoluteFilePath";
	public static final String SOURCE_FILE_INPUT_STREAM_OBJECT = "sourceFileInputStreamObject";
	public static final String DESTINATION_SERVICE_CONFIG_OBJECT="destinationServiceConfigObj";
	
	public static final String OVERRIDE_EXISTING_REMOTE_FILE_FLAG = "override.existing.remotefile.flag";
	public static final String TMP_REMOTE_FILE_NAME_PREFIX_FLAG = "tmp.remote.fileName.prefix.value";
	public static final String TMP_REMOTE_FILE_NAME_SUFFIX_FLAG = "tmp.remote.fileName.suffix.value";
	public static final String TMP_REMOTE_DIRECTORY_FLAG = "tmp.remotedirectory.flag";
	public static final String TMP_REMOTE_DIRECTORY_LOCATION = "tmp.remotedirectory.location";
	public static final String SOURCE_DIRECTORY_LOCATION = "sourceDirectory";
	public static final String REMOTE_DIRECTORY_LOCATION = "remoteDirectory";
	public static final String SFTP_AUTHENTICATION_TYPE_PPK_USR_PWD = "sftp.authenticationtype.privatekey.username.password";
	public static final String SFTP_AUTHENTICATION_TYPE_PPK_USR_ONLY = "sftp.authenticationtype.privatekey.username";
	public static final String SFTP_PRIVATE_KEY_ABSOLUTE_PATH = "sftp.privatekey.absolute.path";
	public static final String SFTP_PASSPHRASE = "password";
	
	public static final String SOURCE_FILE_NAME = "sourceFileName";
	public static final String SFTP_REMOTE_DIRECTORY_LOCATION="sftpRemoteDirectoryLocation";
	public static final String SFTP_REMOTE_FILENAME="sftpRemoteFileName";
	
	public static final String SFTP_DESTINATION_PROPERTIES_FILE_PREFIX = "connect_config_";
	public static final String SFTP_DESTINATION_PROPERTIES_FILE_SUFFIX = "_sftp.properties";
	
	public static final String LOGGER_MESSAGE_ID = "messageId";
	public static final String LOGGER_MESSAGE_SOURCE = "messageSource";
	public static final String LOGGER_MESSAGE_ACTION = "messageAction";
	public static final String LOGGER_RESOURCE_ID = "resourceId";
	public static final String LOGGER_RESOURCE_NAME = "resourceName";
	
	public static final String FAILED_SFTP_FILE_DETAILS = "failedSftpedFileDetails";
	
	public static final String RETYABLE_FLAG = "retryableFlag"; 
	public static final String SERVICE_NAME = "SFTP GATEWAY";
	public static final String ACTUAL_PAYLOAD = "actualPayload";

}
