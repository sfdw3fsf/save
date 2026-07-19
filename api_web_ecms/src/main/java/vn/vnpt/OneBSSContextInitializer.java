package vn.vnpt;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import com.zaxxer.hikari.HikariConfig;

import vn.vnpt.Utils.BSSCrypto;

public class OneBSSContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext ctx) {
        ctx.addBeanFactoryPostProcessor(beanFactory -> {
            beanFactory.registerSingleton("customHikariConfig", new BeanPostProcessor() {
                @Override
                public Object postProcessBeforeInitialization(Object bean, String beanName) {
                    if (bean instanceof HikariConfig) {
                        HikariConfig config = (HikariConfig) bean;
                        String key = ctx.getEnvironment().getProperty("spring.datasource.crypto_key");
                        config.setUsername(BSSCrypto.decrypt(config.getUsername(), key));
                        config.setPassword(BSSCrypto.decrypt(config.getPassword(), key));
                    }
                    return bean;
                }
            });
        });
    }
}