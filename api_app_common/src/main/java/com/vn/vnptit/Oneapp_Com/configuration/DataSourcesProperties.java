package com.vn.vnptit.Oneapp_Com.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@ConfigurationProperties(prefix = "spring")
public class DataSourcesProperties {
    private Map<String, DataSourceConfig> databases;


    public Map<String, DataSourceConfig> getDatabases() {
        return databases;
    }

    public void setDatabases(Map<String, DataSourceConfig> databases) {
        this.databases = databases;
    }
}
