package com.redn.connect.sap2connect.common.constants;

/**
 * 
 * @author Mahasweta.Das
 *
 *This class contains constants for sap2connect 
 */

public interface SAP2ConnectConstants 
{


	public static final String L_SERVICE_NAME = "SAP2ES_HCM";

	public static final String IDOC_TYPE = "IDOCTYP";
	
	public static final String IDOC_MESSAGE_ID = "IDOC_MESSAGE_ID";
	
	public static final String MESSAGE_ACTION = "Publish";
	
	public static final String MESSAGE_CODE = "MESCOD";
	
	public static final String MESSAGE_ID = "messageId";

	public static final String DOCUMENT_NUMBER = "DOCNUM";

	// actual value is computed in mule flow
	public static final String IDOC_FILE_LOCATION = "IDOC_FILE_LOCATION";

	// actual value is computed in mule flow
	public static final String IDOC_FILE_NAME = "IDOC_FILE_NAME";

	public static final String DATE_FORMAT = "yyyyMMdd.HHmmss";

	public static final String SERVICE_NAME = "SAP2ES-HCM";
	public static final String VAR_NON_AS2 = "Non AS2";
	public static final String VAR_ASYNC="Async";
	public static final String VAR_SERVICE_NAME="serviceName";
	public static final String VAR_ENTERPRISE_MESSAGE  = "enterpriseMessage";

	public static final String APIGATEWAY_ERROR_DESCRIPTION = "The API Gateway is not available (or) not running";

	public static final String ERROR_CODE_FILE_SIZE="400017100";
	public static final String ERROR_DESCRIPTION_FILE_SIZE="Apigateways call has returned http status other than 200";
	public static final String ERROR_CODE_FILTER_UNACCEPTED="400017110";
	public static final String ERROR_CODE_JMS="400017120";
	public static final String ERROR_CODE_SAP="500017130";
	public static final String ERROR_CODE_OTHER="500017140";
	
	public static final String VAR_ERROR_CODE="errorCode";
	public static final String VAR_ERROR_DESCRIPTION="errorDescription"; 
}
