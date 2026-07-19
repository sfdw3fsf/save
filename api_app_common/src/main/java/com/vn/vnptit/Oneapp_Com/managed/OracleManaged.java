package com.vn.vnptit.Oneapp_Com.managed;

import com.vn.vnptit.Oneapp_Com.common.BSSCrypto;
import com.vn.vnptit.Oneapp_Com.configuration.DataSourceConfig;
import com.vn.vnptit.Oneapp_Com.configuration.DataSourcesProperties;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by User on 6/22/2018.
 */
public class OracleManaged {

    private static Map<String, HikariDataSource> hdbs = new HashMap<>();


    public OracleManaged(DataSourcesProperties dataSourceProperties) {
        Map<String, DataSourceConfig> dbCnfs = dataSourceProperties.getDatabases();

        for (Map.Entry<String, DataSourceConfig> entry : dbCnfs.entrySet()) {
            String dbName = entry.getKey();
            DataSourceConfig dbCnf = entry.getValue();

            HikariDataSource ds = new HikariDataSource();
            ds.setMaximumPoolSize(dbCnf.getMaxPoolSize());
            ds.setMinimumIdle(dbCnf.getMaxPoolSize() / 10);
            ds.setDriverClassName("oracle.jdbc.OracleDriver");
            ds.setJdbcUrl(dbCnf.getUrl());
            ds.setUsername(BSSCrypto.decrypt(dbCnf.getUsername(), dbCnf.getCryptoKey()) );
            ds.setPassword( BSSCrypto.decrypt(dbCnf.getPassword(), dbCnf.getCryptoKey()));
            ds.setLeakDetectionThreshold(dbCnf.getQueryTimeoutSecond() > 0 ? dbCnf.getQueryTimeoutSecond() * 1000 : 10000);
            ds.setAutoCommit(true);
            ds.setRegisterMbeans(true);
            hdbs.put(dbName, ds);
        }
    }


    public DataSource getDataSource(String name) {
        return hdbs.get(name);
    }

    public Connection getConnection(String name) throws SQLException, NullPointerException {
        return getDataSource(name).getConnection();
    }

}
