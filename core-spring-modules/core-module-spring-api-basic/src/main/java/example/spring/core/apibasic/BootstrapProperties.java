package example.spring.core.apibasic;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BootstrapProperties implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        BeanDefinition beanDefinition = registry.getBeanDefinition("propertySourcesPlaceholderConfigurer");
        if (beanDefinition instanceof AbstractBeanDefinition beanDefinitionAbstract ) {
            beanDefinitionAbstract.setBeanClass(PropertyPlaceholderAutoConfigurationDefault.class);
        }
    }

    @SuppressWarnings("java:S1186")
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {}

    
}