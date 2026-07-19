package vn.vnpt.database;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

@Component
public class ConnectionManager {

	public enum Connections {
		DEFAULT, ECMS, CABMAN, CSS
	}

	private static Map<String, HikariDataSource> mapHikariDataSources = new HashMap<String, HikariDataSource>();

	public ConnectionManager(ConnectionConfiguration connectionConfiguration, EntityManager entityManager)
			throws SQLException {
		// this.entityManager = entityManager;
		if (entityManager != null) {
			try {
				EntityManagerFactoryInfo info = ((EntityManagerFactoryInfo) entityManager.getEntityManagerFactory());
				mapHikariDataSources.put(Connections.DEFAULT.name().toLowerCase(),
						(HikariDataSource) info.getDataSource());
			} catch (Exception e) {
			}
		}
	}

	public DataSource getDataSource() {
		return getDataSource(Connections.DEFAULT);
	}

	public DataSource getDataSource(Connections connection) {
		return getDataSource(connection.name().toLowerCase());
	}

	private DataSource getDataSource(String name) {
		name = Connections.DEFAULT.name().toLowerCase();
		return mapHikariDataSources.get(name);
	}
}
