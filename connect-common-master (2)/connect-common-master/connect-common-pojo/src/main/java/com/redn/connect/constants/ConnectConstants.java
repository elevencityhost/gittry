package com.redn.connect.constants;

import java.util.Locale;

public interface ConnectConstants {

	// Error Messages

	public static final String ERROR_GENERIC_MSG = "Error Occured while processing";

	public static final String ERROR_MSG_MANDATORY_VALUE_MISSING = "Mandatory Value: %s missing to form UNG/UNE segment";

	public static final String ERROR_MSG_MAX_LENGTH_EXCEEDED = "Length of field:%s exceeded, Max length:%s";
	
	public static final String ERROR_MSG_FILEPATH_WITH_FILENAME_MISSING = "%s cannot be null or empty";
	
	public static final String ERROR_MSG_CONTENT_OR_FILEPATH_MISSING = "%s or %s is null or empty to copy the content to a location";

	// UNG Fields

	public static final String UNG_FIELD_GROUP_ID = "groupId";

	public static final String UNG_FIELD_SENDER_ID = "senderId";

	public static final String UNG_FIELD_SENDER_CODE_QUALIFIER = "senderCodeQualifier";

	public static final String UNG_FIELD_RECIPIENT_ID = "recipientId";

	public static final String UNG_FIELD_RECIPIENT_CODE_QUALIFIER = "recipientCodeQualifier";

	public static final String UNG_FIELD_DATE = "date";

	public static final String UNG_FIELD_TIME = "time";

	public static final String UNG_FIELD_GROUP_REFERENCE_NUMBER = "groupReferenceNumber";

	public static final String UNG_FIELD_CONTROLLING_AGENCY = "controllingAgency";

	public static final String UNG_FIELD_MESSAGE_VERSION = "messageVersion";

	public static final String UNG_FIELD_RELEASE_NUMBER = "releaseNumber";
	
	public static final String UNG_FIELD_ASSOCIATION_ASSIGNED_CODE = "associationAssignedCode";
	

	// Common Variables

	public static final String VAR_ENTERPRISE_MESSAGE = "enterpriseMessage";

	public static final String VAR_MESSAGE_ID = "messageId";

	public static final String VAR_RESOURCE_ID = "resourceId";

	public static final String VAR_MESSAGE_SOURCE = "messageSource";

	public static final String VAR_MESSAGE_ACTION = "messageAction";

	public static final String VAR_SERVICE_NAME = "serviceName";

	public static final String VAR_TARGET_SYSTEM = "targetSystem";

	public static final String VAR_SOURCE_SYSTEM = "sourceSystem";

	public static final String VAR_ERROR_CODE = "errorCode";

	public static final String VAR_ERROR_DESCRIPTION = "errorDescription";

	public static final String VAR_ERROR_CATEGORY = "errorCategory";

	public static final String VAR_ERROR_ORIGIN = "errorOrigin";

	public static final String VAR_RETRYABLE = "isRetryable";

	public static final String VAR_DOCNUM = "DOCNUM";

	public static final String VAR_COMMUNICATION = "communication";

	public static final String VAR_IDOC_SOURCE_TYPE = "idocSourceType";

	public static final String VAR_REDHAT_DISTRIBUTION_VALUE = "redhat.distribution";

	public static final String VAR_LENOVO_SERVICES_VALUE = "lenovo.services";

	public static final String VAR_PRIORITY = "priority";

	public static final String VAR_ATTACHMENT_PATH = "attachmentPath";

	public static final String VAR_REQUEST_ID = "requestID";

	public static final String VAR_APPLICATION_ID = "applicationID";

	public static final String VAR_EMAIL_MESSAGE_AVAILABLE = "EMAIL_MESSAGE_AVAILABLE";

	public static final String VAR_SMS_MESSAGE_AVAILABLE = "SMS_MESSAGE_AVAILABLE";
	
	public static final String VAR_FTP_EMAIL_SEND = "sendEmail";

	public static final String VAR_FTP_OUTBOUND_FILES_PATH = "ftpOutboundFilesPath";
	// Constants

	public static final String CONST_ERROR = "ERROR";

	public static final String CONST_LOCALHOST = "localhost";

	public static final String CONST_UNKNOWN = "UNKNOWN";

	public static final String CONST_MESSAGE_ENTERPRISE_MESSAGE_VALIDATION_FAILED = "Enterprise Message Validation failed";

	public static final String CONST_EXCEPTION_ORIGIN_MULE_ESB_CONNECTHTTP = "Mule ESB (Connect Http)";

	public static final String CONST_EXCEPTION_ORIGIN_MULE_ESB_NS = "Mule ESB (Native Service)";

	public static final String CONST_EXCEPTION_ORIGIN_SAP = "SAP";

	public static final String CONST_EXCEPTION_ORIGIN_PARTNER = "Partner";

	public static final String CONST_EXCEPTION_ORIGIN_MULE_HTTP_CONNECT = "Mule ESB (Http Connect)";

	public static final String CONST_EXCEPTION_ORIGIN_MULE_API_MANAGER = "Mule ESB (Connect API Manager)";
	
	public static final String CONST_EXCEPTION_ORIGIN_MULE_ESB_CONNECT_HTTP_API = "Mule ESB (Connect Http Api)";

	public static final String CONST_EXCEPTION_ORIGIN_ACTIVEMQ = "ACTIVEMQ";

	public static final String CONST_EXCEPTION_ORIGIN_SMTP = "SMTP server";

	public static final String CONST_DOCUMENT_NUMBER = "DOCNUM";

	public static final String CONST_CRE_DAT = "CREDAT";

	public static final String CONST_CRE_TIM = "CRETIM";
	
	public static final String CONST_UNB_0201 = "senderId";

	public static final String CONST_UNB_0301 = "recipientId";

	public static final String CONST_UNB_0302 = "qualifierCode";

	public static final String SHIP_TIME = "shipTime";

	public static final String TIME_ZONE = "timeZone";

	public static final String ENTERPRISE_MESSAGE = "enterpriseMessage";

	public static final String PARTNER_ID = "partnerID";

	public static final String REFERENCE_NUMBER = "referenceNumber";

	public static final String CONST_EXCEPTION_ORIGIN_FTP = "FTP";

	public static final String CONST_INTERFACE_NAME = "interface";

	public static final String CONST_FTP_PROTOCOL = "protocol";

	public static final String CONST_SUCCESS = "SUCCESS";
	
	public static final String CONST_FAILURE = "FAILURE";
	
	public static final String CONST_FTP = "ftp";
	
	

	public static final String FILEPATH_WITH_FILENAME = "filePathwithFileName";

	public static final String DATA = "Data";

	public static final String FILE_PATH = "FilePath";

	public static final String CONST_CRE_DATE_TIME = "dateTime";

	public static final String UNB_RECIPIENT_ID = "getUNBrecipientId";


	

}
