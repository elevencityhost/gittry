package com.redn.connect.smsservice.constants;

public class SmsServiceConstants {
	
	public static final String ERROR_CODE_JMS_EXCEPTION = "400020210";
	public static final String ERROR_CODE_CONNECT_EXCEPTION = "400020220";
	
	public static final String ERROR_CODE_INVALID_JSON_DATA_RECEIVED = "400020230";
	public static final String ERROR_CODE_INVALID_JSON_DATA_RECEIVED_DESC = "Failed to transform received JSON data to SMSMessage Object is failed";
	
	public static final String EXCEPTION_ORIGIN_ROUTE_MOBILE = "Route Mobile";
	public static final String EXCEPTION_ORIGIN_REDINGTON_GULF = "redingtongulf";
	
	 public static final String MESSAGE_SPLIT_SYMBOL = "||";
	 public static final String DESTINATION_SPLIT_SYMBOL = ",";
	 public static final String MESSAGE_SPLIT_SYMBOL_WITH_ESCAPE ="\\|\\|";
	 public static final String SET_BULK_MESSAGE_SPLIT_SYMBOL = "||-||";
	 
	 public static final String ERROR_DESC_INVALID_MESSAGE_DESTINATION = "Missing one to one mapping between ‘Destinations’ and ‘Messages’ values";
	 public static final String ERROR_CODE_INVALID_MESSAGE_DESTINATION = "400020240";
	 
	 public static final String ERROR_DESC_MISSING_DESTINATION = "Destination is unavailable in received request";
	 public static final String ERROR_CODE_MISSING_DESTINATION = "400020250";
	 
	 public static final String ERROR_DESC_MISSING_MESSAGE = "Message is unavailable in received request";
	 public static final String ERROR_CODE_MISSING_MESSAGE = "400020260";
	 
	 public static final String ERROR_CODE_APPLICATION_ID_UNAVAILABLE = "400020270";
	 public static final String ERROR_DESC_APPLICATION_ID_UNAVAILABLE = "Application ID is unavailable in received request ";
	 
	 public static final String ERROR_CODE_REQUEST_ID_UNAVAILABLE = "400020280";
	 public static final String ERROR_DESC_REQUEST_ID_UNAVAILABLE = "Request ID is unavailable in received request ";
	 
	 public static final String ERROR_CODE_SOURCE_UNAVAILABLE = "400020290";
	 public static final String ERROR_CODE_SOURCE_UNAVAILABLE_DESC = "Source is unavailable in received request ";
	 
	 
	 public static final String REQUIRED_DELIVERY = "connect.smsservice.requiredDelivery";
	 
	 public static final String REQUIRED_TYPE = "connect.smsservice.requiredType";
	 
	 public static final String BULK_MESSAGES_PATH = "connect.smsservice.http.bulkmessages.path";
	 
	 public static final String SINGLE_MESSAGE_PATH = "connect.smsservice.http.singlemessage.path";
	 
	 
	 public static final String ERROR_CODE_MESSAGE_LENGTH ="400020310";
	 
	 public static final String ERROR_DESC_MESSAGE_LENGTH ="Message length should be less than or equal to 160 characters";
	 
	 public static final String ERROR_CODE_SOURCE_LENGTH ="400020320";
	 
	 public static final String ERROR_DESC_SOURCE_LENGTH = "Source length should be 18 if numeric or 11 if alphanumeric";
	 
	 public static final String ERROR_CODE_FILTER_UNACCEPTED = "400020110";
	 
	 public static final String ERROR_CODE_OTHER = "400020120";
	 
	 public static final String SUCCESS_CODE = "200";
	 public static final String SUCCESS_CODE_DESC = "SMS sent successfully !!!";
	 public static final String SUCCESS_STATUS = "SUCCESS";
	 
	 
	 public static final String ERROR_CODE_BAD_REQUEST = "400";
	 public static final String ERROR_CODE_BAD_REQUEST_INTERNAL_SERVER = "500";

	 public static final String VAR_STATUS_FAILURE = "FAILED";
	 public static final String VAR_EMPTY = "";
		
	 public static final String ERROR_CODE_JMS_UNAVAILABLE_DESC = "Unable to communicate with JMS";

	 public static final String UNDELIVERED_STATUS = "Undelivered";
	 
	 public static final int ERROR_CODE_1702 = 1702;
	 public static final String ERROR_CODE_1702_DESC = "One of the parameter is missing";
	 
	 public static final int ERROR_CODE_1703 = 1703;
	 public static final String ERROR_CODE_1703_DESC = "Bad Username Password.Means user authentication failed";
	 
	 public static final int ERROR_CODE_1704 = 1704;
	 public static final String ERROR_CODE_1704_DESC = "Bad type";
	 
	 public static final int ERROR_CODE_1705 = 1705;
	 public static final String ERROR_CODE_1705_DESC = "Bad message";
	 
	 public static final String ERROR_CODE_1706 = "Invalid MSISDN";
	 
	 public static final String ERROR_CODE_1707 = "Bad source/sender";
	 
	 public static final String ERROR_CODE_1709 = "Bind failed";
	 
	 public static final String ERROR_CODE_1710 = "Unknown error"; 
	 public static final String ERROR_CODE_1713  = "To many destinations"; 
	  
	 public static final String ERROR_CODE_1715 ="Response timeout"; 
	 public static final String ERROR_CODE_1025 ="Insufficient user credit";
	 
	 public static final String ERROR_CODE_1813 ="Error occurred  while creating  job file";
	 
	 public static final String APPLICATION_ID = "applicationId";
	 
	 public static final String REQUEST_ID = "requestId";
	 
	 public static final String SOURCE = "source";
	 
	 public static final String TYPE = "type";
	 
	 public static final String DLR = "dlr";
	 
	 public static final String DESTINATION = "destination";
	 
	 public static final String MESSAGE_CONTENT = "messageContent";
	 
	 public static final String PATH = "path";
	 
	 public static final String DATE_DONE = "DATE_DONE";
	 public static final String DATE_SUBMIT = "DATE_SUBMIT";
	 public static final String APP_ID = "APPLICATION_ID";
	 public static final String SENDER = "SENDER";
	 public static final String REQ_ID = "REQUEST_ID";
	 public static final String STATUS = "STATUS";
	 public static final String MESSAGE_ID = "MESSAGE_ID";
	 public static final String MOBILE_NO = "MOBILE_NO";
	 
	 public static final int MESSAGE_LENGTH = 160;
	 public static final int NUMERIC_SOURCE_LENGTH =18;
	 public static final int ALPHANUMERIC_SOURCE_LENGTH =11;
	 
	 public static final String EXCEPTION_ORIGIN_SMS_VENDOR ="Route Mobile";
	 
	 public static final String ERROR_CODE_DB_FAILED = "400020130";
}
