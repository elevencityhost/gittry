package com.redn.connect.o3klen018.pojo.response;

import java.io.Serializable;

public class ReceivedRequestBody implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Request response;

	public Request getResponse() {
		return response;
	}

	public void setResponse(Request response) {
		this.response = response;
	}


}
