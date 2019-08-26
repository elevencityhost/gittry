package com.redn.connect.processor.edifactD97A;

import java.math.BigDecimal;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.edifact.UNGSegment;
import com.redn.connect.util.ConnectUtilsWdc;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.EnterpriseHeader;

public class UpdateCEMwithEdifactSegmentsWdc implements Callable {
	
	private String targetSystem;
	
	private String messageGroupId;

	public String getTargetSystem() {
		return targetSystem;
	}

	public void setTargetSystem(String targetSystem) {
		this.targetSystem = targetSystem;
	}

	public String getMessageGroupId() {
		return messageGroupId;
	}

	public void setMessageGroupId(String messageGroupId) {
		this.messageGroupId = messageGroupId;
	}

	ConnectUtilsWdc utils = new ConnectUtilsWdc();
	
	
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
			MuleMessage message = eventContext.getMessage();
			ConnectEnterpriseMessage cem = (ConnectEnterpriseMessage) message.getProperty(ConnectConstants.ENTERPRISE_MESSAGE,
					PropertyScope.INVOCATION);
			String credat = message.getProperty(ConnectConstants.CONST_CRE_DAT,PropertyScope.INVOCATION);
			String cretim = message.getProperty(ConnectConstants.CONST_CRE_TIM,PropertyScope.INVOCATION);
			String senderId = message.getProperty(ConnectConstants.CONST_UNB_0201, PropertyScope.INVOCATION);
			String recipientId = message.getProperty(ConnectConstants.CONST_UNB_0301, PropertyScope.INVOCATION);
			String recipientCodeQualifier = message.getProperty(ConnectConstants.CONST_UNB_0302, PropertyScope.INVOCATION);
			String dateTime = message.getProperty(ConnectConstants.CONST_CRE_DATE_TIME, PropertyScope.INVOCATION);
			String unbRecipientId=message.getProperty(ConnectConstants.UNB_RECIPIENT_ID, PropertyScope.INVOCATION);
			
			EnterpriseHeader enterpriseHeader = cem.getEnterpriseHeader();
			enterpriseHeader.setTargetSystem(targetSystem);
			Object payload = message.getPayload();
			String payloadStr = payload.toString();
			
			UNGSegment ungSegment = SetUNGSegment(senderId, recipientId,credat,cretim,dateTime);
			
			String ungSegmentStr = utils.buildUNZSegmentD97A_V4(ungSegment);
			String uneSegmentStr = utils.buildUNESegmentD97A(dateTime, 1);

			StringBuffer strBuffer = new StringBuffer().append(payloadStr.substring(0, payloadStr.indexOf("UNH")))
					.append(ungSegmentStr).append(payloadStr.substring(payloadStr.indexOf("UNH")));
			
			strBuffer = new StringBuffer().append(strBuffer.toString().substring(0, strBuffer.toString().lastIndexOf("UNZ")))
					.append(uneSegmentStr).append(strBuffer.toString().substring(strBuffer.toString().lastIndexOf("UNZ")));
			System.out.print(strBuffer);
			String edifactContent = strBuffer.toString();
			String updatededifact = "";
			//TODO - Edifact Connector is writing * for reserved place, need to find a nice way
			updatededifact = edifactContent.replace("UNA:+.?*'", "UNA:+.? '");
			if(targetSystem.equals("WDC")){
				updatededifact = updatededifact.replace("UNOY:4", "UNOB:2");
				updatededifact = updatededifact.replace(unbRecipientId+":"+recipientCodeQualifier+"+00"+credat+":"+cretim, unbRecipientId+":"+recipientCodeQualifier+"+"+credat+":"+cretim);
			}
			utils.updateConnectEnterprsieMessagePayload(cem, updatededifact);
			return cem;
	}
	
	public UNGSegment SetUNGSegment(String senderId,String recipientId,String credat,String cretim,String dateTime) {
		UNGSegment ungSegment = new UNGSegment();
		ungSegment.setMessageGroupId(messageGroupId);
		ungSegment.setSenderId(senderId);
		ungSegment.setRecipientId(recipientId);
		ungSegment.setDate(credat);
		ungSegment.setTime(cretim);
		ungSegment.setGroupReferenceNumber(dateTime);
		ungSegment.setControllingAgency("UN");
		ungSegment.setMessageVersion("D");
		ungSegment.setMessageReleaseNumber("97A");
		ungSegment.setAssociationAssignedCode("UN");
		return ungSegment;
	}
	
}
