package com.redn.connect.vo;

import java.io.Serializable;

/**
 * @author Sai Prasad Jonnala
 * 
 * To set the custom Properties
 */
public class CustomProperties implements Serializable {

	private static final long serialVersionUID = -5481523156652134325L;

	private boolean isTriggerFileEnabled;

	private boolean useInProcessExtension;

	private boolean useCustomProps;
	
	private boolean invokeAPIGateway;

	private boolean removeInterfaceName;

	private String customPropertyInterfaceName;

	private String triggerFileName;

	private String delimiter;

	private String inputFileExtension;

	private String triggerFileExtension;

	private String fileNames;

	private String renameTriggerFileName;

	private String archieveFilePath;
	
	private boolean isTriggerFileReceived;

	/**
	 * @return the isTriggerFileEnabled
	 */
	public boolean isTriggerFileEnabled() {

		return isTriggerFileEnabled;
	}

	/**
	 * @param isTriggerFileEnabled
	 *            the isTriggerFileEnabled to set
	 */
	public void setTriggerFileEnabled(boolean isTriggerFileEnabled) {

		this.isTriggerFileEnabled = isTriggerFileEnabled;
	}

	/**
	 * @return the useInProcessExtension
	 */
	public boolean isUseInProcessExtension() {

		return useInProcessExtension;
	}

	/**
	 * @param useInProcessExtension
	 *            the useInProcessExtension to set
	 */
	public void setUseInProcessExtension(boolean useInProcessExtension) {

		this.useInProcessExtension = useInProcessExtension;
	}

	/**
	 * @return the useCustomProps
	 */
	public boolean isUseCustomProps() {

		return useCustomProps;
	}

	/**
	 * @param useCustomProps
	 *            the useCustomProps to set
	 */
	public void setUseCustomProps(boolean useCustomProps) {

		this.useCustomProps = useCustomProps;
	}

	/**
	 * @return the removeInterfaceName
	 */
	public boolean isRemoveInterfaceName() {

		return removeInterfaceName;
	}

	/**
	 * @param removeInterfaceName
	 *            the removeInterfaceName to set
	 */
	public void setRemoveInterfaceName(boolean removeInterfaceName) {

		this.removeInterfaceName = removeInterfaceName;
	}

	/**
	 * @return the triggerFileName
	 */
	public String getTriggerFileName() {

		return triggerFileName;
	}

	/**
	 * @param triggerFileName
	 *            the triggerFileName to set
	 */
	public void setTriggerFileName(String triggerFileName) {

		this.triggerFileName = triggerFileName;
	}

	/**
	 * @return the delimiter
	 */
	public String getDelimiter() {

		return delimiter;
	}

	/**
	 * @param delimiter
	 *            the delimiter to set
	 */
	public void setDelimiter(String delimiter) {

		this.delimiter = delimiter;
	}

	/**
	 * @return the inputFileExtension
	 */
	public String getInputFileExtension() {

		return inputFileExtension;
	}

	/**
	 * @param inputFileExtension
	 *            the inputFileExtension to set
	 */
	public void setInputFileExtension(String inputFileExtension) {

		this.inputFileExtension = inputFileExtension;
	}

	/**
	 * @return the triggerFileExtension
	 */
	public String getTriggerFileExtension() {

		return triggerFileExtension;
	}

	/**
	 * @param triggerFileExtension
	 *            the triggerFileExtension to set
	 */
	public void setTriggerFileExtension(String triggerFileExtension) {

		this.triggerFileExtension = triggerFileExtension;
	}

	/**
	 * @return the fileNames
	 */
	public String getFileNames() {

		return fileNames;
	}

	/**
	 * @param fileNames
	 *            the fileNames to set
	 */
	public void setFileNames(String fileNames) {

		this.fileNames = fileNames;
	}

	/**
	 * @return the renameTriggerFileName
	 */
	public String getRenameTriggerFileName() {

		return renameTriggerFileName;
	}

	/**
	 * @param renameTriggerFileName
	 *            the renameTriggerFileName to set
	 */
	public void setRenameTriggerFileName(String renameTriggerFileName) {

		this.renameTriggerFileName = renameTriggerFileName;
	}

	/**
	 * @return the archieveFilePath
	 */
	public String getArchieveFilePath() {

		return archieveFilePath;
	}

	/**
	 * @param archieveFilePath
	 *            the archieveFilePath to set
	 */
	public void setArchieveFilePath(String archieveFilePath) {

		this.archieveFilePath = archieveFilePath;
	}

	
	/**
	 * @return the customPropertyInterfaceName
	 */
	public String getCustomPropertyInterfaceName() {
	
		return customPropertyInterfaceName;
	}

	
	/**
	 * @param customPropertyInterfaceName the customPropertyInterfaceName to set
	 */
	public void setCustomPropertyInterfaceName(String customPropertyInterfaceName) {
	
		this.customPropertyInterfaceName = customPropertyInterfaceName;
	}

	
	/**
	 * @return the invokeAPIGateway
	 */
	public boolean isInvokeAPIGateway() {
	
		return invokeAPIGateway;
	}

	
	/**
	 * @param invokeAPIGateway the invokeAPIGateway to set
	 */
	public void setInvokeAPIGateway(boolean invokeAPIGateway) {
	
		this.invokeAPIGateway = invokeAPIGateway;
	}

	
	/**
	 * @return the isTriggerFileReceived
	 */
	public boolean isTriggerFileReceived() {
	
		return isTriggerFileReceived;
	}

	
	/**
	 * @param isTriggerFileReceived the isTriggerFileReceived to set
	 */
	public void setTriggerFileReceived(boolean isTriggerFileReceived) {
	
		this.isTriggerFileReceived = isTriggerFileReceived;
	}

}
