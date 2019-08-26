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

import com.redn.connect.FTPInboundConstants;
import com.redn.connect.exception.Category;
import com.redn.connect.exception.ConnectException;


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
	public FTPClient createFTPConnection(String host, String username,
			String password) throws Exception {

		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(host);
			if (!ftpClient.login(username, password)) {
				ftpClient.logout();
			}
			ftpClient.enterLocalPassiveMode();
			ftpClient.setBufferSize(1024000);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		} catch (Exception e) {
			throw new ConnectException(FTPInboundConstants.ERROR_CODE_FTP_CONNECTION,
					e.getMessage(),
					Category.TECH, 
					FTPInboundConstants.CONST_EXCEPTION_ORIGIN_MULE);
		}
		if(200 == ftpClient.getReplyCode()){
		return ftpClient;
		}
		else
			throw new Exception(FTPInboundConstants.FTP_CONENCTION_FAILED);
	}
	
	/**
	 * Method to change current working directory
	 * 
	 * @param ftpClient
	 * @param path
	 * @return ftpClient
	 * @throws Exception
	 */
	public FTPClient changeWorkingDirectory(FTPClient ftpClient, String path)
			throws Exception {

		
		if (!ftpClient.changeWorkingDirectory(path)) {
			throw new ConnectException(FTPInboundConstants.CHANGE_DIR_ERROR_CODE,
					FTPInboundConstants.SOURCE_DIRECTORY_DOESNOT_EXIST,
					Category.TECH, 
					FTPInboundConstants.CONST_EXCEPTION_ORIGIN_MULE);
		}

		return ftpClient;
	}

	/**
	 * Method to check the given filenames are available in the current
	 * directory
	 * 
	 * sets true for the filename if the file exists and sets false for the
	 * filename if the doesn't exists
	 * 
	 * @param ftpClient
	 * @param filenames
	 * @return filesAvailabilityMap
	 * @throws Exception
	 */
	public List<String> findAvailableFiles(FTPClient ftpClient, String regex) throws Exception {
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   Calendar now = Calendar.getInstance();
		      now.set(Calendar.HOUR, 0);
		      now.set(Calendar.MINUTE, 0);
		      now.set(Calendar.SECOND, 0);
		      now.set(Calendar.HOUR_OF_DAY, 0);
		      String currentDateStr =  sdf.format(now.getTime());
		      Date currentDate = sdf.parse(currentDateStr);
		    List<String> availableFiles = new ArrayList<String>();
		    FTPFile[] files = ftpClient.listFiles();
		    for (FTPFile file : files) {
		     String fileDateStr = sdf.format(file.getTimestamp().getTime());
		     Date fileDate = sdf.parse(fileDateStr);
		      if(fileDate.after(currentDate)){// find all files of current date
		           Pattern pattern = Pattern.compile(regex); // file name started words 
		           String fileName = file.getName();
		           Matcher matcher = pattern.matcher(fileName);
		           if(matcher.find()){
		        	   availableFiles.add(fileName); 
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
	/*public void archiveFileInFTP(FTPClient ftpClient, String sourceLocation,
			String sourceFilename, String archiveLocation) throws Exception {
		
		if (!ftpClient.rename(sourceLocation + File.separator + sourceFilename, archiveLocation
				+ sourceFilename)) {
			throw new ConnectException(FTPInboundConstants.ARCHIVE_FILE_ERROR_CODE,
			FTPInboundConstants.ARCHIVING_FAILED_AT_SOURCE,
			Category.TECH, 
			FTPInboundConstants.CONST_EXCEPTION_ORIGIN_MULE);
	}
	}*/

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
	public synchronized void copyFileToLocalSystem(FTPClient ftpClient,
			String sourceFilename, String sourceLocation, String targetFilename)
			throws Exception {

		OutputStream outstream = null;
		outstream = new BufferedOutputStream(new FileOutputStream(
				targetFilename));
		if (!ftpClient.retrieveFile(sourceFilename, outstream)) {

			throw new ConnectException(FTPInboundConstants.ERROR_CODE_RETRIEVING_FILE,
					FTPInboundConstants.ERROR_RETRIEVING_SOURCE_FILE,
					Category.TECH, 
					FTPInboundConstants.CONST_EXCEPTION_ORIGIN_MULE);
		}
		outstream.close();

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
