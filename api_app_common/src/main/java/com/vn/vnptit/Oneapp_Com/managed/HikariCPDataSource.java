package com.vn.vnptit.Oneapp_Com.managed;

import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.vn.vnptit.Oneapp_Com.common.BSSCrypto;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author DanhNQ
 */
@Component
public class HikariCPDataSource {

    private static void createDataSource(String url, String user, String password, Integer maxpool, String dbName, String cryptoKey) {
        HikariConfig hikariConfig = getHikariConfig(url, user, password, maxpool, dbName, cryptoKey);
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        DB_POOLMAP.put(dbName, hikariDataSource);
    }

    private final static Map<String, DataSource> DB_POOLMAP = new ConcurrentHashMap<>(2);

    public synchronized static Connection getConnection(String url, String user, String password, Integer maxpool, String dbName, String cryptoKey) throws Exception {
        if (!DB_POOLMAP.containsKey(dbName)) {
            createDataSource(url, user, password, maxpool, dbName, cryptoKey);
        }
        DataSource dataSource = DB_POOLMAP.get(dbName);
        return dataSource.getConnection();
    }

    private static HikariConfig getHikariConfig(String url, String user, String password, Integer maxpool, String dbname, String cryptoKey) {

        HikariConfig hikaConfig = new HikariConfig();
        hikaConfig.addDataSourceProperty("cachePrepStmts", "false");
        //This is same as passing the Connection info to the DriverManager class.
        //your jdbc url. in my case it is mysql.
        hikaConfig.setJdbcUrl(url);
        //username
        hikaConfig.setUsername(BSSCrypto.decrypt(user, cryptoKey));
        //password
        hikaConfig.setPassword(BSSCrypto.decrypt(password, cryptoKey));
        hikaConfig.setAutoCommit(true);
        hikaConfig.setDriverClassName("oracle.jdbc.OracleDriver");

        hikaConfig.setMaxLifetime(45000);

        hikaConfig.setPoolName(dbname);
        hikaConfig.setMaximumPoolSize(10);
        hikaConfig.setMinimumIdle(3);
        hikaConfig.setConnectionTimeout(20000);
        hikaConfig.setIdleTimeout(10000);
        return hikaConfig;
    }

    public HikariCPDataSource() {
        // contructor
    }
}
