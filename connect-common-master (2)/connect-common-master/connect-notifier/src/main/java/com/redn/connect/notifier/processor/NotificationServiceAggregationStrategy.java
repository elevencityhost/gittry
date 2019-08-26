package com.redn.connect.notifier.processor;

import java.util.List;

import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.routing.AggregationContext;
import org.mule.routing.AggregationStrategy;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.constants.NotifierConstants;
import com.redn.connect.notifications.request.CommonAttributesMessage;
import com.redn.connect.notifications.response.EmailMessageResponse;
import com.redn.connect.notifications.response.NotificationMessageResponse;
import com.redn.connect.notifications.response.SMSMessageResponse;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.Contents;
import com.redn.connect.vo.CustomProps;
import com.redn.connect.vo.EnterpriseHeader.Custom;

public class NotificationServiceAggregationStrategy implements AggregationStrategy {

	@Override
	public MuleEvent aggregate(AggregationContext context) throws MuleException {

		List<MuleEvent> listOfEventsWithExceptions = context.collectEventsWithExceptions();
		List<MuleEvent> listOfEventsWithOutExceptions = context.collectEventsWithoutExceptions();

		NotificationMessageResponse notificationMessageResponse = new NotificationMessageResponse();
		CommonAttributesMessage commonAttributesMessage = new CommonAttributesMessage();
		notificationMessageResponse.setCommonAttributesMessage(commonAttributesMessage);

		MuleEvent finalResult = null;
		finalResult = prepareActualResponse(listOfEventsWithOutExceptions, notificationMessageResponse, finalResult,
				commonAttributesMessage);
		finalResult = prepareExceptionCaseResponse(listOfEventsWithExceptions, notificationMessageResponse, finalResult,
				commonAttributesMessage);
		return finalResult;
	}

	private MuleEvent prepareExceptionCaseResponse(List<MuleEvent> listOfEventsWithExceptions,
			NotificationMessageResponse notificationMessageResponse, MuleEvent finalResult,
			CommonAttributesMessage commonAttributesMessage) {
		EmailMessageResponse emailMessageResponse;
		SMSMessageResponse smsMessageResponse;
		if (listOfEventsWithExceptions.size() > 0) {

			for (int listOfEventsWithExceptionsCount = 0; listOfEventsWithExceptionsCount < listOfEventsWithExceptions
					.size(); listOfEventsWithExceptionsCount++) {

				MuleEvent eventObj = listOfEventsWithExceptions.get(listOfEventsWithExceptionsCount);
				Object payloadInstance = eventObj.getMessage().getPayload();
				NotificationServiceAggregationStrategy notificationServiceAggregationStrategy = new NotificationServiceAggregationStrategy();

				if (payloadInstance != null && payloadInstance instanceof ConnectEnterpriseMessage) {

					ConnectEnterpriseMessage connectEnterpriseMessage = (ConnectEnterpriseMessage) payloadInstance;
					Custom customObj = connectEnterpriseMessage.getEnterpriseHeader().getCustom();

					CustomProps customProps = null;

					if (customObj.getAny() != null) {

						customProps = (CustomProps) customObj.getAny();
						List<Contents> contentsList = customProps.getContents();

						for (Contents contentsObj : contentsList) {

							switch (contentsObj.getName()) {

							case ConnectConstants.VAR_APPLICATION_ID:

								commonAttributesMessage.setApplicationID(contentsObj.getValue());

								break;

							case ConnectConstants.VAR_REQUEST_ID:

								commonAttributesMessage.setRequestID(contentsObj.getValue());

								break;

							case ConnectConstants.VAR_EMAIL_MESSAGE_AVAILABLE:

								if (Boolean.valueOf(contentsObj.getValue())) {

									emailMessageResponse = notificationServiceAggregationStrategy
											.createEmailMessageResponse(
													NotifierConstants.ERROR_CODE_BAD_REQUEST_INTERNAL_SERVER,
													NotifierConstants.ERROR_CODE_JMS_UNAVAILABLE_DESC,
													NotifierConstants.VAR_STATUS_FAILURE);
									notificationMessageResponse.setEmailMessageResponse(emailMessageResponse);
								}

								break;

							case ConnectConstants.VAR_SMS_MESSAGE_AVAILABLE:

								if (Boolean.valueOf(contentsObj.getValue())) {

									smsMessageResponse = notificationServiceAggregationStrategy
											.createSMSMessageResponse(
													NotifierConstants.ERROR_CODE_BAD_REQUEST_INTERNAL_SERVER,
													NotifierConstants.ERROR_CODE_JMS_UNAVAILABLE_DESC,
													NotifierConstants.VAR_STATUS_FAILURE);
									notificationMessageResponse.setSmsMessageResponse(smsMessageResponse);
								}

								break;

							default:
								break;
							}

						}
					}

				}

				if (payloadInstance != null && payloadInstance instanceof SMSMessageResponse) {

					smsMessageResponse = (SMSMessageResponse) payloadInstance;
					notificationMessageResponse.setSmsMessageResponse(smsMessageResponse);
				}

				eventObj.getMessage().setPayload(notificationMessageResponse);
				finalResult = eventObj;
			}
		}
		return finalResult;
	}

	private MuleEvent prepareActualResponse(List<MuleEvent> listOfEventsWithOutExceptions,
			NotificationMessageResponse notificationMessageResponse, MuleEvent finalResult,
			CommonAttributesMessage commonAttributesMessage) {

		EmailMessageResponse emailMessageResponse;
		SMSMessageResponse smsMessageResponse;

		if (listOfEventsWithOutExceptions.size() > 0) {
			for (int listOfEventsWithOutExceptionsCount = 0; listOfEventsWithOutExceptionsCount < listOfEventsWithOutExceptions
					.size(); listOfEventsWithOutExceptionsCount++) {
				MuleEvent eventObj = listOfEventsWithOutExceptions.get(listOfEventsWithOutExceptionsCount);

				if (eventObj != null) {

					MuleMessage muleMessage = eventObj.getMessage();
					
					System.out.println("===== Mule Message Received ======= "+muleMessage);
					Object payloadInstance = eventObj.getMessage().getPayload();

					System.out.println("========== Payload Instance ==== " + payloadInstance);

					if (payloadInstance != null && payloadInstance instanceof ConnectEnterpriseMessage) {

						ConnectEnterpriseMessage connectEnterpriseMessage = (ConnectEnterpriseMessage) payloadInstance;
						Custom customObj = connectEnterpriseMessage.getEnterpriseHeader().getCustom();

						CustomProps customProps = null;

						if (customObj.getAny() != null) {

							customProps = (CustomProps) customObj.getAny();
							List<Contents> contentsList = customProps.getContents();

							for (Contents contentsObj : contentsList) {

								switch (contentsObj.getName()) {

								case ConnectConstants.VAR_APPLICATION_ID:

									commonAttributesMessage.setApplicationID(contentsObj.getValue());

									break;

								case ConnectConstants.VAR_REQUEST_ID:

									commonAttributesMessage.setRequestID(contentsObj.getValue());

									break;

								case ConnectConstants.VAR_EMAIL_MESSAGE_AVAILABLE:

									if (Boolean.valueOf(contentsObj.getValue())) {

										emailMessageResponse = this.createEmailMessageResponse(NotifierConstants.SUCCESS_CODE,
														NotifierConstants.SUCCESS_CODE_DESC,
														NotifierConstants.VAR_STATUS_SUCCESS);

										notificationMessageResponse.setEmailMessageResponse(emailMessageResponse);
									}

									break;

								default:
									break;
								}

							}
						}

					} else if (payloadInstance != null && payloadInstance instanceof SMSMessageResponse) {

						smsMessageResponse = (SMSMessageResponse) payloadInstance;
						notificationMessageResponse.setSmsMessageResponse(smsMessageResponse);

					} else if (payloadInstance != null && payloadInstance instanceof EmailMessageResponse) {

						emailMessageResponse = (EmailMessageResponse) payloadInstance;
						notificationMessageResponse.setEmailMessageResponse(emailMessageResponse);
					}

					commonAttributesMessage.setRequestID(muleMessage.getInvocationProperty("requestID"));
					commonAttributesMessage.setApplicationID(muleMessage.getInvocationProperty("applicationID"));
					notificationMessageResponse.setCommonAttributesMessage(commonAttributesMessage);
					eventObj.getMessage().setPayload(notificationMessageResponse);
					finalResult = eventObj;
				}

			}
		}
		
		System.out.println("=========Final Result Message==========="+finalResult.getMessage());
		return finalResult;
	}

	public EmailMessageResponse createEmailMessageResponse(String responseCode, String responseDescription,
			String status) {

		EmailMessageResponse emailMessageResponse = new EmailMessageResponse();

		emailMessageResponse.setResponseCode(responseCode);
		emailMessageResponse.setResponseDescription(responseDescription);
		emailMessageResponse.setStatus(status);

		return emailMessageResponse;
	}

	public SMSMessageResponse createSMSMessageResponse(String responseCode, String responseDescription, String status) {

		SMSMessageResponse smsMessageResponse = new SMSMessageResponse();

		smsMessageResponse.setResponseCode(responseCode);
		smsMessageResponse.setResponseDescription(responseDescription);
		smsMessageResponse.setStatus(status);

		return smsMessageResponse;
	}
}
