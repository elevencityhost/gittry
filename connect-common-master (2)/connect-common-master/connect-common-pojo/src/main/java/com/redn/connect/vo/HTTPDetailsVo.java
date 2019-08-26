package com.redn.connect.vo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

public class HTTPDetailsVo {
	
	private String host;
	private String port;
	private String basePath;
	private String path;
	private String method;
	private Map<String, String> queryParams = new HashMap<String, String>();
	private Map<String, String> uriParams = new HashMap<String, String>();
	private Map<String, String> headers = new HashMap<String, String>();
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basPath) {
		this.basePath = basPath;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Map<String, String> getQueryParams() {
		return queryParams;
	}
	public void setQueryParams(Map<String, String> queryParams) {
		this.queryParams = queryParams;
	}
	public Map<String, String> getUriParams() {
		return uriParams;
	}
	public void setUriParams(Map<String, String> uriParams) {
		this.uriParams = uriParams;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	
	@Override
	public String toString() {
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = "";
		try {
			json = ow.writeValueAsString(this);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Http Details JSON Data ====== " + json);
		return json;
	}

}
