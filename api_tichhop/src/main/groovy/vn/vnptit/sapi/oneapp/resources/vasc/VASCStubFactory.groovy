package vn.vnptit.sapi.oneapp.resources.vasc

import client.ws.vasc.SubscriberManagementLocator
import client.ws.vasc.SubscriberManagementSoap_BindingStub
import org.apache.axis.message.SOAPHeaderElement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import org.springframework.web.context.request.NativeWebRequest
import org.tempuri.AuthHeader
import org.tempuri.AuthHeaderE
import vn.vnptit.sapi.oneapp.util.GenericApi

import javax.net.ssl.KeyManager
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.xml.soap.SOAPElement
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate


@Service
class VASCStubFactory {
    private Map<String, ThreadLocal<SubscriberManagementSoap_BindingStub>> cache = [:]
    private Map<String, AuthHeaderE> cacheHeader = [:]

    @Value("\${spring.httpservices.vasc.url}")
    String vascUrl

    @Value("\${spring.httpservices.vasc.accounts}")
    String accounts

    ThreadLocal<SubscriberManagementSoap_BindingStub> get(String phanvungId) {
        if (cache.containsKey(phanvungId)) return cache[phanvungId]
        synchronized (this) {
            if (cache.containsKey(phanvungId)) return cache[phanvungId]
            String username = ""
            String password = ""
            accounts.split(',').each {
                String[] accRec = it.split(":", 3)
                if (accRec[0] == phanvungId) {
                    username = accRec[1]
                    password = accRec[2]
                }
            }
            def newItem = ThreadLocal.withInitial {
                SubscriberManagementLocator locator = new SubscriberManagementLocator()
                locator.setSubscriberManagementSoapEndpointAddress(vascUrl)
                SubscriberManagementSoap_BindingStub service = (SubscriberManagementSoap_BindingStub) locator.getSubscriberManagementSoap()
                SOAPHeaderElement header = new SOAPHeaderElement("http://tempuri.org/", "AuthHeader")
                SOAPElement node = header.addChildElement("strUserName")
                node.addTextNode(username)
                SOAPElement node1 = header.addChildElement("strPassword")
                node1.addTextNode(password)

                service.setHeader(header)
                return service
            }

            cache.put(phanvungId, newItem)

            newItem
        }
    }

    AuthHeaderE getAuthHeader(String phanvungId){
        if (cacheHeader.containsKey(phanvungId)) return cacheHeader[phanvungId]
        synchronized (this) {
            if (cacheHeader.containsKey(phanvungId)) return cacheHeader[phanvungId]
            String username = ""
            String password = ""
            accounts.split(',').each {
                String[] accRec = it.split(":", 3)
                if (accRec[0] == phanvungId) {
                    username = accRec[1]
                    password = accRec[2]
                }
            }

            AuthHeader header = new AuthHeader()
            header.setStrUserName(username)
            header.setStrPassword(password)
            AuthHeaderE authHeaderE = new AuthHeaderE()
            authHeaderE.setAuthHeader(header)
            cacheHeader.put(phanvungId, authHeaderE)

            authHeaderE
        }
    }

}
