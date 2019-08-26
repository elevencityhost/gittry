package com.redn.connect.emailservice.processor;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;
import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.emailservice.constants.EmailServiceConstants;
import com.redn.connect.exception.Category;
import com.redn.connect.exception.ConnectException;
import com.redn.connect.notifications.request.EmailMessage;
import com.redn.connect.processor.connectconfig.ConnectConfiguration;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.Contents;
import com.redn.connect.vo.CustomProps;
import com.redn.connect.vo.EnterpriseHeader.Custom;

public class EmailSender implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage muleMessage = eventContext.getMessage();
		ConnectEnterpriseMessage connectEnterpriseMessage = (ConnectEnterpriseMessage) muleMessage
				.getProperty(ConnectConstants.VAR_ENTERPRISE_MESSAGE, PropertyScope.INVOCATION);
		EmailMessage emailMessage = (EmailMessage) muleMessage.getPayload();

		ConnectConfiguration connectConfiguration = eventContext.getMuleContext().getRegistry()
				.lookupObject("connectConfigBean");

		String applicationID = emailMessage.getCommonAttributesMessage().getApplicationID();
		String username = connectConfiguration.get("connect.emailservice.smtp." + applicationID + ".username");
		String password = connectConfiguration.get("connect.emailservice.smtp." + applicationID + ".password");
		String host = connectConfiguration.get("connect.emailservice.smtp." + applicationID + ".host");
		String auth = connectConfiguration.get("connect.emailservice.smtp." + applicationID + ".auth");
		String tlsEnable = connectConfiguration.get("connect.emailservice.smtp." + applicationID + ".tlsEnable");
		String sslTrust = connectConfiguration.get("connect.emailservice.smtp." + applicationID + ".sslTrust");
		String portnumber = connectConfiguration.get("connect.emailservice.smtp." + applicationID + ".portnumber");
		String fromName = connectConfiguration.get("connect.emailservice.smtp." + applicationID + ".fromName");
		String replyToName = connectConfiguration.get("connect.emailservice.smtp." + applicationID + ".replyToName");

		if (applicationID == null || username == null || password == null || host == null || auth == null
				|| tlsEnable == null || sslTrust == null || portnumber == null || fromName == null
				|| replyToName == null) {

			StringBuffer mandatoryPropsObj = new StringBuffer();
			mandatoryPropsObj.append("ApplicationID=");
			mandatoryPropsObj.append(applicationID);
			mandatoryPropsObj.append("username=");
			mandatoryPropsObj.append(username);
			mandatoryPropsObj.append("password=");
			mandatoryPropsObj.append(password);
			mandatoryPropsObj.append("host=");
			mandatoryPropsObj.append(host);
			mandatoryPropsObj.append("auth=");
			mandatoryPropsObj.append(auth);
			mandatoryPropsObj.append("tlsEnable=");
			mandatoryPropsObj.append(tlsEnable);
			mandatoryPropsObj.append("sslTrust=");
			mandatoryPropsObj.append(sslTrust);
			mandatoryPropsObj.append("portnumber=");
			mandatoryPropsObj.append(portnumber);
			mandatoryPropsObj.append("fromName=");
			mandatoryPropsObj.append(fromName);
			mandatoryPropsObj.append("replyToName=");
			mandatoryPropsObj.append(replyToName);

			throw new ConnectException(EmailServiceConstants.ERROR_CODE_MANDATORY_PROPERTY_MISSING,
					String.format(EmailServiceConstants.ERROR_CODE_MANDATORY_PROPERTY_MISSING_DESC,
							mandatoryPropsObj.toString()),
					Category.DATA, connectEnterpriseMessage.getEnterpriseHeader().getMessageSource());

		}

		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.user", username);
		properties.put("mail.password", password);
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.auth", auth);
		properties.put("mail.smtp.starttls.enable", tlsEnable);
		properties.put("mail.smtp.ssl.trust", sslTrust);
		properties.put("mail.smtp.port", portnumber);

		// creates a new session with an authenticator
		Authenticator authenticator = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};
		Session session = Session.getInstance(properties, authenticator);

		// creates a new e-mail message
		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress(username, fromName));

		String toList = emailMessage.getToList().toString();
		if (toList != null) {
			String toListAsArray[] = toList.trim().replace("[", "").replace("]", "").split(",");
			Address[] toAddressesArray = new InternetAddress[toListAsArray.length];
			for (int i = 0; i < toListAsArray.length; i++) {
				toAddressesArray[i] = new InternetAddress(toListAsArray[i].trim());
			}
			if (toAddressesArray.length > 0 && !toAddressesArray[0].toString().isEmpty()) {
				message.setRecipients(Message.RecipientType.TO, toAddressesArray);
			}

		}

		String ccList = emailMessage.getCcList().toString();
		if ((ccList != null && !ccList.isEmpty())) {
			String ccListAsArray[] = ccList.trim().replace("[", "").replace("]", "").split(",");
			Address[] ccAddressesArray = new InternetAddress[ccListAsArray.length];
			for (int i = 0; i < ccListAsArray.length; i++) {
				if (ccListAsArray[i] != null && !ccListAsArray[i].toString().isEmpty())
					ccAddressesArray[i] = new InternetAddress(ccListAsArray[i].trim());
			}

			if ((ccAddressesArray != null && ccAddressesArray.length > 0)
					&& (ccAddressesArray[0] != null && ccAddressesArray[0].toString().contains("@")))
				message.setRecipients(Message.RecipientType.CC, ccAddressesArray);

		}

		String bccList = emailMessage.getBccList().toString();
		if (bccList != null && !bccList.isEmpty()) {
			String bccListAsArray[] = bccList.trim().replace("[", "").replace("]", "").split(",");
			Address[] bccAddressesArray = new InternetAddress[bccListAsArray.length];

			for (int i = 0; i < bccListAsArray.length; i++) {
				if (bccListAsArray[i] != null && !bccListAsArray[i].toString().isEmpty())
					bccAddressesArray[i] = new InternetAddress(bccListAsArray[i].trim());
			}

			if ((bccAddressesArray != null && bccAddressesArray.length > 0)
					&& (bccAddressesArray[0] != null && bccAddressesArray[0].toString().contains("@")))
				message.setRecipients(Message.RecipientType.BCC, bccAddressesArray);
		}
		message.setSubject(emailMessage.getSubject());
		message.setSentDate(new Date());

		Address[] address = new InternetAddress[1];
		address[0] = new InternetAddress(emailMessage.getReplyTo(), replyToName);
		
		message.setReplyTo(address);
		// creates message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(emailMessage.getBody(), "text/html;charset=UTF-8");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		Custom customObj = connectEnterpriseMessage.getEnterpriseHeader().getCustom();
		if (customObj != null) {
			CustomProps customProps = (CustomProps) customObj.getAny();
			if (customProps != null) {
				List<Contents> contentsList = customProps.getContents();
				for (Contents contentsObj : contentsList) {
					if (contentsObj.getName().equals(ConnectConstants.VAR_ATTACHMENT_PATH)) {
						if (contentsObj.getValue() != null) {
							String[] fileNames = contentsObj.getValue().toString().split(",");
							for (String fileName : fileNames) {
								MimeBodyPart attachPart = new MimeBodyPart();
								attachPart.attachFile(fileName);
								multipart.addBodyPart(attachPart);
							}
						}
					}
				}
			}
		}

		message.setContent(multipart);
		Transport.send(message);
		
		if (customObj != null) {
			CustomProps customProps = (CustomProps) customObj.getAny();
			
			if (customProps != null) {
				List<Contents> contentsList = customProps.getContents();
				for (Contents contentsObj : contentsList) {
					if (contentsObj.getName().equals(ConnectConstants.VAR_ATTACHMENT_PATH)) {
						if (contentsObj.getValue() != null) {
							String[] fileNames = contentsObj.getValue().toString().split(",");
							for (String fileName : fileNames) {
								//For deleting the attached files
								 File file = new File(fileName);
								 file.delete();
							}
						}
					}
				}
			}
		}
		muleMessage.setPayload("Email Sent Successfully !!!!");
		return muleMessage;
	}

}
