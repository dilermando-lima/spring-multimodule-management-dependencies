package example.spring.core.oracle;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

@Configuration
public class DataSourceOracleConfig {

    @Bean
	public PoolDataSource getOracleDatasource(@Autowired DataSourceOracleProperties properties) throws SQLException {

		var pollDataSource = PoolDataSourceFactory.getPoolDataSource();
        var connectionFactoryClassName = oracle.jdbc.pool.OracleDataSource.class.getName();

		pollDataSource.setConnectionFactoryClassName(connectionFactoryClassName);
		pollDataSource.setURL(properties.getUrl());
		pollDataSource.setUser(properties.getUser());
		pollDataSource.setPassword(properties.getPassword());
		pollDataSource.setConnectionPoolName(properties.getConnectionPoolName());
		pollDataSource.setMinPoolSize(properties.getMinPoolSize());
		pollDataSource.setMaxPoolSize(properties.getMaxPoolSize());
		pollDataSource.setInitialPoolSize(properties.getInitialPoolSize());
		pollDataSource.setTimeToLiveConnectionTimeout(properties.getTimeToLiveConnectionTimeout());
		pollDataSource.setQueryTimeout(properties.getQueryTimeout());
		pollDataSource.setInactiveConnectionTimeout(properties.getInactiveConnectionTimeout());
		pollDataSource.setAbandonedConnectionTimeout(properties.getAbandonedConnectionTimeout());
		pollDataSource.setConnectionWaitTimeout(properties.getConnectionWaitTimeout());
		pollDataSource.setTimeoutCheckInterval(properties.getTimeoutCheckInterval());
		pollDataSource.setConnectionProperty("v$session.program", properties.getSessionName());

		return pollDataSource;
	}

    
}
