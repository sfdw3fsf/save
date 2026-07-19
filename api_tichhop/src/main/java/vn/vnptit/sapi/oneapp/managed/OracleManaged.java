package vn.vnptit.sapi.oneapp.managed;

import com.zaxxer.hikari.HikariDataSource;
//import io.dropwizard.lifecycle.Managed;
import oracle.ucp.jdbc.PoolDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import vn.vnptit.sapi.oneapp.properties.DataSourceConfig;
import vn.vnptit.sapi.oneapp.properties.DataSourceProperties;
//import vn.vnptnet.mapi.MyVnptApiConfiguration;
//import vn.vnptnet.mapi.config.OracleConfiguration;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 6/22/2018.
 */
public class OracleManaged {
    private static Logger logger = LoggerFactory.getLogger(OracleManaged.class);
    private static Map<String, PoolDataSource> dbs = new HashMap<String, PoolDataSource>();
    private static Map<String, HikariDataSource> hdbs = new HashMap<String, HikariDataSource>();
    private static Map<String, String> defaultDatasource = new HashMap<String, String>();

    /*@Autowired
    private DataSourceProperties dataSourceProperties;*/

    public OracleManaged(DataSourceProperties dataSourceProperties) throws SQLException {
        Map<String, DataSourceConfig> dbCnfs = dataSourceProperties.getDatabases();
        defaultDatasource = dataSourceProperties.getDefaultconfigs();
        for (Map.Entry<String, DataSourceConfig> entry : dbCnfs.entrySet()) {
            String dbName = entry.getKey();
            DataSourceConfig dbCnf = entry.getValue();

            //if (dbCnf.getPoolType() != null && dbCnf.getPoolType().equalsIgnoreCase("hikari")) {
            HikariDataSource ds = new HikariDataSource();
            ds.setMaximumPoolSize(dbCnf.getMaxPoolSize());
            ds.setMinimumIdle(dbCnf.getMaxPoolSize() / 10);
            ds.setDriverClassName("oracle.jdbc.OracleDriver");
            ds.setJdbcUrl(dbCnf.getUrl());
            ds.setUsername(dbCnf.getUsername());
            //ds.setPassword(pwdCryptor.decrypt(dbCnf.getPassword()));
            ds.setPassword(dbCnf.getPassword());
            ds.setLeakDetectionThreshold(dbCnf.getQueryTimeoutSecond() > 0 ? dbCnf.getQueryTimeoutSecond() * 1000 : 10000);
            ds.setAutoCommit(true);
            ds.setRegisterMbeans(true);
            hdbs.put(dbName, ds);
            /*} else {
                PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();
                pds.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
                pds.setURL(dbCnf.getUrl());
                pds.setUser(dbCnf.getUsername());
                //pds.setPassword(pwdCryptor.decrypt(dbCnf.getPassword()));
                pds.setPassword(dbCnf.getPassword());
                pds.setMaxPoolSize(dbCnf.getMaxPoolSize());
                pds.setValidateConnectionOnBorrow(true);
                pds.setSQLForValidateConnection("select * from dual");

                if (dbCnf.getAbandonedConnTimeoutSecond() > 0)
                    pds.setAbandonedConnectionTimeout(dbCnf.getAbandonedConnTimeoutSecond());
                if (dbCnf.getConnWaitTimeoutSecond() > 0)
                    pds.setConnectionWaitTimeout(dbCnf.getConnWaitTimeoutSecond());
                *//*if (dbCnf.getQueryTimeoutSecond() > 0)
                    pds.setQueryTimeout(dbCnf.getQueryTimeoutSecond());*//*
                if (dbCnf.getMaxStatements() > 0)
                    pds.setMaxStatements(dbCnf.getMaxStatements());
                if (dbCnf.getMaxConnReuseTIme() > 0)
                    pds.setMaxConnectionReuseTime(dbCnf.getMaxConnReuseTIme());
                if (dbCnf.getMaxConnReuseCount() > 0)
                    pds.setMaxConnectionReuseCount(dbCnf.getMaxConnReuseCount());
                if (dbCnf.getInactiveConnTimeoutSecond() > 0)
                    pds.setInactiveConnectionTimeout(dbCnf.getInactiveConnTimeoutSecond());

                    *//*
java.util.Properties props = new java.util.Properties();
props.setProperty("password","mypassword");
props.setProperty("user","myusername");
props.put("v$session.osuser", System.getProperty("user.name").toString());
props.put("v$session.machine", InetAddress.getLocalHost().getCanonicalHostName());
props.put("v$session.program", "My Program Name");
pds.setConnectionFactoryProperties(props);
                    * *//*
             *//*java.util.Properties props = new java.util.Properties();
                    props.put("v$session.program", configuration.getAppInfo().get(0));
                    pds.setConnectionFactoryProperties(props);*//*

                dbs.put(dbName, pds);
            }*/
        }
    }
    /*public static void init(BasicTextEncryptor pwdCryptor, List<OracleConfiguration> dbCnfs, MyVnptApiConfiguration configuration) throws Exception {
        for (OracleConfiguration dbCnf : dbCnfs) {
            if (StringUtils.isNotBlank(dbCnf.getJdni())) {
                Hashtable contextArgs = new Hashtable();

                contextArgs.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");

                contextArgs.put(Context.PROVIDER_URL, dbCnf.getJdni());

                Context myCurrentContext = new InitialContext(contextArgs);

                PoolDataSource pds = (PoolDataSource) myCurrentContext.lookup(dbCnf.getJdniName());
                dbs.put(dbCnf.getName(), pds);
            } else {
                try {
                    if (dbCnf.getPoolType() != null && dbCnf.getPoolType().equalsIgnoreCase("hikari")) {
                        HikariDataSource ds = new HikariDataSource();
                        ds.setMaximumPoolSize(dbCnf.getMaxPoolSize());
                        ds.setDriverClassName("oracle.jdbc.OracleDriver");
                        ds.setJdbcUrl(dbCnf.getUrl());
                        ds.setUsername(dbCnf.getUser());
                        ds.setPassword(pwdCryptor.decrypt(dbCnf.getPassword()));
                        ds.setLeakDetectionThreshold(dbCnf.getQueryTimeoutSecond() > 0 ? dbCnf.getQueryTimeoutSecond() * 1000 : 10000);
                        ds.setRegisterMbeans(true);
                        hdbs.put(dbCnf.getName(), ds);
                    } else {
                        PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();
                        pds.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
                        pds.setURL(dbCnf.getUrl());
                        pds.setUser(dbCnf.getUser());
                        pds.setPassword(pwdCryptor.decrypt(dbCnf.getPassword()));
                        pds.setMaxPoolSize(dbCnf.getMaxPoolSize());
                        pds.setValidateConnectionOnBorrow(true);
                        pds.setSQLForValidateConnection("select * from dual");

                        if (dbCnf.getAbandonedConnTimeoutSecond() > 0)
                            pds.setAbandonedConnectionTimeout(dbCnf.getAbandonedConnTimeoutSecond());
                        if (dbCnf.getConnWaitTimeoutSecond() > 0)
                            pds.setConnectionWaitTimeout(dbCnf.getConnWaitTimeoutSecond());
                        if (dbCnf.getQueryTimeoutSecond() > 0)
                            pds.setQueryTimeout(dbCnf.getQueryTimeoutSecond());
                        if (dbCnf.getMaxStatements() > 0)
                            pds.setMaxStatements(dbCnf.getMaxStatements());
                        if (dbCnf.getMaxConnReuseTIme() > 0)
                            pds.setMaxConnectionReuseTime(dbCnf.getMaxConnReuseTIme());
                        if (dbCnf.getMaxConnReuseCount() > 0)
                            pds.setMaxConnectionReuseCount(dbCnf.getMaxConnReuseCount());
                        if (dbCnf.getInactiveConnTimeoutSecond() > 0)
                            pds.setInactiveConnectionTimeout(dbCnf.getInactiveConnTimeoutSecond());

                    *//*
java.util.Properties props = new java.util.Properties();
props.setProperty("password","mypassword");
props.setProperty("user","myusername");
props.put("v$session.osuser", System.getProperty("user.name").toString());
props.put("v$session.machine", InetAddress.getLocalHost().getCanonicalHostName());
props.put("v$session.program", "My Program Name");
pds.setConnectionFactoryProperties(props);
                    * *//*
     *//*java.util.Properties props = new java.util.Properties();
                    props.put("v$session.program", configuration.getAppInfo().get(0));
                    pds.setConnectionFactoryProperties(props);*//*

                        dbs.put(dbCnf.getName(), pds);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }*/

    public DataSource getDataSource(String name) {
        return dbs.containsKey(name) ? dbs.get(name) : hdbs.get(name);
    }

    public Connection getConnection(String name) throws SQLException, NullPointerException {
        return getDataSource(name).getConnection();
    }

    public String getDefaultDataSource() {
        return defaultDatasource.get("database");
    }

    /*@Override
    public void start() throws Exception {

    }

    @Override
    public void stop() throws Exception {

    }*/
}
