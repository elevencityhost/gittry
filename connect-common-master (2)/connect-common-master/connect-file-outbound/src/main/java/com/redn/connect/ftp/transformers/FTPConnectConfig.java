
package com.redn.connect.ftp.transformers;
/**
 * @author Laxshmi Maram
 *  *
 *  This class will load  the required  property file from zuul
 */


import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.springframework.core.io.ClassPathResource;
import com.redn.connect.processor.connectconfig.ConnectConfiguration;

public class FTPConnectConfig implements Callable {

    public static final String DESTINATION_SERVICE_CONFIG_OBJECT="destinationServiceConfigObj";
	public ConnectConfiguration connectconfiguration = null;

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		
		MuleMessage muleMessage = eventContext.getMessage();

		// Get Destination configuration properties file name.
		String ftpvar=eventContext.getMessage().getInvocationProperty("propertyFileNameVar");

		

		connectconfiguration = new ConnectConfiguration();

		String configRoot = System.getProperty("env.configRoot");
		String decryptionAlgorithm = System.getProperty("env.decryptionAlgorithm");
		String decryptionPassword = System.getProperty("env.decryptionPassword");
		
		connectconfiguration.setDecryptionAlgorithm(decryptionAlgorithm);
		connectconfiguration.setDecryptionPassword(decryptionPassword);
		connectconfiguration.setConfigRoot(configRoot);
		connectconfiguration.setLocation(new ClassPathResource(
				ftpvar));
		String host=connectconfiguration.get("connect.host");
		System.out.println("host details from class "+ host);
		muleMessage.setInvocationProperty(DESTINATION_SERVICE_CONFIG_OBJECT,connectconfiguration);

		return muleMessage.getPayload();
	}

}