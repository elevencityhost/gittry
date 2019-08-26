package com.redn.connect.cnaudit;

import java.io.IOException;

import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.param.Payload;
import org.mule.api.annotations.rest.HttpMethod;
import org.mule.api.annotations.rest.RestCall;
import org.mule.api.annotations.rest.RestQueryParam;
import org.mule.api.annotations.rest.RestUriParam;

import com.redn.connect.audit.pojo.json.AuditGetResponseJson;
import com.redn.connect.audit.pojo.json.AuditPostRequestJson;
import com.redn.connect.audit.pojo.json.AuditPutRequestJson;
import com.redn.connect.audit.pojo.json.AuditPutResponseJson;
import com.redn.connect.audit.pojo.json.StatusResponseJson;
import com.redn.connect.audit.pojo.json.ValidationPutRequestJson;
import com.redn.connect.audit.pojo.json.ValidationPutResponseJson;
import com.redn.connect.cnaudit.config.ConnectorConfig;

@Connector(name="cnaudit", friendlyName="Cnaudit")
public abstract class CnauditConnector 
{

	/**
	 * Configurable host name of the audit rest web service
	 */
	@RestUriParam("host")
	@Configurable
	private String host;
	
	/**
	 * Configurable port number of the audit rest web service
	 */
	@RestUriParam("port")
	@Configurable
	private String port;
	
	/**
	 * Configurable path of the  audit rest web service
	 */
	@RestUriParam("path")
	@Configurable
	private String path;
	
	

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * Invoke a service to get job details based on job_id.
	 * 
	 * {@sample.xml ../../../doc/e2eaudit-connector.xml.sample e2eaudit:lookup}
	 * 
	 * @param job_id
	 *            JOB_ID of the record
	 * @return
	 * @throws IOException
	 */
	@Processor
	@RestCall(uri = "http://{host}:{port}/{path}/audit", method = HttpMethod.GET, contentType = "application/json")
	public abstract AuditGetResponseJson lookup(@RestQueryParam("job_id") String job_id)
			throws IOException;
    
	/**
	 * Invoke a service to insert Audit record details into database.
	 * 
	 * {@sample.xml ../../../doc/e2eaudit-connector.xml.sample e2eaudit:insert}
	 * 
	 * @param msgPost
	 *            Message to be inserted in to database.
	 * @return
	 * @throws IOException
	 */
	@Processor
	@RestCall(uri = "http://{host}:{port}/{path}/audit", method = HttpMethod.POST, contentType = "application/json")
	public abstract StatusResponseJson insert(@Payload AuditPostRequestJson auditPostRequestJson) throws IOException;

	/**
	 * Invoke a service to verify the validation status is success or not.
	 * 
	 * {@sample.xml ../../../doc/e2eaudit-connector.xml.sample e2eaudit:verify}
	 * 
	 * @param msgVerify
	 *            Message to be verified in database.
	 * @return
	 * @throws IOException
	 */
	@Processor
	@RestCall(uri = "http://{host}:{port}/{path}/verify", method = HttpMethod.PUT, contentType = "application/json")
	public abstract AuditPutResponseJson verify(@Payload AuditPutRequestJson auditPutRequestJson) throws IOException;

	/**
	 * Invoke a service to validate the Audit records.
	 * 
	 * {@sample.xml ../../../doc/e2eaudit-connector.xml.sample
	 * e2eaudit:validate}
	 * 
	 * @param msgValidate
	 *            Message to be validated in database.
	 * @return
	 * @throws IOException
	 */
	@Processor
	@RestCall(uri = "http://{host}:{port}/{path}/validate", method = HttpMethod.PUT, contentType = "application/json")
	public abstract ValidationPutResponseJson validate(@Payload ValidationPutRequestJson validationPutRequestJson)
			throws IOException;

	/**
	 * Invoke a service to get the property value for output-count property
	 * based on job_id and interface_id.
	 * 
	 * {@sample.xml ../../../doc/e2eaudit-connector.xml.sample
	 * e2eaudit:get-output-count}
	 * 
	 * @param interface_id
	 *            interface_id of the record
	 * @param job_id
	 *            job_id of the record
	 * @return
	 * @throws IOException
	 */
	@Processor
	@RestCall(uri = "http://{host}:{port}/{path}/validate", method = HttpMethod.GET, contentType = "application/json")
	public abstract String getOutputCount(
			@RestQueryParam("interface_id") String interface_id,
			@RestQueryParam("job_id") String job_id) throws IOException;
}