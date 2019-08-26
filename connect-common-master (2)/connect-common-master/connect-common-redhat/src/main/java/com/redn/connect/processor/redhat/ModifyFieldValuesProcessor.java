package com.redn.connect.processor.redhat;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

public class ModifyFieldValuesProcessor implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		MuleMessage muleMessage = eventContext.getMessage();
		String payload = (String) muleMessage.getPayload();
		int indexOfUNA = payload.trim().indexOf("'");
		String payloadFromUNB = payload.substring(indexOfUNA + 1, payload.length());
		int indexOfUNBSeperator = payloadFromUNB.indexOf("'");
		String UNBSegmentValue = payload.substring(indexOfUNA + 1, indexOfUNBSeperator + indexOfUNA + 2);
		String[] UNBSegmentArray = UNBSegmentValue.split("\\+");
		if (UNBSegmentArray.length > 4) {
			String DTMFieldValue = UNBSegmentArray[4];
			String[] DTMFieldAsArray = DTMFieldValue.split(":");

			if (DTMFieldAsArray.length > 0) {

				String modifiedDTMFieldValue = DTMFieldAsArray[0].substring(2, DTMFieldAsArray[0].length());
				String modifiedUNBSegment = "UNB+" + UNBSegmentArray[1] + "+" + UNBSegmentArray[2] + "+"
						+ UNBSegmentArray[3] + "+" + modifiedDTMFieldValue + ":" + DTMFieldAsArray[1] + "+"
						+ UNBSegmentArray[5];
				payload = payload.replace(UNBSegmentValue, modifiedUNBSegment);
			}
		}

		muleMessage.setPayload(payload);

		return muleMessage;
	}

	/*public static void main(String[] args) {

		String payload = "UNA:+.?*'UNB+UNOB:4+662424795TEST+IBMEDIID:ZZ+00160330:1416+4'UNH+2+ORDERS:D:97A:UN'BGM+155+4300446679+9'DTM+137:20161107:102'FTX+AAI+++This is a media kit order'RFF+DB:9042237'RFF+ADZ:5591021'FF+AGN:D-1234'RFF+ADL:CH-PO-443356'RFF+AJL:30'RFF+AGK:'RFF+FF:8098312'NAD+DS++DIST_XX_IE_654321_1234567:E1EDKA1_NAME2:91:E1EDKA1_NAME4:+E1EDKA1_STRS2 ::+++++'CTA+AT+:'CTA+OC+:E1EDKA1_BNAME'COM+:EM'COM+:TE'NAD+DU++Reseller Company:E1EDKA1_2_NAME2:E1EDKA1_2_NAME3:E1EDKA1_2_NAME4:+Building G:10 Avenue Des Arbres:++Lyon++92000+FR'CTA+AT+:'CTA+OC+:ACHAT LICENCES'COM+achats@reseller.fr:EM'COM+:EI'COM+0123928132:TE'NAD+EN++End User Company:E1EDKA1_3_NAME2:E1EDKA1_2_NAME3:E1EDKA1_3_NAME4:+21 Parc Andre Citreon::++PARIS++75015+FR'CTA+AT+:Technical Sensei'CTA+OC+:Martin Seguin'COM+mseguin@endcompany.com:EM'COM+endcompanylogin:EI'COM+0143329372:TE'CTA+AT+:'CTA+OC+:Desmond Sensei'COM+desmondsensei@shiptocompany.com:EM'COM+:EI'COM+0143328882:TE'NAD+ST++Ship To Company:E1EDKA1_4_NAME4:E1EDKA1_2_NAME3:E1EDKA1_4_NAME4:+131 Another Part::++PARIS++75078+FR'CTA+AT+:Technical Sensei'CTA+OC+:Martin Seguin'COM+mseguin@endcompany.com:EM'COM+endcompanylogin:EI'COM+0143329372:TE'CTA+AT+:'CTA+OC+:Desmond Sensei'COM+desmondsensei@shiptocompany.com:EM'COM+:EI'COM+0143328882:TE'CUX+2:RMB'LIN+20++RH00002:VP'QTY+21:1:PCE'DTM+194:20161207:102'DTM+206:20171206:102'DTM+194:20161207:102'DTM+206:20171206:102'RFF+CT:UR'RFF+ADZ:UPS Next Day Air'NAD+EN++str1234:str1234:str1234:str1234:str1234+str1234:str1234:str1234++str1234+str1234+str1234+str'CTA+OC+:str1234'COM+str1234:EM'COM+str1234:EI'COM+str1234:TE'CTA+OC+:str1234'COM+str1234:EM'COM+str1234:EI'COM+str1234:TE'LIN+20++RH00002:VP'QTY+21:1:PCE'DTM+194:20161207:102'DTM+206:20171206:102'DTM+194:20161207:102'DTM+206:20171206:102'RFF+CT:UR'RFF+ADZ:UPS Next Day Air'NAD+EN++str1234:str1234:str1234:str1234:str1234+str1234:str1234:str1234++str1234+str1234+str1234+str'CTA+OC+:str1234'COM+str1234:EM'COM+str1234:EI'COM+str1234:TE'CTA+OC+:str1234'COM+str1234:EM'COM+str1234:EI'COM+str1234:TE'UNS+S'MOA+11:1'UNT+82+2'UNZ+1+4'";
		
		 * String[] seperator = payload.split("'"); System.out.println(
		 * "Seperator Size " + seperator[1]); String unbSegmentValue =
		 * seperator[1]; String[] unbSegmentAsArray =
		 * unbSegmentValue.split("\\+"); System.out.println(
		 * "UNB Segment Date Value " + unbSegmentAsArray[4]); String dateField =
		 * unbSegmentAsArray[4]; String[] DTMAsArray = dateField.split(":");
		 * System.out.println(DTMAsArray[0].substring(2,
		 * DTMAsArray[0].length()));
		 * 
		 * System.out.println(payload.replaceFirst(DTMAsArray[0],
		 * DTMAsArray[0].substring(2, DTMAsArray[0].length())));
		 
		int indexOfUNA = payload.trim().indexOf("'");
		String payloadFromUNB = payload.substring(indexOfUNA + 1, payload.length());
		int indexOfUNBSeperator = payloadFromUNB.indexOf("'");
		String UNBSegmentValue = payload.substring(indexOfUNA + 1, indexOfUNBSeperator + indexOfUNA + 2);
		String[] UNBSegmentArray = UNBSegmentValue.split("\\+");

		if (UNBSegmentArray.length > 3) {

			String DTMFieldValue = UNBSegmentArray[4];
			String[] DTMFieldAsArray = DTMFieldValue.split(":");

			if (DTMFieldAsArray.length > 0) {

				String modifiedDTMFieldValue = DTMFieldAsArray[0].substring(2, DTMFieldAsArray[0].length());
				String modifiedUNBSegment = "UNB+" + UNBSegmentArray[1] + "+" + UNBSegmentArray[2] + "+"
						+ UNBSegmentArray[3] + "+" + modifiedDTMFieldValue + ":" + DTMFieldAsArray[1] + "+"
						+ UNBSegmentArray[5];
				payload = payload.replace(UNBSegmentValue, modifiedUNBSegment);

			}
		}
	
	}
*/
}
