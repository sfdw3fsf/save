package vn.vnptit.sapi.oneapp.resources

import client.ws.visa.common.CommonBusinessServicesSoapBindingStub
import client.ws.visa.common.WSComImplServiceLocator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean

import javax.net.ssl.KeyManager
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate

@org.springframework.context.annotation.Configuration
class Configuration {
    @Bean("trustAllCerts")
    TrustManager[] getTrustAllCerts(){
        [new X509TrustManager(){

            @Override
            void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            X509Certificate[] getAcceptedIssuers() {
                return null
            }
        }]
    }
}
