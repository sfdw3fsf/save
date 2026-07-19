package vn.vnptit.sapi.oneapp.resources.subman


import org.apache.axis.message.SOAPHeaderElement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.tempuri.AuthHeader
import org.tempuri.AuthHeaderE
import ws.client.cts.LineTestWSLocator
import ws.client.cts.LineTestWSSoap_BindingStub
import ws.client.subman.EwsdproLocator
import ws.client.subman.EwsdproSoap12Stub

import javax.net.ssl.KeyManager
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.xml.soap.SOAPElement
import java.security.SecureRandom

@Configuration
class SubmanConfiguration {
    @Bean("submanServicesStubThreadLocal")
    ThreadLocal<EwsdproSoap12Stub> get(
            @Autowired TrustManager[] trustAllCerts,
            @Value("\${spring.httpservices.subman.url}") String url
    ){
        ThreadLocal.withInitial {
            SSLContext ctx = SSLContext.getInstance("TLS")
            ctx.init(new KeyManager[0], trustAllCerts, new SecureRandom())
            SSLContext.setDefault(ctx)
            EwsdproLocator locator = new EwsdproLocator()
            locator.setewsdproSoap12EndpointAddress(url)

            EwsdproSoap12Stub service = (EwsdproSoap12Stub) locator.getewsdproSoap12()
            return service
        }
    }

    @Bean("skey")
    String getSkey(@Value("\${spring.httpservices.subman.skey}") String skey
    ){
        return skey
    }
}
