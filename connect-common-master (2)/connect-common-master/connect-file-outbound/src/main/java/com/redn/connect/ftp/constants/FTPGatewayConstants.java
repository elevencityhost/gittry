package com.redn.connect.ftp.constants;

import java.io.File;

/**
 * @author Laxshmi Maram
 *  *
 *  This java class includes the constants used in the file outbound
 */
public class FTPGatewayConstants 
{
	public static final String SERVICE_CONFIG_BEAN =  "ftpGatewayServiceConfigBean"; 
	public static final String SOURCE_ABSOLUTE_FILE_PATH = "sourceAbsoluteFilePath";
	public static final String SOURCE_FILE_INPUT_STREAM_OBJECT = "sourceFileInputStreamObject";
	public static final String DESTINATION_PROPERTY_PREFIX = "connect.ftp.gateway.destination.";
	public static final String DESTINATION_SERVICE_CONFIG_OBJECT="destinationServiceConfigObj";
	
	public static final String OVERRIDE_EXISTING_REMOTE_FILE_FLAG = "override.existing.remotefile.flag";
	public static final String TMP_REMOTE_FILE_NAME_PREFIX_VALUE = "tmp.remote.fileName.prefix.value";
	public static final String TMP_REMOTE_FILE_NAME_SUFFIX_VALUE = "tmp.remote.fileName.suffix.value";
	public static final String TMP_REMOTE_DIRECTORY_LOCATION = "tmp.remotedirectory.location";
	public static final String SOURCE_DIRECTORY_LOCATION = "sourceDirectory";
	public static final String REMOTE_DIRECTORY_LOCATION = "remoteDirectory";
	
	public static final String SOURCE_FILE_NAME = "sourceFileName";
	public static final String FTP_REMOTE_DIRECTORY_LOCATION="ftpRemoteDirectoryLocation";
	public static final String FTP_REMOTE_FILENAME="ftpRemoteFileName";

	public static final String FTP_DESTINATION_PROPERTIES_FILE_PREFIX = "connect_config_";
	public static final String FTP_DESTINATION_PROPERTIES_FILE_SUFFIX = "_ftp.properties";
	
	public static final String LOGGER_MESSAGE_ID = "messageId";
	public static final String LOGGER_MESSAGE_SOURCE = "messageSource";
	public static final String LOGGER_MESSAGE_ACTION = "messageAction";
	public static final String LOGGER_RESOURCE_ID = "resourceId";
	public static final String LOGGER_RESOURCE_NAME = "resourceName";
	
	public static final String FAILED_FTP_FILE_DETAILS = "failedFtpedFileDetails";
	
	public static final String RETYABLE_FLAG = "retryableFlag"; 
	public static final String SERVICE_NAME = "FTP GATEWAY";
	public static final String ACTUAL_PAYLOAD = "actualPayload";
	
	
	public static final String PRPHOST ="prp_host";
	public static final String PRPPORT ="prp_port";
	public static final String PRPUSER="prp_user";
	public static final String PRPPASSWPRD="prp_password";
	public static final String PRPREMOTEDIRECTORY="prp_remote_directory";
	public static final String PRPPROTOCOL="prp_protocol";
	public static final String PRPPPK="prp_ppk";
	public static final String ALLSOURCEFILENAME="allSourceFileNames";
	public static final String SOURCEFILENAMELIST="sourceFileNameList";
	public static final String EXCEPTIONQUEUENAME="exception_queue_name";
	
	
	public static final String VAR_ERROR_CODE = "errorCode";
	public static final String VAR_ERROR_DESCRIPTION = "errorDescription";
	public static final String ERROR_CODE_FILTER_UNACCEPTED = "400012100";
	public static final String ERROR_CODE_UNACCEPTED = "400012110";
	public static final String ERROR_ACTIVEMQ = "400012120";
	public static final String ERROR_HANDLEOTHERCODE = "400012130";
	
	public static final String ERROR_FTP_COMMUNICATION ="500012140";
	
	public static final String fileSeperator = File.separator;
	
	
	
}
