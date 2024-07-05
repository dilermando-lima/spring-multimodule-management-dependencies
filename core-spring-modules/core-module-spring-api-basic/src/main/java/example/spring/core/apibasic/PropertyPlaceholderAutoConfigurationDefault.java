package example.spring.core.apibasic;

import java.util.Properties;

import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

public class PropertyPlaceholderAutoConfigurationDefault extends PropertyPlaceholderAutoConfiguration{

    
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurerDefault();
    }

    public static class PropertySourcesPlaceholderConfigurerDefault extends PropertySourcesPlaceholderConfigurer{

        public PropertySourcesPlaceholderConfigurerDefault(){
            setIgnoreUnresolvablePlaceholders(true);
            setProperties(getDefaultProperties());
        }

        private Properties getDefaultProperties(){

            var properties = new Properties();

            properties.setProperty("server.port","8085");

            // handle 404 mapping response
            properties.setProperty("spring.mvc.throw-exception-if-no-handler-found","true");
            properties.setProperty("spring.web.resources.add-mappings","false");

            // override beans when configuring context
            properties.setProperty("spring.main.allow-bean-definition-overriding","true");

            // default path swagger
            properties.setProperty("springdoc.swagger-ui.path", "/swagger");
            properties.setProperty("springdoc.api-docs.path", "/swagger/api-docs");

            // ignore null attrs in response body
            properties.setProperty("spring.jackson.default-property-inclusion", "NON_NULL");

            // actuator
            properties.setProperty("management.endpoints.web.base-path","/");
            properties.setProperty("management.endpoint.health.enabled","true");
            properties.setProperty("management.endpoints.web.exposure.include", "health");
            properties.setProperty("management.endpoints.web.path-mapping.health", "healthcheck");
            properties.setProperty("management.endpoint.health.probes.enabled", "true");

            return properties;

        }   
    } 

    
}

