package com.redn.connect.ftps.outbound.constants;

public class FTPSOutboundConstants {

	public final static String VAR_STREAM_OBJECT = "fileInputStreamObject";
	public final static String VAR_FILE_NAME = "fileNameReceived";
	public final static String VAR_FTPS_PORT_NUMBER = "ftpsPortNumber";
	public static final String VAR_TARGET_SYSTEM_SEND_FLOW = "targetSystemFlow";
	public static final String VAR_TARGET_SYSTEM_SEND_FLOW_SUFFIX = "-ftps-send-flow";
	public static final String VAR_ARCHIVING_FILE_NAME_Pattern = "archivingFileNamePattern";

	public final static String LIST_OF_FILE_LOCATIONS = "listOfFileLocations";
	public final static String PROPERTY_FILE_SUFFIX = "connect_config_";
	public final static String PROPERTY_FTPS_PREFIX = "_ftps";
	public final static String PROPERTY_FILE_FROM_ZUUL = "propertyFileObjFromZUUL";

	public static final String ERROR_CODE_FILTER_UNACCEPTED = "400021100";
	public static final String ERROR_CODE_OTHER = "500021100";
	public static final String ERROR_CODE_FTPS_COMMUNICATION = "500021110";
	public final static String ERROR_CODE_CUSTOM_PROPS_OBJ_EMPTY = "400021110";
	public final static String ERROR_CODE_CONTENTS_LIST_EMPTY = "400021120";
	public final static String ERROR_CODE_FILE_LOCATIONS_LIST_EMPTY = "400021130";
	public final static String ERROR_CODE_FILE_LOCATIONS_LIST_NAME_EMPTY = "400021140";
	public final static String ERROR_CODE_TARGET_SYSTEM_IS_EMPTY = "400021150";
	public final static String ERROR_CODE_FILE_NOT_EXIST = "400021160";
	public final static String ERROR_CODE_ARCHIVING_LOCATION_NOT_EXIST = "400021170";
	public final static String ERROR_CODE_CUSTOM_OBJ_NOT_FOUND = "400021180";

	public final static String ERROR_CODE_CUSTOM_OBJ_NOT_FOUND_DESC = "Custom obj not found in received CEM";
	public final static String ERROR_CODE_ARCHIVING_LOCATION_NOT_EXIST_DESC = "archiving location is empty"; 
	public final static String ERROR_CODE_FILE_NOT_EXIST_DESC = "file not exist in given location";
	public final static String ERROR_CODE_TARGET_SYSTEM_IS_EMPTY_DESC = "target system is empty";
	public final static String ERROR_FILE_LOCATIONS_LIST_NAME_EMPTY_DESC = "filelocation is empty or wrong";
	public final static String ERROR_FILE_LOCATIONS_LIST_EMPTY_DESC = "custom object is empty in CEM";
	public final static String ERROR_CODE_CUSTOM_PROPS_OBJ_EMPTY_DESC = "custom object is empty in CEM";
	public final static String ERROR_CONTENTS_LIST_EMPTY_DESC = "contents object is empty in CEM";
	public static final String ERROR_CODE_FTPS_COMMUNICATION_DESC = "Error In Communicating with FTPS Server";
	public static final String ERROR_CODE_FTPS_COMMUNICATION_ORIGIN = "FTPS Server";
}
