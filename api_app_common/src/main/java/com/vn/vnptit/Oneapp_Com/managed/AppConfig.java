package com.vn.vnptit.Oneapp_Com.managed;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.vn.vnptit.Oneapp_Com.configuration.DataSourcesProperties;

import java.sql.SQLException;

@Configuration
class AppConfig {

    @Autowired
    private DataSourcesProperties dataSourcesProperties;

    @Bean("oracleManaged")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public OracleManaged getOracleManaged() throws SQLException {
        return new OracleManaged(dataSourcesProperties);
    }

    @Bean("loggingManaged")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public LoggingManaged getLoggingManaged() {
        return new LoggingManaged();
    }
}
