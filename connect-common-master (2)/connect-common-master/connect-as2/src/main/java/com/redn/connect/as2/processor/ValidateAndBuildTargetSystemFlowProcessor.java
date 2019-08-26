package com.redn.connect.as2.processor;

import java.util.ArrayList;
import java.util.List;

import org.mule.api.MuleContext;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.context.MuleContextAware;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.exception.Category;
import com.redn.connect.exception.ConnectException;
import com.redn.connect.processor.connectconfig.ConnectConfiguration;
import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.as2.constants.AS2Constants;

/**
 * @author Vinay Kumar Thota
 * 
 * This class is used to validate the target system and builds target system
 * send flow. It reads the target system and validates it against the 
 * list of supported target systems.
 * If target system is not valid then an exception is thrown.
 *
 */
public class ValidateAndBuildTargetSystemFlowProcessor implements Callable, MuleContextAware {

	private static List<String> supportedTargetSystems = new ArrayList<>();
	
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		
		MuleMessage message = eventContext.getMessage();
		
		String targetSystem = message.getInvocationProperty(AS2Constants.VAR_TARGET_SYSTEM);
		
		if(targetSystem == null){
			throw new ConnectException(AS2Constants.ERROR_CODE_TARGET_SYSTEM_NOT_PRESENT, 
					"Target System is null", 
					Category.DATA, ConnectConstants.CONST_EXCEPTION_ORIGIN_MULE_ESB_NS);
		}
		
		if(!supportedTargetSystems.contains(targetSystem.toLowerCase())){
			throw new ConnectException(AS2Constants.ERROR_CODE_TARGET_SYSTEM_NOT_SUPPORTED, 
					"Target System: " + targetSystem + " is not supported", 
					Category.DATA, ConnectConstants.CONST_EXCEPTION_ORIGIN_MULE_ESB_NS);
		}
		
        String targetSystemFlow = targetSystem.toLowerCase() + AS2Constants.TARGET_SYSTEM_SEND_FLOW_SUFFIX;

        message.setInvocationProperty(AS2Constants.VAR_TARGET_SYSTEM_SEND_FLOW, targetSystemFlow);
		
		return message;
	}

	@Override
	public void setMuleContext(MuleContext context) {
		ConnectConfiguration connectConfig = 
				context.getRegistry().lookupObject(AS2Constants.BEAN_CONNECT_CONFIG);
		String supportedTargetSystemsStr = connectConfig.get(AS2Constants.PROP_SUPPORTED_TARGET_SYSTEMS);
		if(supportedTargetSystemsStr != null){
			String[] supportedTargetSystemList = supportedTargetSystemsStr.split(",");
			for(String eachSystem: supportedTargetSystemList){
					supportedTargetSystems.add(eachSystem.toLowerCase().trim());
			}
		}
	}

}