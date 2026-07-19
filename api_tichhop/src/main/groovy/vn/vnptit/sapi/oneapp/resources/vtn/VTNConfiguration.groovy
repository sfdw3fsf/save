package vn.vnptit.sapi.oneapp.resources.vtn


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ws.client.vtn.VTNAPIPortBindingStub
import ws.client.vtn.VTNAPIServiceLocator

import javax.net.ssl.KeyManager
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import java.security.SecureRandom

@Configuration
class VTNConfiguration {
    @Bean("vtnServicesStubThreadLocal")
    ThreadLocal<VTNAPIPortBindingStub> get(@Autowired TrustManager[] trustAllCerts,
                                           @Value("\${spring.httpservices.vtn.url}") String url
    ){
        ThreadLocal.withInitial {
            SSLContext ctx = SSLContext.getInstance("TLS")
            ctx.init(new KeyManager[0], trustAllCerts, new SecureRandom())
            SSLContext.setDefault(ctx)
            VTNAPIServiceLocator locator = new VTNAPIServiceLocator()
            locator.setVTNAPIPortEndpointAddress(url)

            VTNAPIPortBindingStub service = (VTNAPIPortBindingStub) locator.getVTNAPIPort()
            return service
        }
    }

}
