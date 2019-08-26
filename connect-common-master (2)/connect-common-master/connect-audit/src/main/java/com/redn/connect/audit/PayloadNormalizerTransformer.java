package com.redn.connect.audit;

/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

import org.mule.api.MuleMessage;
import org.mule.api.transformer.DataType;
import org.mule.api.transformer.Transformer;
import org.mule.api.transformer.TransformerException;
import org.mule.config.i18n.MessageFactory;
import org.mule.module.apikit.transform.DataTypePair;
import org.mule.module.apikit.transform.TransformerCache;
import org.mule.transformer.AbstractMessageTransformer;
import org.mule.transformer.types.DataTypeFactory;

import com.redn.connect.audit.constant.AuditConstants;
//import com.umgi.es.common.util.log.enterpriselogger.EnterpriseLogger;

/*************************************************************************************************************************
 * *@author  Laxshmi Maram
 

 * This class is used to transform the JSON message into List of Map objects.
 * 
 *************************************************************************************************************************/
public class PayloadNormalizerTransformer extends AbstractMessageTransformer
{
	
	@SuppressWarnings("rawtypes")
    @Override
    public Object transformMessage(MuleMessage message, String encoding) throws TransformerException
    {
		DataType sourceDataType = DataTypeFactory.create(message.getPayload().getClass(), AuditConstants.CONTENT_TYPE);    	
    	DataType resultDataType = getReturnDataType();

        Transformer transformer;
        try
        {
            transformer = TransformerCache.getTransformerCache(muleContext).get(new DataTypePair(sourceDataType, resultDataType));
        }
        catch (Exception e)
        {
            throw new TransformerException(MessageFactory.createStaticMessage(e.getMessage()), e);
        }
        
        // Transform the json message into list of object.
        return transformer.transform(message.getPayload());
    }
}
