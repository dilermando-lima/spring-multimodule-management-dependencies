package example.spring.core.apibasic;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "core.error")
public class ExceptionRestProperties {

    private String applicationOriginDefault;
    private boolean enableLogBadRequestMessage = false;
    private boolean enableLogInternalServerErrorStrace = true;
    
    public String getApplicationOriginDefault() {
        return applicationOriginDefault;
    }
    public void setApplicationOriginDefault(String applicationOriginDefault) {
        this.applicationOriginDefault = applicationOriginDefault;
    }
    public boolean isEnableLogBadRequestMessage() {
        return enableLogBadRequestMessage;
    }
    public void setEnableLogBadRequestMessage(boolean enableLogBadRequestMessage) {
        this.enableLogBadRequestMessage = enableLogBadRequestMessage;
    }
    public boolean isEnableLogInternalServerErrorStrace() {
        return enableLogInternalServerErrorStrace;
    }
    public void setEnableLogInternalServerErrorStrace(boolean enableLogInternalServerErrorStrace) {
        this.enableLogInternalServerErrorStrace = enableLogInternalServerErrorStrace;
    }

    
}