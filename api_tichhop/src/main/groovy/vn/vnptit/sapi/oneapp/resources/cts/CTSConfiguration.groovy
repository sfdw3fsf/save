package vn.vnptit.sapi.oneapp.resources.cts


import org.apache.axis.message.SOAPHeaderElement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.tempuri.AuthHeader
import org.tempuri.AuthHeaderE
import ws.client.cts.LineTestWSLocator
import ws.client.cts.LineTestWSSoap_BindingStub

import javax.net.ssl.KeyManager
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.xml.soap.SOAPElement
import java.security.SecureRandom

@Configuration
class CTSConfiguration {
    @Bean("ctsServicesStubThreadLocal")
    ThreadLocal<LineTestWSSoap_BindingStub> get(
            @Autowired TrustManager[] trustAllCerts,
            @Value("\${spring.httpservices.cts.url}") String url,
            @Value("\${spring.httpservices.cts.username}") String username,
            @Value("\${spring.httpservices.cts.password}") String password
    ){
        ThreadLocal.withInitial {
            SSLContext ctx = SSLContext.getInstance("TLS")
            ctx.init(new KeyManager[0], trustAllCerts, new SecureRandom())
            SSLContext.setDefault(ctx)
            LineTestWSLocator locator = new LineTestWSLocator()
            locator.setLineTestWSSoapEndpointAddress(url)
            LineTestWSSoap_BindingStub service = (LineTestWSSoap_BindingStub) locator.getLineTestWSSoap()
            SOAPHeaderElement header = new SOAPHeaderElement("http://vnpt.vn/", "AuthHeaderWS")
            SOAPElement node = header.addChildElement("Username")
            node.addTextNode(username)
            SOAPElement node1 = header.addChildElement("Password")
            node1.addTextNode(password)
            service.setHeader(header)
            return service
        }
    }
}
