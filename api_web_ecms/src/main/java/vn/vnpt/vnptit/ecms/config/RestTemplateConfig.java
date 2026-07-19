package vn.vnpt.vnptit.ecms.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.net.InetAddress;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    private final MiddlewarePropeties requestConfig;

    public RestTemplateConfig(MiddlewarePropeties requestConfig) {
        this.requestConfig = requestConfig;
    }


    @Bean
    @Primary
    public RestTemplate restTemplateDefault() {
        return new RestTemplate();
    }

    @Bean
    @Qualifier("vcenterRestTemplate")
    public RestTemplate restTemplate() throws Exception {

        DnsResolver dnsResolver = host -> {
            if (requestConfig.getSmartCloudExternal().getDomain().equals(host)) {
                return new InetAddress[]{
                        InetAddress.getByName(requestConfig.getSmartCloudExternal().getIp())
                };
            }
            return InetAddress.getAllByName(host);
        };

        // 1. Tạo SSLContext bỏ qua toàn bộ certificate validation
        SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial(null, (chain, authType) -> true) // Trust all certs
                .build();

        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(
                sslContext, NoopHostnameVerifier.INSTANCE);

        Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", sslSocketFactory)
                        .build();


        // 2. Cấu hình Pooling Connection Manager
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry,
                null,
                dnsResolver
        );
        cm.setMaxTotal(200); // tổng connection outbound
        cm.setDefaultMaxPerRoute(50); // connection tới VMware

        // 3. Cấu hình Timeouts
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(5 * 1000) // TCP Connect (5s)
                .setConnectionRequestTimeout(5 * 1000) // Chờ lấy kết nối từ pool (5s)
                .setSocketTimeout(30 * 1000) // Chờ trả dữ liệu (30s) - SocketTimeout trong HC4
                .build();

        // 4. Tạo HttpClient
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslSocketFactory)
                .setConnectionManager(cm)
                .setDefaultRequestConfig(config)
                .evictExpiredConnections()
                .build();

        // 5. Khởi tạo RestTemplate với factory
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);

        // 6. Thêm Interceptor cho các header mặc định
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        interceptors.add((request, body, execution) -> {
            request.getHeaders().add("x-private-key", this.requestConfig.getVcenter().getPrivateKey());
            request.getHeaders().add("x-app-name", this.requestConfig.getVcenter().getAppName());
            return execution.execute(request, body);
        });
        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }
}
