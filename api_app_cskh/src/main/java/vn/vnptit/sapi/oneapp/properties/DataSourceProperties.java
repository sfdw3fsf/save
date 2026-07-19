package vn.vnptit.sapi.oneapp.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

//import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "spring")
//@PropertySource(value = "classpath:datasources.yaml", factory = YamlPropertySourceFactory.class)
public class DataSourceProperties {
    //Map<String, Map<String, String>> datasources;
    private Map<String, DataSourceConfig> databases;
    private Map<String, HttpServiceConfig> httpservices;
    private Map<String, String> defaultconfigs;
    private Map<String, FileStorageConfig> ftps;
    private Map<String, FileStorageConfig> objectStorages;

    public Map<String, DataSourceConfig> getDatabases() {
        return databases;
    }

    public void setDatabases(Map<String, DataSourceConfig> databases) {
        this.databases = databases;
    }

    public Map<String, HttpServiceConfig> getHttpservices() {
        return httpservices;
    }

    public void setHttpservices(Map<String, HttpServiceConfig> httpservices) {
        this.httpservices = httpservices;
    }

    public Map<String, String> getDefaultconfigs() {
        return defaultconfigs;
    }

    public void setDefaultconfigs(Map<String, String> defaultconfigs) {
        this.defaultconfigs = defaultconfigs;
    }

    public Map<String, FileStorageConfig> getFtps() {
        return ftps;
    }

    public void setFtps(Map<String, FileStorageConfig> ftps) {
        this.ftps = ftps;
    }

    public Map<String, FileStorageConfig> getObjectStorages() {
        return objectStorages;
    }

    public void setObjectStorages(Map<String, FileStorageConfig> objectStorages) {
        this.objectStorages = objectStorages;
    }
}
