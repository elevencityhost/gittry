package com.redn.connect.ftp.message.dispatchers;
/**
 * @author Laxshmi Maram
 *  *
 *  This class	will call the CustomSftpMessageDispatcher class
 */
import org.mule.api.MuleException;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.transport.MessageDispatcher;
import org.mule.transport.AbstractMessageDispatcherFactory;

public class CustomSftpMessageDispatcherFactory extends
		AbstractMessageDispatcherFactory {

	@Override
	public MessageDispatcher create(OutboundEndpoint endpoint) throws MuleException
    {
        return new CustomSftpMessageDispatcher(endpoint);
    }

}

