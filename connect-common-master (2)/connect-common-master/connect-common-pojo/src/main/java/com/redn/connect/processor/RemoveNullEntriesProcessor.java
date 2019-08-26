package com.redn.connect.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

/**
 * @author Vinay Kumar Thota
 *
 * This Processor is used to remove any elements with null values
 * or empty values from a Map recursively
 * 
 */
public class RemoveNullEntriesProcessor implements Callable{

	
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		
		MuleMessage message = eventContext.getMessage();
		
		if(!(message.getPayload() instanceof Map)){
			
			return message;
		}
		
		@SuppressWarnings("rawtypes")
		Map keyValMap = (Map) message.getPayload();
		
		List<String> exclusionList = new ArrayList<String>();
		exclusionList.add("Summary");
		
		removeNullElements(keyValMap, exclusionList);
		
		return keyValMap;
	}

	@SuppressWarnings({ "rawtypes" })
	private static void removeNullElements(Map keyValueMap, List<String> exclusionList){
		
		Set keySet = keyValueMap.keySet();
		Object[] array = keySet.toArray();
		
		for(int i=0; i< array.length; i++){
			Object value = keyValueMap.get(array[i]);
			if(value instanceof Map){
				removeNullElements((Map)value,exclusionList);
				if(((Map) value).size() == 0 && !exclusionList.contains(array[i])){
					keyValueMap.remove(array[i]);
				}
			}else if(value instanceof List){
				List listValues = (List)value;
				
				List<Map> removalList = new ArrayList<Map>();
				for(int j = 0 ; j < listValues.size(); j++){
					Object listValue = listValues.get(j);
					if(listValue instanceof Map){
						Map listValueMap = (Map) listValue;
						removeNullElements(listValueMap,exclusionList);
						if(listValueMap.size() == 0){
							removalList.add((Map)listValue);
						}
					}
				}
				
				for(Map eachRemovalElement: removalList){
					listValues.remove(eachRemovalElement);
				}
			}
			if(value == null){
				keyValueMap.remove(array[i]);
			}
		}
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String args[]){
		Map mapping = new HashMap();
		
		//mapping.put("Key1", "Value1");
		Map value2 = new HashMap();
		//value2.put("Value2Key1", "value2Key1Value");
		//value2.put("Value2Key2", null);
		//Map innerInnerMap = new HashMap();
		//innerInnerMap.put("II1", "II1Value");
		//innerInnerMap.put("II2", null);
		//innerInnerMap.put("II3", "II3Value");
		//value2.put("Value2Key3", innerInnerMap);
		mapping.put("Key2", value2);
		
		List listValues = new ArrayList();
		Map m1 = new HashMap();
		m1.put("M1", "M1Val");
		m1.put("M2", null);
		m1.put("M3", "M1Val3");
		
		Map m2 = new HashMap();
		m2.put("2M1", null);
		m2.put("2M2", null);
		m2.put("2M3", "M1Val3");
		
		Map m3 = new HashMap();
		m3.put("3M1", new HashMap());
		
		
		
		listValues.add(m1);
		listValues.add(m2);
		listValues.add(m3);
		
		
		value2.put("ListKey", listValues);
		
		Map m4 = new HashMap();
		m4.put("normalValue", new HashMap());
		
		value2.put("NormalKey", m4);
		
		System.out.println(mapping);
		List<String> exclusionList = new ArrayList<String>();
		exclusionList.add("3M1");
		removeNullElements(mapping,exclusionList);
		System.out.println(mapping);
		
	}
	
}
