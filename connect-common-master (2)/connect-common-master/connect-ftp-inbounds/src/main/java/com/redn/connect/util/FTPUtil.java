package com.redn.connect.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.redn.connect.connectlogger.ConnectLogger;
import com.redn.connect.connectlogger.config.ConnectorConfig;
import com.redn.connect.exception.Category;
import com.redn.connect.exception.ConnectException;
import com.redn.connect.ftp.constants.FTPConstants;
import com.redn.connect.processor.connectconfig.ConnectConfiguration;
import com.redn.connect.vo.ConnectEnterpriseMessage;

/**
 * 
 * @author Shruthi.Kolloju The class contains functionalities like
 *         createFTPConenction, CopyFTPFileToLocalSyatem,
 *         changeWorkingDirectory, findAvailableFiles, archiveFileInFTP,
 *         logoutFTP
 */

public class FTPUtil {

	/**
	 * Method to create FTPClient and login into FTP
	 * 
	 * @param host
	 * @param username
	 * @param password
	 * @return FTPClient
	 * @throws Exception
	 */
	public FTPClient createFTPConnection(String host, String username, String password) throws Exception {

		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(host);
			if (!ftpClient.login(username, password)) {
				ftpClient.logout();
			} else {
				ftpClient.enterLocalPassiveMode();
				ftpClient.setBufferSize(1024000);
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			}
		} catch (Exception e) {
			throw new ConnectException(FTPConstants.ERROR_CODE_FTP_CONNECTION, e.getMessage(), Category.TECH,
					FTPConstants.MULE);
		}
		if (200 == ftpClient.getReplyCode()) {
			return ftpClient;
		} else
			throw new Exception(FTPConstants.FERROR_FTP_CONNECTION_FAILED);
	}

	/**
	 * Method to change current working directory
	 * 
	 * @param ftpClient
	 * @param path
	 * @return ftpClient
	 * @throws Exception
	 */
	public void changeWorkingDirectory(FTPClient ftpClient, String path) throws Exception {

		if (!ftpClient.changeWorkingDirectory(path)) {
			throw new ConnectException(FTPConstants.ERROR_CODE_CHANGE_DIR,
					FTPConstants.ERROR_SOURCE_DIRECTORY_DOESNOT_EXIST, Category.TECH, FTPConstants.MULE);

		}

	}

	/**
	 * Method to check the given filenames are available in the current
	 * directory
	 * 
	 * sets true for the filename if the file exists and sets false for the
	 * filename if the doesn't exists
	 * 
	 * @param ftpClient
	 * @param connectorConfig 
	 * @param logger 
	 * @param cem 
	 * @param connectorConfiguration
	 * @param serviceName
	 * @param hourOfDay
	 * @param filenames
	 * @return filesAvailabilityMap
	 * @throws Exception
	 */
	public List<String> findAvailableFiles(FTPClient ftpClient, ConnectEnterpriseMessage cem, ConnectLogger logger, ConnectorConfig connectorConfig, ConnectConfiguration connectorConfiguration,
			String serviceName) throws Exception {

		String dateFormat = 
				connectorConfiguration.get(FTPConstants.CONNECT + serviceName + FTPConstants.DATE_FORMAT);
		String minute = connectorConfiguration.get(FTPConstants.CONNECT + serviceName + FTPConstants.MINUTE);
		String second = connectorConfiguration.get(FTPConstants.CONNECT + serviceName + FTPConstants.SECOND);
		String hour_Of_day = connectorConfiguration.get(FTPConstants.CONNECT + serviceName + FTPConstants.HOUR_OF_DAY);

		if ((null == dateFormat || (dateFormat.isEmpty())) || (null == minute || (minute.isEmpty())) || ((null == second || (second.isEmpty())))
				|| ((null == hour_Of_day || (hour_Of_day.isEmpty())))) {
			throw new ConnectException(FTPConstants.ERROR_CODE_VALIDATE_ZUUL, FTPConstants.ERROR_ZUUL_MISSING,
					Category.DATA, FTPConstants.MULE);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(
				connectorConfiguration.get(FTPConstants.CONNECT + serviceName + FTPConstants.DATE_FORMAT));// "yyyy-MM-dd
																											// HH:mm:ss"
		Calendar now = Calendar.getInstance();
		now.set(Calendar.MINUTE,
				Integer.parseInt(connectorConfiguration.get(FTPConstants.CONNECT + serviceName + FTPConstants.MINUTE)));
		now.set(Calendar.SECOND,
				Integer.parseInt(connectorConfiguration.get(FTPConstants.CONNECT + serviceName + FTPConstants.SECOND)));
		now.set(Calendar.HOUR_OF_DAY, Integer
				.parseInt(connectorConfiguration.get(FTPConstants.CONNECT + serviceName + FTPConstants.HOUR_OF_DAY)));
		String currentDateStr = sdf.format(now.getTime());
		Date currentDate = sdf.parse(currentDateStr);
		System.out.println(currentDate);
		List<String> availableFiles = new ArrayList<String>();
		FTPFile[] files = ftpClient.listFiles();
		for (FTPFile file : files) {
			if (file.isFile()) {
				System.out.println(file.getTimestamp().getTime());
				String fileDateStr = sdf.format(file.getTimestamp().getTime());
				Date fileDate = sdf.parse(fileDateStr);
				//changed logger to debug
				logger.debug(cem, null, "102162110",
						"cuurent date :" + currentDateStr + "  fileDateStr  :" + fileDateStr, connectorConfig);
				//add condition for nbd and scb ftp --------if (fileDate.after(currentDate) || serviceName.contains("scb") || serviceName.contains("nbd") ){
				if (fileDate.after(currentDate)) {// find all files of current
													// date
					String regex = connectorConfiguration.get(FTPConstants.CONNECT + serviceName + FTPConstants.REGEX);
					if (null != regex && !(regex.isEmpty())) {
						Pattern pattern = Pattern.compile(regex); // file name
																	// started
																	// words
						String fileName = file.getName();
						Matcher matcher = pattern.matcher(fileName);
						if (matcher.find()) {
							availableFiles.add(fileName);
						}
					} else {
						availableFiles.add(file.getName());
					}
				}
			}
		}
		return availableFiles;
	}

	/**
	 * Method to archive the file in FTP location from source location to
	 * archive location
	 * 
	 * @param ftpClient
	 * @param sourceLocation
	 * @param sourceFilename
	 * @param archiveLocation
	 * @param archiveFilename
	 * @throws Exception
	 */
	public void moveFileInFTP(FTPClient ftpClient, String sourceLocation, String sourceFilename, String archiveLocation)
			throws Exception {

		if (!ftpClient.rename(sourceLocation + File.separator + sourceFilename,
				archiveLocation + File.separator + sourceFilename)) {
			throw new ConnectException(FTPConstants.ERROR_CODE_ARCHIVE_FILE_FTP,
					FTPConstants.ERROR_ARCHIVING_FAILED_AT_FTP, Category.TECH, FTPConstants.MULE);

		}
	}

	/**
	 * This method will copy the file from FTP to local folder
	 * 
	 * once file moved close the output stream
	 * 
	 * @param ftpClient
	 * @param sourceFilename
	 * @param sourceLocation
	 * @param targetFilename
	 * @throws Exception
	 */
	public synchronized void copyFileToLocalSystem(FTPClient ftpClient, String sourceFilename, String sourceLocation,
			File targetFile) throws Exception {

		OutputStream outstream = null;
		try {
			outstream = new BufferedOutputStream(new FileOutputStream(targetFile));
			if (!ftpClient.retrieveFile(sourceFilename, outstream)) {
				throw new ConnectException(FTPConstants.ERROR_CODE_RETRIEVING_FILE,
						FTPConstants.ERROR_RETRIEVING_SOURCE_FILE, Category.TECH, FTPConstants.MULE);
			}
		} finally {
			outstream.close();
		}

	}

	/**
	 * Method to logout from the FTP
	 * 
	 * @param ftpClient
	 * @throws Exception
	 */
	public void logoutFTP(FTPClient ftpClient) throws Exception {

		if (ftpClient != null) {
			ftpClient.logout();
			ftpClient.disconnect();
		}
	}
}
