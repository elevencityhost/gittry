package com.redn.connect.constants;

/**
 * @author SaiPrasad.Jonnala
 * 
 *
 * This class contains the constants for Connect Notifier component
 */
public class NotifierConstants {

	public static final String VAR_EMAIL_CONTENT_TYPE = "connect.notifier.email.contentType";
	public static final String VAR_EMAIL_SUBJECT = "connect.notifier.email.subject";
	public static final String VAR_EMAIL_TO_LIST = "connect.notifier.email.to.list";
	public static final String VAR_EMAIL_CC_LIST = "connect.notifier.email.cc.list";
	public static final String VAR_SERVICE_NAME = "serviceName";
	public static final String VAR_PROPERTY_KEY_SEPERATOR = ".";
	public static final String VAR_CONNECT_NOTIFIER = "connect.notifier.";
	public static final String VAR_CONNECT_NOTIFIER_TEMPLATE = "connect.notifier.template.";
	public static final String VAR_PATTERN_OF_PUBLIC_URL_IMAGE = "<image.*.publicurl>";
	public static final String VAR_SMTP_TO_LIST = ".smtp.toList";
	public static final String VAR_SMTP_CC_LIST = ".smtp.ccList";
	public static final String VAR_SMTP_BCC_LIST = ".smtp.bccList";
	public static final String VAR_SMTP_SUBJECT = ".smtp.subject";
	public static final String VAR_SMTP_REPLY_TO = ".smtp.replyto";
	public final static String VAR_IMAGEID_ZUUL = ".image%d.publicurl";
	public final static String VAR_STATUS_SUCCESS = "SUCCESS";
	public static final String VAR_STATUS_FAILURE = "FAILED";
	public static final String VAR_EMPTY = "";
	public static final String VAR_EMAIL_MESSAGE_AVAILABLE = "isEmailMessageAvailable";
	public static final String VAR_SMS_MESSAGE_AVAILABLE = "isSMSMessageAvailable";

	public static final String PROP_EMAIL_FROM_ADDRESS = "connect.notifier.email.from.address";

	public static final String CONNECT_NOTIFIER_PREFIX = "connect.notifier";
	public static final String CONST_SMTP_CONFIGURATION_ERROR = "SMTP configuration error";
	public static final String UNSUPPORTED_PAYLOAD_ERROR_CODE = "400014130";
	public static final String SMTP_SEND_FAILED_EXCEPTION = "400014100";

	public static final String ERROR_CODE_JMS = "400014210";
	public static final String ERROR_CODE_CONNECT_EXCEPTION = "400014220";
	public static final String ERROR_CODE_APPLICATION_ID_UNAVAILABLE = "400014230";
	public static final String ERROR_CODE_RECIPIENT_LIST_UNAVAILABLE = "400014240";
	public static final String ERROR_CODE_TEMPLATE_FILE_UNAVAILABLE = "400014250";
	public static final String ERROR_CODE_SMTP_FAILED = "400014260";
	public static final String ERROR_CODE_SERVICE_NAME_UNAVAILABLE = "400014270";
	public static final String ERROR_CODE_INVALID_EMAIL_ADDRESS = "400014280";
	public static final String ERROR_CODE_SUBJECT_EMPTY = "400014290";
	public static final String ERROR_CODE_REQUEST_ID_EMPTY = "400014300";
	public static final String ERROR_CODE_INVALID_JSON_DATA_RECEIVED = "400014310";
	public static final String ERROR_CODE_EMAIL_AND_SMS_ARE_NULL = "400014320";
	public static final String ERROR_CODE_COMMON_ATTRIBUTES_MESSAGE_NULL = "400014330";
	public static final String ERROR_CODE_COMMON_APPLICATION_ID_NOT_REGISTERED_ON_MULE = "400014340";
	public static final String ERROR_CODE_OTHER = "400014350";

	public static final String SUCCESS_CODE = "200";
	public static final String ERROR_CODE_BAD_REQUEST = "400";
	public static final String ERROR_CODE_BAD_REQUEST_INTERNAL_SERVER = "500";

	public static final String SUCCESS_CODE_DESC = "Email sent successfully !!!";
	public static final String VAR_EMAILMESSAGE_NOT_RECEIVED = "Didn't received request to send email !!!";
	public static final String ERROR_CODE_SERVICE_NAME_UNAVAILABLE_DESC = "Service Name is unavailable to retrieve to,cc,bcc Lists from ZUUL ";
	public static final String ERROR_CODE_RECEPIENT_TO_LIST_UNAVAILABLE_DESC = "Recepient toList details are unavailable in ZUUL ";
	public static final String ERROR_CODE_RECEPIENT_LIST_UNAVAILABLE_DESC = "RecepientList - to,cc,bcc list details are unavailable in ZUUL ";
	public static final String ERROR_CODE_APPLICATION_ID_UNAVAILABLE_DESC = "Application ID is unavailable in received request ";
	public static final String ERROR_CODE_TEMPLATE_FILE_UNAVAILABLE_DESC = "Template File unavailable in location %s";
	public static final String ERROR_CODE_SUBJECT_EMPTY_DESC = "Email Subject received is empty";
	public static final String ERROR_CODE_REQUEST_ID_EMPTY_DESC = "request ID is unavailable in received request ";
	public static final String ERROR_CODE_JMS_UNAVAILABLE_DESC = "Unable to communicate with JMS";
	public static final String ERROR_CODE_INVALID_EMAIL_ADDRESS_DESC = "Invalid email address found %s";
	public static final String ERROR_CODE_INVALID_JSON_DATA_RECEIVED_DESC = "Failed to transform received JSON data to NotificationMessage Object";
	public static final String ERROR_CODE_EMAIL_AND_SMS_ARE_NULL_DESC = "Received EmailMessage And SMSMessage Are NULL";
	public static final String ERROR_CODE_COMMON_ATTRIBUTES_MESSAGE_NULL_DESC = "CommonAttributesMessage is NULL as part of the request";
	public static final String ERROR_CODE_COMMON_APPLICATION_ID_NOT_REGISTERED_ON_MULE_DESC = "Received application ID is not registered on MULEESB platform, Please communicate with MULEESB team to get it registered";

	public static final String EXCEPTION_ORIGIN_REDINGTON_GULF = "redingtongulf";

}
