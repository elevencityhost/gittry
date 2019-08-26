package com.redn.connect.as2.constants;

/**
 * @author Vinay Kumar Thota
 * 
 * This class contains constants used for this project
 *
 */
public class AS2Constants {

	public static final String ENTERPRISE_MESSAGE = "enterpriseMessage";
	public static final String SERVICE_NAME = "connect-as2";
	public static final String CONST_ACTION_AS2 = "AS2";
	
	public static final String VAR_MESSAGE_ID  = "messageId";
	public static final String VAR_RESOURCE_ID  = "resourceId";
	public static final String VAR_MESSAGE_SOURCE  = "messageSource";
	public static final String VAR_MESSAGE_ACTION  = "messageAction";
	public static final String VAR_SERVICE_NAME  = "serviceName";
	public static final String VAR_TARGET_SYSTEM  = "targetSystem";
	public static final String VAR_SOURCE_SYSTEM  = "sourceSystem";
	public static final String VAR_MESSAGE_TYPE  = "message_type";
	public static final String VAR_SYSTEM  = "system";
	public static final String VAR_AS2_FROM  = "as2-from";
	public static final String VAR_ORGNL_MESSAGE_ID  = "originalMessageId";
	public static final String VAR_DISPOSITION  = "disposition";

	public static final String VAR_DIRECTION  = "direction";
	public static final String VAR_BACKUP_FOLDER  = "backupFileFolder";
	public static final String VAR_BACKUP_SUBFOLDER  = "backupFileSubFolder";
	public static final String VAR_BACKUP_FILE_PATH  = "backupFilePath";
	public static final String VAR_BACKUP_FILE_NAME  = "backupFileName";
	public static final String VAR_BACKUP_FILE_PREFIX  = "backupFilePrefix";
	public static final String VAR_BACKUP_FILE_EXTN  = "backupFileExtn";
	public static final String VAR_IS_APIMGR_CALL_SUCCESS  = "isAPIManagerCallSuccessful";
	public static final String VAR_BACKUP_FILE_NAME_RECEIVE = "backupFileNameReceive";
	
	
	public static final String VAR_TARGET_SYSTEM_SEND_FLOW  = "targetSystemSendFlow";
	
	public static final String BEAN_CONNECT_CONFIG = "connectConfigBean";
	
	public static final String PROP_SOURCE_SYSTEM_PREFIX = "connect.as2.source.";
	public static final String PROP_MESSAGE_TYPE_PREFIX = "connect.as2.message-type.";
	public static final String PROP_SUPPORTED_TARGET_SYSTEMS = "connect.as2.supported.target.systems";
	public static final String PROP_BACKUP_FILE_EXTN  = "connect.as2.backup.file.extension.";
	public static final String PROP_BACKUP_FILE_PREFIX = "connect.as2.backup.file.prefix.";

	
	public static final String TARGET_SYSTEM_SEND_FLOW_SUFFIX  = "-send-flow";
	
	public static final String CONST_EDIFACT  = "edifact";
	public static final String CONST_SYSTEM_LENOVO  = "lenovo";
	public static final String CONST_DIRECTION_INBOUND  = "Inbound";
	public static final String CONST_DIRECTION_OUTBOUND  = "Outbound";
	public static final String CONST_INBOUND_SUBFOLDER_ALL = "All";
	public static final String CONST_ORGNL_MSGID = "Original-Message-ID:";
	public static final String CONST_DISPOSITION = "Disposition:";

	public static final String CONST_EXCEPTION_ORIGIN_CONNECT_AS2 = "Connect AS2 component";
	public static final String CONST_EXCEPTION_ORIGIN_LENOVO_AS2_HTTPS = "Lenovo-AS2-Https";
	public static final String CONST_EXCEPTION_ORIGIN_IBM_AS2_HTTPS = "Lenovo-AS2-Https";
	public static final String CONST_EXCEPTION_ORIGIN_REDHAT_AS2_HTTPS = "Redhat-AS2-Https";
	public static final String CONST_EXCEPTION_ORIGIN_HP_AS2_HTTPS = "HP-AS2-Https";
	public static final String CONST_EXCEPTION_ORIGIN_WDC_AS2_HTTPS = "WDC-AS2-Https";
	
	public static final String CONST_EXCEPTION_ORIGIN_PARTNER_SYSTEM = "Partner System";
	
	public static final String HTTP_HEADER_AS2_FROM_CC = "AS2-From";
	
	public static final String ERROR_CODE_FILTER_UNACCEPTED			  = "400009100";
	public static final String ERROR_CODE_AS2_FROM_NOT_PRESENT 		  = "400009110";
	public static final String ERROR_CODE_SOURCE_SYSTEM_DERIVATION 	  = "400009120";
	
	
	public static final String ERROR_CODE_TARGET_SYSTEM_NOT_PRESENT   = "500009100";
	public static final String ERROR_CODE_TARGET_SYSTEM_NOT_SUPPORTED = "500009110";
	public static final String ERROR_CODE_LENOVO_AS2_COMMUNICATION	  = "500009120";
	public static final String ERROR_CODE_IBM_AS2_COMMUNICATION	 	  = "500009130";
	public static final String ERROR_CODE_REDHAT_AS2_COMMUNICATION	  = "500009140";
	public static final String ERROR_CODE_FAILURE_MDN				  = "500009150";
	public static final String ERROR_CODE_MESSAGE_TYPE_DERIVATION 	  = "500009160";
	public static final String ERROR_CODE_CONNECT_API_MANAGER_COMMUNICATION  = "500009500";
	public static final String ERROR_CODE_OTHER 					  = "500009600";
	
	public static final String ERROR_CODE_HP_AS2_COMMUNICATION	  = "500009170";
	public static final String ERROR_CODE_WDC_AS2_COMMUNICATION	  = "500009180";
}
