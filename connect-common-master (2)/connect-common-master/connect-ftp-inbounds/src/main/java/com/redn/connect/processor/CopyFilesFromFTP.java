package com.redn.connect.processor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.mule.api.MuleContext;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.connectlogger.ConnectLogger;
import com.redn.connect.connectlogger.config.ConnectorConfig;
import com.redn.connect.exception.Category;
import com.redn.connect.exception.ConnectException;
import com.redn.connect.ftp.constants.FTPConstants;
import com.redn.connect.processor.connectconfig.ConnectConfiguration;
import com.redn.connect.util.FTPUtil;
import com.redn.connect.util.LoggerUtil;
import org.mule.api.context.MuleContextAware;
import com.redn.connect.vo.ConnectEnterpriseMessage;

/**
 * @author Shruthi.Kolloju
 * 
 *         This class creates FTP connection to Samsung and archives the files
 *         from Samsung FTP location to local directory
 */

public class CopyFilesFromFTP implements Callable, MuleContextAware {

	private static FTPUtil ftpUtil = new FTPUtil();

	ConnectConfiguration connectConfiguration;
	// private MuleContext muleContext;

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		FTPClient ftpClient = null;
		List<String> availableFiles = new ArrayList<String>();

		MuleMessage message = eventContext.getMessage();
		ConnectEnterpriseMessage cem = message.getInvocationProperty(FTPConstants.CONNECT_ENTERPRISE_MESSAGE);
		String serviceName = cem.getEnterpriseHeader().getServiceName();
		ConnectorConfig connectorConfig = new ConnectorConfig();
		connectorConfig.setLoggerName(FTPConstants.CONNECT + serviceName + FTPConstants.LOGGER_PREFIX);

		StringBuilder ftpHost = new StringBuilder();
		StringBuilder ftpUser = new StringBuilder();
		StringBuilder ftpPass = new StringBuilder();
		StringBuilder localDir = new StringBuilder();
		StringBuilder sourceFTPPath = new StringBuilder();
		
		//if((null != ))

		ftpHost.append(validateZuulProperty(connectConfiguration.get(FTPConstants.CONNECT + serviceName + FTPConstants.HOST)));
		ftpUser.append(validateZuulProperty(connectConfiguration.get(FTPConstants.CONNECT + serviceName + FTPConstants.USER)));
		ftpPass.append(validateZuulProperty(connectConfiguration.get(FTPConstants.CONNECT + serviceName + FTPConstants.PSWD)));
		localDir.append(validateZuulProperty(connectConfiguration.get(FTPConstants.CONNECT + serviceName + FTPConstants.LOCAL_DIR)));
		sourceFTPPath.append(validateZuulProperty(connectConfiguration.get(FTPConstants.CONNECT + serviceName + FTPConstants.REMOTE_DIR)));
		String archiveLoc = validateZuulProperty(connectConfiguration.get(FTPConstants.CONNECT + serviceName + FTPConstants.ARCHIVE_PATH));

		ConnectLogger logger = LoggerUtil.getDatabaseConenction();
		logger.info(cem, null, FTPConstants.FTP_DETAILS_CODE, "ftpHost :" + ftpHost + " ftpUser:" + ftpUser
				+ " ftpPass:" + ftpPass + " localDir:" + localDir + " archiveLoc:" + archiveLoc, connectorConfig);
		try {
			// create FTP connection
			ftpClient = ftpUtil.createFTPConnection(ftpHost.toString(), ftpUser.toString(), ftpPass.toString());
			logger.debug(cem, null, FTPConstants.FTP_CONNECTION_SUCCESS_CODE, FTPConstants.FTP_CONNECTION_SUCCESS,
					connectorConfig);
			downdloadFilesAndArchiveinFTP(ftpClient, localDir.toString(), sourceFTPPath.toString(), eventContext,
					connectConfiguration, availableFiles, logger, serviceName, connectorConfig, cem, archiveLoc);

			eventContext.getMessage().setInvocationProperty(FTPConstants.LIST_OF_FILES, availableFiles);
			eventContext.getMessage().setInvocationProperty(FTPConstants.CONNECT_LOG_CONFIG, connectorConfig);
			eventContext.getMessage().setInvocationProperty(FTPConstants.CONNECT_CONFIG, connectConfiguration);

		} catch (Exception e) {
			throw new ConnectException(FTPConstants.ERROR_CODE_FTP, e.getMessage(), Category.COMM, FTPConstants.MULE);
		} finally {
			ftpUtil.logoutFTP(ftpClient);
		}
		return message;
	}

	/*
	 * To download files from FTP
	 */
	private static void downdloadFilesAndArchiveinFTP(FTPClient ftpClient, String localDir, String sourcePath,
			MuleEventContext eventContext, ConnectConfiguration connectorConfiguration, List<String> allfilesInFTP,
			ConnectLogger logger, String serviceName, ConnectorConfig connectorConfig, ConnectEnterpriseMessage cem,
			String archiveLoc) throws Exception {

		String[] sourceFolders = sourcePath.split(",");

		try {
			for (int folderCount = 0; folderCount < sourceFolders.length; folderCount++) {
				ftpUtil.changeWorkingDirectory(ftpClient, sourceFolders[folderCount]);
				List<String> ftpFiles = new ArrayList<String>();
				//ftpFiles = ftpUtil.findAvailableFiles(ftpClient, connectorConfiguration, serviceName);
				ftpFiles = ftpUtil.findAvailableFiles(ftpClient, cem,logger,connectorConfig, connectorConfiguration, serviceName);
				/*
				 * for(String fileName:ftpFiles){ filesInFTP.add(fileName); }
				 */
				if (ftpFiles.size() > 0){
				logger.info(cem, null, FTPConstants.NUMBER_OF_FILES_CODE,
						FTPConstants.NUMBER_OF_FILES + sourceFolders[folderCount] + " is " + ftpFiles.size()+" and files are"+ftpFiles.toString(),
						connectorConfig);
				}

				for (int i = 0; i < ftpFiles.size(); i++) {
					String stagingFilepath = localDir + File.separator + ftpFiles.get(i);
					logger.info(cem, null, FTPConstants.STAGING_FILE_PATH_CODE,
							FTPConstants.STAGING_FILE_PATH_VALUE+stagingFilepath+" and file is "+ftpFiles.get(i), connectorConfig);
					
					ftpUtil.copyFileToLocalSystem(ftpClient, ftpFiles.get(i), sourceFolders[folderCount],
							new File(stagingFilepath));
					logger.info(cem, null, FTPConstants.FILES_COPIED_TO_lOCAL_CODE,
							FTPConstants.FILES_COPIED_TO_lOCAL_SYSTEM, connectorConfig);

					archiveFileInLocal(stagingFilepath, archiveLoc, ftpFiles.get(i));
					allfilesInFTP.add(ftpFiles.get(i));
					logger.info(cem, null, FTPConstants.FILE_ARCHIVED_CODE, FTPConstants.FILE_ARCHIVED,
							connectorConfig);

					moveFileToProcessed(ftpClient, connectorConfiguration, serviceName, sourceFolders[folderCount],
							ftpFiles.get(i));
				}

			}
		} catch (Exception e) {
			throw new ConnectException(FTPConstants.ERROR_CODE_FTP, e.getMessage(), Category.COMM, FTPConstants.MULE);
		}
	}

	private static void archiveFileInLocal(String stagingFilepath, String archiveLoc, String fileName)
			throws ConnectException, IOException {

		FileInputStream stagingStream = null;
		FileOutputStream archiveStream = null;
		try {
			File stagingFile = new File(stagingFilepath);
			File archivefile = new File(archiveLoc + File.separator + fileName);
			stagingStream = new FileInputStream(stagingFile);
			archiveStream = new FileOutputStream(archivefile);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = stagingStream.read(buffer)) > 0) {
				archiveStream.write(buffer, 0, length);
			}
			stagingStream.close();
			archiveStream.close();
		} catch (IOException ioException) {
			throw new ConnectException(FTPConstants.ERROR_CODE_ARCHIVE_FILE, ioException.getMessage(), Category.TECH, FTPConstants.MULE);
		}

	}

	private static void moveFileToProcessed(FTPClient ftpClient, ConnectConfiguration connectorConfiguration,
			String serviceName, String sourcepath, String ftpFileName) throws Exception {
		String processedPath = connectorConfiguration
				.get(FTPConstants.CONNECT + serviceName + FTPConstants.PROCESSED_PATH);
		if (null != processedPath && !(processedPath.isEmpty())) {
			ftpUtil.moveFileInFTP(ftpClient, sourcepath, ftpFileName, processedPath);

		}
	}

	@Override
	public void setMuleContext(MuleContext context) {
		connectConfiguration = context.getRegistry().lookupObject("connectConfigBean");
	}
	
	private String validateZuulProperty(String value) throws ConnectException
	{		
		if(null != value && value.length() > 0){
		}
		else{
			throw new ConnectException(FTPConstants.ERROR_CODE_VALIDATE_ZUUL, FTPConstants.ERROR_ZUUL_PROPERTY_MISSING, Category.TECH, FTPConstants.MULE);
		}
		return value;
	}
}
