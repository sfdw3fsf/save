package vn.vnptit.sapi.oneapp.managed;

import com.zaxxer.hikari.HikariDataSource;
import oracle.ucp.jdbc.PoolDataSource;
import vn.vnptit.sapi.oneapp.properties.DataSourceConfig;
import vn.vnptit.sapi.oneapp.properties.DataSourceProperties;
import vn.vnptit.sapi.oneapp.util.BSSCrypto;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class OracleManaged {
    private static Map<String, PoolDataSource> dbs = new HashMap<String, PoolDataSource>();
    private static Map<String, HikariDataSource> hdbs = new HashMap<String, HikariDataSource>();
    private static Map<String, String> defaultDatasource = new HashMap<String, String>();

    public OracleManaged(DataSourceProperties dataSourceProperties) throws SQLException {
        Map<String, DataSourceConfig> dbCnfs = dataSourceProperties.getDatabases();
        defaultDatasource = dataSourceProperties.getDefaultconfigs();
        for (Map.Entry<String, DataSourceConfig> entry : dbCnfs.entrySet()) {
            String dbName = entry.getKey();
            DataSourceConfig dbCnf = entry.getValue();

            HikariDataSource ds = new HikariDataSource();
            ds.setMaximumPoolSize(dbCnf.getMaxPoolSize());
            ds.setMinimumIdle(dbCnf.getMinimumIdle());
            ds.setMaxLifetime(dbCnf.getMaxLifetime());
            ds.setDriverClassName("oracle.jdbc.OracleDriver");
            ds.setJdbcUrl(dbCnf.getUrl());
            ds.setUsername(BSSCrypto.decrypt(dbCnf.getUsername(), dataSourceProperties.getDefaultconfigs().get("crypto_key")));
            ds.setPassword(BSSCrypto.decrypt(dbCnf.getPassword(), dataSourceProperties.getDefaultconfigs().get("crypto_key")));
            //ds.setUsername(dbCnf.getUsername());
            //ds.setPassword(dbCnf.getPassword());
            ds.setLeakDetectionThreshold(100000);
            ds.setConnectionTimeout(360 * 1000);
            ds.setMaxLifetime(300000);
            ds.setAutoCommit(true);
            ds.setRegisterMbeans(true);
            hdbs.put(dbName, ds);
        }
    }

    public DataSource getDataSource(String name) {
        name = "css";
        return dbs.containsKey(name) ? dbs.get(name) : hdbs.containsKey(name) ? hdbs.get(name) : hdbs.get("css");
    }

    public Connection getConnection(String name) throws SQLException, NullPointerException {
        return getDataSource(name).getConnection();
    }

    public String getDefaultDataSource() {
        return defaultDatasource.get("database");
    }
}
