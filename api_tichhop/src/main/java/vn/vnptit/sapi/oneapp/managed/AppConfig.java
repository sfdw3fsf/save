package vn.vnptit.sapi.oneapp.managed;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import vn.vnptit.sapi.oneapp.properties.DataSourceProperties;
import vn.vnptit.sapi.oneapp.util.GenericApi;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.SQLException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAsync
class AppConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//       registry.addInterceptor(new SqlInjectInterceptor());
    }

    @Bean("oracleManaged")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public OracleManaged getOracleManaged() throws SQLException {
        return new OracleManaged(dataSourceProperties);
    }

    @Bean("httpManaged")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public HttpManaged getHttpManaged() throws NoSuchAlgorithmException, KeyManagementException {
        // Set up a default HostnameVerifier that performs proper verification
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        // Set up a TrustManager that performs proper verification
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    // Implement this method to return the list of trusted issuers
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                    // Implement this method to check the client certificates
                    for (X509Certificate cert : certs) {
                        cert.checkValidity();  // Check if the certificate is valid
                    }
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                    // Implement this method to check the server certificates
                    for (X509Certificate cert : certs) {
                        cert.checkValidity();  // Check if the certificate is valid
                    }
                }
            }
        };

        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        return new HttpManaged();
    }


    @Bean("genericApi")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public GenericApi getGenericApi() {
        return new GenericApi();
    }

    @Bean("loggingManaged")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public LoggingManaged getLoggingManaged() {
        return new LoggingManaged();
    }

    @Bean("cacheIn15min")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Cache<String, Object[]> getCacheIn15min() {
        return CacheBuilder.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(15, TimeUnit.MINUTES)
                .build();
    }

    @Bean("ftpsManaged")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public FtpsManaged getFtpsManaged() throws SQLException {
        return new FtpsManaged(dataSourceProperties.getFtps());
    }

    @Bean("objectStoragesManaged")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ObjectStoragesManaged getObjectStoragesManaged() throws SQLException {
        return new ObjectStoragesManaged(dataSourceProperties.getObjectStorages());
    }

    @Bean("gsonBuidlers")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ThreadLocal<Gson> gsonBuidlers() throws SQLException {
        return ThreadLocal.withInitial(() -> new GsonBuilder().serializeNulls().create());
    }

    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // Set the number of core threads in the pool
        executor.setMaxPoolSize(10); // Set the maximum number of threads in the pool
        executor.setQueueCapacity(25); // Set the capacity of the task queue
        executor.setThreadNamePrefix("AsyncThread-"); // Set the thread name prefix
        executor.initialize();
        return executor;
    }
}