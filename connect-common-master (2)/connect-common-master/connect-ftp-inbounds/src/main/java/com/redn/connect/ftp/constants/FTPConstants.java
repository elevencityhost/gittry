package com.redn.connect.ftp.constants;

/**
 * 
 * @author Shruthi.Kolloju
 * 
 * This Class is used to declare constants
 */

public class FTPConstants {
	
	public static final String CONNECT_ENTERPRISE_MESSAGE = "connectEnterpriseMessage";
	public static final String CONNECT = "connect.";
	public static final String USER = ".user";
	public static final String HOST = ".host";
	public static final String PORT = ".port";
	public static final String PSWD = ".password";
	public static final String FTPINBOUND = "ftpinb";
	public static final String LOCAL_DIR = ".localDir";
	public static final String REMOTE_DIR = ".remoteDir";
	public static final String MINUTE = ".MINUTE";
	public static final String SECOND = ".SECOND";
	public static final String HOUR_OF_DAY = ".HOUR_OF_DAY";
	public static final String REGEX = ".regex";
	public static final String LOGGER_PREFIX = ".logger";
	public static final String MULE = "mule";
	public static final String PROCESSED_PATH = ".processed";
	public static final String PROPERTY_FILENAME = "connect_config_";
	public static final String ARCHIVE_PATH = ".archive.path";
	public static final String ACK_FILE = "_ACK";
	public static final String NACK_FILE = "_NACK";
	public static final String PSR_FILE = "_PSR";
	public static final String MT103_FILE = "_MT103";
	public static final String MT940_FILE = "_MT940";
	public static final String TO_ADDRESS = "to.address";
	public static final String CC_ADDRESS = "cc.address";
	public static final String EMAIL_BODY = "email.body";
	public static final String EMAIL_SUBJECT = "email.subject";
	public static final String PROPERTIES_PREFIX = ".properties";
	public static final String SOURCE = "source";
	public static final String COMPONENT_NAME = "connect-ftp-inbounds";
	public static final String I1KNBD002 = "i1knbd002";
	public static final String FILES = "files";
	public static final String NON_AS2 = "NonAS2";
	public static final String LENOVO = "lenovo";
	


	public static final String FTP_DETAILS_CODE = "102162107";
	public static final String FTP_CONNECTION_SUCCESS_CODE = "102162108";
	public static final String NUMBER_OF_FILES_CODE = "102162109";
	public static final String FILES_COPIED_TO_lOCAL_CODE = "102162110";
	public static final String FILE_ARCHIVED_CODE = "102162111";
	public static final String CEM_CODE = "102162112";
	public static final String ERROR_CODE_STAGING_CONFIG_MISSING = "500172150";
	public static final String STAGING_FILE_PATH_CODE = "102162113";
	public static final String ERROR_CONFIG_MISSING = "Missing Properties";

	public static final String ERROR_STAGING_CONFIG_MISSING = "Staging Path configuration is missing in ZUUL";
	public static final String FTP_CONNECTION_SUCCESS = "FTP connection successful";
	public static final String LIST_OF_FILES = "listOfFiles";
	public static final String CONNECT_CONFIG = "connectConfig";
	public static final String NUMBER_OF_FILES = "Number of files in ";
	public static final String FILES_COPIED_TO_lOCAL_SYSTEM = "Files copied to local system";
	public static final String FILE_ARCHIVED = "File moved to archive location ";
	public static final String CEM_CREATED_LOG = "CEM created";
	public static final String CONNECT_LOG_CONFIG = "connectLogConfig";
	public static final String REDINGTON = "redington";
	public static final String BEAN_CONNECT_CONFIG = "connectConfigBean";
	public static final String DATE_FORMAT = ".dateFormat";
	public static final String STAGING_FILE_PATH_VALUE = "Staging file path value is : ";
	

	public static final String ERROR_CODE_FTP_CONNECTION = "400162200";
	public static final String ERROR_CODE_FTP = "500162201";
	public static final String ERROR_CODE_CHANGE_DIR = "400162202";
	public static final String ERROR_CODE_RETRIEVING_FILE = "400162203";
	public static final String ERROR_CODE_ARCHIVE_FILE = "500162204";
	public static final String ERROR_CODE_ARCHIVE_FILE_FTP = "500162205";
	public static final String ERROR_CODE_FILESIZE_ZERO = "500162206";
	public static final String ERROR_CODE_CEM_CREATION = "500162207";
	public static final String ERROR_CODE_OTHER = "500162208";
	public static final String ERROR_CODE_JMS = "400162209";
	public static final String ERROR_CODE_VALIDATE_ZUUL = "400162210";
	
	
	public static final String ERROR_FILE_NOT_ARCHIVED = " Archiving of file is not done";
	public static final String FERROR_FTP_CONNECTION_FAILED = "FTP Connection or login failed";
	public static final String ERROR_SOURCE_DIRECTORY_DOESNOT_EXIST = "Source directory does not exist";
	public static final String ERROR_ARCHIVING_FAILED_AT_FTP = "Archiving of file failed at Source";
	public static final String ERROR_RETRIEVING_SOURCE_FILE = "Error retrieving source file";
	public static final String ERROR_NO_FILES_RECEIVED = "No files received from FTP";
	public static final String ERROR_ZUUL_PROPERTY_MISSING = " ZUUL Configuration is missing for one of the properties: ftpHost or ftpUser or ftpPassword or localDir or SourceFTPPath or archiveLoc";
	public static final String ERROR_ZUUL_MISSING = " ZUUL Configuration is missing for one of the properties: dateFormat or minute or second or hour";
	
	
	
	
}
