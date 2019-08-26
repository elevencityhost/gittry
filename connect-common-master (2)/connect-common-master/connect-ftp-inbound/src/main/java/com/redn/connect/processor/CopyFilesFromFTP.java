package com.redn.connect.processor;

import java.io.File;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.mule.api.MuleContext;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.context.MuleContextAware;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.FTPInboundConstants;
import com.redn.connect.connectlogger.ConnectLogger;
import com.redn.connect.connectlogger.config.ConnectorConfig;
import com.redn.connect.exception.Category;
import com.redn.connect.exception.ConnectException;
import com.redn.connect.processor.connectconfig.ConnectConfiguration;
import com.redn.connect.util.FTPUtil;
import com.redn.connect.util.LoggerUtil;

public class CopyFilesFromFTP implements Callable,MuleContextAware {
	
	private static FTPUtil ftpUtil = new FTPUtil();
	ConnectConfiguration connectConfig = null;
	
	private MuleContext muleContext;
	
	public MuleContext getMuleContext() {
		return muleContext;
	}

	@Override
	public void setMuleContext(MuleContext context) {
		connectConfig = context.getRegistry().lookupObject(FTPInboundConstants.BEAN_CONNECT_CONFIG);
		
	}

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		FTPClient ftpClient = null;
		String ftpHosts = eventContext.getMuleContext().getRegistry().get("connect.ftp2connect.sourceFTPHosts");
		MuleMessage message = eventContext.getMessage();
		ConnectorConfig connectorConfig = new ConnectorConfig();
		connectorConfig.setLoggerName(FTPInboundConstants.CONNECT_FTP_INBOUND_LOGGER_NAME);
		
		String[] targets = ftpHosts.split(",");
		for(int i = 0; i < targets.length; i++) {
			StringBuilder ftpHost = new StringBuilder("");
			StringBuilder ftpUser = new StringBuilder("");
			StringBuilder ftpPass = new StringBuilder("");
			StringBuilder localDir = new StringBuilder("");
			StringBuilder sourceFTPPath = new StringBuilder("");
			loadServiceConfigData(connectConfig, ftpHost, ftpUser, ftpPass, localDir, sourceFTPPath, targets[i]);
			ConnectLogger logger = LoggerUtil.getDatabaseConenction();
			logger.info(null, null, FTPInboundConstants.FTP_DETAILS_CODE, "ftpHost :"+ftpHost+" ftpUser:"+ftpUser+
					" ftpPass:"+ftpPass+" localDir:"+localDir, connectorConfig);
			if (ftpHost.length() < 1 || ftpUser.length() < 1 || ftpPass.length() < 1 || localDir.length() < 1 || sourceFTPPath.length() < 1){
				throw new ConnectException(FTPInboundConstants.FTP_ERROR, "Please ensure all ftp details are set for "+targets[i]+" ", Category.COMM, FTPInboundConstants.MULE);
			}
			else{
			// create FTP connection
				ftpClient = ftpUtil.createFTPConnection(ftpHost.toString(), ftpUser.toString(), ftpPass.toString());
				downdloadFilesAndArchiveinFTP(ftpClient, localDir.toString(), sourceFTPPath.toString(), eventContext);
			}
		}
		return message;
	}
	
	
	
	private void loadServiceConfigData(ConnectConfiguration connectConfig, StringBuilder ftpHost, StringBuilder ftpUser, StringBuilder ftpPass,
		StringBuilder localDir,  StringBuilder sourceFTPPath,String target) {
		if(connectConfig.get(target+".host") !=null){
			String host = connectConfig.get(target+".host");
			ftpHost.append(host);
		}
		if(connectConfig.get(target+".user") !=null){
			String user = connectConfig.get(target+".user");
			ftpUser.append(user);
		}
		if(connectConfig.get(target+".password") !=null){
			String password = connectConfig.get(target+".password");
			ftpPass.append(password);
		}
		if(connectConfig.get(target+".path") !=null){
			String path = connectConfig.get(target+".path");
			sourceFTPPath.append(path);
		}
		if(connectConfig.get(target+".localDir") !=null){
			String dir = connectConfig.get(target+".localDir");
			localDir.append(dir);
		}
	}
	
	private static void downdloadFilesAndArchiveinFTP(FTPClient ftpClient,String localDir, String sourcePath,MuleEventContext eventContext) throws Exception {
		String[] sourceFolders  = sourcePath.split(",");
		for(int folderCount = 0;  folderCount < sourceFolders.length; folderCount++){
			ftpUtil.changeWorkingDirectory(ftpClient, sourceFolders[folderCount]);
			String extension ="";
			if(eventContext.getMuleContext().getRegistry().get( sourceFolders[folderCount]+".format") !=null) {
				String fileFormat = eventContext.getMuleContext().getRegistry().get( sourceFolders[folderCount]+".format");
				String regex = "^"+fileFormat;
				List<String> availableFiles = ftpUtil.findAvailableFiles(ftpClient, regex.toString());
				for(int i = 0; i < availableFiles.size(); i++) {
					String targetFilename = localDir + File.separator + availableFiles.get(i);
					ftpUtil.copyFileToLocalSystem(ftpClient, availableFiles.get(i), sourceFolders[folderCount], targetFilename + extension);
				}
			}
		}
			ftpUtil.logoutFTP(ftpClient);
	}
		
		
		

}
