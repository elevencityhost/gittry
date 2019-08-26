package com.redn.connect.connectlogger;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.mule.api.MuleContext;
import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.param.InvocationHeaders;
import org.mule.api.annotations.param.Payload;
import org.mule.api.context.MuleContextAware;

import com.redn.connect.connectlogger.config.ConnectorConfig;

/**
 * 
 * @author Prathyusha.V
 *  
 *  This is to implement Connectloger connector business logic.
 */
@Connector(name="connectlogger", friendlyName="Connectlogger")
public class ConnectloggerConnector implements MuleContextAware {

    @Config
    ConnectorConfig config;
    
    @Inject
	MuleContext muleContext;
    
    ConnectLogger connectLogger;

	@PostConstruct
	public void initializeLoggerConfiguration() throws SQLException {

		//ConnectConfiguration connectConfig = new ConnectConfiguration();
		connectLogger = new ConnectLogger(config, getMuleContext());
		
		String configRoot = System.getProperty(ConnectLoggerConstants.ENV_CONFIGROOT);
		String decryptionAlgorithm = System.getProperty(ConnectLoggerConstants.ENV_DECRYPTION_ALGORITHM);
		String decryptionPassword = System.getProperty(ConnectLoggerConstants.ENV_DECRYPTION_PASSWORD);
		//String location = System.getProperty(ConnectLoggerConstants.ENV_LOCATION) ;

		/*if (null != location) {
			
			if (decryptionAlgorithm != null) {
					connectConfig.setDecryptionAlgorithm(decryptionAlgorithm);
			}

			if (decryptionPassword != null) {
				connectConfig.setDecryptionPassword(decryptionPassword);
			}

			connectConfig.setConfigRoot(configRoot);
			connectConfig.setLocation(new ClassPathResource(location));
		}*/
	}
	
	/**
	 * 
	 */
	@PreDestroy
	public void tearDown() {
		connectLogger.shutDown();
	}

	@Processor
	public void debug(@Payload Object payloadObject,
			@InvocationHeaders(ConnectLoggerConstants.FLOWHEADERS_VARS_INJECTION) Map<String, String> headers,
			String processState, String logMessage) {
		connectLogger.debug(payloadObject, headers, processState, logMessage, config);
	}

	@Processor
	public void error(@Payload Object payloadObject,
			@InvocationHeaders(ConnectLoggerConstants.FLOWHEADERS_VARS_INJECTION) Map<String, String> headers,
			String processState, String logMessage) {
		connectLogger.error(payloadObject, headers, processState, logMessage, config);
	}

	@Processor
	public void errorWithThrowable(@Payload Object payloadObject,
			@InvocationHeaders(ConnectLoggerConstants.FLOWHEADERS_VARS_INJECTION) Map<String, String> headers,
			String processState, String logMessage, Throwable error) {
		connectLogger.error(payloadObject, headers, processState, logMessage, error, config);
	}

	@Processor
	public void fatal(@Payload Object payloadObject,
			@InvocationHeaders(ConnectLoggerConstants.FLOWHEADERS_VARS_INJECTION) Map<String, String> headers,
			String processState, String logMessage) {
		connectLogger.fatal(payloadObject, headers, processState, logMessage, config);
	}

	@Processor
	public void fatalWithThrowable(@Payload Object payloadObject,
			@InvocationHeaders(ConnectLoggerConstants.FLOWHEADERS_VARS_INJECTION) Map<String, String> headers,
			String processState, String logMessage, Throwable error) {
		connectLogger.fatal(payloadObject, headers, processState, logMessage, error, config);
	}


	@Processor
	public void info(@Payload Object payloadObject,
			@InvocationHeaders(ConnectLoggerConstants.FLOWHEADERS_VARS_INJECTION) Map<String, String> headers,
			String processState, String logMessage ) {
		connectLogger.info(payloadObject, headers, processState, logMessage, config);
	}

	@Processor
	public void trace(@Payload Object payloadObject,
			@InvocationHeaders(ConnectLoggerConstants.FLOWHEADERS_VARS_INJECTION) Map<String, String> headers,
			String processState, String logMessage) {
		connectLogger.trace(payloadObject, headers, processState, logMessage, config);
	}

	@Processor
	public void warn(@Payload Object payloadObject,
			@InvocationHeaders(ConnectLoggerConstants.FLOWHEADERS_VARS_INJECTION) Map<String, String> headers,
			String processState, String logMessage) {
		connectLogger.warn(payloadObject, headers, processState, logMessage, config);
	}

	@Processor
	public void warnWithThrowable(@Payload Object payloadObject,
			@InvocationHeaders(ConnectLoggerConstants.FLOWHEADERS_VARS_INJECTION) Map<String, String> headers,
			String processState, String logMessage, Throwable error) {
		connectLogger.warn(payloadObject, headers, processState, logMessage, error, config);
	}

	@Processor
	public void all(@Payload Object payloadObject,
			@InvocationHeaders(ConnectLoggerConstants.FLOWHEADERS_VARS_INJECTION) Map<String, String> headers,
			String processState, String logMessage) {
		connectLogger.all(payloadObject, headers, processState, logMessage, config);
	}

	@Processor
	public void off() {
		connectLogger.off();
	}

    public ConnectorConfig getConfig() {
        return config;
    }

    public void setConfig(ConnectorConfig config) {
        this.config = config;
    }
    
    public MuleContext getMuleContext() {
		return muleContext;
	}

	public void setMuleContext(MuleContext muleContext) {
		this.muleContext = muleContext;
	}
}