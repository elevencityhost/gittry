package com.redn.connect.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.redn.connect.constants.ConnectConstants;
import com.redn.connect.edifact.UNGSegment;
import com.redn.connect.vo.ConnectEnterpriseException;
import com.redn.connect.vo.ConnectEnterpriseMessage;
import com.redn.connect.vo.Contents;
import com.redn.connect.vo.ConnectEnterpriseMessage.EnterpriseBody;
import com.redn.connect.vo.CustomProps;
import com.redn.connect.vo.EnterpriseHeader;
import com.redn.connect.vo.EnterpriseHeader.Custom;

public class ConnectUtilsWdc {

	private static DatatypeFactory DATATYPE_FACTORY;

	static {
		try {
			DATATYPE_FACTORY = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			// LOGGER.error("Unable to create a DatatypeFactory. Possible
			// classloader issues?", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * This method formats the EDIFACT date
	 * 
	 * @param inputDTM
	 *            the input DTM
	 * @return the formatted date
	 * @throws Exception
	 *             the exception
	 */
	/*-
	 * Ex:dateToBFormated  : DTM+69:20150501010101UTC?+12:304
	 *         Result      : Mon May 11 01:01:01 IST 2015
	 * @throws Exception
	 */
	public String getFormattedDate(String inputDTM) throws ParseException {

		inputDTM = inputDTM.substring(7, 24);
		System.out.println(inputDTM);
		SimpleDateFormat dateObj = new SimpleDateFormat("yyyyMMddHHmmssZZZ");
		Date date = dateObj.parse(inputDTM);
		System.out.println("Converted form is :-" + date);
		return inputDTM;
	}

	/**
	 * This method is used to get the date as XMLGregorianCalendar.
	 *
	 * @param date
	 *            the date
	 * @return the date as xml gregorian calendar
	 */
	public XMLGregorianCalendar getDateAsXMLGregorianCalendar(Date date) {

		final Date dateValue = date == null ? new Date() : date;
		GregorianCalendar gregorianCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
		gregorianCalendar.setTime(dateValue);
		return DATATYPE_FACTORY.newXMLGregorianCalendar(gregorianCalendar);
	}

	/**
	 * This method is used to get the current time stamp in yyyy-MM-dd-HHmmss
	 * format
	 * 
	 * @return current time string in yyyy-MM-dd-HHmmss format
	 */
	public static String getCurrentTimeAsString() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
		Date date = new Date();
		return formatter.format(date);
	}

	public String getPayloadTextContentFromCEM(ConnectEnterpriseMessage cem) {

		String textContent = null;

		EnterpriseBody body = cem.getEnterpriseBody();

		Node any = (Node) body.getAny();
		if (any != null) {
			textContent = (String) any.getTextContent();
		}

		return textContent;

	}

	/**
	 * This method is used to extract the customProps from
	 * {@link ConnectEnterpriseMessage}
	 * 
	 * @param cem
	 *            {@link ConnectEnterpriseMessage} from which customProps to be
	 *            extracted
	 * @return CustomProps obj
	 */
	public static CustomProps getCustomPropsFromCEM(ConnectEnterpriseMessage cem) {

		CustomProps customProps = null;
		EnterpriseHeader header = cem.getEnterpriseHeader();

		Custom custom = header.getCustom();
		if (custom != null) {
			customProps = (CustomProps) custom.getAny();
		}

		return customProps;

	}

	/**
	 * This method is used to update given {@link ConnectEnterpriseMessage} with
	 * input customProps
	 * 
	 * @param cem
	 *            {@link ConnectEnterpriseMessage} to be updated
	 * @param payload
	 *            text to be updated as payload
	 */
	public static void updateConnectEnterprsieMessageWithCustomProps(final ConnectEnterpriseMessage cem,
			CustomProps customProps) {

		EnterpriseHeader header = cem.getEnterpriseHeader();

		EnterpriseHeader.Custom custom = new EnterpriseHeader.Custom();
		header.setCustom(custom);
		custom.setAny(customProps);

	}

	/**
	 * This method is used to build {@link ConnectEnterpriseMessage} with input
	 * payload set to {@link EnterpriseBody} and empty {@link EnterpriseHeader}
	 * object
	 * 
	 * @param payload
	 *            payload text to be set
	 * @return {@link ConnectEnterpriseMessage}
	 * @throws Exception
	 */
	public ConnectEnterpriseMessage buildConnectEnterprsieMessage(String payload) throws Exception {

		ConnectEnterpriseMessage cem = new ConnectEnterpriseMessage();

		EnterpriseBody body = new EnterpriseBody();
		EnterpriseHeader header = new EnterpriseHeader();

		cem.setEnterpriseBody(body);
		cem.setEnterpriseHeader(header);

		Node payloadNode = getNodeForPayload(payload);
		body.setAny(payloadNode);

		return cem;
	}

	/**
	 * This method is used to update given {@link ConnectEnterpriseMessage} with
	 * input payload text
	 * 
	 * @param cem
	 *            {@link ConnectEnterpriseMessage} to be updated
	 * @param payload
	 *            text to be updated as payload
	 * @throws Exception
	 */
	public void updateConnectEnterprsieMessagePayload(final ConnectEnterpriseMessage cem, String payload)
			throws Exception {

		EnterpriseBody body = cem.getEnterpriseBody();

		Node payloadNode = getNodeForPayload(payload);
		body.setAny(payloadNode);

	}

	public static String getSAPCurrentTimeAsString() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		return formatter.format(date);
	}

	/**
	 * This method is used to remove the leading zeros from a given string
	 * 
	 * @param inputValue
	 *            from which leading zeros to be removed
	 * @return {@link String} with leading zeros removed
	 */
	public String removeLeadingZeros(String inputValue) {

		if (inputValue == null) {
			return null;
		}
		String outputValue = inputValue.replaceAll("^0+(?!$)", "");
		return outputValue;
	}

	/**
	 * This method is used to append zero to a given string .
	 * 
	 * @param inputValue
	 * @param length
	 * @return
	 */
	public String setLeadingZeros(String inputValue, int length) {
		StringBuffer outputValue = new StringBuffer();
		if (inputValue == null) {
			return null;
		}
		if (inputValue.length() < length) {
			int count = length - inputValue.length();
			for (int j = 1; j <= count; j++) {
				outputValue.append("0");
			}
			outputValue.append(inputValue);
		} else {
			return inputValue;
		}
		return outputValue.toString();
	}

	/**
	 * This method validates the UNG segment
	 * 
	 * @param ungSegment
	 *            {@link UNGSegment} to be validated
	 * @throws Exception
	 */
	public void validateUNZSegmentD97A_V4(UNGSegment ungSegment) throws Exception {

		String msgGroupId = ungSegment.getMessageGroupId();
		int maxLength = 6;
		// Message Grp ID - max length 6
		if (msgGroupId != null) {
			if (msgGroupId.length() > maxLength) {
				throw new Exception(String.format(ConnectConstants.ERROR_MSG_MAX_LENGTH_EXCEEDED,
						ConnectConstants.UNG_FIELD_GROUP_ID, String.valueOf(maxLength)));
			}
		} else {
			throw new Exception(String.format(ConnectConstants.ERROR_MSG_MANDATORY_VALUE_MISSING,
					ConnectConstants.UNG_FIELD_GROUP_ID));
		}

		String senderId = ungSegment.getSenderId();
		maxLength = 35;
		// Sender ID - max length 35
		if (senderId != null) {
			if (senderId.length() > maxLength) {
				throw new Exception(String.format(ConnectConstants.ERROR_MSG_MAX_LENGTH_EXCEEDED,
						ConnectConstants.UNG_FIELD_SENDER_ID, String.valueOf(maxLength)));
			}
		} else {
			throw new Exception(String.format(ConnectConstants.ERROR_MSG_MANDATORY_VALUE_MISSING,
					ConnectConstants.UNG_FIELD_SENDER_ID));
		}

		// Recipient ID - max length 35
		String recipientId = ungSegment.getRecipientId();
		maxLength = 35;
		if (recipientId != null) {
			if (recipientId.length() > maxLength) {
				throw new Exception(String.format(ConnectConstants.ERROR_MSG_MAX_LENGTH_EXCEEDED,
						ConnectConstants.UNG_FIELD_RECIPIENT_ID, String.valueOf(maxLength)));
			}
		} else {
			throw new Exception(String.format(ConnectConstants.ERROR_MSG_MANDATORY_VALUE_MISSING,
					ConnectConstants.UNG_FIELD_RECIPIENT_ID));
		}

		// Recipient Code Qualifier - max length 4
		/** String recipientCC = ungSegment.getRecipientCodeQualifier();
		maxLength = 4;
		if (recipientCC != null) {
			if (recipientCC.length() > maxLength) {
				throw new Exception(String.format(ConnectConstants.ERROR_MSG_MAX_LENGTH_EXCEEDED,
						ConnectConstants.UNG_FIELD_RECIPIENT_CODE_QUALIFIER, String.valueOf(maxLength)));
			}
		} else {
			throw new Exception(String.format(ConnectConstants.ERROR_MSG_MANDATORY_VALUE_MISSING,
					ConnectConstants.UNG_FIELD_RECIPIENT_CODE_QUALIFIER));
		}*/

		// Date - max length 8
		String date = ungSegment.getDate();
		maxLength = 8;
		if (date != null) {
			if (date.length() > maxLength) {
				throw new Exception(String.format(ConnectConstants.ERROR_MSG_MAX_LENGTH_EXCEEDED,
						ConnectConstants.UNG_FIELD_DATE, String.valueOf(maxLength)));
			}
		} else {
			throw new Exception(
					String.format(ConnectConstants.ERROR_MSG_MANDATORY_VALUE_MISSING, ConnectConstants.UNG_FIELD_DATE));
		}

		// Time - max length 4
		String time = ungSegment.getTime();
		maxLength = 4;
		if (time != null) {
			if (time.length() > maxLength) {
				throw new Exception(String.format(ConnectConstants.ERROR_MSG_MAX_LENGTH_EXCEEDED,
						ConnectConstants.UNG_FIELD_TIME, String.valueOf(maxLength)));
			}
		} else {
			throw new Exception(
					String.format(ConnectConstants.ERROR_MSG_MANDATORY_VALUE_MISSING, ConnectConstants.UNG_FIELD_TIME));
		}

		// Group Reference Number - max length 14
		String groupRefNum = ungSegment.getGroupReferenceNumber();
		maxLength = 14;
		if (groupRefNum != null) {
			if (groupRefNum.length() > maxLength) {
				throw new Exception(String.format(ConnectConstants.ERROR_MSG_MAX_LENGTH_EXCEEDED,
						ConnectConstants.UNG_FIELD_GROUP_REFERENCE_NUMBER, String.valueOf(maxLength)));
			}
		} else {
			throw new Exception(String.format(ConnectConstants.ERROR_MSG_MANDATORY_VALUE_MISSING,
					ConnectConstants.UNG_FIELD_GROUP_REFERENCE_NUMBER));
		}

		// Controlling Agency - max length 3
		String controllingAgency = ungSegment.getControllingAgency();
		maxLength = 3;
		if (controllingAgency != null) {
			if (controllingAgency.length() > maxLength) {
				throw new Exception(String.format(ConnectConstants.ERROR_MSG_MAX_LENGTH_EXCEEDED,
						ConnectConstants.UNG_FIELD_CONTROLLING_AGENCY, String.valueOf(maxLength)));
			}
		} else {
			throw new Exception(String.format(ConnectConstants.ERROR_MSG_MANDATORY_VALUE_MISSING,
					ConnectConstants.UNG_FIELD_CONTROLLING_AGENCY));
		}

		// version number - max length 3
		String versionNumber = ungSegment.getMessageVersion();
		maxLength = 3;
		if (versionNumber != null) {
			if (versionNumber.length() > maxLength) {
				throw new Exception(String.format(ConnectConstants.ERROR_MSG_MAX_LENGTH_EXCEEDED,
						ConnectConstants.UNG_FIELD_MESSAGE_VERSION, String.valueOf(maxLength)));
			}
		} else {
			throw new Exception(String.format(ConnectConstants.ERROR_MSG_MANDATORY_VALUE_MISSING,
					ConnectConstants.UNG_FIELD_MESSAGE_VERSION));
		}

		// release number - max length 3
		String releaseNumber = ungSegment.getMessageReleaseNumber();
		maxLength = 3;
		if (releaseNumber != null) {
			if (releaseNumber.length() > maxLength) {
				throw new Exception(String.format(ConnectConstants.ERROR_MSG_MAX_LENGTH_EXCEEDED,
						ConnectConstants.UNG_FIELD_RELEASE_NUMBER, String.valueOf(maxLength)));
			}
		} else {
			throw new Exception(String.format(ConnectConstants.ERROR_MSG_MANDATORY_VALUE_MISSING,
					ConnectConstants.UNG_FIELD_RELEASE_NUMBER));
		}
		// release number - max length 3
		String associationAssignedCode = ungSegment.getAssociationAssignedCode();
		maxLength = 3;
		if (associationAssignedCode != null) {
			if (associationAssignedCode.length() > maxLength) {
				throw new Exception(String.format(ConnectConstants.ERROR_MSG_MAX_LENGTH_EXCEEDED,
						ConnectConstants.UNG_FIELD_ASSOCIATION_ASSIGNED_CODE, String.valueOf(maxLength)));
			}
		} else {
			throw new Exception(String.format(ConnectConstants.ERROR_MSG_MANDATORY_VALUE_MISSING,
					ConnectConstants.UNG_FIELD_ASSOCIATION_ASSIGNED_CODE));
		}

	}

	public String buildUNZSegmentD97A_V4(UNGSegment ungSegment) throws Exception {

		validateUNZSegmentD97A_V4(ungSegment);

		// StringBuilder ungSegment = new StringBuilder("UNG+INVRPT");
		// String strUNG =
		// "UNG+INVRPT+connect-test+662424795TEST:16+"+credat+":"+cretim+"+"+dateTime+"+"+"UN+D:97A:UN'";
		// String strUNE = "UNE+1+"+idocNum+"'";

		StringBuilder ungSegmentStr = new StringBuilder();

		ungSegmentStr.append("UNG");
		ungSegmentStr.append("+");
		ungSegmentStr.append(ungSegment.getMessageGroupId());
		ungSegmentStr.append("+");
		ungSegmentStr.append(ungSegment.getSenderId());
		ungSegmentStr.append("+");
		ungSegmentStr.append(ungSegment.getRecipientId());
		ungSegmentStr.append("+");
		ungSegmentStr.append(ungSegment.getDate());
		ungSegmentStr.append(":");
		ungSegmentStr.append(ungSegment.getTime());
		ungSegmentStr.append("+");
		ungSegmentStr.append(ungSegment.getGroupReferenceNumber());
		ungSegmentStr.append("+");
		ungSegmentStr.append(ungSegment.getControllingAgency());
		ungSegmentStr.append("+");
		ungSegmentStr.append(ungSegment.getMessageVersion());
		ungSegmentStr.append(":");
		ungSegmentStr.append(ungSegment.getMessageReleaseNumber());
	    ungSegmentStr.append(":");
	    ungSegmentStr.append(ungSegment.getAssociationAssignedCode());
		ungSegmentStr.append("'");

		return ungSegmentStr.toString();
	}

	public String buildUNESegmentD97A(String dateTime, int count) throws Exception {

		StringBuilder uneSegmentStr = new StringBuilder();

		// Group Reference Number - max length 14
		if (dateTime != null) {
			if (dateTime.length() > 14) {
				throw new Exception(String.format(ConnectConstants.ERROR_MSG_MAX_LENGTH_EXCEEDED,
						ConnectConstants.UNG_FIELD_GROUP_REFERENCE_NUMBER));
			}
		} else {
			throw new Exception(String.format(ConnectConstants.ERROR_MSG_MANDATORY_VALUE_MISSING,
					ConnectConstants.UNG_FIELD_GROUP_REFERENCE_NUMBER));
		}

		uneSegmentStr.append("UNE");
		uneSegmentStr.append("+");
		uneSegmentStr.append(String.valueOf(count));
		uneSegmentStr.append("+");
		uneSegmentStr.append(dateTime);
		uneSegmentStr.append("'");

		return uneSegmentStr.toString();

	}

	/**
	 * This method is used to convert CEM JAXB object to XML which we call
	 * marshalling {@link ConnectEnterpriseMessage}
	 * 
	 * @param connectEnterpriseMessage
	 *            ConnectEnterpriseMessage object to be set
	 * @return {@link ConnectEnterpriseMessage as XML}
	 */
	public static String jaxbCEMObjectToXML(ConnectEnterpriseMessage connectEnterpriseMessage) throws Exception {

		try {
			JAXBContext context = JAXBContext.newInstance(ConnectEnterpriseMessage.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			marshaller.marshal(connectEnterpriseMessage, sw);
			String result = sw.toString();
			String resultUnEscape = StringEscapeUtils.unescapeXml(result);
			return resultUnEscape;
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new Exception("Can not convert CEM to XML: " + e.getMessage());
		}
	}

	/**
	 * This method is used to convert CEE JAXB object to XML which we call
	 * marshalling {@link ConnectEnterpriseException}
	 * 
	 * @param connectEnterpriseException
	 *            ConnectEnterpriseException object to be set
	 * @return {@link ConnectEnterpriseException as XML}
	 */
	public static String jaxbCEEObjectToXML(ConnectEnterpriseException connectEnterpriseException) throws Exception {

		try {
			JAXBContext context = JAXBContext.newInstance(ConnectEnterpriseException.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			marshaller.marshal(connectEnterpriseException, sw);
			String result = sw.toString();
			return result;
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new Exception("Can not convert CEE to XML: " + e.getMessage());
		}
	}

	/**
	 * This method is used to convert CEM as XML to CEM as JAXB Object which we
	 * call unmarshalling {@link ConnectEnterpriseMessage}
	 * 
	 * @param cemASXML
	 *            String object to be set
	 * @return {@link ConnectEnterpriseMessage as JAXB object}
	 */
	public static ConnectEnterpriseMessage jaxbXMLToCEMObject(String cemASXML) throws Exception {
		try {
			JAXBContext context = JAXBContext.newInstance(ConnectEnterpriseMessage.class);
			Unmarshaller un = context.createUnmarshaller();
			@SuppressWarnings("unchecked")
			JAXBElement<ConnectEnterpriseMessage> connectEnterpriseMessage = (JAXBElement<ConnectEnterpriseMessage>) un
					.unmarshal(new StringReader(cemASXML));
			return connectEnterpriseMessage.getValue();
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new Exception("Can not convert XML to ConnectEnterpriseMessage: " + e.getMessage());
		}
	}

	/**
	 * This method is used to prepare CEM JAXB object with payload content
	 * {@link ConnectEnterpriseMessage} We are adding payload content between
	 * <payload></payload> tags as string
	 * 
	 * @param payload
	 *            String object to be set
	 * @return {@link Node payload content as node}
	 */
	public Node getNodeForPayload(String payload) throws SAXException, IOException, ParserConfigurationException {

		String payloadXML = "<payload><![CDATA[" + payload + "]]></payload>";
		Node nodeValue = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse((new InputSource(new StringReader(payloadXML)))).getDocumentElement();
		;

		return nodeValue;

	}

	/**
	 * This method is used to get the current time stamp in yyyyMMddHHmmss
	 * format
	 * 
	 * @return current time string in yyyyMMddHHmmss format
	 */
	public static String getCurrentTimeAsStringForBanking() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		return formatter.format(date);
	}

	public void copyDataToFile(String data, String filePathWithFileName) throws Exception {

		FileOutputStream fos = null;
		if (null != data && !("".equals(data))
				&& (null != filePathWithFileName && !("".equals(filePathWithFileName)))) {
			try {
				File file = new File(filePathWithFileName);
				fos = new FileOutputStream(file);

				byte[] dataInBytes = data.getBytes();

				fos.write(dataInBytes);
				fos.flush();
				fos.close();
			} catch (IOException io) {
				throw new Exception(io.getMessage());

			} finally {
				try {
					if (fos != null) {
						fos.close();
					}
				} catch (IOException io) {
					throw new Exception(io.getMessage());
				}
			}
		} else {
			throw new Exception(String.format(ConnectConstants.ERROR_MSG_CONTENT_OR_FILEPATH_MISSING,
					ConnectConstants.DATA, ConnectConstants.FILE_PATH));
		}
	}

	public String readDataFromFile(String filePathWithFileName) throws Exception {

		FileInputStream fis = null;
		StringBuilder sb = new StringBuilder();
		File file = new File(filePathWithFileName);

		try {
			fis = new FileInputStream(file);
			int content;
			while ((content = fis.read()) != -1) {
				sb.append((char) content);
			}

		} catch (IOException io) {
			throw new Exception(io.getMessage());
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException io) {
				throw new Exception(io.getMessage());
			}
		}

		return sb.toString();
	}

	public String getContentValue(ConnectEnterpriseMessage cem, String ContentName) {

		String contentValue = null;

		Custom custom = (cem.getEnterpriseHeader() != null) ? cem.getEnterpriseHeader().getCustom() : null;

		CustomProps customProps = (custom != null) ? (CustomProps) (custom.getAny()) : null;

		List<Contents> contents = (customProps != null) ? customProps.getContents() : null;

		if (null != contents) {
			for (Contents content : contents) {

				if (content.getName().equals(ContentName)) {

					contentValue = content.getValue();

				}
			}
		}

		return contentValue;
	}

}