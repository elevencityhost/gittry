/**
 * 
 * @author SaiPrasad.Jonnala
 * {@link NotificationMessage}
 * {@link EmailMessage}
 * {@link SMSMessage}
 * {@link ConnectConfiguration}
 *
 * ModifyEmailTemplateOrBodyContent - This class is responsible to validate the received input payload 
 * And based on that validation appropriate exceptions will be thrown.
 * 
 * ***/

package com.redn.connect.notifier.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
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

public class ModifyEmailTemplateOrBodyContent implements Callable {

	private static Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
			"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])",
			Pattern.CASE_INSENSITIVE);

	private CommonAttributesMessage commonAttributesMessage;

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		ConnectConfiguration connectConfiguration = eventContext.getMuleContext().getRegistry()
				.lookupObject("connectConfigBean");

		MuleMessage muleMessage = eventContext.getMessage();

		NotificationMessage notificationMessage = (NotificationMessage) muleMessage.getPayload();

		this.commonAttributesMessage = notificationMessage.getCommonAttributesMessage();

		EmailMessage emailMessage = notificationMessage.getEmailMessage();
		//SMSMessage smsMessage = notificationMessage.getSmsMessage();

		String sourceSystem = muleMessage.getInvocationProperty(ConnectConstants.VAR_MESSAGE_SOURCE);

		Boolean emailMessageAvailable = muleMessage.getInvocationProperty(NotifierConstants.VAR_EMAIL_MESSAGE_AVAILABLE,
				Boolean.FALSE);
		
		
		String serviceName = muleMessage.getInvocationProperty(ConnectConstants.VAR_SERVICE_NAME);
		
		if (emailMessageAvailable) {

			validateSubjectAndReplyTo(connectConfiguration, emailMessage, sourceSystem,serviceName);
			validateAndSetEmailIDNames(connectConfiguration, emailMessage, sourceSystem,serviceName);
			
			if ((emailMessage.getTemplateID() != null && !emailMessage.getTemplateID().isEmpty())
					&& (emailMessage.getBody() == null || emailMessage.getBody().isEmpty())) {
				prepareEmailBodyContent(connectConfiguration, emailMessage, sourceSystem);
			}

		}

		//updateCustomPropsOfCEM(muleMessage, notificationMessage);

		return muleMessage;
	}

	private boolean isEmptyEmailMessage(EmailMessage emailMessage) {
		boolean emailMessageReceived = true;
		if (emailMessage.getSubject() == null && emailMessage.getReplyTo() == null
				&& emailMessage.getAttachments() == null && emailMessage.getTemplateID() == null
				&& emailMessage.getKeyValuePairs().size() == 0 && emailMessage.getBody() == null
				&& emailMessage.getCcList().size() == 0 && emailMessage.getBccList().size() == 0
				&& emailMessage.getToList().size() == 0) {
			emailMessageReceived = false;
		}
		return emailMessageReceived;
	}

	private boolean isEmptySMSMessage(SMSMessage smsMessage) {
		boolean smsMessageReceived = true;
		if (smsMessage.getSource() == null && smsMessage.getDestinations() == null
				&& smsMessage.getMessages() == null) {
			smsMessageReceived = false;
		}
		return smsMessageReceived;
	}

	private void updateCustomPropsOfCEM(MuleMessage muleMessage, NotificationMessage notificationMessage) {
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

		Contents contentsApplicationIDObj = new Contents();
		contentsApplicationIDObj.setName(ConnectConstants.VAR_APPLICATION_ID);
		contentsApplicationIDObj.setValue(notificationMessage.getCommonAttributesMessage().getApplicationID());
		contentsList.add(contentsApplicationIDObj);

		Contents contentsRequestIDObj = new Contents();
		contentsRequestIDObj.setName(ConnectConstants.VAR_REQUEST_ID);
		contentsRequestIDObj.setValue(notificationMessage.getCommonAttributesMessage().getRequestID());
		contentsList.add(contentsRequestIDObj);

		if (notificationMessage.getEmailMessage() != null
				&& notificationMessage.getEmailMessage().getSubject() != null) {

			Contents contentsEmailMessageAvailability = new Contents();
			contentsEmailMessageAvailability.setName(ConnectConstants.VAR_EMAIL_MESSAGE_AVAILABLE);
			contentsEmailMessageAvailability.setValue(String.valueOf(true));
			contentsList.add(contentsEmailMessageAvailability);

		}

		if (notificationMessage.getSmsMessage() != null && notificationMessage.getSmsMessage().getSource() != null) {

			Contents contentsSMSMessageAvailability = new Contents();
			contentsSMSMessageAvailability.setName(ConnectConstants.VAR_SMS_MESSAGE_AVAILABLE);
			contentsSMSMessageAvailability.setValue(String.valueOf(true));
			contentsList.add(contentsSMSMessageAvailability);

		}

		customObj.setAny(customPropsObj);

		muleMessage.setInvocationProperty(ConnectConstants.VAR_ENTERPRISE_MESSAGE, connectEnterpriseMessage);
	}

	private void prepareEmailBodyContent(ConnectConfiguration connectConfiguration, EmailMessage emailMessage,
			String sourceSystem) throws IOException, FileNotFoundException, ConnectException {
		// if template modify key name and value pairs

		String locationOfTemplate = connectConfiguration
				.get(NotifierConstants.VAR_CONNECT_NOTIFIER_TEMPLATE + commonAttributesMessage.getApplicationID().trim()
						+ NotifierConstants.VAR_PROPERTY_KEY_SEPERATOR + emailMessage.getTemplateID().trim());
		if (locationOfTemplate == null) {
			locationOfTemplate = "";
		}
		File emailTemplate = new File(locationOfTemplate);

		if (emailTemplate.exists()) {

			try (BufferedReader br = new BufferedReader(new FileReader(emailTemplate))) {

				StringBuffer fileContent = new StringBuffer();
				String line = br.readLine();

				while (line != null) {
					fileContent.append(line);
					fileContent.append(System.lineSeparator());
					line = br.readLine();
				}

				String fileContentAsString = fileContent.toString();
				Map<String, String> templateKeyValuePairs = emailMessage.getKeyValuePairs();

				if (!templateKeyValuePairs.isEmpty()) {

					for (String keyName : templateKeyValuePairs.keySet()) {

						fileContentAsString = fileContentAsString.replaceAll(keyName,
								templateKeyValuePairs.get(keyName));
					}
				}

				Pattern pattern = Pattern.compile(NotifierConstants.VAR_PATTERN_OF_PUBLIC_URL_IMAGE);
				Matcher matcher = pattern.matcher(fileContentAsString);
				List<String> matchedCases = new ArrayList<String>();
				while (matcher.find()) {
					matchedCases.add(matcher.group());
				}
				for (int count = 0; count < matchedCases.size(); count++) {
					String publicURLOfImageInTemplate = connectConfiguration
							.get(NotifierConstants.VAR_CONNECT_NOTIFIER_TEMPLATE
									+ commonAttributesMessage.getApplicationID().trim()
									+ NotifierConstants.VAR_PROPERTY_KEY_SEPERATOR + emailMessage.getTemplateID().trim()
									+ String.format(NotifierConstants.VAR_IMAGEID_ZUUL, new Integer(count + 1)));
					fileContentAsString = fileContentAsString.replace(matchedCases.get(count),
							publicURLOfImageInTemplate);
				}

				emailMessage.setBody(fileContentAsString);
			}
		} else {

			throw new ConnectException(NotifierConstants.ERROR_CODE_TEMPLATE_FILE_UNAVAILABLE,
					String.format(NotifierConstants.ERROR_CODE_TEMPLATE_FILE_UNAVAILABLE_DESC, locationOfTemplate),
					Category.DATA, sourceSystem);
		}
	}

	private void validateSubjectAndReplyTo(ConnectConfiguration connectConfiguration, EmailMessage emailMessage,
			String sourceSystem,String serviceName) throws ConnectException {

		if (emailMessage.getSubject() == null || emailMessage.getSubject().isEmpty()) {

			throw new ConnectException(NotifierConstants.ERROR_CODE_SUBJECT_EMPTY,
					NotifierConstants.ERROR_CODE_SUBJECT_EMPTY_DESC, Category.DATA, sourceSystem);
		}

		if (emailMessage.getReplyTo() == null || emailMessage.getReplyTo().isEmpty()) {
			
			String replyToEmailID = connectConfiguration
					.get(NotifierConstants.VAR_CONNECT_NOTIFIER + commonAttributesMessage.getApplicationID().trim()
							+ NotifierConstants.VAR_PROPERTY_KEY_SEPERATOR+serviceName.trim()+ NotifierConstants.VAR_SMTP_REPLY_TO);
			
			if (replyToEmailID != null && !replyToEmailID.isEmpty()) {
				String[] recipientList = replyToEmailID.split(",");
				for (String emailID : recipientList) {
					validate(emailID, sourceSystem);
				}
				emailMessage.setReplyTo(replyToEmailID);
			}
		}
	}

	private void validateAndSetEmailIDNames(ConnectConfiguration connectConfiguration, EmailMessage emailMessage,
			String sourceSystem,String serviceName) throws ConnectException {
		if (emailMessage.getToList().isEmpty()) {
			
			
			System.out.println("KEY NAME ========================= "+NotifierConstants.VAR_CONNECT_NOTIFIER + commonAttributesMessage.getApplicationID().trim()
							+ NotifierConstants.VAR_PROPERTY_KEY_SEPERATOR+serviceName.trim()+ NotifierConstants.VAR_SMTP_TO_LIST);
			String recipientToListFromZuul = connectConfiguration
					.get(NotifierConstants.VAR_CONNECT_NOTIFIER + commonAttributesMessage.getApplicationID().trim()
							+ NotifierConstants.VAR_PROPERTY_KEY_SEPERATOR+serviceName.trim()+ NotifierConstants.VAR_SMTP_TO_LIST);

			if (recipientToListFromZuul != null && !recipientToListFromZuul.isEmpty()) {
				String[] recipientList = recipientToListFromZuul.split(",");
				for (String emailID : recipientList) {
					validate(emailID, sourceSystem);
				}
				emailMessage.setToList(Arrays.asList(recipientToListFromZuul));
			} else {

				throw new ConnectException(NotifierConstants.ERROR_CODE_RECIPIENT_LIST_UNAVAILABLE,
						NotifierConstants.ERROR_CODE_RECEPIENT_TO_LIST_UNAVAILABLE_DESC, Category.DATA, sourceSystem);
			}
		} else {

			List<String> listOfEmails = emailMessage.getToList();
			for (String emailID : listOfEmails) {
				validate(emailID, sourceSystem);
			}
		}

		if (emailMessage.getBccList().isEmpty()) {
			String recipientBCCListFromZuul = connectConfiguration
					.get(NotifierConstants.VAR_CONNECT_NOTIFIER + commonAttributesMessage.getApplicationID().trim()
							+ NotifierConstants.VAR_PROPERTY_KEY_SEPERATOR+serviceName.trim()+NotifierConstants.VAR_SMTP_BCC_LIST);

			if (recipientBCCListFromZuul != null && !recipientBCCListFromZuul.isEmpty()) {
				String[] recipientList = recipientBCCListFromZuul.split(",");
				for (String emailID : recipientList) {
					validate(emailID, sourceSystem);
				}
				emailMessage.setBccList(Arrays.asList(recipientBCCListFromZuul));
			}
		} else {

			List<String> listOfEmails = emailMessage.getBccList();
			for (String emailID : listOfEmails) {
				validate(emailID, sourceSystem);
			}
		}

		if (emailMessage.getCcList().isEmpty()) {

			String recipientCCListFromZuul = connectConfiguration
					.get(NotifierConstants.VAR_CONNECT_NOTIFIER + commonAttributesMessage.getApplicationID().trim()
							+ NotifierConstants.VAR_PROPERTY_KEY_SEPERATOR+serviceName.trim()+NotifierConstants.VAR_SMTP_CC_LIST);

			if (recipientCCListFromZuul != null && !recipientCCListFromZuul.isEmpty()) {
				String[] recipientList = recipientCCListFromZuul.split(",");
				for (String emailID : recipientList) {
					validate(emailID, sourceSystem);
				}
				emailMessage.setCcList(Arrays.asList(recipientCCListFromZuul));
			}
		} else {

			List<String> listOfEmails = emailMessage.getCcList();
			for (String emailID : listOfEmails) {
				validate(emailID, sourceSystem);
			}
		}

		if (emailMessage.getToList().isEmpty() && emailMessage.getCcList().isEmpty()
				&& emailMessage.getBccList().isEmpty()) {

			throw new ConnectException(NotifierConstants.ERROR_CODE_RECIPIENT_LIST_UNAVAILABLE,
					NotifierConstants.ERROR_CODE_RECEPIENT_LIST_UNAVAILABLE_DESC, Category.DATA, sourceSystem);

		}

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

	public static boolean validate(String emailStr, String sourceSystem) throws ConnectException {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		if (!matcher.find()) {
			throw new ConnectException(NotifierConstants.ERROR_CODE_INVALID_EMAIL_ADDRESS,
					String.format(NotifierConstants.ERROR_CODE_INVALID_EMAIL_ADDRESS_DESC, emailStr), Category.DATA,
					sourceSystem);
		}
		return matcher.find();
	}
}
