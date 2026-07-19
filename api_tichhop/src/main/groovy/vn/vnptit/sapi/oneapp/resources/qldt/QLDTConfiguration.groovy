package vn.vnptit.sapi.oneapp.resources.qldt

import org.apache.axis.message.SOAPHeaderElement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ws.client.qldt.*

import javax.net.ssl.KeyManager
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.xml.soap.SOAPElement
import java.security.SecureRandom

@Configuration
class QLDTConfiguration {
    @Bean("qldtServicesStubThreadLocal")
    ThreadLocal<MainSVRSoap_BindingStub> get(
            @Autowired TrustManager[] trustAllCerts,
            @Value("\${spring.httpservices.qldt.url}") String url
    ){
        ThreadLocal.withInitial {
            SSLContext ctx = SSLContext.getInstance("TLS")
            ctx.init(new KeyManager[0], trustAllCerts, new SecureRandom())
            SSLContext.setDefault(ctx)
            MainSVRLocator locator = new MainSVRLocator()
            locator.setMainSVRSoapEndpointAddress(url)
            MainSVRSoap_BindingStub service = (MainSVRSoap_BindingStub) locator.getMainSVRSoap()
//            SOAPHeaderElement header = new SOAPHeaderElement("http://vnpt.vn/", "AuthHeaderWS")
//            SOAPElement node = header.addChildElement("Username")
//            node.addTextNode(username)
//            SOAPElement node1 = header.addChildElement("Password")
//            node1.addTextNode(password)
//            service.setHeader(header)
            return service
        }
    }
}
