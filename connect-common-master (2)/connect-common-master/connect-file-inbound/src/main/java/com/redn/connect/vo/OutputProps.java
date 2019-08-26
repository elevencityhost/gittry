/**
 * 
 */
package com.redn.connect.vo;

import java.io.Serializable;
/**
 * @author Sai Prasad Jonnala
 * 
 * This is a pojo class that includes the OutputProps
 */
public class OutputProps implements Serializable {

	private static final long serialVersionUID = 4101452026254100556L;

	private CustomProperties customProperties = new CustomProperties();
	
	private String fileName;
	
	private String interfaceName;
	
	private String resourceId;
	
	private boolean isCustomInterface;
	
	private String targetDirectory;
	
	private String inputFileLocation;
	
	private String targetFileLocation;
	
	private boolean isFileAlreadyEsists;

	
	/**
	 * @return the customProperties
	 */
	public CustomProperties getCustomProperties() {
	
		return customProperties;
	}

	
	/**
	 * @param customProperties the customProperties to set
	 */
	public void setCustomProperties(CustomProperties customProperties) {
	
		this.customProperties = customProperties;
	}

	
	/**
	 * @return the interfaceName
	 */
	public String getInterfaceName() {
	
		return interfaceName;
	}

	
	/**
	 * @param interfaceName the interfaceName to set
	 */
	public void setInterfaceName(String interfaceName) {
	
		this.interfaceName = interfaceName;
	}

	
	/**
	 * @return the resourceId
	 */
	public String getResourceId() {
	
		return resourceId;
	}

	
	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(String resourceId) {
	
		this.resourceId = resourceId;
	}

	
	/**
	 * @return the isCustomInterface
	 */
	public boolean isCustomInterface() {
	
		return isCustomInterface;
	}

	
	/**
	 * @param isCustomInterface the isCustomInterface to set
	 */
	public void setCustomInterface(boolean isCustomInterface) {
	
		this.isCustomInterface = isCustomInterface;
	}


	
	/**
	 * @return the targetDirectory
	 */
	public String getTargetDirectory() {
	
		return targetDirectory;
	}


	
	/**
	 * @param targetDirectory the targetDirectory to set
	 */
	public void setTargetDirectory(String targetDirectory) {
	
		this.targetDirectory = targetDirectory;
	}


	
	/**
	 * @return the inputFileLocation
	 */
	public String getInputFileLocation() {
	
		return inputFileLocation;
	}


	
	/**
	 * @param inputFileLocation the inputFileLocation to set
	 */
	public void setInputFileLocation(String inputFileLocation) {
	
		this.inputFileLocation = inputFileLocation;
	}


	
	/**
	 * @return the targetFileLocation
	 */
	public String getTargetFileLocation() {
	
		return targetFileLocation;
	}


	
	/**
	 * @param targetFileLocation the targetFileLocation to set
	 */
	public void setTargetFileLocation(String targetFileLocation) {
	
		this.targetFileLocation = targetFileLocation;
	}


	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
	
		return fileName;
	}


	
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
	
		this.fileName = fileName;
	}


	
	/**
	 * @return the isFileAlreadyEsists
	 */
	public boolean isFileAlreadyEsists() {
	
		return isFileAlreadyEsists;
	}


	
	/**
	 * @param isFileAlreadyEsists the isFileAlreadyEsists to set
	 */
	public void setFileAlreadyEsists(boolean isFileAlreadyEsists) {
	
		this.isFileAlreadyEsists = isFileAlreadyEsists;
	}
}
