package vn.vnpt.database;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "spring")
public class ConnectionConfiguration {
	private Map<String, ConnectionProperties> databases;

	public Map<String, ConnectionProperties> getDatabases() {
		return databases;
	}

	public void setDatabases(Map<String, ConnectionProperties> databases) {
		this.databases = databases;
	}
}
