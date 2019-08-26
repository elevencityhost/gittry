/***
 * 
 * @author SaiPrasad.Jonnala
 * 
 * InternalHTTPConstants - It contains all the constants which are required across connect internal HTTP flow.
 * 
 * */

package com.redn.connect.constants;

public class InternalHTTPConstants {

	public static final String EXCEPTION_ORIGIN_REDINGTON_GULF = "redingtongulf";

	public static final String VAR_PAYLOAD = "payload";
	public static final String VAR_ATTACHMENT_NAMES = "attachmentNames";
	public static final String VAR_FILE_PROCESSING_LOCATION = "fileProcessingLocation";
	public static final String VAR_REQUEST_ID = "requestID";
	public static final String VAR_APPLICATION_ID = "applicationID";
	public static final String VAR_FAILURE = "FAILED";
	public static final String VAR_SUCCESS = "SUCCESS";
	public static final String VAR_EMPTY = "";
	public static final String VAR_ARCHIVING_FILE_NAME_EXTENSION = ".txt";
	public static final String VAR_HYPHEN = "-";
	public static final String VAR_CONNECT_CONFIG_BEAN = "connectConfigBean";
	public static final String VAR_SOURCE_QUERY_PARAM = "source";

	public static final String ACTION_CONSTANT = "Non AS2";
	public static final String COMMUNICATION_CONSTANT = "Sync";
	public static final String COMPONENT_NAME_CONSTANT = "connect-internal-http";
	public static final String INTERNAL_HTTP_SOURCE_SYSTEM_CONSTANT = "RedingtonGulf";
	public static final String INBOUND_ATTACHMENTS_VALIDATION_FAILED_CONSTANT = "Inbound Attachments Received Are Null Or Empty";

	public static final String ERROR_CODE_FILTER_UNACCEPTED = "400018110";
	public static final String ERROR_CODE_MANDATORY_DATA_MISSING = "400018120";
	public static final String ERROR_CODE_NETWORK_EXCEPTION = "400018130";
	public static final String ERROR_CODE_OTHER_EXCEPTIONS = "400018150";
	public static final String ERROR_CODE_JMS = "400018140";
	public static final String ERROR_CODE_ARCHIVE_LOCATION_EMPTY = "400018160";
	public static final String ERROR_CODE_HTTP_QUERY_PARAM_SOURCE_MISSING = "400018170";
	public static final String ERROR_CODE_HTTP_QUERY_PARAM_SOURCE_NOT_AS_PER_SPECIFICATION = "400018180";

	public static final String ERROR_CODE_HTTP_QUERY_PARAM_SOURCE_MISSING_DESC = "HTTP Query Param 'source' Is Missing As Part Request URL";
	public static final String ERROR_CODE_ARCHIVE_LOCATION_EMPTY_DESC = "Archiving Location From ZUUL is 	empty or null";
	public static final String ERROR_CODE_HTTP_QUERY_PARAM_SOURCE_NOT_AS_PER_SPECIFICATION_DESC = "HTTP Query Param source is not as per API specification";
	
	public static final String BAD_REQUEST_ERROR_CODE = "400";
	public static final String BAD_REQUEST_INTERNAL_SERVER_ERROR_CODE = "500";

}
