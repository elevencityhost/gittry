package com.redn.connect.connectlogger;
		
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnection;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.mule.api.MuleContext;
import org.mule.api.context.MuleContextAware;
import org.springframework.core.io.PathResource;

import com.redn.connect.processor.connectconfig.ConnectConfiguration;
/**
 * 
 * @author Prathyusha.V
 *  
 *  This class is used to get database connection using system properties.
 *  and 
 */
public class ConnectionFactory  implements MuleContextAware{

	private static interface Singleton {
		final ConnectionFactory INSTANCE = new ConnectionFactory();
	}

	private final DataSource dataSource;

	@Inject
	private static MuleContext muleContext;

	public ConnectionFactory() {
		
		
		
		/*ConnectConfiguration connectConfiguration = muleContext.getRegistry().lookupObject("");
		String dbUser = connectConfiguration.get("ENV_DB_USER");
		String dbPassword = connectConfiguration.get("ENV_DB_PASSWORD");
		String dbURL = connectConfiguration.get("ENV_DB_URL");
		String dbDriverName = connectConfiguration.get("JDBC_DRIVER_NAME");*/
		// To read the below properties from zuul, not from wrapper.config
		ConnectConfiguration  connectConfiguration = new ConnectConfiguration();
		connectConfiguration.setLocation(new PathResource("connect_logger.properties"));
		String dbUser = connectConfiguration.get("connect.logger.db.user");
		String dbPassword = connectConfiguration.get("connect.logger.db.password");
		String dbURL = connectConfiguration.get("connect.logger.db.url");
		String dbDriverName = connectConfiguration.get("connect.logger.jdbc.driver");
		
		
		
		Properties properties = new Properties();
		properties.setProperty("user", dbUser);
		properties.setProperty("password", dbPassword);

		try {
			Class.forName(dbDriverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		GenericObjectPool<PoolableConnection> pool = new GenericObjectPool<PoolableConnection>();
		
		DriverManagerConnectionFactory connectionFactory = new DriverManagerConnectionFactory(dbURL, properties
				);
		new PoolableConnectionFactory(
				connectionFactory, pool, null, "SELECT 1", 3, false, false, Connection.TRANSACTION_READ_COMMITTED
				);

		this.dataSource = new PoolingDataSource(pool);
	
	}

	public static Connection getDatabaseConnection() throws SQLException {
		
		
		Connection connection = Singleton.INSTANCE.dataSource.getConnection();
		

		return connection;
	}

	public static MuleContext getMuleContext() {
		return muleContext;
	}

	public void setMuleContext(MuleContext muleContext) {
		ConnectionFactory.muleContext = muleContext;
	}
}
