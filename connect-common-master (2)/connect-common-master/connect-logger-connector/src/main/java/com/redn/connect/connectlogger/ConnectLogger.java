package com.redn.connect.connectlogger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.mule.api.MuleContext;
import org.mule.api.annotations.param.Payload;
import org.mule.api.context.MuleContextAware;

import com.redn.connect.connectlogger.config.ConnectorConfig;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.EnterpriseHeader;

/**
 * 
 * @author Prathyusha.V
 *
 *   This class will implement the IConnectLogger interface and also pass the required
 *   values to ThreadContext map dynamically through log4j2.xml JDBC appender. 
 */
public class ConnectLogger implements IConnectLogger,MuleContextAware{

	@Inject
	MuleContext muleContext;

	Logger enterpriseLogger = LogManager.getLogger();
	
	private static final long serialVersionUID = 674178030419190802L;

	String configRoot = System.getProperty(ConnectLoggerConstants.ENV_CONFIGROOT);	

	/** The location. *//*
	String location = (System.getProperty(ConnectLoggerConstants.ENV_LOCATION) == null
			? ConnectLoggerConstants.LOG_CONFIG_NAME : System.getProperty(ConnectLoggerConstants.ENV_LOCATION));
*/
	String location = null;
	/** The decryption algorithm. */
	String decryptionAlgorithm = System.getProperty(ConnectLoggerConstants.ENV_DECRYPTION_ALGORITHM);

	/** The decryption password. */
	String decryptionPassword = System.getProperty(ConnectLoggerConstants.ENV_DECRYPTION_PASSWORD);

	// this logger code will be used for mule common logging
	public static String common_logger_code = "100100100";


	public  ConnectLogger(ConnectorConfig config, MuleContext muleContext) {
		
	
		this.muleContext = muleContext;
		

	}

	@Override
	public void debug(Object payloadobj, Map<String, String> headers, String processState, String logMsg,ConnectorConfig config) {
		putPayloadParams(payloadobj, headers, processState,config);
		if (null != logMsg) {
			enterpriseLogger.log(Level.toLevel(ConnectLoggerConstants.DEBUG), logMsg);
		}

		// Set default logger code for mule logging statements
		if (null == processState) {
			ThreadContext.put(ConnectLoggerConstants.PROCESS_STATE, common_logger_code);
		}
	}

	@Override
	public void error(Object payloadobj, Map<String, String> headers, String processState, String logMsg,ConnectorConfig config) {
		putPayloadParams(payloadobj, headers, processState, config);
		if (null != logMsg) {
			enterpriseLogger.log(Level.toLevel(ConnectLoggerConstants.ERROR), logMsg);
		}

		// Set default logger code for mule logging statements
		if (null == processState) {
			ThreadContext.put(ConnectLoggerConstants.PROCESS_STATE, common_logger_code);
		}
	}

	@Override
	public void error(Object payloadobj, Map<String, String> headers, String processState, String logMsg,
			Throwable error,ConnectorConfig config) {
		putPayloadParams(payloadobj, headers, processState, config);
		if (null != logMsg && null != error) {
			boolean isErrorThrowable = error instanceof Throwable;
			if (isErrorThrowable) {
				ThreadContext.put(ConnectLoggerConstants.ERROR_MESSAGE, error.getMessage());
				enterpriseLogger.log(Level.toLevel(ConnectLoggerConstants.ERROR), logMsg, error);
			} else {
				ThreadContext.put(ConnectLoggerConstants.ERROR_MESSAGE, error.getMessage());
				enterpriseLogger.log(Level.toLevel(ConnectLoggerConstants.ERROR), logMsg);
			}
		}
		ThreadContext.remove(ConnectLoggerConstants.ERROR_MESSAGE);

		// Set default logger code for mule logging statements
		if (null == processState) {
			ThreadContext.put(ConnectLoggerConstants.PROCESS_STATE, common_logger_code);
		}
	}

	@Override
	public void fatal(Object payloadobj, Map<String, String> headers, String processState, String logMsg,ConnectorConfig config) {
		putPayloadParams(payloadobj, headers, processState, config);
		if (null != logMsg) {
			enterpriseLogger.log(Level.toLevel(ConnectLoggerConstants.FATAL), logMsg);
		}

		// Set default logger code for mule logging statements
		if (null == processState) {
			ThreadContext.put(ConnectLoggerConstants.PROCESS_STATE, common_logger_code);
		}
	}

	@Override
	public void fatal(Object payloadobj, Map<String, String> headers, String processState, String logMsg,
			Throwable error,ConnectorConfig config) {
		putPayloadParams(payloadobj, headers, processState, config);
		if (null != logMsg && null != error) {
			boolean isErrorThrowable = error instanceof Throwable;
			if (isErrorThrowable) {
				ThreadContext.put(ConnectLoggerConstants.ERROR_MESSAGE, error.getMessage());
				enterpriseLogger.log(Level.toLevel(ConnectLoggerConstants.FATAL), logMsg, error);
			} else {
				ThreadContext.put(ConnectLoggerConstants.ERROR_MESSAGE, error.getMessage());
				enterpriseLogger.log(Level.toLevel(ConnectLoggerConstants.FATAL), logMsg);
			}
		}
		ThreadContext.remove(ConnectLoggerConstants.ERROR_MESSAGE);

		// Set default logger code for mule logging statements
		if (null == processState) {
			ThreadContext.put(ConnectLoggerConstants.PROCESS_STATE, common_logger_code);
		}
	}

	@Override
	public void info(Object payloadobj, Map<String, String> headers, String processState, String logMsg, ConnectorConfig config) {
		putPayloadParams(payloadobj, headers, processState , config );
		if (null != logMsg) {
			enterpriseLogger.log(Level.toLevel(ConnectLoggerConstants.INFO), logMsg);
		}

		// Set default logger code for mule logging statements
		if (null == processState) {
			ThreadContext.put(ConnectLoggerConstants.PROCESS_STATE, common_logger_code);
		}
	}

	@Override
	public void trace(Object payloadobj, Map<String, String> headers, String processState, String logMsg,ConnectorConfig config) {
		putPayloadParams(payloadobj, headers, processState, config);
		if (null != logMsg) {
			enterpriseLogger.log(Level.toLevel(ConnectLoggerConstants.TRACE), logMsg);
		}

		// Set default logger code for mule logging statements
		if (null == processState) {
			ThreadContext.put(ConnectLoggerConstants.PROCESS_STATE, common_logger_code);
		}
	}

	@Override
	public void warn(Object payloadobj, Map<String, String> headers, String processState, String logMsg,ConnectorConfig config) {
		putPayloadParams(payloadobj, headers, processState, config);
		if (null != logMsg) {
			enterpriseLogger.log(Level.toLevel(ConnectLoggerConstants.WARN), logMsg);
		}

		// Set default logger code for mule logging statements
		if (null == processState) {
			ThreadContext.put(ConnectLoggerConstants.PROCESS_STATE, common_logger_code);
		}
	}

	@Override
	public void warn(Object payloadobj, Map<String, String> headers, String processState, String logMsg,
			Throwable error,ConnectorConfig config) {
		putPayloadParams(payloadobj, headers, processState, config);

		if (null != logMsg && null != error) {
			boolean isErrorThrowable = error instanceof Throwable;
			if (isErrorThrowable) {
				ThreadContext.put(ConnectLoggerConstants.ERROR_MESSAGE, error.getMessage());
				enterpriseLogger.log(Level.toLevel(ConnectLoggerConstants.WARN), logMsg, error);
			} else {
				ThreadContext.put(ConnectLoggerConstants.ERROR_MESSAGE, error.getMessage());
				enterpriseLogger.log(Level.toLevel(ConnectLoggerConstants.WARN), logMsg);
			}
		}
		ThreadContext.remove(ConnectLoggerConstants.ERROR_MESSAGE);

		// Set default logger code for mule logging statements
		if (null == processState) {
			ThreadContext.put(ConnectLoggerConstants.PROCESS_STATE, common_logger_code);
		}
	}

	@Override
	public void all(Object payloadobj, Map<String, String> headers, String processState, String logMsg,ConnectorConfig config) {
		putPayloadParams(payloadobj, headers, processState,config);
		if (null != logMsg) {
			enterpriseLogger.log(Level.toLevel(ConnectLoggerConstants.ALL), logMsg);
		}

		// Set default logger code for mule logging statements
		if (null == processState) {
			ThreadContext.put(ConnectLoggerConstants.PROCESS_STATE, common_logger_code);
		}
	}

	@Override
	public void all(Object payloadobj, Map<String, String> headers, String processState, String logMsg,
			Throwable error,ConnectorConfig config) {
		putPayloadParams(payloadobj, headers, processState ,config);

		if (null != logMsg && null != error) {
			boolean isErrorThrowable = error instanceof Throwable;
			if (isErrorThrowable) {
				ThreadContext.put(ConnectLoggerConstants.ERROR_MESSAGE, error.getMessage());
				enterpriseLogger.log(Level.toLevel(ConnectLoggerConstants.ALL), logMsg, error);
			} else {
				ThreadContext.put(ConnectLoggerConstants.ERROR_MESSAGE, error.getMessage());
				enterpriseLogger.log(Level.toLevel(ConnectLoggerConstants.ALL), logMsg);
			}
		}
		ThreadContext.remove(ConnectLoggerConstants.ERROR_MESSAGE);

		// Set default logger code for mule logging statements
		if (null == processState) {
			ThreadContext.put(ConnectLoggerConstants.PROCESS_STATE, common_logger_code);
		}
	}

	@Override
	public void off() {
		//enterpriseLogger.setLevel(Level.OFF);
	}

	public void putPayloadParams(@Payload Object emObject, Map<String, String> headers, String processState, ConnectorConfig config) {
		if (null != emObject) {
			
			if (null != config.getLoggerName()) {
				ThreadContext.put(ConnectLoggerConstants.LOGGER_NAME, config.getLoggerName());
			}
							
			try {
				ThreadContext.put(ConnectLoggerConstants.NODE_NAME, InetAddress.getLocalHost().getHostName());
			} catch (UnknownHostException e) {

				enterpriseLogger.error("Error while getting the NodeName in EnterpriseLogger.java Class", e);
			}

			if (emObject instanceof ConnectEnterpriseMessage) {

				ConnectEnterpriseMessage enterpriseMessage = (ConnectEnterpriseMessage) emObject;
				EnterpriseHeader enterpriseHeader = enterpriseMessage.getEnterpriseHeader();
				if (null != enterpriseHeader) {

					if (null != enterpriseHeader.getMessageId()) {
						ThreadContext.put(ConnectLoggerConstants.MESSAGE_ID, enterpriseHeader.getMessageId());
					}

					if (null != enterpriseHeader.getAction()) {
						ThreadContext.put(ConnectLoggerConstants.ACTION, enterpriseHeader.getAction());

					}

					if (null != enterpriseHeader.getMessageSource()) {
						ThreadContext.put(ConnectLoggerConstants.SOURCE, enterpriseHeader.getMessageSource());

					}

					if (null != enterpriseMessage.getEnterpriseHeader()) {

						if (null != enterpriseHeader.getMessageSource()) {
							ThreadContext.put(ConnectLoggerConstants.MESSAGE_SOURCE,
									enterpriseMessage.getEnterpriseHeader().getMessageSource());

						}
						
						if (null != enterpriseHeader.getServiceName()) {
							ThreadContext.put(ConnectLoggerConstants.SERVICE_NAME,
									enterpriseMessage.getEnterpriseHeader().getServiceName());
						}

					}

				}

			} 
			else {
				
				if (null != headers.get(ConnectLoggerConstants.HEADER_KEY_MESSAGE_ID)) {
					ThreadContext.put(ConnectLoggerConstants.MESSAGE_ID,
							headers.get(ConnectLoggerConstants.HEADER_KEY_MESSAGE_ID));
				}
				if (null != headers.get(ConnectLoggerConstants.HEADER_KEY_MESSAGE_SOURCE)) {
					ThreadContext.put(ConnectLoggerConstants.SOURCE,
							headers.get(ConnectLoggerConstants.HEADER_KEY_MESSAGE_SOURCE));
				}
				if (null != headers.get(ConnectLoggerConstants.HEADER_KEY_MESSAGE_ACTION)) {
					ThreadContext.put(ConnectLoggerConstants.ACTION,
							headers.get(ConnectLoggerConstants.HEADER_KEY_MESSAGE_ACTION));
				}
				if (null != headers.get(ConnectLoggerConstants.HEADER_KEY_RESOURCE_ID)) {
					ThreadContext.put(ConnectLoggerConstants.MESSAGE_SOURCE,
							headers.get(ConnectLoggerConstants.HEADER_KEY_RESOURCE_ID));
				}
				if (null != headers.get(ConnectLoggerConstants.HEADER_KEY_RESOURCE_NAME)) {
					ThreadContext.put(ConnectLoggerConstants.SERVICENAME,
							headers.get(ConnectLoggerConstants.HEADER_KEY_RESOURCE_NAME));
				}
				if (null != headers.get(ConnectLoggerConstants.HEADER_KEY_SOURCE_ID)) {
					ThreadContext.put(ConnectLoggerConstants.SOURCE_ID,
							headers.get(ConnectLoggerConstants.HEADER_KEY_SOURCE_ID));
				}
			}
			
		}
		if (null != processState) {
			ThreadContext.put(ConnectLoggerConstants.PROCESS_STATE, processState);
		}

	}

	@Override
	public String getConfigRoot() {
		return configRoot;
	}

	@Override
	public void setConfigRoot(String configRoot) {
		this.configRoot = configRoot;
	}

	@Override
	public String getDecryptionAlgorithm() {
		return decryptionAlgorithm;
	}

	@Override
	public void setDecryptionAlgorithm(String decryptionAlgorithm) {
		this.decryptionAlgorithm = decryptionAlgorithm;
	}

	@Override
	public String getDecryptionPassword() {
		return decryptionPassword;
	}

	@Override
	public void setDecryptionPassword(String decryptionPassword) {
		this.decryptionPassword = decryptionPassword;
	}

	@Override
	public String getLocation() {
		return location;
	}
	@Override
	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public void shutDown() {
		System.out.println("What to do here? LogManager is shared with others!");
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public MuleContext getMuleContext() {
		return muleContext;
	}

	public void setMuleContext(MuleContext muleContext) {
		this.muleContext = muleContext;
	}


}
