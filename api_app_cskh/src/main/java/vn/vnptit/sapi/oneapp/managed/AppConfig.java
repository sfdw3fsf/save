package vn.vnptit.sapi.oneapp.managed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import vn.vnptit.sapi.oneapp.interceptor.SqlInjectInterceptor;
import vn.vnptit.sapi.oneapp.properties.DataSourceProperties;
import vn.vnptit.sapi.oneapp.resources.GenericApi;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
class AppConfig extends WebMvcConfigurerAdapter {

    @Value("${spring.defaultconfigs.sqli.normal-length:0}")
    private int NORMAL_LEN;
    @Value("${spring.defaultconfigs.sqli.max-length:0}")
    private int MAX_LEN;
    @Value("${spring.defaultconfigs.sqli.number-of-long-parameter:0}")
    private int NUMBER_OF_LONGPARAM;


    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new SqlInjectInterceptor(NORMAL_LEN, MAX_LEN, NUMBER_OF_LONGPARAM)).excludePathPatterns("/cskh_vnpt/cskh36t/capNhatStickerBH");
//        registry.addInterceptor(new SqlInjectInterceptor())
//                .addPathPatterns("/khieunai/hoanThanh", "/khieunai/dsPhuongAnXL", "/khieunai/dsKetQuaLoi",
//                        "/khieunai/dsHTTraLoi", "/khieunai/chuyenTo", "/khieunai/khoaPhieu", "/tienIch/chuyenDoiMytvVMP");
    }

    @Bean("oracleManaged")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public OracleManaged getOracleManaged() throws SQLException {
        return new OracleManaged(dataSourceProperties);
    }

    @Bean("httpManaged")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public HttpManaged getHttpManated() throws NoSuchAlgorithmException, KeyManagementException {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {return true;}
        });
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
        }};

        SSLContext sc = SSLContext.getInstance("SSL");
        //SSLContext sc = SSLContext.getInstance("TLS");
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

    @Bean("objectStorageManaged")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ObjectStorageManaged getObjectStorageManaged() {
        return new ObjectStorageManaged();
    }

    @Bean("objectStoragesManaged")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ObjectStoragesManaged getObjectStoragesManaged() throws SQLException {
        return new ObjectStoragesManaged(dataSourceProperties.getObjectStorages());
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
