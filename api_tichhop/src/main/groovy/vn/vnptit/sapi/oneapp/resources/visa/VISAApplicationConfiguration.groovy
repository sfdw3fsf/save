package vn.vnptit.sapi.oneapp.resources.visa

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ws.client.visa.application.ApplicationBusinessServicesSoapBindingStub
import ws.client.visa.application.WSAppImplServiceLocator

import javax.net.ssl.KeyManager
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate

@Configuration
class VISAApplicationConfiguration {
    @Bean("visaApplicationServicesStubThreadLocal")
    ThreadLocal<ApplicationBusinessServicesSoapBindingStub> get(@Autowired TrustManager[] trustAllCerts,
                                                                @Value("\${spring.httpservices.visa.url}") String visaUrl
    ){
        ThreadLocal.withInitial {
            SSLContext ctx = SSLContext.getInstance("TLS")
            ctx.init(new KeyManager[0], trustAllCerts, new SecureRandom())
            SSLContext.setDefault(ctx)
            WSAppImplServiceLocator locator = new WSAppImplServiceLocator()
            locator.setApplicationBusinessServicesEndpointAddress(visaUrl + "ApplicationBusinessServices")

            ApplicationBusinessServicesSoapBindingStub service = (ApplicationBusinessServicesSoapBindingStub) locator.getApplicationBusinessServices()
            return service
        }
    }

}
