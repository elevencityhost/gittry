package com.redn.connect.processor;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import com.redn.connect.util.ConnectUtils;
import com.redn.connect.vo.ConnectEnterpriseMessage;
/**
 * @author jigyasa.arora
 * 
 * This processor modifies CEM to trim XML prolog statement from payload
 *
 */
public class RemoveProlog implements Callable{

		private static ConnectUtils utils = new ConnectUtils();

		@Override
		public Object onCall(MuleEventContext eventContext) throws Exception {

			ConnectEnterpriseMessage cem = (ConnectEnterpriseMessage) eventContext.getMessage().getPayload();

			String textContent = utils.getPayloadTextContentFromCEM(cem);
			int indexOfProlog = textContent.indexOf("?>");
			if (indexOfProlog != -1) {
				textContent = textContent.substring(indexOfProlog + 2);
			}

			return textContent;
		}
}
