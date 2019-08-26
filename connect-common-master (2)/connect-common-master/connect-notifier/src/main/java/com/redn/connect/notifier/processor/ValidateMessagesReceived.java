package com.redn.connect.notifier.processor;

import java.util.List;
import java.util.regex.Pattern;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.constants.NotifierConstants;
import com.redn.connect.exception.Category;
import com.redn.connect.exception.ConnectException;
import com.redn.connect.notifications.request.CommonAttributesMessage;
import com.redn.connect.notifications.request.EmailMessage;
import com.redn.connect.notifications.request.NotificationMessage;
import com.redn.connect.notifications.request.SMSMessage;
import com.redn.connect.processor.connectconfig.ConnectConfiguration;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.Contents;
import com.redn.connect.vo.CustomProps;
import com.redn.connect.vo.EnterpriseHeader.Custom;

public class ValidateMessagesReceived implements Callable {

	private CommonAttributesMessage commonAttributesMessage;

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		ConnectConfiguration connectConfiguration = eventContext.getMuleContext().getRegistry()
				.lookupObject("connectConfigBean");

		MuleMessage muleMessage = eventContext.getMessage();

		NotificationMessage notificationMessage = (NotificationMessage) muleMessage.getPayload();

		this.commonAttributesMessage = notificationMessage.getCommonAttributesMessage();

		EmailMessage emailMessage = notificationMessage.getEmailMessage();
		SMSMessage smsMessage = notificationMessage.getSmsMessage();

		String sourceSystem = muleMessage.getInvocationProperty(ConnectConstants.VAR_MESSAGE_SOURCE);

		checkApplicationID(commonAttributesMessage, sourceSystem);
		
		Boolean applicationIDRegistered = Boolean.FALSE;
		String applicationIDs = connectConfiguration.get("connect.notifier.registered.applicationids");
		
		if(applicationIDs != null && !applicationIDs.isEmpty()){
			System.out.println("APPLICATION ID's ======="+applicationIDs);
			
			String[] registeredApplicationIDs = applicationIDs.split("&&");
			for(String applicationID:registeredApplicationIDs){
				
				System.out.println("COMPARING APPLICATION IDS========"+applicationID.trim().equals(commonAttributesMessage.getApplicationID().trim()));
				if(applicationID.trim().equals(commonAttributesMessage.getApplicationID().trim())){
					applicationIDRegistered = Boolean.TRUE;
					break;
				}
			}
		}else {
			
			throw new ConnectException(NotifierConstants.ERROR_CODE_COMMON_APPLICATION_ID_NOT_REGISTERED_ON_MULE,
					NotifierConstants.ERROR_CODE_COMMON_APPLICATION_ID_NOT_REGISTERED_ON_MULE_DESC, Category.DATA, sourceSystem);
			
		}
		
		if(applicationIDRegistered){
			
			checkAndSetMandatoryAttributes(muleMessage, emailMessage, smsMessage, sourceSystem);
		
		}else{
			
			throw new ConnectException(NotifierConstants.ERROR_CODE_COMMON_APPLICATION_ID_NOT_REGISTERED_ON_MULE,
					NotifierConstants.ERROR_CODE_COMMON_APPLICATION_ID_NOT_REGISTERED_ON_MULE_DESC, Category.DATA, sourceSystem);
		}
		

		return muleMessage;
	}

	private void checkAndSetMandatoryAttributes(MuleMessage muleMessage, EmailMessage emailMessage,
			SMSMessage smsMessage, String sourceSystem) throws ConnectException {
		muleMessage.setProperty("requestID", commonAttributesMessage.getRequestID(), PropertyScope.INVOCATION);
		muleMessage.setProperty("applicationID", commonAttributesMessage.getApplicationID(), PropertyScope.INVOCATION);

		Boolean isEmailMessageReceived = isEmptyEmailMessage(emailMessage);
		Boolean isSMSMessageReceived = isEmptySMSMessage(smsMessage);

		if (!isEmailMessageReceived && !isSMSMessageReceived) {
			throw new ConnectException(NotifierConstants.ERROR_CODE_EMAIL_AND_SMS_ARE_NULL,
					NotifierConstants.ERROR_CODE_EMAIL_AND_SMS_ARE_NULL_DESC, Category.DATA, sourceSystem);
		}


		muleMessage.setInvocationProperty(NotifierConstants.VAR_EMAIL_MESSAGE_AVAILABLE, isEmailMessageReceived);
		muleMessage.setInvocationProperty(NotifierConstants.VAR_SMS_MESSAGE_AVAILABLE, isSMSMessageReceived);

		ConnectEnterpriseMessage connectEnterpriseMessage = (ConnectEnterpriseMessage) muleMessage
				.getProperty(ConnectConstants.VAR_ENTERPRISE_MESSAGE, PropertyScope.INVOCATION);

		Custom customObj = connectEnterpriseMessage.getEnterpriseHeader().getCustom();
		if (customObj == null) {
			customObj = new Custom();
		}

		CustomProps customPropsObj = null;

		if (customObj.getAny() != null) {
			customPropsObj = (CustomProps) customObj.getAny();
		} else {
			customPropsObj = new CustomProps();
		}

		List<Contents> contentsList = customPropsObj.getContents();

		Contents contentsEmailMessageAvailability = new Contents();
		contentsEmailMessageAvailability.setName(ConnectConstants.VAR_EMAIL_MESSAGE_AVAILABLE);
		contentsEmailMessageAvailability.setValue(String.valueOf(isEmailMessageReceived));
		contentsList.add(contentsEmailMessageAvailability);

		Contents contentsSMSMessageAvailability = new Contents();
		contentsSMSMessageAvailability.setName(ConnectConstants.VAR_SMS_MESSAGE_AVAILABLE);
		contentsSMSMessageAvailability.setValue(String.valueOf(isSMSMessageReceived));
		contentsList.add(contentsSMSMessageAvailability);

		Contents contentsApplicationIDObj = new Contents();
		contentsApplicationIDObj.setName(ConnectConstants.VAR_APPLICATION_ID);
		contentsApplicationIDObj.setValue(commonAttributesMessage.getApplicationID());
		contentsList.add(contentsApplicationIDObj);

		Contents contentsRequestIDObj = new Contents();
		contentsRequestIDObj.setName(ConnectConstants.VAR_REQUEST_ID);
		contentsRequestIDObj.setValue(commonAttributesMessage.getRequestID());
		contentsList.add(contentsRequestIDObj);

		connectEnterpriseMessage.getEnterpriseHeader().setCustom(customObj);
		muleMessage.setInvocationProperty(ConnectConstants.VAR_ENTERPRISE_MESSAGE, connectEnterpriseMessage);
	}

	private Boolean isEmptyEmailMessage(EmailMessage emailMessage) {
		if (emailMessage.getSubject() == null && emailMessage.getReplyTo() == null
				&& emailMessage.getAttachments() == null && emailMessage.getTemplateID() == null
				&& emailMessage.getKeyValuePairs().size() == 0 && emailMessage.getBody() == null
				&& emailMessage.getCcList().size() == 0 && emailMessage.getBccList().size() == 0
				&& emailMessage.getToList().size() == 0) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	private Boolean isEmptySMSMessage(SMSMessage smsMessage) {
		if (smsMessage.getSource() == null && smsMessage.getDestinations() == null
				&& smsMessage.getMessages() == null) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	private void checkApplicationID(CommonAttributesMessage commonAttributesMessage, String sourceSystem)
			throws ConnectException {

		if (commonAttributesMessage == null) {

			throw new ConnectException(NotifierConstants.ERROR_CODE_COMMON_ATTRIBUTES_MESSAGE_NULL,
					NotifierConstants.ERROR_CODE_COMMON_ATTRIBUTES_MESSAGE_NULL_DESC, Category.DATA, sourceSystem);
		}

		if (commonAttributesMessage.getRequestID() == null || commonAttributesMessage.getRequestID().isEmpty()) {

			throw new ConnectException(NotifierConstants.ERROR_CODE_REQUEST_ID_EMPTY,
					NotifierConstants.ERROR_CODE_REQUEST_ID_EMPTY_DESC, Category.DATA, sourceSystem);
		}

		if (commonAttributesMessage.getApplicationID() == null
				|| commonAttributesMessage.getApplicationID().isEmpty()) {

			throw new ConnectException(NotifierConstants.ERROR_CODE_APPLICATION_ID_UNAVAILABLE,
					NotifierConstants.ERROR_CODE_APPLICATION_ID_UNAVAILABLE_DESC, Category.DATA, sourceSystem);
		}
	}

	/*
	 * private void validateAndSetEmailIDNames(ConnectConfiguration
	 * connectConfiguration, EmailMessage emailMessage, String sourceSystem)
	 * throws ConnectException { if (emailMessage.getToList().isEmpty()) {
	 * String recipientToListFromZuul = connectConfiguration
	 * .get(NotifierConstants.VAR_CONNECT_NOTIFIER +
	 * commonAttributesMessage.getApplicationID().trim() +
	 * NotifierConstants.VAR_PROPERTY_KEY_SEPERATOR +
	 * NotifierConstants.VAR_SMTP_TO_LIST);
	 * 
	 * if (recipientToListFromZuul != null &&
	 * !recipientToListFromZuul.isEmpty()) { String[] recipientList =
	 * recipientToListFromZuul.split(","); for (String emailID : recipientList)
	 * { validate(emailID, sourceSystem); }
	 * emailMessage.setToList(Arrays.asList(recipientToListFromZuul)); } else {
	 * 
	 * throw new
	 * ConnectException(NotifierConstants.ERROR_CODE_RECIPIENT_LIST_UNAVAILABLE,
	 * NotifierConstants.ERROR_CODE_RECEPIENT_TO_LIST_UNAVAILABLE_DESC,
	 * Category.DATA, sourceSystem); } } else {
	 * 
	 * List<String> listOfEmails = emailMessage.getToList(); for (String emailID
	 * : listOfEmails) { validate(emailID, sourceSystem); } }
	 * 
	 * if (emailMessage.getBccList().isEmpty()) { String
	 * recipientBCCListFromZuul = connectConfiguration
	 * .get(NotifierConstants.VAR_CONNECT_NOTIFIER +
	 * commonAttributesMessage.getApplicationID().trim() +
	 * NotifierConstants.VAR_PROPERTY_KEY_SEPERATOR +
	 * NotifierConstants.VAR_SMTP_BCC_LIST);
	 * 
	 * if (recipientBCCListFromZuul != null &&
	 * !recipientBCCListFromZuul.isEmpty()) { String[] recipientList =
	 * recipientBCCListFromZuul.split(","); for (String emailID : recipientList)
	 * { validate(emailID, sourceSystem); }
	 * emailMessage.setBccList(Arrays.asList(recipientBCCListFromZuul)); } }
	 * else {
	 * 
	 * List<String> listOfEmails = emailMessage.getBccList(); for (String
	 * emailID : listOfEmails) { validate(emailID, sourceSystem); } }
	 * 
	 * if (emailMessage.getCcList().isEmpty()) {
	 * 
	 * String recipientCCListFromZuul = connectConfiguration
	 * .get(NotifierConstants.VAR_CONNECT_NOTIFIER +
	 * commonAttributesMessage.getApplicationID().trim() +
	 * NotifierConstants.VAR_PROPERTY_KEY_SEPERATOR +
	 * NotifierConstants.VAR_SMTP_CC_LIST);
	 * 
	 * if (recipientCCListFromZuul != null &&
	 * !recipientCCListFromZuul.isEmpty()) { String[] recipientList =
	 * recipientCCListFromZuul.split(","); for (String emailID : recipientList)
	 * { validate(emailID, sourceSystem); }
	 * emailMessage.setCcList(Arrays.asList(recipientCCListFromZuul)); } } else
	 * {
	 * 
	 * List<String> listOfEmails = emailMessage.getCcList(); for (String emailID
	 * : listOfEmails) { validate(emailID, sourceSystem); } }
	 * 
	 * if (emailMessage.getToList().isEmpty() &&
	 * emailMessage.getCcList().isEmpty() &&
	 * emailMessage.getBccList().isEmpty()) {
	 * 
	 * throw new
	 * ConnectException(NotifierConstants.ERROR_CODE_RECIPIENT_LIST_UNAVAILABLE,
	 * NotifierConstants.ERROR_CODE_RECEPIENT_LIST_UNAVAILABLE_DESC,
	 * Category.DATA, sourceSystem);
	 * 
	 * }
	 * 
	 * }
	 * 
	 * private void validateSubjectAndReplyTo(ConnectConfiguration
	 * connectConfiguration, EmailMessage emailMessage, String sourceSystem)
	 * throws ConnectException {
	 * 
	 * if (emailMessage.getSubject() == null ||
	 * emailMessage.getSubject().isEmpty()) {
	 * 
	 * throw new ConnectException(NotifierConstants.ERROR_CODE_SUBJECT_EMPTY,
	 * NotifierConstants.ERROR_CODE_SUBJECT_EMPTY_DESC, Category.DATA,
	 * sourceSystem); }
	 * 
	 * if (emailMessage.getReplyTo() == null ||
	 * emailMessage.getReplyTo().isEmpty()) { String replyToEmailID =
	 * connectConfiguration .get(NotifierConstants.VAR_CONNECT_NOTIFIER +
	 * commonAttributesMessage.getApplicationID().trim() +
	 * NotifierConstants.VAR_PROPERTY_KEY_SEPERATOR +
	 * NotifierConstants.VAR_SMTP_REPLY_TO); if (replyToEmailID != null &&
	 * !replyToEmailID.isEmpty()) { String[] recipientList =
	 * replyToEmailID.split(","); for (String emailID : recipientList) {
	 * validate(emailID, sourceSystem); }
	 * emailMessage.setReplyTo(replyToEmailID); } } }
	 * 
	 * public static boolean validate(String emailStr, String sourceSystem)
	 * throws ConnectException { Matcher matcher =
	 * VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr); if (!matcher.find()) { throw
	 * new ConnectException(NotifierConstants.ERROR_CODE_INVALID_EMAIL_ADDRESS,
	 * String.format(NotifierConstants.ERROR_CODE_INVALID_EMAIL_ADDRESS_DESC,
	 * emailStr), Category.DATA, sourceSystem); } return matcher.find(); }
	 */
}
