
package example.spring.core.oracle;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "core.datasource.oracle")
public class DataSourceOracleProperties{

    private String url = "jdbc:oracle:thin:@localhost:1521/SERVICEA";
    private String user = "USER_DEFAULT";
    private String password = "PASS_DEFAULT";
    private String connectionPoolName = "LOCAL_RUNNING";
    private Integer minPoolSize = 2;
    private Integer maxPoolSize = 2;
    private Integer initialPoolSize = 2;
    private Integer inactiveConnectionTimeout = 60;
    private Integer connectionWaitTimeout = 120;
    private Integer timeoutCheckInterval = 30;
    private Integer timeToLiveConnectionTimeout = 180;
    private Integer queryTimeout = 0;
    private Integer abandonedConnectionTimeout = 6;
    private String sessionName = "LOCAL_RUNNING";
    
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getConnectionPoolName() {
        return connectionPoolName;
    }
    public void setConnectionPoolName(String connectionPoolName) {
        this.connectionPoolName = connectionPoolName;
    }
    public Integer getMinPoolSize() {
        return minPoolSize;
    }
    public void setMinPoolSize(Integer minPoolSize) {
        this.minPoolSize = minPoolSize;
    }
    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }
    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }
    public Integer getInitialPoolSize() {
        return initialPoolSize;
    }
    public void setInitialPoolSize(Integer initialPoolSize) {
        this.initialPoolSize = initialPoolSize;
    }
    public Integer getInactiveConnectionTimeout() {
        return inactiveConnectionTimeout;
    }
    public void setInactiveConnectionTimeout(Integer inactiveConnectionTimeout) {
        this.inactiveConnectionTimeout = inactiveConnectionTimeout;
    }
    public Integer getConnectionWaitTimeout() {
        return connectionWaitTimeout;
    }
    public void setConnectionWaitTimeout(Integer connectionWaitTimeout) {
        this.connectionWaitTimeout = connectionWaitTimeout;
    }
    public Integer getTimeoutCheckInterval() {
        return timeoutCheckInterval;
    }
    public void setTimeoutCheckInterval(Integer timeoutCheckInterval) {
        this.timeoutCheckInterval = timeoutCheckInterval;
    }
    public Integer getTimeToLiveConnectionTimeout() {
        return timeToLiveConnectionTimeout;
    }
    public void setTimeToLiveConnectionTimeout(Integer timeToLiveConnectionTimeout) {
        this.timeToLiveConnectionTimeout = timeToLiveConnectionTimeout;
    }
    public Integer getQueryTimeout() {
        return queryTimeout;
    }
    public void setQueryTimeout(Integer queryTimeout) {
        this.queryTimeout = queryTimeout;
    }
    public Integer getAbandonedConnectionTimeout() {
        return abandonedConnectionTimeout;
    }
    public void setAbandonedConnectionTimeout(Integer abandonedConnectionTimeout) {
        this.abandonedConnectionTimeout = abandonedConnectionTimeout;
    }
    public String getSessionName() {
        return sessionName;
    }
    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }


    
}
