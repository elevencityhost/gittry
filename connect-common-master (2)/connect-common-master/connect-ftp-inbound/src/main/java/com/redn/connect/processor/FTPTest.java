package com.redn.connect.processor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;

import com.redn.connect.util.FTPUtil;

public class FTPTest {
	
	private static FTPUtil ftpUtil = new FTPUtil();
	
	/*public static void main(String[] args) throws Exception{
		FTPClient ftpClient = null;
		String regex = null;
		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String ftphost = eventContext.getMessage().getProperty("PropertyName",PropertyScope.INVOCATION); 
		// retrieve service config object
		//CConfig serviceConfig = eventContext.getMuleContext().getRegistry().lookupObject("scm004ServiceConfigBean");
		//String[] targets = serviceConfig.get("es.scm004.target.locations").split(",");
		String sourceFTPHosts = "TPG,IPG";
		String[] targets = sourceFTPHosts.split(",");
		for(int i = 0; i < targets.length; i++) {
		//	StringBuilder ftpHost = new StringBuilder("");
		//	StringBuilder ftpUser = new StringBuilder("");
			//StringBuilder ftpPass = new StringBuilder("");
			//StringBuilder localDir = new StringBuilder("");
			//StringBuilder regex = new StringBuilder("");
			//StringBuilder sourceFTPPath = new StringBuilder("");
			//StringBuilder archiveFTPPath = new StringBuilder("");	
		//	loadServiceConfigData(eventContext, ftpHost, ftpUser, ftpPass, localDir, regex, sourceFTPPath, archiveFTPPath, targets[i], serviceConfig);
			String ftpHost = "usmfttst.lenovo.com";
			String ftpUser = "Ex_CDW_QAS";
			String ftpPass = "Ex_CDW_QAS";
			String localDir = "C://Users//jigyasa.arora//Desktop//jigs";
			if(targets[i].equals("TPG")){
				String r = "^TPG_";
				
				  String d2 = d1.replaceAll("-", "_");
			}
			if(targets[i].equals("materialmaster")){
				String regex1 = "^DPE_";
				String d1= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				  String regex2 = d1.replaceAll("-", "");
			}
			//String regex = ".TXT" ;
			String sourceFTPPath="/MaterialMaster";
			String archiveFTPPath = "C://Users//jigyasa.arora//Desktop//jigs";
			if (ftpHost.length() < 1 || ftpUser.length() < 1 || ftpPass.length() < 1 || localDir.length() < 1 || regex.length() < 1 || sourceFTPPath.length() < 1 || archiveFTPPath.length() < 1)
			{
				//throw new UMGIException(400779111, "Please ensure all ftp details are set for "+targets[i]+" ");
			}
			else{
			// create FTP connection
				
				
				ftpClient = ftpUtil.createFTPConnection(ftpHost, ftpUser, ftpPass);
				
			downdloadFilesAndArchiveinFTP(ftpClient, localDir.toString(), sourceFTPPath.toString(), archiveFTPPath.toString(), regex.toString());
				System.out.println(ftpClient);
			}
		}
	}
	
	private static void downdloadFilesAndArchiveinFTP(FTPClient ftpClient,
			String localDir, String sourcePath, String archivePath, String regex) throws Exception {

	// change working directory
		ftpUtil.changeWorkingDirectory(ftpClient, sourcePath);
		String extension = message.getProperty(SCM004Constants.VAR_INPUTFILE_EXTENSION, PropertyScope.INVOCATION);
		String extension ="";
		// check for file availability
		List<String> availableFiles = ftpUtil.availableFiles(ftpClient, regex);
		List<String> availableFiles =  null;
		
		for(int i = 0; i < availableFiles.size(); i++){
			String targetFilename = localDir + File.separator + availableFiles.get(i);
			ftpUtil.copyFileToLocalSystem(ftpClient, availableFiles.get(i), sourcePath, targetFilename + extension);
			while((new Date().getTime() - new File(targetFilename).lastModified()) < 20000)
			{
				Thread.sleep(5000);
			}
			//ftpUtil.archiveFileInFTP(ftpClient, sourcePath, availableFiles.get(i), archivePath);
		}
		if (message.getProperty("listOfFiles", PropertyScope.INVOCATION) != null
				&& ((List<String>) message.getProperty("listOfFiles", PropertyScope.INVOCATION)).size() > 0) {
			availableFiles.addAll(((List<String>) message.getProperty("listOfFiles", PropertyScope.INVOCATION)));	
		} 
		message.setProperty("listOfFiles", availableFiles, PropertyScope.INVOCATION);
		
		// logout
			ftpUtil.logoutFTP(ftpClient);
	}*/

}
