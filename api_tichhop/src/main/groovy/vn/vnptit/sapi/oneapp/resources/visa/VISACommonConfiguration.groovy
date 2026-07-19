package vn.vnptit.sapi.oneapp.resources.visa

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ws.client.visa.common.CommonBusinessServicesSoapBindingStub
import ws.client.visa.common.WSComImplServiceLocator

import javax.net.ssl.KeyManager
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import java.security.SecureRandom

@Configuration
class VISACommonConfiguration {
    @Bean("visaCommonServicesStubThreadLocal")
    ThreadLocal<CommonBusinessServicesSoapBindingStub> get(@Autowired TrustManager[] trustAllCerts,
                                                           @Value("\${spring.httpservices.visa.url}") String visaUrl
    ){
        ThreadLocal.withInitial {
            SSLContext ctx = SSLContext.getInstance("TLS")
            ctx.init(new KeyManager[0], trustAllCerts, new SecureRandom())
            SSLContext.setDefault(ctx)
            WSComImplServiceLocator locator = new WSComImplServiceLocator()
            locator.setCommonBusinessServicesEndpointAddress(visaUrl + "CommonBusinessServices")

            CommonBusinessServicesSoapBindingStub service = (CommonBusinessServicesSoapBindingStub) locator.getCommonBusinessServices()
            return service
        }
    }
}
