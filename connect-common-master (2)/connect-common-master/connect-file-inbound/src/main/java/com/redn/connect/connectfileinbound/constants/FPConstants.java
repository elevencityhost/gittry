package com.redn.connect.connectfileinbound.constants;
/**
 * @author Sai Prasad Jonnala
 * 
 * The java class includes all the constants needed 
 */
public interface FPConstants {
	
	public static final String DATE_FORMAT = "yyyyMMdd.HHmmss";

	public static final String ORIGINAL_FILENAME = "originalFileName";

	public static final String DIRECTORY = "directory";

	public static final String TIMESTAMP = "timestamp";

	public static final String FILE_SIZE = "fileSize";
	
	public static final String  INBOUND = "-INBOUND";
	
	public static final String SERVICE_NAME = "Connect File Inbound Service";

	public static final String TARGET_DIRECTORY = "targetDirectory";
	
	public static final String DEFAULT_DIRECTORY = "/ConnectFileInbound/ES/Default";
	
	public static final String PUBLISH = "Publish";
	
	public static final String VAR_ENTERPRISE_MESSAGE  = "enterpriseMessage";

	public static final String DOT=".";
	public static final String OUTPUT_PROPS="outputProps";
	public static final String INTERFACE_DESTINATION_PROPERTIES_FILE_SUFFIX = ".properties";
	public static final String INVALID_FILENAME = "Invalid file format. Please check your input fileName. Expected format is <INTERFACE_ID>.<COUNTRY_ID>.<SEQUENCE_NUMBER>.<DATE>.<TIME>.<EXTENSION>";
	public static final String POSITION_NOT_ZERO = "The position value should be greate then zero ";
	public static final String INVALID_POSITION = "Invalid position value. The position value is out of index";
	public static final String VAR_ERROR_CODE = "errorCode";
	public static final String VAR_ERROR_DESCRIPTION = "errorDescription";
	public static final String ERROR_CODE_FILTER_UNACCEPTED = "400017100";
	public static final String ERROR_CODE_UNACCEPTED = "400017100";
	public static final String ERROR_ACTIVEMQ = "400013130";
	public static final String ERROR_HANDLEOTHERCODE = "400101110";
	
	
	public static final String MESSAGEID="messageid";
	public static final String MESSAGEACTION="messageaction";
	public static final String MESSAGESCOUCE="messagesource";
	public static final String RESOURCEID="resourceid";
	public static final String RESOURCENAME="resourceName";
}
