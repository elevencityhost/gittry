package com.redn.connect.component;

import java.io.File;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import com.redn.connect.processor.connectconfig.ConnectConfiguration;
import com.redn.connect.vo.OutputProps;
import com.redn.connect.connectfileinbound.constants.FPConstants;
import java.util.ArrayList;
/**
 * @author Sai Prasad Jonnala
 *  This java class will set the target Directory
 */

public class SetTargetDirectoryAttributeToMessage implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		String interfaceName = null;
		String targetDirectory = null;
ConnectConfiguration connectConfig = eventContext.getMuleContext().getRegistry()
				.lookupObject("connectConfigBean");
				
/*		ConnectConfiguration connectConfig =new ConnectConfiguration();
		connectConfig.setLocation(new ClassPathResource("connectfileinbound.properties"));		
			*/	
				
				
		MuleMessage message = eventContext.getMessage();
		OutputProps outputProps = new OutputProps();
		String fileName = eventContext.getMessage().getInboundProperty(FPConstants.ORIGINAL_FILENAME);
		outputProps.setFileName(fileName);
		outputProps.setInterfaceName(fileName);
		if (fileName != null) {
			interfaceName = getValueByInputDelimeter(fileName, 1, null);
			message.setProperty("messageSource", interfaceName + "-INBOUND", PropertyScope.INVOCATION);
			targetDirectory = getTargetDirectory(connectConfig, interfaceName);
			if (targetDirectory != null && targetDirectory.trim().length() > 0) {
				outputProps.setInterfaceName(interfaceName);
				outputProps.setTargetDirectory(targetDirectory);
			}
		}
		outputProps.setInputFileLocation(message.getInboundProperty("directory") + File.separator + fileName);

		//outputProps.setTargetFileLocation(outputProps.getTargetDirectory() +File.separator  + fileName);
		outputProps.setTargetFileLocation(outputProps.getTargetDirectory() +File.separator+ fileName);

		outputProps.setResourceId(outputProps.getTargetDirectory() + File.separator + fileName);
		message.setProperty(FPConstants.OUTPUT_PROPS, outputProps, PropertyScope.INVOCATION);
		return message;
	}

	/**
	 * This method returns the target based on the input interface name we pass
	 */
	private String getTargetDirectory(ConnectConfiguration connectConfiguration, String interfaceName) {

		return connectConfiguration.get("connect.fib." + interfaceName);
	}
	
	public String getValueByInputDelimeter(String fileName, int position, String delimeter)
			throws Exception {
		validateFileName(fileName, position, false);
		if(delimeter != null ){
			String[] splitArray = fileName.split(delimeter);
			return splitArray[position - 1];
		}else{
			
			int dotPosition = 0;
			int hyphenPosition = 0;
			int underScorePosition = 0;

			ArrayList<Integer> positionArray = new ArrayList<Integer>();
			if (fileName.contains(".")) {
				dotPosition = fileName.indexOf(".");
				positionArray.add(dotPosition);
			}
			if (fileName.contains("-")) {
				hyphenPosition = fileName.indexOf("-");
				positionArray.add(hyphenPosition);
			}
			if (fileName.contains("_")) {
				underScorePosition = fileName.indexOf("_");
				positionArray.add(underScorePosition);
			}

			int smallest = Integer.MAX_VALUE;
			for (int i = 0; i < positionArray.size(); i++) {
				if (smallest > positionArray.get(i)) {
					smallest = positionArray.get(i);
				}
			}
			return fileName.substring(0,smallest);
		}
	}
		private void validateFileName(String fileName, int position, boolean isByKey)
				throws Exception {

			if (!fileName.contains("."))
				throw new Exception(FPConstants.INVALID_FILENAME);
			
			String[] splitArray = fileName.split("\\.");
			int splitArrayLength = splitArray.length;
			if (isByKey) {
				if (splitArrayLength < 6 || splitArrayLength > 6)
					throw new Exception(FPConstants.INVALID_FILENAME);
			} else {
				if (position <= 0)
					throw new Exception(FPConstants.POSITION_NOT_ZERO);
				
				if (position > splitArrayLength) {

					throw new Exception(FPConstants.INVALID_POSITION);
				}
				

			}
}}
