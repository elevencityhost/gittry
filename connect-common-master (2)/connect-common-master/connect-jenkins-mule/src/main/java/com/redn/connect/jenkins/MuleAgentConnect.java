package com.redn.connect.jenkins;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redn.connect.jenkins.vo.Application;
import com.redn.connect.jenkins.vo.ApplicationAction;

public class MuleAgentConnect {
	
	static enum OperationType {
		UNDEPLOYMENT,
		VERIFY_DEPLOYMENT
	}
	
	public static final int DEFAULT_WAIT_TIME_SECS = 30;
	
	public static final int DEFAULT_NUM_OF_RETRIES = 10;

	public String baseUrl;
	
	public String applicationName;
	
	public int waitTimeInSecs = DEFAULT_WAIT_TIME_SECS;
	
	public int noOfRetries = DEFAULT_NUM_OF_RETRIES;
	
	public OperationType operationType = null;
	
	public static void main(String args[]) throws Exception{
		
		//args[0] -> base url
		//args[1] -> application name
		//args[2] -> wait time in seconds
		//args[3] -> number of retries
		
		MuleAgentConnect status = new MuleAgentConnect();
		
		if(args.length < 3){
			throw new Exception("operation type, base url and application name missing in the arguments");
		}else{
			int operationType = Integer.parseInt(args[0]);
			if(operationType != 1  && operationType != 2 ){
				throw new Exception("Invalid Operation Type. Use 1 for undeployment and 2 for verifying successful deployment");
			}else{
				if(operationType == 1){
					status.operationType = OperationType.UNDEPLOYMENT;
				}else if(operationType == 2){
					status.operationType = OperationType.VERIFY_DEPLOYMENT;
				}
			}
			status.setBaseUrl(args[1]);
			status.setApplicationName(args[2]);
		}
		
		if(args.length > 3){
			status.setWaitTimeInSecs(Integer.parseInt(args[3]));
			if(args.length == 5){
				status.setNoOfRetries(Integer.parseInt(args[4]));
			}
		}
		
		System.out.println("Number of Retries: " + status.noOfRetries);
		System.out.println("Wait Time In Seconds: " + status.waitTimeInSecs);
		
		
	
		switch(status.operationType){
			case UNDEPLOYMENT:
				boolean isUnDeploySuccess = status.checkAndUndeploy();
				if(!isUnDeploySuccess){
					throw new Exception("Error In Undeploying previous version of application, please check Mule Logs for details");
				}
				break;
			case VERIFY_DEPLOYMENT:
				//Wait for 
				boolean isSuccess = false;
				if(!isSuccess){
					//Wait for some time and check
					for(int i=1 ; i <= status.getNoOfRetries() ; i++){
						Thread.sleep(status.getWaitTimeInSecs()*1000);
						isSuccess = status.checkStatus(i);
						if(isSuccess == true){
							break;
						}
					}
					if(!isSuccess){
						throw new Exception("Error In Deploying the application, please check Mule Logs for details");
					}
				}
				break;
		}
		
	}


	public boolean checkStatus(int count) throws Exception{
		
		System.out.println("#### Start - Attempt No: " + count + "####");
		
		//URL url = new URL("http://172.20.102.115:9998/mule/applications/o3klen009-1.0");
		String urlStr = getBaseUrl() + "/mule/applications/" + getApplicationName();
		
		boolean status = false;
		try {
			
			String responseStr = HttpConnectUtil.connectAndGetResponse(urlStr, "GET");
			
			ObjectMapper mapper = new ObjectMapper();
			//mapper.readValue(response.toString(), ApplicationStatus.class);
			
			//List<Application> values = mapper.readValue(response.toString(), new TypeReference<List<Application>>(){} );
			Application values = mapper.readValue(responseStr, Application.class );
			
			System.out.println("Values are: " + values);
			
			if(values.getState().equals("STARTED")){
				System.out.println("Deployed State is Success");
				status = true;
			}else{
				System.out.println("Deployed State:" + values.getState());
				status = false;
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
			throw e;
		} catch (JsonMappingException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		System.out.println("#### End - Attempt No: " + count + "####");
		
		return status;
	}
	
	public boolean checkAndUndeploy() throws Exception{
		
		System.out.println("#### Getting List of applications ####");
		
		//URL url = new URL("http://172.20.102.115:9998/mule/applications/o3klen009-1.0");
	
		//1. Get all the applications
		//2. Check whether there is an application starts with application name 
		//3. If there is an old version, un deploy it
		//4. Check whether undeployment is successful
		
		String artifactId = applicationName;
		int indexOfHyphen = applicationName.lastIndexOf('-');
		if(indexOfHyphen != -1){
			artifactId = applicationName.substring(0,indexOfHyphen);
		}
		
		HttpURLConnection httpConnection = null;
		BufferedReader reader = null;
		
		boolean status = false;
		boolean foundPrevVersion = false;
		
		System.out.println("Checking for application to undeploy starting with: " + artifactId);
		
		try {
			//1. Get appl the applications
			String urlStr = getBaseUrl() + "/mule/applications";
			
			System.out.println("########### URL as String #########"+urlStr);
			
			String response = HttpConnectUtil.connectAndGetResponse(urlStr, "GET");
			
			ObjectMapper mapper = new ObjectMapper();
			
			List<Application> values = mapper.readValue(response.toString(), new TypeReference<List<Application>>(){} );
			//Application values = mapper.readValue(response.toString(), Application.class );
			
			System.out.println("Values are: " + values);
			
			//Go through each application and see whether there is an old version
			for(Application eachApplication: values){
				String eachApplicationName = eachApplication.getName();
				//System.out.println("Application Name is: " + eachApplicationName);
				if(eachApplicationName.startsWith(artifactId)){
					foundPrevVersion = true;
					System.out.println("Found application to undeploy: " + eachApplicationName);
					status = undeploy(eachApplicationName);
				}
			} 
			/*
			if(values.getState().equals("STARTED")){
				System.out.println("Deployed State is Success");
				status = true;
			}else{
				System.out.println("Deployed State:" + values.getState());
				status = false;
			}
			*/
			
			
		} catch (JsonParseException e) {
			e.printStackTrace();
			throw e;
		} catch (JsonMappingException e) {
			e.printStackTrace();
			throw e;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw e;
		} catch (ProtocolException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			if(reader != null){
				try{
					reader.close();
				}catch(Exception e){
					//
				}
			}
			
			if(httpConnection != null){
				httpConnection.disconnect();
			}
		}
		
		if(foundPrevVersion == false){
			System.out.println("No Previous Version found for application: " + applicationName);
			status = true;
		}
		
		return status;
	}
	
	
	
	public boolean undeploy(String applicationName) throws Exception{
		
		System.out.println("#### Started Undeploying Application: " + applicationName + " ####");
		
		//URL url = new URL("http://172.20.102.115:9998/mule/applications/o3klen009-1.0");
		String urlStr = getBaseUrl() + "/mule/applications/" + applicationName;
		
		boolean status = false;
		try {
			
			String responseStr = HttpConnectUtil.connectAndGetResponse(urlStr, "DELETE");
			
			ObjectMapper mapper = new ObjectMapper();
			//mapper.readValue(response.toString(), ApplicationStatus.class);
			
			//List<Application> values = mapper.readValue(response.toString(), new TypeReference<List<Application>>(){} );
			ApplicationAction values = mapper.readValue(responseStr, ApplicationAction.class );
			
			System.out.println("Values are: " + values);
			
			if(values.getStatus().equals("Undeployment attempt started")){
				System.out.println("Undeployment attempt started SUCCESSFULLY for application: " + 
							applicationName);
				status = true;
			}else{
				System.out.println("Undeployment attempt FAILED for application: " + applicationName + 
						"; Message From Agent: " + values.getStatus());
				status = false;
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
			throw e;
		} catch (JsonMappingException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return status;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public int getWaitTimeInSecs() {
		return waitTimeInSecs;
	}

	public void setWaitTimeInSecs(int waitTimeInSecs) {
		this.waitTimeInSecs = waitTimeInSecs;
	}

	public int getNoOfRetries() {
		return noOfRetries;
	}

	public void setNoOfRetries(int noOfRetries) {
		this.noOfRetries = noOfRetries;
	}
}
