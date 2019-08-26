package com.redn.connect.processor.connectconfig;


import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.ManagedReloadingStrategy;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.net.URL;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

public class ConnectConfiguration extends PropertyPlaceholderConfigurer {
	
	
	private FileConfiguration configuration = null;
	ManagedReloadingStrategy mReloadingStrategy = new ManagedReloadingStrategy();
	
	
	/** config root system properties **/
	String configRoot = System.getProperty(ConfigConstants.ENV_CONFIGROOT);
	
	
	Logger logger = Logger.getRootLogger();
	
	
	/** decryption algorithm system properties **/
	 private String decryptionAlgorithm = System.getProperty(ConfigConstants.EVN_ALGORITHM);

	 
	 /** decryption password from system properties **/
	 String decryptionPassword = System.getProperty(ConfigConstants.EVN_DECRYPTIONPASSWORD);
	 
	
	public String getDecryptionPassword() {
		return decryptionPassword;
	}

	public void setDecryptionPassword(String decryptionPassword) {
		this.decryptionPassword = decryptionPassword;
	}

	public String getDecryptionAlgorithm() {
		return decryptionAlgorithm;
	}

	public void setDecryptionAlgorithm(String decryptionAlgorithm) {
		this.decryptionAlgorithm = decryptionAlgorithm;
	}
	public void setConfigRoot(String configRoot) {
		this.configRoot = configRoot;
	}
	
	public void setConfigRoot(URL url) {
		this.configRoot = url.toExternalForm();
	}

	public String getConfigRoot() {
		return this.configRoot;
	}
	

	public void setLocation(Resource configPropertiesFileName) {
		logger.debug("Resource : " + configPropertiesFileName);
		try {
			//Combine configRoot and location if configRoot is a valid URL (file or http)
			if (isValidURL(configRoot) && !(configPropertiesFileName instanceof UrlResource)) {
				String urlLocation = configRoot + configPropertiesFileName.getFilename();
				UrlResource urlResource = new UrlResource(urlLocation);
				super.setLocation(urlResource);
				if (configuration == null) {
					configuration = new PropertiesConfiguration(urlResource.getURL());
					configuration.setReloadingStrategy(mReloadingStrategy);
				} 
				else {
					configuration.setURL(urlResource.getURL());
					configuration.reload();
				}
				logger.debug(String.format("The properties are loaded from: ", urlResource.getURL()));
			}
			//Otherwise read properties from classpath
		
			
			else {
				super.setLocation(configPropertiesFileName);
				if (configuration == null) {
					configuration = new PropertiesConfiguration(configPropertiesFileName.getFile());
				}
				else {
					configuration.setFile(configPropertiesFileName.getFile());
				}
			}
		}
		catch (IOException e) {
			logger.error("get ERROR",e.getCause());
		}
		catch (ConfigurationException e) {
			logger.error("get ERROR",e.getCause());
		}
	}
	
	public void reload() {
		if (configuration != null && configRoot != null) {
			logger.info("Reloading configuration...");
			configuration.reload();
			mReloadingStrategy.refresh();
		}
	}
	
	private boolean isValidURL(String path) {
		if (path != null) {
			if(path.startsWith("http://") || path.startsWith("file://")) {
				return true;
			}
		}
		return false;
	}				
			
	private String decryptPropertyValue(String encryptedPropertyVlue,String encryptPassword, String encryptAlgorithm) throws ConfigurationException {
		
		StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
		decryptor.setAlgorithm(encryptAlgorithm);
		decryptor.setPassword(encryptPassword);
		String decryptedPropertyValue = decryptor.decrypt(encryptedPropertyVlue);
		return decryptedPropertyValue;
	}
	
	public String get(Object key) {
		if (null != configuration) {
			String[] propertyValues = configuration.getStringArray(key.toString());
			if (null != propertyValues && propertyValues.length>0) {

				String strENCPropValue = propertyValues[0];

				if (null != strENCPropValue
						&& strENCPropValue.toString().startsWith("ENC(")
						&& strENCPropValue.toString().endsWith(")")) {
					String decryptPropertyValue = null;

					String encryptedPropertyVlue = propertyValues[0].substring(
							propertyValues[0].indexOf("("),
							propertyValues[0].lastIndexOf(")"));
					try {
						if (null != decryptionPassword) {
							decryptPropertyValue = decryptPropertyValue(
									encryptedPropertyVlue, decryptionPassword,
									decryptionAlgorithm);
						}

						configuration.setProperty((String) key,
								decryptPropertyValue);

					} catch (ConfigurationException e) {
						logger.error("get ERROR",e.getCause());
					}
					
					return decryptPropertyValue;

				} else {
					StringBuilder sbPropertyValues = new StringBuilder();
					for (int i = 0; i < propertyValues.length; i++) {

						if (i != 0)
							sbPropertyValues.append(',');
						sbPropertyValues.append(propertyValues[i]);

					}
					return sbPropertyValues.toString();
					
				}

			}

		}

		return null;
	}
	
	public FileConfiguration getConfiguration(){
		return configuration;
	}
	
	/**
	 * This method is used to decrypt the encrypted values while setting the
	 * location
	 */
	protected String convertPropertyValue(String originalValue) {
		if (originalValue.startsWith("ENC(") && originalValue.endsWith(")")) {
			try {
				originalValue = originalValue.substring(
						originalValue.indexOf("("),
						originalValue.lastIndexOf(")"));
				return decryptPropertyValue(originalValue, decryptionPassword,
						decryptionAlgorithm);
			} catch (ConfigurationException e) {
				logger.error("Error in decryption", e.getCause());
			}
		}
		return originalValue;
	}	


}

