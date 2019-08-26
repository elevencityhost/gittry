package com.redn.connect.audit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;  
import java.util.List;
import java.util.Map;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.audit.constant.AuditConstants;

/*************************************************************************************************************************
 * *@author  Laxshmi Maram
 * 	This class is used to do audit validation and to get the validation status.
 * 
 *************************************************************************************************************************/
public class AuditValidation implements Callable {

	private Map<String, Object> responseMap = new HashMap<String, Object>();


	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		// Declaration
		MuleMessage muleMessage = eventContext.getMessage();
		@SuppressWarnings("unchecked")
		// Property list (payload) which are retrieved from database in flow.
		List<Map<String, Object>> dbResultPropertyList = (LinkedList<Map<String, Object>>) muleMessage
				.getPayload();
		Map<String, Map<String, Object>> dataPropertyList = new HashMap<String, Map<String, Object>>();
		Map<String, Object> mapPhaseIDPropertyValue = new HashMap<String, Object>();
		/* If flow name is 'get:/audit:Router'
		*** (auditGetFlow) return the validation status.
		*** Separate Function because Props are of string type in Verify/GET operation  where as it is array list in Verify/PUT  
		**/
		if (eventContext.getFlowConstruct().getName().equalsIgnoreCase(AuditConstants.VERIFY_GET_FLOW_NAME)) {
			System.out.println(null+ "  101008101  "+ "-- executing validation check");
			return getValidationCheckForVarifyGet(muleMessage);
		}
		
		// If flow name is 'put:/audit:application/json:auditapi-config'
		// (auditPutFlow) return the validation status.
		if (eventContext.getFlowConstruct().getName().equalsIgnoreCase(AuditConstants.VERIFY_PUT_FLOW_NAME) 
				|| eventContext.getFlowConstruct().getName().equalsIgnoreCase(AuditConstants.SOAP_GET_VALIDATION_STATUS_FLOW_NAME)) {
			System.out.println(null+ "  101008102  "+ "-- executing validation check");
			return getValidationCheck(muleMessage);
		}
		String previousPropertyName = "";
		boolean firstEntry = true;

		List<String> inputPropertyList = muleMessage
				.getInvocationProperty(AuditConstants.PROPERTIES);

		// Creating Map of property name as Key and 'Map object of Phase_id and
		// Property_value' as Value
		// Filter database property list based on input property list.
		if (inputPropertyList != null && inputPropertyList.size() > 0) {
			dbResultPropertyList = getPropsList(dbResultPropertyList,
					inputPropertyList);
		}

		System.out.println("101008103"+ "-- Props List size: " +dbResultPropertyList.size() );

		// Create a Map object of properties for each phase id and store it into
		// a list.
		for (Map<String, Object> map : dbResultPropertyList) {
			String propertyNameValue = String.valueOf(map
					.get(AuditConstants.PROP_NAME));
			String phaseIdValue = String.valueOf(map
					.get(AuditConstants.PHASE_ID));
			String propertyValue = String.valueOf(map
					.get(AuditConstants.PROP_VALUE));
			if (propertyNameValue != null && !propertyNameValue.isEmpty()) {
				if (firstEntry) {
					mapPhaseIDPropertyValue.put(phaseIdValue, propertyValue);
					dataPropertyList.put(propertyNameValue,
							mapPhaseIDPropertyValue);
					previousPropertyName = propertyNameValue;
					firstEntry = false;
				} else {
					if (previousPropertyName.trim().equalsIgnoreCase(
							propertyNameValue.trim())) {
						if (mapPhaseIDPropertyValue.containsKey(phaseIdValue)) {
							String pValue = mapPhaseIDPropertyValue.get(
									phaseIdValue).toString();
							BigDecimal bd = BigDecimal.valueOf(
									Double.parseDouble(pValue)).add(
									BigDecimal.valueOf(Double
											.parseDouble(propertyValue)));
							propertyValue = bd.toPlainString();
						}
						mapPhaseIDPropertyValue
								.put(phaseIdValue, propertyValue);
						dataPropertyList.put(propertyNameValue,
								mapPhaseIDPropertyValue);
					} else {
						mapPhaseIDPropertyValue = new HashMap<String, Object>();
						mapPhaseIDPropertyValue
								.put(phaseIdValue, propertyValue);
						dataPropertyList.put(propertyNameValue,
								mapPhaseIDPropertyValue);
						previousPropertyName = propertyNameValue;
					}
				}

			}
		}
		
		// Declaration for Header and Skipped property values
		BigDecimal skippedCount = new BigDecimal(0);
		BigDecimal headerPropValue = new BigDecimal(0);

		// looping for each property name
		for (String mapKey : dataPropertyList.keySet()) {
			// Skip validation for property Name Recorded only once or property
			// name is null or empty
			if (mapKey != null && !mapKey.isEmpty()
					&& dataPropertyList.get(mapKey).keySet().size() > 1) {

				// capture header properties value
				if (dataPropertyList.get(mapKey).keySet()
						.contains(AuditConstants.HEADER_PROPERTY_210)
						&& !isAlphaNumeric(dataPropertyList.get(mapKey)
								.get(AuditConstants.HEADER_PROPERTY_210)
								.toString())) {
					headerPropValue = BigDecimal.valueOf(Double.valueOf(String
							.valueOf(dataPropertyList.get(mapKey).get(
									AuditConstants.HEADER_PROPERTY_210))));
				} 

				// capture skipped record value
				if (dataPropertyList.get(mapKey).keySet()
						.contains(AuditConstants.HEADER_PROPERTY_291)) {
					skippedCount = BigDecimal.valueOf(Double
							.parseDouble(dataPropertyList.get(mapKey)
									.get(AuditConstants.HEADER_PROPERTY_291)
									.toString()));
				}

				// looping for each phase id for given property name
				for (String phaseId : dataPropertyList.get(mapKey).keySet()) {

					// if no header data is recorded but have multiple entries
					// then take the first phase as header value
					if (!(dataPropertyList.get(mapKey).keySet()
							.contains(AuditConstants.HEADER_PROPERTY_210)) && (headerPropValue.compareTo( new BigDecimal("0")) == 0) ) {
						headerPropValue = BigDecimal.valueOf(Double
								.valueOf(dataPropertyList.get(mapKey)
										.get(phaseId).toString()));
						System.out.println(null+ "  101008104" + "-- Property "	+ mapKey
								+ " has not recorded with phase id 220 "
								+ " taking phase id: " + phaseId
								+ " value as header value");
					}

					/**
					 * 
					 * Avoid validation for job start (phase_id: 200) and job
					 * end (phase_id: 299) entries No need to validate header
					 * value against header value, so skip validation for phase
					 * id: 220 Skipped entry is not validated against header,
					 * but validates along with phase id: 260 Also skip records
					 * which have alphanumeric property value
					 * dataPropertyList.get(mapKey)
									.get(phaseId).toString()
					 **/
					if ((phaseId != null)
							&& (!(phaseId
									.equals(AuditConstants.HEADER_PROPERTY_200)
									|| phaseId
											.equals(AuditConstants.HEADER_PROPERTY_299) || phaseId
										.equals(AuditConstants.HEADER_PROPERTY_210))
							&& Integer.parseInt(phaseId) != AuditConstants.HEADER_PROPERTY_INT_291
							/*&& (dataPropertyList.get(mapKey).get(phaseId) != null)*/
							&& (!isAlphaNumeric(phaseId)))){
						BigDecimal propertyValue = new BigDecimal("0");
						propertyValue = BigDecimal.valueOf(Double
								.valueOf(dataPropertyList.get(mapKey)
										.get(phaseId).toString()));
						if (skippedCount.compareTo(new BigDecimal("0")) == 1 && phaseId
								.equalsIgnoreCase(AuditConstants.HEADER_PROPERTY_260) || phaseId.equalsIgnoreCase(AuditConstants.HEADER_PROPERTY_240)) {
							//propertyValue = propertyValue.add(skippedCount);
							propertyValue = BigDecimal.valueOf(
									(propertyValue.add(skippedCount)).doubleValue());
						}
						// Converting to plain string to avoid validation on
						// exponential format
						if (!(propertyValue.toPlainString()
								.equalsIgnoreCase(headerPropValue
										.toPlainString()))) {
							responseMap.put(mapKey, "-1");
							break;
						} else {
							responseMap.put(mapKey, "1");
						}
					} else {
						if (phaseId == null
								|| dataPropertyList.get(mapKey).get(phaseId) == null) {

							System.out.println(null +"101008105"+ "-- Step ID "
									+ phaseId + " or Property Value is "
									+ dataPropertyList.get(mapKey).get(phaseId));
						}
					}
				}
			} else {
				if (mapKey == null || mapKey.isEmpty()) {
					System.out.println(null+ "101008106"+
							"-- Property Name is null or empty+ Hence No validation takes place");
				} else {
					System.out.println(null+ "101008106"+ "-- Property Name: "
							+ mapKey
							+ " entry is single hence treated as valid");
					responseMap.put(mapKey, "1");
				}
			}
		}
		List<Map<String, Object>> responseList = new ArrayList<Map<String, Object>>();
		// Create HashMap response to Linked List format
		for (String s : responseMap.keySet()) {
			addToList(responseList, s, responseMap.get(s).toString());
		}

		System.out.println(null +"101008107"+ "-- output:" + responseList);
		return responseList;
	}

	/**
	 * Validate property value is alphanumeric or not
	 * 
	 * @param pv
	 * @return Return true if it is alphabets
	 */
	private boolean isAlphaNumeric(String pv) {
		if (pv.matches(".*[a-zA-Z]+.*")) {
			return true;
		}
		return false;
	}

	/**
	 * Add provided key and value as Map to the List.
	 * 
	 * @param list
	 *            This List becomes as List of Maps
	 * @param key
	 *            Key of the Map to be added to the list
	 * @param value
	 *            Value of the Map to be added to the list
	 */
	private void addToList(List<Map<String, Object>> list, String key,
			String value) {
		HashMap<String, Object> tmap = new HashMap<String, Object>();
		tmap.put(AuditConstants.PROP_NAME, key);
		tmap.put(AuditConstants.STATUS, value);
		list.add(tmap);
	}

	/**
	 * Get properties List
	 * 
	 * @param dbPropertyList
	 * @param inputPropertyList
	 * @return Return propertiesList
	 */
	private List<Map<String, Object>> getPropsList(
			List<Map<String, Object>> dbPropertyList,
			List<String> inputPropertyList) {

		List<Map<String, Object>> propertiesList = new ArrayList<Map<String, Object>>();

//		if (inputPropertyList != null && inputPropertyList.size() > 0) {
//			for (Map<String, Object> map : dbPropertyList) {
//				String propertyName = map.get(AuditConstants.PROP_NAME)
//						.toString();
//				for (int i = 0; i < inputPropertyList.size(); i++) {
//					if (inputPropertyList.get(i).equalsIgnoreCase(propertyName)) {
//						propertiesList.add(map);
//						break;
//					} 
//					else {
//						responseMap.put(map.get(AuditConstants.PROP_NAME)
//								.toString(), "0");
//					}
//				}
//			}
//		}
		
		if (inputPropertyList != null && inputPropertyList.size() > 0) {
			boolean isPropNameExists = false;
			String propertyName = "";
				for (int i = 0; i < inputPropertyList.size(); i++) {
					isPropNameExists = false;
					propertyName = inputPropertyList.get(i);
					for (Map<String, Object> map : dbPropertyList) {						
						if (propertyName.equalsIgnoreCase(map.get(AuditConstants.PROP_NAME).toString())) {
							propertiesList.add(map);
							isPropNameExists = true;							
						} 
					}
					if(!isPropNameExists){
						responseMap.put(inputPropertyList.get(i).toString(), "0");
					}
			}
		}
		
		return propertiesList;
	}

	/**
	 * Get System_id from property list
	 * 
	 * @param dbPropertyList
	 *            property list retrieved form database.
	 * @param systemId
	 *            System Id.
	 * @return Return system_id value.
	 */
	private List<Map<String, Object>> getResultsBySystemId(
			List<Map<String, Object>> dbPropertyList, String systemId) {
		List<Map<String, Object>> propertiesList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : dbPropertyList) {

			if (systemId.equalsIgnoreCase(map.get(AuditConstants.DB_SYSTEM_ID)
					.toString())) {
				propertiesList.add(map);
				
			}
		}
		return propertiesList;
	}

	/**
	 * Do Validate check and return status
	 * 
	 * @param muleMessage
	 * @return Return status as 1 (true) or -1 (false)
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getValidationCheck(MuleMessage muleMessage) {
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		List<Map<String, Object>> propertyList = (List<Map<String, Object>>) muleMessage
				.getPayload();
		String invalidProperties = "";
		String inputSystemId = muleMessage
				.getInvocationProperty(AuditConstants.SYSTEM_ID);
		if (inputSystemId != null && !(inputSystemId.trim().isEmpty())) {
			propertyList = getResultsBySystemId(propertyList, inputSystemId);
		}
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		List<String> listForVerification = new ArrayList<String>();
		ArrayList<String> allPropertiesNames = muleMessage
				.getInvocationProperty(AuditConstants.PROPERTIES);
		System.out.println(propertyList+ "101008103"+ "-- List of property");
		if (allPropertiesNames != null && !(allPropertiesNames.isEmpty())) {
			for (Map<String, Object> map : propertyList) {
				String propertyName = map.get(AuditConstants.PROP_NAME)
						.toString();
				for (int i = 0; i < allPropertiesNames.size(); i++) {
					if (allPropertiesNames.get(i)
							.equalsIgnoreCase(propertyName)) {
						resultList.add(map);
						listForVerification.add(propertyName);
						break;
					}
				}
			}

				for (int j = 0; j < allPropertiesNames.size(); j++) {
					if (!(listForVerification
							.contains(allPropertiesNames.get(j)))) {
							invalidProperties = invalidProperties+ " "+allPropertiesNames.get(j)+",";
					
					}
				}
				if(!(invalidProperties.trim().isEmpty())){
					muleMessage.setInvocationProperty("invalidProperties", invalidProperties);
				resultList = new ArrayList<Map<String, Object>>();
				return resultList;
				}
			return resultList;
		} else {
			return propertyList;
		}

	}
	
	/**
	 * Do Validate check and return status
	 * 
	 * @param muleMessage
	 * @return Return status as 1 (true) or -1 (false)
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getValidationCheckForVarifyGet(MuleMessage muleMessage) {
		List<Map<String, Object>> propertyList = (List<Map<String, Object>>) muleMessage
				.getPayload();
		String invalidProperties = "";
		String inputSystemId = muleMessage
				.getInvocationProperty(AuditConstants.SYSTEM_ID);
		if (inputSystemId != null && inputSystemId.trim().length() > 0) {
			propertyList = getResultsBySystemId(propertyList, inputSystemId);
		}
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		List<String> listForVerification = new ArrayList<String>();
		String allPropertiesNames = muleMessage
				.getInvocationProperty(AuditConstants.PROPERTIES);
		allPropertiesNames = allPropertiesNames.replaceAll("%2C", ",");
		System.out.println(propertyList+"  101008105  "+ "-- List of property");
		if (allPropertiesNames != null && allPropertiesNames.length() > 0) {
			for (Map<String, Object> map : propertyList) {
				String propertyName = map.get(AuditConstants.PROP_NAME)
						.toString();
					if (allPropertiesNames.contains(propertyName)) {
						resultList.add(map);
						listForVerification.add(propertyName);
					}
			}

				String[] inputPropsArray = allPropertiesNames.split(",");
				for (int i = 0; i < inputPropsArray.length; i++) {
				if (!listForVerification
							.contains(inputPropsArray[i])) {
					invalidProperties = invalidProperties+ " "+inputPropsArray[i]+",";
					}
				}
				if(invalidProperties != ""){
					muleMessage.setInvocationProperty("invalidProperties", invalidProperties);
				resultList = new ArrayList<Map<String, Object>>();
				return resultList;
				}
			return resultList;
		} else {
			return propertyList;
		}

	}
	
	
}
