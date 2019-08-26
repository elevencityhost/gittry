package com.red.connect.testflow;

/**
 * 
 * 
 * @author Laxshmi Maram
 *  *
 */

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.springframework.core.io.ClassPathResource;

import com.redn.connect.processor.connectconfig.ConnectConfiguration;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.Contents;
import com.redn.connect.vo.CustomProps;
import com.redn.connect.vo.EnterpriseHeader;
import com.redn.connect.vo.EnterpriseHeader.Custom;
public class TEstCEMCreation implements Callable{
	public ConnectConfiguration connectconfiguration = null;

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		
		MuleMessage message = eventContext.getMessage();
		connectconfiguration = new ConnectConfiguration();
		String configRoot = System.getProperty("env.configRoot");
		String decryptionAlgorithm = System.getProperty("env.decryptionAlgorithm");
		String decryptionPassword = System.getProperty("env.decryptionPassword");
		
		connectconfiguration.setDecryptionAlgorithm(decryptionAlgorithm);
		connectconfiguration.setDecryptionPassword(decryptionPassword);
		connectconfiguration.setConfigRoot(configRoot);
		connectconfiguration.setLocation(new ClassPathResource("connect_config_testflow.properties"));
		
		String filename=connectconfiguration.get("filename");
		System.out.println("filename"+filename);
		//String filename="E:\\Backup\\RCI\\Basicstuff\\Files\\input\\test512.zip";
		ConnectEnterpriseMessage cem = new ConnectEnterpriseMessage();
		EnterpriseHeader header = new  EnterpriseHeader();
		Custom custom = new Custom();
		CustomProps prop = new CustomProps();
		Contents contents = new Contents();
		
		Contents contents1 = new Contents();
		header.setServiceName("connect-file-outbound");
		contents.setName("interface");
		contents.setValue("o3klen009");
		
		contents1.setName("protocol");
		contents1.setValue("ftp");
		prop.getContents().add(contents );
		prop.getContents().add(contents1 );
		custom.setAny(prop);
		header.setCustom(custom);
		header.setMessageSource(filename);
		
		cem.setEnterpriseHeader(header );

		return cem;
	}
}

