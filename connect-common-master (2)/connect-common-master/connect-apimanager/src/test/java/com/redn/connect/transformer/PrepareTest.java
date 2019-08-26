package com.redn.connect.transformer;

import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.ConnectEnterpriseMessage.EnterpriseBody;
import com.redn.connect.vo.EnterpriseHeader;

public class PrepareTest {
	
	public static Object getCEM(String str)throws Exception
	{
		ConnectUtils connectUtils = new ConnectUtils();
		ConnectEnterpriseMessage cem=new ConnectEnterpriseMessage();
	    EnterpriseHeader head=new EnterpriseHeader();
	    head.setServiceName("o3klen013");
	    /*head.setAction("AS2");
	    head.setCommunication("Async");
	  
	    head.setMessageId("1111");*/
	    EnterpriseBody body=new EnterpriseBody();
	    cem.setEnterpriseBody(body);
		cem.setEnterpriseHeader(head);
		
	    connectUtils.updateConnectEnterprsieMessagePayload(cem,str);
	    
	    
	    return cem;
	}
	
	
}
