package com.redn.connect.processor.edifactD97A;

import java.math.BigDecimal;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.edifact.UNGSegment;
import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.EnterpriseHeader;

public class UpdateCEMwithEdifactSegments implements Callable {
	
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

	ConnectUtils utils = new ConnectUtils();
	
	
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
			MuleMessage message = eventContext.getMessage();
			ConnectEnterpriseMessage cem = (ConnectEnterpriseMessage) message.getProperty(ConnectConstants.ENTERPRISE_MESSAGE,
					PropertyScope.INVOCATION);
			String idocNum = message.getProperty(ConnectConstants.CONST_DOCUMENT_NUMBER,PropertyScope.INVOCATION);
			BigDecimal credat = message.getProperty(ConnectConstants.CONST_CRE_DAT,PropertyScope.INVOCATION);
			BigDecimal cretim = message.getProperty(ConnectConstants.CONST_CRE_TIM,PropertyScope.INVOCATION);
			String senderId = message.getProperty(ConnectConstants.CONST_UNB_0201, PropertyScope.INVOCATION);
			String recipientId = message.getProperty(ConnectConstants.CONST_UNB_0301, PropertyScope.INVOCATION);
			String recipientCodeQualifier = message.getProperty(ConnectConstants.CONST_UNB_0302, PropertyScope.INVOCATION);
			
			idocNum = utils.removeLeadingZeros(idocNum);
			EnterpriseHeader enterpriseHeader = cem.getEnterpriseHeader();
			enterpriseHeader.setTargetSystem(targetSystem);
			Object payload = message.getPayload();
			String payloadStr = payload.toString();
			
			UNGSegment ungSegment = SetUNGSegment(senderId, recipientId, recipientCodeQualifier,credat,cretim,idocNum);
			
			String ungSegmentStr = utils.buildUNZSegmentD97A_V4(ungSegment);
			String uneSegmentStr = utils.buildUNESegmentD97A(idocNum, 1);

			StringBuffer strBuffer = new StringBuffer().append(payloadStr.substring(0, payloadStr.indexOf("UNH")))
					.append(ungSegmentStr).append(payloadStr.substring(payloadStr.indexOf("UNH")));
			
			strBuffer = new StringBuffer().append(strBuffer.toString().substring(0, strBuffer.toString().lastIndexOf("UNZ")))
					.append(uneSegmentStr).append(strBuffer.toString().substring(strBuffer.toString().lastIndexOf("UNZ")));
			System.out.print(strBuffer);
			String edifactContent = strBuffer.toString();
			String updatededifact = "";
			//TODO - Edifact Connector is writing * for reserved place, need to find a nice way
			updatededifact = edifactContent.replace("UNA:+.?*'", "UNA:+.? '");
			if(targetSystem.equals("ibm")){
				updatededifact = updatededifact.replace("UNOY:4", "UNOC:3");
			}
			utils.updateConnectEnterprsieMessagePayload(cem, updatededifact);
			return cem;
	}
	
	public UNGSegment SetUNGSegment(String senderId,String recipientId,String recipientCodeQualifier,BigDecimal credat,BigDecimal cretim,String idocNum) {
		UNGSegment ungSegment = new UNGSegment();
		ungSegment.setMessageGroupId(messageGroupId);
		ungSegment.setSenderId(senderId);
		ungSegment.setRecipientId(recipientId);
		ungSegment.setRecipientCodeQualifier(recipientCodeQualifier);
		ungSegment.setDate(String.valueOf(credat));
		ungSegment.setTime(utils.setLeadingZeros(String.valueOf(cretim), 4));
		ungSegment.setGroupReferenceNumber(idocNum);
		ungSegment.setControllingAgency("UN");
		ungSegment.setMessageVersion("D");
		ungSegment.setMessageReleaseNumber("97A");
		return ungSegment;
	}
	
}
