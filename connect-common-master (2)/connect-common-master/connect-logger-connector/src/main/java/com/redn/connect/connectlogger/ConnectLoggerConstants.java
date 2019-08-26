package com.redn.connect.connectlogger;

/**
 * @author viswanath.mallela
 *
 */
public class ConnectLoggerConstants {
	/**
	 * Logger Levels
	 */

	public static final String INFO = "INFO";
	public static final String DEBUG = "DEBUG";
	public static final String WARN = "WARN";
	public static final String ERROR = "ERROR";
	public static final String TRACE = "TRACE";
	public static final String FATAL = "FATAL";
	public static final String ALL = "ALL";
	public static final String OFF = "OFF";

	public static final String ENTERPRISE_CONSOLE = "EnterpriseConsole";
	public static final String ENTERPRISE_FILE = "EnterpriseFile";
	public static final String ENTERPRISE_DATABASE = "EnterpriseDatabase";

	//public static final String LOG_CONFIG_NAME = "connect_logger.properties";
	public static final String ENV_CONFIGROOT = "env.configRoot";
	public static final String ENV_LOCATION = "env.location";
	public static final String ENV_DECRYPTION_ALGORITHM = "env.decryptionAlgorithm";
	public static final String ENV_DECRYPTION_PASSWORD = "env.decryptionPassword";
	public static final String ENV_DB_USER = "env.dbUser";
	public static final String ENV_DB_PASSWORD = "env.dbPassword";
	public static final String ENV_DB_URL = "env.dbUrl";
	/*
	 * Header parameters
	 */
	public static final String HEADER_KEY_MESSAGE_ID = "messageId";
	public static final String HEADER_KEY_MESSAGE_SOURCE = "messageSource";
	public static final String HEADER_KEY_MESSAGE_ACTION = "messageAction";
	public static final String HEADER_KEY_RESOURCE_NAME = "resourceName";
	public static final String HEADER_KEY_RESOURCE_ID = "resourceId";
	public static final String HEADER_KEY_SOURCE_ID = "sourceId";

	/**
	 * ThreadContext Parameters
	 */

	public static final String PROCESS_STATE = "PROCESS_STATE";
	public static final String LOGGER_NAME = "LOGGER_NAME";
	public static final String SERVICE_NAME = "SERVICE_NAME";
	public static final String NODE_NAME = "NODE_NAME";
	public static final String FILE_LOCATION = "FILE_LOCATION";
	public static final String ERROR_MESSAGE = "ERROR_MESSAGE";
	public static final String ROUTINGKEY = "ROUTINGKEY";
	public static final String ROUTINGDBKEY = "ROUTINGDBKEY";
	public static final String LOG_INTO_FILE = "logIntoFile";
	public static final String LOG_INTO_DB = "logIntoDB";

	/**
	 * Enterprise Message Header ThreadContext Parameters
	 */

	public static final String MESSAGE_ID = "MESSAGE_ID";
	public static final String SOURCE = "SOURCE";
	public static final String ACTION = "ACTION";
	public static final String RESOURCE_NAME = "RESOURCE_NAME";
	public static final String RESOURCE_ID = "RESOURCE_ID";
	public static final String SOURCE_ID = "SOURCE_ID";

	public static final String ES_DEFAULT_MULE_LOGGING_CODE = "connect.common.logger.mule.default.logging.code";


	public static final String SERVICENAME = "SERVICENAME";
	public static final String MESSAGE_SOURCE = "MESSAGE_SOURCE";

	public static final String FLOWHEADERS_VARS_INJECTION = HEADER_KEY_MESSAGE_ID + "?," + HEADER_KEY_MESSAGE_SOURCE + "?,"
			+ HEADER_KEY_MESSAGE_ACTION + "?," +  HEADER_KEY_SOURCE_ID + "?";

	public static final String ES_LOGGER_SERVICE_NAME_LOGGER_IMPL_PREFIX = "connect.common.logger.impl.for.service.";

	public static final String JDBC_DRIVER_NAME = "com.mysql.jdbc.Driver";
}
